package it.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dto.BookDto;
import it.dto.UserDto;
import it.exception.NoBookIdFoundException;
import it.exception.NoIsbnFoundException;
import it.service.BookService;

import jakarta.servlet.http.HttpSession;


@Controller
public class BookController {
	

	private final BookService bookService;

    /**
     * Costruttore per BookController.
     *
     * @param bookRepository Repository per la gestione dei libri
     * @param userRepository Repository per la gestione degli utenti
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
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
			HttpSession session,
			@RequestParam(value = "isbn", required = false) String isbn,
			@RequestParam(value = "bookName", required = false) String bookName,
			RedirectAttributes redirectAttributes) {
		UserDto user = (UserDto) session.getAttribute("user");
		if (user == null || !"role_admin".equals(user.getUserRole())) {
			return "redirect:/";
		}
		try {
			BookDto bookDto = new BookDto(isbn);
			bookService.addBook(bookDto.getIsbnCode());
			redirectAttributes.addFlashAttribute("popupType", "addCopy");
			redirectAttributes.addFlashAttribute("popupBookIsbn", isbn);
			redirectAttributes.addFlashAttribute("popupBookTitle", bookName);
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
			HttpSession session,
			@RequestParam(value = "bookId", required = false) Integer bookId,
			@RequestParam(value = "bookName", required = false) String bookName,
			RedirectAttributes redirectAttributes) {
		UserDto user = (UserDto) session.getAttribute("user");
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
			@RequestParam("title") String title,
			@RequestParam("isbn") String isbn,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam("authorId") Integer authorId,
			@RequestParam("categoryId") Integer categoryId,
			@RequestParam("publisherId") Integer publisherId,
			@RequestParam("email") String email,
			HttpSession session,
			
			RedirectAttributes redirectAttributes) {
		UserDto user = (UserDto) session.getAttribute("user");
		if (user == null) {
			return "redirect:/";
		}
		try {
			bookService.insertBook(title, authorId, publisherId, date, categoryId, isbn);
			redirectAttributes.addFlashAttribute("popupType", "addEdition");
			redirectAttributes.addFlashAttribute("popupBookTitle", title);
			redirectAttributes.addFlashAttribute("popupBookIsbn", isbn);
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", "Impossibile aggiungere l'edizione.");
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
	@GetMapping("/fragments/book-copies")
	public String getBookCopiesFragment(
			@RequestParam("editionId") int editionId,
			@RequestParam(value = "includeDeleted", defaultValue = "false") boolean includeDeleted,
			@RequestParam(value = "editionTitle", required = false) String editionTitle,
			Model model) {
		model.addAttribute("books", bookService.getBooksByEditionId(editionId, includeDeleted));
		model.addAttribute("includeDeleted", includeDeleted);
		model.addAttribute("editionTitle", editionTitle);
		return "fragments/popup :: bookCopiesList";
	}
}