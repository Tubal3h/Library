package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.BookName;

/**
 * Repository per la gestione dei nomi/titoli dei libri nel database.
 */
@Repository
public class BookNameRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Costruttore per BookNameRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public BookNameRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera la lista di tutti i titoli dei libri.
     * 
     * @return Lista di tutti i titoli dei libri presenti nel database
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


