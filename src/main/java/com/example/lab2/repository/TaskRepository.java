package com.example.lab2.repository;

import com.example.lab2.entity.DevelopmentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<DevelopmentTask, Long> {
}
