package it.mapper;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import it.model.BookName;

@Component
public class BookNameRowMapper implements RowMapper<BookName> {

    @Override
    public BookName mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        BookName bookName = new BookName();
        bookName.setTitle(rs.getString("book_name"));
        return bookName;
    }
    
}
