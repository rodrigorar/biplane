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

package com.rodrigorar.biplane.core.eviction;

import java.util.concurrent.ConcurrentHashMap;

public class PolicyRegistry<P extends Policy, F extends FactoryPolicy> {
	private static PolicyRegistry _instance;
	private final ConcurrentHashMap<Class<P>, Class<F>> _registry;

	private PolicyRegistry() {
		_registry = new ConcurrentHashMap<>();
	}

	public static PolicyRegistry getInstance() {
		if (_instance == null) {
			_instance = new PolicyRegistry();
		}
		return _instance;
	}

	public void register(Class<P> policy, Class<F> factoryPolicy) {
		_registry.put(policy, factoryPolicy);
	}

	public F factory(Class<P> policyClass) throws IllegalAccessException, InstantiationException {
		Class<F> factoryClass = _registry.get(policyClass);
		F factory = factoryClass.newInstance();
		return factory;
	}
}
