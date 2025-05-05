// src/main/java/com/example/service/ReportService.java
package com.example.service;

import com.example.model.Report;
import com.example.model.User;
import com.example.repository.ProductRepository;
import com.example.repository.ReportRepository;
import com.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReportService(ReportRepository reportRepository, 
                       UserRepository userRepository,
                       ProductRepository productRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Long createReport() {
        Report report = new Report();
        report.setStatus(Report.Status.CREATED);
        report = reportRepository.save(report);
        return report.getId();
    }

    @Async
    public CompletableFuture<Void> generateReport(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        
        try {
            // Запускаем задачи в отдельных потоках
            CompletableFuture<String> usersTask = CompletableFuture.supplyAsync(this::countUsers);
            CompletableFuture<String> productsTask = CompletableFuture.supplyAsync(this::listProducts);
            
            // Ждем завершения всех задач
            CompletableFuture.allOf(usersTask, productsTask).join();
            
            // Получаем результаты
            String usersResult = usersTask.get();
            String productsResult = productsTask.get();
            
            // Формируем HTML отчет
            String htmlReport = buildHtmlReport(usersResult, productsResult);
            
            report.setContent(htmlReport);
            report.setStatus(Report.Status.COMPLETED);
        } catch (Exception e) {
            log.error("Error generating report", e);
            report.setStatus(Report.Status.ERROR);
            report.setContent("Error generating report: " + e.getMessage());
        }
        
        reportRepository.save(report);
        return CompletableFuture.completedFuture(null);
    }

    private String countUsers() {
        long startTime = System.currentTimeMillis();
        
        long count = userRepository.count();
        String result = "Total users: " + count;
        
        long elapsed = System.currentTimeMillis() - startTime;
        return result + " (Time: " + elapsed + "ms)";
    }

    private String listProducts() {
        long startTime = System.currentTimeMillis();
        
        List<Product> products = productRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<table class='table'><tr><th>ID</th><th>Name</th><th>Price</th></tr>");
        
        for (Product product : products) {
            sb.append("<tr><td>").append(product.getId()).append("</td>")
              .append("<td>").append(product.getName()).append("</td>")
              .append("<td>").append(product.getPrice()).append("</td></tr>");
        }
        sb.append("</table>");
        
        long elapsed = System.currentTimeMillis() - startTime;
        return sb.toString() + " (Time: " + elapsed + "ms)";
    }

    private String buildHtmlReport(String usersResult, String productsResult) {
        long startTime = System.currentTimeMillis();
        
        String[] usersParts = usersResult.split("\\(");
        String[] productsParts = productsResult.split("\\(");
        
        String html = "<!DOCTYPE html>" +
                "<html><head>" +
                "<title>Application Report</