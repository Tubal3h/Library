package it.repository;

import java.util.List;

import it.entity.Category;
import it.exception.InsertCategoryException;

public interface CategoryRepositoryInterface {
	public List<Category> getAllCategories();
	public void insertCategoryByNameCategory(String categoryName) throws InsertCategoryException;
	public int updateCategory(Category category);
	public Boolean isCategoryPresent(Category category);
	public Boolean isCategoryPresentByName(Category category);
}
