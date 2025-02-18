package com.example.leadmanagement.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


// Customer Entity
@Entity
@Getter
@Setter
@Table(name="customers") //It will create a Table named "customers" in database.
public class Customer {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String city;
    private String phone;
    private String email;

    //Has a OneToOne relationship with LoyaltyCard (A Customer can have only one LoyaltyCard)
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="loyaltyCard_id", referencedColumnName = "id")
    private LoyaltyCard loyaltyCard;

    //and a One-to-Many relationship with Lead. (A Customer can have multiple leads)
    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private List<Lead> leads;
}
