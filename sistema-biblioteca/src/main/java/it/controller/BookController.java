package it.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dto.BookDto;
import it.dto.EditionDto;
import it.dto.UserDto;
import it.component.UserSession;
import it.exception.NoBookIdFoundException;
import it.exception.InsertBookServiceException;
import it.exception.NoIsbnFoundException;
import it.service.AuthorService;
import it.service.BookService;
import it.service.PublisherService;
import it.service.CategoryService;
import it.service.EditionService;

@Controller
public class BookController {
	

    private final BookService bookService;
    private final UserSession userSession;
	private final AuthorService authorService;
	private final PublisherService publisherService;
	private final CategoryService categoryService;
	private final EditionService editionService;

    /**
     * Costruttore per BookController.
     *
     * @param bookService Servizio per la gestione dei libri
     * @param userSession Componente per la gestione della sessione utente
     */
    public BookController(	BookService bookService,
							UserSession userSession,
							AuthorService authorService,
							PublisherService publisherService,
							CategoryService categoryService,
							EditionService editionService) {
        this.bookService = bookService;
        this.userSession = userSession;
		this.authorService = authorService;
		this.publisherService = publisherService;
		this.categoryService = categoryService;
		this.editionService = editionService;
    }

    	/**
	 * Gestisce l'aggiunta di una nuova copia fisica tramite il suo codice ISBN.
	 *
	 * @param email              Email dell'amministratore che esegue l'operazione
	 * @param isbn               Codice ISBN dell'edizione da inserire
	 * @param redirectAttributes Attributi di redirect per passare messaggi alla
	 *                           vista
	 * @return Redirect alla sezione delle edizioni
	 */
	@GetMapping("/api/addBook")
	public String addBook(
			@RequestParam(value = "isbn", required = false) String isbn,
			@RequestParam(value = "bookName", required = false) String bookName,
			RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null || !"role_admin".equals(user.getUserRole())) {
			return "redirect:/";
		}
		try {
			int newBookId = bookService.addBook(isbn);
			redirectAttributes.addFlashAttribute("popupType", "addCopy");
			redirectAttributes.addFlashAttribute("popupBookIsbn", isbn);
			redirectAttributes.addFlashAttribute("popupBookTitle", bookName);
			redirectAttributes.addFlashAttribute("popupBookId", newBookId);
		} catch (NoIsbnFoundException ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("errorMessage ", ex.getMessage());
		}
		return "redirect:/dashboard";
	}


	/**
	 * Gestisce l'eliminazione (logica) di una copia fisica tramite il suo ID.
	 *
	 * @param email              Email dell'amministratore che esegue l'operazione
	 * @param bookId             ID della copia da eliminare
	 * @param redirectAttributes Attributi di redirect per passare messaggi alla
	 *                           vista
	 * @return Redirect alla sezione catalogo
	 */
	@GetMapping("api/deleteBook")
	public String deleteBook(
			@RequestParam(value = "bookId", required = false) Integer bookId,
			@RequestParam(value = "bookName", required = false) String bookName,
			RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null || !"role_admin".equals(user.getUserRole())) {
			return "redirect:/";
		}
		try {
			BookDto bookDto = new BookDto(bookId);
			bookService.deleteBook(bookDto.getBookId());
			redirectAttributes.addFlashAttribute("popupType", "deleteBook");
			redirectAttributes.addFlashAttribute("popupBookId", bookId);
			redirectAttributes.addFlashAttribute("popupBookTitle", bookName);
		} catch (NoBookIdFoundException ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.getMessage());
		}
		return "redirect:/dashboard";
	}

	/**
	 * Gestisce l'aggiunta di una nuova edizione di un libro.
	 * Riceve i dettagli dell'edizione (titolo, ISBN, data, autore, categoria,
	 * editore)
	 * e coordina l'inserimento nel sistema tramite il BookService.
	 *
	 * @param title              Titolo del libro
	 * @param isbn               Codice ISBN dell'edizione
	 * @param date               Data di pubblicazione dell'edizione
	 * @param authorId           ID dell'autore dell'edizione
	 * @param categoryId         ID della categoria del libro
	 * @param publisherId        ID della casa editrice
	 * @param email              Email dell'amministratore che esegue l'operazione
	 * @param redirectAttributes Attributi di redirect per passare messaggi alla
	 *                           vista (successo/errore)
	 * @return Redirect alla sezione delle edizioni con i parametri necessari
	 */
	@PostMapping("/api/addEdition")
	public String addEdition(
	        @RequestParam(value = "title", required = false) String title,
	        @RequestParam(value = "isbn", required = false) String isbn,
	        @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
	        @RequestParam(value = "authorName", required = false) String authorName,
	        @RequestParam(value = "authorLastName", required = false) String authorLastName,
	        @RequestParam(value = "categoryName", required = false) String categoryName,
	        @RequestParam(value = "publisherName", required = false) String publisherName,
	        @RequestParam(value = "email", required = false) String email,
	        RedirectAttributes redirectAttributes) {
			if(hasNullOrBlankParameters(title, isbn, authorName, authorLastName, categoryName, publisherName, email) && date == null) {
				redirectAttributes.addFlashAttribute("popupType", "error");
				redirectAttributes.addFlashAttribute("popupErrorMessage", "errore ci sono dei campi vuoti");
				return "redirect:/dashboard";
			}
		
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}
		try {
			bookService.insertBook(title, authorName, authorLastName, date, categoryName, publisherName, isbn);
			redirectAttributes.addFlashAttribute("popupType", "addEdition");
			redirectAttributes.addFlashAttribute("popupBookTitle", title);
			redirectAttributes.addFlashAttribute("popupBookIsbn", isbn);
		} catch (InsertBookServiceException ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}

		return "redirect:/dashboard";
	}

	/**
     * Aggiorna il titolo di un libro.
     *
     * @param bookNameId ID del libro
     * @param title      Nuovo titolo del libro
     * @param redirectAttributes Attributi di redirect per passare messaggi alla vista
     * @return Redirect alla sezione delle edizioni con i parametri necessari
     */

	@PostMapping("/api/updateBookTitle")
	public String updateBookTitle(
		@RequestParam(value = "editionId") int editionId,
		@RequestParam(value = "bookNameId") int bookNameId,
		RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}
		if(!user.getUserRole().equals("role_admin")) {
			userSession.setSection("home");
			return "redirect:/dashboard";
		}	

		try {
			EditionDto editionDto = new EditionDto();
			editionDto.setEditionId(editionId);
			editionDto.setBookNameId(bookNameId);
			editionService.updateTitleId(editionDto);
			
			redirectAttributes.addFlashAttribute("popupType", "updateTitle");
			redirectAttributes.addFlashAttribute("popupBookId", bookNameId);
			redirectAttributes.addFlashAttribute("popupBookTitle", bookService.getBookNameById(bookNameId));
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}
		return "redirect:/dashboard";
	}
	

	/**
     * Aggiorna l'autore di un libro.
     *
     * @param authorId ID dell'autore
     * @param authorName Nuovo nome dell'autore
     * @param authorLastName Nuova cognome dell'autore
     * @param redirectAttributes Attributi di redirect per passare messaggi alla vista
     * @return Redirect alla sezione delle edizioni con i parametri necessari
     */

	@PostMapping("/api/updateAuthor")
	public String updateAuthor(
		@RequestParam(value = "editionId") int editionId,
		@RequestParam(value = "authorId") int authorId,
		RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}

		
		try {
			
		if(!user.getUserRole().equals("role_admin")) {
			userSession.setSection("home");
			return "redirect:/dashboard";
		}
			editionService.updateAuthorId(editionId, authorId);

			redirectAttributes.addFlashAttribute("popupType", "updateAuthor");
			redirectAttributes.addFlashAttribute("popupAuthorId", authorId);
			redirectAttributes.addFlashAttribute("popupAuthorName", authorService.getAuthorById(authorId).getAuthorName());
			redirectAttributes.addFlashAttribute("popupAuthorLastName", authorService.getAuthorById(authorId).getAuthorLastName());
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}
		return "redirect:/dashboard";
	}

	/**
	 * Aggiorna la casa editrice di un libro.
	 *
	 * @param publisherId ID della casa editrice
	 * @param publisherName Nuovo nome della casa editrice
	 * @param redirectAttributes Attributi di redirect per passare messaggi alla vista
	 * @return Redirect alla sezione delle edizioni con i parametri necessari
	 */

	@PostMapping("/api/updatePublisher")
	public String updatePublisher(
		@RequestParam(value = "editionId") Integer editionId,
		@RequestParam(value = "publisherNameId") int publisherNameId,
		RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}

		
		try {

			if(!user.getUserRole().equals("role_admin")) {
				userSession.setSection("home");
				return "redirect:/dashboard";
			}

			editionService.updatePublisherId(editionId, publisherNameId);
			redirectAttributes.addFlashAttribute("popupType", "updatePublisher");
			redirectAttributes.addFlashAttribute("popupPublisherId", publisherNameId);
			redirectAttributes.addFlashAttribute("popupPublisherName", publisherService.getPublisherById(publisherNameId).getPublisherName());
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}
		return "redirect:/dashboard";
	}

	@PostMapping("/api/updateCategory")
	public String updateCategory(
		@RequestParam(value = "editionId") int editionId,
		@RequestParam(value = "categoryNameId") int categoryNameId,
		RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}

		
		try {

			if(!user.getUserRole().equals("role_admin")) {
				userSession.setSection("home");
				return "redirect:/dashboard";
			}

			editionService.updateCategoryId(editionId, categoryNameId);
			redirectAttributes.addFlashAttribute("popupType", "updateCategory");
			redirectAttributes.addFlashAttribute("popupCategoryId", categoryNameId);
			redirectAttributes.addFlashAttribute("popupCategoryName", categoryService.getCategoryById(categoryNameId).getCategoryName());
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}
		return "redirect:/dashboard";
	}

	@PostMapping("/api/addBookName")
	public String addBookName(
		@RequestParam(value = "title") String title,
		RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}
		if(!user.getUserRole().equals("role_admin")) {
			userSession.setSection("home");
			return "redirect:/dashboard";
		}	
		try {
			bookService.insertAndGetBookNameId(title);
			redirectAttributes.addFlashAttribute("popupType", "addBookName");
			redirectAttributes.addFlashAttribute("popupBookTitle", title);
		} catch (InsertBookServiceException ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}

		return "redirect:/dashboard";
	}
	
	@PostMapping("/api/addAuthor")
	public String addAuthor(
		@RequestParam(value = "authorName") String authorName,
		@RequestParam(value = "authorLastName") String authorLastName,
		RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}
		if(!user.getUserRole().equals("role_admin")) {
			userSession.setSection("home");
			return "redirect:/dashboard";
		}	
		try {
			bookService.insertAuthor(authorName, authorLastName);
			redirectAttributes.addFlashAttribute("popupType", "addAuthor");
			redirectAttributes.addFlashAttribute("popupAuthorName", authorName);
			redirectAttributes.addFlashAttribute("popupAuthorLastName", authorLastName);
		} catch (InsertBookServiceException ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}

		return "redirect:/dashboard";
	}
	

	@PostMapping("/api/addPublisher")
	public String addPublisher(
		@RequestParam(value = "publisherName") String publisherName,
		RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}
		if(!user.getUserRole().equals("role_admin")) {
			userSession.setSection("home");
			return "redirect:/dashboard";
		}	
		try {
			bookService.insertPublisher(publisherName);
			redirectAttributes.addFlashAttribute("popupType", "addPublisher");
			redirectAttributes.addFlashAttribute("popupPublisherName", publisherName);
		} catch (InsertBookServiceException ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}

		return "redirect:/dashboard";
	}
	
	@PostMapping("/api/addCategory")
	public String addCategory(
		@RequestParam(value = "categoryName") String categoryName,
		RedirectAttributes redirectAttributes) {
		UserDto user = userSession.getUser();
		if (user == null) {
			return "redirect:/";
		}
		if(!user.getUserRole().equals("role_admin")) {
			userSession.setSection("home");
			return "redirect:/dashboard";
		}	
		try {
			bookService.insertCategory(categoryName);
			redirectAttributes.addFlashAttribute("popupType", "addCategory");
			redirectAttributes.addFlashAttribute("popupCategoryName", categoryName);
		} catch (InsertBookServiceException ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.toString());
		}

		return "redirect:/dashboard";
	}
	
	
	
	
	
	/**
	 * Restituisce il frammento HTML per la lista delle copie di un'edizione.
	 * Utilizzato per il caricamento dinamico nel popup tramite Thymeleaf Fragments.
	 *
	 * @param editionId      ID dell'edizione
	 * @param includeDeleted Flag per includere le copie eliminate
	 * @param editionTitle   Titolo dell'edizione (opzionale)
	 * @param model          Modello Thymeleaf
	 * @return Il frammento "bookCopiesList" all'interno di popup.html
	 */
	// @GetMapping("/fragments/book-copies")
	// public String getBookCopiesFragment(
	// 		@RequestParam("editionId") int editionId,
	// 		@RequestParam(value = "includeDeleted", defaultValue = "false") boolean includeDeleted,
	// 		@RequestParam(value = "editionTitle", required = false) String editionTitle,
	// 		Model model) {
	// 	model.addAttribute("books", bookService.getBooksByEditionId(editionId, includeDeleted));
	// 	model.addAttribute("includeDeleted", includeDeleted);
	// 	model.addAttribute("editionTitle", editionTitle);
	// 	return "fragments/popup :: bookCopiesList";
	// }
	
	private boolean hasNullOrBlankParameters(String... params) {
		for(String s : params) {
			if(s == null || s.isBlank()) {
				return true;
			}
		}
		return false;
	}
}