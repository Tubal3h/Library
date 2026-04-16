package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.entity.BookJoin;
import it.mapper.BookJoinRowMapper;

/**
 * Repository per la gestione dei dati dei libri nel database.
 * Esegue query aggregate con JOIN per recuperare le informazioni complete dei libri.
 */
@Repository
public class BookRepository implements BookRepositoryInterface{

    private final JdbcTemplate jdbcTemplate;
    private final BookJoinRowMapper bookJoinMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    

    /**
     * Costruttore per BookRepository.
     *
     * @param jdbcTemplate  Il template JDBC per le operazioni sul database
     * @param bookJoinMapper Mapper per convertire i record del database in oggetti BookJoin
     */
    public BookRepository(JdbcTemplate jdbcTemplate, BookJoinRowMapper bookJoinMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookJoinMapper = bookJoinMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        
    }

    /**
     * Recupera il nome completo dell'autore tramite il suo ID.
     *
     * @param authorId ID dell'autore
     * @return Nome completo dell'autore (nome + cognome concatenati)
     */
    public String getAuthorFullNameByID(int authorId) {
        String sql = "SELECT CONCAT(author_name, ' ', author_last_name) AS author_full_name FROM author WHERE author_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, authorId);
    }

    /**
     * Recupera il nome della casa editrice tramite il suo ID.
     *
     * @param publisherId ID della casa editrice
     * @return Nome della casa editrice
     */
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
    public String getCategoryNameByID(int categoryId) {
        String sql = "SELECT category_name FROM category WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, categoryId);
    }

    /**
     * Conta il numero totale di libri fisici nel sistema.
     *
     * @return Numero totale di libri presenti nella tabella books
     */
    public int countBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Recupera la lista completa dei libri con tutte le informazioni aggregate.
     * Utilizza una JOIN tra books, edition, books_names, author, publisher e category.
     *
     * @return Lista di oggetti {@link BookJoin} con i dati completi di ogni libro
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
    
    /**
     * Inserisce una nuova copia fisica nella tabella books tramite il codice ISBN dell'edizione.
     * La copia viene inizializzata con lo stato 'disponibilita'.
     *
     * @param isbn Codice ISBN dell'edizione
     * @return Numero di record inseriti
     */
	public int insertBookByIsbn(String isbn) {
		String query = "INSERT INTO books(edition_id, status)\r\n"
					 + "VALUES((SELECT edition_id FROM edition WHERE isbn = :isbn), \r\n"
				     + "('disponibilita'))";
		
		SqlParameterSource sqlParameters = new MapSqlParameterSource().addValue("isbn", isbn);
		int res = namedParameterJdbcTemplate.update(query, sqlParameters);
		return res;
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
     * Utilizzato durante la creazione di una nuova edizione per aggiungere la prima copia.
     *
     * @param title Titolo del libro
     * @return Numero di record inseriti
     */
	public int insertBookByTitle(String title) {
		String insertBook = "INSERT INTO books (edition_id, status)\r\n"
						  + "VALUES((SELECT edition_id FROM edition INNER JOIN books_names ON edition.book_name_id = books_names.book_name_id WHERE title = :title),\r\n"
						  + "('disponibilita'));";
		
		SqlParameterSource sqlParameter = new MapSqlParameterSource().addValue("title", title);
		int res = namedParameterJdbcTemplate.update(insertBook, sqlParameter);
		return res;
	}

}
