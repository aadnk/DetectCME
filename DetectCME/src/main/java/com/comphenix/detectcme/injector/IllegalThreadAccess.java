package com.comphenix.detectcme.injector;

public class IllegalThreadAccess extends RuntimeException {
	/**
	 * Generated by Eclipse.
	 */
	private static final long serialVersionUID = 8526046409412299246L;

	public IllegalThreadAccess() {
		super();
	}
	
	public IllegalThreadAccess(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalThreadAccess(String message) {
		super(message);
	}

	public IllegalThreadAccess(Throwable cause) {
		super(cause);
	}
	
	public static IllegalThreadAccess fromFormat(String message, Object... params) {
		return new IllegalThreadAccess(String.format(message, params));
	}
}