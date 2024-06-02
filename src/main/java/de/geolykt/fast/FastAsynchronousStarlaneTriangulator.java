package de.geolykt.fast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stianloader.concurrent.ConcurrentInt62Set;

import de.geolykt.starloader.api.empire.Star;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongSet;

public final class FastAsynchronousStarlaneTriangulator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastAsynchronousStarlaneTriangulator.class);
    public static final FastAsynchronousStarlaneTriangulator INSTANCE = new FastAsynchronousStarlaneTriangulator();

    public void connectStars(List<@NotNull Star> stars, double maxX, double maxY) {
        int starCount = stars.size();
        int gridXSize = (int) Math.ceil((maxX * 2D) / DimensionalRegion.REGION_SIZE) + 1;
        int gridYSize = (int) Math.ceil((maxY * 2D) / DimensionalRegion.REGION_SIZE) + 1;
        DimensionalRegion[] grid = new DimensionalRegion[gridXSize * gridYSize];
        LongSet starlanes = new ConcurrentInt62Set(Math.max(Integer.highestOneBit(starCount) >> 5, 16));
//      LongSet starlanes = new LongOpenHashSet();

        for (int i = 0; i < grid.length; i++) {
            grid[i] = new DimensionalRegion();
        }

        LOGGER.info("Grid size {} by {} at {} elements total", gridXSize, gridYSize, grid.length);

        for (int i = 0; i < starCount; i++) {
            Star star = stars.get(i);
            int gridX = (int) ((star.getX() + maxX) / DimensionalRegion.REGION_SIZE);
            int gridY = (int) ((star.getY() + maxY) / DimensionalRegion.REGION_SIZE);

            grid[gridY * gridXSize + gridX].insert(star);
        }

        for (int i = 0; i < grid.length; i++) {
            grid[i].bake();
        }

        LOGGER.info("Grid baked.");

        AtomicInteger counter = new AtomicInteger();
        CompletableFuture<?>[] futures = new CompletableFuture[grid.length + (gridXSize - 1) * gridYSize + gridXSize * (gridYSize - 1)];

        for (int i = 0; i < grid.length; i++) {
            final int gridId = i;
            futures[i] = CompletableFuture.runAsync(() -> {
                grid[gridId].connectStars(stars, grid, gridId, gridXSize, starlanes);
                int val = counter.incrementAndGet();
                if (val % 100 == 0) {
                    LOGGER.info("Connecting stars... {}% done", ((float) val * 100) / futures.length);
                }
            });
        }

        int n = grid.length;
        for (int i = 0; i < gridYSize; i++) {
            for (int j = 0; j < (gridXSize - 1); j++) {
                int baseIndex = i * gridXSize + j;
                futures[n++] = CompletableFuture.runAsync(() -> {
                    grid[baseIndex].connectRegions(grid[baseIndex + 1], starlanes);
                    int progress = counter.incrementAndGet();
                    if (progress % 100 == 0) {
                        LOGGER.info("Connecting stars... {}% done", ((float) progress * 100) / futures.length);
                    }
                });
            }
        }
        for (int i = 0; i < gridYSize - 1; i++) {
            for (int j = 0; j < gridXSize; j++) {
                int baseIndex = i * gridXSize + j;
                futures[n++] = CompletableFuture.runAsync(() -> {
                    grid[baseIndex].connectRegions(grid[baseIndex + gridXSize], starlanes);
                    int progress = counter.incrementAndGet();
                    if (progress % 100 == 0) {
                        LOGGER.info("Connecting stars... {}% done", ((float) progress * 100) / futures.length);
                    }
                });
            }
        }

        LOGGER.info("Runners started; awaiting completion");
        CompletableFuture.allOf(futures).join();
        LOGGER.info("Computed lanes");
        long[] lanes = starlanes.toLongArray();
        LOGGER.info("Applying {} starlanes", lanes.length);

        for (long lane : lanes) {
            Star starA = stars.get((int) (lane & 0xFFFFFF));
            Star starB = stars.get((int) (lane >> 24));
            starA.addNeighbour(starB);
            starB.addNeighbour(starA);
        }
    }

    private static final class DimensionalRegion {
        // Constants uplifted from GMPP's codebase
        static final float GRANULARITY_FACTOR = 0.035F;
        static final float MAP_FACTOR = 5F;
        static final float REGION_SIZE = GRANULARITY_FACTOR * MAP_FACTOR * 16;

        private final List<Star> stars = new ArrayList<>();
        private Star[] starArr;

        void insert(Star star) {
            this.stars.add(star);
        }

        void bake() {
            this.starArr = this.stars.toArray(new Star[0]);
        }

        void connectStars(List<Star> stars, DimensionalRegion[] grid, int regionId, int gridWidth, LongSet out) {
            Int2ObjectMap<IntSet> networks = new Int2ObjectOpenHashMap<>(this.starArr.length);
            for (Star star : this.starArr) {
                IntSet set = new IntOpenHashSet();
                set.add(star.getUID() + 1);
                networks.put(star.getUID() + 1, set);
            }
            this.selfFastInterconnect(out, networks);
            this.intertwineNets(stars, out, networks);
        }

        private static float distanceSq(float x1, float y1, float x2, float y2) {
            x1 -= x2;
            y1 -= y2;
            return x1 * x1 + y1 * y1;
        }

        private void intertwineNets(List<Star> stars, LongSet out, Int2ObjectMap<IntSet> networks) {
            int largestNetwork = 0;
            for (Star star : this.starArr) {
                largestNetwork = Math.max(largestNetwork, networks.get(star.getUID() + 1).size());
            }

            while (largestNetwork != this.starArr.length) {
                for (Star star : this.starArr) {
                    float minDist = Float.MAX_VALUE;
                    Star minDistStar = null;
                    IntSet sourceNetwork = networks.get(star.getUID() + 1);
                    for (Star other : this.starArr) {
                        if (sourceNetwork.contains(other.getUID() + 1)) {
                            continue;
                        }
                        float dist = distanceSq(star.getX(), star.getY(), other.getX(), other.getY());
                        if (dist < minDist) {
                            minDist = dist;
                            minDistStar = other;
                        }
                    }

                    assert minDistStar != null : "There should be at least one remaining star at " + networks + " networks";

                    Star sourceStar = star;
                    Star targetStar = minDistStar;

                    if (minDist > GRANULARITY_FACTOR * 4F) {
                        // That is a long distance ...
                        // Perhaps a different star from the same source network can connect to it?
                        IntIterator sourceNetworkIterator = sourceNetwork.iterator();
                        while (sourceNetworkIterator.hasNext()) {
                            Star candidate = stars.get(sourceNetworkIterator.nextInt());
                            float dist = distanceSq(candidate.getX(), candidate.getY(), targetStar.getX(), targetStar.getY());
                            if (dist < minDist) {
                                minDist = dist;
                                sourceStar = candidate;
                            }
                        }
                    }

                    final int sourceID = sourceStar.getUID() + 1;
                    final int targetID = targetStar.getUID() + 1;
                    out.add(DimensionalRegion.hash(sourceID, targetID));
                    DimensionalRegion.handleReachabilities(networks, sourceID, targetID);

                    if ((largestNetwork = Math.max(largestNetwork, networks.get(sourceID).size())) == this.starArr.length) {
                        break;
                    }
                }
            }
        }

        private void selfFastInterconnect(LongSet out, Int2ObjectMap<IntSet> reachabilities) {
            for (int i = 0; i < this.starArr.length; i++) {
                Star a = this.starArr[i];
                for (int j = 0; j < i; j++) {
                    Star b = this.starArr[j];
                    if (distanceSq(a.getX(), a.getY(), b.getX(), b.getY()) < GRANULARITY_FACTOR * 2F) {
                        out.add(DimensionalRegion.hash(a.getUID() + 1, b.getUID() + 1));
                        DimensionalRegion.handleReachabilities(reachabilities, a.getUID() + 1, b.getUID() + 1);
                    }
                }
            }
        }

        private void connectRegions(DimensionalRegion target, LongSet out) {
            if (this.starArr.length == 0 || target.starArr.length == 0) {
                return;
            }
            float cutoff = GRANULARITY_FACTOR * 2F;
            boolean modified = false;
            while (!modified) {
                for (Star starT : target.starArr) {
                    for (Star starS : this.starArr) {
                        if (distanceSq(starS.getX(), starS.getY(), starT.getX(), starT.getY()) < cutoff) {
                            out.add(DimensionalRegion.hash(starT.getUID() + 1, starS.getUID() + 1));
                            modified = true;
                        }
                    }
                }
                cutoff += GRANULARITY_FACTOR;
                assert cutoff < DimensionalRegion.REGION_SIZE * 2 : "Cutoff limit exceeeded";
            }
        }

        private static void handleReachabilities(Int2ObjectMap<IntSet> reachabilities, int a, int b) {
            IntSet reachabilitiesA = reachabilities.get(a);
            IntSet reachabilitiesB = reachabilities.get(b);

            if (reachabilitiesA.size() > reachabilitiesB.size()) {
                DimensionalRegion.mergeNetworks(reachabilitiesA, reachabilitiesB, reachabilities);
            } else {
                DimensionalRegion.mergeNetworks(reachabilitiesB, reachabilitiesA, reachabilities);
            }
        }

        private static void mergeNetworks(IntSet absorber, IntSet absorbed, Int2ObjectMap<IntSet> networks) {
            absorber.addAll(absorbed);
            for (int member : absorbed) {
                networks.put(member, absorber);
            }
        }

        private static long hash(long a, long b) {
            if (a > b) {
                return (a << 24) | b;
            } else {
                return (b << 24) | a;
            }
        }
    }
}
