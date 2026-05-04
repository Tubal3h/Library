package it.dto;

/**
 * Data Transfer Object per la gestione dei dati di CategoryDto.
 */
public class CategoryDto {
    private int categoryId;
    private String categoryName;

    public CategoryDto() {}

    public CategoryDto(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName != null ? categoryName.toLowerCase() : null;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
