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

import it.entity.Author;
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.UpdateAuthorException;
import it.exception.repository.AuthorRepositoryException;
import it.mapper.AuthorRowMapper;
import it.repository.interfaces.AuthorRepositoryInterface;

/**
 * Repository per la gestione dei dati degli autori nel database.
 */
@Repository
public class AuthorRepository implements AuthorRepositoryInterface{
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final AuthorRowMapper authorRowMapper;

    /**
     * Costruttore per AuthorRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public AuthorRepository(JdbcTemplate jdbcTemplate, AuthorRowMapper authorRowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorRowMapper = authorRowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Recupera la lista di tutti gli autori.
     * 
     * @return Lista di tutti gli autori nel database
     */
    public List<Author> getAllAuthors() throws AuthorRepositoryException {
    	String sql = "SELECT * FROM author";
        try {
        	return jdbcTemplate.query(sql, authorRowMapper);
        }catch(DataAccessException ex) {
        	throw new AuthorRepositoryException("errore nel visualizzare gli autori");
        }
    }

    /**
     * Recupera un autore tramite il suo ID.
     * 
     * @param authorId ID dell'autore
     * @return Autore con l'ID specificato
     */

    public Author getAuthorById(int authorId) throws AuthorRepositoryException {
        String sql = "SELECT * FROM author WHERE author_id = :authorId";
        SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("authorId", authorId);
        try {
        	return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, authorRowMapper);        	
        }catch(DataAccessException ex) {
        	throw new AuthorRepositoryException(authorId);
        }
    }

    /**
     * Insere un autore nel database.
     * 
     * @param name     Nome dell'autore
     * @param lastName Cognome dell'autore
     * @return Numero di righe aggiornate
     * @throws InsertAuthorException Se si verifica un errore durante l'inserimento
     */
    
    public void insertAuthorByNameAndLastName(String name, String lastName) throws AuthorRepositoryException {
    	String insert = "INSERT INTO author (author_name, author_last_name) VALUES (:name, :lastName)";
    	SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("name", name)
    																	 .addValue("lastName", lastName);
    	try {
    		namedParameterJdbcTemplate.update(insert, parameterSource);		
    	}catch(DataAccessException ex) {
    		throw new AuthorRepositoryException("errore nell'inserimento dell'autore");
    	}
    }

    /**
     * Aggiorna i dati di un autore.
     * 
     * @param author L'autore da aggiornare
     */

    public void updateAuthor(Author author) throws UpdateAuthorException {
    	String update = "UPDATE author SET author_name = :name, author_last_name = :lastName WHERE author_id = :authorId";
    	SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("name", author.getAuthorName())
    																	 .addValue("lastName", author.getAuthorLastName())
    																	 .addValue("authorId", author.getAuthorId());
    	try {
    		namedParameterJdbcTemplate.update(update, parameterSource);
    	}catch(DataAccessException ex) {
    		throw new UpdateAuthorException("errore modificare l'autore");
    	}
    }

    /**
     * Verifica se un autore esiste nel database.
     * 
     * @param name     Nome dell'autore
     * @param lastName Cognome dell'autore
     * @return True se l'autore esiste, false altrimenti
     */
    public Boolean isAuthorPresent(String name, String lastName) throws AuthorRepositoryException, QueryIsNullOrNegativeExcepetion{
    	String sql = "SELECT COUNT(*) FROM author WHERE author_name = :name AND author_last_name = :lastName";
    	SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("name", name)
    								 .addValue("lastName", lastName);
    	Integer count = null;
    	try {
    		count = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);    		
    		if(count == null || count <= 0) {
    			throw new QueryIsNullOrNegativeExcepetion("attenzione errore nel cercare l'autore");
    		}
    		return count != null && count > 0;
    	}catch(DataAccessException ex) {
    		throw new AuthorRepositoryException("l'autore non è presente");
    	}
    }

    public void insertAuthor(String authorName, String authorLastName) throws AuthorRepositoryException {
        String insert = "INSERT INTO author (author_name, author_last_name) VALUES (:authorName, :authorLastName)";
        SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("authorName", authorName)
                                                                         .addValue("authorLastName", authorLastName);
        try {
            namedParameterJdbcTemplate.update(insert, parameterSource);
        }catch(DataAccessException ex) {
            throw new AuthorRepositoryException("errore nell'inserimento dell'autore");
        }
    }
}


