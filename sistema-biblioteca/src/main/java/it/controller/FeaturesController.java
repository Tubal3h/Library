package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.dto.UserDto;
import jakarta.servlet.http.HttpSession;

@Controller
public class FeaturesController {
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
    @GetMapping("/api/search")
    public String search(
        @RequestParam(value = "search", required = false) String search,
        HttpSession session,Model model) {
        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        session.setAttribute("search",search);
        return "redirect:/dashboard";
    }
}
