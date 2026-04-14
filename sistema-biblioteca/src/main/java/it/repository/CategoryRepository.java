package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Category;
import it.mapper.CategoryRowMapper;

/**
 * Repository per la gestione delle categorie dei libri nel database.
 */
@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CategoryRowMapper categoryRowMapper;

    /**
     * Costruttore per CategoryRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public CategoryRepository(JdbcTemplate jdbcTemplate, CategoryRowMapper categoryRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRowMapper = categoryRowMapper;
    }

    /**
     * Recupera la lista di tutte le categorie presenti nel database.
     * 
     * @return Lista di tutte le categorie nel database
     */
    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, categoryRowMapper);
    }
}


