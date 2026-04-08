package it.repository;

import it.mapper.BookCatalogDtoMapper;
import it.model.dto.BookCatalogDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookCatalogDto> findAllForBooks() {
        String sql = """
            SELECT b.book_id,
                   bn.title,
                   CONCAT(a.author_name, ' ', a.author_last_name) AS author_full_name,
                   p.publisher_name,
                   b.publishing_date,
                   i.code AS isbn_code,
                   c.category_name,
                   b.status
            FROM books b
            JOIN books_names bn ON b.title_id = bn.book_name_id
            JOIN author a        ON b.author_id = a.author_id
            JOIN publisher p     ON b.publisher_id = p.publisher_id
            JOIN category c      ON b.category_id = c.category_id
            JOIN isbn i           ON b.isbn_id = i.isbn_id
            """;

        return jdbcTemplate.query(sql,  (rs, rowNum) -> {
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
        });
    }

    public BookCatalogDto findById(int bookId) {
        String sql = """
            SELECT b.book_id,
                bn.title,
                CONCAT(a.author_name, ' ', a.author_last_name) AS author_full_name,
                p.publisher_name,
                b.publishing_date,
                i.code AS isbn_code,
                c.category_name,
                b.status
            FROM books b
            JOIN books_names bn ON b.title_id = bn.book_name_id
            JOIN author a        ON b.author_id = a.author_id
            JOIN publisher p     ON b.publisher_id = p.publisher_id
            JOIN category c      ON b.category_id = c.category_id
            JOIN isbn i           ON b.isbn_id = i.isbn_id
            WHERE b.book_id = ?
            """;
        return jdbcTemplate.queryForObject(sql, new BookCatalogDtoMapper(), bookId);
    }

}
