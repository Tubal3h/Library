package it.repository;

import java.util.List;

import it.entity.Category;
import it.exception.InsertCategoryException;

public interface CategoryRepositoryInterface {
	public List<Category> getAllCategories();
	public int insertCategoryByNameCategory(String categoryName) throws InsertCategoryException;
 }
