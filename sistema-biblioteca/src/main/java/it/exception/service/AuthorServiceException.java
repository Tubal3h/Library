package it.exception.service;

public class AuthorServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9101367282067764691L;
	
	public AuthorServiceException(String message) {
		super(message);
	}
}
