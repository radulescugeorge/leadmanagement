package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.SalesAgentDto;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import org.springframework.stereotype.Component;

@Component
public class SalesAgentMapper implements ObjectMapper<SalesAgentDto, SalesAgent> {
    @Override
    public SalesAgentDto mapToDto(SalesAgent salesAgent) {
        return new SalesAgentDto(
                salesAgent.getId(),
                salesAgent.getName(),
                salesAgent.getPhone(),
                salesAgent.getEmail()
        );
    }

    @Override
    public SalesAgent mapToEntity(SalesAgentDto salesAgentDto) {
        SalesAgent salesAgent = new SalesAgent();

        salesAgent.setEmail(salesAgentDto.getEmail());
        salesAgent.setName(salesAgentDto.getName());
        salesAgent.setPhone(salesAgentDto.getPhone());

        return salesAgent;

    }
}
