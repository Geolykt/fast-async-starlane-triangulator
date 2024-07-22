package de.geolykt.fast;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.badlogic.gdx.math.Vector2;

import de.geolykt.starloader.api.CoordinateGrid;
import de.geolykt.starloader.api.NamespacedKey;
import de.geolykt.starloader.api.empire.ActiveEmpire;
import de.geolykt.starloader.api.empire.Faction;
import de.geolykt.starloader.api.empire.Star;
import de.geolykt.starloader.api.event.TickCallback;

public class MockStar implements Star {

    @NotNull
    private final Vector2 coords = new Vector2();
    private int uid;

    // Simulate the horrible inefficiencies of galimulator code
    @NotNull
    private final Vector<@NotNull Star> neighbours = new Vector<>();

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public int getUID() {
        return this.uid;
    }

    public void setCoords(float x, float y) {
        this.coords.set(x, y);
    }

    @Override
    public float getX() {
        return this.coords.x;
    }

    @Override
    public float getY() {
        return this.coords.y;
    }

    @Override
    @NotNull
    public Vector2 getCoordinates() {
        return this.coords;
    }

    @Override
    public boolean hasKey(@NotNull NamespacedKey key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMetadata(@NotNull NamespacedKey key, @Nullable Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    public Object getMetadata(@NotNull NamespacedKey key) {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public CoordinateGrid getGrid() {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public Random getInternalRandom() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInternalRandom(@NotNull Random random) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addLocalDevelopment(int development) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addNeighbour(@NotNull Star star) {
        this.neighbours.add(star);
    }

    @Override
    public void addTickCallback(TickCallback<Star> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearStarlaneCache() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean doTakeover(@NotNull ActiveEmpire newOwner) {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public ActiveEmpire getAssignedEmpire() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAssignedEmpireUID() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    public Faction getFaction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getHeat() {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public NamespacedKey getMajorityFaith() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    public NamespacedKey getMinorityFaith() {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector<Integer> getNeighbourIDs() {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public List<@NotNull Star> getNeighbourList() {
        return this.neighbours;
    }

    @Override
    @NotNull
    public Vector<Star> getNeighbours() {
        return this.neighbours;
    }

    @Override
    @NotNull
    public Vector<Star> getNeighboursRecursive(int recurseDepth) {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public Iterable<TickCallback<Star>> getTickCallbacks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getWealth() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getSprawlLevel() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasNeighbour(@NotNull Star star) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isNeighbour(@NotNull Star star) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void moveRelative(float x, float y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeNeighbour(@NotNull Star star) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setAssignedEmpire(@NotNull ActiveEmpire empire) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setFaction(@Nullable Faction faction) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHeat(float heat) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setMajorityFaith(@NotNull NamespacedKey religion) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setMinorityFaith(@Nullable NamespacedKey religion) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setNeighbours(@NotNull Vector<Star> neighbours) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setSprawlLevel(float sprawl) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setWealth(float wealth) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void syncCoordinates() {
        // TODO Auto-generated method stub
        
    }
}
