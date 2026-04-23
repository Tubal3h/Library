package it.exception;

public class InsertBookServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 798540482838954814L;
	private final String message;
	
	public InsertBookServiceException(String message) {
		super();
		this.message = message;
	}
	
	public String toString() {
		return message;
	}
	
}
