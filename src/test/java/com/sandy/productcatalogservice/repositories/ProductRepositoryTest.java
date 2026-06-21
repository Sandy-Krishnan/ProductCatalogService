package com.sandy.productcatalogservice.repositories;

import com.sandy.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testPriceBetween() {
       /* List<Product> productList = productRepository.findByPriceBetween(100.00, 500.00);
        System.out.println(productList);*/

        Product product = new Product();
        String desc = productRepository.getDescriptionWhereIdIs(product.getId() );
    }
}