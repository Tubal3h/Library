package it.exception;

/**
 * Eccezione personalizzata per gestire l'errore: HistoryNotFoundException.
 */
public class HistoryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1089049621319772519L;
	
	private String message;
	
	public HistoryNotFoundException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return message;
	}
}
