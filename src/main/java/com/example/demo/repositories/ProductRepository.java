package com.example.demo.repositories;

import com.example.demo.models.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query( nativeQuery = true,
            value = "SELECT  id , 0 as order_id , product_id , quantities , total FROM order_produts u WHERE u.order_id = ?1")
    public abstract ArrayList<Product> findByOrderId(Integer orderId);

}