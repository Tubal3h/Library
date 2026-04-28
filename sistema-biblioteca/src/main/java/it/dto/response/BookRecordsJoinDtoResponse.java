package it.dto.response;

import java.time.LocalDate;

public class BookRecordsJoinDtoResponse {
    private int bookId;
    private int rentalId;
    private String userName;
    private String userLastName;
    private String bookTitle;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;
    private LocalDate bookingDate;
	
    public BookRecordsJoinDtoResponse(int bookId, int rentalId, String userName, String userLastName,
			LocalDate rentalDate, LocalDate rentalExpired, LocalDate rentalEnded) {
		this.bookId = bookId;
		this.rentalId = rentalId;
		this.userName = userName;
		this.userLastName = userLastName;
		this.rentalDate = rentalDate;
		this.rentalExpired = rentalExpired;
		this.rentalEnded = rentalEnded;
	}
	
	public BookRecordsJoinDtoResponse() {
		
	}
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getRentalId() {
		return rentalId;
	}
	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
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
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}
