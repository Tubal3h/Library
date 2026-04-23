package it.dto;

public class AuthorDto {
    private int authorId;
    private String authorName;
    private String authorLastName;

    /**
     * Costruttore di default per AuthorDto.
     */

    public AuthorDto() {
    }

    /**
     * Costruttore per AuthorDto.
     * 
     * @param authorId     ID dell'autore
     * @param authorName   Nome dell'autore
     * @param authorLastName Cognome dell'autore
     */

    public AuthorDto(int authorId, String authorName, String authorLastName) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorLastName = authorLastName;
    }
    
    public int getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getAuthorLastName() {
        return authorLastName;
    }
    
    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }
}
