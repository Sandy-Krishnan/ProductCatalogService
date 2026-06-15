package com.sandy.productcatalogservice.dtos;

import com.sandy.productcatalogservice.models.Category;
import com.sandy.productcatalogservice.models.Product;

public class FakeStoreProductDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private Double price;
    private String category;

    public Product toProduct() {
        Product  product = new Product();
        product.setId(this.id);
        product.setName(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setImageUrl(this.image);
        Category category = new Category();
        category.setName(this.category);
        product.setCategory(category);

        return product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
