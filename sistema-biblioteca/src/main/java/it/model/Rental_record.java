package it.model;

import java.util.Date;
public class Rental_record {
    private int rental_record_id;
    private int user_id;
    private int book_id;
    private Date rental_date;
    private Date rental_expired;
    private Date rental_ended;

    public Rental_record(int user_id, int book_id, Date rental_date, Date rental_expired, Date rental_ended) {
        this.rental_record_id = rental_record_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.rental_date = rental_date;
        this.rental_expired = rental_expired;
        this.rental_ended = rental_ended;
    }
    public int getRental_record_id() {
        return rental_record_id;
    }

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
    public Date getRental_date() {
        return rental_date;
    }
    public void setRental_date(Date rental_date) {
        this.rental_date = rental_date;
    }
    public Date getRental_expired() {
        return rental_expired;
    }
    public void setRental_expired(Date rental_expired) {
        this.rental_expired = rental_expired;
    }
    public Date getRental_ended() {
        return rental_ended;
    }
    public void setRental_ended(Date rental_ended) {
        this.rental_ended = rental_ended;
    }

}
