package com.example.demo.repositories;


import com.example.demo.models.OrderProductsValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderProductsValuesRepository extends CrudRepository<OrderProductsValues, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT  id , 0 as order_id , product_id , quantities , total FROM order_products u WHERE u.order_id = ?1")
    ArrayList<OrderProductsValues> findByOrderId(Integer orderId);

    Integer deleteByOrderId(Integer OrderId);

}
