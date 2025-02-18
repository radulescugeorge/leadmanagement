package com.example.leadmanagement.persistence.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// LoyaltyCard Entity
@Entity
@Getter
@Setter
@Table(name="loyaltycards") //It will create a table "loyaltycards" in database
public class LoyaltyCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String serialNumber;

    //This will affect the final amount of the lead.
    //A loyal customer has a greater discount
    private double discount;

    //Has a OneToOne relationship with Customer (One LoyaltyCard can be at only one customer )
    @JsonManagedReference
    @OneToOne(mappedBy = "loyaltyCard")
    private Customer customer;

}
