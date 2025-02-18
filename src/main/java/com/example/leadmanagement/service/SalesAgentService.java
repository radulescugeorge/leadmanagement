package com.example.leadmanagement.service;

import com.example.leadmanagement.dto.SalesAgentDto;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import com.example.leadmanagement.persistence.repository.SalesAgentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesAgentService {


    private final SalesAgentRepository salesAgentRepository;
    private final ObjectMapper<SalesAgentDto, SalesAgent>  salesAgentMapper;

    public SalesAgentService(SalesAgentRepository salesAgentRepository, ObjectMapper<SalesAgentDto, SalesAgent> salesAgentMapper) {
        this.salesAgentRepository = salesAgentRepository;
        this.salesAgentMapper = salesAgentMapper;
    }

    public List<SalesAgentDto> getSalesAgents(){
        return salesAgentRepository.findAllSalesAgents().stream()
                .map(salesAgentMapper::mapToDto)
                .toList();
    }

    public List<SalesAgentDto> getSalesAgentByPhone(String phone){
        List<SalesAgent> agentsByPhone =salesAgentRepository.findSalesAgentByPhone(phone);
        return agentsByPhone.stream()
                .map(salesAgentMapper::mapToDto)
                .toList();
    }



    public SalesAgent getSalesAgentById(long id){
        return salesAgentRepository.findById(id).orElse(null);
    }

    public SalesAgentDto createSalesAgent(SalesAgentDto salesAgent){

        SalesAgent entity = salesAgentMapper.mapToEntity(salesAgent);
        return salesAgentMapper.mapToDto(salesAgentRepository.saveAndFlush(entity));

    }


    public SalesAgentDto replaceSalesAgent(Long id, SalesAgentDto salesAgentDto){
       SalesAgent existingSalesAgent = getSalesAgentById(id);

       existingSalesAgent.setEmail(salesAgentDto.getEmail());
       existingSalesAgent.setName(salesAgentDto.getName());
       existingSalesAgent.setPhone(salesAgentDto.getPhone());

       return salesAgentMapper.mapToDto(salesAgentRepository.save(existingSalesAgent));

    }


    public SalesAgentDto updateSalesAgent(Long id, SalesAgentDto salesAgentDto){

        SalesAgent existingSalesAgent = getSalesAgentById(id);

        if(salesAgentDto.getName() != null){
            existingSalesAgent.setName(salesAgentDto.getName());
        }

        if(salesAgentDto.getPhone() != null){
            existingSalesAgent.setPhone(salesAgentDto.getPhone());
        }

        if(salesAgentDto.getEmail() != null){
            existingSalesAgent.setName(salesAgentDto.getEmail());
        }

        return salesAgentMapper.mapToDto(salesAgentRepository.save(existingSalesAgent));
    }

    public void deleteSalesAgentById(long id){
        salesAgentRepository.deleteById(id);
    }
}
