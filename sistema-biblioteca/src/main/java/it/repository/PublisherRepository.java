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
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.PublisherExceptionRepository;
import it.mapper.PublisherRowMapper;
import it.repository.interfaces.PublisherRepositoryInterface;

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
    public List<Publisher> getAllPublishers() throws PublisherExceptionRepository {
        String sql = "SELECT * FROM publisher";
        try {
        	return jdbcTemplate.query(sql, publisherRowMapper);	
        }catch(DataAccessException ex) {
        	throw new PublisherExceptionRepository("attenzione publisher non trovati");
        }
    }

    /**
     * Recupera un publisher tramite il suo ID.
     * 
     * @param id L'ID del publisher da recuperare
     * @return Il publisher corrispondente
     */
    public Publisher getPublisherById(int id) throws PublisherExceptionRepository{
        String sql = "SELECT * FROM publisher WHERE publisher_id = :publisherId";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("publisherId", id);
        try {
        	return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, publisherRowMapper);
        }catch(DataAccessException ex) {
        	throw new PublisherExceptionRepository("attenzione publisher non trovato");
        }
    }

    /**
     * Aggiunge un nuovo publisher al database.
     * 
     * @param publisherName Il nome del publisher da aggiungere
     * @return Il numero di publisher aggiunti
     * @throws InsertPublisherException Se si verifica un errore nell'inserimento
     */
    public void insertPublisherByPubliserName(String publisherName) throws PublisherExceptionRepository {
    	String insert = "INSERT INTO publisher (publisher_name) VALUES (:publisherName)";
    	SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("publisherName", publisherName);
    	try {
    		namedParameterJdbcTemplate.update(insert, parameterSource);		
    	}catch(DataAccessException ex) {
    		throw new PublisherExceptionRepository("errore nell'inserimento del publisher");
    	}
    }

    /**
     * Verifica se un publisher esiste nel database.
     * 
     * @param publisherName Il nome del publisher da verificare
     * @return True se il publisher esiste, false altrimenti
     */
    public Boolean isPublisherPresent(Publisher publisher) throws PublisherExceptionRepository, QueryIsNullOrNegativeExcepetion{
        if(publisher.getPublisherName() == null || publisher.getPublisherName().isEmpty()) {
            throw new IllegalArgumentException("Il nome del publisher non può essere vuoto");
        }
        String sql = "SELECT COUNT(*) FROM publisher WHERE publisher_name = :publisherName";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("publisherName", publisher.getPublisherName());
        Integer count = null; 
        try {
        	count = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
        	if(count == null || count < 0) {
        		throw new QueryIsNullOrNegativeExcepetion("errore grave nel trovare il publisher");
        	}
        	return true;
        }catch(DataAccessException ex) {
        	throw new PublisherExceptionRepository("publisher non presente");
        }
    }

    /**
     * Aggiorna un publisher nel database.
     * 
     * @param publisher Il publisher da aggiornare
     */
    public void updatePublisher(Publisher publisher) throws PublisherExceptionRepository{
        String sql = "UPDATE publisher SET publisher_name = :publisherName WHERE publisher_id = :publisherId";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("publisherName", publisher.getPublisherName())
                .addValue("publisherId", publisher.getPublisherId());
        try {
        	namedParameterJdbcTemplate.update(sql, parameterSource); 	
        }catch(DataAccessException ex) {
        	throw new PublisherExceptionRepository("impossibile aggiornare il publisher");
        }
    }

    /**
     * Elimina un publisher dal database.
     * 
     * @param publisher Il publisher da eliminare
     * @return Il numero di publisher eliminati
     */
    public int deletePublisher(Publisher publisher) throws PublisherExceptionRepository {
        String sql = "DELETE FROM publisher WHERE publisher_id = :publisherId";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("publisherId",
                publisher.getPublisherId());
        try {
        	return namedParameterJdbcTemplate.update(sql, parameterSource);
        	
        }catch(DataAccessException ex) {
        	throw new PublisherExceptionRepository("impossibile elimare il publisher");
        }
    }

    public void insertPublisher(String publisherName) throws PublisherExceptionRepository {
        String insert = "INSERT INTO publisher (publisher_name) VALUES (:publisherName)";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("publisherName", publisherName);
        try {
            namedParameterJdbcTemplate.update(insert, parameterSource);
        }catch(DataAccessException ex) {
            throw new PublisherExceptionRepository("errore nell'inserimento del publisher");
        }
    }
}


