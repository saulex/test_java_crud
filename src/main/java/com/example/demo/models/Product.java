package com.example.demo.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
public class Product {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Getter
    @Setter
    private String description;

    @Column(unique = true)
    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    @Column(nullable = false)
    private Float price;

    @Getter
    @Setter
    @Column(nullable = false)
    private Float weight;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private OrderProductsValues orderProductsValues;

}