package com.comphenix.detectcme.injector;

public abstract class AbstractSingleThreaded<T> {
	protected final T delegate;
	protected final Thread expected;

	public AbstractSingleThreaded(T delegate, Thread expected) {
		this.delegate = delegate;
		this.expected = expected;
	}

	/**
	 * Retrieve the thread we're locked to.
	 * @return The only thread that is allowed to access us.
	 */
	public Thread getExpected() {
		return expected;
	}
	
	/**
	 * Retrieve the delegate object we are storing.
	 * @return Delegate object.
	 */
	public T getDelegate() {
		return delegate;
	}
	
	protected void verifyThread() {
		if (Thread.currentThread().getId() != expected.getId()) {
			throw IllegalThreadAccess.fromFormat(
					"%s tried to access object %s that is locked by %s.", 
					Thread.currentThread(), getDelegate().getClass(), expected);
		}
	}
}
