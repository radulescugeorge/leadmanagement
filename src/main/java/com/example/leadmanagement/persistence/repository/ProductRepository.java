package com.example.leadmanagement.persistence.repository;

import com.example.leadmanagement.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p ")
    List<Product> findAllProducts();

    List<Product> findByNameContainingIgnoreCase(String name);
}
