package com.example.demo.integration;

import com.example.demo.converter.OrderProductsValuesConverter;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.OrderProductDto;
import com.example.demo.models.Customer;
import com.example.demo.models.OrderProductsValues;
import com.example.demo.models.Product;
import com.example.demo.response.CustomerCreationException;
import com.example.demo.services.CustomerService;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
@ActiveProfiles("testh2")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Test
    public void createOrderWitCustomerAndProduct(){

        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setPhone("12345");
        customer1.setEmail("email@j.co");
        customer1.setName("Test1");
        customerService.saveCustomer(customer1);

        Product product1 = new Product();
        product1.setId(1);
        product1.setPrice(2f);
        product1.setWeight(4.2f);
        product1.setDescription("Test1");
        productService.saveProduct(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setPrice(3f);
        product2.setWeight(3f);
        product2.setDescription("Test2");
        productService.saveProduct(product2);


        OrderProductsValues orderProducts1 = new OrderProductsValues();
        orderProducts1.setId(1);
        orderProducts1.setProduct(product1);
        orderProducts1.setQuantities(3f);

        OrderProductsValues orderProducts2 = new OrderProductsValues();
        orderProducts2.setId(1);
        orderProducts2.setProduct(product2);
        orderProducts2.setQuantities(3f);

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber("222");

        OrderProductDto orderProductsDto1 = OrderProductsValuesConverter.convert(orderProducts1);
        orderProductsDto1.setProduct(product1);
        orderDto.getProducts().add(orderProductsDto1);
        Float totalPrice1 = orderProductsDto1.getQuantities() * product1.getPrice();

        OrderProductDto orderProductsDto2 = OrderProductsValuesConverter.convert(orderProducts2);
        orderProductsDto2.setProduct(product2);
        orderDto.getProducts().add(orderProductsDto2);
        Float totalPrice2 = orderProductsDto2.getQuantities() * product2.getPrice();

        OrderDto resultSave = orderService.saveOrder(orderDto);
        Optional<OrderDto> result = orderService.getOrderById(resultSave.getId());
        assertEquals(true, result.isPresent());
        assertEquals(totalPrice1 + totalPrice2, result.get().getTotalAmount());

    }

    @Test
    public void createOrderWitCustomerAndInvalidProduct(){
        String expectedMsn = "Error when Order was created Integrity constraint violation error: Product not found id: 2 ";
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setPhone("12345");
        customer1.setEmail("email@j.co");
        customer1.setName("Test1");
        customerService.saveCustomer(customer1);

        Product product1 = new Product();
        product1.setId(1);
        product1.setPrice(2f);
        product1.setWeight(4.2f);
        product1.setDescription("Test1");
        productService.saveProduct(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setPrice(3f);
        product2.setWeight(3f);
        product2.setDescription("Test2");

        OrderProductsValues orderProducts1 = new OrderProductsValues();
        orderProducts1.setId(1);
        orderProducts1.setProduct(product1);
        orderProducts1.setQuantities(3f);

        OrderProductsValues orderProducts2 = new OrderProductsValues();
        orderProducts2.setId(1);
        orderProducts2.setProduct(product2);
        orderProducts2.setQuantities(3f);

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber("222");

        OrderProductDto orderProductsDto1 = OrderProductsValuesConverter.convert(orderProducts1);
        orderProductsDto1.setProduct(product1);
        orderDto.getProducts().add(orderProductsDto1);

        OrderProductDto orderProductsDto2 = OrderProductsValuesConverter.convert(orderProducts2);
        orderProductsDto2.setProduct(product2);
        orderDto.getProducts().add(orderProductsDto2);

        Exception exception = assertThrows(CustomerCreationException.class, () -> {
            OrderDto resultSave = orderService.saveOrder(orderDto);
        });

        assertEquals(expectedMsn.trim(), exception.getMessage().trim());

    }

    @Test
    public void createOrderWitCustomerWithOutProduct(){
        String expectedMsn = "Error when Order was created Integrity constraint violation error: Products order empty";
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setPhone("12345");
        customer1.setEmail("email@j.co");
        customer1.setName("Test1");
        customerService.saveCustomer(customer1);

        Product product1 = new Product();
        product1.setId(1);
        product1.setPrice(2f);
        product1.setWeight(4.2f);
        product1.setDescription("Test1");
        productService.saveProduct(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setPrice(3f);
        product2.setWeight(3f);
        product2.setDescription("Test2");

        OrderProductsValues orderProducts1 = new OrderProductsValues();
        orderProducts1.setId(1);
        orderProducts1.setProduct(product1);
        orderProducts1.setQuantities(3f);

        OrderProductsValues orderProducts2 = new OrderProductsValues();
        orderProducts2.setId(1);
        orderProducts2.setProduct(product2);
        orderProducts2.setQuantities(3f);

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber("222");

        Exception exception = assertThrows(CustomerCreationException.class, () -> {
            OrderDto resultSave = orderService.saveOrder(orderDto);
        });

        assertEquals(expectedMsn.trim(), exception.getMessage().trim());

    }

    @Test
    public void createOrderWitCustomerAndProductWithSameOrderCode(){
        String expectedMsn = "Error when creating the Order  Data integrity violation error: Data integrity violation error: could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement ";

        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setPhone("12345");
        customer1.setEmail("email@j.co");
        customer1.setName("Test1");
        customerService.saveCustomer(customer1);

        Product product1 = new Product();
        product1.setId(1);
        product1.setPrice(2f);
        product1.setWeight(4.2f);
        product1.setDescription("Test1");
        productService.saveProduct(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setPrice(3f);
        product2.setWeight(3f);
        product2.setDescription("Test2");
        productService.saveProduct(product2);


        OrderProductsValues orderProducts1 = new OrderProductsValues();
        orderProducts1.setId(1);
        orderProducts1.setProduct(product1);
        orderProducts1.setQuantities(3f);

        OrderProductsValues orderProducts2 = new OrderProductsValues();
        orderProducts2.setId(1);
        orderProducts2.setProduct(product2);
        orderProducts2.setQuantities(3f);

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber("222");

        OrderProductDto orderProductsDto1 = OrderProductsValuesConverter.convert(orderProducts1);
        orderProductsDto1.setProduct(product1);
        orderDto.getProducts().add(orderProductsDto1);
        Float totalPrice1 = orderProductsDto1.getQuantities() * product1.getPrice();

        OrderProductDto orderProductsDto2 = OrderProductsValuesConverter.convert(orderProducts2);
        orderProductsDto2.setProduct(product2);
        orderDto.getProducts().add(orderProductsDto2);
        Float totalPrice2 = orderProductsDto2.getQuantities() * product2.getPrice();

        OrderDto resultSave = orderService.saveOrder(orderDto);
        Optional<OrderDto> result = orderService.getOrderById(resultSave.getId());
        assertEquals(true, result.isPresent());
        assertEquals(totalPrice1 + totalPrice2, result.get().getTotalAmount());

        Exception exception = assertThrows(CustomerCreationException.class, () -> {
           orderService.saveOrder(orderDto);
        });
        assertEquals(expectedMsn.trim(), exception.getMessage().trim());
    }
}
