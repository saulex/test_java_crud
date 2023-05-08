package com.example.demo.unittest.controller;


import com.example.demo.controllers.CustomerController;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.ShippingAddressDto;
import com.example.demo.models.Customer;
import com.example.demo.models.ShippingAddress;
import com.example.demo.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private CustomerController customerController;

    @Test
    public void testGetCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");
        customer.setPhone("555-5555");
        customer.setEmail("johndoe@example.com");
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(1);
        shippingAddress.setStreet("123 Main St");
        shippingAddress.setNumberStreet("Apt 4B");
        shippingAddress.setCity("Anytown");
        shippingAddress.setZipCode(12345);
        shippingAddress.setCountry("USA");
        customer.setShippingAddress(shippingAddress);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        Mockito.when(customerService.getCustomer()).thenReturn((ArrayList<Customer>) customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("John Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone", Matchers.is("555-5555")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("johndoe@example.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shippingAddress.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shippingAddress.street", Matchers.is("123 Main St")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shippingAddress.numberStreet", Matchers.is("Apt 4B")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shippingAddress.city", Matchers.is("Anytown")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shippingAddress.zipCode", Matchers.is(12345)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shippingAddress.country", Matchers.is("USA")));
    }

}
