package it.exception;

public class InsertBookNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8910464321309051L;
	private String message;
	
	public InsertBookNameException(String message) {
		super();
		this.message = message;
	}
	
	public String toString() {
		return "attenzione: " + message;
	}
}
