package it.dto;

public class BookDto {
    private int bookId;
    private int editionId;
    private String status;

    public BookDto() {
    }

    public BookDto(int bookId, int editionId, String status) {
        this.bookId = bookId;
        this.editionId = editionId;
        this.status = status;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getEditionId() {
        return editionId;
    }

    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookDto [bookId=" + bookId + ", editionId=" + editionId + ", status=" + status + "]";
    }
}
