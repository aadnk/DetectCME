package com.comphenix.detectcme.injector;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * Represents a list that can only be modified by a given thread.
 * 
 * @author Kristian
 * @param <T> - type of each element in the list.
 */
public class SingleThreadedList<T> extends SingleThreadedCollection<T> implements List<T> {
	protected List<T> listDelegate;
	
	public SingleThreadedList(List<T> delegate, Thread expected) {
		super(delegate, expected);
		this.listDelegate = delegate;
	}

	@Override
	public void add(int index, T element) {
		verifyThread();
		listDelegate.add(index, element);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		verifyThread();
		return listDelegate.addAll(index, c);
	}

	@Override
	public T get(int index) {
		return listDelegate.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return listDelegate.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return listDelegate.lastIndexOf(o);
	}
	
	@Override
	public ListIterator<T> listIterator() {
		return new SingleThreadedListIterator<T>(listDelegate.listIterator(), expected);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new SingleThreadedListIterator<T>(listDelegate.listIterator(index), expected);
	}
	
	@Override
	public T remove(int index) {
		verifyThread();
		return listDelegate.remove(index);
	}
	
	@Override
	public T set(int index, T element) {
		verifyThread();
		return listDelegate.set(index, element);
	}
	
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		verifyThread();
		return listDelegate.subList(fromIndex, toIndex);
	}
}
