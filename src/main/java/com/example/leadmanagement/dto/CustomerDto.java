package com.example.leadmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {
    private long id;
    private String name;
    private String city;
    private String phone;
    private String email;
    private LoyaltyCardDto loyaltyCard;
}
