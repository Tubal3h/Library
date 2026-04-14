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

    /**
     * Mappa una riga del ResultSet in un oggetto Author.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto Author mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setAuthorName(rs.getString("author_name"));
        author.setAuthorLastName(rs.getString("author_surname"));
        return author;
    }
}


