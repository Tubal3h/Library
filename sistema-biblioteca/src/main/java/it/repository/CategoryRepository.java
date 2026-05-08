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
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.CategoryRepositoryException;
import it.mapper.CategoryRowMapper;
import it.repository.interfaces.CategoryRepositoryInterface;

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
    public List<Category> getAllCategories() throws CategoryRepositoryException{
        String sql = "SELECT * FROM category";
        try {
        	return jdbcTemplate.query(sql, categoryRowMapper);
        }catch(DataAccessException ex) {
        	throw new CategoryRepositoryException("non e' stata trovata nessuna categoria");
        }
    }

    /**
     * Inserisce una nuova categoria nel database.
     * 
     * @param categoryName Il nome della categoria da inserire
     * @return Il numero di righe modificate (dovrebbe essere 1 se l'inserimento ha successo)
     * @throws InsertCategoryException Se si verifica un'eccezione durante l'inserimento
     */

	@Override
	public void insertCategoryByNameCategory(String categoryName) throws CategoryRepositoryException{
		String insert = "INSERT INTO category (category_name) VALUES (:categoryName)";
		SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("categoryName", categoryName);
		try {
			namedParameterJdbcTemplate.update(insert, parameterSource);				
		}catch(DataAccessException ex){
			throw new CategoryRepositoryException("errore nell'inserimento della categoria");
		}
	}

    /**
     * Aggiorna il nome di una categoria nel database.
     * 
     * @param category La categoria da aggiornare
     * 
     * @throws Exception Se si verifica un'eccezione durante l'aggiornamento
     */

    @Override
    public void updateCategory(Category category) throws CategoryRepositoryException {
        String sql = "UPDATE category SET category_name = :category_name WHERE category_id = :category_id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("category_name", category.getCategoryName())
                .addValue("category_id", category.getCategoryId());
        try {
        	namedParameterJdbcTemplate.update(sql, parameterSource);	
        }catch(DataAccessException ex) {
        	throw new CategoryRepositoryException("errore nell'effettuare l'aggiornamento");
        }
    }

    /**
     * Verifica se una categoria è presente nel database.
     * 
     * @param category La categoria da verificare
     * @return true se la categoria è presente, false altrimenti
     */
    @Override
   
    public Boolean isCategoryPresent(Category category) throws CategoryRepositoryException, QueryIsNullOrNegativeExcepetion {
        String sql = "SELECT COUNT(*) FROM category WHERE category_id = :categoryId";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("categoryId", category.getCategoryId());
        Integer count = null;
        try {
        	count = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);	
        	if(count == null || count < 0) {
        		throw new QueryIsNullOrNegativeExcepetion("errore grave nel trovare la categoria");
        	}
        	return true;
        }catch(DataAccessException ex) {
        	throw new CategoryRepositoryException("categoria non presente");
        }
        
    }
   
    public Boolean isCategoryPresentByName(Category category) throws CategoryRepositoryException, QueryIsNullOrNegativeExcepetion {
        String sql = "SELECT COUNT(*) FROM category WHERE category_name = :categoryName";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("categoryName", category.getCategoryName());
        Integer count = null;
        try {
        	count = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);	
        	if(count == null || count < 0) {
        		throw new QueryIsNullOrNegativeExcepetion("errore grave nel trovare la categoria");
        	}
        	return true;
        }catch(DataAccessException ex) {
        	throw new CategoryRepositoryException("categoria non presente");
        }
    }

    @Override
	public void insertCategory(String categoryName) throws CategoryRepositoryException {
		String insert = "INSERT INTO category (category_name) VALUES (:categoryName)";
		SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("categoryName", categoryName);
		try {
			namedParameterJdbcTemplate.update(insert, parameterSource);				
		}catch(DataAccessException ex){
			throw new CategoryRepositoryException("errore nell'inserimento della categoria");
		}
	}
}


 