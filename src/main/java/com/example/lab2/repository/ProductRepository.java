package com.example.lab2.repository;

import com.example.lab2.entity.SoftwareProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<SoftwareProduct, Long> {
}
