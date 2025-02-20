package com.example.leadmanagement.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/*
 Lead entity
 This will store data about the lead:
 - what product
 - who is the salesagent that has the lead
 - who is the customer
 - what quantity of the product
 - Total amount (minus client discount)
 - and the date of the lead
*/



@Entity
@Getter
@Setter
@Table(name="leads") // It will create a table named "leads" in database
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double quantity;
    private double totalAmount;
    private LocalDate date;


    // a ManyToOne relationship with customer (Many leads can be of a single customer)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

    // a ManyToOne relationship with product (Many leads can be of a single product.)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", referencedColumnName = "id")
    @JsonIgnore
    private Product product;

    // a ManyToOne relationship with salesagent (Many leads can be of a single salesagent.)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="salesagent", referencedColumnName = "id")
    @JsonIgnore
    private SalesAgent salesAgent;


}
