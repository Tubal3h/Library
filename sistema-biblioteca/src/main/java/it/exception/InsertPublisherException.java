package it.exception;

public class InsertPublisherException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2006082225378102652L;
	private String message;
	
	public InsertPublisherException(String message) {
		super();
		this.message = message;
	}
	
	public String toString() {
		return message;
	}
}
