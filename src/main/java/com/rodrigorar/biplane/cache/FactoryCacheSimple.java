package com.rodrigorar.biplane.cache;

import com.rodrigorar.biplane.eviction.FactoryPolicy;
import com.rodrigorar.biplane.utils.Validator;

import java.time.Duration;

public class FactoryCacheSimple<K, V> extends AbstractFactoryCache<K, V, CacheConfigurationSimple> {
	private Duration _timeToLive;
	private Integer _maxEntries;

	public FactoryCacheSimple timeToLive(Duration timeToLive) {
		_timeToLive = timeToLive;
		return this;
	}

	public FactoryCacheSimple maxEntries(int maxEntries) {
		_maxEntries = maxEntries;
		return this;
	}

	@Override
	Cache<K, V> build() {
		Validator.validateOrDefault(_timeToLive, Duration.ofMinutes(10));
		Validator.validateOrDefault(_maxEntries, 100);

		_configuration.setEvictionPolicy(FactoryPolicy.timeBased(_timeToLive));
		_configuration.setMaxEntries(_maxEntries);
		InternalCache<K, V> internal = new InternalCacheSimple<K, V>(_configuration);
		return new Cache(internal);
	}
}
