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
    private int userId;
    private int bookId;
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

	public RentalRecord(int rentalId, int userId, int bookId, LocalDate bookingDate, LocalDate rentalDate,
			LocalDate rentalExpired, LocalDate rentalEnded) {
		this.rentalId = rentalId;
		this.userId = userId;
		this.bookId = bookId;
		this.bookingDate = bookingDate;
		this.rentalDate = rentalDate;
		this.rentalExpired = rentalExpired;
		this.rentalEnded = rentalEnded;
	}

	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
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

