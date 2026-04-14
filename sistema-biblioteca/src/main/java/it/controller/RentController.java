package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.dto.RentDto;
import it.dto.UserDto;
import it.service.RentService;
import it.service.UserService;

/**
 * Controller per la gestione delle operazioni di noleggio dei libri.
 * Espone gli endpoint per il prestito e la restituzione dei libri.
 */
@Controller
public class RentController {

    private final UserService userService;
    private final RentService rentService;

    /**
     * Costruttore per RentController.
     *
     * @param userService Servizio per la gestione degli utenti
     * @param rentService Servizio per la gestione dei noleggi
     */
    public RentController(UserService userService, RentService rentService) {
        this.userService = userService;
        this.rentService = rentService;
    }

    /**
     * Gestisce la richiesta di prestito di un libro da parte di un utente.
     * Valida i parametri, costruisce il DTO con i dati corretti e delega
     * la creazione del noleggio al {@link it.service.RentService}.
     *
     * @param email  Email dell'utente che effettua il noleggio
     * @param bookId ID del libro da noleggiare (stringa numerica)
     * @return Redirect alla sezione noleggi in caso di successo, o redirect al catalogo con errore
     */
    @GetMapping("/api/borrow")
    public String borrowBook(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "bookId", required = false) String bookId) {

        if (email == null || email.isEmpty()) {
            return "redirect:/";
        }

        UserDto user = userService.getUserByEmail(email);

        if (user == null) {
            return "redirect:/";
        }
        if (bookId == null || bookId.isEmpty()) {
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog";
        }

        int parsedBookId;
        try {
            parsedBookId = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            System.out.println("Errore: bookId non valido - " + bookId);
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog&error=invalid_id";
        }

        RentDto rental = new RentDto();
        rental.setUserId(user.getUserId());
        rental.setBookId(parsedBookId);

        try {
            rentService.createRental(rental);
        } catch (Exception e) {
            System.out.println("Errore: impossibile noleggiare il libro - " + bookId);
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog&error=rental_failed";
        }

        return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=rents";
    }

    /**
     * Gestisce la restituzione di un libro precedentemente noleggiato.
     *
     * @param email  Email dell'utente che restituisce il libro
     * @param bookID ID del libro restituito
     * @param rentID ID del record di noleggio da chiudere
     * @return Redirect alla sezione noleggi in caso di successo, o redirect alla sezione 404 in caso di errore
     */
    @GetMapping("/api/delivered")
    public String deliveredBook(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "bookID", required = false) String bookID,
            @RequestParam(value = "rentID", required = false) String rentID) {

        if (email == null || email.isEmpty()) {
            return "redirect:/";
        }

        UserDto user = userService.getUserByEmail(email);

        if (user == null) {
            return "redirect:/";
        }
        if (bookID == null || bookID.isEmpty()) {
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog";
        }

        try {
            rentService.updateStatus(Integer.parseInt(bookID), Integer.parseInt(rentID));
        } catch (NumberFormatException e) {
            System.out.println("Errore: bookId non valido - " + bookID);
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=404";
        } catch (Exception e) {
            System.out.println("Errore: impossibile registrare la restituzione del libro - " + bookID);
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=404";
        }

        return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=rents";
    }
}
