package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

/* -------------------------------------------------------------------------- */
/*                                  JAVA UTIL                                 */
/* -------------------------------------------------------------------------- */
import java.util.List;

/* -------------------------------------------------------------------------- */
/*                              SPRING FRAMEWORK                              */
/* -------------------------------------------------------------------------- */
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */
import it.entity.Category;

@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return Lista di tutte le categorie presenti nel database
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

