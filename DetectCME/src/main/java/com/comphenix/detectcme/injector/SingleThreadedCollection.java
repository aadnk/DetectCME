package com.comphenix.detectcme.injector;

import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a collection that can only be modified by a given thread.
 * 
 * @author Kristian
 * @param <T> - type of each element in the collection.
 */
public class SingleThreadedCollection<T> 
      extends AbstractSingleThreaded<Collection<T>> implements Collection<T>  {
	
	public SingleThreadedCollection(Collection<T> delegate, Thread expected) {
		super(delegate, expected);
	}

	@Override
	public boolean add(T e) {
		verifyThread();
		return delegate.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		verifyThread();
		return delegate.addAll(c);
	}

	@Override
	public void clear() {
		verifyThread();
		delegate.clear();
	}

	@Override
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}
	
	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new SingleThreadedIterator<T>(delegate.iterator(), expected);
	}

	@Override
	public boolean remove(Object o) {
		verifyThread();
		return delegate.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		verifyThread();
		return delegate.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		verifyThread();
		return delegate.retainAll(c);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	@Override
	public <V> V[] toArray(V[] a) {
		return delegate.toArray(a);
	}
}
