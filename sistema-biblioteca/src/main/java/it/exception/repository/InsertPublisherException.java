package it.exception.Repository;

/**
 * Eccezione personalizzata per gestire l'errore: InsertPublisherException.
 */
public class InsertPublisherException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2006082225378102652L;
	
	public InsertPublisherException(String message) {
		super(message);
	}
}
