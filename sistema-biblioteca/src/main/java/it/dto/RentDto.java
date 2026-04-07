package it.dto;

import java.sql.Date;

public class RentDto {
    private int userId;
    private int bookId;
    private Date rentalDate;
    private Date rentalExpired;
    private Date rentalEnded;
    public int getUserId() { return userId;}
    public void setUserId(int userId) { this.userId = userId;}
    public int getBookId() { return bookId;}
    public void setBookId(int bookId) { this.bookId = bookId;}
    public Date getRentalDate() { return rentalDate;}
    public void setRentalDate(Date rentalDate) { this.rentalDate = rentalDate;}
    public Date getRentalExpired() { return rentalExpired;}
    public void setRentalExpired(Date rentalExpired) { this.rentalExpired = rentalExpired;}
    public Date getRentalEnded() { return rentalEnded;}
    public void setRentalEnded(Date rentalEnded) { this.rentalEnded = rentalEnded;}
}
