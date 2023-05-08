package com.example.demo.services;

import com.example.demo.models.Order;
import com.example.demo.models.OrderProductsValues;
import com.example.demo.models.Product;
import com.example.demo.repositories.OrderProductsValuesRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.response.CustomerCreationException;
import org.hibernate.PersistentObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class OrderProductsValuesService {
    @Autowired
    OrderProductsValuesRepository orderProductsValuesRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;

    public ArrayList<OrderProductsValues> getOrderProducts() {
        return (ArrayList<OrderProductsValues>) orderProductsValuesRepository.findAll();
    }

    public OrderProductsValues saveOrderProducts(OrderProductsValues orderProducts) {
        try {
            Optional<Product> product = productRepository.findById(orderProducts.getId());
            Optional<Order> order = orderRepository.findById(orderProducts.getOrderId());
            if (!order.isPresent()){
                throw new CustomerCreationException("Error Order not found", null, null, null);
            }
            if (!product.isPresent()){
                throw new CustomerCreationException("Error Products empty", null, null, null);
            }
            Float total = product.get().getPrice() * orderProducts.getQuantities();
            orderProducts.setTotal(total);
            return orderProductsValuesRepository.save(orderProducts);

        } catch (ConstraintViolationException e) {
            throw new CustomerCreationException("Error when Order was created", "Integrity constraint violation error: " + e.getMessage(), null, null);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when creating the Order", null, "Data integrity violation error: " + e.getMessage(), null);
        } catch (PersistentObjectException e) {
            throw new CustomerCreationException("Error when creating the Order", null, null, "Persistent object error: " + e.getMessage());
        }

    }

    public Optional<OrderProductsValues> getOrderProductsById(Integer id) {
        return orderProductsValuesRepository.findById(id);
    }

    public boolean deleteOrderProducts(Integer id) {
        try {
            orderProductsValuesRepository.deleteById(id);
            return true;
        } catch (ConstraintViolationException e) {
            throw new CustomerCreationException("Error when Order Product was deleted", "Integrity constraint violation error: " + e.getMessage(), null, null);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when Order Product was deleted", null, "Data integrity violation error: " + e.getMessage(), null);
        } catch (PersistentObjectException e) {
            throw new CustomerCreationException("Error when Order Product was deleted", null, null, "Persistent object error: " + e.getMessage());
        }
    }

    public  ArrayList<OrderProductsValues> findByOrderId(Integer orderId){
        return orderProductsValuesRepository.findByOrderId(orderId);
    }
}
