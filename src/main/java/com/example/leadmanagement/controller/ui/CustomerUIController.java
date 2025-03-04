package com.example.leadmanagement.controller.ui;

import com.example.leadmanagement.dto.CustomerDto;
import com.example.leadmanagement.dto.LoyaltyCardDto;
import com.example.leadmanagement.exceptionhandlers.InvalidDataException;
import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerUIController {
    private final CustomerService customerService;

    public CustomerUIController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String getAllCustomers(Model model){
        List<CustomerDto> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setLoyaltyCard(new LoyaltyCardDto());
        model.addAttribute("customer", customerDto);
        return "add-customer";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute CustomerDto customerDto,
                               Model model){
        try {
            customerService.createCustomerWithLoyaltyCard(customerDto);
            return "redirect:/customers";
        } catch (InvalidDataException e){
            model.addAttribute("error",e.getMessage());
            model.addAttribute("customer", customerDto);
            return "add-customer";
        }
    }

    @GetMapping("delete/{id}")
    public String deleteCustomer(@PathVariable long id){
        customerService.deleteCustomerById(id);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm (@PathVariable long id, Model model){
        Customer customer = customerService.getCustomerById(id);
        if(customer == null){
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "edit-customer";

    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute CustomerDto customerDto,
                                 Model model){
        try {
            customerService.updateCustomer(customerDto.getId(), customerDto);
            return "redirect:/customers";
        } catch (InvalidDataException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("customer",customerDto);
            return "edit-customer";
        }
    }

    @GetMapping("/search")
    public String searchCustomer(@RequestParam String name, Model model){
        List<CustomerDto> customers = customerService.getCustomersByName(name);
        model.addAttribute("customers", customers);
        model.addAttribute("searchQuery", name);
        return "customers";
    }
}
