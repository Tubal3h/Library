package it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.configuration.UserSession;
import it.dto.UserDto;
import it.dto.request.AuthDto;
import it.dto.request.ChangePasswordDto;
import it.exception.NoDeleteUserServiceException;
import it.service.UserService;
import jakarta.validation.Valid;

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
            UserDto userDto,
            RedirectAttributes redirectAttributes) {

        AuthDto currentUser = userSession.getAuth();
        if (currentUser == null || !"role_admin".equals(currentUser.getUserDto().getUserRole())) {
            return "redirect:/";
        }

        try {
            AuthDto authDto = new AuthDto();
            String cleanName = userDto.getUserName().toLowerCase().replaceAll("[^a-z0-9àèéìòù]", "");
            String cleanLastName = userDto.getUserLastName().toLowerCase().replaceAll("[^a-z0-9àèéìòù]", "");
            authDto.setUserEmail(cleanName + "." + cleanLastName + "@biblioteca.it");
            authDto.setUserPassword("Password123!");
            authDto.setUserDto(userDto);

            userService.createUser(authDto);

            redirectAttributes.addFlashAttribute("popupType", "addUser");
            redirectAttributes.addFlashAttribute("popupUserName", authDto.getUserDto().getUserName() + " " + authDto.getUserDto().getUserLastName());
            redirectAttributes.addFlashAttribute("popupUserEmail", authDto.getEmail());
            redirectAttributes.addFlashAttribute("popupUserRole",
                    "role_admin".equalsIgnoreCase(authDto.getUserDto().getUserRole()) ? "Amministratore" : "Dipendente");
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
        
        AuthDto currentUser = userSession.getAuth();
        if (currentUser == null || !"role_admin".equals(currentUser.getUserDto().getUserRole())) {
            return "redirect:/";
        }

        try {
            userService.deleteUserById(userId);
            
            redirectAttributes.addFlashAttribute("popupType", "deleteUser");
            redirectAttributes.addFlashAttribute("popupUserId", userId);
            redirectAttributes.addFlashAttribute("popupUserName", userName);
        } catch (NoDeleteUserServiceException ex) {
            redirectAttributes.addFlashAttribute("popupType", "error");
            redirectAttributes.addFlashAttribute("popupErrorMessage", ex.getMessage());
        }
        
        userSession.setSection("users");
        return "redirect:/dashboard";
    }

    @PostMapping("/api/changePassword")
    public String changePassword(
    		@Valid @ModelAttribute ChangePasswordDto changePasswordDto,
            BindingResult bindingResult,
    		RedirectAttributes redirectAttributes) {
        
        AuthDto currentUser = userSession.getAuth();
        if (currentUser == null ) {
            return "redirect:/";
        }
        
        if(bindingResult.hasErrors()) {
        	String message = bindingResult.getAllErrors().getFirst().getDefaultMessage();
        	redirectAttributes.addFlashAttribute("popupErrorMessage", message);
        	return "redirect:/dashboard";
        }
        
        try {
            userService.updatePassword(currentUser, changePasswordDto);
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
