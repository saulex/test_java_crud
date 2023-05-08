package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "customer")
@NoArgsConstructor
public class Customer {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String phone;

    @Getter
    @Setter
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "primary_shipping_address_id", referencedColumnName = "id")
    private ShippingAddress shippingAddress;

}
