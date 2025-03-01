package com.example.leadmanagement.controller;

import com.example.leadmanagement.dto.LeadDto;
import com.example.leadmanagement.mapper.impl.LeadMapper;
import com.example.leadmanagement.persistence.entity.Lead;
import com.example.leadmanagement.service.LeadService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@Validated
public class LeadController {
    private final LeadService leadService;
    private final LeadMapper leadMapper;

    public LeadController(LeadService leadService, LeadMapper leadMapper) {
        this.leadService = leadService;
        this.leadMapper = leadMapper;
    }


    @GetMapping
    public ResponseEntity<List<LeadDto>> getAllLeads() {
        return ResponseEntity.ok(leadService.getLeads());
    }


    @GetMapping("/{id}")
    public ResponseEntity<LeadDto> getLeadById(@PathVariable long id) {

        Lead lead = leadService.getLeadById(id);
        if (lead == null) {
            throw new EntityNotFoundException("Lead not found. ID=" + id);
        }

        LeadDto leadDto = leadMapper.mapToDto(lead);

        return ResponseEntity.ok(leadDto);

    }

    @PostMapping("/create")
    public ResponseEntity<String> createLead(@RequestBody LeadDto leadDto) {

        leadService.createLead(leadDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("New lead added");


    }

    @PutMapping("/replace/{id}")
    public ResponseEntity<LeadDto> replaceLead(@PathVariable long id,
                                               @RequestBody LeadDto leadDto) {
        return ResponseEntity.ok(leadService.replaceLead(id, leadDto));
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<LeadDto> updateLead(@PathVariable long id,
                                              @RequestBody LeadDto leadDto) {
        return ResponseEntity.ok(leadService.updateLead(id, leadDto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLead(@PathVariable long id) {
        leadService.deleteLeadById(id);
    }

    @GetMapping("/salesagent/{salesAgentId}")
    public List<LeadDto> getAllLeadsBySalesAgent(@PathVariable long salesAgentId) {
        return leadService.getAllLeadsBySalesAgent(salesAgentId);
    }

    @GetMapping("/customer/{customerId}")
    public List<LeadDto> getAllLeadsByCustomer(@PathVariable long customerId) {
        return leadService.getAllLeadsByCustomer(customerId);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<LeadDto>> getAllLeadsByProduct(@PathVariable long productId) {
        return ResponseEntity.ok(leadService.getAllLeadsByProduct(productId));
    }


}
