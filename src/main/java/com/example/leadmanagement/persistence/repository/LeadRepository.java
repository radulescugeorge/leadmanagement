package com.example.leadmanagement.persistence.repository;

import com.example.leadmanagement.persistence.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {


    List<Lead> findBySalesAgentId(long salesAgentId);

    List<Lead> findByCustomerId(long customerId);

    List<Lead> findByProductId(long productId);

    List<Lead> findByDate(LocalDate date);



}
