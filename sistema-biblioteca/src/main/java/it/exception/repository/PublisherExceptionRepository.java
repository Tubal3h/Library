package it.exception.repository;

public class PublisherExceptionRepository extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7190768277252310617L;

	public PublisherExceptionRepository(String message) {
		super(message);
	}
}
