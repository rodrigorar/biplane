package pt.rodrigorar;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import pt.rodrigorar.caches.InternalCache;
import pt.rodrigorar.policies.TimeBasedEvictionPolicy;

public class Evictor extends TimerTask {
    private final List<InternalCache<?, ?>> caches;

    public Evictor() {
        this.caches = new LinkedList<>();
    }

    public void addCache(InternalCache<?, ?> cache) {
        this.caches.add(cache);
    }

    @Override
    public void run() {
        for (InternalCache<?, ?> cache : caches) {
            cache.evict(new TimeBasedEvictionPolicy<>(cache.getConfiguration()));
        }
    }
}
