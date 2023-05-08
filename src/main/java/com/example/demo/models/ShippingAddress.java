package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shipping_address")
@NoArgsConstructor
public class ShippingAddress {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(unique = true, nullable = false)
    private Integer id;

    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    private String numberStreet;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private Integer zipCode;

    @Getter
    @Setter
    private String country;

    @OneToOne(mappedBy = "shippingAddress")
    private Customer customer;

}
