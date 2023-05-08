package com.example.demo.services;

import com.example.demo.converter.CustomerConverter;
import com.example.demo.converter.ShippingAddressConverter;
import com.example.demo.dto.CustomerDto;
import com.example.demo.models.Customer;
import com.example.demo.models.ShippingAddress;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.ShippingAddressRepository;
import com.example.demo.response.CustomerCreationException;
import org.hibernate.PersistentObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    public ArrayList<Customer> getCustomer() {
        ArrayList<Customer> customers = (ArrayList<Customer>) customerRepository.findAll();
        return customers;
    }

    @Transactional
    public Customer saveCustomer(CustomerDto customerDto) {
        Customer customer = CustomerConverter.convertDtoToCostumer(customerDto);
        if (customerDto.getShippingAddress().getId() == null) {
            ShippingAddress shippingAddress = ShippingAddressConverter.ShippingAddressDtoShippingAddress(customerDto.getShippingAddress());
            shippingAddress = shippingAddressRepository.save(shippingAddress);
            customer.setShippingAddress(shippingAddress);
        } else {
            customer.setShippingAddress(ShippingAddressConverter.ShippingAddressDtoShippingAddress(customerDto.getShippingAddress()));
        }

        return saveCustomer(customer);
    }

    public Customer saveCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (ConstraintViolationException e) {
            throw new CustomerCreationException("Error when customer was created",
                    "Integrity constraint violation error: " + e.getMessage(),
                    null,
                    null);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when creating the customer",
                    null,
                    "Data integrity violation error: " + e.getMessage(),
                    null);
        } catch (PersistentObjectException e) {
            throw new CustomerCreationException("Error when creating the customer",
                    null,
                    null,
                    "Persistent object error: " + e.getMessage());
        }

    }

    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    public boolean deleteCustomer(Integer id) {
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (ConstraintViolationException e) {
            throw new CustomerCreationException("Error when customer was delete",
                    "Integrity constraint violation error: " + e.getMessage(),
                    null,
                    null);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when delete the customer",
                    null,
                    "Data integrity violation error: " + e.getMessage(),
                    null);
        } catch (PersistentObjectException e) {
            throw new CustomerCreationException("Error when delete the customer",
                    null,
                    null,
                    "Persistent object error: " + e.getMessage());
        }
    }
}
