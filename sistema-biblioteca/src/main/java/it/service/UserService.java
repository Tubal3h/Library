package it.service;

import java.util.ArrayList;

/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;
import it.service.AuthService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.dto.UserDto;
import it.dto.request.AuthDto;
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
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        List<User> entities = userRepository.getAllUsers();
        return entities.stream().map(u -> {
            UserDto dto = new UserDto();
            dto.setUserId(u.getUserId());
            dto.setUserName(u.getUserName());
            dto.setUserLastName(u.getUserLastName());
            dto.setUserRole(u.getUserRole());
            return dto;
        }).toList();
    }

    /**
     * Recupera una lista di utenti filtrata per nome.
     * 
     * @param search Il termine di ricerca per il nome dell'utente
     * @return Lista di UserDto contenente le informazioni condensate degli utenti filtrati per nome
     */
    public List<UserDto> getUserListByName(String search) {
		List<UserDto> myList = getAllUsers();
		List<UserDto> filteredList = new ArrayList<>();
		if(search != null && !search.isBlank()) {
			String [] searchString = search.toLowerCase().trim().split("\\s+");
			for(UserDto user : myList) {
				String userName = user.getUserName();
				String userLastName = user.getUserLastName();
				String fullName = (userName + " " + userLastName).toLowerCase();
				boolean allMatch = true;
				for(String s : searchString) {
					if(!fullName.contains(s)) {
						allMatch = false;
						break;
					}
				}
				if(allMatch) {
					filteredList.add(user);
				}
			}
		}else {
			return myList;
		}
		if(filteredList.isEmpty() || filteredList == null) {
			return myList;
		}else {
			return filteredList;
		}
	}

    /**
     * Recupera un utente tramite il suo ID.
     * 
     * @param userId ID dell'utente
     * @return UserDto dell'utente se trovato, null altrimenti
     */
    @Transactional(readOnly = true)
    public UserDto getUserById(int userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setUserLastName(user.getUserLastName());
        dto.setUserRole(user.getUserRole());
        return dto;
    }

    /**
     * Recupera il numero totale di utenti registrati nel sistema.
     * 
     * @return Il numero totale di utenti registrati con ruolo 'role_user'
     */
    
    @Transactional(readOnly = true)
    public int getTotalUsers() {
        int users = userRepository.countUsers();
        return users;
    }

    /**
     * Crea un nuovo utente nel sistema con i dati forniti.
     *
     * @param userDto dati del nuovo utente
     * @return numero di righe inserite
     */

    public int createUser(UserDto userDto, AuthDto AuthDto) {
        validateUser(userDto);

        if (userRepository.existsByEmail(AuthDto.getUserEmail())) {
            throw new IllegalArgumentException("Esiste già un utente con questa email: " + AuthDto.getUserEmail());
        }

        return userRepository.insertUser(
                userDto.getUserName().trim(),
                userDto.getUserLastName().trim(),
                AuthDto.getUserEmail().trim().toLowerCase(),
                AuthDto.getUserPassword().trim(),
                userDto.getUserRole().trim().toLowerCase());
    }

    private void validateUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("Dati utente mancanti.");
        }

        if (userDto.getUserName() == null || userDto.getUserName().isBlank()) {
            throw new IllegalArgumentException("Il nome è obbligatorio.");
        }

        if (userDto.getUserLastName() == null || userDto.getUserLastName().isBlank()) {
            throw new IllegalArgumentException("Il cognome è obbligatorio.");
        }

        if (userDto.getUserPassword() == null || userDto.getUserPassword().isBlank()) {
            throw new IllegalArgumentException("La password è obbligatoria.");
        }

        String role = userDto.getUserRole();
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Il ruolo è obbligatorio.");
        }

        String normalizedRole = role.trim().toLowerCase();
        if (!"role_admin".equals(normalizedRole) && !"role_user".equals(normalizedRole)) {
            throw new IllegalArgumentException("Ruolo non valido. Usare ROLE_ADMIN o ROLE_USER.");
        }
    }

    /**
     * Elimina un utente dal sistema tramite il suo ID.
     *
     * @param userId ID dell'utente da eliminare
     * @return numero di righe eliminate
     */
    @Transactional
    public int deleteUserById(String userId) {
        return userRepository.deleteUserById(userId);
    }

    /**
     * Aggiorna la password dell'utente verificando la password attuale.
     *
     * @param email Email dell'utente
     * @param oldPassword Password attuale
     * @param newPassword Nuova password
     * @param confirmPassword Conferma della nuova password
     */
    @Transactional
    public void updatePassword(AuthDto authDto, String oldPassword, String newPassword, String confirmPassword) {
        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("La nuova password e la conferma non coincidono.");
        }
        
        AuthDto user = authService.authenticate(authDto);
        if (user == null) {
            throw new IllegalArgumentException("Utente non trovato.");
        }
        
        if (!user.getUserPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("La password attuale non è corretta.");
        }
        
        userRepository.updatePassword(email, newPassword);
    }
}

