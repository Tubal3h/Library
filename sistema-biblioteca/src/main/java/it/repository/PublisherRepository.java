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

import it.entity.Publisher;
import it.exception.InsertPublisherException;
import it.mapper.PublisherRowMapper;

/**
 * Repository per la gestione delle case editrici nel database.
 */
@Repository
public class PublisherRepository implements PublisherRepositoryInterface{
    private final JdbcTemplate jdbcTemplate;
    private final PublisherRowMapper publisherRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Costruttore per PublisherRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public PublisherRepository(JdbcTemplate jdbcTemplate, PublisherRowMapper publisherRowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.publisherRowMapper = publisherRowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Recupera la lista di tutte le case editrici presenti nel database.
     * 
     * @return Lista di tutte le case editrici presenti nel database
     */
    public List<Publisher> getAllPublishers() {
        String sql = "SELECT * FROM publisher";
        return jdbcTemplate.query(sql, publisherRowMapper);
    }
    public int insertPublisherByPubliserName(String publisherName) throws InsertPublisherException {
    	String insert = "INSERT INTO publisher (publisher_name) VALUES (:publisherName)";
    	SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("publisherName", publisherName);
    	try {
    		int res = namedParameterJdbcTemplate.update(insert, parameterSource);
    		return res;
    	}catch(DataAccessException ex) {
    		throw new InsertPublisherException("errore nell'inserimento del publisher");
    	}
    }
}


