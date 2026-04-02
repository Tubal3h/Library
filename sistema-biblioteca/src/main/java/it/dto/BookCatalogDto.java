package it.dto;

public class BookCatalogDto {
    private int bookId;
    private String title;
    private String authorFullName;
    private String publisherName;
    private String publishingDate;
    private String isbnCode;
    private String categoryName;
    private String status;
    public int getBookId() { return bookId;}
    public void setBookId(int bookId) { this.bookId = bookId;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getAuthorFullName() { return authorFullName;}
    public void setAuthorFullName(String authorFullName) { this.authorFullName = authorFullName;}
    
    public String getPublisherName() { return publisherName;}
    public void setPublisherName(String publisherName) { this.publisherName = publisherName;}
    
    public String getPublishingDate() { return publishingDate;}
    public void setPublishingDate(String publishingDate) { this.publishingDate = publishingDate;}
    
    public String getIsbnCode() { return isbnCode;}
    public void setIsbnCode(String isbnCode) { this.isbnCode = isbnCode;}

    public String getCategoryName() { return categoryName;}
    public void setCategoryName(String categoryName) { this.categoryName = categoryName;}
    
    public String getStatus() { return status;}
    public void setStatus(String status) { this.status = status;}
}
