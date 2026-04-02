package it.mapper;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import it.model.Author;

@Component
public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public it.model.Author mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        it.model.Author author = new it.model.Author();
        author.setAuthorName(rs.getString("author_name"));
        author.setAuthorSurname(rs.getString("author_surname"));
        return author;
    }
    
}
