package it.service;
 
/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Service;

import it.dto.UserDto;
import it.dto.request.AuthDto;
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
    public AuthDto authenticate(AuthDto authDto) {
        System.out.println("Autenticazione utente: " + authDto.getEmail());
        User user = userRepository.findByEmail(authDto.getEmail());
        
        if (user == null || !user.getUserPassword().equals(authDto.getPassword())) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUserName());
        userDto.setUserLastName(user.getUserLastName());
        userDto.setUserRole(user.getUserRole());
        userDto.setUserEmail(user.getUserEmail());

        AuthDto result = new AuthDto();
        result.setUserDto(userDto);
        result.setEmail(user.getUserEmail());
        result.setPassword(user.getUserPassword());

        return result;
    }



    
}

