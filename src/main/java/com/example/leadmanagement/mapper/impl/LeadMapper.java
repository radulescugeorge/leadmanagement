package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.LeadDto;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.Lead;
import org.springframework.stereotype.Component;

@Component
public class LeadMapper implements ObjectMapper<LeadDto, Lead> {
    @Override
    public LeadDto mapToDto(Lead lead) {
        return new LeadDto(
                lead.getId(),
                lead.getQuantity(),
                lead.getTotalAmount(),
                lead.getDate(),
                lead.getCustomer(),
                lead.getProduct(),
                lead.getSalesAgent()
        );
    }

    @Override
    public Lead mapToEntity(LeadDto leadDto) {
        Lead lead = new Lead();
        lead.setQuantity(leadDto.getQuantity());
        lead.setTotalAmount(leadDto.getTotalAmount()); // check if you need to apply discount here.
        lead.setDate(leadDto.getDate());

        return lead;
    }
}
