package com.example.demo.dto;

import com.example.demo.models.Customer;
import com.example.demo.models.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class OrderDto {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String orderNumber;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    private PaymentType paymentType;

    @Getter
    @Setter
    private Float totalAmount;

    @Getter
    @Setter
    private Float totalWeight;

    @Getter
    @Setter
    private List<OrderProductDto> products = new ArrayList<>();

}
