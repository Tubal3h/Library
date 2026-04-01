package it.controller;

import it.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("loggedUser") != null) {
            return "redirect:/dashboard";
        }
        return "index"; 
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String email,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {

        User authenticatedUser = mockDatabaseQuery(email, password);

        if (authenticatedUser != null) {
            session.setAttribute("loggedUser", authenticatedUser);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Email o password errati. Riprova.");
            return "index";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    //---------------------------------------------------------
    // Helper Mock (ELIMINARE QUANDO SARÀ COLLEGATO AL VERO DB)
    //---------------------------------------------------------
    private User mockDatabaseQuery(String email, String password) {
        // Simulazione ruolo ADMIN
        if ("admin@mba.it".equals(email) && "admin123".equals(password)) {
            return new User("Marco", "Rossi", email, password, "role_admin");
        } 
        // Simulazione ruolo USER
        else if ("user@mba.it".equals(email) && "user123".equals(password)) {
            return new User("Giulia", "Bianchi", email, password, "role_user");
        }
        return null;
    }
}
