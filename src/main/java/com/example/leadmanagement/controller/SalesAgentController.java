package com.example.leadmanagement.controller;

import com.example.leadmanagement.dto.SalesAgentDto;
import com.example.leadmanagement.mapper.impl.SalesAgentMapper;
import com.example.leadmanagement.service.SalesAgentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/salesagents")
public class SalesAgentController {

    private final SalesAgentService salesAgentService;
    private final SalesAgentMapper salesAgentMapper;

    public SalesAgentController(SalesAgentService salesAgentService, SalesAgentMapper salesAgentMapper) {
        this.salesAgentService = salesAgentService;
        this.salesAgentMapper = salesAgentMapper;
    }

    @GetMapping
    public ResponseEntity<List<SalesAgentDto>> getSalesAgents(){
        return ResponseEntity.ok(salesAgentService.getSalesAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesAgentDto> getSalesAgentById(@PathVariable long id){
        SalesAgentDto salesAgentDtoById = salesAgentMapper.mapToDto(salesAgentService.getSalesAgentById(id));
        if(salesAgentDtoById == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(salesAgentDtoById);
    }

    @PostMapping
    public ResponseEntity<String> addSalesAgent(@RequestBody SalesAgentDto salesAgentDto){
        salesAgentService.createSalesAgent(salesAgentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("New sales agent added. ");
    }

    @PutMapping("/replace/{id}")
    public ResponseEntity<SalesAgentDto> replaceSalesAgent(@PathVariable long id,
                                                           @RequestBody SalesAgentDto salesAgentDto){
        SalesAgentDto replaceddSalesAgentDto = salesAgentService.updateSalesAgent(id,salesAgentDto);
        if(replaceddSalesAgentDto ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(replaceddSalesAgentDto);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<SalesAgentDto> updateSalesAgent(@PathVariable long id,
                                                           @RequestBody SalesAgentDto salesAgentDto){
        SalesAgentDto updatedSalesAgentDto = salesAgentService.updateSalesAgent(id,salesAgentDto);
        if(updatedSalesAgentDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSalesAgentDto);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteSalesAgent(@PathVariable long id){
        salesAgentService.deleteSalesAgentById(id);
    }


}
