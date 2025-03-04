package com.example.leadmanagement.controller;

import com.example.leadmanagement.dto.CustomerDto;
import com.example.leadmanagement.mapper.impl.CustomerMapper;
import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable long id) {
        Customer customer = customerService.getCustomerById(id);
        if(customer==null){
            throw new EntityNotFoundException("Customer not found with ID="+id);
        }
        CustomerDto customerDtoById = customerMapper.mapToDto(customer);
        return ResponseEntity.ok(customerDtoById);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = customerService.createCustomerWithLoyaltyCard(customerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable long id,
                                                      @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> replaceCustomer(@PathVariable long id,
                                                       @RequestBody CustomerDto customerDto){
        if (customerDto.getName() == null ||
                customerDto.getCity() == null ||
                customerDto.getPhone() == null ||
                customerDto.getEmail() == null ||
                customerDto.getLoyaltyCard() == null) {
            return ResponseEntity.badRequest().build();
        } else {

            return ResponseEntity.ok(customerService.replaceCustomer(id, customerDto));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomerById(id);
    }


}
