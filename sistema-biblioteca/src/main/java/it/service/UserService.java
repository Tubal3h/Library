package it.service;

import java.util.ArrayList;
import java.text.Normalizer;

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
            dto.setUserLastName(u.getUserLastName());
            dto.setUserEmail(u.getUserEmail());
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
			for(UserDto user : myList) {
				if(user.getUserName().replaceAll("\\s+","").toLowerCase().equals(search.replaceAll("\\s+","").toLowerCase())) {
					filteredList.add(user);
				}
			}	
		}
		if(filteredList.isEmpty() || filteredList == null) {
			return myList;
		}else {
			return filteredList;
		}
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
        dto.setUserPassword(user.getUserPassword());
        dto.setUserRole(user.getUserRole());
        return dto;
    }

    /**
     * Recupera il numero totale di utenti registrati nel sistema.
     * 
     * @return Il numero totale di utenti registrati con ruolo 'role_user'
     */

    public int getTotalUsers() {
        int users = userRepository.countUsers();
        return users;
    }

    /**
 * Crea un nuovo utente nel sistema generando automaticamente la mail
 * aziendale nel formato nome.cognome@biblioteca.it.
 *
 * @param userDto dati del nuovo utente
 * @return numero di righe inserite
 */
public int createUser(UserDto userDto) {
    validateUser(userDto);

    String email = buildCorporateEmail(userDto.getUserName(), userDto.getUserLastName());
    userDto.setUserEmail(email);

    if (userRepository.existsByEmail(email)) {
        throw new IllegalArgumentException("Esiste già un utente con questa email: " + email);
    }

    return userRepository.insertUser(
            userDto.getUserName().trim(),
            userDto.getUserLastName().trim(),
            email,
            userDto.getUserPassword().trim(),
            userDto.getUserRole().trim().toLowerCase());
}

/**
 * Genera l'email aziendale a partire da nome e cognome.
 *
 * @param userName nome dipendente
 * @param userLastName cognome dipendente
 * @return email in formato nome.cognome@biblioteca.it
 */
public String buildCorporateEmail(String userName, String userLastName) {
    String normalizedName = normalizeForEmail(userName);
    String normalizedLastName = normalizeForEmail(userLastName);
    return normalizedName + "." + normalizedLastName + "@biblioteca.it";
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

private String normalizeForEmail(String value) {
    String normalized = Normalizer.normalize(value == null ? "" : value, Normalizer.Form.NFD)
            .replaceAll("\\p{M}+", "")
            .replaceAll("[^A-Za-z0-9]", "")
            .toLowerCase();

    if (normalized.isBlank()) {
        throw new IllegalArgumentException("Nome o cognome non validi per la generazione della mail aziendale.");
    }

    return normalized;
    }
}

