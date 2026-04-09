package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.BookName;

/**
 * Mapper per convertire i record del database della tabella books_names in oggetti Entity BookName.
 */
@Component
public class BookNameRowMapper implements RowMapper<BookName> {

    /**
     * Mappa una riga del ResultSet in un oggetto BookName.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto BookName mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    @Override
    public BookName mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookName bookName = new BookName();
        bookName.setTitle(rs.getString("book_name"));
        return bookName;
    }
}


