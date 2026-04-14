package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Publisher;
import it.mapper.PublisherRowMapper;

/**
 * Repository per la gestione delle case editrici nel database.
 */
@Repository
public class PublisherRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PublisherRowMapper publisherRowMapper;

    /**
     * Costruttore per PublisherRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public PublisherRepository(JdbcTemplate jdbcTemplate, PublisherRowMapper publisherRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.publisherRowMapper = publisherRowMapper;
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
}


