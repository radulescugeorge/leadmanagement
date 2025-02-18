package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.CustomerDto;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.Customer;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper implements ObjectMapper<CustomerDto, Customer> {

    private final LoyaltyCardMapper loyaltyCardMapper;

    public CustomerMapper(LoyaltyCardMapper loyaltyCardMapper) {
        this.loyaltyCardMapper = loyaltyCardMapper;
    }

    @Override
    public CustomerDto mapToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getCity(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getLoyaltyCard() != null ? loyaltyCardMapper.mapToDto(customer.getLoyaltyCard()):null
        );

    }

    @Override
    public Customer mapToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setCity(customerDto.getCity());
        customer.setPhone(customerDto.getPhone());
        customer.setEmail(customerDto.getEmail());

        if (customerDto.getLoyaltyCard() != null) {
            customer.setLoyaltyCard(loyaltyCardMapper.mapToEntity(customerDto.getLoyaltyCard()));
        }

        return customer;
    }
}
