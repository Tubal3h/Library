package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Author;

/**
 * Repository per la gestione dei dati degli autori nel database.
 */
@Repository
public class AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Costruttore per AuthorRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public AuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera la lista di tutti gli autori.
     * 
     * @return Lista di tutti gli autori nel database
     */
    public List<Author> getAllAuthors() {
        String sql = "SELECT * FROM author";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Author author = new Author();
            author.setAuthorId(rs.getInt("author_id"));
            author.setAuthorName(rs.getString("author_name"));
            author.setAuthorLastName(rs.getString("author_last_name"));
            return author;
        });
    }
}


