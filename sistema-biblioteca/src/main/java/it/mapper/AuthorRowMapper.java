package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Component;

import it.entity.Author;

import org.springframework.jdbc.core.RowMapper;

@Component
public class AuthorRowMapper implements RowMapper<Author> {

    /**
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto Author mappato dalla riga del database
     * @throws java.sql.SQLException in caso di errori con il database
     */
    @Override
    public Author mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Author author = new Author();
        author.setAuthorName(rs.getString("author_name"));
        author.setAuthorLastName(rs.getString("author_surname"));
        return author;
    }
    
}

