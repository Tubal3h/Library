package it.entity;

import java.time.LocalDate;

/**
 * Classe che rappresenta un'edizione di un libro con i dati delle tabelle collegate.
 */
public class EditionJoin {
    private int edition_id;
    private int book_id;
    private String book_name;
    private String author_name;
    private String publisher_name;
    private String category_name;
    private LocalDate publishing_date;
    private String isbn;
    private int quantity;
    
    /**
     * Costruttore vuoto per EditionJoin.
     */
    public EditionJoin() {
    }
    
    /**
     * Costruttore completo per EditionJoin.
     * 
     * @param edition_id ID dell'edizione
     * @param book_id ID del libro
     * @param book_name Nome del libro
     * @param author_name Nome dell'autore
     * @param publisher_name Nome dell'editore
     * @param category_name Nome della categoria
     * @param publishing_date Data di pubblicazione
     * @param isbn Codice ISBN
     * @param quantity Quantità di copie disponibili
     */
    public EditionJoin(int edition_id, int book_id, String book_name, String author_name, String publisher_name, String category_name, LocalDate publishing_date, String isbn, int quantity) {
        this.edition_id = edition_id;
        this.book_id = book_id;
        this.book_name = book_name;
        this.author_name = author_name;
        this.publisher_name = publisher_name;
        this.category_name = category_name;
        this.publishing_date = publishing_date;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    
    /**
     * Ottiene l'ID dell'edizione.
     * 
     * @return ID dell'edizione
     */
    public int getEditionId() {
        return edition_id;
    }
    
    /**
     * Imposta l'ID dell'edizione.
     * 
     * @param edition_id ID dell'edizione
     */
    public void setEditionId(int edition_id) {
        this.edition_id = edition_id;
    }
    
    /**
     * Ottiene l'ID del libro.
     * 
     * @return ID del libro
     */
    public int getBookId() {
        return book_id;
    }
    
    /**
     * Imposta l'ID del libro.
     * 
     * @param book_id ID del libro
     */
    public void setBookId(int book_id) {
        this.book_id = book_id;
    }
    
    /**
     * Ottiene il nome del libro.
     * 
     * @return Nome del libro
     */
    public String getBookName() {
        return book_name;
    }
    
    /**
     * Imposta il nome del libro.
     * 
     * @param book_name Nome del libro
     */
    public void setBookName(String book_name) {
        this.book_name = book_name;
    }
    
    /**
     * Ottiene il nome dell'autore.
     * 
     * @return Nome dell'autore
     */
    public String getAuthor() {
        return author_name;
    }
    
    /**
     * Imposta il nome dell'autore.
     * 
     * @param author_name Nome dell'autore
     */
    public void setAuthor(String author_name) {
        this.author_name = author_name;
    }
    
    /**
     * Ottiene il nome dell'editore.
     * 
     * @return Nome dell'editore
     */
    public String getPublisher() {
        return publisher_name;
    }
    
    /**
     * Imposta il nome dell'editore.
     * 
     * @param publisher_name Nome dell'editore
     */
    public void setPublisher(String publisher_name) {
        this.publisher_name = publisher_name;
    }
    
    /**
     * Ottiene il nome della categoria.
     * 
     * @return Nome della categoria
     */
    public String getCategory() {
        return category_name;
    }
    
    /**
     * Imposta il nome della categoria.
     * 
     * @param category_name Nome della categoria
     */
    public void setCategory(String category_name) {
        this.category_name = category_name;
    }
    
    /**
     * Ottiene la data di pubblicazione.
     * 
     * @return Data di pubblicazione
     */
    public LocalDate getPublishingDate() {
        return publishing_date;
    }
    
    /**
     * Imposta la data di pubblicazione.
     * 
     * @param publishing_date Data di pubblicazione
     */
    public void setPublishingDate(LocalDate publishing_date) {
        this.publishing_date = publishing_date;
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
        return "EditionJoin [editionId=" + edition_id + ", bookName=" + book_name + ", author=" + author_name
                + ", publisher=" + publisher_name + ", category=" + category_name + ", publishingDate="
                + publishing_date + ", isbn=" + isbn + ", quantity=" + quantity + "]";
    }
}
