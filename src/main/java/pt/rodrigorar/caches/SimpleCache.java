package pt.rodrigorar.caches;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import pt.rodrigorar.api.Cache;
import pt.rodrigorar.api.CacheConfiguration;
import pt.rodrigorar.api.EntryNotFoundException;

public class SimpleCache<K, V> implements Cache<K, V> {
    private final CacheConfiguration configuration;
    private final ConcurrentMap<K, Entry<V>> data;

    public SimpleCache(CacheConfiguration configuration) {
       this.configuration = configuration;
       this.data = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized V put(K key, V value) {
        final Entry<V> result;
        if (data.containsKey(key)) {
            result = data.get(key);
            result.updateValue(value);
        } else {
            result = new Entry(value);
            data.put(key, result);
        }
        return result.getValue();
    }

    @Override
    public synchronized Optional<V> fetch(K key) {
        final V result;
        if (data.containsKey(key)) {
            result = data.get(key).getValue();
        } else {
            result = null;
        }
        return Optional.ofNullable(result);
    }

    @Override
    public synchronized void invalidate(K key) throws EntryNotFoundException {
        if (! data.containsKey(key)) {
            throw new EntryNotFoundException("Entry not found for key " + key.toString());
        } else {
            Entry<V> entry = data.get(key);
            data.remove(entry);
        }
    }
}
