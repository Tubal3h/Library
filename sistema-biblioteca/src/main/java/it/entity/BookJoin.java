package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

/**
 * Entità che rappresenta la vista aggregata di un libro nel sistema,
 * ottenuta tramite JOIN tra le tabelle books, edition, books_names, author, publisher e category.
 */
public class BookJoin {

    private int edition_id;
    private int book_id;
    private String book_name;
    private String author_full_name;
    private String publisher_name;
    private LocalDate publication_date;
    private String category_name;
    private String isbn_code;
    private String status;
    private String userName;
    private String userLastName;


    /**
     * Costruttore di default.
     */
    public BookJoin() {
    }

    /**
     * Costruttore con parametri completi.
     *
     * @param edition_id       ID dell'edizione
     * @param book_id          ID del libro fisico
     * @param book_name        Titolo del libro
     * @param author_full_name Nome completo dell'autore
     * @param publisher_name   Nome della casa editrice
     * @param publication_date Data di pubblicazione
     * @param category_name    Nome della categoria
     * @param isbn_code        Codice ISBN
     * @param status           Stato del libro (es. disponibilita, in prestito)
     */
    public BookJoin(int edition_id, int book_id, String book_name, String author_full_name,
            String publisher_name, LocalDate publication_date, String category_name,
            String isbn_code, String status, String userName, String userLastName) {
        this.edition_id = edition_id;
        this.book_id = book_id;
        this.book_name = book_name;
        this.author_full_name = author_full_name;
        this.publisher_name = publisher_name;
        this.publication_date = publication_date;
        this.category_name = category_name;
        this.isbn_code = isbn_code;
        this.status = status;
        this.userName = userName;
        this.userLastName = userLastName;
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
     * @return ID del libro fisico
     */
    public int getBookId() {
        return book_id;
    }

    /**
     * @param book_id ID del libro fisico
     */
    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    /**
     * @return Titolo del libro
     */
    public String getBookName() {
        return book_name;
    }

    /**
     * @param book_name Titolo del libro
     */
    public void setBookName(String book_name) {
        this.book_name = book_name;
    }

    /**
     * @return Nome completo dell'autore
     */
    public String getAuthorFullName() {
        return author_full_name;
    }

    /**
     * @param author_full_name Nome completo dell'autore
     */
    public void setAuthorFullName(String author_full_name) {
        this.author_full_name = author_full_name;
    }

    /**
     * @return Nome della casa editrice
     */
    public String getPublisherName() {
        return publisher_name;
    }

    /**
     * @param publisher_name Nome della casa editrice
     */
    public void setPublisherName(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    /**
     * @return Data di pubblicazione dell'edizione
     */
    public LocalDate getPublicationDate() {
        return publication_date;
    }

    /**
     * @param publication_date Data di pubblicazione
     */
    public void setPublicationDate(LocalDate publication_date) {
        this.publication_date = publication_date;
    }

    /**
     * @return Nome della categoria
     */
    public String getCategoryName() {
        return category_name;
    }

    /**
     * @param category_name Nome della categoria
     */
    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }

    /**
     * @return Codice ISBN dell'edizione
     */
    public String getIsbnCode() {
        return isbn_code;
    }

    /**
     * @param isbn_code Codice ISBN
     */
    public void setIsbn(String isbn_code) {
        this.isbn_code = isbn_code;
    }

    /**
     * @return Stato del libro (es. disponibilita, in prestito)
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }


    @Override
    public String toString() {
        return "BookJoin [edition_id=" + edition_id + ", book_id=" + book_id + ", bookName=" + book_name
                + ", author_full_name=" + author_full_name + ", publisher_name=" + publisher_name
                + ", category_name=" + category_name + ", isbn_code=" + isbn_code + ", status=" + status + "]";
    }
}
