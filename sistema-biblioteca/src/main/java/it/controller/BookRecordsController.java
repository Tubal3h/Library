package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.configuration.UserSession;
import it.dto.UserDto;

@Controller
public class BookRecordsController {

    private final UserSession userSession;

    public BookRecordsController(UserSession userSession) {
        this.userSession = userSession;
    }
    
    @GetMapping("/api/navigation/bookRecords/{bookId}")
    public String bookRecords(@PathVariable(value = "bookId") String bookId, RedirectAttributes redirectAttributes) {
        UserDto user = userSession.getUser();
        if (user == null || !"role_admin".equals(user.getUserRole())) {
            return "redirect:/";
        }
        
        userSession.setSection("bookRecords");
        userSession.setRecordBookId(Integer.parseInt(bookId));
        return "redirect:/dashboard";
    }

    @GetMapping("/api/navigation/userRecords/{userId}")
    public String userRecords(@PathVariable(value = "userId") String userId, RedirectAttributes redirectAttributes) {
        UserDto user = userSession.getUser();
        if (user == null || !"role_admin".equals(user.getUserRole())) {
            return "redirect:/";
        }
        
        userSession.setSection("userRecords");
        userSession.setRecordUserId(Integer.parseInt(userId));
        return "redirect:/dashboard";
    }
}
