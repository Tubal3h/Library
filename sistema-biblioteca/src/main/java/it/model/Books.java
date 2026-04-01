package it.model;

import java.util.Date;

public class Books {
    private int book_id;
    private int title_id;
    private int author_id;
    private int publisher_id;
    private Date publishing_date;
    private int isbn_id;
    private int category_id;
    private String status;

    public Books(int title_id, int author_id, int publisher_id, Date publishing_date, int isbn_id, int category_id, String status) {
        this.title_id = title_id;
        this.author_id = author_id;
        this.publisher_id = publisher_id;
        this.publishing_date = publishing_date;
        this.isbn_id = isbn_id;
        this.category_id = category_id;
        this.status = status;
    }
    public int getBook_id() {
        return book_id;
    }
    public int getTitle_id() {
        return title_id;
    }
    public void setTitle_id(int title_id) {
        this.title_id = title_id;
    }
    public int getAuthor_id() {
        return author_id;
    }
    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }
    public int getPublisher_id() {
        return publisher_id;
    }
    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }
    public Date getPublishing_date() {
        return publishing_date;
    }
    public void setPublishing_date(Date publishing_date) {
        this.publishing_date = publishing_date;
    }
    public int getIsbn_id() {
        return isbn_id;
    }
    public void setIsbn_id(int isbn_id) {
        this.isbn_id = isbn_id;
    }
    public int getCategory_id() {
        return category_id;
    }
    public void setCategory_id(int category_id) {
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
        return "Books [book_id=" + book_id + ", title_id=" + title_id + ", author_id=" + author_id
                + ", publisher_id=" + publisher_id + ", publishing_date=" + publishing_date + ", isbn_id=" + isbn_id
                + ", category_id=" + category_id + ", status=" + status + "]";
    }

}
