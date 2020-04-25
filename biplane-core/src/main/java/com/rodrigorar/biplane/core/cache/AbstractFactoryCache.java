package com.rodrigorar.biplane.core.cache;

import com.rodrigorar.biplane.core.eviction.EvictionExecutioner;

public abstract class AbstractFactoryCache<K, V, C extends CacheConfiguration> {
	protected C _configuration;

	public final void setConfiguration(C configuration) {
		if (_configuration == null) {
			_configuration = configuration;
		}
	}

	public final Cache<K, V> build() {
		final Cache<K, V> cache = doBuild();
		EvictionExecutioner evictionExecutioner = EvictionExecutioner.getInstance();
		evictionExecutioner.addCache(cache.getInternalCache());
		return cache;
	}

	protected abstract Cache<K, V> doBuild();
}
