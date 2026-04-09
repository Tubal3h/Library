package it.dto;

import java.time.LocalDate;

public class BookCatalogDto {
    private int editionId;
    private int bookId;
    private String title;
    private String authorFullName;
    private String publisherName;
    private LocalDate publishingDate;
    private String isbnCode;
    private String categoryName;
    private String status;

    public BookCatalogDto() {}
    
    public BookCatalogDto(int editionId, int bookId, String title, String authorFullName, LocalDate publishingDate, String publisherName, String isbnCode, String categoryName, String status) {
        this.editionId = editionId;
        this.bookId = bookId;
        this.title = title;
        this.authorFullName = authorFullName;
        this.publishingDate = publishingDate;
        this.publisherName = publisherName;
        this.isbnCode = isbnCode;
        this.categoryName = categoryName;
        this.status = status;
    }
    public int getEditionId() { return editionId;}
    public void setEditionId(int editionId) { this.editionId = editionId;}

    public int getBookId() { return bookId;}
    public void setBookId(int bookId) { this.bookId = bookId;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getAuthorFullName() { return authorFullName;}
    public void setAuthorFullName(String authorFullName) { this.authorFullName = authorFullName;}
    
    public String getPublisherName() { return publisherName;}
    public void setPublisherName(String publisherName) { this.publisherName = publisherName;}
    
    public LocalDate getPublishingDate() { return publishingDate;}
    public void setPublishingDate(LocalDate publishingDate) { this.publishingDate = publishingDate;}
    
    public String getIsbnCode() { return isbnCode;}
    public void setIsbnCode(String isbnCode) { this.isbnCode = isbnCode;}

    public String getCategoryName() { return categoryName;}
    public void setCategoryName(String categoryName) { this.categoryName = categoryName;}
    
    public String getStatus() { return status;}
    public void setStatus(String status) { this.status = status;}
}
