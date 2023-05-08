package com.example.demo.unittest.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.controllers.OrderProductsValuesController;
import com.example.demo.controllers.ProductController;
import com.example.demo.models.Product;
import com.example.demo.services.OrderProductsValuesService;
import com.example.demo.services.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @Test
    public void testGetProducts() {
        // Arrange
        ArrayList<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1);
        product.setDescription("Product 1");
        product.setPrice(10.0f);
        products.add(product);

        product = new Product();
        product.setId(2);
        product.setDescription("Product 2");
        product.setPrice(20.0f);
        products.add(product);

        when(productService.getProducts()).thenReturn(products);

        // Act
        ResponseEntity<?> response = productController.getProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productService, times(1)).getProducts();
    }

    @Test
    public void testSaveProduct() {
        // Arrange
        Product product = new Product();
        product.setId(1);
        product.setDescription("Product 1");
        product.setPrice(10.0f);
        when(productService.saveProduct(product)).thenReturn(product);

        // Act
        ResponseEntity<?> response = productController.saveProducts(product);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    public void testSaveProductWithException() {
        // Arrange
        Product product = new Product();
        product.setId(1);
        product.setDescription("Product 1");
        String errorMessage = "Error saving product";
        when(productService.saveProduct(product)).thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<?> response = productController.saveProducts(product);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    public void testGetProductById() {
        // Arrange
        int productId = 1;
        Product product = new Product();
        product.setId(1);
        product.setDescription("Product 1");
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<?> response = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testGetProductByIdNotFound() {
        // Arrange
        int productId = 1;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        int productId = 1;
        when(productService.deleteProduct(productId)).thenReturn(true);

        // Act
        ResponseEntity<?> response = productController.deleteById(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product was deleted", response.getBody());
        verify(productService, times(1)).deleteProduct(productId);
    }
}