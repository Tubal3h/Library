package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.configuration.UserSession;
import it.dto.UserDto;

@Controller
public class FeaturesController {
    private final UserSession userSession;

    public FeaturesController(UserSession userSession) {
        this.userSession = userSession;
    }
    /**
     * Gestisce la richiesta di ricerca.
     * Reindirizza l'utente alla dashboard con il parametro di ricerca incluso nell'URL.
     *
     * @param email   Email dell'utente loggato
     * @param section Sezione in cui si sta effettuando la ricerca
     * @param search  Stringa di ricerca immessa dall'utente
     * @param model   il modello per la vista
     * @return Redirect alla dashboard con i parametri aggiornati
     */
    @GetMapping("/api/search/{search}")
    public String search(
        @PathVariable(value = "search", required = false) String search,
        RedirectAttributes redirectAttributes) {
        UserDto user = userSession.getUser();
        if (user == null) {
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("search",search);
        return "redirect:/dashboard";
    }

    @GetMapping("/api/navigation/{section}")
    public String navigate(
        @PathVariable(value = "section") String section,
        Model model) {
        UserDto user = userSession.getUser();
        System.out.println("Section: " + section);
        if (user == null) {
            return "redirect:/";
        }
        if (section == null || section.isEmpty()) {
            return "redirect:/dashboard";
        }
        if (user.getUserRole().equals("role_user")) {
            if (section.equals("users") || section.equals("edition")) {
                section = "home";
            }
        }

        userSession.setSection(section);
        return "redirect:/dashboard";
    }
}
