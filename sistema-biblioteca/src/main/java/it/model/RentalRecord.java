package it.model;

import java.time.LocalDate;

public class RentalRecord {

    private int rentalRecordId;
    private int userId;
    private int bookId;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;

    public RentalRecord() {
    }

    public RentalRecord(int rentalRecordId, int userId, int bookId,
        LocalDate rentalDate, LocalDate rentalExpired, LocalDate rentalEnded) {
        this.rentalRecordId = rentalRecordId;
        this.userId = userId;
        this.bookId = bookId;
        this.rentalDate = rentalDate;
        this.rentalExpired = rentalExpired;
        this.rentalEnded = rentalEnded;
    }

    public int getRentalRecordId() {
        return rentalRecordId;
    }

    public void setRentalRecordId(int rentalRecordId) {
        this.rentalRecordId = rentalRecordId;
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