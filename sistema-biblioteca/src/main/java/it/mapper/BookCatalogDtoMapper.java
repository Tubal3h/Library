package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.dto.BookCatalogDto;

/**
 * Mapper per convertire i record del database in oggetti DTO BookCatalogDto.
 */
@Component
public class BookCatalogDtoMapper implements RowMapper<BookCatalogDto> {
    
    /**
     * Mappa una riga del ResultSet in un oggetto BookCatalogDto.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto BookCatalogDto mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    @Override
    public BookCatalogDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookCatalogDto dto = new BookCatalogDto();
        dto.setBookId(rs.getInt("book_id"));
        dto.setTitle(rs.getString("title"));
        dto.setAuthorFullName(rs.getString("author_full_name"));
        dto.setPublishingDate(rs.getDate("publishing_date").toLocalDate());
        dto.setPublisherName(rs.getString("publisher_name"));
        dto.setIsbnCode(rs.getString("isbn_code"));
        dto.setCategoryName(rs.getString("category_name"));
        dto.setStatus(rs.getString("status"));
        return dto;
    }
}


