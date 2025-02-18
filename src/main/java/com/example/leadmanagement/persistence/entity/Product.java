package com.example.leadmanagement.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//Product entity
@Entity
@Getter
@Setter
@Table(name="products") // It will create a table named "products" in database
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private double price;

    //It has a OneToMany relationship with leads. (One product can be on multiple leads)
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<Lead> leads;

}
