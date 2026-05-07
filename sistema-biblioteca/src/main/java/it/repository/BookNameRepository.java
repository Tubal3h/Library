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

import it.dto.BookNameDto;
import it.entity.BookName;
import it.exception.BookNamesRepositoryException;
import it.exception.InsertBookNameException;
import it.exception.SelectAllBookNamesException;
import it.mapper.BookNameRowMapper;
import it.repository.interfaces.BookNameRepositoryInterface;

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
    public List<BookName> getAllBookNames() throws BookNamesRepositoryException { 
        String sql = "SELECT * FROM books_names";
        try {
        	return jdbcTemplate.query(sql, bookNameRowMapper);
        }catch(DataAccessException ex) {
        	throw new BookNamesRepositoryException("titolo non trovato");
        }
    }

    /**
     * Recupera il titolo di un libro tramite il suo ID.
     *
     * @param titleId ID del titolo
     * @return Titolo del libro corrispondente all'ID
     */
    public BookName getBookNameById(int titleId) throws BookNamesRepositoryException {
        String sql = "SELECT * FROM books_names WHERE book_name_id = ?";
        try {
        	return jdbcTemplate.queryForObject(sql, bookNameRowMapper, titleId);        	
        }catch(DataAccessException ex) {
        	throw new BookNamesRepositoryException(titleId);
        }
    }
    
    /**
     * Inserisce un nuovo titolo nella tabella books_names.
     *
     * @param title Il titolo del libro da inserire
     * @return Numero di record inseriti
     */
    public void insertBookByTitle(String title) throws BookNamesRepositoryException{
		
    	String insertBook = "INSERT INTO books_names(title)\r\n"
				  		  + "VALUES(:title)";
		SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("title", title);
		try {
			namedParameterJdbcTemplate.update(insertBook, sqlParameters);	
		}catch(DataAccessException ex) {
			throw new BookNamesRepositoryException("errore nell'inserimento del titolo del libro");
		}    
    }

    /**
     * Aggiorna il titolo.
     *
     * @param bookNameDto DTO del titolo da aggiornare
     */

    public void updateBookTitle(BookNameDto bookNameDto) throws BookNamesRepositoryException {
        String updateBook = "UPDATE books_names SET title = :title WHERE book_name_id = :book_name_id";
        SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("title", bookNameDto.getTitle()).addValue("book_name_id", bookNameDto.getBookNameId());
        try {
        	namedParameterJdbcTemplate.update(updateBook, sqlParameters);       	
        }catch(DataAccessException ex) {
        	throw new BookNamesRepositoryException("titolo non modificato: " + bookNameDto.getTitle());
        }
    }

    /**
     * Recupera i titoli dei libri tramite il loro nome.
     *
     * @param title Nome del libro
     * @return Lista dei titoli dei libri corrispondenti al nome
     */
    public List<BookName> getBookNamesByTitle(String title) throws BookNamesRepositoryException{
        String sql = "SELECT * FROM books_names WHERE title = ?";
        try {
        	return jdbcTemplate.query(sql, bookNameRowMapper, title);
        }catch(DataAccessException ex) {
        	throw new BookNamesRepositoryException("titolo non trovato: " + title);
        }
    }
	
    @Override
	public Boolean isTitleOnDb(String title) {
		String selectBookByTitle = "SELECT COUNT(*) FROM books_names WHERE title =:title";
		SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("title", title);
		Integer counter = namedParameterJdbcTemplate.queryForObject(selectBookByTitle, sqlParameters, Integer.class);
		return counter != null && counter > 0;
		
	}
}
