package it.exception;

/**
 * Eccezione personalizzata per gestire l'errore: NoIsbnFoundException.
 */
public class NoIsbnFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4668432982567943998L;
	private String error;
	
	public NoIsbnFoundException(String error) {
		this.error = error;
	}
	
	public String ToString() {
		return error;
	}
}
