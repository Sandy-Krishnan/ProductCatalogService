package com.sandy.productcatalogservice.exceptions;

public class ProductNotExistException extends Exception {
    public ProductNotExistException(String message) {
        super(message);
    }
}
