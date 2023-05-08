package com.example.demo.unittest.controller;


import com.example.demo.controllers.OrderProductsValuesController;
import com.example.demo.models.OrderProductsValues;
import com.example.demo.services.OrderProductsValuesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProductsValuesControllerTest {

    @Mock
    OrderProductsValuesService orderProductsValuesService;

    @InjectMocks
    OrderProductsValuesController orderProductsValuesController;

    @Test
    void getProducts_ReturnsListOfProducts() {
        // Arrange
        when(orderProductsValuesService.getOrderProducts()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> response = orderProductsValuesController.getProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ArrayList<>(), response.getBody());
        verify(orderProductsValuesService, times(1)).getOrderProducts();
    }

    @Test
    void saveProducts_ReturnsSavedProduct() {
        // Arrange
        OrderProductsValues product = new OrderProductsValues();
        when(orderProductsValuesService.saveOrderProducts(any(OrderProductsValues.class))).thenReturn(product);

        // Act
        ResponseEntity<?> response = orderProductsValuesController.saveProducts(product);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(orderProductsValuesService, times(1)).saveOrderProducts(any(OrderProductsValues.class));
    }

    @Test
    void saveProducts_ReturnsInternalServerError_WhenExceptionIsThrown() {
        // Arrange
        OrderProductsValues product = new OrderProductsValues();
        when(orderProductsValuesService.saveOrderProducts(any(OrderProductsValues.class)))
                .thenThrow(new RuntimeException("Error saving product"));

        // Act
        ResponseEntity<?> response = orderProductsValuesController.saveProducts(product);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error saving product", response.getBody());
        verify(orderProductsValuesService, times(1)).saveOrderProducts(any(OrderProductsValues.class));
    }

    @Test
    void getProductById_ReturnsNotFound_WhenProductIsNotFound() {
        // Arrange
        when(orderProductsValuesService.getOrderProductsById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = orderProductsValuesController.getProductById(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(orderProductsValuesService, times(1)).getOrderProductsById(1);
    }
}