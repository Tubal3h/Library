package it.repository;


import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import it.model.Book;



@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;


    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        public List<Book> GetAllBooks() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Book book = new Book();
            book.setBookId(rs.getInt("book_id"));
            book.setEditionId(rs.getInt("edition_id"));
            book.setCategoryId(rs.getInt("category_id"));
            book.setStatus(rs.getString("status"));
            return book;
        });
    }

    public String GetTitleByID(int titleId) {
        String sql = "SELECT title FROM books_names WHERE book_name_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, titleId);
    }

    public String GetAuthorFullNameByID(int authorId) {
        String sql = "SELECT CONCAT(author_name, ' ', author_last_name) AS author_full_name FROM author WHERE author_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, authorId);
    }

    public String GetPublisherNameByID(int publisherId) {
        String sql = "SELECT publisher_name FROM publisher WHERE publisher_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, publisherId);
    }

    public String GetIsbnCodeByID(int isbnId) {
        String sql = "SELECT code FROM isbn WHERE isbn_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, isbnId);
    }

    public String GetCategoryNameByID(int categoryId) {
        String sql = "SELECT category_name FROM category WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, categoryId);
    }

    
    // public BookCatalogDto findById(int bookId) {
    //     String sql = """
    //         SELECT b.book_id,
    //             bn.title,
    //             CONCAT(a.author_name, ' ', a.author_last_name) AS author_full_name,
    //             p.publisher_name,
    //             b.publishing_date,
    //             i.code AS isbn_code,
    //             c.category_name,
    //             b.status
    //         FROM books b
    //         JOIN books_names bn ON b.title_id = bn.book_name_id
    //         JOIN author a        ON b.author_id = a.author_id
    //         JOIN publisher p     ON b.publisher_id = p.publisher_id
    //         JOIN category c      ON b.category_id = c.category_id
    //         JOIN isbn i           ON b.isbn_id = i.isbn_id
    //         WHERE b.book_id = ?
    //         """;
    //     return jdbcTemplate.queryForObject(sql, new BookCatalogDtoMapper(), bookId);
    // }

}
