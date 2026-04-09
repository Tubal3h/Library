package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

public class Edition {
    private int edition_id;
    private int book_name_id;
    private int author_id;
    private int publisher_id;
    private LocalDate publishing_date;
    private String isbn;

    public Edition() {
    }
    public Edition(int book_name_id, int author_id, int publisher_id, LocalDate publishing_date) {
        this.book_name_id = book_name_id;
        this.author_id = author_id;
        this.publisher_id = publisher_id;
        this.publishing_date = publishing_date;
    }

    /**
     * @return ID dell'edizione
     */
    public int getEditionId() {
        return edition_id;
    }
    public void setEditionId(int edition_id) {
        this.edition_id = edition_id;
    }
    /**
     * @return ID del nome del libro
     */
    public int getBookNameId() {
        return book_name_id;
    }
    public void setBookNameId(int book_name_id) {
        this.book_name_id = book_name_id;
    }
    /**
     * @return ID dell'autore
     */
    public int getAuthorId() {
        return author_id;
    }
    public void setAuthorId(int author_id) {
        this.author_id = author_id;
    }
    /**
     * @return ID dell'editore
     */
    public int getPublisherId() {
        return publisher_id;
    }
    public void setPublisherId(int publisher_id) {
        this.publisher_id = publisher_id;
    }
    /**
     * @return Data di pubblicazione
     */
    public LocalDate getPublishingDate() {
        return publishing_date;
    }
    public void setPublishingDate(LocalDate publishing_date) {
        this.publishing_date = publishing_date;
    }
    /**
     * @return Codice ISBN
     */
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    @Override
    public String toString() {
        return "Edition [editionId=" + edition_id + ", bookNameId=" + book_name_id + ", authorId=" + author_id + ", publisherId=" + publisher_id + ", publishingDate=" + publishing_date + "]";
    }
}

