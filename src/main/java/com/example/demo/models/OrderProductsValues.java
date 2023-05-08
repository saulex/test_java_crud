package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_products")
@NoArgsConstructor
public class OrderProductsValues {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Getter
    @Setter
    private Integer orderId;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Getter
    @Setter
    private Float quantities;

    @Getter
    @Setter
    private Float total;

}
