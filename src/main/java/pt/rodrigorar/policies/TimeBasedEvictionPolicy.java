package pt.rodrigorar.policies;

import java.time.Duration;
import java.time.Instant;
import pt.rodrigorar.api.CacheConfiguration;
import pt.rodrigorar.caches.Entry;

public class TimeBasedEvictionPolicy<V> implements Policy<V> {
    private final CacheConfiguration cacheConfiguration;

    public TimeBasedEvictionPolicy(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
    }

    @Override
    public boolean evaluate(Entry<V> entry) {
        final Duration timeToLive = cacheConfiguration.getTimeToLive();
        if (entry.getWhenCreated().plus(timeToLive).isBefore(Instant.now())) {
            return true;
        } else {
            return false;
        }
    }
}
