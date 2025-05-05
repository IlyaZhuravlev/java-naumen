// src/test/java/com/example/service/ReportServiceTest.java
package com.example.service;

import com.example.model.Report;
import com.example.repository.ProductRepository;
import com.example.repository.ReportRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ReportService reportService;

    @Test
    void createReport_ShouldReturnReportId() {
        Report report = new Report();
        report.setId(1L);
        
        when(reportRepository.save(any(Report.class))).thenReturn(report);
        
        Long reportId = reportService.createReport();
        
        assertEquals(1L, reportId);
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void generateReport_ShouldSetCompletedStatus_WhenSuccessful() throws Exception {
        Report report = new Report();
        report.setId(1L);
        
        when(reportRepository.findById(1L)).thenReturn(java.util.Optional.of(report));
        when(userRepository.count()).thenReturn(5L);
        
        CompletableFuture<Void> future = reportService.generateReport(1L);
        future.get(); // Ждем завершения
        
        assertEquals(Report.Status.COMPLETED, report.getStatus());
        assertNotNull(report.getContent());
    }

    @Test
    void getReport_ShouldThrowException_WhenNotFound() {
        when(reportRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        
        assertThrows(RuntimeException.class, () -> reportService.getReport(1L));
    }
}