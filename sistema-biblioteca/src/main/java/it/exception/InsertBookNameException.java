package it.exception;

public class InsertBookNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8910464321309051L;
	
	public InsertBookNameException(String message) {
		super(message);
	}
}
