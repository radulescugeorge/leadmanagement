package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.SalesAgentDto;
import com.example.leadmanagement.exception_handlers.InvalidDataException;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import org.springframework.stereotype.Component;

import static com.example.leadmanagement.global_validator.GlobalValidator.isValidEmailGV;
import static com.example.leadmanagement.global_validator.GlobalValidator.isValidPhoneGV;

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

        String dtoEmail = salesAgentDto.getEmail();
        if (!isValidEmailGV(dtoEmail)) {
            throw new InvalidDataException("Invalid email format: [" + dtoEmail
                    + "]. Email should be x@x.x");
        }
        salesAgent.setEmail(dtoEmail);

        salesAgent.setName(salesAgentDto.getName());

        String dtoPhone = salesAgentDto.getPhone();
        if (!isValidPhoneGV(dtoPhone)) {
            throw new InvalidDataException("Invalid phone format: [" + dtoPhone
                    + "]. Phone must start with \"07\" and have 10 digits.");
        }
        salesAgent.setPhone(dtoPhone);

        return salesAgent;

    }

}
