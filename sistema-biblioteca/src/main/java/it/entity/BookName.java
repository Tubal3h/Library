package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta il titolo di un libro nel sistema.
 */
public class BookName {
    private int bookNameId;
    private String title;

    /**
     * Costruttore di default.
     */
    public BookName() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param title Titolo del libro
     */
    public BookName(String title) {
        this.title = title;
    }

    /**
     * @return ID unico per il nome del libro
     */
    public int getBookNameId() {
        return bookNameId;
    }

    /**
     * @param bookNameId ID unico per il nome del libro
     */
    public void setBookNameId(int bookNameId) {
        this.bookNameId = bookNameId;
    }

    /**
     * @return Titolo del libro
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title Titolo del libro
     */
    public void setTitle(String title) {
        this.title = title;
    }
}


