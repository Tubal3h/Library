package it.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



import it.service.BookService;
import it.service.UserService;
import it.dto.BookCatalogDto;
import it.dto.UserDto;

@Controller
public class DashboardController {
    
    private final UserService userService;
    private final BookService bookService;

    public DashboardController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
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

    UserDto user = userService.getUserByEmail(email);

    if (user == null) {
        return "redirect:/";
    }

    model.addAttribute("user", user);
    model.addAttribute("section", section);

    if (section.equals("users") && user.getUserRole().equals("role_admin")) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
    }

    if(section.equals("catalog")) {
        List<BookCatalogDto> books = bookService.getAllBooks();
        System.out.println("Books: " + books);
        model.addAttribute("books", books);
    }

    if (section.equals("rents") && user.getUserRole().equals("role_user")) {
        // Qui puoi aggiungere la logica per recuperare i noleggi dell'utente
    }




    return "dashboard";
}
    
}
