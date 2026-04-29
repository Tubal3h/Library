package it.entity;

import java.time.LocalDate;

/**
 * Classe che rappresenta un'edizione di un libro con i dati delle tabelle collegate.
 */
public class Edition {
    private int editionId;
    private Author author;
    private BookNames bookNames;
    private Category category;
    private Publisher publisher;
    private LocalDate publishingDate;
    private String isbn;
    
    //funzione colonna
    private int quantity;
    
    /**
     * Costruttore vuoto per EditionJoin.
     */
    public Edition() {
    }
    
    /**
     * Costruttore completo per EditionJoin.
     * 
     * @param editionId ID dell'edizione
     * @param author Autore
     * @param book Libro
     * @param bookName Nome del libro
     * @param category Categoria
     * @param publisher Editore
     * @param publishingDate Data di pubblicazione
     * @param isbn Codice ISBN
     * @param quantity Quantità di copie disponibili
     */
    public Edition(
        int editionId, 
        Author author,  
        BookNames bookNames, 
        Category category, 
        Publisher publisher, 
        LocalDate publishingDate, 
        String isbn,
        int quantity
    ) {
        this.editionId = editionId;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.publishingDate = publishingDate;
        this.isbn = isbn;
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
     * @param edition_id ID dell'edizione
     */
    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }
    
    /**
     * Ottiene il libro.
     * 
     * @return Libro
     */

	
    /**
     * Ottiene il nome del libro.
     * 
     * @return Nome del libro
     */
    public BookNames getBookNames() {
        return bookNames;
    }
    
    /**
     * Imposta il nome del libro.
     * 
     * @param book_name Nome del libro
     */
    public void setBookName(BookNames bookNames) {
        this.bookNames = bookNames;
    }
    
    /**
     * Ottiene l'autore.
     * 
     * @return Autore
     */
    public Author getAuthor() {
        return author;
    }
    
    /**
     * Imposta l'autore.
     * 
     * @param author Autore
     */
    public void setAuthor(Author author) {
        this.author = author;
    }
    
    /**
     * Ottiene l'editore.
     * 
     * @return Editore
     */
    public Publisher getPublisher() {
        return publisher;
    }
    
    /**
     * Imposta l'editore.
     * 
     * @param publisher Editore
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    
    /**
     * Ottiene la categoria.
     * 
     * @return Categoria
     */
    public Category getCategory() {
        return category;
    }
    
    /**
     * Imposta la categoria.
     * 
     * @param category Categoria
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    
    /**
     * Ottiene la data di pubblicazione.
     * 
     * @return Data di pubblicazione
     */
    public LocalDate getPublishingDate() {
        return publishingDate;
    }
    
    /**
     * Imposta la data di pubblicazione.
     * 
     * @param publishing_date Data di pubblicazione
     */
    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }
    
    /**
     * Ottiene il codice ISBN.
     * 
     * @return Codice ISBN
     */
    public String getIsbn() {
        return isbn;
    }
    
    /**
     * Imposta il codice ISBN.
     * 
     * @param isbn Codice ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Ottiene la quantità di copie disponibili.
     * 
     * @return Quantità di copie disponibili
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Imposta la quantità di copie disponibili.
     * 
     * @param quantity Quantità di copie disponibili
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Converte l'oggetto EditionJoin in una stringa.
     * 
     * @return Stringa che rappresenta l'oggetto EditionJoin
     */
    @Override
    public String toString() {
        return "Edition [editionId=" + editionId + ", bookName=" + bookNames + ", author=" + author
                + ", publisher=" + publisher + ", category=" + category + ", publishingDate="
                + publishingDate + ", isbn=" + isbn + ", quantity=" + quantity + "]";
    }
}
