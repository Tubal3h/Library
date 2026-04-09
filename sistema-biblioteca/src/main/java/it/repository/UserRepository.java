package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import it.entity.User;
import it.mapper.UserRowMapper;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserRepository(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    /**
     * @param email Email dell'utente da cercare
     * @return L'utente corrispondente all'email, o null se non trovato
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        System.out.println("Executing SQL email: " + sql + " with email: " + email);
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * @return Lista di tutti gli utenti registrati
     */
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }


}

