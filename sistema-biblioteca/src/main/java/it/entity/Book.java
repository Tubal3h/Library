package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Rappresenta una singola copia fisica di un libro (esemplare) nel sistema.
 * Ogni libro è associato a una specifica edizione e ha uno stato (es. disponibile, prestato).
 */
public class Book {
    private int bookId;
    private Edition edition;
    private String status;

    /**
     * Costruttore di default.
     */
    public Book() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param edition ID dell'edizione
     * @param category_id ID della categoria
     * @param status Stato del libro
     */
    public Book(Edition edition, String status) {
        this.edition = edition;
        this.status = status;
    }

    /**
     * @return ID del libro
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * @param bookId ID del libro
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * @return ID dell'edizione
     */
    public Edition getEdition() {
        return edition;
    }

    /**
     * @param edition ID dell'edizione
     */

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    /**
     * @return Stato del libro (es. Disponibile, Noleggiato)
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status Stato del libro
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Books [bookId=" + bookId + ", editionId=" + edition + ", status=" + status + "]";
    }
}



