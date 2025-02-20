package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.LeadDto;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.persistence.entity.Lead;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import com.example.leadmanagement.persistence.repository.CustomerRepository;
import com.example.leadmanagement.persistence.repository.ProductRepository;
import com.example.leadmanagement.persistence.repository.SalesAgentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LeadMapper implements ObjectMapper<LeadDto, Lead> {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final SalesAgentRepository salesAgentRepository;

    public LeadMapper(CustomerRepository customerRepository, ProductRepository productRepository, SalesAgentRepository salesAgentRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.salesAgentRepository = salesAgentRepository;
    }

    @Override
    public LeadDto mapToDto(Lead lead) {

        LeadDto leadDto = new LeadDto();

        leadDto.setId(lead.getId());
        leadDto.setQuantity(lead.getQuantity());
        leadDto.setDate(lead.getDate());
        leadDto.setTotalAmount(lead.getTotalAmount());
        leadDto.setCustomerId(lead.getCustomer().getId());
        leadDto.setProductId(lead.getProduct().getId());
        leadDto.setSalesAgentId(lead.getSalesAgent().getId());


        return leadDto;
    }

    @Override
    public Lead mapToEntity(LeadDto leadDto) {

        Lead lead = new Lead();

        Customer customer = customerRepository
                .findById(leadDto.getCustomerId())
                .orElseThrow(()-> new EntityNotFoundException("Customer not found"));

        Product product = productRepository
                .findById(leadDto.getProductId())
                .orElseThrow(()->new EntityNotFoundException("Product not found"));

        SalesAgent salesAgent = salesAgentRepository
                .findById(leadDto.getSalesAgentId())
                .orElseThrow(()-> new EntityNotFoundException("Sales Agent not found"));

        lead.setQuantity(leadDto.getQuantity());

        double totalAmountWithDiscount = (lead.getQuantity()*product.getPrice())*
                (1-customer.getLoyaltyCard().getDiscount()/100);

        lead.setTotalAmount(totalAmountWithDiscount);
        lead.setDate(leadDto.getDate());
        lead.setCustomer(customer);
        lead.setProduct(product);
        lead.setSalesAgent(salesAgent);

        return lead;
    }
}
