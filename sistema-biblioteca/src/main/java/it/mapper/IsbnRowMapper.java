package it.mapper;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import it.model.Isbn;
@Component
public class IsbnRowMapper implements RowMapper<Isbn> {

    @Override
    public Isbn mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Isbn isbn = new Isbn();
        isbn.setIsbnId(rs.getInt("isbn_id"));
        isbn.setCode(rs.getString("isbn"));
        return isbn;
    }

}
