package it.exception;

public class InsertCategoryException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9178236508609228462L;
	private String message;
	
	public InsertCategoryException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return message;
	}

}
