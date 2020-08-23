/***
 * Copyright 2020 Rodrigo Ramos Rosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rodrigorar.biplane.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CacheFactoryRegistry {
	private static CacheFactoryRegistry _instance;
	private final Map<Class<?>, CacheRegistry<?, ?, ?>> _registry;

	private CacheFactoryRegistry() {
		_registry = new HashMap<>();
	}

	public static CacheFactoryRegistry getInstance() {
		if (_instance == null) {
			_instance = new CacheFactoryRegistry();
		}
		return _instance;
	}

	public synchronized <I extends InternalCache<?, ?>,
			F extends AbstractFactoryCache<?, ?, C>,
			C extends CacheConfiguration<?, ?>> void register(Class<I> cache, Class<F> factory, Class<C> configuration) {
		CacheRegistry<I, F, C> register = new CacheRegistry<>(cache, factory, configuration);
		_registry.put(cache, register);
	}

	public <I extends InternalCache<?, ?>,
			F extends AbstractFactoryCache<?, ?, C>,
			C extends CacheConfiguration<?, ?>> Optional<F> factory(Class<I> clazz) {
		CacheRegistry<?, ?, ?> register = _registry.get(clazz);
		F result = null;
		if (register != null) {
			try {
				Class<C> configurationClass = (Class<C>)register.getConfiguration();
				C configuration = configurationClass.newInstance();
				Class<F> factory = (Class<F>)register.getFactory();
				result = factory.newInstance();
				result.setConfiguration(configuration);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return Optional.ofNullable(result);
	}

	private static class CacheRegistry<
			I extends InternalCache<?, ?>,
			F extends AbstractFactoryCache<?, ?, C>,
			C extends CacheConfiguration<?, ?>> {
		private final Class<I> _cache;
		private final Class<F> _factory;
		private final Class<C> _configuration;

		public CacheRegistry(Class<I> cache, Class<F> factory, Class<C> configuration) {
			_cache = cache;
			_factory = factory;
			_configuration = configuration;
		}

		public Class<I> getCache() {
			return _cache;
		}

		public Class<F> getFactory() {
			return _factory;
		}

		public Class<C> getConfiguration() {
			return _configuration;
		}
	}
}
