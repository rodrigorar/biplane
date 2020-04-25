package com.rodrigorar.biplane.toolkit.cache;

import com.rodrigorar.biplane.core.cache.AbstractFactoryCache;
import com.rodrigorar.biplane.core.cache.Cache;
import com.rodrigorar.biplane.core.cache.InternalCache;
import com.rodrigorar.biplane.core.utils.Validator;
import com.rodrigorar.biplane.toolkit.policy.FactoryPolicy;

import java.time.Duration;
import java.util.function.Function;

public class FactoryCacheLoading<K, V> extends AbstractFactoryCache<K, V, CacheConfigurationLoading> {
	private Duration _timeToLive;
	private Integer _maxEntries;
	private Function<K, V> _cacheLoader;

	public FactoryCacheLoading timeToLive(Duration timeToLive) {
		_timeToLive = timeToLive;
		return this;
	}

	public FactoryCacheLoading maxEntries(int maxEntries) {
		_maxEntries = maxEntries;
		return this;
	}

	public FactoryCacheLoading cacheLoader(Function<K, V> cacheLoader) {
		_cacheLoader = cacheLoader;
		return this;
	}

	@Override
	public Cache<K, V> build() {
		Validator.validateOrDefault(_timeToLive, Duration.ofMinutes(10));
		Validator.validateOrDefault(_maxEntries, 100);
		Validator.validateOrDefault(_cacheLoader, (Function) o -> null);

		_configuration.setEvictionPolicy(FactoryPolicy.timeBased(_timeToLive));
		_configuration.setMaxEntries(_maxEntries);
		_configuration.setCacheLoader(_cacheLoader);
		InternalCache<K, V> internal = new InternalCacheLoading<K, V>(_configuration);
		return new Cache<>(internal);
	}
}
