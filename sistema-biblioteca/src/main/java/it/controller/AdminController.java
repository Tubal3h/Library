package it.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.dto.BookDto;
import it.entity.Book;
import it.exception.NoIsbnFoundException;
import it.service.BookService;

@Controller
public class AdminController {
	
	private final BookService bookService;
	public AdminController(BookService bookService) {
		this.bookService = bookService;
	}
	
	
	@PostMapping("/admin/gestione/aggiungi")
	public String addBook(@RequestParam String username, 
						  @RequestParam String role,
						  @RequestParam String azione,
						  @RequestParam String isbn,
						  Model model) {
		
		
		int res = 0;
		model.addAttribute("username", username);
		model.addAttribute("role", role);
		System.out.println(username);
		if(azione.equals("aggiungi")) {
			try {
				BookDto bookDto= new BookDto(isbn);
				res = bookService.addBook(bookDto.getIsbnCode());
				model.addAttribute("addBook", "hai inserito" + " " + res + " libro" );
				return "layout";
			}catch(NoIsbnFoundException ex) {
				model.addAttribute("insertFallitaException", ex.ToString());
				return "layout";
			}
		}	
		return "layout";
	}
	
}
