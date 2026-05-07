package it.service;

import java.util.ArrayList;




/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.utils.ConvertTo;

import it.dto.BookDto;
import it.dto.RentalRecordDto;
import it.dto.BookNameDto;
import it.dto.request.InsertBookDto;
import it.entity.Publisher;
import it.entity.BookName;
import it.entity.Book;
import it.entity.Category;
import it.entity.Edition;
import it.repository.AuthorRepository;
import it.repository.BookNameRepository;
import it.repository.BookRepository;
import it.repository.EditionRepository;
import it.repository.PublisherRepository;
import it.repository.CategoryRepository;
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.AuthorRepositoryException;
import it.exception.repository.BookNamesRepositoryException;
import it.exception.repository.BookNotFoundException;
import it.exception.repository.BookRepositoryException;
import it.exception.repository.CategoryRepositoryException;
import it.exception.repository.NoBookIdFoundException;
import it.exception.repository.PublisherExceptionRepository;
import it.exception.service.BookServiceException;



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
    private final ConvertTo convertTo;

    /**
     * Costruttore per BookService.
     *
     * @param bookRepository Repository per l'accesso ai dati dei libri
     * 
     */
    public BookService(BookRepository bookRepository, EditionRepository editionRepository, BookNameRepository bookNameRepository,
    				   AuthorRepository authorRepository, CategoryRepository categoryRepository, PublisherRepository publisherRepository,
    				   ConvertTo convertTo) {
        this.bookRepository = bookRepository;
		this.editionRepository = editionRepository;
		this.bookNameRepository = bookNameRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
		this.publisherRepository = publisherRepository;
		this.convertTo = convertTo;
    }

    public List<BookNameDto> getAllBookNames() throws BookServiceException {
        try {
        	return bookNameRepository.getAllBookNames()
        			.stream()
        			.map(convertTo::convertToBookNameDto)
        			.toList();
        }catch(BookNamesRepositoryException ex) {
        	throw new BookServiceException(ex.getMessage());
        }
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
    private List<BookDto> getAllBooks(String userRole) throws BookServiceException {
        try {
        	List<Book> repoBook = bookRepository.getAllBooks();
        	return repoBook.stream()
        			.filter(book -> !userRole.equals("role_user") ||
        					"disponibilita".equalsIgnoreCase(book.getStatus()))
        			.map(convertTo::convertToBookDto)
        			.toList();
        }catch(BookRepositoryException ex) {
        	throw new BookServiceException(ex.getMessage());
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
    public BookDto getBookById(int bookId) throws BookServiceException {
       try {
    	   var book = bookRepository.getAllBooks().stream()
    			   .filter(b -> b.getBookId() == bookId)
    			   .findFirst()
    			   .orElseThrow(() -> new RuntimeException("testo un'eccezione"));
    	   BookDto bookDto = new BookDto();
    	   bookDto.setBookId(book.getBookId());
    	   bookDto.setEditionDto(convertTo.convertToEditionDto(book.getEdition()));
    	   bookDto.setStatus(book.getStatus());
    	   return bookDto;
       }catch(BookRepositoryException ex) {
    	   throw new BookServiceException(ex.getMessage());
       }

    }

    /**
     * Recupera il numero totale di libri fisici presenti nel sistema.
     *
     * @return Numero totale di libri nel database
     */
    @Transactional(readOnly = true)
    public int getTotalCountBooks() throws BookServiceException{
        try {
			return bookRepository.countAllBooks();
		} catch (BookRepositoryException e) {
			throw new BookServiceException(e.getMessage());
		} catch (QueryIsNullOrNegativeExcepetion e) {
			throw new BookServiceException(e.getMessage());
		}
    }
    
    @Transactional(readOnly = true) 
    public int getTotalNotElimatedBooks() throws BookServiceException{
    	try{
    		return bookRepository.countAllNotEliminatedBooks();
    	}catch(BookRepositoryException | QueryIsNullOrNegativeExcepetion ex) {
    		throw new BookServiceException(ex.getMessage());
    	}
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
	public int addBook(String isbn) throws BookServiceException {
		int res = 0;
		if(isbn == null || isbn.isEmpty()) {
			throw new BookServiceException("isbn non valido");
		}
		try {
			res = bookRepository.insertBookByIsbn(isbn);	
		}catch(BookRepositoryException ex) {
			throw new BookServiceException(ex.getMessage());
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
	// @SuppressWarnings("null")
	@Transactional
	public int deleteBook(Integer id) throws BookServiceException {
		int res = 0;
		if(id == null || id <= 0) {
			throw new BookServiceException("id non valido");
		}
		try {
			res = bookRepository.deleteBookById(id);	
		}catch(BookRepositoryException ex) {
			throw new BookServiceException(ex.getMessage());
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
	
	public List<BookDto> getBookListByName(String search, String userRole) throws BookServiceException{
		List<BookDto> myList = getAllBooks(userRole);
		List<BookDto> filteredList = new ArrayList<>();
		if(search != null && !search.isBlank()) {
			String [] searchArray = search.toLowerCase().split("\\s+");
			for(BookDto book : myList) {
				String title = book.getEditionDto().getBookNameDto().getTitle().trim();
				String authorName = book.getEditionDto().getAuthorDto().getAuthorName().trim();
				String authorLastName = book.getEditionDto().getAuthorDto().getAuthorLastName().trim();
				String category = book.getEditionDto().getCategoryDto().getCategoryName().trim();
				String publisher = book.getEditionDto().getPublisherDto().getPublisherName().trim();
				String finalString = (title + " " + authorName + " " + authorLastName + " " + category + " " + publisher).toLowerCase().trim();
				boolean allRight = true;
				for(String s : searchArray) {
					if(!finalString.contains(s)) {
						allRight = false;
					}
				}
				if(allRight) {
					filteredList.add(book);
				}
			}	
		}else {
			return myList;
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
	public int insertBook(InsertBookDto insertBookDto) throws BookServiceException {
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
		    if(!bookNameRepository.isTitleOnDb(insertBookDto.getTitle().trim())) {
		        bookNameRepository.insertBookByTitle(insertBookDto.getTitle().trim());
		        System.out.println("titolo insert fatta");
		    }

		    if(!authorRepository.isAuthorPresent(insertBookDto.getAuthorName().trim(), insertBookDto.getAuthorLastName().trim())) {
		        authorRepository.insertAuthorByNameAndLastName(insertBookDto.getAuthorName().trim(), insertBookDto.getAuthorLastName().trim());
		        System.out.println("autore insert fatta");
		    }

		    Category categoryBis = new Category(insertBookDto.getCategoryName().trim());
		    if(!categoryRepository.isCategoryPresentByName(categoryBis)) {
		        categoryRepository.insertCategoryByNameCategory(insertBookDto.getCategoryName().trim());
		        System.out.println("categoria insert fatta");
		    }

		    Publisher publisherBis = new Publisher(insertBookDto.getPublisherName().trim());
		    if(!publisherRepository.isPublisherPresent(publisherBis)) {
		        publisherRepository.insertPublisherByPubliserName(insertBookDto.getPublisherName().trim());
		        System.out.println("publisher insert fatta");
		    }
		    	
		    editionRepository.insertEdition(
		        insertBookDto.getTitle().trim(),
		        insertBookDto.getAuthorName().trim(),
		        insertBookDto.getAuthorLastName().trim(),
		        insertBookDto.getPublisherName().trim(),
		        insertBookDto.getLocalDate(),
		        insertBookDto.getCategoryName().trim(),
		        insertBookDto.getIsbn().trim()
		    );
		    List<Edition> myList = editionRepository.getAllEditions();
		    
		    myList.forEach(e -> {System.out.println(e.getEditionId() + " " + e.getBookName().getTitle() + " " + e.getBookName().getBookNameId());});
		    int bookId = bookRepository.insertBookByTitleAndIsbn(insertBookDto.getTitle().trim(), insertBookDto.getIsbn().trim());
		    
		    return bookId;
		} catch(BookNamesRepositoryException | QueryIsNullOrNegativeExcepetion ex) {
		    System.out.println(ex.toString());
		    throw new BookServiceException(ex.getMessage());
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
            .map(convertTo::convertToRentalRecordDto)
            .toList();
    }


    /**
     * Aggiorna il titolo di un libro esistente.
     *
     * @param bookNameDto DTO del titolo da aggiornare
     */
    @Transactional
    public void updateBookTitle(BookNameDto bookNameDto) throws BookServiceException { 
        try {
        	bookNameRepository.updateBookTitle(bookNameDto);   	
        }catch(BookNamesRepositoryException ex) {
        	throw new BookServiceException(ex.getMessage());
        }
    }

    /**
     * Inserisce un nuovo titolo e restituisce il suo ID generato.
     *
     * @param title Il titolo da inserire
     * @return L'ID del titolo inserito
     * @throws InsertBookNameException se l'inserimento fallisce
     */
    @Transactional
    public int insertAndGetBookNameId(String title) throws BookServiceException {
        try{
        	bookNameRepository.insertBookByTitle(title);
        }catch(BookNamesRepositoryException ex) {
        	throw new BookServiceException(ex.getMessage());
        }
        return getBookNameId(title);
    }

    /**
     * Inserisce un nuovo autore nel sistema.
     *
     * @param authorName Nome dell'autore
     * @param authorLastName Cognome dell'autore
     * @throws InsertAuthorException se l'inserimento fallisce
     */
    @Transactional
    public void insertAuthor(String authorName, String authorLastName) throws BookServiceException {
        try {
        	authorRepository.insertAuthor(authorName, authorLastName);
        }catch(AuthorRepositoryException ex) {
        	 throw new BookServiceException(ex.getMessage());
        }
    }

    /**
     * Inserisce una nuova casa editrice nel sistema.
     *
     * @param publisherName Nome della casa editrice
     * @throws InsertPublisherException se l'inserimento fallisce
     */
    @Transactional
    public void insertPublisher(String publisherName) throws BookServiceException {
        try {
        	publisherRepository.insertPublisher(publisherName);
        }catch(PublisherExceptionRepository ex) {
        	throw new BookServiceException(ex.getMessage());
        }
    }

    /**
     * Inserisce una nuova categoria nel sistema.
     *
     * @param categoryName Nome della categoria
     * @throws InsertCategoryException se l'inserimento fallisce
     */
    @Transactional
    public void insertCategory(String categoryName) throws BookServiceException {
        try {
        	categoryRepository.insertCategory(categoryName);
        	
        }catch(CategoryRepositoryException ex) {
        	throw new BookServiceException(ex.getMessage());
        }
    }


    /**
     * Verifica se un titolo di libro è già presente nel database, escludendo l'ID specificato.
     *
     * @param bookNameDto DTO contenente il titolo da verificare e l'ID da escludere
     * @return true se il titolo esiste già, false altrimenti
     */
    @Transactional
    public boolean isBookNamePresent(BookNameDto bookNameDto) throws BookServiceException {
        try {
        	return bookNameRepository.getBookNamesByTitle(bookNameDto.getTitle()).stream()
        			.filter(b -> b.getBookNameId() != bookNameDto.getBookNameId())
        			.findFirst()
        			.isPresent();
        }catch(BookNamesRepositoryException ex) {
        	throw new BookServiceException(ex.getMessage());
        }
    }
    
    /**
     * Recupera l'ID di un titolo tramite il suo nome.
     *
     * @param title Titolo del libro da cercare
     * @return ID del titolo trovato
     */
    @Transactional
    public int getBookNameId(String title) throws BookServiceException {
        try {
        	return bookNameRepository.getBookNamesByTitle(title).stream()
        			.findFirst()
        			.get().getBookNameId();
        }catch(BookNamesRepositoryException ex) {
        	throw new BookServiceException(ex.getMessage());
        }
    }


    /**
     * Recupera i dettagli di un titolo tramite il suo ID.
     *
     * @param bookNameId ID del record del titolo
     * @return {@link BookNameDto} con i dettagli del titolo
     */
    @Transactional(readOnly = true)
    public BookNameDto getBookNameById(int bookNameId) throws BookServiceException {
        try {
        	BookName bookName = bookNameRepository.getBookNameById(bookNameId);
        	BookNameDto BookNameDto = new BookNameDto();
        	BookNameDto.setBookNameId(bookName.getBookNameId());
        	BookNameDto.setTitle(bookName.getTitle());
        	return BookNameDto;
        }catch(BookNamesRepositoryException ex) {
        	throw new BookServiceException(ex.getMessage());
        }
    }
}
