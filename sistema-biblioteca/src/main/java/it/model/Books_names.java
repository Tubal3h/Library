package it.model;

public class Books_names {
    public int books_name_id;
    public String title;
    public Books_names(String title) {
        this.title = title;
    }
    public int getBooks_name_id() {
        return books_name_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
