package it.service;
 
/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Service;

import it.dto.LoginDto;
import it.dto.UserDto;
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
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUserName());
        userDto.setUserLastName(user.getUserLastName());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserRole(user.getUserRole());


        if (!user.getUserPassword().equals(loginDto.getPassword())) {
            return null;
        }

        return userDto;
    }
}

