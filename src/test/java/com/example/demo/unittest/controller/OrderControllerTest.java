package com.example.demo.unittest.controller;

import com.example.demo.controllers.OrderController;
import com.example.demo.dto.OrderDto;
import com.example.demo.models.Order;
import com.example.demo.services.OrderService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void getOrdersTest() {
        // Arrange
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order());
        when(orderService.getOrders()).thenReturn(orders);

        // Act
        ResponseEntity<?> response = orderController.getOrders();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
        verify(orderService, times(1)).getOrders();
    }


    @Test
    void getCustomerByIdTest() {
        // Arrange
        Optional<OrderDto> order = Optional.of(new OrderDto());
        when(orderService.getOrderById(anyInt())).thenReturn(order);

        // Act
        ResponseEntity<?> response = orderController.getCustomerById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).getOrderById(1);
    }

    @Test
    void getCustomerByIdNotFoundTest() {
        // Arrange
        Optional<OrderDto> order = Optional.empty();
        when(orderService.getOrderById(anyInt())).thenReturn(order);

        // Act
        ResponseEntity<?> response = orderController.getCustomerById(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).getOrderById(1);
    }

    @Test
    void deleteByIdTest() {
        // Arrange
        when(orderService.deleteOrder(anyInt())).thenReturn(true);

        // Act
        ResponseEntity<?> response = orderController.deleteById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order was deleted", response.getBody());
        verify(orderService, times(1)).deleteOrder(1);
    }
}
