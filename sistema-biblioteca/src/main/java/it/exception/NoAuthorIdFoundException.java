package it.exception;

/**
 * Eccezione personalizzata per gestire l'errore: NoAuthorIdFoundException.
 */
public class NoAuthorIdFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 3706114236311657896L;
    private int id;
    
    public NoAuthorIdFoundException(int id) {
        super("Author with ID " + id + " not found");
        this.id = id;
    }

    public NoAuthorIdFoundException(String message) {
        super(message);
    }

    public int getId() {
        return id;
    }
}
