// src/main/java/com/example/repository/ReportRepository.java
package com.example.repository;

import com.example.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}