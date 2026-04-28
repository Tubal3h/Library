package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Author;

/**
 * Mapper per convertire i record del database della tabella author in oggetti Entity Author.
 */
@Component
public class AuthorRowMapper implements RowMapper<Author> {

    public static Author map(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setAuthorId(rs.getInt("authorId"));
        author.setAuthorName(rs.getString("authorName"));
        author.setAuthorLastName(rs.getString("authorLastName"));
        return author;
    }

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }
}


