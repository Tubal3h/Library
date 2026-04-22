package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

/**
 * Entità che rappresenta un record di noleggio nel sistema.
 */
public class RentalRecordJoin {

    private int rentalId;
    private int bookId;
    private int userId;
    private String bookName;
    private String authorFullName;
    private String userName;
    private String userLastName;
    private String publisherName;
    private LocalDate publicationDate;
    private String categoryName;
    private String isbnCode;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;
	public RentalRecordJoin(int rentalId, int bookId, int userId, String bookName, String authorFullName,
			String userName, String userLastName, String publisherName, LocalDate publicationDate, String categoryName, String isbnCode,
			LocalDate rentalDate, LocalDate rentalExpired, LocalDate rentalEnded) {
		this.rentalId = rentalId;
		this.bookId = bookId;
		this.userId = userId;
		this.bookName = bookName;
		this.authorFullName = authorFullName;
		this.userName = userName;
		this.userLastName = userLastName;
		this.publisherName = publisherName;
		this.publicationDate = publicationDate;
		this.categoryName = categoryName;
		this.isbnCode = isbnCode;
		this.rentalDate = rentalDate;
		this.rentalExpired = rentalExpired;
		this.rentalEnded = rentalEnded;
	}
	public RentalRecordJoin() {
		
	}
	public int getRentalId() {
		return rentalId;
	}
	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthorFullName() {
		return authorFullName;
	}
	public void setAuthorFullName(String authorFullName) {
		this.authorFullName = authorFullName;
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
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public LocalDate getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getIsbnCode() {
		return isbnCode;
	}
	public void setIsbnCode(String isbnCode) {
		this.isbnCode = isbnCode;
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

