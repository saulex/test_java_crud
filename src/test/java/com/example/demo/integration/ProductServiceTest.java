package com.example.demo.integration;

import com.example.demo.models.Customer;
import com.example.demo.models.Product;
import com.example.demo.services.CustomerService;
import com.example.demo.services.ProductService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("testh2")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Test
    public void testSaveAndGetProduct() {

        Product product1 = new Product();
        product1.setId(1);
        product1.setPrice(12f);
        product1.setWeight(2.2f);
        product1.setDescription("Test1");
        product1.setWeight(0.5f);

        Product product2 = new Product();
        product2.setId(2);
        product2.setPrice(22f);
        product2.setWeight(3.2f);
        product2.setDescription("Test1");
        product2.setWeight(1.5f);


        Product product3 = new Product();
        product3.setId(3);
        product3.setPrice(24f);
        product3.setWeight(6.2f);
        product3.setDescription("Test1");
        product3.setWeight(7.5f);

        ArrayList<Product> expectedResult = new ArrayList<>();
        expectedResult.add(product1);
        expectedResult.add(product2);
        expectedResult.add(product3);

        productService.saveProduct(product1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);

        ArrayList<Product> result = productService.getProducts();

        assertEquals(expectedResult.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            Product obj1 = expectedResult.get(i);
            Product obj2 = result.get(i);
            assertEquals(obj1.getDescription(), obj2.getDescription());
            assertEquals(obj1.getPrice(), obj2.getPrice());
            assertEquals(obj1.getId(), obj2.getId());
        }

    }
}
