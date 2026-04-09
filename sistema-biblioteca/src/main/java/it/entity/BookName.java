package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

public class BookName {
    public int booksNameId;
    public String title;
    public BookName() {
    }
    public BookName(String title) {
        this.title = title;
    }
    /**
     * @return ID unico per il nome del libro
     */
    public int getBooksNameId() {
        return booksNameId;
    }
    public void setBooksNameId(int booksNameId) {
        this.booksNameId = booksNameId;
    }
    /**
     * @return Titolo del libro
     */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}

