package com.comphenix.detectcme.injector;

import java.util.ListIterator;

public class SingleThreadedListIterator<T> extends AbstractSingleThreaded<ListIterator<T>> implements ListIterator<T> {
	public SingleThreadedListIterator(ListIterator<T> delegate, Thread expected) {
		super(delegate, expected);
	}

	@Override
	public void add(T e) {
		verifyThread();
		delegate.add(e);
	}
	
	@Override
	public void remove() {
		verifyThread();
		delegate.remove();
	}

	@Override
	public void set(T e) {
		verifyThread();
		delegate.set(e);
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return delegate.hasPrevious();
	}

	@Override
	public T next() {
		return delegate.next();
	}

	@Override
	public int nextIndex() {
		return delegate.nextIndex();
	}

	@Override
	public T previous() {
		return delegate.previous();
	}

	@Override
	public int previousIndex() {
		return delegate.previousIndex();
	}
}
