package it.mapper.response;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import it.dto.response.BookUserDto;

public class BookUserDtoRowMapper implements RowMapper<BookUserDto> {

    @Override
    public BookUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookUserDto dto = new BookUserDto();
        
        // ID del libro e dell'edizione
        dto.setBookId(rs.getInt("book_id"));
        dto.setEditionId(rs.getInt("edition_id"));
        
        // Titolo
        dto.setTitle(rs.getString("title"));
        
        // Dati autore
        dto.setAuthorName(rs.getString("author_name"));
        dto.setAuthorLastName(rs.getString("author_last_name"));
        
        // Dati editore
        dto.setPublisherName(rs.getString("publisher_name"));
        
        // Data di pubblicazione
        java.sql.Date pubDate = rs.getDate("publishing_date");
        dto.setPublishingDate(pubDate != null ? pubDate.toLocalDate() : null);
        
        // Codice ISBN
        dto.setIsbnCode(rs.getString("isbn_code"));
        
        // Dati categoria
        dto.setCategoryName(rs.getString("category_name"));
        
        // Stato (disponibile, prenotato, ecc.)
        dto.setStatus(rs.getString("status"));
        
        return dto;
    }
}
