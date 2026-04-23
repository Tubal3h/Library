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

    /**
     * Aggiunge un nuovo publisher al database.
     * 
     * @param publisherName Il nome del publisher da aggiungere
     * @return Il numero di publisher aggiunti
     * @throws InsertPublisherException Se si verifica un errore nell'inserimento
     */
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

    /**
     * Verifica se un publisher esiste nel database.
     * 
     * @param publisherName Il nome del publisher da verificare
     * @return True se il publisher esiste, false altrimenti
     */
    public boolean isPublisherPresent(Publisher publisher) {
        if(publisher.getPublisherName() == null || publisher.getPublisherName().isEmpty()) {
            throw new IllegalArgumentException("Il nome del publisher non può essere vuoto");
        }
        String sql = "SELECT COUNT(*) FROM publisher WHERE publisher_name = :publisherName";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("publisherName", publisher.getPublisherName());
        return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class) > 0;
    }

    /**
     * Aggiorna un publisher nel database.
     * 
     * @param publisher Il publisher da aggiornare
     * @return Il numero di publisher aggiornati
     */
    public int updatePublisher(Publisher publisher) {
        String sql = "UPDATE publisher SET publisher_name = :publisherName WHERE publisher_id = :publisherId";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("publisherName", publisher.getPublisherName())
                .addValue("publisherId", publisher.getPublisherId());
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    /**
     * Elimina un publisher dal database.
     * 
     * @param publisher Il publisher da eliminare
     * @return Il numero di publisher eliminati
     */
    public int deletePublisher(Publisher publisher) {
        String sql = "DELETE FROM publisher WHERE publisher_id = :publisherId";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("publisherId",
                publisher.getPublisherId());
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }


}


