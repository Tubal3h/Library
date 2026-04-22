package it.exception;

public class InsertBookException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7501292396164489267L;
	private String message;
	
	public InsertBookException(String message) {
		super();
		this.message = message;
	}
	
	public String toString() {
		return message;
	}
}
