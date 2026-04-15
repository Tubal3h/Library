package it.dto;

import java.time.LocalDate;

/**
 * Classe che rappresenta un'edizione di un libro con i dati delle tabelle collegate.
 */
public class EditionDto {
    private int editionId;
    private int bookId;
    private String bookName;
    private int authorId;
    private String authorName;
    private String publisherName;
    private String categoryName;
    private LocalDate publicationDate;
    private String isbnCode;
    private int quantity;

        /**
     * Costruttore vuoto per EditionDto.
     */
    public EditionDto() {
    }

    /**
     * Costruttore completo per EditionDto.
     * 
     * @param editionId ID dell'edizione
     * @param bookId ID del libro
     * @param bookName Nome del libro
     * @param authorId ID dell'autore
     * @param authorName Nome dell'autore
     * @param publisherName Nome dell'editore
     * @param categoryName Nome della categoria
     * @param publicationDate Data di pubblicazione
     * @param isbnCode Codice ISBN
     * @param quantity Quantità di copie disponibili
     */
    public EditionDto(int editionId, int bookId, String bookName, int authorId, String authorName, String publisherName, String categoryName, LocalDate publicationDate, String isbnCode, int quantity) {
        this.editionId = editionId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorId = authorId;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.categoryName = categoryName;
        this.publicationDate = publicationDate;
        this.isbnCode = isbnCode;
        this.quantity = quantity;
    }

    /**
     * Ottiene l'ID dell'edizione.
     * 
     * @return ID dell'edizione
     */
    public int getEditionId() {
        return editionId;
    }

    /**
     * Imposta l'ID dell'edizione.
     * 
     * @param editionId ID dell'edizione
     */
    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }

    /**
     * Ottiene l'ID del libro.
     * 
     * @return ID del libro
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Imposta l'ID del libro.
     * 
     * @param bookId ID del libro
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Ottiene il nome del libro.
     * 
     * @return Nome del libro
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Imposta il nome del libro.
     * 
     * @param bookName Nome del libro
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * Ottiene l'ID dell'autore.
     * 
     * @return ID dell'autore
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Imposta l'ID dell'autore.
     * 
     * @param authorId ID dell'autore
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Ottiene il nome dell'autore.
     * 
     * @return Nome dell'autore
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Imposta il nome dell'autore.
     * 
     * @param authorName Nome dell'autore
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Ottiene il nome dell'editore.
     * 
     * @return Nome dell'editore
     */
    public String getPublisherName() {
        return publisherName;
    }

    /**
     * Imposta il nome dell'editore.
     * 
     * @param publisherName Nome dell'editore
     */
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    /**
     * Ottiene il nome della categoria.
     * 
     * @return Nome della categoria
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Imposta il nome della categoria.
     * 
     * @param categoryName Nome della categoria
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Ottiene la data di pubblicazione.
     * 
     * @return Data di pubblicazione
     */
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    /**
     * Imposta la data di pubblicazione.
     * 
     * @param publicationDate Data di pubblicazione
     */
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Ottiene il codice ISBN.
     * 
     * @return Codice ISBN
     */
    public String getIsbnCode() {
        return isbnCode;
    }

    /**
     * Imposta il codice ISBN.
     * 
     * @param isbnCode Codice ISBN
     */
    public void setIsbnCode(String isbnCode) {
        this.isbnCode = isbnCode;
    }

    /**
     * @return Quantità di copie disponibili
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity Quantità di copie disponibili
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Converte l'oggetto EditionDto in una stringa.
     * 
     * @return Stringa che rappresenta l'oggetto EditionDto
     */
    @Override
    public String toString() {
        return "EditionDto [editionId=" + editionId + ", bookId=" + bookId + ", bookName=" + bookName + ", authorId="
                + authorId + ", authorName=" + authorName + ", publisherName=" + publisherName + ", categoryName="
                + categoryName + ", publicationDate=" + publicationDate + ", isbnCode=" + isbnCode + ", quantity=" + quantity + "]";
    }
}
