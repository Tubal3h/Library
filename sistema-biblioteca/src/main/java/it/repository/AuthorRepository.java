package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Author;
import it.mapper.AuthorRowMapper;

/**
 * Repository per la gestione dei dati degli autori nel database.
 */
@Repository
public class AuthorRepository {
    private final JdbcTemplate jdbcTemplate;
    private final AuthorRowMapper authorRowMapper;

    /**
     * Costruttore per AuthorRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public AuthorRepository(JdbcTemplate jdbcTemplate, AuthorRowMapper authorRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorRowMapper = authorRowMapper;
    }

    /**
     * Recupera la lista di tutti gli autori.
     * 
     * @return Lista di tutti gli autori nel database
     */
    public List<Author> getAllAuthors() {
        String sql = "SELECT * FROM author";
        return jdbcTemplate.query(sql, authorRowMapper);
    }
}


