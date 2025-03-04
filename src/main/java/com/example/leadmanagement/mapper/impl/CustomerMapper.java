package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.CustomerDto;
import com.example.leadmanagement.exceptionhandlers.InvalidDataException;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.Customer;
import org.springframework.stereotype.Component;

import static com.example.leadmanagement.globalvalidator.GlobalValidator.isValidEmailGV;
import static com.example.leadmanagement.globalvalidator.GlobalValidator.isValidPhoneGV;


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

        String dtoEmail = customerDto.getEmail();
        if (!isValidEmailGV(dtoEmail)) {
            throw new InvalidDataException("Invalid email format: [" + dtoEmail
                    + "]. Email should be x@x.x");
        }
        customer.setEmail(dtoEmail);

        String dtoPhone = customerDto.getPhone();
        if (!isValidPhoneGV(dtoPhone)) {
            throw new InvalidDataException("Invalid phone format: [" + dtoPhone
                    + "]. Phone must start with \"07\" and have 10 digits.");
        }
        customer.setPhone(dtoPhone);


        if (customerDto.getLoyaltyCard() != null) {
            customer.setLoyaltyCard(loyaltyCardMapper.mapToEntity(customerDto.getLoyaltyCard()));
        }

        return customer;
    }
}
