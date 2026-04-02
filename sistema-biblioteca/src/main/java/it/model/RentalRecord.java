package it.model;

import java.util.Date;
public class RentalRecord {
    private int rentalRecordId;
    private int userId;
    private int bookId;
    private Date rentalDate;
    private Date rentalExpired;
    private Date rentalEnded;

    public RentalRecord(int userId, int bookId, Date rentalDate, Date rentalExpired, Date rentalEnded) {
        this.userId = userId;
        this.bookId = bookId;
        this.rentalDate = rentalDate;
        this.rentalExpired = rentalExpired;
        this.rentalEnded = rentalEnded;
    }
    public int getRentalRecordId() {
        return rentalRecordId;
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
    public Date getRentalDate() {
        return rentalDate;
    }
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }
    public Date getRentalExpired() {
        return rentalExpired;
    }
    public void setRentalExpired(Date rentalExpired) {
        this.rentalExpired = rentalExpired;
    }
    public Date getRentalEnded() {
        return rentalEnded;
    }
    public void setRentalEnded(Date rentalEnded) {
        this.rentalEnded = rentalEnded;
    }

}
