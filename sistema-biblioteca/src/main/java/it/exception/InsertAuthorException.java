package it.exception;

/**
 * Eccezione personalizzata per gestire l'errore: InsertAuthorException.
 */
public class InsertAuthorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1259183957238528186L;
	
	public InsertAuthorException(String message) {
		super(message);
	}

}
