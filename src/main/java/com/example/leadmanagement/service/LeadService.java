package com.example.leadmanagement.service;

import com.example.leadmanagement.dto.LeadDto;
import com.example.leadmanagement.dto.LeadViewDto;
import com.example.leadmanagement.mapper.impl.LeadMapper;
import com.example.leadmanagement.mapper.impl.LeadViewMapper;
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
import java.util.List;
import java.util.Optional;


@Service
public class LeadService {
    private final LeadRepository leadRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final SalesAgentRepository salesAgentRepository;
    private final LeadMapper leadMapper;
    private final LeadViewMapper leadViewMapper;

    public LeadService(LeadRepository leadRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository,
                       SalesAgentRepository salesAgentRepository,
                       LeadMapper leadMapper, LeadViewMapper leadViewMapper) {
        this.leadRepository = leadRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.salesAgentRepository = salesAgentRepository;
        this.leadMapper = leadMapper;
        this.leadViewMapper = leadViewMapper;
    }


    public List<LeadDto> getLeads() {
        return leadRepository.findAll().stream()
                .map(leadMapper::mapToDto)
                .toList();
    }

    public List<LeadDto> getAllLeadsBySalesAgent(long salesAgentId) {
        return leadRepository.findBySalesAgentId(salesAgentId)
                .stream()
                .map(leadMapper::mapToDto)
                .toList();
    }

    public List<LeadViewDto> getAllViewLeadsBySalesAgent(Long salesAgentId) {
        return leadRepository.findBySalesAgentId(salesAgentId).stream()
                .map(leadViewMapper::mapToDto)
                .toList();
    }

    public List<LeadDto> getAllLeadsByProduct(long productId) {
        return leadRepository.findByProductId(productId).stream()
                .map(leadMapper::mapToDto)
                .toList();
    }

    public List<LeadViewDto> getAllViewLeadsByProduct(Long productId) {
        return leadRepository.findByProductId(productId).stream()
                .map(leadViewMapper::mapToDto)
                .toList();
    }

    public List<LeadDto> getAllLeadsByCustomer(long customerId) {
        return leadRepository.findByCustomerId(customerId)
                .stream()
                .map(leadMapper::mapToDto)
                .toList();
    }

    public List<LeadViewDto> getAllViewLeadsByCustomer(Long customerId) {
        return leadRepository.findByCustomerId(customerId).stream()
                .map(leadViewMapper::mapToDto)
                .toList();
    }


    public List<LeadViewDto> getAllViewLeadsByDate(LocalDate date) {
        return leadRepository.findByDate(date).stream()
                .map(leadViewMapper::mapToDto)
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

        double price =0d;
        double discount =0d;


        //customer.
        // if customer in payload is empty use existing customer
        if (leadDto.getCustomerId() != 0) {
            Optional<Customer> customerOpt = customerRepository
                    .findById(leadDto.getCustomerId());
            if (customerOpt.isPresent()) {
                existingLead.setCustomer(customerOpt.get());
                discount = customerOpt.get().getLoyaltyCard().getDiscount();
            }
        } else {
            Optional<Customer> customerOpt = customerRepository
                    .findById(existingLead.getCustomer().getId());
            if (customerOpt.isPresent()) {
                existingLead.setCustomer(customerOpt.get());
                discount = customerOpt.get().getLoyaltyCard().getDiscount();
            }
        }

        //product
        //if product in payload is empty, use existing product.

        if (leadDto.getProductId() != 0) {
            Optional<Product> productOpt = productRepository
                    .findById(leadDto.getProductId());
            if (productOpt.isPresent()) {
                existingLead.setProduct(productOpt.get());
                price = productOpt.get().getPrice();
            }
        } else {
            Optional<Product> productOpt = productRepository
                    .findById(existingLead.getProduct().getId());
            if(productOpt.isPresent()) {
                existingLead.setProduct(productOpt.get());
                price = productOpt.get().getPrice();
            }
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

    public List<LeadViewDto> getViewLeads() {
        List<Lead> leads = leadRepository.findAll(); // Fetch leads from DB

        return leads.stream().map(lead -> {
            LeadViewDto dto = new LeadViewDto();
            dto.setId(lead.getId());
            dto.setDate(lead.getDate());
            dto.setQuantity(lead.getQuantity());
            dto.setTotalAmount(lead.getTotalAmount());

            // Set product ID
            dto.setProductId(lead.getProduct() != null ? lead.getProduct().getId() : null);
            dto.setCustomerId(lead.getCustomer() != null ? lead.getCustomer().getId() : null);
            dto.setSalesAgentId(lead.getSalesAgent() != null ? lead.getSalesAgent().getId() : null);

            // Set product name
            dto.setProductName(lead.getProduct() != null ? lead.getProduct().getName() : "Unknown Product");
            dto.setCustomerName(lead.getCustomer() != null ? lead.getCustomer().getName() : "Unknown Customer");
            dto.setSalesAgentName(lead.getSalesAgent() != null ? lead.getSalesAgent().getName() : "Unknown Sales Agent");

            return dto;
        }).toList();
    }

    public LeadViewDto createViewLead(LeadViewDto leadViewDto) {

        Lead newLead = leadViewMapper.mapToEntity(leadViewDto);
        return leadViewMapper.mapToDto(leadRepository.save(newLead));
    }


}
