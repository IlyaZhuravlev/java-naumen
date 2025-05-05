// src/test/java/com/example/selenium/AuthTest.java
package com.example.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private String baseUrl;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
        baseUrl = "http://localhost:" + port;
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testSuccessfulLoginAndLogout() {
        // 1. Переход на страницу входа
        driver.get(baseUrl + "/login");

        // 2. Ввод учетных данных
        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginButton.click();

        // 3. Проверка успешной авторизации
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement productsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h1[contains(text(),'Products List')]")));
        
        assertTrue(productsHeader.isDisplayed());
        assertEquals(baseUrl + "/products", driver.getCurrentUrl());

        // 4. Выход из системы
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();

        // 5. Проверка выхода
        WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("form[action='/login']")));
        assertTrue(loginForm.isDisplayed());
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    void testFailedLogin() {
        driver.get(baseUrl + "/login");

        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameInput.sendKeys("wrong");
        passwordInput.sendKeys("credentials");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement errorAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'alert-danger')]")));

        assertTrue(errorAlert.isDisplayed());
        assertTrue(errorAlert.getText().contains("Invalid username or password"));
    }
}