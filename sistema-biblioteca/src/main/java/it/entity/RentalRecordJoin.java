package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

/**
 * Entità che rappresenta un record di noleggio nel sistema.
 */
public class RentalRecordJoin {

    private int rental_id;
    private int book_id;
    private int user_id;
    private String book_name;
    private String author_full_name;
    private String publisher_name;
    private LocalDate publication_date;
    private String category_name;
    private String isbn_code;
    private LocalDate rental_date;
    private LocalDate rental_expired;
    private LocalDate rental_ended;

    /**
     * Costruttore di default.
     */
    public RentalRecordJoin() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param rental_id ID del noleggio
     * @param user_id ID dell'utente
     * @param book_id ID del libro
     * @param rental_date Data di inizio noleggio
     * @param rental_expired Data di scadenza noleggio
     * @param rental_ended Data di fine noleggio
     */
    public RentalRecordJoin(int rental_id, int user_id, int book_id, String book_name, String author_full_name, String publisher_name, LocalDate publication_date, String category_name, String isbn_code, LocalDate rental_date, LocalDate rental_expired, LocalDate rental_ended) {
        this.rental_id = rental_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.book_name = book_name;
        this.author_full_name = author_full_name;
        this.publisher_name = publisher_name;
        this.publication_date = publication_date;
        this.category_name = category_name;
        this.isbn_code = isbn_code;
        this.rental_date = rental_date;
        this.rental_expired = rental_expired;
        this.rental_ended = rental_ended;
    }

    /**
     * @return ID del noleggio
     */
    public int getRentalId() {
        return rental_id;
    }

    /**
     * @param rental_id ID del noleggio
     */
    public void setRentalId(int rental_id) {
        this.rental_id = rental_id;
    }

    /**
     * @return ID dell'utente
     */
    public int getUserId() {
        return user_id;
    }

    /**
     * @param user_id ID dell'utente
     */
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return Nome del libro
     */
    public String getBookName() {
        return book_name;
    }

    /**
     * @param book_name Nome del libro
     */
    public void setBookName(String book_name) {
        this.book_name = book_name;
    }

    /**
     * @return Nome dell'autore
     */
    public String getAuthorFullName() {
        return author_full_name;
    }

    /**
     * @param author_full_name Nome dell'autore
     */
    public void setAuthorFullName(String author_full_name) {
        this.author_full_name = author_full_name;
    }

    /**
     * @return Nome dell'editore
     */
    public String getPublisherName() {
        return publisher_name;
    }

    /**
     * @param publisher_name Nome dell'editore
     */
    public void setPublisherName(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    /**
     * @return Data di pubblicazione
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
     * @return Codice ISBN
     */
    public String getIsbnCode() {
        return isbn_code;
    }

    /**
     * @param isbn_code Codice ISBN
     */
    public void setIsbnCode(String isbn_code) {
        this.isbn_code = isbn_code;
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
     * @return Data di inizio noleggio
     */
    public LocalDate getRentalDate() {
        return rental_date;
    }

    /**
     * @param rental_date Data di inizio noleggio
     */
    public void setRentalDate(LocalDate rental_date) {
        this.rental_date = rental_date;
    }

    /**
     * @return Data di scadenza noleggio
     */
    public LocalDate getRentalExpired() {
        return rental_expired;
    }

    /**
     * @param rental_expired Data di scadenza noleggio
     */
    public void setRentalExpired(LocalDate rental_expired) {
        this.rental_expired = rental_expired;
    }

    /**
     * @return Data di fine noleggio (effettiva restituzione)
     */
    public LocalDate getRentalEnded() {
        return rental_ended;
    }

    /**
     * @param rental_ended Data di fine noleggio
     */
    public void setRentalEnded(LocalDate rental_ended) {
        this.rental_ended = rental_ended;
    }
}

