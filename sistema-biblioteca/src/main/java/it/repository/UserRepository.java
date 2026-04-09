package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.User;
import it.mapper.UserRowMapper;

/**
 * Repository per la gestione dei dati degli utenti nel database.
 */
@Repository
public class UserRepository {
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
        String sql = "SELECT * FROM users";
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
}


