package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Publisher;

/**
 * Repository per la gestione delle case editrici nel database.
 */
@Repository
public class PublisherRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Costruttore per PublisherRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public PublisherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera la lista di tutte le case editrici presenti nel database.
     * 
     * @return Lista di tutte le case editrici presenti nel database
     */
    public List<Publisher> getAllPublishers() {
        String sql = "SELECT * FROM publisher";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Publisher publisher = new Publisher();
            publisher.setPublisherId(rs.getInt("publisher_id"));
            publisher.setPublisherName(rs.getString("publisher_name"));
            return publisher;
        });
    }
}


