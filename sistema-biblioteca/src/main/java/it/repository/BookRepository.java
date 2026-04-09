package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

/* -------------------------------------------------------------------------- */
/*                                  JAVA UTIL                                 */
/* -------------------------------------------------------------------------- */
import java.util.List;


/* -------------------------------------------------------------------------- */
/*                              SPRING FRAMEWORK                              */
/* -------------------------------------------------------------------------- */
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */
import it.entity.Book;



@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;


    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return Lista di tutti i libri nel database
     */
    public List<Book> getAllBooks() {
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

    /**
     * @param titleId ID del titolo
     * @return Titolo del libro corrispondente all'ID
     */
    public String getTitleByID(int titleId) {
        String sql = "SELECT title FROM books_names WHERE book_name_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, titleId);
    }

    /**
     * @param authorId ID dell'autore
     * @return Nome completo dell'autore
     */
    public String getAuthorFullNameByID(int authorId) {
        String sql = "SELECT CONCAT(author_name, ' ', author_last_name) AS author_full_name FROM author WHERE author_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, authorId);
    }

    /**
     * @param publisherId ID della casa editrice
     * @return Nome della casa editrice
     */
    public String getPublisherNameByID(int publisherId) {
        String sql = "SELECT publisher_name FROM publisher WHERE publisher_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, publisherId);
    }

    /**
     * @param isbnId ID dell'ISBN
     * @return Codice ISBN
     */
    public String getIsbnCodeByID(int isbnId) {
        String sql = "SELECT code FROM isbn WHERE isbn_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, isbnId);
    }

    /**
     * @param categoryId ID della categoria
     * @return Nome della categoria
     */
    public String getCategoryNameByID(int categoryId) {
        String sql = "SELECT category_name FROM category WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, categoryId);
    }
}

