package it.entity;

import java.time.LocalDate;

public class BookView {
    private int edition_id;
    private int book_id;
    private String book_name;
    private String author_full_name;
    private String publisher_name;
    private LocalDate publication_date;
    private String category_name;
    private String isbn_code;
    private String status;

    public BookView() {
    }

    public BookView(int edition_id, int book_id, String book_name, String author_full_name, String publisher_name, LocalDate publication_date, String category_name, String isbn_code, String status) {
        this.edition_id = edition_id;
        this.book_id = book_id;
        this.book_name = book_name;
        this.author_full_name = author_full_name;
        this.publisher_name = publisher_name;
        this.publication_date = publication_date;
        this.category_name = category_name;
        this.isbn_code = isbn_code;
        this.status = status;
    }

    public int getEditionId() {
        return edition_id;
    }

    public void setEditionId(int edition_id) {
        this.edition_id = edition_id;
    }

    public int getBookId() {
        return book_id;
    }

    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    public String getBookName() {
        return book_name;
    }

    public void setBookName(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthorFullName() {
        return author_full_name;
    }

    public void setAuthorFullName(String author_full_name) {
        this.author_full_name = author_full_name;
    }

    public String getPublisherName() {
        return publisher_name;
    }

    public void setPublisherName(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public LocalDate getPublicationDate() {
        return publication_date;
    }

    public void setPublicationDate(LocalDate publication_date) {
        this.publication_date = publication_date;
    }

    public String getCategoryName() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }

    public String getIsbnCode() {
        return isbn_code;
    }

    public void setIsbnCode(String isbn_code) {
        this.isbn_code = isbn_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookJoin [edition_id=" + edition_id + ", book_id=" + book_id + ", bookName=" + book_name + ", author_full_name=" + author_full_name + ", publisher_name=" + publisher_name + ", category_name=" + category_name + ", isbn_code=" + isbn_code + ", status=" + status + "]";
    }
}
