package com.sandy.productcatalogservice.services;

import com.sandy.productcatalogservice.dtos.FakeStoreProductDTO;
import com.sandy.productcatalogservice.exceptions.ProductNotExistException;
import com.sandy.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Product> getAllProducts() {
        // /products
        ResponseEntity<FakeStoreProductDTO[]> fakeStoreProductDTOS = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class
        );
        if(fakeStoreProductDTOS.getBody() != null
        && fakeStoreProductDTOS.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            List<Product> productList = new ArrayList<>();
            for (FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOS.getBody()) {
                productList.add(fakeStoreProductDTO.toProduct());
            }
            return productList;
        }
        return null;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotExistException {
        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTO = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/{id}",
                FakeStoreProductDTO.class,
                id
        );
        if(fakeStoreProductDTO.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            if(fakeStoreProductDTO != null) {
                return fakeStoreProductDTO.getBody().toProduct();
            } else {
                throw new ProductNotExistException("Product does not exist");
            }
        } else {
            return null;
        }

    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }
}
