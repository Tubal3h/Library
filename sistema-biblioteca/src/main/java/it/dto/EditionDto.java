package it.dto;

import java.time.LocalDate;

/**
 * Classe che rappresenta un'edizione di un libro con i dati delle tabelle collegate.
 */
public class EditionDto {
    private int editionId;
    private AuthorDto authorDto;
    private BookNameDto bookNameDto;
    private CategoryDto categoryDto;
    private PublisherDto publisherDto;
    private LocalDate publishingDate;
    private String isbn;
    private int quantity;


    

    /**
     * Costruttore vuoto per EditionDto.
     */
    public EditionDto() {
    }
    
    /**
     * Costruttore completo per EditionJoinDto.
     * 
     * @param editionId ID dell'edizione
     * @param authorDto DTO dell'autore
     * @param bookNameDto DTO del libro
     * @param categoryDto DTO della categoria
     * @param publisherDto DTO dell'editore
     * @param publishingDate Data di pubblicazione
     * @param isbn Codice ISBN
     * @param quantity Quantità
     */
    public EditionDto(
        int editionId,
        AuthorDto authorDto,
        BookNameDto bookNameDto,
        CategoryDto categoryDto,
        PublisherDto publisherDto,
        LocalDate publishingDate,
        String isbn,
        int quantity
    ) {
        this.editionId = editionId;
        this.authorDto = authorDto;
        this.bookNameDto = bookNameDto;
        this.categoryDto = categoryDto;
        this.publisherDto = publisherDto;
        this.publishingDate = publishingDate;
        this.isbn = isbn;
        this.quantity = quantity;
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
     * @return DTO dell'autore
     */
    public AuthorDto getAuthorDto() {
        return authorDto;
    }
    
    /**
     * @param authorDto DTO dell'autore
     */
    public void setAuthorDto(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }
    
    /**
     * @return DTO del libro
     */
    public BookNameDto getBookNameDto() {
        return bookNameDto;
    }
    
    /**
     * @param bookNameDto DTO del libro
     */
    public void setBookNameDto(BookNameDto bookNameDto) {
        this.bookNameDto = bookNameDto;
    }
    
    /**
     * @return DTO della categoria
     */
    public CategoryDto getCategoryDto() {
        return categoryDto;
    }
    
    /**
     * @param categoryDto DTO della categoria
     */
    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }
    
    /**
     * @return DTO dell'editore
     */
    public PublisherDto getPublisherDto() {
        return publisherDto;
    }
    
    /**
     * @param publisherDto DTO dell'editore
     */
    public void setPublisherDto(PublisherDto publisherDto) {
        this.publisherDto = publisherDto;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "EditionJoinDto [editionId=" + editionId + ", authorDto=" + authorDto + ", bookNameDto=" + bookNameDto + ", categoryDto=" + categoryDto + ", publisherDto=" + publisherDto + ", publishingDate=" + publishingDate + ", isbn=" + isbn + ", quantity=" + quantity + "]";
    }
}
