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
import it.exception.InsertAuthorException;
import it.mapper.AuthorRowMapper;

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
    public List<Author> getAllAuthors() {
        String sql = "SELECT * FROM author";
        return jdbcTemplate.query(sql, authorRowMapper);
    }

    /**
     * Insere un autore nel database.
     * 
     * @param name     Nome dell'autore
     * @param lastName Cognome dell'autore
     * @return Numero di righe aggiornate
     * @throws InsertAuthorException Se si verifica un errore durante l'inserimento
     */
    
    public int insertAuthorByNameAndLastName(String name, String lastName) throws InsertAuthorException {
    	String insert = "INSERT INTO author (author_name, author_last_name) VALUES (:name, :lastName)";
    	SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("name", name)
    																	 .addValue("lastName", lastName);
    	try {
    		int res = namedParameterJdbcTemplate.update(insert, parameterSource);
    		return res;
    	}catch(DataAccessException ex) {
    		throw new InsertAuthorException("errore nell'inserimento dell'autore");
    	}
    }

    /**
     * Aggiorna i dati di un autore.
     * 
     * @param author L'autore da aggiornare
     */

    public void updateAuthor(Author author) {
    	String update = "UPDATE author SET author_name = :name, author_last_name = :lastName WHERE author_id = :authorId";
    	SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("name", author.getAuthorName())
    																	 .addValue("lastName", author.getAuthorLastName())
    																	 .addValue("authorId", author.getAuthorId());
    	jdbcTemplate.update(update, parameterSource);
    }

    /**
     * Verifica se un autore esiste nel database.
     * 
     * @param name     Nome dell'autore
     * @param lastName Cognome dell'autore
     * @return True se l'autore esiste, false altrimenti
     */
    public boolean isAuthorPresent(String name, String lastName) throws InsertAuthorException{
    	String sql = "SELECT COUNT(*) FROM author WHERE author_name = :name AND author_last_name = :lastName";
    	SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("name", name)
    								 .addValue("lastName", lastName);
    	int count = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
    	return count > 0;
    }
}


