package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Edition;
import it.entity.EditionJoin;
import it.mapper.EditionRowMapper;
import it.mapper.EditionJoinRowMapper;

/**
 * Repository per la gestione delle edizioni dei libri nel database.
 */
@Repository
public class EditionRepository {
    private final EditionRowMapper editionRowMapper;
    private final JdbcTemplate jdbcTemplate;
    private final EditionJoinRowMapper editionJoinRowMapper;
    
    /**
     * Costruttore per EditionRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     * @param editionRowMapper Mapper per convertire i record del database in oggetti Edition
     * @param editionJoinRowMapper Mapper per convertire i record del database in oggetti EditionJoin
     */
    public EditionRepository(JdbcTemplate jdbcTemplate, EditionRowMapper editionRowMapper, EditionJoinRowMapper editionJoinRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.editionRowMapper = editionRowMapper;
        this.editionJoinRowMapper = editionJoinRowMapper;
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
    	        e.book_id,
    	        bn.title,
    	        CONCAT(a.author_name, ' ', a.author_last_name) AS author_name,
    	        p.publisher_name,
    	        c.category_name,
    	        e.publishing_date,
    	        e.isbn,
    	        b.status
    	        FROM edition e
    	        JOIN books_names bn ON e.book_name_id = bn.book_name_id
    	        JOIN author a ON e.author_id = a.author_id
    	        JOIN publisher p ON e.publisher_id = p.publisher_id
    	        JOIN category c ON e.category_id = c.category_id
    	        LEFT JOIN books b ON e.edition_id = b.edition_id
    	        WHERE b.status != 'eliminato'
    	        GROUP BY e.edition_id, e.book_name_id, bn.title, a.author_name, a.author_last_name, p.publisher_name, c.category_name, e.publishing_date, e.isbn, b.status
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
}


