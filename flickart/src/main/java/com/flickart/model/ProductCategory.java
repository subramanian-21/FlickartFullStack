package com.flickart.model;

public class ProductCategory {
    private String categoryName;
    private String category;
    private String categoryImage;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public ProductCategory(String categoryName, String category, String categoryImage) {
        this.categoryName = categoryName;
        this.category = category;
        this.categoryImage = categoryImage;
    }
}
