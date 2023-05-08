package com.example.demo.converter;

import com.example.demo.dto.CustomerDto;
import com.example.demo.models.Customer;

public class CustomerConverter {
    public static Customer convertDtoToCostumer(CustomerDto customerDto){
        Customer customerMode = new Customer();
        customerMode.setId(customerDto.getId());
        customerMode.setEmail(customerDto.getEmail());
        customerMode.setName(customerDto.getName());
        customerMode.setPhone(customerDto.getPhone());
        return customerMode;
    }
}
