package com.primax.util.cons;

import java.util.List;

public class Is<T> {

	private T value;

	public Is(T value) {
		this.value = value;
	}

	public boolean in(T... set) {
		for (T item : set) {
			if (value.equals(item)) {
				return true;
			}
		}
		return false;
	}

	public boolean inList(List<?> list) {
		for (Object item : list) {
			if (value.equals(item)) {
				return true;
			}
		}
		return false;
	}

	public static <T> Is<T> is(T value) {
		return new Is<T>(value);
	}
}