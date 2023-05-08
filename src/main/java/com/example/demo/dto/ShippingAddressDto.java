package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

public class ShippingAddressDto {
    @Getter
    @Setter
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

}
