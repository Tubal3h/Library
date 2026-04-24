package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dto.RentDto;
import it.dto.UserDto;
import it.component.UserSession;
import it.service.RentService;

/**
 * Controller per la gestione delle operazioni di noleggio dei libri.
 * Espone gli endpoint per il prestito e la restituzione dei libri.
 */
@Controller
public class RentController {

    private final RentService rentService;
    private final UserSession userSession;

    /**
     * Costruttore per RentController.
     *
     * @param rentService Servizio per la gestione dei noleggi
     * @param userSession Componente per la gestione della sessione utente
     */
    public RentController(RentService rentService, UserSession userSession) {
        this.rentService = rentService;
        this.userSession = userSession;
    }

    /**
     * Inserire forse id utente come parametro dato che la conferma la faccio dalla parte admin
     * Gestisce la richiesta di prestito di un libro da parte di un utente.
     * Valida i parametri, costruisce il DTO con i dati corretti e delega
     * la creazione del noleggio al {@link it.service.RentService}.
     *
     * @param email  Email dell'utente che effettua il noleggio
     * @param bookId ID del libro da noleggiare (stringa numerica)
     * @return Redirect alla sezione noleggi in caso di successo, o redirect al catalogo con errore
     */
    @GetMapping("/api/borrow")
    public String borrowBook(@RequestParam(value = "bookId", required = false) String bookId, 
    						 @RequestParam(value = "userId", required = false) String userId,
    						 Boolean confirmed, 
    						 RedirectAttributes redirectAttributes) {
                
    	int parsedBookId;
    	int parsedUserId;
    	UserDto user = userSession.getUser();
    	if(bookId == null || bookId.isEmpty()) {
    		return "redirect:/dashboard";
    	}
     
        try {
            parsedBookId = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            System.out.println("Errore: bookId non valido - " + bookId);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "ID non valido.");
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog&error=invalid_id";
        }
        
        try {
            parsedUserId = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            System.out.println("Errore: user non valido - " + userId);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "ID non valido.");
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog&error=invalid_id";
        }
        
        RentDto rent = new RentDto();
        rent.setBookId(parsedBookId);
        rent.setUserId(parsedUserId);
        
        try {
        	rentService.createRental(rent, confirmed);
        	redirectAttributes.addFlashAttribute("popupType", "rental");
        	redirectAttributes.addFlashAttribute("popupBookId", bookId);
        	redirectAttributes.addFlashAttribute("popupConfirmed", confirmed ? "prestito accettato" : "prestito rifiutato");
        	
        }catch(Exception e) {
            System.out.println("Errore: impossibile creare un noleggio - " + bookId);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Errore in prenotazione.");
            userSession.setSection("404");
            return "redirect:/dashboard";
        }
    	
        return "redirect:/dashboard";
    }
    
    
    @GetMapping("/api/booked")
    public String bookedBook(
            @RequestParam(value = "bookId", required = false) String bookId,
            RedirectAttributes redirectAttributes
        ) {
        UserDto user = userSession.getUser();
        userSession.setSection("rents");  
        if (user == null) {
            return "redirect:/";
        }

        if (bookId == null || bookId.isEmpty()) {
            return "redirect:/dashboard";
        }
        
        int parsedBookId;
        try {
            parsedBookId = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            System.out.println("Errore: bookId non valido - " + bookId);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "ID non valido.");
            return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=catalog&error=invalid_id";
        }

        RentDto rental = new RentDto();
        rental.setUserId(user.getUserId());
        rental.setBookId(parsedBookId);

        try {
            rentService.createBookedDate(rental);
            redirectAttributes.addFlashAttribute("popupType", "booked");
            redirectAttributes.addFlashAttribute("popupBookId", bookId);
        } catch (Exception e) {
            System.out.println("Errore: impossibile noleggiare il libro - " + bookId);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Errore in prenotazione.");
            userSession.setSection("404");
            return "redirect:/dashboard";
        }

        return "redirect:/dashboard";
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
            @RequestParam(value = "bookID", required = false) String bookID,
            @RequestParam(value = "rentID", required = false) String rentID,
            @RequestParam(value = "bookTitle", required = false) String bookTitle,
            RedirectAttributes redirectAttributes
        ) {
        UserDto user = userSession.getUser();
        userSession.setSection("rents");  

        if (user == null) {
            return "redirect:/";
        }

        if (bookID == null || bookID.isEmpty() || rentID == null || rentID.isEmpty()) {
            return "redirect:/dashboard";
        }

        try {
            rentService.updateStatus(Integer.parseInt(bookID), Integer.parseInt(rentID));
            redirectAttributes.addFlashAttribute("popupType", "delivered");
            redirectAttributes.addFlashAttribute("popupBookId", bookID);
            redirectAttributes.addFlashAttribute("popupBookTitle", bookTitle != null ? bookTitle : "");
        } catch (NumberFormatException e) {
            System.out.println("Errore: bookId non valido - " + bookID);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "ID non valido.");
            userSession.setSection("404");
            return "redirect:/dashboard";
        } catch (Exception e) {
            System.out.println("Errore: impossibile registrare la restituzione del libro - " + bookID);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Errore restituzione.");
            userSession.setSection("404");
            return "redirect:/dashboard";
        }

        return "redirect:/dashboard";
    }

    /**
     * Gestisce la restituzione effettiva del libro (in prestito -> disponibile).
     *
     * @param bookID ID del libro restituito
     * @param rentID ID del record di noleggio da chiudere
     * @param bookTitle Titolo del libro
     * @param redirectAttributes Attributi per il redirect
     * @return Redirect alla dashboard
     */
    @GetMapping("/api/returned")
    public String returnedBook(
            @RequestParam(value = "bookID", required = false) String bookID,
            @RequestParam(value = "rentID", required = false) String rentID,
            @RequestParam(value = "bookTitle", required = false) String bookTitle,
            RedirectAttributes redirectAttributes
        ) {
        UserDto user = userSession.getUser();
        userSession.setSection("rents");

        if (user == null) {
            return "redirect:/";
        }

        if (bookID == null || bookID.isEmpty() || rentID == null || rentID.isEmpty()) {
            return "redirect:/dashboard";
        }

        try {
            rentService.updateStatus(Integer.parseInt(bookID), Integer.parseInt(rentID));
            redirectAttributes.addFlashAttribute("popupType", "returned");
            redirectAttributes.addFlashAttribute("popupBookId", bookID);
            redirectAttributes.addFlashAttribute("popupBookTitle", bookTitle != null ? bookTitle : "");
        } catch (NumberFormatException e) {
            System.out.println("Errore: bookId non valido - " + bookID);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "ID non valido.");
            userSession.setSection("404");
            return "redirect:/dashboard";
        } catch (Exception e) {
            System.out.println("Errore: impossibile registrare la restituzione del libro - " + bookID);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Errore restituzione.");
            userSession.setSection("404");
            return "redirect:/dashboard";
        }

        return "redirect:/dashboard";
    }
}
