package it.dto;

import java.time.LocalDate;

public class EditionDto {
    private int editionId;
    private int bookNameId;
    private int authorId;
    private int publisherId;
    private int categoryId;
    private LocalDate publishingDate;
    private String isbn;

    public EditionDto() {
    }

    public EditionDto(int editionId, int bookNameId, int authorId, int publisherId, int categoryId, LocalDate publishingDate, String isbn) {
        this.editionId = editionId;
        this.bookNameId = bookNameId;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.categoryId = categoryId;
        this.publishingDate = publishingDate;
        this.isbn = isbn.toLowerCase();
    }

    public int getEditionId() {
        return editionId;
    }

    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }

    public int getBookNameId() {
        return bookNameId;
    }

    public void setBookNameId(int bookNameId) {
        this.bookNameId = bookNameId;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getIsbn() {
        return isbn.toLowerCase();
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn.toLowerCase();
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }
}
