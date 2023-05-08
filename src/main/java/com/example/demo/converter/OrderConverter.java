package com.example.demo.converter;

import com.example.demo.dto.OrderDto;
import com.example.demo.models.Order;

import java.sql.Date;

public class OrderConverter {

    public static OrderDto orderModelToOrderDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setDate(new Date(System.currentTimeMillis()));
        orderDto.setCustomer(order.getCustomer());
        orderDto.setPaymentType(order.getPaymentType());
        return orderDto;
    }
}
