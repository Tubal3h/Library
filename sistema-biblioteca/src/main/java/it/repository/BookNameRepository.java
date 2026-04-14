package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.BookName;
import it.mapper.BookNameRowMapper;

/**
 * Repository per la gestione dei nomi/titoli dei libri nel database.
 */
@Repository
public class BookNameRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BookNameRowMapper bookNameRowMapper;

    /**
     * Costruttore per BookNameRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public BookNameRepository(JdbcTemplate jdbcTemplate, BookNameRowMapper bookNameRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookNameRowMapper = bookNameRowMapper;
    }

    /**
     * Recupera la lista di tutti i titoli dei libri.
     * 
     * @return Lista di tutti i titoli dei libri presenti nel database
     */
    public List<BookName> getAllBookNames() {
        String sql = "SELECT * FROM books_names";
        return jdbcTemplate.query(sql, bookNameRowMapper);
    }

        /**
     * Recupera il titolo di un libro tramite ID del nome.
     * 
     * @param titleId ID del titolo
     * @return Titolo del libro corrispondente all'ID
     */
    public String getBookNameById(int titleId) {
        String sql = "SELECT title FROM books_names WHERE book_name_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, titleId);
    }
}


