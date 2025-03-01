package com.example.leadmanagement;

import com.example.leadmanagement.dto.SalesAgentDto;
import com.example.leadmanagement.mapper.impl.SalesAgentMapper;
import com.example.leadmanagement.persistence.entity.Lead;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import com.example.leadmanagement.persistence.repository.SalesAgentRepository;
import com.example.leadmanagement.service.SalesAgentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SalesAgentServiceTest {

    @Mock
    private SalesAgentRepository salesAgentRepository;

    @Mock
    private SalesAgentMapper salesAgentMapper;

    @InjectMocks
    private SalesAgentService salesAgentService;

    @Test
    void getSalesAgent_ShouldReturnMappedDtoList(){
       List<Lead> leadList = List.of(
               new Lead(),
               new Lead()
       );
        List<SalesAgent> salesAgents = List.of(
                new SalesAgent(1L,"Tester1", "0777111111", "test1@test.ro",leadList),
                new SalesAgent(2L,"Tester2","0777222222","test2@test.ro",leadList)
        );

        List<SalesAgentDto> expectedDtos = List.of(
                new SalesAgentDto(1L,"Tester1", "0777111111", "test1@test.ro"),
                new SalesAgentDto(2L,"Tester2","0777222222","test2@test.ro")
        );

        when(salesAgentRepository.findAllSalesAgents()).thenReturn(salesAgents);
        when(salesAgentMapper.mapToDto(salesAgents.get(0))).thenReturn(expectedDtos.get(0));
        when(salesAgentMapper.mapToDto(salesAgents.get(1))).thenReturn((expectedDtos.get(1)));

        List<SalesAgentDto> result = salesAgentService.getSalesAgents();

        assertEquals(expectedDtos, result);

        verify(salesAgentRepository, times(1)).findAllSalesAgents();
        verify(salesAgentMapper, times(2)).mapToDto(any(SalesAgent.class));
    }
}
