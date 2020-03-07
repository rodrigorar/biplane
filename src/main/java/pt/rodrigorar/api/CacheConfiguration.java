package pt.rodrigorar.api;

import java.time.Duration;
import pt.rodrigorar.utils.Validator;

public interface CacheConfiguration {
    Cache.Type getCacheType();
    Duration getTimeToLive();

    class Builder {
        private Cache.Type cacheType;
        private Duration timeToLive;

        public Builder setCacheType(Cache.Type cacheType) {
            this.cacheType = cacheType;
            return this;
        }

        public Builder setTimeToLive(Duration timeToLive) {
            this.timeToLive = timeToLive;
            return this;
        }

        public CacheConfiguration build() {
            Validator.notNull(cacheType);
            Validator.notNull(timeToLive);

            return new CacheConfiguration() {
                @Override
                public Cache.Type getCacheType() {
                    return cacheType;
                }

                @Override
                public Duration getTimeToLive() {
                    return timeToLive;
                }
            };
        }
    }

}
