package it.dto;

import java.time.LocalDate;

/**
 * DTO per la visualizzazione dello storico dei noleggi (bookRecords).
 */
public class BookRecordsJoinDto {
    
    private int bookId;
    private String userName;
    private String userLastName;
    private int rentalId;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;

    public BookRecordsJoinDto() {}

    public BookRecordsJoinDto(int bookId, String userName, String userLastName, int rentalId, 
                              LocalDate rentalDate, LocalDate rentalExpired, LocalDate rentalEnded) {
        this.bookId = bookId;
        this.userName = userName;
        this.userLastName = userLastName;
        this.rentalId = rentalId;
        this.rentalDate = rentalDate;
        this.rentalExpired = rentalExpired;
        this.rentalEnded = rentalEnded;
    }

    // Getters and Setters
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserLastName() { return userLastName; }
    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }

    public int getRentalId() { return rentalId; }
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }

    public LocalDate getRentalDate() { return rentalDate; }
    public void setRentalDate(LocalDate rentalDate) { this.rentalDate = rentalDate; }

    public LocalDate getRentalExpired() { return rentalExpired; }
    public void setRentalExpired(LocalDate rentalExpired) { this.rentalExpired = rentalExpired; }

    public LocalDate getRentalEnded() { return rentalEnded; }
    public void setRentalEnded(LocalDate rentalEnded) { this.rentalEnded = rentalEnded; }
}
