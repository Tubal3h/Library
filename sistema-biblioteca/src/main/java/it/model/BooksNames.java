package it.model;

public class BooksNames {
    public int booksNameId;
    public String title;
    public BooksNames(String title) {
        this.title = title;
    }
    public int getBooksNameId() {
        return booksNameId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
