package it.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dto.BookDto;
import it.dto.UserDto;
import it.exception.NoBookIdFoundException;
import it.exception.NoIsbnFoundException;
import it.service.BookService;
import it.service.UserService;

@Controller
public class BookController {
	
	private final BookService bookService;
	private final UserService userService;
	
	public BookController(BookService bookService, UserService userService) {
		this.bookService = bookService;
		this.userService = userService;
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
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "isbn", required = false) String isbn,
			@RequestParam(value = "bookName", required = false) String bookName,
			RedirectAttributes redirectAttributes) {
		UserDto user = userService.getUserByEmail(email);
		if (user == null) {
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
		return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=edition";
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
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "bookId", required = false) Integer bookId,
			@RequestParam(value = "bookName", required = false) String bookName,
			RedirectAttributes redirectAttributes) {
		UserDto user = userService.getUserByEmail(email);
		if (user == null) {
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
		return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog";
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
			RedirectAttributes redirectAttributes) {
		// Ora 'date' è un oggetto LocalDate pronto all'uso
		System.out.println("Data ricevuta: " + date);
		System.out.println("Titolo: " + title);
		System.out.println("ISBN: " + isbn);
		System.out.println("Autore ID: " + authorId);
		System.out.println("Categoria ID: " + categoryId);
		System.out.println("Editore ID: " + publisherId);
		System.out.println("Email: " + email);

		try {
			bookService.insertBook(title, authorId, publisherId, date, categoryId, isbn);
			redirectAttributes.addFlashAttribute("popupType", "addEdition");
			redirectAttributes.addFlashAttribute("popupBookTitle", title);
			redirectAttributes.addFlashAttribute("popupBookIsbn", isbn);
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("popupType", "error");
			redirectAttributes.addFlashAttribute("popupErrorMessage", "Impossibile aggiungere l'edizione.");
		}

		return "redirect:/dashboard?email=" + email + "&section=edition";
	}
}
