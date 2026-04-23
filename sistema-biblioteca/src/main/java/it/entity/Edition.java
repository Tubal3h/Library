package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

/**
 * Entità che rappresenta un'edizione di un libro nel sistema.
 */
public class Edition {
    private int editionId;
    private int bookNameId;
    private int authorId;
    private int publisherId;
    private int categoryId;
    private LocalDate publishingDate;
    private String isbn;

    /**
     * Costruttore di default.
     */
    public Edition() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param bookNameId ID del nome del libro
     * @param authorId ID dell'autore
     * @param publisherId ID dell'editore
     * @param categoryId ID della categoria
     * @param publishingDate Data di pubblicazione
     */
    public Edition(int bookNameId, int authorId, int publisherId, int categoryId, LocalDate publishingDate) {
        this.bookNameId = bookNameId;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.categoryId = categoryId;
        this.publishingDate = publishingDate;
    }

    /**
     * @return ID dell'edizione
     */
    public int getEditionId() {
        return editionId;
    }

    /**
     * @param editionId ID dell'edizione
     */
    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }

    /**
     * @return ID del nome del libro
     */
    public int getBookNameId() {
        return bookNameId;
    }

    /**
     * @param bookNameId ID del nome del libro
     */
    public void setBookNameId(int bookNameId) {
        this.bookNameId = bookNameId;
    }

    /**
     * @return ID dell'autore
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * @param authorId ID dell'autore
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * @return ID dell'editore
     */
    public int getPublisherId() {
        return publisherId;
    }

    /**
     * @param publisherId ID dell'editore
     */
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    /**
     * @return ID della categoria
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId ID della categoria
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return Data di pubblicazione
     */
    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    /**
     * @param publishingDate Data di pubblicazione
     */
    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    /**
     * @return Codice ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn Codice ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Edition [editionId=" + editionId + ", bookNameId=" + bookNameId + ", authorId=" + authorId + ", publisherId=" + publisherId + ", publishingDate=" + publishingDate + "]";
    }
}


