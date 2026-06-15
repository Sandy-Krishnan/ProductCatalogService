package com.sandy.productcatalogservice.controllers;

import com.sandy.productcatalogservice.dtos.ProductDTO;
import com.sandy.productcatalogservice.exceptions.ProductNotExistException;
import com.sandy.productcatalogservice.models.Product;
import com.sandy.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(Product::toProductDTO)
                .toList();
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id")Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product.toProductDTO());
        }catch (ProductNotExistException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/products")
    public ProductDTO createProduct(@RequestBody ProductDTO product) {
        return null;
    }

}
