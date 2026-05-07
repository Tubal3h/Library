package it.exception.service;

public class BookServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4781903526887039205L;
	
	public BookServiceException(String message) {
		super(message);
	}
}
