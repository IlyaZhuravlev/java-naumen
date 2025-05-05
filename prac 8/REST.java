// src/main/java/com/example/controller/ReportController.java
package com.example.controller;

import com.example.model.Report;
import com.example.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public Long createReport() {
        Long reportId = reportService.createReport();
        reportService.generateReport(reportId);
        return reportId;
    }

    @GetMapping("/{id}")
    public String getReport(@PathVariable Long id) {
        Report report = reportService.getReport(id);
        
        switch (report.getStatus()) {
            case CREATED:
                return "Report is being generated. Please try again later.";
            case ERROR:
                return "Error generating report: " + report.getContent();
            case COMPLETED:
                return report.getContent();
            default:
                return "Unknown report status";
        }
    }
}