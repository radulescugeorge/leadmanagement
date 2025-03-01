package com.example.leadmanagement.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//SalesAgent Entity

@Entity
@Getter
@Setter
@Table(name="salesagents") // It will create a table named "salesagents" in database
@JsonIgnoreProperties("leads")
@AllArgsConstructor
@NoArgsConstructor
public class SalesAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String phone;
    private String email;

    //It has a OneToMany relationship with lead. (A salesagent can have multiple sales leads)
    @JsonManagedReference
    @OneToMany(mappedBy = "salesAgent")
    private List<Lead> leads;



}
