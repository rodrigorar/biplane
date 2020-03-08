package pt.rodrigorar.caches;

import pt.rodrigorar.api.Cache;
import pt.rodrigorar.api.CacheConfiguration;
import pt.rodrigorar.policies.Policy;

public interface InternalCache<K, V> extends Cache<K, V> {
    CacheConfiguration getConfiguration();
    void evict(Policy<V> policy);
}
