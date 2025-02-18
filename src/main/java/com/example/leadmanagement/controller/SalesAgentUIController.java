package com.example.leadmanagement.controller;

import com.example.leadmanagement.dto.SalesAgentDto;
import com.example.leadmanagement.mapper.impl.SalesAgentMapper;
import com.example.leadmanagement.service.SalesAgentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salesagents")
public class SalesAgentUIController {

    private final SalesAgentService salesAgentService;
    private final SalesAgentMapper salesAgentMapper;


    public SalesAgentUIController(SalesAgentService salesAgentService, SalesAgentMapper salesAgentMapper) {
        this.salesAgentService = salesAgentService;
        this.salesAgentMapper = salesAgentMapper;
    }

    @GetMapping
    public String getAllSalesAgents(Model model){
        model.addAttribute("salesAgents", salesAgentService.getSalesAgents());
        return "salesagents";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("salesAgent", new SalesAgentDto());
        return "add-agent";
    }

    @PostMapping("/save")
    public String saveSalesAgent(@ModelAttribute SalesAgentDto salesAgentDto){
        salesAgentService.createSalesAgent(salesAgentDto);
        return "redirect:/salesagents";
    }

    @GetMapping("/delete/{id}")
    public String deleteSalesAgent(@PathVariable Long id){
        salesAgentService.deleteSalesAgentById(id);
        return "redirect:/salesagents";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        SalesAgentDto salesAgentDto = salesAgentMapper.mapToDto(salesAgentService.getSalesAgentById(id));
        if(salesAgentDto == null){
            return "redirect:/salesagents";
        }
        model.addAttribute("salesAgent", salesAgentDto);
        return "edit-agent";
    }

    @PostMapping("/update")
    public String updateSalesAgent(@ModelAttribute SalesAgentDto salesAgentDto){
        salesAgentService.updateSalesAgent(salesAgentDto.getId(), salesAgentDto);
        return "redirect:/salesagents";
    }

    @GetMapping("/search")
    public String searchSalesAgents(@RequestParam String phone, Model model){
        List<SalesAgentDto> salesAgents = salesAgentService.getSalesAgentByPhone(phone);
        model.addAttribute("salesAgents", salesAgents);
        model.addAttribute("phone", phone);
        return "salesagents";
    }

}
