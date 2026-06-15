package com.sandy.productcatalogservice.services;

import com.sandy.productcatalogservice.clients.FakeStoreApiClient;
import com.sandy.productcatalogservice.dtos.FakeStoreProductDTO;
import com.sandy.productcatalogservice.exceptions.ProductNotExistException;
import com.sandy.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    FakeStoreApiClient fakeStoreApiClient;

    @Override
    public List<Product> getAllProducts() {
        // /products
        ResponseEntity<FakeStoreProductDTO[]> fakeStoreProductDTOS = fakeStoreApiClient.requestForEntity(
                HttpMethod.GET,
                "https://fakestoreapi.com/products",
                null,
                FakeStoreProductDTO[].class
        );
        if(fakeStoreApiClient.validateResponse(fakeStoreProductDTOS)) {
            List<Product> productList = new ArrayList<>();
            for (FakeStoreProductDTO fakeStoreProductDTO : Objects.requireNonNull(fakeStoreProductDTOS.getBody())) {
                productList.add(fakeStoreProductDTO.toProduct());
            }
            return productList;
        }
        return null;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotExistException {
        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTO = fakeStoreApiClient.requestForEntity(
                HttpMethod.GET,
                "https://fakestoreapi.com/products/{id}",
                null,
                FakeStoreProductDTO.class,
                id
        );
        if(fakeStoreApiClient.validateResponse(fakeStoreProductDTO)) {
            return Objects.requireNonNull(fakeStoreProductDTO.getBody()).toProduct();
        } else {
            return null;
        }

    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public  Product replaceProduct(Product product, Long id) {
            ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity = fakeStoreApiClient.requestForEntity(
                    HttpMethod.PUT,
                    "https://fakestoreapi.com/products/{id}",
                    product.toFakeStoreProductDTO(),
                    FakeStoreProductDTO.class,
                    id
            );

            if(fakeStoreApiClient.validateResponse(fakeStoreProductDTOResponseEntity)) {
                    return Objects.requireNonNull(fakeStoreProductDTOResponseEntity.getBody()).toProduct();
            }
    return  null;
    }
}
