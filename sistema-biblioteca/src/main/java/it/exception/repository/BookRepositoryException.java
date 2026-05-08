package it.exception.repository;

public class BookRepositoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -206332659879723797L;
	
	public BookRepositoryException(String message) {
		super(message);
	}
}
