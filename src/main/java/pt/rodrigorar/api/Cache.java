package pt.rodrigorar.api;

public interface Cache<K, V> {
    V put(K key, V value);
    V fetch(K key);
    void invalidate(K key);
}
