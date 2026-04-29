package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta un libro nel sistema.
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
     * @param book_id ID del libro
     */
    public void setBookId(int book_id) {
        this.bookId = book_id;
    }

    /**
     * @return ID dell'edizione
     */
    public Edition getEditionId() {
        return edition;
    }

    /**
     * @param editionId ID dell'edizione
     */
    public void setEditionId(Edition editionId) {
        this.edition = editionId;
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



