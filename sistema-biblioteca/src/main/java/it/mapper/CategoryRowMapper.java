package it.mapper;

import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import it.model.Category;
@Component
public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        return category;
    }

}
