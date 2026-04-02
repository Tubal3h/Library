package it.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import it.model.User;
import it.repository.UserRepository;
import it.repository.BookRepository;
import it.dto.BookCatalogDto;

@Controller
public class DashboardController {
    
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public DashboardController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

@GetMapping("/dashboard")
public String dashboard(
    @RequestParam(value = "email", required = false) String email,
    @RequestParam(value = "section", defaultValue = "home") String section,
    Model model
) {
    if (email == null || email.isEmpty()) {
        return "redirect:/";
    }

    User user = userRepository.findByEmail(email);

    if (user == null) {
        return "redirect:/";
    }

    model.addAttribute("user", user);
    model.addAttribute("section", section);

    if (section.equals("users") && user.getUserRole().equals("role_admin")) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
    }

    if(section.equals("catalog") && user.getUserRole().equals("role_admin")) {
        // Qui puoi aggiungere la logica per recuperare i libri disponibili
        List<BookCatalogDto> books = bookRepository.findAllForCatalog();
        model.addAttribute("books", books);
    }

    // if(section.equals("catalog") && user.getUserRole().equals("role_user")) {
    //     // Qui puoi aggiungere la logica per recuperare i libri disponibili
    //             List<BookCatalogDto> books = bookRepository.findAllForCatalog();
    //     model.addAttribute("books", books);
    // }

    if (section.equals("rents") && user.getUserRole().equals("role_user")) {
        // Qui puoi aggiungere la logica per recuperare i noleggi dell'utente
    }




    return "dashboard";
}
    
}
