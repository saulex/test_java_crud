package com.example.demo.controllers;

import com.example.demo.dto.CustomerDto;
import com.example.demo.models.Customer;
import com.example.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@Validated
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping()
    public ResponseEntity<?> getCustomer() {
        try {
            ArrayList<Customer> customers = customerService.getCustomer();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = this.customerService.saveCustomer(customerDto);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Integer id) {
        Optional<Customer> customer = this.customerService.getCustomerById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);

    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        try {
            Customer customerSaved = this.customerService.saveCustomer(customer);
            return new ResponseEntity<>(customerSaved, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        try {
            this.customerService.deleteCustomer(id);
            return new ResponseEntity<String>("Customer was deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
