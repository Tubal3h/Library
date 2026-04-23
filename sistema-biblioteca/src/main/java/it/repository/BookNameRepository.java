package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.entity.BookName;
import it.exception.InsertBookNameException;
import it.mapper.BookNameRowMapper;

/**
 * Repository per la gestione dei nomi/titoli dei libri nel database.
 */
@Repository
public class BookNameRepository implements BookNameRepositoryInterface {

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
    
    /**
     * Inserisce un nuovo titolo nella tabella books_names.
     *
     * @param title Il titolo del libro da inserire
     * @return Numero di record inseriti
     */
    public int insertBookByTitle(String title) throws InsertBookNameException{
		
    	String insertBook = "INSERT INTO books_names(title)\r\n"
				  		  + "VALUES(:title)";
		SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("title", title);
		try {
			int res = namedParameterJdbcTemplate.update(insertBook, sqlParameters);
			return res;
		}catch(DataAccessException ex) {
			throw new InsertBookNameException("errore nell'inserimento del titolo del libro");
		}    
    }

    /**
     * Aggiorna il titolo di un libro.
     *
     * @param bookNameId ID del libro
     * @param title Titolo del libro
     */

    public void updateBookTitle(int bookNameId, String title) {
        String updateBook = "UPDATE books_names SET title = :title WHERE book_name_id = :book_name_id";
        SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("title", title).addValue("book_name_id", bookNameId);
        namedParameterJdbcTemplate.update(updateBook, sqlParameters);
    }
}
