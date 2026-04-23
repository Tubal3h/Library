package it.repository;

import java.time.LocalDate;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.entity.Edition;
import it.entity.EditionJoin;
import it.exception.InsertEditionException;
import it.mapper.EditionRowMapper;
import it.mapper.EditionJoinRowMapper;

/**
 * Repository per la gestione delle edizioni dei libri nel database.
 */
@Repository
public class EditionRepository implements EditionRepositoryInterface {
    private final EditionRowMapper editionRowMapper;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final EditionJoinRowMapper editionJoinRowMapper;
    
    /**
     * Costruttore per EditionRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     * @param editionRowMapper Mapper per convertire i record del database in oggetti Edition
     * @param editionJoinRowMapper Mapper per convertire i record del database in oggetti EditionJoin
     */
    public EditionRepository(JdbcTemplate jdbcTemplate, EditionRowMapper editionRowMapper, EditionJoinRowMapper editionJoinRowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.editionRowMapper = editionRowMapper;
        this.editionJoinRowMapper = editionJoinRowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Recupera la lista di tutte le edizioni dei libri con i nomi dei libri, autori, editori e categorie.
     * 
     * @return Lista di tutte le edizioni disponibili nel database
     */
    public List<EditionJoin> getAllEditions() {
    	String sql = """
    	        SELECT
    	        COUNT(b.book_id) AS quantity,
    	        e.edition_id,
    	        MIN(b.book_id) AS book_id,
    	        bn.title AS book_name,
    	        CONCAT(a.author_name, ' ', a.author_last_name) AS author_name,
    	        p.publisher_name,
    	        c.category_name,
    	        e.publishing_date,
    	        e.isbn,
    	        MIN(b.status) AS status
    	        FROM edition e
    	        JOIN books_names bn ON e.book_name_id = bn.book_name_id
    	        JOIN author a ON e.author_id = a.author_id
    	        JOIN publisher p ON e.publisher_id = p.publisher_id
    	        JOIN category c ON e.category_id = c.category_id
    	        LEFT JOIN books b ON e.edition_id = b.edition_id AND b.status != 'eliminato'
    	        GROUP BY 
    	        e.edition_id,
    	        bn.title, 
    	        a.author_name, 
    	        a.author_last_name, 
    	        p.publisher_name, 
    	        c.category_name, 
    	        e.publishing_date, 
    	        e.isbn
    	        """;
        
        return jdbcTemplate.query(sql, editionJoinRowMapper);
    }

    /**
     * Recupera un'edizione specifica tramite il suo ID.
     * 
     * @param editionId l'ID dell'edizione
     * @return L'oggetto Edition corrispondente all'ID fornito
     */
    public Edition getEditionById(int editionId) {
        String sql = "SELECT * FROM edition WHERE edition_id = ?";
        return jdbcTemplate.queryForObject(sql, editionRowMapper, editionId);
    }

    /**
     * Inserisce una nuova edizione nella tabella edition.
     * Recupera gli ID necessari (titolo, autore, editore, categoria) tramite sottoquery.
     *
     * @param title Titolo del libro
     * @param authorId ID dell'autore
     * @param publisherId ID della casa editrice
     * @param publishingDate Data di pubblicazione
     * @param categoryId ID della categoria
     * @param isbn Codice ISBN dell'edizione
     * @return Numero di righe inserite
     */
	@Override
	public int insertEdition(String title, String authorName, String authorLastName, String publisher, LocalDate publishingDate, String category,
			String isbn) throws InsertEditionException {
		String insertEdition = "INSERT INTO edition (book_name_id, author_id, publisher_id, publishing_date, category_id, isbn)\r\n"
							 + "VALUES((SELECT book_name_id FROM books_names WHERE title = :title),\r\n"
							 + "(SELECT author_id FROM author WHERE author_name = :authorName AND author_last_name = :authorLastName),\r\n"
							 + "(SELECT publisher_id FROM publisher WHERE publisher_name = :publisher),\r\n"
							 + "(:publishingDate),\r\n"
							 + "(SELECT category_id FROM category WHERE category_name = :category),\r\n"
							 + "(:isbn))";
		
		SqlParameterSource sqlParameter = new MapSqlParameterSource().addValue("title", title)
																	 .addValue("authorName", authorName)
																	 .addValue("authorLastName", authorLastName) 
																	 .addValue("publisher", publisher) 
																	 .addValue("publishingDate", publishingDate)
																	 .addValue("category", category) 
																	 .addValue("isbn", isbn);
		try {
			int res = namedParameterJdbcTemplate.update(insertEdition, sqlParameter);
			return res;
		}catch(DataAccessException ex) {
			throw new InsertEditionException("errore nell'inserimento dell'edizione del libro");
		}
	} 
}


