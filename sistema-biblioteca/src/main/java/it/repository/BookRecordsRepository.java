package it.repository;

import java.util.List;

import it.entity.BookRecordsJoin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookRecordsRepository {

    private final JdbcTemplate jdbcTemplate;
    
    public BookRecordsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<BookRecordsJoin> getBookRecords(int bookId) {
    String sql = """
        SELECT * 
        FROM rental_records rr
        JOIN users u ON rr.users_id = u.users_id
        JOIN books b ON rr.book_id = b.book_id
        JOIN edition e ON b.edition_id = e.edition_id
        JOIN books_names bn ON e.book_name_id = bn.book_name_id
        JOIN author a ON e.author_id = a.author_id
        JOIN publisher p ON e.publisher_id = p.publisher_id
        JOIN category c ON e.category_id = c.category_id
        WHERE rr.books_id = ?
    """;

    return 
}

}
