package com.example.demo.controllers;

import com.example.demo.models.OrderProductsValues;
import com.example.demo.services.OrderProductsValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/oderproduct")
public class OrderProductsValuesController {
    @Autowired
    OrderProductsValuesService orderProductsValuesService;

    @GetMapping()
    public ResponseEntity<?> getProducts() {
        try {
            ArrayList<OrderProductsValues> products = orderProductsValuesService.getOrderProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveProducts(@RequestBody OrderProductsValues product) {
        try {
            OrderProductsValues orderProductsValues = this.orderProductsValuesService.saveOrderProducts(product);
            return new ResponseEntity<>(orderProductsValues, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) {
        Optional<OrderProductsValues> orderProductsValues = this.orderProductsValuesService.getOrderProductsById(id);
        if (orderProductsValues.isPresent()) {
            return new ResponseEntity<>(orderProductsValues.get(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        try {
            this.orderProductsValuesService.deleteOrderProducts(id);
            return new ResponseEntity<String>("Order Products Values was deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
