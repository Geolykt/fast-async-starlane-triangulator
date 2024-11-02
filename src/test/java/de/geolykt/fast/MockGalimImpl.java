package de.geolykt.fast;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import de.geolykt.starloader.api.Galimulator;
import de.geolykt.starloader.api.actor.SpawnPredicatesContainer;
import de.geolykt.starloader.api.actor.WeaponsManager;
import de.geolykt.starloader.api.dimension.Empire;
import de.geolykt.starloader.api.empire.Star;
import de.geolykt.starloader.api.gui.BackgroundTask;
import de.geolykt.starloader.api.gui.MapMode;
import de.geolykt.starloader.api.gui.MouseInputListener;
import de.geolykt.starloader.api.serial.SavegameFormat;
import de.geolykt.starloader.api.serial.SupportedSavegameFormat;
import de.geolykt.starloader.api.sound.SoundHandler;
import de.geolykt.starloader.api.utils.RandomNameType;
import de.geolykt.starloader.api.utils.TickLoopLock;

final class MockGalimImpl implements Galimulator.GameImplementation {

    @NotNull
    private BackgroundTask task = new MockBackgroundTask();

    @Override
    public void connectStars(@NotNull Star starA, @NotNull Star starB) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void disconnectStars(@NotNull Star starA, @NotNull Star starB) {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public String generateRandomName(@NotNull RandomNameType type) {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public MapMode getActiveMapmode() {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public BackgroundTask getBackgroundTask() {
        return this.task;
    }

    @Override
    @Nullable
    @SuppressWarnings("deprecation")
    public de.geolykt.starloader.api.empire.ActiveEmpire getEmpireByUID(int uid) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public List<de.geolykt.starloader.api.empire.@NotNull ActiveEmpire> getEmpires() {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public @UnmodifiableView Collection<de.geolykt.starloader.api.empire.@NotNull ActiveEmpire> getEmpiresView() {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getGameYear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public de.geolykt.starloader.api.@NotNull Map getMap() {
        throw new UnsupportedOperationException();

    }

    @Override
    @Nullable
    public Star getNearestStar(float boardX, float boardY, float searchRadius) {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public de.geolykt.starloader.api.empire.@NotNull ActiveEmpire getNeutralEmpire() {
        throw new UnsupportedOperationException();

    }

    @Override
    @Nullable
    @SuppressWarnings("deprecation")
    public de.geolykt.starloader.api.empire.@NotNull ActiveEmpire getPlayerEmpire() {
        throw new UnsupportedOperationException();

    }

    @Override
    @Nullable
    public SavegameFormat getSavegameFormat(@NotNull InputStream input) {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public SavegameFormat getSavegameFormat(@NotNull SupportedSavegameFormat format) {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public Iterable<? extends SavegameFormat> getSavegameFormats() {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public TickLoopLock getSimulationLoopLock() {
        throw new UnsupportedOperationException();

    }

    @Override
    public @NotNull SoundHandler getSoundHandler() {
        throw new UnsupportedOperationException();

    }

    @Override
    @Nullable
    public Star getStarAt(float boardX, float boardY) {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public List<@NotNull Star> getStarList() {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public List<@NotNull Star> getStars() {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public SpawnPredicatesContainer getStateActorSpawningPredicates() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getTranscendedEmpires() {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull de.geolykt.starloader.api.Galimulator.@NotNull Unsafe getUnsafe() {
        throw new UnsupportedOperationException();

    }

    @Override
    public @NotNull WeaponsManager getWeaponsManager() {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean hasUsedSandbox() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPaused() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadClipboardScenario() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void loadGameState(byte[] data) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void loadGameState(@NotNull InputStream in) throws IOException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void loadSavegameFile(@NotNull Path savegameFile) {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public Empire lookupEmpire(int uid) {
        throw new UnsupportedOperationException();

    }

    @Override
    @NotNull
    public Star lookupStar(int id) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void panic(@NotNull String message, boolean save) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void panic(@NotNull String message, boolean save, @NotNull Throwable cause) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void pauseGame() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void recalculateVoronoiGraphs() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void registerMouseInputListener(@NotNull MouseInputListener listener) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void resumeGame() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void runTaskOnNextFrame(Runnable task) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void runTaskOnNextTick(@NotNull Runnable runnable) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void saveFile(@NotNull String name, byte[] data) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void saveFile(@NotNull String name, @NotNull InputStream data) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setActiveMapmode(@NotNull MapMode mode) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setBackgroundTask(@NotNull BackgroundTask task) {
        this.task = task;
    }

    @Override
    public void setBackgroundTaskProgress(@Nullable String progressDescription) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setGameYear(int year) {
        throw new UnsupportedOperationException();

    }

    @Override
    @SuppressWarnings("deprecation")
    public void setMap(@NotNull de.geolykt.starloader.api.@NotNull Map map) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setPaused(boolean paused) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setTranscendedEmpires(int count) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setUsedSandbox(boolean state) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void showGalaxyCreationScreen() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void showOnlineScenarioBrowser() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void showModUploadScreen() {
        throw new UnsupportedOperationException();

    }

    @Override
    @SuppressWarnings("deprecation")
    public void showScenarioMetadataEditor(@NotNull de.geolykt.starloader.api.@NotNull Map map) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void showScenarioSaveScreen() {
        throw new UnsupportedOperationException();

    }
}
