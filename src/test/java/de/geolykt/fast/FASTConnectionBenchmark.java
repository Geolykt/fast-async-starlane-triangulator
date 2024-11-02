package de.geolykt.fast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;
import org.stianloader.concurrent.ConcurrentInt62Set;

import de.geolykt.starloader.api.Galimulator;
import de.geolykt.starloader.api.empire.Star;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public class FASTConnectionBenchmark {

    private static final int STAR_COUNT = 10_000;

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void connectConcurrentInt62SetAsync(Blackhole blackhole) {
        List<@NotNull Star> stars = new ArrayList<>();
        float maxY = (float) (Math.sqrt(FASTConnectionBenchmark.STAR_COUNT / 100) * 0.8F);
        float maxX = maxY * 1.7777778F;

        for (int i = 0; i < FASTConnectionBenchmark.STAR_COUNT; i++) {
            MockStar s = new MockStar();
            s.setUid(i - 1);
            s.setCoords(ThreadLocalRandom.current().nextFloat() * maxX * 2 - maxX, ThreadLocalRandom.current().nextFloat() * maxY * 2 - maxY);
            stars.add(s);
        }

        FastAsynchronousStarlaneTriangulator.INSTANCE.connectStars(stars, maxX, maxY);

        for (Star star : stars) {
            for (Star neighbour : star.getNeighbourList()) {
                blackhole.consume(star.getUID());
                blackhole.consume(neighbour.getUID());
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void connectConcurrentInt62SetSync(Blackhole blackhole) {
        List<@NotNull Star> stars = new ArrayList<>();
        float maxY = (float) (Math.sqrt(FASTConnectionBenchmark.STAR_COUNT / 100) * 0.8F);
        float maxX = maxY * 1.7777778F;

        for (int i = 0; i < FASTConnectionBenchmark.STAR_COUNT; i++) {
            MockStar s = new MockStar();
            s.setUid(i - 1);
            s.setCoords(ThreadLocalRandom.current().nextFloat() * maxX * 2 - maxX, ThreadLocalRandom.current().nextFloat() * maxY * 2 - maxY);
            stars.add(s);
        }

        FastAsynchronousStarlaneTriangulator.INSTANCE.connectStars(stars, maxX, maxY, (starCount) -> new ConcurrentInt62Set(Math.max(Integer.highestOneBit(starCount) >> 5, 16)), Runnable::run);

        for (Star star : stars) {
            for (Star neighbour : star.getNeighbourList()) {
                blackhole.consume(star.getUID());
                blackhole.consume(neighbour.getUID());
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void connectFastutilSync(Blackhole blackhole) {
        List<@NotNull Star> stars = new ArrayList<>();
        float maxY = (float) (Math.sqrt(FASTConnectionBenchmark.STAR_COUNT / 100) * 0.8F);
        float maxX = maxY * 1.7777778F;

        for (int i = 0; i <FASTConnectionBenchmark.STAR_COUNT; i++) {
            MockStar s = new MockStar();
            s.setUid(i - 1);
            s.setCoords(ThreadLocalRandom.current().nextFloat() * maxX * 2 - maxX, ThreadLocalRandom.current().nextFloat() * maxY * 2 - maxY);
            stars.add(s);
        }

        FastAsynchronousStarlaneTriangulator.INSTANCE.connectStars(stars, maxX, maxY, LongOpenHashSet::new, Runnable::run);

        for (Star star : stars) {
            for (Star neighbour : star.getNeighbourList()) {
                blackhole.consume(star.getUID());
                blackhole.consume(neighbour.getUID());
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void connectJavaConcurrentHashMapAsync(Blackhole blackhole) {
        List<@NotNull Star> stars = new ArrayList<>();
        float maxY = (float) (Math.sqrt(FASTConnectionBenchmark.STAR_COUNT / 100) * 0.8F);
        float maxX = maxY * 1.7777778F;

        for (int i = 0; i < FASTConnectionBenchmark.STAR_COUNT; i++) {
            MockStar s = new MockStar();
            s.setUid(i - 1);
            s.setCoords(ThreadLocalRandom.current().nextFloat() * maxX * 2 - maxX, ThreadLocalRandom.current().nextFloat() * maxY * 2 - maxY);
            stars.add(s);
        }

        FastAsynchronousStarlaneTriangulator.INSTANCE.connectStars(stars, maxX, maxY, (starCount) -> {
            return new ObjectAsLongSet(Objects.requireNonNull(ConcurrentHashMap.newKeySet(starCount)));
        }, ForkJoinPool.commonPool());

        for (Star star : stars) {
            for (Star neighbour : star.getNeighbourList()) {
                blackhole.consume(star.getUID());
                blackhole.consume(neighbour.getUID());
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void connectJavaHashSetSync(Blackhole blackhole) {
        List<@NotNull Star> stars = new ArrayList<>();
        float maxY = (float) (Math.sqrt(FASTConnectionBenchmark.STAR_COUNT / 100) * 0.8F);
        float maxX = maxY * 1.7777778F;

        for (int i = 0; i <FASTConnectionBenchmark.STAR_COUNT; i++) {
            MockStar s = new MockStar();
            s.setUid(i - 1);
            s.setCoords(ThreadLocalRandom.current().nextFloat() * maxX * 2 - maxX, ThreadLocalRandom.current().nextFloat() * maxY * 2 - maxY);
            stars.add(s);
        }

        FastAsynchronousStarlaneTriangulator.INSTANCE.connectStars(stars, maxX, maxY, (len) -> {
            return new ObjectAsLongSet(new HashSet<>(len));
        }, Runnable::run);

        for (Star star : stars) {
            for (Star neighbour : star.getNeighbourList()) {
                blackhole.consume(star.getUID());
                blackhole.consume(neighbour.getUID());
            }
        }
    }

    static {
        Galimulator.setImplementation(new MockGalimImpl());
    }
}
