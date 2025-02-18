package com.example.leadmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class LoyaltyCardDto {
    private long id;
    private String serialNumber;
    private double discount;
}
