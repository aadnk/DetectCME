package com.comphenix.detectcme.injector;

import java.util.Set;

/**
 * Represents a set that can only be modified by a given thread.
 * 
 * @author Kristian
 * @param <T> - type of each element in the set.
 */
public class SingleThreadedSet<T> extends SingleThreadedCollection<T> implements Set<T> {
	public SingleThreadedSet(Set<T> delegate, Thread expected) {
		super(delegate, expected);
	}
}
