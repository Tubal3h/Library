package it.repository.interfaces;

import java.util.List;

import it.entity.Category;
import it.exception.repository.InsertCategoryException;

public interface CategoryRepositoryInterface {
	public List<Category> getAllCategories();
	public void insertCategoryByNameCategory(String categoryName) throws InsertCategoryException;
	public void updateCategory(Category category);
	public Boolean isCategoryPresent(Category category);
	public Boolean isCategoryPresentByName(Category category);
	public void insertCategory(String categoryName) throws InsertCategoryException;
}
