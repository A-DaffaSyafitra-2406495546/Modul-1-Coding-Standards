package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = testBaseUrl + ":" + serverPort;
    }

    @Test
    void createProduct_success_productAppearsInList(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        driver.findElement(By.name("productName"))
                .sendKeys("Produk Selenium");

        driver.findElement(By.name("productQuantity"))
                .sendKeys("7");

        driver.findElement(By.tagName("button"))
                .click();

        driver.get(baseUrl + "/product/list");

        String pageSource = driver.getPageSource();

        assertTrue(pageSource.contains("Produk Selenium"));
    }
}
