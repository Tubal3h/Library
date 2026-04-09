package it.model.dto;

import java.time.LocalDate;

public class RentDto {
    private int rentId;
    private int userId;
    private int bookId;
    private BookCatalogDto book;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;
    public int getRentId() { return rentId;}
    public void setRentId(int rentId) { this.rentId = rentId;}
    public int getUserId() { return userId;}
    public void setUserId(int userId) { this.userId = userId;}
    public int getBookId() { return bookId;}
    public void setBookId(int bookId) { this.bookId = bookId;}
    public BookCatalogDto getBook() { return book;}
    public void setBook(BookCatalogDto book) { this.book = book;}
    public LocalDate getRentalDate() { return rentalDate;}
    public void setRentalDate(LocalDate rentalDate) { this.rentalDate = rentalDate;}
    public LocalDate getRentalExpired() { return rentalExpired;}
    public void setRentalExpired(LocalDate rentalExpired) { this.rentalExpired = rentalExpired;}
    public LocalDate getRentalEnded() { return rentalEnded;}
    public void setRentalEnded(LocalDate rentalEnded) { this.rentalEnded = rentalEnded;}
}
