package it.exception.repository;

/* -------------------------------------------------------------------------- */
/*                                  EXCEPTION                                 */
/* -------------------------------------------------------------------------- */

/**
 * Eccezione lanciata quando un libro non viene trovato nel sistema.
 * Estende {@link RuntimeException} per essere non controllata.
 */
/**
 * Eccezione personalizzata per gestire l'errore: BookNotFoundException.
 */
public class BookNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7788434178582279353L;

	/**
     * Costruisce l'eccezione con il messaggio descrittivo specificato.
     *
     * @param message Messaggio descrittivo dell'errore
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}
