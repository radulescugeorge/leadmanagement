package com.example.leadmanagement;

import com.example.leadmanagement.persistence.entity.*;
import com.example.leadmanagement.persistence.repository.CustomerRepository;
import com.example.leadmanagement.persistence.repository.LeadRepository;
import com.example.leadmanagement.persistence.repository.ProductRepository;
import com.example.leadmanagement.persistence.repository.SalesAgentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.config.location=classpath:application-test.properties",
        classes = LeadmanagementApplication.class)
@AutoConfigureMockMvc
public class LeadControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private LeadRepository leadRepository;
    @MockitoBean
    private CustomerRepository customerRepository;
    @MockitoBean
    private ProductRepository productRepository;
    @MockitoBean
    private SalesAgentRepository salesAgentRepository;

    @Test
    void testGetLeads() throws Exception {
        LocalDate date = LocalDate.now();
        Customer customer = new Customer();
        customer.setId(1);
        Product product = new Product();
        product.setId(1);
        SalesAgent salesAgent = new SalesAgent();
        salesAgent.setId(1);

        Lead lead = new Lead();
        lead.setId(1L);
        lead.setQuantity(100);
        lead.setDate(date);
        lead.setTotalAmount(1000);
        lead.setCustomer(customer);
        lead.setProduct(product);
        lead.setSalesAgent(salesAgent);

        when(leadRepository.findAll()).thenReturn(List.of(lead));

        mockMvc.perform(get("/api/leads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].quantity").value("100.0"))
                .andExpect(jsonPath("$[0].customerId").value("1"))
                .andExpect(jsonPath("$[0].productId").value("1"))
                .andExpect(jsonPath("$[0].salesAgentId").value("1"));
    }

    @Test
    void testCreateLead() throws Exception {
        LocalDate date = LocalDate.parse("2025-02-28");

        Customer customer = new Customer();
        customer.setId(1L);
        //set LoyaltyCard needed for Customer
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        loyaltyCard.setId(1);
        loyaltyCard.setCustomer(customer);
        loyaltyCard.setDiscount(0);
        customer.setLoyaltyCard(loyaltyCard);
        Product product = new Product();
        product.setId(1L);
        SalesAgent salesAgent = new SalesAgent();
        salesAgent.setId(1L);

        Lead lead = new Lead();
        lead.setId(1L);
        lead.setQuantity(100);
        lead.setDate(date);
        lead.setTotalAmount(1000);
        lead.setCustomer(customer);
        lead.setProduct(product);
        lead.setSalesAgent(salesAgent);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(salesAgentRepository.findById(1L)).thenReturn(Optional.of(salesAgent));

        when(leadRepository.save(any(Lead.class))).thenReturn(lead);

        mockMvc.perform(post("/api/leads/create")
                        .content("""
                                {
                                     "id": 1,
                                     "quantity": 100,
                                     "totalAmount": 1000,
                                     "date": "2025-02-28",
                                     "customerId": 1,
                                     "productId": 1,
                                     "salesAgentId": 1
                                   }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().string("New lead added"));
    }
}
