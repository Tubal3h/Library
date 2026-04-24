package it.service;

import java.time.LocalDate;
import java.util.ArrayList;


/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.exception.NoBookIdFoundException;

import it.dto.BookDto;
import it.dto.BookNameDto;
import it.entity.BookJoin;
import it.entity.Category;
import it.entity.Publisher;
import it.exception.BookNotFoundException;
import it.exception.InsertBookServiceException;
import it.repository.AuthorRepository;
import it.repository.BookNameRepository;
import it.repository.BookRepository;
import it.repository.EditionRepository;
import it.repository.PublisherRepository;
import it.repository.CategoryRepository;
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
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    /**
     * Costruttore per BookService.
     *
     * @param bookRepository Repository per l'accesso ai dati dei libri
     * 
     */
    public BookService(BookRepository bookRepository, EditionRepository editionRepository, BookNameRepository bookNameRepository,
    				   AuthorRepository authorRepository, CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
		this.editionRepository = editionRepository;
		this.bookNameRepository = bookNameRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
		this.publisherRepository = publisherRepository;
    }

    public List<it.entity.BookName> getAllBookNames() {
        return bookNameRepository.getAllBookNames();
    }

    /**
     * Recupera tutti i libri visibili per l'utente in base al suo ruolo.
     * Gli utenti con ruolo {@code role_user} vedono solo i libri disponibili;
     * gli amministratori vedono tutti i libri indipendentemente dallo stato.
     *
     * @param userRole Ruolo dell'utente (es. role_user, role_admin)
     * @return Lista di {@link BookDto} dei libri accessibili all'utente
     */
    @Transactional(readOnly = true)
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
                dto.setIsbn(book.getIsbnCode());
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
    @Transactional(readOnly = true)
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
        dto.setIsbn(book.getIsbnCode());
        dto.setCategoryName(book.getCategoryName());
        dto.setStatus(book.getStatus());
        return dto;
    }

    /**
     * Recupera il numero totale di libri fisici presenti nel sistema.
     *
     * @return Numero totale di libri nel database
     */
    @Transactional(readOnly = true)
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
	// @SuppressWarnings("null")
	@Transactional
	public int addBook(String isbn) throws NoIsbnFoundException {
		int res = 0;
		if(isbn != null && !isbn.isEmpty()) {
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
	@Transactional
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
	
	@Transactional
	public void insertBook(String title, String authorName, String authorLastName, LocalDate date, String category, String publisher, String isbn) throws InsertBookServiceException {
		String authorFullName = authorName.concat(" ").concat(authorLastName);
		System.out.println("author full name: " + authorFullName);
		System.out.println("title: " + title);
		System.out.println("authorName: " + authorName);
		System.out.println("authorLastName " + authorLastName);
		System.out.println("publisher: " + publisher);
		System.out.println("date: " + date);
		System.out.println("category: " + category);
		System.out.println("isbn: " + isbn);
		
		
		try {
			if(!bookNameRepository.isTitleOnDb(title)) {
				bookNameRepository.insertBookByTitle(title);
				System.out.println("titolo insert fatta");
			}
			
			if(!authorRepository.isAuthorPresent(authorName, authorLastName)) {
				authorRepository.insertAuthorByNameAndLastName(authorName, authorLastName);	
				System.out.println("autore insert fatta");
			}
			 
			Category categoryBis = new Category(category);
			if(!categoryRepository.isCategoryPresentByName(categoryBis)) {
				categoryRepository.insertCategoryByNameCategory(category);
				System.out.println("caegoria insert fatta");
			}
			
			Publisher publisherBis = new Publisher(publisher);
			if(!publisherRepository.isPublisherPresent(publisherBis)) {
				publisherRepository.insertPublisherByPubliserName(publisher);
				System.out.println("publisher insert fatta");
			}
			editionRepository.insertEdition(title, authorName, authorLastName, publisher, date, category, isbn);
			bookRepository.insertBookByTitle(title);
		}catch(RuntimeException ex) {
			System.out.println(ex.toString());
			throw new InsertBookServiceException("errore nell'inserimento di un libro");	
		}
	}
	

    /**
     * Recupera la lista di libri (copie) associate a una specifica edizione.
     *
     * @param editionId ID dell'edizione
     * @param includeDeleted Flag per includere anche i libri eliminati
     * @return Lista di BookDto delle copie trovate
     */
    public List<BookDto> getBooksByEditionId(int editionId, boolean includeDeleted) {
        return bookRepository.getBooksByEditionId(editionId, includeDeleted).stream()
            .map(book -> {
                BookDto dto = new BookDto();
                dto.setEditionId(book.getEditionId());
                dto.setBookId(book.getBookId());
                dto.setTitle(book.getBookName());
                dto.setAuthorFullName(book.getAuthorFullName());
                dto.setPublisherName(book.getPublisherName());
                dto.setPublishingDate(book.getPublicationDate());
                dto.setIsbn(book.getIsbnCode());
                dto.setCategoryName(book.getCategoryName());
                dto.setStatus(book.getStatus());
                return dto;
            })
            .toList();
    }


    @Transactional
    public void updateBookTitle(int bookNameId, String editionTitle) { 
        bookNameRepository.updateBookTitle(bookNameId, editionTitle);
    }

    @Transactional
    public int insertAndGetBookNameId(String title) throws it.exception.InsertBookNameException {
        bookNameRepository.insertBookByTitle(title);
        return getBookNameId(title);
    }

    @Transactional
    public boolean isBookNamePresent(BookNameDto bookNameDto) {
        return bookNameRepository.getBookNamesByTitle(bookNameDto.getTitle()).stream()
                .filter(b -> b.getBookNameId() != bookNameDto.getBookNameId())
                .findFirst()
                .isPresent();
    }
    
    @Transactional
    public int getBookNameId(String title) {
        return bookNameRepository.getBookNamesByTitle(title).stream()
                .findFirst()
                .get().getBookNameId();
    }
}
