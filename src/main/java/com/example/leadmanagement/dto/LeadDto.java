package com.example.leadmanagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

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
