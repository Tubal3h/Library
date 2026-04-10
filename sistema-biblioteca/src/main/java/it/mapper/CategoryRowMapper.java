package it.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Category;

/**
 * RowMapper per la mappatura dei risultati del database in oggetti Category.
 */
@Component
public class CategoryRowMapper implements RowMapper<Category> {

    /**
     * Mappa una riga del ResultSet in un oggetto Category.
     * 
     * @param rs La ResultSet contenente i dati del database
     * @param rowNum Il numero della riga corrente
     * @return Oggetto Category mappato dai dati della riga
     * @throws SQLException Se si verifica un errore durante l'accesso ai dati della ResultSet
     */
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        return category;
    }
}
