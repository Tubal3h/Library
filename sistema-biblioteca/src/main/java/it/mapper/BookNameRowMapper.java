package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.BookNames;

/**
 * Mapper per convertire i record del database della tabella books_names in oggetti Entity BookName.
 */
@Component
public class BookNameRowMapper implements RowMapper<BookNames> {

    /**
     * Mappa una riga del ResultSet in un oggetto BookName.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto BookName mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    public static BookNames map(ResultSet rs) throws SQLException {
        BookNames bookName = new BookNames();
        bookName.setBookNameId(rs.getInt("bookNameId"));
        bookName.setTitle(rs.getString("title"));
        return bookName;
    }

    @Override
    public BookNames mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }
}


