package it.dto;

/**
 * Data Transfer Object per la gestione dei dati di BookNameDto.
 */
public class BookNameDto {
    private int bookNameId;
    private String title;
    
    public BookNameDto() {
    }

    public BookNameDto(int bookNameId, String title) {
        this.bookNameId = bookNameId;
        this.title = title;
    }

    public int getBookNameId() {
        return bookNameId;
    }

    public void setBookNameId(int bookNameId) {
        this.bookNameId = bookNameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
