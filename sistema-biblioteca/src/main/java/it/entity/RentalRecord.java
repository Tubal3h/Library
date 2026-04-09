package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

public class RentalRecord {

    private int rental_id;
    private int user_id;
    private int book_id;
    private LocalDate rental_date;
    private LocalDate rental_expired;
    private LocalDate rental_ended;

    public RentalRecord() {
    }

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

    public void setRentalId(int rental_id) {
        this.rental_id = rental_id;
    }

    /**
     * @return ID dell'utente
     */
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return ID del libro
     */
    public int getBookId() {
        return book_id;
    }

    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    /**
     * @return Data di inizio noleggio
     */
    public LocalDate getRentalDate() {
        return rental_date;
    }

    public void setRentalDate(LocalDate rental_date) {
        this.rental_date = rental_date;
    }

    /**
     * @return Data di scadenza noleggio
     */
    public LocalDate getRentalExpired() {
        return rental_expired;
    }

    public void setRentalExpired(LocalDate rental_expired) {
        this.rental_expired = rental_expired;
    }

    /**
     * @return Data di fine noleggio (effettiva restituzione)
     */
    public LocalDate getRentalEnded() {
        return rental_ended;
    }

    public void setRentalEnded(LocalDate rental_ended) {
        this.rental_ended = rental_ended;
    }
}