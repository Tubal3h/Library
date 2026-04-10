package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Edition;
import it.mapper.EditionRowMapper;

/**
 * Repository per la gestione delle edizioni dei libri nel database.
 */
@Repository
public class EditionRepository {
    private final EditionRowMapper editionRowMapper;
    private final JdbcTemplate jdbcTemplate;
    
    /**
     * Costruttore per EditionRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     * @param editionRowMapper Mapper per convertire i record del database in oggetti Edition
     */
    public EditionRepository(JdbcTemplate jdbcTemplate, EditionRowMapper editionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.editionRowMapper = editionRowMapper;
    }

    /**
     * Recupera la lista di tutte le edizioni dei libri.
     * 
     * @return Lista di tutte le edizioni disponibili nel database
     */
    public List<Edition> getAllEditions() {
        String sql = "SELECT * FROM edition";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Edition edition = new Edition();
            edition.setEditionId(rs.getInt("edition_id"));
            edition.setBookNameId(rs.getInt("book_name_id"));
            edition.setAuthorId(rs.getInt("author_id"));
            edition.setPublisherId(rs.getInt("publisher_id"));
            edition.setCategoryId(rs.getInt("category_id"));
            edition.setPublishingDate(rs.getDate("publishing_date").toLocalDate());
            edition.setIsbn(rs.getString("isbn"));
            return edition;
        });
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


