package it.model;

import java.util.Date;

public class Book {
    private int book_id;
    private int edition_id;
    private int category_id;
    private String status;

    public Book() {

    }
    public Book(int edition_id, int author_id, int publisher_id, Date publishing_date, int isbn_id, int category_id, String status) {
        this.edition_id = edition_id;
        this.category_id = category_id;
        this.status = status;
    }
    public int getBookId() {
        return book_id;
    }
    public void setBookId(int book_id) {
        this.book_id = book_id;
    }
    public int getEditionId() {
        return edition_id;
    }
    public void setEditionId(int edition_id) {
        this.edition_id = edition_id;
    }
    public int getCategoryId() {
        return category_id;
    }
    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Books [bookId=" + book_id + ", editionId=" + edition_id + ", categoryId=" + category_id + ", status=" + status + "]";
    }

}
