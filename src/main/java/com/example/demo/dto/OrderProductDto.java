package com.example.demo.dto;

import com.example.demo.models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
public class OrderProductDto {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private Float quantities;

    @Getter
    @Setter
    private Float total;

    @Getter
    @Setter
    private Product product;

}
