package pt.rodrigorar.api;

public class UnknownCacheType extends Exception {
    public UnknownCacheType(Cache.Type cacheType) {
        super("Unknown cache type" + cacheType);
    }
}
