package com.rodrigorar.biplane.cache;

public abstract class AbstractFactoryCache<K, V, C extends CacheConfiguration> {
	protected C _configuration;

	public final void setConfiguration(C configuration) {
		if (_configuration == null) {
			_configuration = configuration;
		}
	}

	abstract Cache<K, V> build();
}
