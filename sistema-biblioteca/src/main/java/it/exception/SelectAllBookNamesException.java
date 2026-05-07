package it.exception;

public class SelectAllBookNamesException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3677720304407829977L;
	
	public SelectAllBookNamesException(int id) {
		super("titolo non trovato con id: " + id);
	}
	
	public SelectAllBookNamesException(String message) {
		super(message);
	}

}
