package com.rodrigorar.biplane.cache;

public abstract class AbstractFactoryCache<I extends InternalCache, C extends CacheConfiguration> {
	private C _configuration;

	public final void setConfiguration(C configuration) {
		_configuration = configuration;
	}

	abstract I build();
}
