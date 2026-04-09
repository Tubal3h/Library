package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta un autore nel sistema.
 */
public class Author {
    private int authorId;
    private String authorName;
    private String authorLastName;

    /**
     * Costruttore di default.
     */
    public Author() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param authorName Nome dell'autore
     * @param authorLastName Cognome dell'autore
     */
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

    /**
     * @param authorId ID dell'autore
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * @return Nome dell'autore
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName Nome dell'autore
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * @return Cognome dell'autore
     */
    public String getAuthorLastName() {
        return authorLastName;
    }   

    /**
     * @param authorLastName Cognome dell'autore
     */
    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    @Override
    public String toString() {
        return "Author [authorId=" + authorId + ", authorName=" + authorName + ", authorLastName="
                + authorLastName + "]";
    }
}


