package it.dto.response;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

/**
 * Data Transfer Object per la visualizzazione dei libri nel catalogo.
 */
public class BookHistoryDto {
    private int editionId;
    private int bookId;
    private String title;
    private String authorName;
    private String authorLastName;
    private String authorFullName;
    private String publisherName;
    private LocalDate publishingDate;
    private String isbnCode;
    private String categoryName;
    private String status;
    private String userName;
    private String userLastName;

    

    /**
     * Costruttore di default.
     */
    public BookHistoryDto() {
    }
    
    /**
     * Costruttore con parametri.
     */

    
    public BookHistoryDto(int editionId, int bookId, String title, String authorFullName, LocalDate publishingDate, String publisherName, String isbnCode, String categoryName, String status) {
        this.editionId = editionId;
        this.bookId = bookId;
        this.title = title;
        this.authorFullName = authorFullName;
        this.publishingDate = publishingDate;
        this.publisherName = publisherName;
        this.isbnCode = isbnCode;
        this.categoryName = categoryName;
        this.status = status;
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

    public BookHistoryDto(int bookId) {
    	this.bookId = bookId;
    }

    /**
     * @return ID dell'edizione
     */
    public int getEditionId() {
        return editionId;
    }

    /**
     * @param editionId ID dell'edizione
     */
    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }

    /**
     * @return ID del libro
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * @param bookId ID del libro
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * @return Titolo del libro
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title Titolo del libro
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return Nome completo dell'autore
     */
    public String getAuthorFullName() {
        return authorFullName;
    }

    /**
     * @param authorFullName Nome completo dell'autore
     */
    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }
    
    /**
     * @return Nome della casa editrice
     */
    public String getPublisherName() {
        return publisherName;
    }

    /**
     * @param publisherName Nome della casa editrice
     */
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    
    /**
     * @return Data di pubblicazione
     */
    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    /**
     * @param publishingDate Data di pubblicazione
     */
    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }
    
    /**
     * @return Codice ISBN
     */
    public String getIsbn() {
        return isbnCode;
    }

    /**
     * @param isbnCode Codice ISBN
     */
    public void setIsbn(String isbnCode) {
        this.isbnCode = isbnCode;
    }

    /**
     * @return Nome della categoria
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName Nome della categoria
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    /**
     * @return Stato del libro
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status Stato del libro
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

}


