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
/**
 * Mapper per convertire i record del database in oggetti di tipo BookNameRowMapper.
 */
public class BookNameRowMapper implements RowMapper<BookName> {

    /**
     * Mappa una riga del ResultSet in un oggetto BookName.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto BookName mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    public static BookName map(ResultSet rs) throws SQLException {
        BookName bookNames = new BookName();
        bookNames.setBookNameId(rs.getInt("book_name_id"));
        bookNames.setTitle(rs.getString("title"));
        return bookNames;
    }

    @Override
    public BookName mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }
}


