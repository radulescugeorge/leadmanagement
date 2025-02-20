package com.example.leadmanagement.service;

import com.example.leadmanagement.dto.LeadDto;
import com.example.leadmanagement.mapper.impl.LeadMapper;
import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.persistence.entity.Lead;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.persistence.entity.SalesAgent;
import com.example.leadmanagement.persistence.repository.CustomerRepository;
import com.example.leadmanagement.persistence.repository.LeadRepository;
import com.example.leadmanagement.persistence.repository.ProductRepository;
import com.example.leadmanagement.persistence.repository.SalesAgentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeadService {
    private final LeadRepository leadRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final SalesAgentRepository salesAgentRepository;
    private final LeadMapper leadMapper;

    public LeadService(LeadRepository leadRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository,
                       SalesAgentRepository salesAgentRepository,
                       LeadMapper leadMapper) {
        this.leadRepository = leadRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.salesAgentRepository = salesAgentRepository;
        this.leadMapper = leadMapper;
    }


    public List<LeadDto> getLeads() {
        return leadRepository.findAll().stream()
                .map(leadMapper::mapToDto)
                .toList();
    }

    public Lead getLeadById(long id) {
        return leadRepository.findById(id).orElse(null);
    }

    public LeadDto createLead(LeadDto leadDto) {

        Lead newLead = leadMapper.mapToEntity(leadDto);
        return leadMapper.mapToDto(leadRepository.save(newLead));
    }

    public LeadDto replaceLead(long id, LeadDto leadDto) {
        Lead existingLead = getLeadById(id);

        Customer customer = customerRepository
                .findById(leadDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Product product = productRepository
                .findById(leadDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        SalesAgent salesAgent = salesAgentRepository
                .findById(leadDto.getSalesAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Sales Agent not found"));

        existingLead.setQuantity(leadDto.getQuantity());

        double totalAmountWithDiscount = (leadDto.getQuantity() * product.getPrice()) *
                (1 - customer.getLoyaltyCard().getDiscount() / 100);

        existingLead.setTotalAmount(totalAmountWithDiscount);
        existingLead.setDate(leadDto.getDate());
        existingLead.setCustomer(customer);
        existingLead.setProduct(product);
        existingLead.setSalesAgent(salesAgent);

        return leadMapper.mapToDto(leadRepository.save(existingLead));
    }

    public LeadDto updateLead(long id, LeadDto leadDto) {
        Lead existingLead = getLeadById(id);

        double price;
        double discount;


        //customer.
        // if customer in payload is empty use existing customer
        if (leadDto.getCustomerId() != 0) {
            Customer customer = customerRepository
                    .findById(leadDto.getCustomerId())
                    .orElse(null);
            existingLead.setCustomer(customer);
            discount = customer.getLoyaltyCard().getDiscount();

        } else {
            Customer customer = customerRepository
                    .findById(existingLead.getCustomer().getId())
                    .orElse(null);
            existingLead.setCustomer(customer);
            discount = customer.getLoyaltyCard().getDiscount();
        }

        //product
        //if product in payload is empty, use existing product.

        if (leadDto.getProductId() != 0) {
            Product product = productRepository
                    .findById(leadDto.getProductId())
                    .orElse(null);
            existingLead.setProduct(product);
            price = product.getPrice();

        } else {
            Product product = productRepository
                    .findById(existingLead.getProduct().getId())
                    .orElse(null);
            existingLead.setProduct(product);
            price = product.getPrice();
        }

        //sales agent

        if (leadDto.getSalesAgentId() != 0) {
            SalesAgent salesAgent = salesAgentRepository
                    .findById(leadDto.getSalesAgentId())
                    .orElse(null);
            existingLead.setSalesAgent(salesAgent);
        }

        if (leadDto.getQuantity() != 0) {
            existingLead.setQuantity(leadDto.getQuantity());
        }

        double totalAmountWithDiscount = (existingLead.getQuantity() * price) *
                (1 - discount / 100);

        existingLead.setTotalAmount(totalAmountWithDiscount);

        if (leadDto.getDate() != null) {
            existingLead.setDate(leadDto.getDate());
        }

        return leadMapper.mapToDto(leadRepository.save(existingLead));
    }


    public void deleteLeadById(long id) {
        leadRepository.deleteById(id);
    }
}
