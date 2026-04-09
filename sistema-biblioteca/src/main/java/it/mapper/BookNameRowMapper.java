package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Component;

import it.entity.BookName;

import org.springframework.jdbc.core.RowMapper;

@Component
public class BookNameRowMapper implements RowMapper<BookName> {

    /**
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto BookName mappato dalla riga del database
     * @throws java.sql.SQLException in caso di errori con il database
     */
    @Override
    public BookName mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        BookName bookName = new BookName();
        bookName.setTitle(rs.getString("book_name"));
        return bookName;
    }
    
}

