package it.dto;

public class AuthorDto {
    private int authorId;
    private String authorName;
    private String authorLastName;
    
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
