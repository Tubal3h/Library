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
	 */
	public AdminController(BookService bookService, UserService userService) {
		this.bookService = bookService;
		this.userService = userService;
	}
}
