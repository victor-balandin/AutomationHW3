import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderNegativeTests {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestIncorrectNameForm() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input ")).sendKeys("Victor Balandin");
        driver.findElement(By.cssSelector("[data-test-id=phone] input ")).sendKeys("+79153366655");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestEmptyNameForm() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input ")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input ")).sendKeys("+79153366655");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestIncorrectPhoneForm() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input ")).sendKeys("Виктор Баландин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input ")).sendKeys("89153366655");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestEmptyPhoneForm() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input ")).sendKeys("Виктор Баландин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input ")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestEmptyCheckboxForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Виктор Баландин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79153366655");

        driver.findElement(By.cssSelector("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector(".input_invalid [role=presentation]")).getText().trim();
        assertEquals(expected, actual);
    }
}