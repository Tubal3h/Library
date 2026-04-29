package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

/**
 * Entità che rappresenta un record di noleggio nel sistema.
 */
public class RentalRecord {

    private int rentalId;
    private User user;
    private Book book;
    private LocalDate bookingDate;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;

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
    
    


	public int getRentalId() {
		return rentalId;
	}

	public RentalRecord(int rentalId, User user, Book book, LocalDate bookingDate, LocalDate rentalDate,
			LocalDate rentalExpired, LocalDate rentalEnded) {
		this.rentalId = rentalId;
		this.user = user;
		this.book = book;
		this.bookingDate = bookingDate;
		this.rentalDate = rentalDate;
		this.rentalExpired = rentalExpired;
		this.rentalEnded = rentalEnded;
	}

	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public LocalDate getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(LocalDate rentalDate) {
		this.rentalDate = rentalDate;
	}

	public LocalDate getRentalExpired() {
		return rentalExpired;
	}

	public void setRentalExpired(LocalDate rentalExpired) {
		this.rentalExpired = rentalExpired;
	}

	public LocalDate getRentalEnded() {
		return rentalEnded;
	}

	public void setRentalEnded(LocalDate rentalEnded) {
		this.rentalEnded = rentalEnded;
	}


}

