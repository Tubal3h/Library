package it.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.model.Book;

@Component
public class BookRowMapper implements RowMapper<Book> {
    
    @Override
    public Book mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitleId(rs.getInt("title_id"));
        book.setAuthorId(rs.getInt("author_id"));
        book.setPublisherId(rs.getInt("publisher_id"));
        book.setPublishingDate(rs.getDate("publishing_date"));
        book.setIsbnId(rs.getInt("isbn_id"));
        book.setCategoryId(rs.getInt("category_id"));
        book.setStatus(rs.getString("status"));
        return book;
    }
}
