package com.example.demo.services;

import com.example.demo.models.ShippingAddress;
import com.example.demo.repositories.ShippingAddressRepository;
import com.example.demo.response.CustomerCreationException;
import org.hibernate.PersistentObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShippinAddressService {

    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    public ArrayList<ShippingAddress> saveShippingAddress() {
        return (ArrayList<ShippingAddress>) shippingAddressRepository.findAll();
    }

    public ShippingAddress saveShippingAddress(ShippingAddress usuario) {
        return shippingAddressRepository.save(usuario);
    }

    public Optional<ShippingAddress> getShippingAddressById(Integer id) {
        return shippingAddressRepository.findById(id);
    }

    public boolean deleteShippingAddress(Integer id) {
        try {
            shippingAddressRepository.deleteById(id);
            return true;
        } catch (
                ConstraintViolationException e) {
            throw new CustomerCreationException("Error when Shipping Address was deleted", "Integrity constraint violation error: " + e.getMessage(), null, null);
        } catch (
                DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when Shipping Address was deleted", null, "Data integrity violation error: " + e.getMessage(), null);
        } catch (
                PersistentObjectException e) {
            throw new CustomerCreationException("Error when Shipping Address was deleted", null, null, "Persistent object error: " + e.getMessage());
        }
    }
}
