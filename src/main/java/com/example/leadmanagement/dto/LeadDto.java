package com.example.leadmanagement.dto;


import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LeadDto {

    private long id;

    private double quantity;
    private double totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private long customerId;
    private long productId;
    private long salesAgentId;

}
