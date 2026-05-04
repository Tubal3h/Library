package it.exception;

/**
 * Eccezione personalizzata per gestire l'errore: InsertEditionException.
 */
public class InsertEditionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5940959917778592805L;
	
	public InsertEditionException(String message) {
		super(message);
	}
}
