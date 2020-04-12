package com.rodrigorar.biplane.utils;

public class Validator {

	public static void isNotNull(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
	}

	public static void isValidArgument(boolean evaluation) {
		if (! evaluation) {
			throw new IllegalArgumentException();
		}
	}
}
