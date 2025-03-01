package com.example.leadmanagement.controller_ui;

import com.example.leadmanagement.dto.LeadDto;
import com.example.leadmanagement.dto.LeadViewDto;
import com.example.leadmanagement.exception_handlers.InvalidDataException;
import com.example.leadmanagement.mapper.impl.LeadMapper;
import com.example.leadmanagement.mapper.impl.LeadViewMapper;
import com.example.leadmanagement.persistence.repository.CustomerRepository;
import com.example.leadmanagement.persistence.repository.ProductRepository;
import com.example.leadmanagement.persistence.repository.SalesAgentRepository;
import com.example.leadmanagement.service.LeadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/leads")
public class LeadUIController {
    private final LeadService leadService;
    private final LeadMapper leadMapper;
    private final LeadViewMapper leadViewMapper;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SalesAgentRepository salesAgentRepository;

    public LeadUIController(LeadService leadService,
                            LeadMapper leadMapper,
                            LeadViewMapper leadViewMapper,
                            ProductRepository productRepository,
                            CustomerRepository customerRepository,
                            SalesAgentRepository salesAgentRepository) {
        this.leadService = leadService;
        this.leadMapper = leadMapper;
        this.leadViewMapper = leadViewMapper;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.salesAgentRepository = salesAgentRepository;
    }

    @GetMapping
    public String getAllLeads(Model model){
        List<LeadViewDto> leads = leadService.getViewLeads();
        model.addAttribute("leads", leads);
        return "leads";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        LeadViewDto leadViewDto = new LeadViewDto();
        model.addAttribute("lead", leadViewDto);

        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("salesAgents", salesAgentRepository.findAll());
        return "add-lead";
    }

    @PostMapping("/save")
    public String saveViewLead(@ModelAttribute LeadViewDto leadViewDto,
                                Model model){
        try{
            leadService.createViewLead(leadViewDto);
            return "redirect:/leads";
        } catch (InvalidDataException e){
            model.addAttribute("error",e.getMessage());
            model.addAttribute("lead",leadViewDto);
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("salesAgents", salesAgentRepository.findAll());
            return "add-lead";
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteLead(@PathVariable long id){
        leadService.deleteLeadById(id);
        return "redirect:/leads";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model){
        LeadDto leadDto = leadMapper.mapToDto(leadService.getLeadById(id));
        if(leadDto == null){
            return "redirect:/leads";
        }
        model.addAttribute("lead", leadDto);
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("salesAgents", salesAgentRepository.findAll());
        return "edit-lead";
    }

    @PostMapping("/update")
    public String updateLead(@ModelAttribute LeadDto leadDto,
                             Model model){
        try {
            leadService.updateLead(leadDto.getId(), leadDto);
            return "redirect:/leads";
        } catch (InvalidDataException e){
            model.addAttribute("error",e.getMessage());
            model.addAttribute("lead",leadDto);
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("salesAgents", salesAgentRepository.findAll());
            return "edit-lead";
        }
    }

    @GetMapping("/search")
    public String searchLeadById(@RequestParam long id, Model model){
       List<LeadViewDto> leads = leadService.getAllViewLeadsById(id);

        model.addAttribute("leads", leads); //fixed the old list.of - generating errors.
        model.addAttribute("searchQuery", id);
        return "leads";
    }

    @GetMapping("/report")
    public String showReportForm(@RequestParam(required = false) Long salesAgentId,
                              @RequestParam(required = false) Long customerId,
                              @RequestParam(required = false) Long productId,
                              @RequestParam(required = false) String date,
                              Model model){
        List<LeadViewDto> leads;

        if(salesAgentId != null){
            leads=leadService.getAllViewLeadsBySalesAgent(salesAgentId);
        } else if(customerId != null){
            leads = leadService.getAllViewLeadsByCustomer(customerId);
        } else if(productId != null){
            leads=leadService.getAllViewLeadsByProduct(productId);
        } else if (date != null && !date.isEmpty()){
            leads=leadService.getAllViewLeadsByDate(LocalDate.parse(date));
        } else{
            leads = leadService.getViewLeads();
        }

        double totalReportAmount = leads.stream()
                .mapToDouble(LeadViewDto::getTotalAmount)
                .sum();

        model.addAttribute("leads", leads);
        model.addAttribute("salesAgents", salesAgentRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("totalReportAmount", totalReportAmount);
        return "report";
    }
}
