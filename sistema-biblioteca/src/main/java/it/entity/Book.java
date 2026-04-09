package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta un libro nel sistema.
 */
public class Book {
    private int book_id;
    private int edition_id;
    private int category_id;
    private String status;

    /**
     * Costruttore di default.
     */
    public Book() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param edition_id ID dell'edizione
     * @param category_id ID della categoria
     * @param status Stato del libro
     */
    public Book(int edition_id, int category_id, String status) {
        this.edition_id = edition_id;
        this.category_id = category_id;
        this.status = status;
    }

    /**
     * @return ID del libro
     */
    public int getBookId() {
        return book_id;
    }

    /**
     * @param book_id ID del libro
     */
    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    /**
     * @return ID dell'edizione
     */
    public int getEditionId() {
        return edition_id;
    }

    /**
     * @param edition_id ID dell'edizione
     */
    public void setEditionId(int edition_id) {
        this.edition_id = edition_id;
    }

    /**
     * @return ID della categoria
     */
    public int getCategoryId() {
        return category_id;
    }

    /**
     * @param category_id ID della categoria
     */
    public void setCategoryId(int category_id) {
        this.category_id = category_id;
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
        return "Books [bookId=" + book_id + ", editionId=" + edition_id + ", categoryId=" + category_id + ", status=" + status + "]";
    }
}



