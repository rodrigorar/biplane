package pt.rodrigorar.api;

import java.util.Optional;

public interface Cache<K, V> {
    enum Type {
        SIMPLE
    }

    V put(K key, V value);
    Optional<V> fetch(K key);
    void invalidate(K key) throws EntryNotFoundException;
}
