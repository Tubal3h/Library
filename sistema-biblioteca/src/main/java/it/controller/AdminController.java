package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	 * Gestisce l'aggiunta di un nuovo libro tramite il suo codice ISBN.
	 * 
	 * @param email Email dell'amministratore che esegue l'operazione
	 * @param isbn Codice ISBN della nuova edizione da inserire
	 * @param model Il modello per la vista
	 * @return Redirect alla sezione delle edizioni o alla home
	 */
	@GetMapping("/api/addBook")
	public String addBook(
			@RequestParam(value = "email",required = false) String email, 
			@RequestParam(value = "isbn", required = false) String isbn,
			Model model) {
		UserDto user = userService.getUserByEmail(email);
		int res = 0;
        if (user == null) {
            return "redirect:/";
        }
        	try {
				BookDto bookDto= new BookDto(isbn);
				res = bookService.addBook(bookDto.getIsbnCode());
				model.addAttribute("addBook", "hai inserito" + " " + res + " libro" );
			}catch(NoIsbnFoundException ex) {
				model.addAttribute("insertFallitaException", ex.ToString());
				
			}		
		return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=edition";
	}
	
	/**
	 * Gestisce l'eliminazione (logica) di un libro tramite il suo ID.
	 * 
	 * @param email Email dell'amministratore che esegue l'operazione
	 * @param bookId ID del libro da eliminare
	 * @param model Il modello per la vista
	 * @return Redirect alla sezione catalogo o alla home
	 */
	@GetMapping("api/deleteBook")
	public String deleteBook(@RequestParam(value = "email", required = false) String email, 
							 @RequestParam(value = "bookId", required = false) Integer bookId, 
							 Model model ) {
		UserDto user = userService.getUserByEmail(email);
		int res = 0;
		if(user == null) {
			return "redirect:/";
		}
		
		try {
			BookDto bookDto = new BookDto(bookId);
			res = bookService.deleteBook(bookDto.getBookId());
			model.addAttribute("eliminazioneConSuccesso", "hai eliminato il libro con questo id: " + bookId + " libro");
		}catch(NoBookIdFoundException ex){
			model.addAttribute("idNonTrovato", ex.toString());
		}
		
		return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog";
	}
}
