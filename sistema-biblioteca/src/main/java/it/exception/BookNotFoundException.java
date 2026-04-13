package it.exception;

/* -------------------------------------------------------------------------- */
/*                                  EXCEPTION                                 */
/* -------------------------------------------------------------------------- */

/**
 * Eccezione lanciata quando un libro non viene trovato nel sistema.
 * Estende {@link RuntimeException} per essere non controllata.
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Costruisce l'eccezione con il messaggio descrittivo specificato.
     *
     * @param message Messaggio descrittivo dell'errore
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}