package com.example.demo.controllers;

import com.example.demo.models.ShippingAddress;
import com.example.demo.services.ShippinAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/addressshipping")
public class ShippingAddressController {
    @Autowired
    ShippinAddressService shippingAddressService;

    @GetMapping()
    public ResponseEntity<?> getShippingAddress() {
        try {
            ArrayList<ShippingAddress> shippingAddresses = shippingAddressService.saveShippingAddress();
            return new ResponseEntity<>(shippingAddresses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveShippingAddress(@RequestBody ShippingAddress shippingAddress) {
        try {
            ShippingAddress shippingAddressResult = this.shippingAddressService.saveShippingAddress(shippingAddress);
            return new ResponseEntity<>(shippingAddressResult, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getShippingAddressById(@PathVariable("id") Integer id) {
        Optional<ShippingAddress> shippingAddress = this.shippingAddressService.getShippingAddressById(id);
        if (shippingAddress.isPresent()) {
            return new ResponseEntity<>(shippingAddress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        try {
            this.shippingAddressService.deleteShippingAddress(id);
            return new ResponseEntity<String>("Order Products Values was deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



