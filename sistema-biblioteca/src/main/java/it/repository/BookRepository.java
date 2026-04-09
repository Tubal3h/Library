package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Book;

/**
 * Repository per la gestione dei dati dei libri nel database.
 */
@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Costruttore per BookRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera la lista di tutti i libri.
     * 
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
     * Recupera il titolo di un libro tramite ID del nome.
     * 
     * @param titleId ID del titolo
     * @return Titolo del libro corrispondente all'ID
     */
    public String getTitleByID(int titleId) {
        String sql = "SELECT title FROM books_names WHERE book_name_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, titleId);
    }

    /**
     * Recupera il nome completo dell'autore tramite ID.
     * 
     * @param authorId ID dell'autore
     * @return Nome completo dell'autore
     */
    public String getAuthorFullNameByID(int authorId) {
        String sql = "SELECT CONCAT(author_name, ' ', author_last_name) AS author_full_name FROM author WHERE author_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, authorId);
    }

    /**
     * Recupera il nome della casa editrice tramite ID.
     * 
     * @param publisherId ID della casa editrice
     * @return Nome della casa editrice
     */
    public String getPublisherNameByID(int publisherId) {
        String sql = "SELECT publisher_name FROM publisher WHERE publisher_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, publisherId);
    }

    /**
     * Recupera il codice ISBN tramite ID (Nota: il metodo originale faceva riferimento a una tabella isbn non mostrata).
     * 
     * @param isbnId ID dell'ISBN
     * @return Codice ISBN
     */
    public String getIsbnCodeByID(int isbnId) {
        String sql = "SELECT code FROM isbn WHERE isbn_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, isbnId);
    }

    /**
     * Recupera il nome della categoria tramite ID.
     * 
     * @param categoryId ID della categoria
     * @return Nome della categoria
     */
    public String getCategoryNameByID(int categoryId) {
        String sql = "SELECT category_name FROM category WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, categoryId);
    }
}


