package com.rodrigorar.biplane.core.eviction;

public interface FactoryPolicy<P extends Policy> {
	P build();
}
