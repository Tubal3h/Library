package it.dto.response;

import java.time.LocalDate;

public class BookUserDto {
    private int editionId;
    private int bookId;
    private String title;
    private String authorName;
    private String authorLastName;
    private String publisherName;
    private LocalDate publishingDate;
    private String isbnCode;
    private String categoryName;
    private String status;
	
    public BookUserDto(int editionId, int bookId, String title, String authorName, String authorLastName,
			String publisherName, LocalDate publishingDate, String isbnCode, String categoryName,
			String status) {

		this.editionId = editionId;
		this.bookId = bookId;
		this.title = title;
		this.authorName = authorName;
		this.authorLastName = authorLastName;
		this.publisherName = publisherName;
		this.publishingDate = publishingDate;
		this.isbnCode = isbnCode;
		this.categoryName = categoryName;
		this.status = status;
	}
    
    public BookUserDto() {
    	
    }

	public int getEditionId() {
		return editionId;
	}

	public void setEditionId(int editionId) {
		this.editionId = editionId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public LocalDate getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(LocalDate publishingDate) {
		this.publishingDate = publishingDate;
	}

	public String getIsbnCode() {
		return isbnCode;
	}

	public void setIsbnCode(String isbnCode) {
		this.isbnCode = isbnCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}

