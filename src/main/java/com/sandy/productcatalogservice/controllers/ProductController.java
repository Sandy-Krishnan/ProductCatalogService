package com.sandy.productcatalogservice.controllers;

import com.sandy.productcatalogservice.dtos.ProductDTO;
import com.sandy.productcatalogservice.exceptions.ProductNotExistException;
import com.sandy.productcatalogservice.models.Product;
import com.sandy.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    @Qualifier("storageProductService")
    private IProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return productList.stream().map(Product::toProductDTO).toList();
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id")Long id) throws ProductNotExistException {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product.toProductDTO());
    }

    @PostMapping("/products")
    public ProductDTO createProduct(@RequestBody ProductDTO product) {
        Product product1 = product.toProduct();
        return productService.createProduct(product1).toProductDTO();
    }

    @PutMapping("/products/{id}")
    public ProductDTO replaceProduct(@PathVariable("id") Long productId,
                                                                     @RequestBody ProductDTO productDTO) {

        Product product = productService.replaceProduct(productDTO.toProduct(), productId);
        // return Result
        return product.toProductDTO();
    }

    //Local exception handling

    /*@ExceptionHandler(ProductNotExistException.class)
    public ResponseEntity<String> handleProductNotExistException(ProductNotExistException e) {
        return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_FOUND);
    }*/

}
