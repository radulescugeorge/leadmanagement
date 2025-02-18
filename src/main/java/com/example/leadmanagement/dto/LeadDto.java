package com.example.leadmanagement.dto;


import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

public class LeadDto {
    private long id;
    private double quantity;
    private double totalAmount;
    private LocalDateTime date;
    private Customer customer;
    private Product product;
    private SalesAgent salesAgent;
}
