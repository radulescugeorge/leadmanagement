package com.example.leadmanagement.controller;

import com.example.leadmanagement.dto.LeadDto;
import com.example.leadmanagement.mapper.impl.LeadMapper;
import com.example.leadmanagement.service.LeadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {
    private final LeadService leadService;
    private final LeadMapper leadMapper;

    public LeadController(LeadService leadService, LeadMapper leadMapper) {
        this.leadService = leadService;
        this.leadMapper = leadMapper;
    }


    //When I run this, in Postman i have a kind o a loop

    @GetMapping
    public ResponseEntity<List<LeadDto>> getAllLeads() {
        return ResponseEntity.ok(leadService.getLeads());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadDto> getLeadById(@PathVariable long id) {
        LeadDto leadDtoById = leadMapper.mapToDto(leadService.getLeadById(id));

        if (leadDtoById == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leadDtoById);
    }

    @PostMapping("/create")
    public ResponseEntity<LeadDto> createLead(@RequestBody LeadDto leadDto) {

//        long customerId = leadDto.getCustomer().getId();
//        long productId = leadDto.getProduct().getId();
//        long salesAgentId = leadDto.getProduct().getId();


        LeadDto savedLead = leadService.createLead(leadDto);
        return new ResponseEntity<>(savedLead, HttpStatus.CREATED);
    }

    @PutMapping("/replace/{id}")
    public ResponseEntity<LeadDto> replaceLead(@PathVariable long id,
                                               @RequestBody LeadDto leadDto) {
        //Replace only if all fields of DTO have date, otherwise is better to use update
        //if this condition is not met will give a bad request to user.
        if (leadDto.getQuantity() == 0.0 ||
                leadDto.getTotalAmount() == 0.0 ||
                leadDto.getDate() == null ||
                leadDto.getCustomer() == null ||
                leadDto.getProduct() == null ||
                leadDto.getSalesAgent() == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(leadService.replaceLead(id, leadDto));
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<LeadDto> updateLead(@PathVariable long id,
                                              @RequestBody LeadDto leadDto) {
        return ResponseEntity.ok(leadService.updateLead(id, leadDto));
    }

    @DeleteMapping("/{id}")
    public void deleteLead(@PathVariable long id) {
        leadService.deleteLeadById(id);
    }
}
