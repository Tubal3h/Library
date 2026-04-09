package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EditionRowMapper implements RowMapper<it.entity.Edition> {
    
    /**
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto Edition mappato dalla riga del database
     * @throws java.sql.SQLException in caso di errori con il database
     */
    @Override
    public it.entity.Edition mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        it.entity.Edition edition = new it.entity.Edition();
        edition.setEditionId(rs.getInt("edition_id"));
        edition.setBookNameId(rs.getInt("book_name_id"));
        edition.setAuthorId(rs.getInt("author_id"));
        edition.setPublisherId(rs.getInt("publisher_id"));
        edition.setPublishingDate(rs.getDate("publishing_date").toLocalDate());
        edition.setIsbn(rs.getString("isbn"));
        return edition;
    }
}