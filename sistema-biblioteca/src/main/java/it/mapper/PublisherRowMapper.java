package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Publisher;

/**
 * RowMapper per la mappatura dei risultati del database in oggetti {@link Publisher}.
 */
@Component
public class PublisherRowMapper implements RowMapper<Publisher> {

    /**
     * Mappa una riga del ResultSet in un oggetto Publisher.
     *
     * @param rs     La ResultSet contenente i dati del database
     * @param rowNum Il numero della riga corrente
     * @return Oggetto Publisher mappato dai dati della riga
     * @throws SQLException Se si verifica un errore durante l'accesso ai dati della ResultSet
     */
    public static Publisher map(ResultSet rs) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(rs.getInt("publisherId"));
        publisher.setPublisherName(rs.getString("publisherName"));
        return publisher;
    }

    @Override
    public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }
}
