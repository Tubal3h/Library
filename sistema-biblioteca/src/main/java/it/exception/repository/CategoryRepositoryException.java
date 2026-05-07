package it.exception.repository;

public class CategoryRepositoryException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -352379333268324488L;
	
	public CategoryRepositoryException(String message) {
		super(message);
	}
}
