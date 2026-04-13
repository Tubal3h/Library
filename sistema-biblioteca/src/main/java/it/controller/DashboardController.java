package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     * In base al ruolo dell'utente e alla sezione richiesta, carica i dati appropriati nel modello.
     *
     * @param email   Email dell'utente loggato
     * @param section Sezione della dashboard da visualizzare (home, users, catalog, rents)
     * @param model   il modello per la vista
     * @return Nome della vista della dashboard, o redirect alla home se l'email manca o l'utente non esiste
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

        try {
            if ("home".equals(section)) {
                if ("role_user".equals(user.getUserRole())) {
                    model.addAttribute("totalRents", rentService.getTotalRentsByUserId(user.getUserId()));
                } else if ("role_admin".equals(user.getUserRole())) {
                    model.addAttribute("totalUsers", userService.getTotalUsers());
                    model.addAttribute("totalBooks", bookService.getTotalCountBooks());
                    model.addAttribute("totalRents", rentService.getTotalRents());
                }
            }

            if ("users".equals(section) && "role_admin".equals(user.getUserRole())) {
                model.addAttribute("users", userService.getAllUsers());
            }

            if ("catalog".equals(section)) {
                model.addAttribute("books", bookService.getAllBooks(user.getUserRole()));
            }

            if ("rents".equals(section)) {
                List<RentDto> rentedBooks = "role_admin".equals(user.getUserRole())
                        ? rentService.getRentedAllRents()
                        : rentService.getRentedBooksByUserId(user.getUserId());
                model.addAttribute("rentedBooks", rentedBooks);
            }

        } catch (Exception e) {
            System.out.println("Errore di caricamento db: " + e.getMessage());
            model.addAttribute("errorMessage", "Servizio momentaneamente non disponibile.");
            return "redirect:/?error=service_unavailable";
        }

        return "dashboard";
    }
}
