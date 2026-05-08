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
import it.exception.repository.EditionException;
import it.mapper.EditionRowMapper;
import it.mapper.response.EditionJoinResponseRowMapper;
import it.repository.interfaces.EditionRepositoryInterface;

/**
 * Repository per la gestione delle edizioni dei libri nel database.
 */
@Repository
public class EditionRepository implements EditionRepositoryInterface {
	private final EditionRowMapper editionRowMapper;
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final EditionJoinResponseRowMapper editionJoinRowMapper;

	/**
	 * Costruttore per EditionRepository.
	 * 
	 * @param jdbcTemplate         Il template JDBC per le operazioni sul database
	 * @param editionRowMapper     Mapper per convertire i record del database in
	 *                             oggetti Edition
	 * @param editionJoinRowMapper Mapper per convertire i record del database in
	 *                             oggetti EditionJoin
	 */
	public EditionRepository(JdbcTemplate jdbcTemplate, EditionRowMapper editionRowMapper,
			EditionJoinResponseRowMapper editionJoinRowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.editionRowMapper = editionRowMapper;
		this.editionJoinRowMapper = editionJoinRowMapper;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	/**
	 * Recupera la lista di tutte le edizioni dei libri con i nomi dei libri,
	 * autori, editori e categorie.
	 * 
	 * @return Lista di tutte le edizioni disponibili nel database
	 */
	public List<Edition> getAllEditions() throws EditionException{
		String sql = """
				SELECT
				    COUNT(b.book_id) AS quantity,
				    e.edition_id,
				    e.book_name_id,
				    e.author_id,
				    e.publisher_id,
				    e.category_id,
				    bn.title,
				    a.author_name,
				    a.author_last_name,
				    p.publisher_name,
				    c.category_name,
				    e.publishing_date,
				    e.isbn,
				    MIN(b.book_id) AS book_id,
				    MIN(b.status) AS status
				FROM edition e
				JOIN books_names bn ON e.book_name_id = bn.book_name_id
				JOIN author a ON e.author_id = a.author_id
				JOIN publisher p ON e.publisher_id = p.publisher_id
				JOIN category c ON e.category_id = c.category_id
				LEFT JOIN books b ON e.edition_id = b.edition_id AND b.status != 'eliminato'
				GROUP BY
				    e.edition_id,
				    e.book_name_id,
				    e.author_id,
				    e.publisher_id,
				    e.category_id,
				    bn.title,
				    a.author_name,
				    a.author_last_name,
				    p.publisher_name,
				    c.category_name,
				    e.publishing_date,
				    e.isbn
				""";
		try {
			return jdbcTemplate.query(sql, editionJoinRowMapper);
		} catch (DataAccessException e) {
			System.out.println("eccezione get all editions: " + e.getMessage());
			throw new EditionException().throwExceptionIfNotFound();
		}
	}

	/**
	 * Recupera un'edizione specifica tramite il suo ID.
	 * 
	 * @param editionId l'ID dell'edizione
	 * @return L'oggetto Edition corrispondente all'ID fornito
	 */
	public Edition findById(int editionId) throws EditionException{
		String sql = "SELECT * FROM edition WHERE edition_id = ?";
		
		try {
			return jdbcTemplate.queryForObject(sql, editionRowMapper, editionId);
		} catch (DataAccessException e) {
			new EditionException().throwExceptionIfEditionIdIsInvalid(editionId);
			throw new EditionException("Nessuna edizione trovata");
		}
	}

	/**
	 * Inserisce una nuova edizione nella tabella edition.
	 * Recupera gli ID necessari (titolo, autore, editore, categoria) tramite
	 * sottoquery.
	 *
	 * @param title          Titolo del libro
	 * @param authorId       ID dell'autore
	 * @param publisherId    ID della casa editrice
	 * @param publishingDate Data di pubblicazione
	 * @param categoryId     ID della categoria
	 * @param isbn           Codice ISBN dell'edizione
	 * @return Numero di righe inserite
	 */
	@Override
	public void insertEdition(
		String title, 
		String authorName, 
		String authorLastName, 
		String publisher,
		LocalDate publishingDate, 
		String category,
		String isbn) throws EditionException {
				
				System.out.println("Titolo: " + title);
		
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
			int n = namedParameterJdbcTemplate.update(insertEdition, sqlParameter);
			System.out.println("risultato edition:" + n);
		} catch (DataAccessException ex) {
			System.out.println(ex.toString());
			new EditionException().throwExceptionIfIsbnIsInvalid(isbn);
		}
	}

	/**
	 * Aggiorna il book name id di un'edizione.
	 * @param editionId l'id dell'edizione
	 * @param bookNameId l'id del book name
	 */
	@Override
	public void updateBookTitleId(int editionId, int bookNameId) throws EditionException{
		String sql = "UPDATE edition SET book_name_id = :bookNameId WHERE edition_id = :editionId";
		SqlParameterSource sqlParameter = new MapSqlParameterSource().addValue("bookNameId", bookNameId)
				.addValue("editionId", editionId);
		try {
			namedParameterJdbcTemplate.update(sql, sqlParameter);
		} catch (DataAccessException ex) {
			System.out.println(ex.toString());
			throw new EditionException().throwExceptionIfBookTitleIdIsInvalid(editionId, bookNameId);
		}
	}

	/**
	 * Aggiorna l'author id di un'edizione.
	 * @param editionId l'id dell'edizione
	 * @param authorId l'id dell'autore
	 */
	@Override
	public void updateAuthorId(int editionId, int authorId) throws EditionException{
		String sql = "UPDATE edition SET author_id = :authorId WHERE edition_id = :editionId";
		SqlParameterSource sqlParameter = new MapSqlParameterSource().addValue("authorId", authorId)
				.addValue("editionId", editionId);
		try{
			namedParameterJdbcTemplate.update(sql, sqlParameter);
		}catch(DataAccessException ex){
			System.out.println(ex.toString());
			throw new EditionException().throwExceptionIfBookAuthorIdIsInvalid(editionId, authorId);
		}
	}
	/**
	 * Aggiorna il publisher id di un'edizione.
	 * @param editionId l'id dell'edizione
	 * @param publisherId l'id del publisher
	 */
	@Override
	public void updatePublisherId(int editionId, int publisherId) throws EditionException{
		String sql = "UPDATE edition SET publisher_id = :publisherId WHERE edition_id = :editionId";
		SqlParameterSource sqlParameter = new MapSqlParameterSource().addValue("publisherId", publisherId)
				.addValue("editionId", editionId);
		try{
			namedParameterJdbcTemplate.update(sql, sqlParameter);
		}catch(DataAccessException ex){
			System.out.println(ex.toString());
			throw new EditionException().throwExceptionIfBookPublisherIdIsInvalid(editionId, publisherId);
		}
	}
	/**
	 * Aggiorna la category id di un'edizione.
	 * @param editionId l'id dell'edizione
	 * @param categoryId l'id della categoria
	 */
	@Override
	public void updateCategoryId(int editionId, int categoryId) throws EditionException{
		String sql = "UPDATE edition SET category_id = :categoryId WHERE edition_id = :editionId";
		SqlParameterSource sqlParameter = new MapSqlParameterSource().addValue("categoryId", categoryId)
				.addValue("editionId", editionId);
		try{
			namedParameterJdbcTemplate.update(sql, sqlParameter);
		}catch(DataAccessException ex){
			System.out.println(ex.toString());
			throw new EditionException().throwExceptionIfBookCategoryIdIsInvalid(editionId, categoryId);
		}
	}
}
