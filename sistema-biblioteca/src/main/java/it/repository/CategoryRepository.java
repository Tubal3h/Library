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
import org.springframework.stereotype.Repository;

import it.entity.Category;
import it.exception.InsertCategoryException;
import it.mapper.CategoryRowMapper;

/**
 * Repository per la gestione delle categorie dei libri nel database.
 */
@Repository
public class CategoryRepository implements CategoryRepositoryInterface{
    private final JdbcTemplate jdbcTemplate;
    private final CategoryRowMapper categoryRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Costruttore per CategoryRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public CategoryRepository(JdbcTemplate jdbcTemplate, CategoryRowMapper categoryRowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRowMapper = categoryRowMapper;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        
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

    /**
     * Inserisce una nuova categoria nel database.
     * 
     * @param categoryName Il nome della categoria da inserire
     * @return Il numero di righe modificate (dovrebbe essere 1 se l'inserimento ha successo)
     * @throws InsertCategoryException Se si verifica un'eccezione durante l'inserimento
     */

	@Override
	public void insertCategoryByNameCategory(String categoryName) throws InsertCategoryException{
		String insert = "INSERT INTO category (category_name) VALUES (:categoryName)";
		SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("categoryName", categoryName);
		try {
			namedParameterJdbcTemplate.update(insert, parameterSource);				
		}catch(DataAccessException ex){
			throw new InsertCategoryException("errore nell'inserimento della categoria");
		}
	}

    /**
     * Aggiorna il nome di una categoria nel database.
     * 
     * @param category La categoria da aggiornare
     * @return Il numero di righe modificate (dovrebbe essere 1 se l'aggiornamento ha successo)
     */

    @Override
    public int updateCategory(Category category) {
        String sql = "UPDATE category SET category_name = :category_name WHERE category_id = :category_id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("category_name", category.getCategoryName())
                .addValue("category_id", category.getCategoryId());
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    /**
     * Verifica se una categoria è presente nel database.
     * 
     * @param category La categoria da verificare
     * @return true se la categoria è presente, false altrimenti
     */
    @Override
   
    public Boolean isCategoryPresent(Category category) {
        String sql = "SELECT COUNT(*) FROM category WHERE category_id = :categoryId";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("categoryId", category.getCategoryId());
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
        return count != null && count > 0;
    }
   
    public Boolean isCategoryPresentByName(Category category) {
        String sql = "SELECT COUNT(*) FROM category WHERE category_name = :categoryName";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("categoryName", category.getCategoryName());
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
        return count != null && count > 0;
    }
}


 