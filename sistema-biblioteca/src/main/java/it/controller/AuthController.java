package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.dto.LoginDto;
import it.model.User;
import it.service.AuthService;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "index";
    }

    @PostMapping("/api/login")
    public String login(@ModelAttribute("loginDto") LoginDto loginDto, Model model) {
        User user = authService.login(loginDto);

        if (user == null) {
            model.addAttribute("error", "Email o password errati");
            return "index";
        }
        
        // Passiamo l'email nell'URL come parametro base GET (es: /dashboard?email=mario@...)
        // Questo elude la necessità della Sessione!
        return "redirect:/dashboard?email=" + user.getUserEmail() + "&section=home"; // Passiamo anche l'id per sicurezza, ma non è strettamente necessario
    }
    
    @PostMapping("/api/logout")
    public String logout() {
        // In un'applicazione reale, qui si invaliderebbe la sessione o si rimuoverebbe il token di autenticazione.
        return "redirect:/";
    }
}
