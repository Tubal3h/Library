package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.configuration.UserSession;
import it.dto.RentalRecordDto;
import it.dto.BookDto;
import it.dto.UserDto;
import it.dto.request.AuthDto;
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
    
    @PostMapping("/api/acceptLending")
    public String acceptLengindByAdmin(
        @RequestParam(value = "bookId", required = false) String bookId,
        RedirectAttributes redirectAttributes
    ) {
    	AuthDto user = userSession.getUser();
    	userSession.setSection("rents");
    	if(user == null) {
    		return "redirect:/";
    	}
    	
    	if(bookId == null || bookId.isEmpty()) {
    		return "redirect:/dashboard";
    	}
    	redirectAttributes.addFlashAttribute("bookId", bookId);
    	try {
    		Integer.parseInt(bookId);
    	}catch(NumberFormatException ex) {
    		System.out.println("Errore: bookId non valido - " + bookId);
            return "redirect:/dashboard";
    	}
    	return null;
    }
    

    /**
     * Riceve la richiesta di prenotazione di un libro da parte di un utente e la invia al servizio
     * Valida i parametri, costruisce il DTO con i dati corretti e delega
     * la creazione del noleggio al {@link it.service.RentService}.
     * 
     * @param bookId ID del libro da prenotare (stringa numerica)
     * @param redirectAttributes Attributi per il redirect
     * @return Redirect alla sezione noleggi in caso di successo, o redirect al catalogo con errore
     */

    @GetMapping("/api/booked")
    public String bookedBook(
            @RequestParam(value = "bookId", required = false) String bookId,
            @RequestParam(value = "bookTitle", required = false) String bookTitle,
            RedirectAttributes redirectAttributes
        ) {
        AuthDto user = userSession.getUser();
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
            return "redirect:/dashboard";
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserDto().getUserId());
        
        BookDto bookDto = new BookDto();
        bookDto.setBookId(parsedBookId);
        
        RentalRecordDto rentalRecordDto = new RentalRecordDto();
        rentalRecordDto.setBookDto(bookDto);
        rentalRecordDto.setUserDto(userDto);

        try {
            rentService.createBookedDate(rentalRecordDto);
            redirectAttributes.addFlashAttribute("popupType", "booked");
            redirectAttributes.addFlashAttribute("popupBookId", bookId);
            redirectAttributes.addFlashAttribute("popupBookTitle", bookTitle != null ? bookTitle : "");

        } catch (Exception e) {
            System.out.println("Errore: impossibile noleggiare il libro - " + bookId);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Errore in prenotazione.");
            userSession.setSection("404");
            return "redirect:/dashboard";
        }

        
        return "redirect:/dashboard";
    }
    
    @GetMapping("/api/removeReservation")
    public String removeReservation(
        @RequestParam(value = "bookID", required = false) String bookID,
        @RequestParam(value = "rentID", required = false) String rentID,
        @RequestParam(value = "bookTitle", required = false) String bookTitle,
        RedirectAttributes redirectAttributes
    ) {
        AuthDto user = userSession.getUser();
        userSession.setSection("rents");  
        if (user == null) {
            return "redirect:/";
        }

        if (bookID == null || bookID.isEmpty() || rentID == null || rentID.isEmpty()) {
            return "redirect:/dashboard";
        }
        
        int parsedBookId;
        int parsedRentId;
        try {
            parsedBookId = Integer.parseInt(bookID);
            parsedRentId = Integer.parseInt(rentID);
        } catch (NumberFormatException e) {
            System.out.println("Errore: bookID non valido - " + bookID);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "ID non valido.");
            return "redirect:/dashboard";
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserDto().getUserId());
        
        BookDto bookDto = new BookDto();
        bookDto.setBookId(parsedBookId);
        
        RentalRecordDto rentalRecordDto = new RentalRecordDto();
        rentalRecordDto.setBookDto(bookDto);
        rentalRecordDto.setUserDto(userDto);

        try {
            rentService.removeReservation(parsedBookId, parsedRentId);
            redirectAttributes.addFlashAttribute("popupType", "removeReservation");
            redirectAttributes.addFlashAttribute("popupBookId", bookID);
            redirectAttributes.addFlashAttribute("popupBookTitle", bookTitle != null ? bookTitle : "");

        } catch (Exception e) {
            System.out.println("Errore: impossibile rimuovere la prenotazione - " + bookID);
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Errore in rimozione prenotazione.");
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
        AuthDto user = userSession.getUser();
        userSession.setSection("rents");  

        if (user == null) {
            return "redirect:/";
        }

        if (bookID == null || bookID.isEmpty() || rentID == null || rentID.isEmpty()) {
            return "redirect:/dashboard";
        }

        try {
            rentService.deliveredRental(Integer.parseInt(bookID), Integer.parseInt(rentID));
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
        AuthDto user = userSession.getUser();
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
