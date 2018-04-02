package com.williamhaw.friendmanagement.persistence;

/**
 * Exception to wrap all persistence exceptions
 * @author williamhaw
 *
 */
public class PersistenceException extends Exception {
	private static final long serialVersionUID = -3571905008834027782L;
	
	public PersistenceException(final String message) {
		super(message);
	}
	
	public PersistenceException(final Throwable cause) {
		super(cause);
	}
	
	public PersistenceException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
