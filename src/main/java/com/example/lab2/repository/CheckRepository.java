package com.example.lab2.repository;

import com.example.lab2.entity.ReleaseCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRepository extends JpaRepository<ReleaseCheck, Long> {
}
