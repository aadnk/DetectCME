package com.comphenix.detectcme.injector;

import java.util.Iterator;

public class SingleThreadedIterator<T> extends AbstractSingleThreaded<Iterator<T>> implements Iterator<T> {
	public SingleThreadedIterator(Iterator<T> delegate, Thread expected) {
		super(delegate, expected);
	}

	@Override
	public boolean hasNext() {
		// Safe
		return delegate.hasNext();
	}

	@Override
	public T next() {
		// And safe
		return delegate.next();
	}

	@Override
	public void remove() {
		verifyThread();
		delegate.remove();
	}
}
