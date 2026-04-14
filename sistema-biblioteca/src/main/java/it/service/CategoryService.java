package it.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.dto.CategoryDto;
import it.repository.CategoryRepository;
import it.entity.Category;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.getAllCategories().stream()
            .map(this::toCategoryDto)
            .toList();
    }

    private CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }
}
