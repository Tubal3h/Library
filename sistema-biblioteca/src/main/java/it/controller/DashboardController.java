package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.dto.RentDto;
import it.dto.UserDto;
import it.service.BookService;
import it.service.RentService;
import it.service.UserService;
import jakarta.servlet.http.HttpSession;
import it.service.AuthorService;
import it.service.CategoryService;
import it.service.PublisherService;
import it.service.EditionService;

/**
 * Controller per la gestione della dashboard principale dell'applicazione.
 * Gestisce la visualizzazione delle diverse sezioni (home, utenti, catalogo, noleggi).
 */
@Controller
public class DashboardController {

    private final UserService userService;
    private final BookService bookService;
    private final RentService rentService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;
    private final EditionService editionService;

    /**
     * Costruttore per DashboardController.
     *
     * @param userService Servizio per la gestione degli utenti
     * @param bookService Servizio per la gestione dei libri
     * @param rentService Servizio per la gestione dei noleggi
     * @param authorService Servizio per la gestione degli autori
     * @param categoryService Servizio per la gestione delle categorie
     * @param publisherService Servizio per la gestione degli editori
     * @param editionService Servizio per la gestione delle edizioni
     */
    public DashboardController(UserService userService, BookService bookService, RentService rentService, AuthorService authorService, CategoryService categoryService, PublisherService publisherService, EditionService editionService) {
        this.userService = userService;
        this.bookService = bookService;
        this.rentService = rentService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
        this.editionService = editionService;
    }

    /**
     * Gestisce la visualizzazione della dashboard e delle sue sezioni.
     * In base al ruolo dell'utente e alla sezione richiesta, carica i dati appropriati nel modello.
     *
     * @param email   Email dell'utente loggato
     * @param section Sezione della dashboard da visualizzare (home, users, catalog, rents)
     * @param model   il modello per la vista
     * @return Nome della vista della dashboard, o redirect alla home se l'email manca o l'utente non esiste
     * @throws Exception se si verifica un errore durante il caricamento dei dati
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session,Model model
    ) {
        UserDto user = (UserDto) session.getAttribute("user");
        String section = (String) session.getAttribute("section");
        if(section == null || section.isEmpty()) {
            section = "home";
        }
        String search = (String) session.getAttribute("search");
        if (user == null) {
            return "redirect:/";
        }


        model.addAttribute("user", user);
        model.addAttribute("section", section);
        model.addAttribute("search", search);

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
                model.addAttribute("users", userService.getUserListByName(search));
            }

            if ("catalog".equals(section)) {
                
                model.addAttribute("books", bookService.getBookListByName(search,user.getUserRole()));

            }

            if ("rents".equals(section)) {
                List<RentDto> rentedBooks = rentService.getBookListByName(search, user.getUserRole(), user.getUserId());
                model.addAttribute("rentedBooks", rentedBooks);
            }

            if ("edition".equals(section) && "role_admin".equals(user.getUserRole())) {
                model.addAttribute("editions", editionService.getEditionListByName(search));
                model.addAttribute("authors", authorService.getAllAuthors());
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("publishers", publisherService.getAllPublishers());
            }

        } catch (Exception e) {
            System.out.println("Errore di caricamento db: " + e.getMessage());
            model.addAttribute("errorMessage", "Servizio momentaneamente non disponibile.");
            return "redirect:/?error=service_unavailable";
        }

        return "dashboard";
    }
}
