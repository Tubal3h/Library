package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

/**
 * Entità che rappresenta un record di noleggio nel sistema.
 */
public class RentalRecord {

    private int rental_id;
    private int user_id;
    private int book_id;
    private LocalDate rental_date;
    private LocalDate rental_expired;
    private LocalDate rental_ended;

    /**
     * Costruttore di default.
     */
    public RentalRecord() {
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
    public RentalRecord(int rental_id, int user_id, int book_id,
        LocalDate rental_date, LocalDate rental_expired, LocalDate rental_ended) {
        this.rental_id = rental_id;
        this.user_id = user_id;
        this.book_id = book_id;
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
