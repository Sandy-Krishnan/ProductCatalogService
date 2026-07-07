package com.sandy.productcatalogservice.controllers;

import com.sandy.productcatalogservice.dtos.ProductDTO;
import com.sandy.productcatalogservice.exceptions.ProductNotExistException;
import com.sandy.productcatalogservice.models.Category;
import com.sandy.productcatalogservice.models.Product;
import com.sandy.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductController productController;

    @MockitoBean(name = "storageProductService")
    private IProductService productService;

    @Test
    public void getAllProductSuccess() throws Exception {
        //AAA
        //Arrange
        Category category = new Category("category 1", "description 1");
        Category category1 = new Category("category 2", "description 2");
        Product product = new Product("apple", "iphone", 100.00, "abc.com", category);
        Product product1 = new Product("oneplus", "13r", 80.00, "plus.com", category1);
        List<Product> productList = Arrays.asList(product, product1);
        when(productService.getAllProducts()).thenReturn(productList);

        //Act
        List<ProductDTO> expectedProductDTO = productController.getAllProducts();

        //Assert
        assertEquals(2, expectedProductDTO.size());
        assertEquals("apple", expectedProductDTO.get(0).getName() );
        assertEquals("oneplus", expectedProductDTO.get(1).getName());
    }

    @Test
    public void getProductById_Failure() throws Exception{
        //AAA
        //Arrange
        Long productId = -1L;
        when(productService.getProductById(productId)).thenThrow(new ProductNotExistException("Product not found"));
        //Assert
        Exception exception = assertThrows(ProductNotExistException.class, () -> productController.getProductById(productId));
        assertEquals("Product not found", exception.getMessage());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void getProductById_success() throws  Exception  {
        //AAA
        //Arrange
        Long productId = 1L;
        Category category = new Category("category 1", "description 1");
        Product product = new Product("apple", "iphone", 100.00, "abc.com", category);
        when(productService.getProductById(productId)).thenReturn(product);

        //Act
        ResponseEntity<ProductDTO> expectedResult = productController.getProductById(productId);

        //Assert
        assertEquals("apple", Objects.requireNonNull(expectedResult.getBody()).getName());

    }

    @Test
    public void getProductById_Api_failure() throws Exception {
        //Arrange
        Long productId = -1L;
        when(productService.getProductById(productId)).thenThrow(new ProductNotExistException("Product not found"));

        //Act and Assert
        MvcResult mvcResult = mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Something went wrong"))
                .andReturn();

    }

    @Test
    public void getAllProducts_Api_success() throws  Exception {
        //Arrange
        Category category = new Category("category 1", "description 1");
        Category category1 = new Category("category 2", "description 2");
        Product product = new Product("apple", "iphone", 100.00, "abc.com", category);
        Product product1 = new Product("oneplus", "13r", 80.00, "plus.com", category1);
        List<Product> productList = Arrays.asList(product, product1);
        when(productService.getAllProducts()).thenReturn(productList);

        Object[] ele = {};

        // expectedResponse
        ProductDTO productDTO = product.toProductDTO();
        ProductDTO productDTO1 = product1.toProductDTO();
        List<ProductDTO> productDTOList = Arrays.asList(productDTO, productDTO1);

        String expectedResponse = objectMapper.writeValueAsString(productDTOList);
        System.out.println(expectedResponse);

        //Act and Assert

        MvcResult mvcResult = mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andExpect(result -> assertEquals(expectedResponse, result.getResponse().getContentAsString()))
                .andReturn();
    }


}