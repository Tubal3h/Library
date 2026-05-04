package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.configuration.UserSession;
import it.dto.request.AuthDto;

@Controller
public class BookRecordsController {

    private final UserSession userSession;

    public BookRecordsController(UserSession userSession) {
        this.userSession = userSession;
    }

    /**
     * Gestisce la navigazione verso la sezione "Storico Copie".
     * Imposta l'ID della copia da visualizzare nella sessione e reindirizza al dashboard.
     *
     * @param bookId ID della copia di cui visualizzare lo storico
     * @param redirectAttributes Attributi di redirect
     * @return Redirect al dashboard
     */
    @GetMapping("/api/navigation/bookRecords/{bookId}")
    public String bookRecords(
        @PathVariable(value = "bookId") int bookId, 
        RedirectAttributes redirectAttributes) {
        AuthDto user = userSession.getUser();
        if (user == null || !"role_admin".equals(user.getUserDto().getUserRole())) {
            return "redirect:/";
        }
        
        userSession.setSection("bookRecords");
        userSession.setRecordBookId(bookId);
        redirectAttributes.addFlashAttribute("bookId", bookId);
        return "redirect:/dashboard";
    }

    /**
     * Gestisce la navigazione verso la sezione "Storico Utenti".
     * Imposta l'ID dell'utente da visualizzare nella sessione e reindirizza al dashboard.
     *
     * @param userId ID dell'utente di cui visualizzare lo storico
     * @param redirectAttributes Attributi di redirect
     * @return Redirect al dashboard
     */
    @GetMapping("/api/navigation/userRecords/{userId}")
    public String userRecords(
        @PathVariable(value = "userId") int userId, 
        RedirectAttributes redirectAttributes) {
        AuthDto user = userSession.getUser();
        if (user == null || !"role_admin".equals(user.getUserDto().getUserRole())) {
            return "redirect:/";
        }
        
        userSession.setSection("userRecords");
        userSession.setRecordUserId(userId);
        redirectAttributes.addFlashAttribute("userId", userId);
        return "redirect:/dashboard";
    }
}
