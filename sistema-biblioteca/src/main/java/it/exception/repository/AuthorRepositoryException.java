package it.exception.repository;

public class AuthorRepositoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6426337784962172827L;
	
	public AuthorRepositoryException(String message) {
		super(message);
	}
	
	public AuthorRepositoryException(int id) {
		super("Autore con il seguente id " + id + " non trovato.");
	}
}
