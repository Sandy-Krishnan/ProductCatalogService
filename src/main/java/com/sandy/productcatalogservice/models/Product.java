package com.sandy.productcatalogservice.models;

import com.sandy.productcatalogservice.dtos.CategoryDTO;
import com.sandy.productcatalogservice.dtos.ProductDTO;

public class Product extends BaseModel {

    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private Category category;

    public ProductDTO toProductDTO() {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(this.name);
        productDTO.setDescription(this.description);
        productDTO.setPrice(this.price);
        productDTO.setImageUrl(this.imageUrl);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(this.category.getName());
        categoryDTO.setDescription(this.category.getDescription());

        productDTO.setCategoryName(categoryDTO);

        return productDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }




}
