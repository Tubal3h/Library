package it.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EditionRowMapper implements RowMapper<it.model.Edition> {
    @Override
    public it.model.Edition mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        it.model.Edition edition = new it.model.Edition();
        edition.setEditionId(rs.getInt("edition_id"));
        edition.setBookNameId(rs.getInt("book_name_id"));
        edition.setAuthorId(rs.getInt("author_id"));
        edition.setPublisherId(rs.getInt("publisher_id"));
        edition.setPublishingDate(rs.getDate("publishing_date").toLocalDate());
        edition.setIsbn(rs.getString("isbn"));
        return edition;
    }
}