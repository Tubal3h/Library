package it.exception.repository;

public class BookNamesRepositoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6008271957061053051L;
	
	public BookNamesRepositoryException(String message) {
		super(message);
	}	
	
	public BookNamesRepositoryException(int id) {
		super("Titolo con il seguente id " + id + " non trovato.");
	}
}
