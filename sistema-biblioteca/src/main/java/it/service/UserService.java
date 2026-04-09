package it.service;

/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.stereotype.Service;

import it.dto.UserDto;
import it.entity.User;
import it.repository.UserRepository;

/**
 * Servizio per la gestione degli utenti del sistema.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Costruttore per UserService.
     * 
     * @param userRepository Repository per l'accesso ai dati degli utenti
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Recupera tutti gli utenti registrati.
     * 
     * @return Lista di UserDto contenente le informazioni condensate degli utenti
     */
    public List<UserDto> getAllUsers() {
        List<User> entities = userRepository.getAllUsers();
        return entities.stream().map(u -> {
            UserDto dto = new UserDto();
            dto.setUserId(u.getUserId());
            dto.setUserName(u.getUserName());
            dto.setUserLastName(u.getUserSurname());
            dto.setUserEmail(u.getUserEmail());
            dto.setUserRole(u.getUserRole());
            return dto;
        }).toList();
    }

    /**
     * Recupera un utente tramite la sua email.
     * 
     * @param email L'email dell'utente
     * @return UserDto dell'utente se trovato, null altrimenti
     */
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserRole(user.getUserRole());
        return dto;
    }
}

