package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dto.UserDto;
import it.component.UserSession;

@Controller
public class BookRecordsController {

    private final UserSession userSession;

    public BookRecordsController(UserSession userSession) {
        this.userSession = userSession;
    }
    
    @GetMapping("/api/navigation/bookRecords/{bookId}")
    public String bookRecords(@PathVariable(value = "bookId", required = false) String bookId, RedirectAttributes redirectAttributes) {
        UserDto user = userSession.getUser();
        if (user == null) {
            return "redirect:/";
        }
        if (bookId == null || bookId.isEmpty()) {
            return "redirect:/dashboard";
        }
        
        redirectAttributes.addFlashAttribute("bookId", bookId);
        return "redirect:/dashboard";
    }
}
