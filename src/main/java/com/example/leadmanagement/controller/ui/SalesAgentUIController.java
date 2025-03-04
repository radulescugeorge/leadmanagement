package com.example.leadmanagement.controller.ui;

import com.example.leadmanagement.dto.SalesAgentDto;
import com.example.leadmanagement.exceptionhandlers.InvalidDataException;
import com.example.leadmanagement.mapper.impl.SalesAgentMapper;
import com.example.leadmanagement.service.SalesAgentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveSalesAgent(@ModelAttribute("salesAgentDto") SalesAgentDto salesAgentDto,
                                 RedirectAttributes redirectAttributes,
                                 Model model){
        try {
            salesAgentService.createSalesAgent(salesAgentDto);
            redirectAttributes.addFlashAttribute("succes","Sales Agent saved successfully");
            return "redirect:/salesagents";
        } catch (InvalidDataException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("salesAgent", salesAgentDto);
            return "redirect:/salesagents/add";
        }
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
    public String updateSalesAgent(@ModelAttribute SalesAgentDto salesAgentDto,
                                   Model model){
        try {
            salesAgentService.updateSalesAgent(salesAgentDto.getId(), salesAgentDto);
            return "redirect:/salesagents";
        } catch(InvalidDataException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("salesAgent", salesAgentDto);
            return "edit-agent";
        }
    }

    @GetMapping("/search")
    public String searchSalesAgents(@RequestParam String phone, Model model){
        List<SalesAgentDto> salesAgents = salesAgentService.getSalesAgentByPhone(phone);
        model.addAttribute("salesAgents", salesAgents);
        model.addAttribute("phone", phone);
        return "salesagents";
    }

}
