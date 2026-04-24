package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import it.entity.User;
import it.mapper.UserRowMapper;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository per la gestione dei dati degli utenti nel database.
 */
@Repository
public class UserRepository implements UserRepositoryInterface{
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    /**
     * Costruttore per UserRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     * @param userRowMapper Mapper per convertire i record del database in oggetti User
     */
    public UserRepository(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    /**
     * Ricerca un utente tramite la sua email.
     * 
     * @param email Email dell'utente da cercare
     * @return L'utente corrispondente all'email, o null se non trovato
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Recupera la lista di tutti gli utenti registrati.
     * 
     * @return Lista di tutti gli utenti registrati nel sistema
     */
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users ORDER BY user_name ASC";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    /**
    * Conta il numero totale di utenti registrati nel sistema non admin.
    * 
    * @return Il numero totale di utenti registrati con ruolo 'role_user'
    */

    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM users where roles = 'role_user'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Inserisce un nuovo utente nel database.
     *
     * @param userName Nome utente
     * @param userLastName Cognome utente
     * @param email Email aziendale
     * @param password Password iniziale
     * @param role Ruolo utente
     * @return numero righe inserite
     */
    public int insertUser(String userName, String userLastName, String email, String password, String role) {
        String sql = "INSERT INTO users (user_name, user_last_name, email, pass, roles) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, userName, userLastName, email, password, role);
    }

    /**
     * Verifica se esiste già un utente con l'email indicata.
     *
     * @param email email aziendale da controllare
     * @return true se già presente
     */
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    /**
     * Elimina un utente dal database in base al suo ID.
     *
     * @param userId ID dell'utente da eliminare
     * @return numero di righe eliminate
     */
    public int deleteUserById(String userId) {
        String sql = "DELETE FROM users WHERE users_id = ?";
        return jdbcTemplate.update(sql, userId);
    }
}


