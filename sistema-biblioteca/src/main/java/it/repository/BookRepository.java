package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.BookJoin;
import it.mapper.BookJoinRowMapper;

/**
 * Repository per la gestione dei dati dei libri nel database.
 */
@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BookJoinRowMapper bookJoinMapper;

    /**
     * Costruttore per BookRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public BookRepository(JdbcTemplate jdbcTemplate, BookJoinRowMapper bookJoinMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookJoinMapper = bookJoinMapper;
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

    /**
     * Conta il numero totale di libri nel sistema.
     * 
     * @return Il numero totale di libri nel sistema
     */

    public int countBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Recupera la lista di tutti i libri con le informazioni complete.
     * 
     * @return Lista di tutti i libri nel sistema
     * @see BookJoin
     * JOIN version
     */

    public List<BookJoin> getAllBooks() {
        String sql = """
                SELECT 
                    e.edition_id,
                    b.book_id,
                    bn.title,
                    CONCAT(a.author_name, ' ', a.author_last_name) AS author_full_name,
                    p.publisher_name,
                    e.publishing_date,
                    c.category_name,
                    e.isbn,
                    b.status
                FROM books b
                JOIN edition e ON b.edition_id = e.edition_id
                JOIN books_names bn ON e.book_name_id = bn.book_name_id
                JOIN author a ON e.author_id = a.author_id
                JOIN publisher p ON e.publisher_id = p.publisher_id
                JOIN category c ON e.category_id = c.category_id
                """;
        return jdbcTemplate.query(sql, bookJoinMapper);
    }

}


