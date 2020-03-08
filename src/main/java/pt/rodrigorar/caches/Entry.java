package pt.rodrigorar.caches;

import java.time.Instant;

public class Entry<V> {
    private V value;
    private Instant whenCreated;
    private Instant whenAccessed;
    private Instant whenModified;

    public Entry(V value) {
        this.value = value;
        this.whenCreated = Instant.now();
        this.whenAccessed = Instant.now();
        this.whenModified = Instant.now();
    }

    public synchronized V getValue() {
        this.whenAccessed = Instant.now();
        return this.value;
    }

    public synchronized void updateValue(V newValue) {
        this.value = newValue;
        this.whenModified = Instant.now();
    }

    public synchronized Instant getWhenCreated() {
        return this.whenCreated;
    }

    public synchronized Instant getWhenAccessed() {
        return this.whenAccessed;
    }

    public synchronized Instant getWhenModified() {
        return this.whenModified;
    }
}
