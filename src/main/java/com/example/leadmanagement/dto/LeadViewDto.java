package com.example.leadmanagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LeadViewDto {

    private long id;

    private double quantity;
    private double totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String customerName;
    private long customerId;
    private String productName;
    private long productId;
    private String salesAgentName;
    private long salesAgentId;



}
