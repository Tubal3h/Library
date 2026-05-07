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
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.BookRepositoryException;
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
    public String getAuthorFullNameById(int authorId) throws BookRepositoryException {
        String sql = "SELECT author_name, author_last_name FROM author WHERE author_id = ?";
        try {
        	return jdbcTemplate.queryForObject(sql, String.class, authorId);
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("errore nel trovare l'autore con il seguente id: " + authorId);
        }
    }

    /**
     * Recupera il nome della casa editrice tramite il suo ID.
     *
     * @param publisherId ID della casa editrice
     * @return Nome della casa editrice
     */
    @Override
    public String getPublisherNameById(int publisherId) throws BookRepositoryException {
        String sql = "SELECT publisher_name FROM publisher WHERE publisher_id = ?";
        try {
        	return jdbcTemplate.queryForObject(sql, String.class, publisherId);
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("impossibile trovare il publisher: " + publisherId);
        }
    }

    /**
     * Recupera il codice ISBN tramite il suo ID.
     *
     * @param isbnId ID dell'ISBN
     * @return Codice ISBN corrispondente
     */
    @Override
    public String getIsbnCodeById(int isbnId) throws BookRepositoryException{
        String sql = "SELECT code FROM isbn WHERE isbn_id = ?";
        try {
        	return jdbcTemplate.queryForObject(sql, String.class, isbnId); 	
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("impossibile trovare l'isbn");
        }
    }

    /**
     * Recupera il nome della categoria tramite il suo ID.
     *
     * @param categoryId ID della categoria
     * @return Nome della categoria
     */
    @Override
    public String getCategoryNameById(int categoryId) throws BookRepositoryException {
        String sql = "SELECT category_name FROM category WHERE category_id = ?";
        try {
        	return jdbcTemplate.queryForObject(sql, String.class, categoryId);	
        }catch(BookRepositoryException ex) {
        	throw new BookRepositoryException("impossibile trovare la categoria");
        }
    }

    /**
     * Conta il numero totale di libri fisici nel sistema.
     *
     * @return Numero totale di libri presenti nella tabella books
     */

    public int countAllBooks() throws BookRepositoryException, QueryIsNullOrNegativeExcepetion{
        String sql = "SELECT COUNT(*) FROM books ";
        Integer count = null;
        try {
        	count = jdbcTemplate.queryForObject(sql, Integer.class);
        	if(count == null || count < 0) {
        		throw new QueryIsNullOrNegativeExcepetion("attenzione errore nel recapitare il numero di libri");
        	}
        	return count;
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("libreria vuota pezzente");
        }
    }

    /**
     * Conta il numero di libri non ancora eliminati dal sistema.
     *
     * @return Numero di libri con stato diverso da 'eliminato'
     */
    public int countAllNotEliminatedBookss() throws BookRepositoryException, QueryIsNullOrNegativeExcepetion{
        String sql = "SELECT COUNT(*) FROM books WHERE status != 'eliminato'";
        Integer count = null;
        try {
        	count = jdbcTemplate.queryForObject(sql, Integer.class);
        	if(count == null || count < 0) {
        		throw new QueryIsNullOrNegativeExcepetion("errore grave nel trovare i libri non eliminati");
        	}
        	return count;
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("libreria vuota pezzente");
        }
    }

    /**
     * Recupera la lista completa dei libri con tutte le informazioni aggregate.
     * Utilizza una JOIN tra books, edition, books_names, author, publisher e
     * category.
     *
     * @return Lista di oggetti {@link BookJoin} con i dati completi di ogni libro
     */
    @Override
    public List<Book> getAllBooks() throws BookRepositoryException {
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
                    0 AS quantity
                FROM books b
                JOIN edition e ON b.edition_id = e.edition_id
                JOIN books_names bn ON e.book_name_id = bn.book_name_id
                JOIN author a ON e.author_id = a.author_id
                JOIN publisher p ON e.publisher_id = p.publisher_id
                JOIN category c ON e.category_id = c.category_id
                ORDER BY bn.title ASC
                """;
        try {
        	return jdbcTemplate.query(sql, bookJoinMapper);
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("libreria vuota");
        }
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
    public int insertBookByIsbn(String isbn) throws BookRepositoryException{
        String query = "INSERT INTO books(edition_id, status)\r\n"
                + "VALUES((SELECT edition_id FROM edition WHERE isbn = :isbn), \r\n"
                + "('disponibilita'))";

        SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("isbn", isbn);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
        	namedParameterJdbcTemplate.update(query, sqlParameters, keyHolder, new String[] { "book_id" });
        	return keyHolder.getKey().intValue();
        
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("errore nell'inserire una copia fisica del libro");
        }

    }

    /**
     * Esegue l'eliminazione logica di un libro impostando lo stato a 'eliminato'.
     *
     * @param id ID univoco del libro fisico
     * @return Numero di record aggiornati
     */
    @Override
    public int deleteBookById(int id) throws BookRepositoryException{
        String query = "UPDATE books\r\n"
                + "SET status = 'eliminato'\r\n"
                + "WHERE book_id = :id";
        SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("id", id);
        try {
        	int res = namedParameterJdbcTemplate.update(query, sqlParameters);
        	return res;
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("errore nell'aggiornare il libro: " + id);
        }
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
    public int insertBookByTitleAndIsbn(String title, String isbn) throws BookRepositoryException {
      
    	System.out.println("titoloBookRepo: " + title);
    	
        String insertBook = "INSERT INTO books (edition_id, status)\r\n"
                + "VALUES((SELECT edition_id FROM edition INNER JOIN books_names ON edition.book_name_id = books_names.book_name_id WHERE title = :title AND edition.isbn = :isbn),\r\n"
                + "('disponibilita'))";

        SqlParameterSource sqlParameter = new MapSqlParameterSource().addValue("title", title)
        															 .addValue("isbn", isbn);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
        	namedParameterJdbcTemplate.update(insertBook, sqlParameter, keyHolder, new String[]{"book_id"});
            return keyHolder.getKey().intValue();
        	
        } catch (DataAccessException ex) {
        	System.out.println(ex.toString());
            throw new BookRepositoryException("errore nell'inserimento della copia");
        }
    }

    /**
     * Recupera tutte le copie fisiche associate a una specifica edizione.
     *
     * @param editionId ID dell'edizione
     * @return Lista di oggetti BookJoin con i dati delle copie associate
     */
    @Override
    public List<RentalRecord> getBooksByEditionId(int editionId, boolean includeDeleted) throws BookRepositoryException {
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
                    0 AS quantity,
                    r.rental_id,
                    r.rental_date,
                    r.rental_expired,
                    r.rental_ended,
                    r.booking_date,
                    u.users_id,
                    u.user_name,
                    u.user_last_name,
                    u.email,
                    u.pass,
                    u.roles
                FROM books b
                JOIN edition e ON b.edition_id = e.edition_id
                JOIN books_names bn ON e.book_name_id = bn.book_name_id
                JOIN author a ON e.author_id = a.author_id
                JOIN publisher p ON e.publisher_id = p.publisher_id
                JOIN category c ON e.category_id = c.category_id
                LEFT JOIN (SELECT rental_id, book_id, users_id, rental_date, rental_expired, rental_ended, booking_date FROM rental_record WHERE rental_ended IS NULL) r ON b.book_id = r.book_id
                LEFT JOIN users u ON r.users_id = u.users_id
                WHERE e.edition_id = ?
                """
                + filter;
        try {
        	return jdbcTemplate.query(sql, bookHistoryJoinMapper, editionId);
        	
        }catch(DataAccessException ex){
        	throw new BookRepositoryException("libro con questa edizione non trovata: " + editionId );
        }
    }

    /**
     * Conta il numero di libri non eliminati dal sistema.
     *
     * @return Numero totale di libri attivi
     */
    @Override
    public int countAllNotEliminatedBooks() throws BookRepositoryException, QueryIsNullOrNegativeExcepetion {
        String query = "SELECT COUNT(*) FROM books WHERE status = 'disponibilita'";
        Integer count = null;
        try {
        	count = jdbcTemplate.queryForObject(query, Integer.class);
        	if(count == null || count < 0) {
        		throw new QueryIsNullOrNegativeExcepetion("errore grave nel cercare i libri non eliminati");
        	}
        	return count;
        }catch(DataAccessException ex) {
        	throw new BookRepositoryException("errore nel trovare i libri non eliminati");
        }
    }
}
