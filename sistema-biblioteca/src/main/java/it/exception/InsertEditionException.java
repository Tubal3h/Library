package it.exception;

public class InsertEditionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5940959917778592805L;
	private String message;
	
	public InsertEditionException(String message) {
		super();
		this.message = message;
	}
	
	public String toString() {
		return message;
	}
}
