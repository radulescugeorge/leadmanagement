package com.example.leadmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesAgentDto {
    private long id;
    private String name;
    private String phone;
    private String email;
}
