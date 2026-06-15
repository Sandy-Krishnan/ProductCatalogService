package com.sandy.productcatalogservice.services;

import com.sandy.productcatalogservice.dtos.FakeStoreProductDTO;
import com.sandy.productcatalogservice.exceptions.ProductNotExistException;
import com.sandy.productcatalogservice.models.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplate restTemplate;

    public <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request,
                                               Class<T> responseType, @Nullable Object... uriVariables) throws RestClientException {

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

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

    @Override
    public  Product replaceProduct(Product product, Long id) {
            ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity = putForEntity(
                    "https://fakestoreapi.com/products/{id}",
                    product.toFakeStoreProductDTO(),
                    FakeStoreProductDTO.class,
                    id
            );

            if(fakeStoreProductDTOResponseEntity.getBody() != null
                    && fakeStoreProductDTOResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
                    return fakeStoreProductDTOResponseEntity.getBody().toProduct();
            }
    return  null;
    }
}
