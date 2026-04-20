package it.service;

import java.time.LocalDate;
import java.util.ArrayList;


/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;



import org.springframework.stereotype.Service;

import it.exception.NoBookIdFoundException;

import it.dto.BookDto;
import it.entity.BookJoin;
import it.exception.BookNotFoundException;
import it.repository.BookNameRepository;
import it.repository.BookRepository;
import it.repository.EditionRepository;
import it.exception.NoIsbnFoundException;

/**
 * Servizio per la gestione del catalogo dei libri.
 * Fornisce le operazioni di recupero e filtro dei libri in base al ruolo utente.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final EditionRepository editionRepository;
    private final BookNameRepository bookNameRepository;

    /**
     * Costruttore per BookService.
     *
     * @param bookRepository Repository per l'accesso ai dati dei libri
     */
    public BookService(BookRepository bookRepository, EditionRepository editionRepository, BookNameRepository bookNameRepository) {
        this.bookRepository = bookRepository;
		this.editionRepository = editionRepository;
		this.bookNameRepository = bookNameRepository;
    }

    /**
     * Recupera tutti i libri visibili per l'utente in base al suo ruolo.
     * Gli utenti con ruolo {@code role_user} vedono solo i libri disponibili;
     * gli amministratori vedono tutti i libri indipendentemente dallo stato.
     *
     * @param userRole Ruolo dell'utente (es. role_user, role_admin)
     * @return Lista di {@link BookDto} dei libri accessibili all'utente
     */
    private List<BookDto> getAllBooks(String userRole) {
        List<BookJoin> repoBook = bookRepository.getAllBooks();
        return repoBook.stream()
            .filter(book -> !userRole.equals("role_user") ||
                    "disponibilita".equalsIgnoreCase(book.getStatus()))
            .map(book -> {
                BookDto dto = new BookDto();
                dto.setEditionId(book.getEditionId());
                dto.setBookId(book.getBookId());
                dto.setTitle(book.getBookName());
                dto.setAuthorFullName(book.getAuthorFullName());
                dto.setPublisherName(book.getPublisherName());
                dto.setPublishingDate(book.getPublicationDate());
                dto.setIsbnCode(book.getIsbnCode());
                dto.setCategoryName(book.getCategoryName());
                dto.setStatus(book.getStatus());
                return dto;
            })
            .toList();
    }

    /**
     * Recupera i dettagli di un singolo libro tramite il suo ID.
     *
     * @param bookId ID del libro da cercare
     * @return {@link BookDto} del libro trovato
     * @throws BookNotFoundException se nessun libro corrisponde all'ID specificato
     */
    public BookDto getBookById(int bookId) {
        var book = bookRepository.getAllBooks().stream()
            .filter(b -> b.getBookId() == bookId)
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException("Libro non trovato con l'ID: " + bookId));

        BookDto dto = new BookDto();
        dto.setEditionId(book.getEditionId());
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getBookName());
        dto.setAuthorFullName(book.getAuthorFullName());
        dto.setPublisherName(book.getPublisherName());
        dto.setPublishingDate(book.getPublicationDate());
        dto.setIsbnCode(book.getIsbnCode());
        dto.setCategoryName(book.getCategoryName());
        dto.setStatus(book.getStatus());
        return dto;
    }

    /**
     * Recupera il numero totale di libri fisici presenti nel sistema.
     *
     * @return Numero totale di libri nel database
     */
    public int getTotalCountBooks() {
        return bookRepository.countBooks();
    }
    
    /**
     * Aggiunge una nuova copia fisica di un libro al sistema tramite il suo ISBN.
     * Recupera l'edizione corrispondente e inserisce un nuovo record nella tabella books.
     *
     * @param isbn Il codice ISBN dell'edizione a cui aggiungere una copia
     * @return Il numero di righe inserite (1 se successo)
     * @throws NoIsbnFoundException se l'ISBN non viene fornito o non è valido
     */
	@SuppressWarnings("null")
	public int addBook(String isbn) throws NoIsbnFoundException {
		int res = 0;
		if(isbn != null || !isbn.isEmpty()) {
			res = bookRepository.insertBookByIsbn(isbn);
		}else {
			throw new NoIsbnFoundException("attenzione isbn non trovato");
		}
		return res;
	}
	
    /**
     * Esegue l'eliminazione logica di una copia fisica tramite il suo ID.
     * Imposta lo stato del libro a 'eliminato'.
     *
     * @param id ID della copia fisica da eliminare
     * @return Il numero di righe aggiornate (1 se successo)
     * @throws NoBookIdFoundException se l'ID non è fornito o non è trovato
     */
	@SuppressWarnings("null")
	public int deleteBook(Integer id) throws NoBookIdFoundException {
		int res = 0;
		if(id != null || id != 0) {
			res = bookRepository.deleteBookById(id);
		}else {
			throw new NoBookIdFoundException(id);
		}
		return res;
	}

    /**
     * Recupera una lista di libri filtrata per nome del libro.
     * 
     * @param search Il termine di ricerca per il nome del libro
     * @param userRole Il ruolo dell'utente che effettua la ricerca
     * @return Lista di BookDto contenente le informazioni condensate dei libri filtrati per nome del libro
     */
	
	public List<BookDto> getBookListByName(String search, String userRole) {
		List<BookDto> myList = getAllBooks(userRole);
		List<BookDto> filteredList = new ArrayList<>();
		if(search != null && !search.isBlank()) {
			for(BookDto book : myList) {
				if(book.getTitle().replaceAll("\\s+","").toLowerCase().equals(search.replaceAll("\\s+","").toLowerCase())) {
					filteredList.add(book);
				}
			}	
		}
		if(filteredList.isEmpty() || filteredList == null) {
			return myList;
		}else {
			return filteredList;
		}
	}
	

    /**
     * Inserisce un nuovo libro e la sua relativa edizione nel sistema.
     * Esegue tre operazioni atomiche:
     * 1. Inserisce il titolo nella tabella dei nomi.
     * 2. Crea l'edizione legata al titolo, autore, editore e categoria.
     * 3. Crea la prima copia fisica disponibile per questa edizione.
     *
     * @param title Titolo del libro
     * @param authorId ID dell'autore
     * @param publisherId ID della casa editrice
     * @param date Data di pubblicazione
     * @param categoryId ID della categoria
     * @param isbn Codice ISBN dell'edizione
     * @return Somma dei risultati delle operazioni di inserimento
     */
	
	public int insertBook(String title, Integer authorId, Integer publisherId, LocalDate date, Integer categoryId, String isbn) {

		System.out.println("title: " + title);
		System.out.println("authorId: " + authorId);
		System.out.println("publisherId: " + publisherId);
		System.out.println("date: " + date);
		System.out.println("categoryId: " + categoryId);
		System.out.println("isbn: " + isbn);
		
		int firstRes = bookNameRepository.insertBookByTitle(title);
		int secondRes = editionRepository.insertEdition(title, authorId, publisherId, date, categoryId, isbn);
		int thirdRes = bookRepository.insertBookByTitle(title);
		return firstRes + secondRes + thirdRes;
	}
	

}
