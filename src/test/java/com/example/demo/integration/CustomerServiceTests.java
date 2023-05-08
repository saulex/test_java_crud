package com.example.demo.integration;

import com.example.demo.models.Customer;
import com.example.demo.models.ShippingAddress;
import com.example.demo.response.CustomerCreationException;
import com.example.demo.services.CustomerService;
import com.example.demo.services.ShippinAddressService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@ActiveProfiles("testh2")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerServiceTests {

    @Autowired
    CustomerService customerService;

    @Test
    void testSaveAndGetCustomer() {

        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setPhone("22");
        customer1.setEmail("2email@j.co");
        customer1.setName("Test1");


        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setPhone("223");
        customer2.setEmail("2e2mail@j.co");

        Customer costumer3 = new Customer();
        costumer3.setId(3);
        costumer3.setPhone("2233");
        costumer3.setEmail("2e2mail22@j.co");

        ArrayList<Customer> expectedResult = new ArrayList<>();
        expectedResult.add(customer1);
        expectedResult.add(customer2);
        expectedResult.add(costumer3);

        customerService.saveCustomer(customer1);
        customerService.saveCustomer(customer2);
        customerService.saveCustomer(costumer3);

        ArrayList<Customer> result = customerService.getCustomer();

        assertEquals(expectedResult.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            Customer obj1 = expectedResult.get(i);
            Customer obj2 = result.get(i);
            assertEquals(obj1.getName(), obj2.getName());
            assertEquals(obj1.getEmail(), obj2.getEmail());
            assertEquals(obj1.getId(), obj2.getId());
        }

    }

    @Test
    void testSaveCustomerSameEmail() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setPhone("22");
        customer1.setEmail("email@j.co");
        customer1.setName("Test1");

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setPhone("223");
        customer2.setEmail("email@j.co");
        customerService.saveCustomer(customer1);
        Exception exception = assertThrows(CustomerCreationException.class, () -> {
            customerService.saveCustomer(customer2);
        });

    }

    @Test
    void testSaveCustomerSamePhone() {
        String expectedMsn = "Error when creating the customer  Data integrity violation error: Data integrity violation error: could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement ";
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setPhone("12345");
        customer1.setEmail("email@j.co");
        customer1.setName("Test1");

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setPhone("12345");
        customer2.setEmail("email2@j.co");
        customerService.saveCustomer(customer1);
        Exception exception = assertThrows(CustomerCreationException.class, () -> {
            customerService.saveCustomer(customer2);
        });

        assertEquals(expectedMsn.trim(), exception.getMessage().trim());

    }

}
