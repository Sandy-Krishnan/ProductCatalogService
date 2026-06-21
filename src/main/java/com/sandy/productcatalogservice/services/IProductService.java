package com.sandy.productcatalogservice.services;

import com.sandy.productcatalogservice.exceptions.ProductNotExistException;
import com.sandy.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id) throws ProductNotExistException;

    Product createProduct(Product product);

    Product replaceProduct(Product product, Long id);

    boolean deleteProductById(Long id);
}
