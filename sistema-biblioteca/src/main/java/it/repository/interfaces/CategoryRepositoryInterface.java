package it.repository.interfaces;

import java.util.List;


import it.entity.Category;
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.CategoryRepositoryException;

public interface CategoryRepositoryInterface {
	public List<Category> getAllCategories()throws CategoryRepositoryException;
	public void insertCategoryByNameCategory(String categoryName) throws CategoryRepositoryException;
	public void updateCategory(Category category) throws CategoryRepositoryException;
	public Boolean isCategoryPresent(Category category) throws CategoryRepositoryException, QueryIsNullOrNegativeExcepetion;
	public Boolean isCategoryPresentByName(Category category) throws CategoryRepositoryException, QueryIsNullOrNegativeExcepetion;
	public void insertCategory(String categoryName) throws CategoryRepositoryException;
}
