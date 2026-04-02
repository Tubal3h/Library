package it.model;

public class BookName {
    public int booksNameId;
    public String title;
    public BookName() {
    }
    public BookName(String title) {
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
