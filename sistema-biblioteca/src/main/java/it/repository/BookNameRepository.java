package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Costruttore per BookNameRepository.
     *
     * @param jdbcTemplate      Il template JDBC per le operazioni sul database
     * @param bookNameRowMapper Mapper per convertire i record del database in oggetti BookName
     */
    public BookNameRepository(JdbcTemplate jdbcTemplate, BookNameRowMapper bookNameRowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookNameRowMapper = bookNameRowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
     * Recupera il titolo di un libro tramite il suo ID.
     *
     * @param titleId ID del titolo
     * @return Titolo del libro corrispondente all'ID
     */
    public String getBookNameById(int titleId) {
        String sql = "SELECT title FROM books_names WHERE book_name_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, titleId);
    }
    
    public int insertBookByTitle(String title) {
		
    	String insertBook = "INSERT INTO books_names(title)\r\n"
				  		  + "VALUES(:title)";
		SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("title", title);
		int res = namedParameterJdbcTemplate.update(insertBook, sqlParameters);
		return res;
    
    }
}
