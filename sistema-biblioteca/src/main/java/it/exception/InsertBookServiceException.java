package it.exception;

public class InsertBookServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 798540482838954814L;
	
	public InsertBookServiceException(String message) {
		super(message);
	}
}
