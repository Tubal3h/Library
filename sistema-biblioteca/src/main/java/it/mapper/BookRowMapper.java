package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                  MAPPER                                    */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Book;

/**
 * Mapper per convertire i record del database della tabella book in oggetti Entity Book.
 */
@Component
public class BookRowMapper implements RowMapper<Book> {

    public static Book map(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setStatus(rs.getString("status"));
        return book;
    }

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }
}
