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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class LeadService {
    private final LeadRepository leadRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final SalesAgentRepository salesAgentRepository;
    private final LeadMapper leadMapper;

    public LeadService(LeadRepository leadRepository, CustomerRepository customerRepository, ProductRepository productRepository, SalesAgentRepository salesAgentRepository, LeadMapper leadMapper) {
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
        //get customer, product, and salesagent;
//        Customer leadCustomer = customerRepository.findById(customerId)
//                .orElseThrow(()-> new EntityNotFoundException("Customer not found with id: "+customerId));
//        Product leadProduct = productRepository.findById(productId)
//                .orElseThrow(()-> new EntityNotFoundException("Product not found with id: "+productId));
//        SalesAgent leadSalesAgent = salesAgentRepository.findById(salesAgentId)
//                .orElseThrow(()-> new EntityNotFoundException("Product not found with id: "+salesAgentId));

        //calculate total amount using product price and customer discount
        double totalAmount = (leadDto.getQuantity())*(leadDto.getProduct().getPrice())*(1-leadDto.getCustomer().getLoyaltyCard().getDiscount()/100);

        newLead.setTotalAmount(totalAmount);
        newLead.setCustomer(leadDto.getCustomer());
        newLead.setProduct(leadDto.getProduct());
        newLead.setSalesAgent(leadDto.getSalesAgent());
        newLead.setDate(LocalDateTime.now());


        return leadMapper.mapToDto(leadRepository.save(newLead));
    }

    public LeadDto replaceLead(long id, LeadDto leadDto) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead with id " + id + " not found."));

        lead.setQuantity(leadDto.getQuantity());
        lead.setTotalAmount(leadDto.getTotalAmount());
        lead.setDate(leadDto.getDate());
        lead.setCustomer(leadDto.getCustomer());
        lead.setProduct(leadDto.getProduct());
        lead.setSalesAgent(leadDto.getSalesAgent());

        Lead replacedLead = leadRepository.save(lead);
        return leadMapper.mapToDto(replacedLead);

    }

    public LeadDto updateLead(long id, LeadDto leadDto) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead with id " + id + " not found."));

        if (leadDto.getQuantity() == 0.0) lead.setQuantity(leadDto.getQuantity());
        if (leadDto.getTotalAmount() == 0.0) lead.setTotalAmount((leadDto.getTotalAmount()));
        if (leadDto.getDate() != null) lead.setDate((leadDto.getDate()));
        if (leadDto.getCustomer() != null) lead.setCustomer(leadDto.getCustomer());
        if (leadDto.getProduct() != null) lead.setProduct(leadDto.getProduct());
        if (leadDto.getSalesAgent() != null) lead.setSalesAgent(leadDto.getSalesAgent());

        Lead updatedLead = leadRepository.save(lead);
        return leadMapper.mapToDto(updatedLead);

    }

    public void deleteLeadById(long id){
        leadRepository.deleteById(id);
    }
}
