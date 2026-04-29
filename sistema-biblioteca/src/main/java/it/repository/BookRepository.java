package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import it.entity.Book;
import it.entity.RentalRecord;
import it.exception.InsertBookException;
import it.mapper.response.BookJoinResponseRowMapper;
import it.mapper.response.BookRecordJoinResponseRowMapper;
import it.repository.interfaces.BookRepositoryInterface;

/**
 * Repository per la gestione dei dati dei libri nel database.
 * Esegue query aggregate con JOIN per recuperare le informazioni complete dei
 * libri.
 */
@Repository
public class BookRepository implements BookRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;
    private final BookJoinResponseRowMapper bookJoinMapper;
    private final BookRecordJoinResponseRowMapper bookHistoryJoinMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Costruttore per BookRepository.
     *
     * @param jdbcTemplate   Il template JDBC per le operazioni sul database
     * @param bookJoinMapper Mapper per convertire i record del database in oggetti
     *                       BookJoin
     */
    public BookRepository(JdbcTemplate jdbcTemplate, BookJoinResponseRowMapper bookJoinMapper,
            BookRecordJoinResponseRowMapper bookHistoryJoinMapper,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookJoinMapper = bookJoinMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.bookHistoryJoinMapper = bookHistoryJoinMapper;
    }

    /**
     * Recupera il nome completo dell'autore tramite il suo ID.
     *
     * @param authorId ID dell'autore
     * @return Nome e cognome dell'autore
     */
    @Override
    public String getAuthorFullNameByID(int authorId) {
        String sql = "SELECT author_name, author_last_name FROM author WHERE author_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, authorId);
    }

    /**
     * Recupera il nome della casa editrice tramite il suo ID.
     *
     * @param publisherId ID della casa editrice
     * @return Nome della casa editrice
     */
    @Override
    public String getPublisherNameByID(int publisherId) {
        String sql = "SELECT publisher_name FROM publisher WHERE publisher_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, publisherId);
    }

    /**
     * Recupera il codice ISBN tramite il suo ID.
     *
     * @param isbnId ID dell'ISBN
     * @return Codice ISBN corrispondente
     */
    @Override
    public String getIsbnCodeByID(int isbnId) {
        String sql = "SELECT code FROM isbn WHERE isbn_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, isbnId);
    }

    /**
     * Recupera il nome della categoria tramite il suo ID.
     *
     * @param categoryId ID della categoria
     * @return Nome della categoria
     */
    @Override
    public String getCategoryNameByID(int categoryId) {
        String sql = "SELECT category_name FROM category WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, categoryId);
    }

    /**
     * Conta il numero totale di libri fisici nel sistema.
     *
     * @return Numero totale di libri presenti nella tabella books
     */

    public int countAllBooks() {
        String sql = "SELECT COUNT(*) FROM books ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int countAllNotEliminatedBookss() {
        String sql = "SELECT COUNT(*) FROM books WHERE status != 'eliminato'";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Recupera la lista completa dei libri con tutte le informazioni aggregate.
     * Utilizza una JOIN tra books, edition, books_names, author, publisher e
     * category.
     *
     * @return Lista di oggetti {@link BookJoin} con i dati completi di ogni libro
     */
    @Override
    public List<Book> getAllBooks() {
        String sql = """
                SELECT
                    e.edition_id,
                    b.book_id,
                    bn.title,
                    a.author_name,
                    a.author_last_name,
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
                ORDER BY bn.title ASC
                """;
        return jdbcTemplate.query(sql, bookJoinMapper);
    }

    /**
     * Inserisce una nuova copia fisica nella tabella books tramite il codice ISBN
     * dell'edizione.
     * La copia viene inizializzata con lo stato 'disponibilita'.
     *
     * @param isbn Codice ISBN dell'edizione
     * @return Numero di record inseriti
     */
    @Override
    public int insertBookByIsbn(String isbn) {
        String query = "INSERT INTO books(edition_id, status)\r\n"
                + "VALUES((SELECT edition_id FROM edition WHERE isbn = :isbn), \r\n"
                + "('disponibilita'))";

        SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("isbn", isbn);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(query, sqlParameters, keyHolder, new String[] { "book_id" });

        return keyHolder.getKey().intValue();
    }

    /**
     * Esegue l'eliminazione logica di un libro impostando lo stato a 'eliminato'.
     *
     * @param id ID univoco del libro fisico
     * @return Numero di record aggiornati
     */
    @Override
    public int deleteBookById(int id) {
        String query = "UPDATE books\r\n"
                + "SET status = 'eliminato'\r\n"
                + "WHERE book_id = :id";
        SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("id", id);
        int res = namedParameterJdbcTemplate.update(query, sqlParameters);
        return res;
    }

    /**
     * Inserisce una nuova copia fisica basandosi sul titolo del libro.
     * Utilizzato durante la creazione di una nuova edizione per aggiungere la prima
     * copia.
     *
     * @param title Titolo del libro
     * @return Numero di record inseriti
     */
    @Override
    public void insertBookByTitle(String title) throws InsertBookException {
        String insertBook = "INSERT INTO books (edition_id, status)\r\n"
                + "VALUES((SELECT edition_id FROM edition INNER JOIN books_names ON edition.book_name_id = books_names.book_name_id WHERE title = :title),\r\n"
                + "('disponibilita'))";

        SqlParameterSource sqlParameter = new MapSqlParameterSource().addValue("title", title);
        try {
            namedParameterJdbcTemplate.update(insertBook, sqlParameter);
        } catch (DataAccessException ex) {
            throw new InsertBookException("errore nell'inserimento della copia");
        }
    }

    /**
     * Recupera tutte le copie fisiche associate a una specifica edizione.
     *
     * @param editionId ID dell'edizione
     * @return Lista di oggetti BookJoin con i dati delle copie associate
     */
    @Override
    public List<RentalRecord> getBooksByEditionId(int editionId, boolean includeDeleted) {
        String filter = includeDeleted ? "" : " AND b.status != 'eliminato'";
        String sql = """
                SELECT
                    e.edition_id,
                    e.book_name_id,
                    e.author_id,
                    e.publisher_id,
                    e.category_id,
                    b.book_id,
                    bn.title,
                    a.author_name,
                    a.author_last_name,
                    p.publisher_name,
                    e.publishing_date,
                    c.category_name,
                    e.isbn,
                    b.status,
                    u.user_name,
                    u.user_last_name
                FROM books b
                JOIN edition e ON b.edition_id = e.edition_id
                JOIN books_names bn ON e.book_name_id = bn.book_name_id
                JOIN author a ON e.author_id = a.author_id
                JOIN publisher p ON e.publisher_id = p.publisher_id
                JOIN category c ON e.category_id = c.category_id
                LEFT JOIN (SELECT book_id, users_id FROM rental_record WHERE rental_ended IS NULL) r ON b.book_id = r.book_id
                LEFT JOIN users u ON r.users_id = u.users_id
                WHERE e.edition_id = ?
                """
                + filter;
        return jdbcTemplate.query(sql, bookHistoryJoinMapper, editionId);
    }

    @Override
    public int countAllNotEliminatedBooks() {
        String query = "SELECT COUNT(*) FROM books WHERE status != 'eliminato'";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }
}
