package de.geolykt.fast;

import java.util.concurrent.atomic.AtomicInteger;

import org.jetbrains.annotations.NotNull;

import de.geolykt.starloader.api.gui.BackgroundTask;

final class ProgressBackgroundTask implements BackgroundTask {
    @NotNull
    private final String currentDescription;
    @NotNull
    private final AtomicInteger counter;
    private final int capacity;

    public ProgressBackgroundTask(@NotNull String step, @NotNull AtomicInteger counter, int capacity) {
        this.currentDescription = step;
        this.counter = counter;
        this.capacity = capacity;
    }

    @Override
    @NotNull
    public String getProgressDescription() {
        return this.currentDescription + String.format(": %02.2f %% done.", this.counter.get() * 100.0F / this.capacity);
    }

    @Override
    public float getTaskProgress() {
        return this.counter.get() / ((float) this.capacity);
    }
}
