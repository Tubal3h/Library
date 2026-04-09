package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.dto.BookCatalogDto;
import it.dto.RentDto;
import it.dto.UserDto;
import it.service.BookService;
import it.service.UserService;
import it.service.RentService;

@Controller
public class DashboardController {
    
    private final UserService userService;
    private final BookService bookService;
    private final RentService rentService;

    public DashboardController(UserService userService, BookService bookService, RentService rentService) {
        this.userService = userService;
        this.bookService = bookService;
        this.rentService = rentService;
    }

    /**
     * Gestisce la visualizzazione della dashboard e delle sue sezioni.
     * 
     * @param email Email dell'utente loggato
     * @param section Sezione della dashboard da visualizzare
     * @param model il modello per la vista
     * @return Nome della vista della dashboard o redirect alla home se l'email manca o l'utente non esiste
     */
    @GetMapping("/dashboard")
    public String dashboard(
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "section", defaultValue = "home") String section,
        Model model
    ) {
        if (email == null || email.isEmpty()) {
            return "redirect:/";
        }

        UserDto user = userService.getUserByEmail(email);

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        model.addAttribute("section", section);

        if (section.equals("users") && user.getUserRole().equals("role_admin")) {
            List<UserDto> users = userService.getAllUsers();
            model.addAttribute("users", users);
        }

        if(section.equals("catalog")) {
            List<BookCatalogDto> books = bookService.getAllBooks(user.getUserRole());
            System.out.println("Books: " + books);
            model.addAttribute("books", books);
        }

        if (section.equals("rents") && user.getUserRole().equals("role_user")) {
            List<RentDto> rentedBooks = rentService.getRentedBooksByUserId(user.getUserId());
            System.out.println("Rented Books: " + rentedBooks);
            model.addAttribute("rentedBooks", rentedBooks);
        }

        if (section.equals("popup") && user.getUserRole().equals("role_user")) {
            List<RentDto> rentedBooks = rentService.getRentedBooksByUserId(user.getUserId());
            System.out.println("Rented Books: " + rentedBooks);
            model.addAttribute("rentedBooks", rentedBooks);
        }

        return "dashboard";
        }   
        
    /**
     * @param email Parametro opzionale per l'email
     * @return Una nuova stringa vuota (Metodo placeholder)
     */
    @GetMapping("/api/details")
    public String getMethodName(@RequestParam(value = "email", required = false) String email) {

        return new String();
    }
    
}

