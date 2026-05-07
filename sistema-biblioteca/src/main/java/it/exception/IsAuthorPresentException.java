package it.exception;

public class IsAuthorPresentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5124256519143542478L;
	
	public IsAuthorPresentException(String message) {
		super(message);
	}
}
