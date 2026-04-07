package it.model;

import java.util.Date;

public class Book {
    private int bookId;
    private int titleId;
    private int authorId;
    private int publisherId;
    private Date publishingDate;
    private int isbnId;
    private int categoryId;
    private String status;

    public Book() {

    }
    public Book(int titleId, int authorId, int publisherId, Date publishingDate, int isbnId, int categoryId, String status) {
        this.titleId = titleId;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.publishingDate = publishingDate;
        this.isbnId = isbnId;
        this.categoryId = categoryId;
        this.status = status;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getTitleId() {
        return titleId;
    }
    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }
    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public int getPublisherId() {
        return publisherId;
    }
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }
    public Date getPublishingDate() {
        return publishingDate;
    }
    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }
    public int getIsbnId() {
        return isbnId;
    }
    public void setIsbnId(int isbnId) {
        this.isbnId = isbnId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Books [bookId=" + bookId + ", titleId=" + titleId + ", authorId=" + authorId
                + ", publisherId=" + publisherId + ", publishingDate=" + publishingDate + ", isbnId=" + isbnId
                + ", categoryId=" + categoryId + ", status=" + status + "]";
    }

}
