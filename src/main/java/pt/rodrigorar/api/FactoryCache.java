package pt.rodrigorar.api;

import java.util.Timer;
import pt.rodrigorar.Evictor;
import pt.rodrigorar.caches.SimpleCache;

public class FactoryCache {
    private static FactoryCache _instance;
    private final Evictor evictor;

    private FactoryCache() {
        this.evictor = new Evictor();
    }

    public static FactoryCache getInstance() {
        if (_instance == null) {
            _instance = new FactoryCache();
            _instance.initiateEvictor();
        }
        return _instance;
    }

    private void initiateEvictor() {
        Timer timer = new Timer();
        timer.schedule(evictor, 60000); // 60s
    }

    public <K, V> void buildCache(CacheConfiguration cacheConfiguration) throws UnknownCacheType {
        switch(cacheConfiguration.getCacheType()) {
            case SIMPLE:
                evictor.addCache(new SimpleCache<>(cacheConfiguration));
                break;
            default:
                throw new UnknownCacheType(cacheConfiguration.getCacheType());
        }
    }
}
