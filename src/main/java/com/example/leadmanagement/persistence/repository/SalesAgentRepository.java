package com.example.leadmanagement.persistence.repository;

import com.example.leadmanagement.persistence.entity.SalesAgent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesAgentRepository extends JpaRepository<SalesAgent, Long> {

    @Query("SELECT s FROM SalesAgent s")
    List<SalesAgent> findAllSalesAgents();

//    @Query("SELECT s FROM SalesAgent s WHERE s.phone = :keyword")
//    Page<SalesAgent> searchByPhoneSaleAgent(@Param("keyword") String keyword, Pageable pageable);

    List<SalesAgent> findSalesAgentByPhone(String Phone);



}
