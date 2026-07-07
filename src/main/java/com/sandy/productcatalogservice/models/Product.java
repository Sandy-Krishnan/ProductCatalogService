package com.sandy.productcatalogservice.models;

import com.sandy.productcatalogservice.dtos.CategoryDTO;
import com.sandy.productcatalogservice.dtos.FakeStoreProductDTO;
import com.sandy.productcatalogservice.dtos.ProductDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class Product extends BaseModel {

    private String name;
    private String description;
    private double price;
    private String imageUrl;
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Category category;

    public Product() {

    }

    public Product(String name, String description, double price, String imageUrl, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }


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

    public FakeStoreProductDTO toFakeStoreProductDTO() {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(this.name);
        fakeStoreProductDTO.setId(this.getId());
        fakeStoreProductDTO.setCategory(this.category.getName());
        fakeStoreProductDTO.setPrice(this.price);
        fakeStoreProductDTO.setDescription(this.description);
        fakeStoreProductDTO.setImage(this.imageUrl);
        return fakeStoreProductDTO;
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
