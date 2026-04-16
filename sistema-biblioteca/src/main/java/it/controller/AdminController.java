package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dto.BookDto;
import it.dto.UserDto;
import it.exception.NoBookIdFoundException;
import it.exception.NoIsbnFoundException;
import it.service.BookService;
import it.service.UserService;

/**
 * Controller per le operazioni amministrative avanzate.
 * Gestisce l'inserimento e la rimozione (logica) dei libri dal sistema.
 */
@Controller
public class AdminController {
	
	private final BookService bookService;
	private final UserService userService;

	/**
	 * Costruttore per AdminController.
	 * 
	 * @param bookService Servizio per la gestione dei libri
	 * @param userService Servizio per la gestione degli utenti
	 */
	public AdminController(BookService bookService, UserService userService) {
		this.bookService = bookService;
		this.userService = userService;
	}
	
	/**
	 * Gestisce l'aggiunta di una nuova copia fisica tramite il suo codice ISBN.
	 *
	 * @param email Email dell'amministratore che esegue l'operazione
	 * @param isbn Codice ISBN dell'edizione da inserire
	 * @param redirectAttributes Attributi di redirect per passare messaggi alla vista
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
			redirectAttributes.addFlashAttribute("popupErrorMessage", ex.getMessage());
		}
		return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=edition";
	}
	
	/**
	 * Gestisce l'eliminazione (logica) di una copia fisica tramite il suo ID.
	 *
	 * @param email Email dell'amministratore che esegue l'operazione
	 * @param bookId ID della copia da eliminare
	 * @param redirectAttributes Attributi di redirect per passare messaggi alla vista
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
}
