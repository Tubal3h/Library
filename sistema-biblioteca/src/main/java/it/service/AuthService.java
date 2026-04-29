package it.service;
 
/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Service;

import it.dto.UserDto;
import it.dto.request.LoginDto;
import it.entity.User;
import it.repository.UserRepository;

/**
 * Servizio per la gestione dell'autenticazione degli utenti.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Costruttore per AuthService.
     * 
     * @param userRepository Repository per l'accesso ai dati degli utenti
     */
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gestisce il processo di login utenti.
     * 
     * @param loginDto DTO contenente le credenziali dell'utente
     * @return L'oggetto User se le credenziali sono corrette, null altrimenti
     */
    public UserDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setUserLastName(user.getUserLastName());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserPassword(user.getUserPassword());
        userDto.setUserRole(user.getUserRole());


        if (!userDto.getUserPassword().equals(loginDto.getPassword())) {
            return null;
        }

        return userDto;
    }

    
}

