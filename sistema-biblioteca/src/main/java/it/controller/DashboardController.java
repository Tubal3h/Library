package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dto.RentalRecordDto;
import it.dto.BookDto;
import it.dto.EditionDto;
import it.dto.UserDto;
import it.dto.request.AuthDto;
import it.entity.configuration.UserSession;
import it.service.BookService;
import it.service.RentService;
import it.service.UserService;
import it.service.AuthorService;
import it.service.CategoryService;
import it.service.PublisherService;
import it.service.EditionService;

/**
 * Controller per la gestione della dashboard principale dell'applicazione.
 * Gestisce la visualizzazione delle diverse sezioni (home, utenti, catalogo,
 * noleggi).
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
    private final UserSession userSession;

    /**
     * Costruttore per DashboardController.
     *
     * @param userService      Servizio per la gestione degli utenti
     * @param bookService      Servizio per la gestione dei libri
     * @param rentService      Servizio per la gestione dei noleggi
     * @param authorService    Servizio per la gestione degli autori
     * @param categoryService  Servizio per la gestione delle categorie
     * @param publisherService Servizio per la gestione degli editori
     * @param editionService   Servizio per la gestione delle edizioni
     */
    public DashboardController(UserService userService, BookService bookService, RentService rentService,
            AuthorService authorService, CategoryService categoryService, PublisherService publisherService,
            EditionService editionService, UserSession userSession) {
        this.userService = userService;
        this.bookService = bookService;
        this.rentService = rentService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
        this.editionService = editionService;
        this.userSession = userSession;
    }

    /**
     * Gestisce la visualizzazione della dashboard e delle sue sezioni.
     * In base al ruolo dell'utente e alla sezione richiesta, carica i dati
     * appropriati nel modello.
     *
     * @param email   Email dell'utente loggato
     * @param section Sezione della dashboard da visualizzare (home, users, catalog,
     *                rents)
     * @param model   il modello per la vista
     * @return Nome della vista della dashboard, o redirect alla home se l'email
     *         manca o l'utente non esiste
     * @throws Exception se si verifica un errore durante il caricamento dei dati
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model,
            @RequestParam(value = "editionId", required = false) Integer editionId,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "includeDeleted", defaultValue = "false") boolean includeDeleted,
            @RequestParam(value = "search", required = false) String search,
            RedirectAttributes redirectAttributes) {
        AuthDto user = userSession.getUser();
        if (user == null) {
            return "redirect:/";
        }
        String section = userSession.getSection();
        if (section == null || section.isEmpty()) {
            section = "home";
        }

        // Se non passato come parametro, controlla i flash attributes
        if (search == null) {
            search = (String) model.asMap().get("search");
        }

        // Se la ricerca è vuota o contiene solo spazi, la consideriamo null per resettare i filtri
        if (search != null && search.trim().isEmpty()) {
            search = null;
        }

        model.addAttribute("user", user);
        model.addAttribute("section", section);
        model.addAttribute("search", search);

        try {
            if ("home".equals(section)) {
                if ("role_user".equals(user.getUserDto().getUserRole())) {
                    model.addAttribute("totalRents", rentService.getTotalRentsByUserId(user.getUserDto().getUserId()));
                    model.addAttribute("totalBooksBooked", rentService.getTotalBooksBookedByUserId(user.getUserDto().getUserId()));
                } else if ("role_admin".equals(user.getUserDto().getUserRole())) {
                    model.addAttribute("totalUsers", userService.getTotalUsers());
                    model.addAttribute("totalBooks", bookService.getTotalNotElimatedBooks());
                    model.addAttribute("totalRents", rentService.getTotalRents());
                    model.addAttribute("totalBooksBooked", rentService.getTotalBooksBooked());
                }
            }

            if ("users".equals(section) && "role_admin".equals(user.getUserDto().getUserRole())) {
                model.addAttribute("users", userService.getUserListByName(search));
            }

            if ("catalog".equals(section)) {

                model.addAttribute("books", bookService.getBookListByName(search, user.getUserDto().getUserRole()));

            }

            if ("rents".equals(section)) {
                List<RentalRecordDto> rentedBooks = rentService.getBookListByName(search, user.getUserDto().getUserRole(), user.getUserDto().getUserId());
                model.addAttribute("rentedBooks", rentedBooks);
            }

            if ("edition".equals(section) && "role_admin".equals(user.getUserDto().getUserRole())) {
                try {
                    model.addAttribute("editions", editionService.getEditionListByName(search));
                } catch (RuntimeException e) {
                    
                }
            }

            if (("catalog".equals(section) || "edition".equals(section) || "settings".equals(section))
                    && "role_admin".equals(user.getUserDto().getUserRole())) {
                model.addAttribute("authors", authorService.getAllAuthors());
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("publishers", publisherService.getAllPublishers());
                model.addAttribute("bookNames", bookService.getAllBookNames());
            }

            // Sezione Registri (per Libro o per Utente)
            if (("bookRecords".equals(section) || "userRecords".equals(section))
                    && "role_admin".equals(user.getUserDto().getUserRole())) {
                if ("bookRecords".equals(section)) {
                    Object bookIdObj = model.asMap().get("bookId");
                    Integer bookId = null;
                    if (bookIdObj instanceof Integer) {
                        bookId = (Integer) bookIdObj;
                    } else if (bookIdObj instanceof String) {
                        bookId = Integer.parseInt((String) bookIdObj);
                    }


                    if (bookId != null && bookId != 0) {
                        List<RentalRecordDto> records = rentService.getBookRecords(bookId);
                        model.addAttribute("bookRecords", records);
                        
                        BookDto bookDto = new BookDto();
                        bookDto.setBookId(bookId);

                        // Titolo dinamico per il registro del libro
                        try {
                            bookDto = bookService.getBookById(bookId);
                            model.addAttribute("targetRecordName", "Registro: " + bookDto.getEditionDto().getBookNameDto().getTitle() + " #" + bookId);
                        } catch (Exception e) {
                            model.addAttribute("targetRecordName", "Registro Libro #" + bookId);
                        }
                    }
                } else {
                    Object userIdObj = model.asMap().get("userId");
                    Integer userId = null;
                    if (userIdObj instanceof Integer) {
                        userId = (Integer) userIdObj;
                    } else if (userIdObj instanceof String) {
                        userId = Integer.parseInt((String) userIdObj);
                    }

                    if (userId == null || userId == 0) {
                        userId = userSession.getRecordUserId();
                    }

                    if (userId != null && userId != 0) {
                        List<RentalRecordDto> records = rentService.getUserRecords(userId);
                        System.out.println("[DEBUG] DashboardController - Record trovati: "
                                + (records != null ? records.size() : "NULL"));
                        model.addAttribute("bookRecords", records);

                        // Titolo dinamico per il registro dell'utente
                        UserDto targetUser = userService.getUserById(userId);
                        if (targetUser != null) {
                            model.addAttribute("targetRecordName",
                                    "Dipendente: " + targetUser.getUserName() + " " + targetUser.getUserLastName());
                        }
                    }
                }
            }

            // Gestione Popup Visualizzazione Copie (Server-Side)
            if ("viewCopies".equals(action) && editionId != null) {
                EditionDto editionDto = new EditionDto();
                editionDto.setEditionId(editionId);

                List<RentalRecordDto> popupBooks = bookService.getBooksByEditionId(editionDto.getEditionId(), includeDeleted);
                model.addAttribute("popupBooks", popupBooks);
                model.addAttribute("showCopiesPopup", true);
                model.addAttribute("popupEditionId", editionId);
                model.addAttribute("popupIncludeDeleted", includeDeleted);

                if (!popupBooks.isEmpty()) {
                    model.addAttribute("popupEditionTitle", popupBooks.get(0).getBookDto().getEditionDto().getBookNameDto().getTitle());
                } else {
                    model.addAttribute("popupEditionTitle", "Edizione #" + editionId);
                }
            }
        } catch (Exception e) {
            System.out.println("Errore di caricamento db: " + e.getMessage());
            model.addAttribute("errorMessage", "Servizio momentaneamente non disponibile.");
            return "redirect:/?error=service_unavailable";
        }

        return "dashboard";
    }
}
