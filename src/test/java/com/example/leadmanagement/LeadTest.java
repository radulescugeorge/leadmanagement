package com.example.leadmanagement;

import com.example.leadmanagement.dto.LeadDto;
import com.example.leadmanagement.mapper.impl.LeadMapper;
import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.persistence.entity.Lead;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import com.example.leadmanagement.persistence.repository.LeadRepository;
import com.example.leadmanagement.service.LeadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeadTest {

    @Mock
    private LeadRepository leadRepository;
    @Mock
    private LeadMapper leadMapper;
    @InjectMocks
    private LeadService leadService;

    @Test
    void createLead_ShouldReturnSavedLeadDto() {

        LocalDate date = LocalDate.parse("2025-02-28");
        Customer customer = new Customer();
        customer.setId(1);
        Product product = new Product();
        product.setId(1);
        SalesAgent salesAgent = new SalesAgent();
        salesAgent.setId(1);

        LeadDto inputDto = new LeadDto(1l, 100, 900, date, 1, 1, 1);
        Lead newLead = new Lead(1l, 100, 900, date, customer, product, salesAgent);
        Lead savedLead = new Lead(1l, 100, 900, date, customer, product, salesAgent);
        LeadDto expectedDto = new LeadDto(1l, 100, 900, date, 1, 1, 1);

        when(leadMapper.mapToEntity(inputDto)).thenReturn(newLead);
        when(leadRepository.save(newLead)).thenReturn(savedLead);
        when(leadMapper.mapToDto(savedLead)).thenReturn(expectedDto);

        LeadDto result = leadService.createLead(inputDto);

        assertEquals(expectedDto, result);

        verify(leadMapper,times(1)).mapToEntity(inputDto);
        verify(leadRepository, times(1)).save(newLead);
        verify(leadMapper, times(1)).mapToDto(savedLead);


    }

}
