package com.sandy.productcatalogservice.services;

import com.sandy.productcatalogservice.exceptions.ProductNotExistException;
import com.sandy.productcatalogservice.models.Product;
import com.sandy.productcatalogservice.models.State;
import com.sandy.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("storageProductService")
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) throws ProductNotExistException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) {
            throw new ProductNotExistException("Product with id " +  id + " does not exist");
        } else {
            return product.get();
        }
    }

    @Override
    public Product createProduct(Product product) {
        if(product.getId() != null) {
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            if(optionalProduct.isPresent())
                throw new RuntimeException("Product with id : " + product.getId() + "already exist");
        }
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        return null;
    }

    @Override
    public boolean deleteProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new RuntimeException("Product with Id : " + id + "does not exist");
        } else {
            Product product = optionalProduct.get();
            product.setState(State.DELETED);
            productRepository.save(product);
            return true;
        }
    }
}
