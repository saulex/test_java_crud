package com.example.demo.converter;

import com.example.demo.dto.OrderProductDto;
import com.example.demo.models.OrderProductsValues;

public class OrderProductsValuesConverter {
    public static OrderProductDto convert(OrderProductsValues orderProductsValues){
        OrderProductDto result = new OrderProductDto();
        result.setId(orderProductsValues.getId());
        result.setQuantities(orderProductsValues.getQuantities());
        result.setTotal(orderProductsValues.getTotal());
        return result;
    }
}
