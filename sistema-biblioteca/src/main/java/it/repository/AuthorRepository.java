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

import it.entity.Author;
import it.mapper.AuthorRowMapper;

/**
 * Repository per la gestione dei dati degli autori nel database.
 */
@Repository
public class AuthorRepository {
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
    
    public int insertAuthorByNameAndLastName(String name, String lastName) {
    	String insert = "INSERTO INTO author (author_name, author_last_name) VALUES (:name, :lastName)";
    	SqlParameterSource parameterSource  = new MapSqlParameterSource().addValue("name", name)
    																	.addValue("lastname", lastName);
    	
    	int res = namedParameterJdbcTemplate.update(insert, parameterSource);
    	return res;
    }
}


