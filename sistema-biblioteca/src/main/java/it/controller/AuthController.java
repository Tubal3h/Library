package it.controller;

/* -------------------------------------------------------------------------- */
/*                                 CONTROLLER                                 */
/* -------------------------------------------------------------------------- */

import it.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.configuration.UserSession;
import it.dto.LoginDto;
import it.dto.UserDto;

/**
 * Controller per la gestione dell'autenticazione degli utenti (login e logout).
 */
@Controller
public class AuthController {

    private final UserService userService;
    private final UserSession userSession;

    /**
     * Costruttore per AuthController.
     *
     * @param userService Servizio per la gestione degli utenti
     * @param userSession Componente per la gestione della sessione utente
     */
    public AuthController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
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
     * @param model    il modello per la vista
     * @return Redirect alla dashboard in caso di successo, o ritorno alla pagina di login in caso di errore
     */
    @PostMapping("/api/login")
    public String login(@ModelAttribute LoginDto loginDto, Model model) {
    	UserDto user = null;
    	if(loginDto.getEmail() != null && !loginDto.getEmail().isBlank()) {
    		if(loginDto.getPassword() != null && !loginDto.getPassword().isBlank()) {
    			user = userService.getUserByEmail(loginDto.getEmail());
    			
    		}else {
    			return "redirect:/?error=invalid_credentials";
    		}
    	}else {
    		return "redirect:/?error=invalid_credentials";
		}
    	
    	if(user != null) {
    		userSession.setUser(user);
    		userSession.setSection("home"); 		
    		return "redirect:/dashboard";
    	}else {
    		return "redirect:/";
    	}
    }

    @PostMapping("/api/logout")
    public String logout() {
    	
        userSession.logout();
    	return "redirect:/";
    }   
}
