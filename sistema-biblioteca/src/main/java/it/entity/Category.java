package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta una categoria di libri nel sistema.
 */
public class Category {
    private int categoryId;
    private String categoryName;

    /**
     * Costruttore di default.
     */
    public Category() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param categoryName Nome della categoria
     */
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return ID della categoria
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId ID della categoria
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return Nome della categoria
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName Nome della categoria
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
    }
}


