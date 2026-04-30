package it.exception;

public class DeleteUserByIdException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1991069279758422955L;
	
	public DeleteUserByIdException(String message) {
		super(message);
	}
}
