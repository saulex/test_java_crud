package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.models.Product;


import com.example.demo.repositories.ProductRepository;
import com.example.demo.response.CustomerCreationException;
import org.hibernate.PersistentObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ArrayList<Product> getProducts() {
        return (ArrayList<Product>) productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (ConstraintViolationException e) {
            throw new CustomerCreationException("Error when product was created", "Integrity constraint violation error: " + e.getMessage(), null, null);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when product the Order", null, "Data integrity violation error: " + e.getMessage(), null);
        } catch (PersistentObjectException e) {
            throw new CustomerCreationException("Error when product the Order", null, null, "Persistent object error: " + e.getMessage());
        }
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public boolean deleteProduct(Integer id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (ConstraintViolationException e) {
            throw new CustomerCreationException("Error when product was created", "Integrity constraint violation error: " + e.getMessage(), null, null);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when product the Order", null, "Data integrity violation error: " + e.getMessage(), null);
        } catch (PersistentObjectException e) {
            throw new CustomerCreationException("Error when product the Order", null, null, "Persistent object error: " + e.getMessage());
        }
    }
}
