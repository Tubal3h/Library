package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

public class Author {
    private int authorId;
    private String authorName;
    private String authorLastName;
    public Author() {
    }
    public Author(String authorName, String authorLastName) {
        this.authorName = authorName;
        this.authorLastName = authorLastName;
    }
    
    /**
     * @return ID dell'autore
     */
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * @return Nome dell'autore
     */
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    /**
     * @return Cognome dell'autore
     */
    public String getAuthorLastName() {
        return authorLastName;
    }   
    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }
    @Override
    public String toString() {
        return "Author [authorId=" + authorId + ", authorName=" + authorName + ", authorLastName="
                + authorLastName + "]";
    }
}

