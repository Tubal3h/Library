package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import org.springframework.stereotype.Repository;

import it.entity.BookName;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class BookNameRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookNameRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return Lista di tutti i titoli dei libri
     */
    public List<BookName> getAllBookNames() {
        String sql = "SELECT * FROM books_names";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            BookName bookName = new BookName();
            bookName.setBooksNameId(rs.getInt("book_name_id"));
            bookName.setTitle(rs.getString("title"));
            return bookName;
        });
    }
}

