package it.dto;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

/**
 * Data Transfer Object per la gestione dei prestiti (noleggi).
 */
public class RentDto {
    private int rentId;
    private int userId;
    private int bookId;
    private BookDto book;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;
    
    /**
     * @return ID del noleggio
     */
    public int getRentId() {
        return rentId;
    }

    /**
     * @param rentId ID del noleggio
     */
    public void setRentId(int rentId) {
        this.rentId = rentId;
    }
    
    /**
     * @return ID dell'utente
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId ID dell'utente
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /**
     * @return ID del libro
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * @param bookId ID del libro
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    /**
     * @return DTO del libro associato al noleggio
     */
    public BookDto getBook() {
        return book;
    }

    /**
     * @param book DTO del libro associato al noleggio
     */
    public void setBook(BookDto book) {
        this.book = book;
    }
    
    /**
     * @return Data di inizio noleggio
     */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /**
     * @param rentalDate Data di inizio noleggio
     */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    /**
     * @return Data di scadenza noleggio
     */
    public LocalDate getRentalExpired() {
        return rentalExpired;
    }

    /**
     * @param rentalExpired Data di scadenza noleggio
     */
    public void setRentalExpired(LocalDate rentalExpired) {
        this.rentalExpired = rentalExpired;
    }
    
    /**
     * @return Data di fine noleggio (restituzione)
     */
    public LocalDate getRentalEnded() {
        return rentalEnded;
    }

    /**
     * @param rentalEnded Data di fine noleggio
     */
    public void setRentalEnded(LocalDate rentalEnded) {
        this.rentalEnded = rentalEnded;
    }
}


