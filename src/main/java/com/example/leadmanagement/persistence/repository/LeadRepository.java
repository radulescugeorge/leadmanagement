package com.example.leadmanagement.persistence.repository;

import com.example.leadmanagement.persistence.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

}
