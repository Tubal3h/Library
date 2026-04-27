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

    public int getCategoryId(String name) {
        return categoryRepository.getAllCategories().stream()
                .filter(c -> c.getCategoryName().equalsIgnoreCase(name))
                .findFirst()
                .get()
                .getCategoryId();
    }

    public CategoryDto getCategoryById(int id) {
        return categoryRepository.getAllCategories().stream()
                .filter(c -> c.getCategoryId() == id)
                .findFirst()
                .map(this::toCategoryDto)
                .get();
    }

    @Transactional
    public int insertAndGetCategoryId(String name) {
        try {
            categoryRepository.insertCategoryByNameCategory(name);
        } catch (Exception e) {}
        return getCategoryId(name);
    }

    /**
     * Verifica se una categoria è presente nel database.
     * 
     * @param categoryDto Il nome della categoria da verificare
     * @return true se la categoria è presente, false altrimenti
     */
    @Transactional(readOnly = true)
    public boolean isCategoryPresent(CategoryDto categoryDto) {
        if(categoryDto == null) {
            return false;
        }
        Category category = toCategory(categoryDto);
        return categoryRepository.isCategoryPresent(category);
    }

    /**
     * Aggiorna una categoria nel database.
     * 
     * @param categoryDto Il DTO con i dati della categoria da aggiornare
     * @return Il numero di righe interessate
     */
    @Transactional
    public int updateCategory(CategoryDto categoryDto) {
        Category category = toCategory(categoryDto);
        return categoryRepository.updateCategory(category);
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

    /**
     * Converte un DTO CategoryDto in un'entità Category.
     * 
     * @param dto Il DTO da convertire
     * @return L'entità corrispondente
     */
    private Category toCategory(CategoryDto dto) {
        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        category.setCategoryName(dto.getCategoryName());
        return category;
    }
}
