package it.dto;

/**
 * Oggetto di trasferimento dati per un libro.
 * Utilizzato per esporre le informazioni del libro e della sua edizione agli strati esterni.
 */
/**
 * Data Transfer Object per la gestione dei dati di BookDto.
 */
public class BookDto {
    private int bookId;
    private EditionDto editionDto;
    private String status;
    
    public BookDto() {
    }
    
    public BookDto(
        int bookId,
        EditionDto editionDto,
        String status
    ) {
        this.bookId = bookId;
        this.editionDto = editionDto;
        this.status = status;
    }
    
    public EditionDto getEditionDto() {
        return editionDto;
    }

    public void setEditionDto(EditionDto editionDto) {
        this.editionDto = editionDto;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "BookDto [bookId=" + bookId + ", editionDto=" + editionDto + ", status=" + status + "]";
    }    
}
