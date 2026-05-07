package it.exception.repository;

/**
 * Eccezione personalizzata per gestire l'errore: InsertCategoryException.
 */
public class InsertCategoryException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9178236508609228462L;
	
	public InsertCategoryException(String message) {
		super(message);
	}
}
