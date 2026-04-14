package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.BookJoin;

/**
 * Mapper per convertire i record del database in oggetti DTO BookCatalogDto.
 */
@Component
public class BookJoinRowMapper implements RowMapper<BookJoin> {
    
    /**
     * Mappa una riga del ResultSet in un oggetto BookCatalogDto.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto BookCatalogDto mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    @Override
    public BookJoin mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookJoin bookJoin = new BookJoin();
        bookJoin.setEditionId(rs.getInt("edition_id"));
        bookJoin.setBookId(rs.getInt("book_id"));
        bookJoin.setBookName(rs.getString("title"));
        bookJoin.setAuthorFullName(rs.getString("author_full_name"));
        bookJoin.setPublisherName(rs.getString("publisher_name"));
        bookJoin.setPublicationDate(rs.getDate("publishing_date").toLocalDate());
        bookJoin.setCategoryName(rs.getString("category_name"));
        bookJoin.setIsbnCode(rs.getString("isbn"));
        bookJoin.setStatus(rs.getString("status"));
        return bookJoin;
    }
}


