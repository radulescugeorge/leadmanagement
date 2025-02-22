package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.LeadViewDto;
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

@Component
public class LeadViewMapper implements ObjectMapper<LeadViewDto, Lead> {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final SalesAgentRepository salesAgentRepository;

    public LeadViewMapper(CustomerRepository customerRepository, ProductRepository productRepository, SalesAgentRepository salesAgentRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.salesAgentRepository = salesAgentRepository;
    }



    @Override
    public Lead mapToEntity(LeadViewDto leadDto) {

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

    @Override
    public LeadViewDto mapToDto(Lead lead) {

        LeadViewDto leadViewDto = new LeadViewDto();

        leadViewDto.setId(lead.getId());
        leadViewDto.setQuantity(lead.getQuantity());
        leadViewDto.setTotalAmount(lead.getTotalAmount());
        leadViewDto.setDate(lead.getDate());
        leadViewDto.setCustomerName(lead.getCustomer().getName());
        leadViewDto.setCustomerId(lead.getCustomer().getId());
        leadViewDto.setProductName(lead.getProduct().getName());
        leadViewDto.setProductId(lead.getProduct().getId());
        leadViewDto.setSalesAgentName(lead.getSalesAgent().getName());
        leadViewDto.setSalesAgentId(lead.getSalesAgent().getId());
        return leadViewDto;

    }
}
