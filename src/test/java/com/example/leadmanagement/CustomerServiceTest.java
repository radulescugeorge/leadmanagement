package com.example.leadmanagement;


import ch.qos.logback.core.util.Loader;
import com.example.leadmanagement.dto.CustomerDto;
import com.example.leadmanagement.dto.LoyaltyCardDto;
import com.example.leadmanagement.mapper.impl.CustomerMapper;
import com.example.leadmanagement.mapper.impl.LoyaltyCardMapper;
import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.persistence.entity.Lead;
import com.example.leadmanagement.persistence.entity.LoyaltyCard;
import com.example.leadmanagement.persistence.repository.CustomerRepository;
import com.example.leadmanagement.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private LoyaltyCardMapper loyaltyCardMapper;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void getCustomer_ShouldReturnMappedDto(){
        List<LoyaltyCard> loyaltyCards = List.of(
                new LoyaltyCard(),
                new LoyaltyCard()
        );

        LoyaltyCardDto loyaltyCardDto1 = loyaltyCardMapper.mapToDto(loyaltyCards.get(0));
        LoyaltyCardDto loyaltyCardDto2 = loyaltyCardMapper.mapToDto(loyaltyCards.get(1));

        List<Lead> leads = List.of(
                new Lead(),
                new Lead()
        );

        List<Customer> customerList = List.of(
                new Customer(1L,"TestCustomer1",
                        "TestCity1",
                        "0777123123",
                        "test1@test.ro",
                        loyaltyCards.get(0), leads),
                new Customer(2L,"TestCustomer2",
                        "TestCity2",
                        "0777321321",
                        "test2@test.ro",
                        loyaltyCards.get(1), leads)
        );

        List<CustomerDto> expectedDtos = List.of(
                new CustomerDto(1L,"TestCustomer1", "TestCity1",
                        "0777123123","test1@test.ro", loyaltyCardDto1),
                new CustomerDto(2L,"TestCustomer2", "TestCity2",
                        "0777321321","test2@test.ro", loyaltyCardDto2)
        );

        when(customerRepository.findAll()).thenReturn(customerList);
        for (int i = 0; i < expectedDtos.size(); i++) {
            when(customerMapper.mapToDto(customerList.get(i))).thenReturn(expectedDtos.get(i));
        }

        List<CustomerDto> result = customerService.getAllCustomers();

        assertEquals(expectedDtos, result);

        verify(customerRepository, times(1)).findAll();
        verify(customerMapper, times(2)).mapToDto(any(Customer.class));
    }
}
