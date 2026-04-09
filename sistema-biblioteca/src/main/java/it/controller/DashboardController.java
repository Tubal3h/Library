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
import it.service.RentService;
import it.service.UserService;

/**
 * Controller per la gestione della dashboard principale dell'applicazione.
 * Gestisce la visualizzazione delle diverse sezioni (home, utenti, catalogo, noleggi).
 */
@Controller
public class DashboardController {
    
    private final UserService userService;
    private final BookService bookService;
    private final RentService rentService;

    /**
     * Costruttore per DashboardController.
     * 
     * @param userService Servizio per la gestione degli utenti
     * @param bookService Servizio per la gestione dei libri
     * @param rentService Servizio per la gestione dei noleggi
     */
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
        
        if ("home".equals(section) && "role_user".equals(user.getUserRole())) {
            int totalRents = rentService.getTotalRentsByUserId(user.getUserId());

            model.addAttribute("totalRents", totalRents);
        }

        if("home".equals(section) && "role_admin".equals(user.getUserRole())) {
            int totalUsers = userService.getTotalUsers();
            int totalBooks = bookService.getTotalBooks();
            int totalRents = rentService.getTotalRents();

            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalBooks", totalBooks);
            model.addAttribute("totalRents", totalRents);
        }

        if ("users".equals(section) && "role_admin".equals(user.getUserRole())) {
            List<UserDto> users = userService.getAllUsers();
            model.addAttribute("users", users);
        }

        if ("catalog".equals(section)) {
            List<BookCatalogDto> books = bookService.getAllBooks(user.getUserRole());
            model.addAttribute("books", books);
        }

        if (("rents".equals(section) || "popup".equals(section)) && "role_user".equals(user.getUserRole())) {
            List<RentDto> rentedBooks = rentService.getRentedBooksByUserId(user.getUserId());
            model.addAttribute("rentedBooks", rentedBooks);
        }

        return "dashboard";
    }   
        
    /**
     * Endpoint API placeholder per il recupero di dettagli (non ancora implementato).
     * 
     * @param email Parametro opzionale per l'email
     * @return Una stringa vuota
     */
    @GetMapping("/api/details")
    public String getMethodName(@RequestParam(value = "email", required = false) String email) {
        return "";
    }
}


