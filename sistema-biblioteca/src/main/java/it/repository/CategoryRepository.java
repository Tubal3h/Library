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

	@Override
	public int insertCategoryByNameCategory(String categoryName) throws InsertCategoryException{
		String insert = "INSERT INTO category (category_name) VALUES (:categoryName)";
		SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("categoryName", categoryName);
		try {
			int res = namedParameterJdbcTemplate.update(insert, parameterSource);
			return res;	
		}catch(DataAccessException ex){
			throw new InsertCategoryException("errore nell'inserimento della categoria");
		}
	}
}


 