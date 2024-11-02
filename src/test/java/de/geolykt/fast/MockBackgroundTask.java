package de.geolykt.fast;

import org.jetbrains.annotations.NotNull;

import de.geolykt.starloader.api.gui.BackgroundTask;

final class MockBackgroundTask implements BackgroundTask {
    @Override
    @NotNull
    public String getProgressDescription() {
        return "";
    }

    @Override
    public float getTaskProgress() {
        return 0;
    }
}
