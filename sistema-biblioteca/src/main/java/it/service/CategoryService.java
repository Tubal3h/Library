package it.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.dto.CategoryDto;
import it.repository.CategoryRepository;
import it.entity.Category;

/**
 * Servizio per la gestione delle categorie dei libri.
 * Fornisce metodi per il recupero e la mappatura delle categorie in DTO.
 */
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    /**
     * Costruttore per CategoryService.
     * 
     * @param categoryRepository Repository per l'accesso ai dati delle categorie
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Recupera la lista di tutte le categorie registrate nel sistema.
     * 
     * @return Lista di CategoryDto
     */
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.getAllCategories().stream()
            .map(this::toCategoryDto)
            .toList();
    }

    /**
     * Converte un'entità Category in un DTO CategoryDto.
     * 
     * @param category L'entità da convertire
     * @return Il DTO corrispondente
     */
    private CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }
}
