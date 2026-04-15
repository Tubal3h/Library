package it.exception;

public class NoBookIdFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3706114236311657896L;
	private int id;
	
	public NoBookIdFoundException(int id) {
		this.id = id;
	}
	
	public String toString() {
		return "id non trovato: " + id;
	}
}
