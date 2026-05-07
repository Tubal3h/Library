package it.exception;

public class SelectAllAuthorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2717400085510809450L;
	
	public SelectAllAuthorException(int id) {
		super("l'autore con questo id non e' stato trovato: " + id);
	}
	
	public SelectAllAuthorException(String message) {
		super(message);
	}
}
