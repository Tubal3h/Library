package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.dto.LoginDto;
import it.entity.User;
import it.service.AuthService;

/**
 * Controller per la gestione dell'autenticazione degli utenti (login e logout).
 */
@Controller
public class AuthController {

    private final AuthService authService;

    /**
     * Costruttore per AuthController.
     * 
     * @param authService Servizio per la gestione dell'autenticazione
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Mostra la pagina di login.
     * 
     * @param model il modello per la vista
     * @return Nome della vista del login ("index")
     */
    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "index";
    }

    /**
     * Gestisce il processo di login.
     * 
     * @param loginDto DTO con le credenziali di accesso
     * @param model il modello per la vista
     * @return Redirect alla dashboard in caso di successo, o ritorno alla pagina di login in caso di errore
     */
    @PostMapping("/api/login")
    public String login(LoginDto loginDto, Model model) {
        User user = authService.login(loginDto);
        System.out.println(user);
        if (user == null) {
            model.addAttribute("error", "Email o password errati");
            return "index";
        }
        
        // Passiamo l'email nell'URL come parametro base GET (es: /dashboard?email=mario@...)
        // Questo elude la necessità della Sessione!
        return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=home"; 
    }
    
    /**
     * Gestisce il logout dell'utente.
     * 
     * @return Redirect alla pagina di login ("/")
     */
    @PostMapping("/api/logout")
    public String logout() {
        // In un'applicazione reale, qui si invaliderebbe la sessione o si rimuoverebbe il token di autenticazione.
        return "redirect:/";
    }
}


