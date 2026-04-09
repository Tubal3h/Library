package it.mapper;

import it.model.dto.BookCatalogDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookCatalogDtoMapper implements RowMapper<BookCatalogDto> {
    
    @Override
    public BookCatalogDto mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
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
