package it.controller;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.dto.BookDto;
import it.dto.UserDto;
import it.entity.Book;
import it.entity.User;
import it.exception.NoIsbnFoundException;
import it.service.BookService;
import it.service.UserService;

@Controller
public class AdminController {
	
	private final BookService bookService;
	private final UserService userService;
	public AdminController(BookService bookService, UserService userService) {
		this.bookService = bookService;
		this.userService = userService;
	}
	
	
	@GetMapping("/api/addBook")
	public String addBookone(
			@RequestParam(value = "email",required = false) String email, 
			@RequestParam(value = "isbn", required = false) String isbn,
			Model model) {
		UserDto user = userService.getUserByEmail(email);
		int res = 0;
		System.out.println(isbn + " " + email);
        if (user == null) {
            return "redirect:/";
        }
        	try {
				BookDto bookDto= new BookDto(isbn);
				res = bookService.addBook(bookDto.getIsbnCode());
				model.addAttribute("addBook", "hai inserito" + " " + res + " libro" );
				System.out.println("test user e isbn" + user.getUserEmail() +"&"+ bookDto.getIsbnCode());
			}catch(NoIsbnFoundException ex) {
				model.addAttribute("insertFallitaException", ex.ToString());
				
			}		
		return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog";
	}
	
}
