package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import it.entity.Publisher;



@Repository
public class PublisherRepository {
    private final JdbcTemplate jdbcTemplate;

    public PublisherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
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

