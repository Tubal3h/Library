package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.model.User;
import it.repository.UserRepository;

@Controller
public class DashboardController {
    
    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "email", required = false) String email, Model model) {
        
        if (email == null || email.isEmpty()) {
            return "redirect:/"; // Se arrivi senza mail nell'URL cacciato al login
        }
        
        // Riafferriamo l'utente dal Database grazie all'email passata dal form!
        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            return "redirect:/";
        }
        
        // Passiamo l'utente puro a Thymeleaf
        model.addAttribute("user", user);
        
        return "dashboard";
    }
}
