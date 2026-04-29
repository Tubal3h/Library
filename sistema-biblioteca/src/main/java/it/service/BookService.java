package it.service;

import java.util.ArrayList;



/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.exception.NoBookIdFoundException;

import it.dto.AuthorDto;
import it.dto.BookDto;
import it.dto.CategoryDto;
import it.dto.EditionDto;
import it.dto.PublisherDto;
import it.dto.RentalRecordDto;
import it.dto.request.InsertBookDto;
import it.dto.BookNameDto;
import it.entity.BookName;
import it.entity.Publisher;
import it.entity.Book;
import it.exception.BookNotFoundException;
import it.exception.InsertAuthorException;
import it.exception.InsertBookNameException;
import it.exception.InsertBookServiceException;
import it.exception.InsertCategoryException;
import it.exception.InsertPublisherException;
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

    public List<BookNameDto> getAllBookNames() {
        return bookNameRepository.getAllBookNames();
    }

    /**
     * Converte un oggetto {@link BookNames} in un {@link BookNameDto}.
     * 
     * @param bookNames Oggetto {@link BookNames} da convertire
     * @return Oggetto {@link BookNameDto} convertito
     */
    private BookNameDto convertToBookNamesDto(BookName bookNames) {
        return new BookNameDto(bookNames.getBookNamesId(), bookNames.getTitle());
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
    	List<Book> bookList = bookRepository.getAllBooks();
    	List<BookDto> bookDtoList = new ArrayList<>();
    	for(Book b : bookList) {
    		if(userRole != "role_user" || "disponibilita".equalsIgnoreCase(b.getStatus())) {
    			
    		}
    	}
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
        dto.setAuthorName(book.getAuthorName());
        dto.setAuthorLastName(book.getAuthorLastName());
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
        return bookRepository.countAllBooks();
    }
    
    @Transactional(readOnly = true)
    public int getTotalNotElimatedBooks() {
    	return bookRepository.countAllNotEliminatedBooks();
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
				if(book.getEdition().getBookNameDto().getTitle().replaceAll("\\s+","").toLowerCase().contains(search.replaceAll("\\s+","").toLowerCase())) {
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
	public void insertBook(InsertBookDto insertBookDto) throws InsertBookServiceException {
		String authorFullName = insertBookDto.getAuthorName().concat(" ").concat(insertBookDto.getAuthorLastName());
		System.out.println("author full name: " + authorFullName);
		System.out.println("title: " + insertBookDto.getTitle());
		System.out.println("authorName: " + insertBookDto.getAuthorName());
		System.out.println("authorLastName " + insertBookDto.getAuthorLastName());
		System.out.println("publisher: " + insertBookDto.getPublisherName());
		System.out.println("date: " + insertBookDto.getLocalDate());
		System.out.println("category: " + insertBookDto.getCategoryName());
		System.out.println("isbn: " + insertBookDto.getIsbn());
		
		
		try {
			if(!bookNameRepository.isTitleOnDb(insertBookDto.getTitle())) {
				bookNameRepository.insertBookByTitle(insertBookDto.getTitle());
				System.out.println("titolo insert fatta");
			}
			
			if(!authorRepository.isAuthorPresent(insertBookDto.getAuthorName(), insertBookDto.getAuthorLastName())) {
				authorRepository.insertAuthorByNameAndLastName(insertBookDto.getAuthorName(), insertBookDto.getAuthorLastName());	
				System.out.println("autore insert fatta");
			}
			 
			Category categoryBis = new Category(insertBookDto.getCategoryName());
			if(!categoryRepository.isCategoryPresentByName(categoryBis)) {
				categoryRepository.insertCategoryByNameCategory(insertBookDto.getCategoryName());
				System.out.println("caegoria insert fatta");
			}
			
			Publisher publisherBis = new Publisher(insertBookDto.getPublisherName());
			if(!publisherRepository.isPublisherPresent(publisherBis)) {
				publisherRepository.insertPublisherByPubliserName(insertBookDto.getPublisherName());
				System.out.println("publisher insert fatta");
			}
			editionRepository.insertEdition(insertBookDto.getTitle(), insertBookDto.getAuthorName(), insertBookDto.getAuthorLastName(), insertBookDto.getPublisherName(), insertBookDto.getLocalDate(), insertBookDto.getCategoryName(), insertBookDto.getIsbn());
			bookRepository.insertBookByTitle(insertBookDto.getTitle());
		}catch(RuntimeException ex) {
			System.out.println(ex.toString());
			throw new InsertBookServiceException(ex.getMessage());	
		}
	}
	

    /**
     * Recupera la lista di libri (copie) associate a una specifica edizione.
     *
     * @param editionId ID dell'edizione
     * @param includeDeleted Flag per includere anche i libri eliminati
     * @return Lista di BookDto delle copie trovate
     */
    public List<RentalRecordDto> getBooksByEditionId(int editionId, boolean includeDeleted) {
        return bookRepository.getBooksByEditionId(editionId, includeDeleted).stream()
            .map(book -> {
                RentalRecordDto dto = new RentalRecordDto();
                dto.setEditionId(book.getEditionId());
                dto.setBookId(book.getBookId());
                dto.setTitle(book.getBookName());
                dto.setAuthorName(book.getAuthorName());
                dto.setAuthorLastName(book.getAuthorLastName());
                dto.setPublisherName(book.getPublisherName());
                dto.setPublishingDate(book.getPublicationDate());
                dto.setIsbn(book.getIsbnCode());
                dto.setCategoryName(book.getCategoryName());
                dto.setStatus(book.getStatus());
                dto.setUserName(book.getUserName());
                dto.setUserLastName(book.getUserLastName());
                return dto;
            })
            .toList();
    }


    @Transactional
    public void updateBookTitle(int bookNameId, String editionTitle) { 
        bookNameRepository.updateBookTitle(bookNameId, editionTitle);
    }

    @Transactional
    public int insertAndGetBookNameId(String title) throws InsertBookNameException {
        bookNameRepository.insertBookByTitle(title);
        return getBookNameId(title);
    }

    @Transactional
    public void insertAuthor(String authorName, String authorLastName) throws InsertAuthorException {
        authorRepository.insertAuthor(authorName, authorLastName);
    }

    @Transactional
    public void insertPublisher(String publisherName) throws InsertPublisherException {
        publisherRepository.insertPublisher(publisherName);
    }

    @Transactional
    public void insertCategory(String categoryName) throws InsertCategoryException {
        categoryRepository.insertCategory(categoryName);
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


    @Transactional(readOnly = true)
    public BookNameDto getBookNameById(int bookNameId) {
        BookName bookName = bookNameRepository.getBookNameById(bookNameId);
        BookNameDto BookNameDto = new BookNameDto();
        BookNameDto.setBookNameId(bookName.getBookNameId());
        BookNameDto.setTitle(bookName.getTitle());
        return BookNameDto;
    }
}
