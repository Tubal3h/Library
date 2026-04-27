package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.component.UserSession;
import it.dto.UserDto;
import it.service.UserService;

/**
 * Controller per la gestione dei profili utente lato amministratore.
 */
@Controller
public class UserController {

    private final UserService userService;
    private final UserSession userSession;

    public UserController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
    }

    /**
     * Inserisce un nuovo dipendente o amministratore.
     *
     * @param userName nome del dipendente
     * @param userLastName cognome del dipendente
     * @param userEmail email del dipendente
     * @param userPassword password iniziale impostata dall'admin
     * @param userRole ruolo selezionato nel popup
     * @param redirectAttributes attributi flash per popup di esito
     * @return redirect alla dashboard utenti
     */
    @PostMapping("/api/addUser")
    public String addUser(
            @RequestParam("userName") String userName,
            @RequestParam("userLastName") String userLastName,
            @RequestParam("userRole") String userRole,
            RedirectAttributes redirectAttributes) {

        UserDto currentUser = userSession.getUser();
        if (currentUser == null || !"role_admin".equals(currentUser.getUserRole())) {
            return "redirect:/";
        }

        try {
            UserDto newUser = new UserDto();
            newUser.setUserName(userName);
            newUser.setUserLastName(userLastName);
            newUser.setUserEmail(userName + "." + userLastName + "@biblioteca.it");
            newUser.setUserPassword("Password123!");
            newUser.setUserRole(userRole);

            userService.createUser(newUser);

            redirectAttributes.addFlashAttribute("popupType", "addUser");
            redirectAttributes.addFlashAttribute("popupUserName", userName + " " + userLastName);
            redirectAttributes.addFlashAttribute("popupUserEmail", newUser.getUserEmail());
            redirectAttributes.addFlashAttribute("popupUserRole",
                    "role_admin".equalsIgnoreCase(userRole) ? "Amministratore" : "Dipendente");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Impossibile creare il nuovo dipendente.");
        }

        userSession.setSection("users");
        return "redirect:/dashboard";
    }

    @GetMapping("/api/deleteUser")
    public String deleteUser(
            @RequestParam("userId") String userId,
            @RequestParam(value = "userName", required = false) String userName,
            RedirectAttributes redirectAttributes) {
        
        UserDto currentUser = userSession.getUser();
        if (currentUser == null || !"role_admin".equals(currentUser.getUserRole())) {
            return "redirect:/";
        }

        try {
            userService.deleteUserById(userId);
            
            redirectAttributes.addFlashAttribute("popupType", "deleteUser");
            redirectAttributes.addFlashAttribute("popupUserId", userId);
            redirectAttributes.addFlashAttribute("popupUserName", userName);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Impossibile eliminare il dipendente.");
        }
        
        userSession.setSection("users");
        return "redirect:/dashboard";
    }

    @PostMapping("/api/changePassword")
    public String changePassword(
            @RequestParam("email") String email,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {
        
        UserDto currentUser = userSession.getUser();
        if (currentUser == null || !currentUser.getUserEmail().equals(email)) {
            return "redirect:/";
        }

        try {
            userService.updatePassword(email, oldPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("popupType", "changePassword");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", "Impossibile cambiare la password.");
        }
        
        userSession.setSection("profile");
        return "redirect:/dashboard";
    }
}
