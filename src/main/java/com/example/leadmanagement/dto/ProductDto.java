package com.example.leadmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ProductDto {
    private long id;
    private String name;
    private double price;
}
