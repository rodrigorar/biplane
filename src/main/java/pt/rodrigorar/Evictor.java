package pt.rodrigorar;

import java.util.LinkedList;
import java.util.List;
import pt.rodrigorar.caches.InternalCache;
import pt.rodrigorar.policies.TimeBasedEvictionPolicy;

public class Evictor {
    private final List<InternalCache<?, ?>> caches;

    public Evictor() {
        this.caches = new LinkedList<>();
    }

    public void addCache(InternalCache<?, ?> cache) {
        this.caches.add(cache);
    }

    public void evictFromAllCaches() {
        for (InternalCache<?, ?> cache : caches) {
            cache.evict(new TimeBasedEvictionPolicy<>(cache.getConfiguration()));
        }
    }
}
