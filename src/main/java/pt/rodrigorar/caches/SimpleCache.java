package pt.rodrigorar.caches;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import pt.rodrigorar.api.CacheConfiguration;
import pt.rodrigorar.api.EntryNotFoundException;
import pt.rodrigorar.policies.Policy;
import pt.rodrigorar.utils.Validator;

public class SimpleCache<K, V> implements InternalCache<K, V> {
    private final CacheConfiguration configuration;
    private final ConcurrentMap<K, Entry<V>> data;

    public SimpleCache(CacheConfiguration configuration) {
       this.configuration = configuration;
       this.data = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized V put(K key, V value) {
        Validator.notNull(key);
        Validator.notNull(value);

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
        Validator.notNull(key);

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
        Validator.notNull(key);

        if (! data.containsKey(key)) {
            throw new EntryNotFoundException("Entry not found for key " + key.toString());
        } else {
            Entry<V> entry = data.get(key);
            data.remove(entry);
        }
    }

    @Override
    public CacheConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public synchronized void evict(Policy<V> policy) {
        Validator.notNull(policy);

        data.forEach((key, value) -> {
            if (policy.evaluate(value)) {
                data.remove(value);
            }
        });
    }
}
