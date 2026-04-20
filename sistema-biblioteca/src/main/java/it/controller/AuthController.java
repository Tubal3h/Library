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

import it.dto.LoginDto;
import it.dto.UserDto;
import jakarta.servlet.http.HttpSession;

/**
 * Controller per la gestione dell'autenticazione degli utenti (login e logout).
 */
@Controller
public class AuthController {

    private final UserService userService;

    /**
     * Costruttore per AuthController.
     *
     * @param authService Servizio per la gestione dell'autenticazione
     */
    public AuthController(UserService userService) {
		this.userService = userService;
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
    public String login(@ModelAttribute LoginDto loginDto, Model model, HttpSession session) {
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
    		session.setAttribute("user", user);
    		session.setAttribute("section", "home");
    		session.setAttribute("search", "");
    		model.addAttribute("user", user);
    		
    		
    		return "redirect:/dashboard";
    	}else {
    		return "redirect:/";
    	}
    }
/*    
    @PostMapping("/api/login")
    public String login(LoginDto loginDto, Model model) {
        UserDto user = null;
        try {
            user = authService.login(loginDto);
            if (user == null) {
                model.addAttribute("error", "Email o password errati");
                return "redirect:/?error=invalid_credentials";
            }
        } catch (Exception e) {
            System.out.println("Errore di caricamento db: " + e.getMessage());
            model.addAttribute("errorMessage", "Servizio momentaneamente non disponibile.");
            return "redirect:/?error=service_unavailable";
        }

        return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=home";
    }

*/
    /**
     * Gestisce il logout dell'utente invalidando la sessione corrente.
     *
     * @return Redirect alla pagina di login ("/")
     */
    @GetMapping("/api/logout")
    public String logout(HttpSession session) {
        session.invalidate();
    	return "redirect:/";
    }
}
