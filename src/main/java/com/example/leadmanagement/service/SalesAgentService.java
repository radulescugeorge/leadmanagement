package com.example.leadmanagement.service;

import com.example.leadmanagement.dto.SalesAgentDto;
import com.example.leadmanagement.exception_handlers.InvalidDataException;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import com.example.leadmanagement.persistence.repository.SalesAgentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.leadmanagement.global_validator.GlobalValidator.isValidEmailGV;
import static com.example.leadmanagement.global_validator.GlobalValidator.isValidPhoneGV;

@Service
public class SalesAgentService {


    private final SalesAgentRepository salesAgentRepository;
    private final ObjectMapper<SalesAgentDto, SalesAgent> salesAgentMapper;

    public SalesAgentService(SalesAgentRepository salesAgentRepository,
                             ObjectMapper<SalesAgentDto, SalesAgent> salesAgentMapper) {
        this.salesAgentRepository = salesAgentRepository;
        this.salesAgentMapper = salesAgentMapper;
    }

    public List<SalesAgentDto> getSalesAgents() {
        return salesAgentRepository.findAllSalesAgents().stream()
                .map(salesAgentMapper::mapToDto)
                .toList();
    }

    public List<SalesAgentDto> getSalesAgentByPhone(String phone) {
        List<SalesAgent> agentsByPhone = salesAgentRepository.findSalesAgentByPhone(phone);
        return agentsByPhone.stream()
                .map(salesAgentMapper::mapToDto)
                .toList();
    }


    public SalesAgent getSalesAgentById(long id) {
        return salesAgentRepository.findById(id).orElse(null);
    }

    public SalesAgentDto createSalesAgent(SalesAgentDto salesAgent) {

        SalesAgent entity = salesAgentMapper.mapToEntity(salesAgent);
        return salesAgentMapper.mapToDto(salesAgentRepository.saveAndFlush(entity));

    }


    public SalesAgentDto replaceSalesAgent(Long id, SalesAgentDto salesAgentDto) {
        SalesAgent existingSalesAgent = getSalesAgentById(id);

        String dtoEmail = salesAgentDto.getEmail();
        if (!isValidEmailGV(dtoEmail)) {
            throw new InvalidDataException("Invalid email format: [" + dtoEmail
                    + "]. Email should be x@x.x");
        }
        existingSalesAgent.setEmail(dtoEmail);

        existingSalesAgent.setName(salesAgentDto.getName());

        String dtoPhone = salesAgentDto.getPhone();
        if (!isValidPhoneGV(dtoPhone)) {
            throw new InvalidDataException("Invalid phone format: [" + dtoPhone
                    + "]. Phone must start with \"07\" and have 10 digits.");
        }
        existingSalesAgent.setPhone(dtoPhone);

        return salesAgentMapper.mapToDto(salesAgentRepository.save(existingSalesAgent));

    }


    public SalesAgentDto updateSalesAgent(Long id, SalesAgentDto salesAgentDto) {

        SalesAgent existingSalesAgent = getSalesAgentById(id);

        if (salesAgentDto.getName() != null) {
            existingSalesAgent.setName(salesAgentDto.getName());
        }

        if (salesAgentDto.getPhone() != null) {

            String dtoPhone = salesAgentDto.getPhone();
            if (!isValidPhoneGV(dtoPhone)) {
                throw new InvalidDataException("Invalid phone format: [" + dtoPhone
                        + "]. Phone must start with \"07\" and have 10 digits.");
            }
            existingSalesAgent.setPhone(dtoPhone);
        }

        if (salesAgentDto.getEmail() != null) {
            String dtoEmail = salesAgentDto.getEmail();
            if (!isValidEmailGV(dtoEmail)) {
                throw new InvalidDataException("Invalid email format: [" + dtoEmail
                        + "]. Email should be x@x.x");
            }
            existingSalesAgent.setEmail(dtoEmail);
        }

        return salesAgentMapper.mapToDto(salesAgentRepository.save(existingSalesAgent));
    }

    public void deleteSalesAgentById(long id) {
        salesAgentRepository.deleteById(id);
    }
}
