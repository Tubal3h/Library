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
import it.dto.UserDto;
import it.dto.request.InsertBookDto;
import it.dto.BookNameDto;
import it.entity.Publisher;
import it.entity.RentalRecord;
import it.entity.BookName;
import it.entity.Book;
import it.entity.Author;
import it.entity.Category;
import it.entity.Edition;
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
        return bookNameRepository.getAllBookNames()
                .stream()
                .map(this::convertToBookNameDto)
                .toList();
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
        List<Book> repoBook = bookRepository.getAllBooks();
        return repoBook.stream()
            .filter(book -> !userRole.equals("role_user") ||
                    "disponibilita".equalsIgnoreCase(book.getStatus()))
            .map(this::convertToBookDto)
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

        BookDto bookDto = new BookDto();
        bookDto.setBookId(book.getBookId());
        bookDto.setEditionDto(convertToEditionDto(book.getEdition()));
        bookDto.setStatus(book.getStatus());
        return bookDto;
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
				if(book.getEditionDto().getBookNameDto().getTitle().replaceAll("\\s+","").toLowerCase().contains(search.replaceAll("\\s+","").toLowerCase())) {
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
            .map(this::convertToRentalRecordDto)
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



    private BookDto convertToBookDto(Book book) {
        
        BookDto bookDto = new BookDto();
        bookDto.setBookId(book.getBookId());
        bookDto.setEditionDto(convertToEditionDto(book.getEdition()));
        bookDto.setStatus(book.getStatus());
        return bookDto;
    }

    private AuthorDto convertToAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setAuthorId(author.getAuthorId());
        dto.setAuthorName(author.getAuthorName());
        dto.setAuthorLastName(author.getAuthorLastName());
        return dto;
    }

    private BookNameDto convertToBookNameDto(BookName bookName) {
        BookNameDto dto = new BookNameDto();
        dto.setBookNameId(bookName.getBookNameId());
        dto.setTitle(bookName.getTitle());
        return dto;
    }

    private CategoryDto convertToCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    private PublisherDto convertToPublisherDto(Publisher publisher) {
        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(publisher.getPublisherId());
        dto.setPublisherName(publisher.getPublisherName());
        return dto;
    }

    private EditionDto convertToEditionDto(Edition edition) {
        EditionDto dto = new EditionDto();
        dto.setEditionId(edition.getEditionId());
        dto.setAuthorDto(convertToAuthorDto(edition.getAuthor()));
        dto.setCategoryDto(convertToCategoryDto(edition.getCategory()));
        dto.setPublisherDto(convertToPublisherDto(edition.getPublisher()));
        dto.setPublishingDate(edition.getPublishingDate());
        dto.setIsbn(edition.getIsbn());
        dto.setQuantity(edition.getQuantity());
        return dto;
    }

    private RentalRecordDto convertToRentalRecordDto(RentalRecord rent) {
        UserDto user = new UserDto();
        user.setUserName(rent.getUser().getUserName());
        user.setUserLastName(rent.getUser().getUserLastName());

        BookNameDto bookNameDto = new BookNameDto();
        bookNameDto.setTitle(rent.getBook().getEdition().getBookName().getTitle());

        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorName(rent.getBook().getEdition().getAuthor().getAuthorName());
        authorDto.setAuthorLastName(rent.getBook().getEdition().getAuthor().getAuthorLastName());

        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherName(rent.getBook().getEdition().getPublisher().getPublisherName());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(rent.getBook().getEdition().getCategory().getCategoryName());

        EditionDto edition = new EditionDto();
        edition.setEditionId(rent.getBook().getEdition().getEditionId());
        edition.setBookNameDto(bookNameDto);
        edition.setAuthorDto(authorDto);
        edition.setPublisherDto(publisherDto);
        edition.setCategoryDto(categoryDto);
        edition.setPublishingDate(rent.getBook().getEdition().getPublishingDate());
        edition.setIsbn(rent.getBook().getEdition().getIsbn());

        BookDto book = new BookDto();
        book.setBookId(rent.getBook().getBookId());
        book.setEditionDto(edition);
        book.setStatus(rent.getBook().getStatus());

        RentalRecordDto rentalRecordDto = new RentalRecordDto();
        rentalRecordDto.setRentalId(rent.getRentalId());
        rentalRecordDto.setUserDto(user);
        rentalRecordDto.setBookDto(book);
        rentalRecordDto.setRentalDate(rent.getRentalDate());
        rentalRecordDto.setRentalExpired(rent.getRentalExpired());
        rentalRecordDto.setRentalEnded(rent.getRentalEnded());
        rentalRecordDto.setBookingDate(rent.getBookingDate());
        return rentalRecordDto;
    }

}
