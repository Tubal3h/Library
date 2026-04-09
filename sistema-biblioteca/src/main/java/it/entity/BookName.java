package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta il titolo di un libro nel sistema.
 */
public class BookName {
    private int booksNameId;
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
    public int getBooksNameId() {
        return booksNameId;
    }

    /**
     * @param booksNameId ID unico per il nome del libro
     */
    public void setBooksNameId(int booksNameId) {
        this.booksNameId = booksNameId;
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


