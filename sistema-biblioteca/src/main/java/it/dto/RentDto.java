package it.dto;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

public class RentDto {
    private int rentId;
    private int userId;
    private int bookId;
    private BookCatalogDto book;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;
    
    /**
     * @return ID del noleggio
     */
    public int getRentId() { return rentId;}
    public void setRentId(int rentId) { this.rentId = rentId;}
    
    /**
     * @return ID dell'utente
     */
    public int getUserId() { return userId;}
    public void setUserId(int userId) { this.userId = userId;}
    
    /**
     * @return ID del libro
     */
    public int getBookId() { return bookId;}
    public void setBookId(int bookId) { this.bookId = bookId;}
    
    /**
     * @return DTO del libro associato al noleggio
     */
    public BookCatalogDto getBook() { return book;}
    public void setBook(BookCatalogDto book) { this.book = book;}
    
    /**
     * @return Data di inizio noleggio
     */
    public LocalDate getRentalDate() { return rentalDate;}
    public void setRentalDate(LocalDate rentalDate) { this.rentalDate = rentalDate;}
    
    /**
     * @return Data di scadenza noleggio
     */
    public LocalDate getRentalExpired() { return rentalExpired;}
    public void setRentalExpired(LocalDate rentalExpired) { this.rentalExpired = rentalExpired;}
    
    /**
     * @return Data di fine noleggio (restituzione)
     */
    public LocalDate getRentalEnded() { return rentalEnded;}
    public void setRentalEnded(LocalDate rentalEnded) { this.rentalEnded = rentalEnded;}
}

