package com.example.demo.models;

import com.example.demo.models.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Getter
    @Setter
    @Column(unique = true)
    private String orderNumber;

    @Getter
    @Setter
    private Date date;
    @ManyToOne
    @JoinColumn(name = "customer_id")

    @Getter
    @Setter
    private Customer customer;


    @Getter
    @Setter
    private PaymentType paymentType;

}
