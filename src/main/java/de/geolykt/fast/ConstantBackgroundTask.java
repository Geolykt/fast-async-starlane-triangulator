package de.geolykt.fast;

import org.jetbrains.annotations.NotNull;

import de.geolykt.starloader.api.gui.BackgroundTask;

final class ConstantBackgroundTask implements BackgroundTask {

    @NotNull
    private final String description;

    public ConstantBackgroundTask(@NotNull String description) {
        this.description = description;
    }

    @Override
    @NotNull
    public String getProgressDescription() {
        return this.description;
    }

    @Override
    public float getTaskProgress() {
        return 0;
    }
}
