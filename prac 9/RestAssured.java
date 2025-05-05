// src/test/java/com/example/controller/ReportControllerIT.java
package com.example.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void createReport_ShouldReturnReportId() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .post("/api/reports")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(notNullValue());
    }

    @Test
    void getReport_ShouldReturnNotFound_ForInvalidId() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/api/reports/9999")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getReport_ShouldReturnProcessingStatus_ForNewReport() {
        Long reportId = given()
            .contentType(ContentType.JSON)
        .when()
            .post("/api/reports")
        .then()
            .extract().as(Long.class);

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/api/reports/" + reportId)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(containsString("Report is being generated"));
    }
}