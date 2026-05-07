package it.exception.service;

/**
 * Eccezione personalizzata per gestire l'errore: NoDeleteUserServiceException.
 */
public class NoDeleteUserServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8322861925351154757L;
	public NoDeleteUserServiceException(String message) {
		super(message);
	}
}
