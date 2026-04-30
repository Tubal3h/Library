package it.mapper.response;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Book;

/**
 * Mapper per convertire i record del database in oggetti DTO BookCatalogDto.
 */
@Component
public class BookJoinResponseRowMapper implements RowMapper<Book> {
    
    /**
     * Mappa una riga del ResultSet in un oggetto BookCatalogDto.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto BookCatalogDto mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
<<<<<<< HEAD
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

        Book book = new Book();
        book.setEdition(EditionJoinResponseRowMapper.map(rs));

        return book;

=======
    public static Book map(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setEdition(EditionJoinResponseRowMapper.map(rs));
        return book;
>>>>>>> db0141d310098044398ca7b76a7bca1344b8f6d3
    }

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }

}


