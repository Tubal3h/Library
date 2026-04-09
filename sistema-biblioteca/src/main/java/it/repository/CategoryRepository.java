package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.Category;

/**
 * Repository per la gestione delle categorie dei libri nel database.
 */
@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Costruttore per CategoryRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera la lista di tutte le categorie presenti nel database.
     * 
     * @return Lista di tutte le categorie nel database
     */
    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            return category;
        });
    }
}


