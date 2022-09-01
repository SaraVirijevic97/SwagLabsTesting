package sampleapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This test verifies that Products, Shopping Cart, Menu, Twitter link, Facebook link, LinkedIn link and Logout are visible.

public class SwagLabs1 {
    public static WebDriver driver;

    @BeforeAll
    public static void setDriver() {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\vsara\\Desktop\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Test
    public void verifySwagLabs1() throws InterruptedException {

        driver.get("https://www.saucedemo.com/");
        String username = "standard_user";
        String password = "secret_sauce";

        WebElement txt_username = driver.findElement(By.name("user-name"));
        WebElement txt_password = driver.findElement(By.name("password"));
        WebElement login = driver.findElement(By.xpath("//input[@id='login-button']"));
        txt_username.sendKeys(username);
        txt_password.sendKeys(password);
        Thread.sleep(2000);
        login.click();
        String expectedUrl = driver.getCurrentUrl();
        String actualUrl = "https://www.saucedemo.com/inventory.html";
        assertEquals(expectedUrl,actualUrl, "Login failed.");

        boolean expected = true;
        boolean actualProduct = driver.findElement(By.xpath("//span[contains(text(), 'Products')]")).isDisplayed();
        assertEquals(expected, actualProduct, "Products isn't displayed. ");

        boolean actualShoppingCart = driver.findElement(By.xpath("//div[contains(@id, 'shopping')]/a[contains(@class, 'shopping_cart_link')]")).isDisplayed();
        assertEquals(expected, actualShoppingCart, "Shopping cart isn't displayed");

        boolean actualMenu = driver.findElement(By.id("react-burger-menu-btn")).isDisplayed();
        assertEquals(actualMenu, expected, "Burger menu isn't displayed.");

        boolean actualLinkTwitter = driver.findElement(By.xpath("//footer/ul/li[contains(@class, 'twitter')]/a[contains(text(),'Twitter')]")).isDisplayed();
        assertEquals(expected, actualLinkTwitter, "Twitter link isn't displayed");

        boolean actualLinkFacebook = driver.findElement(By.xpath("//footer/ul/li[contains(@class, 'facebook')]/a[contains(text(),'Facebook')]")).isDisplayed();
        assertEquals(expected, actualLinkFacebook, "Facebook isn't displayed");

        boolean actualLinkLinkedin = driver.findElement(By.xpath("//footer/ul/li[contains(@class, 'linkedin')]/a[contains(text(),'LinkedIn')]")).isDisplayed();
        assertEquals(expected, actualLinkLinkedin, "Linkedin isn't displayed");

        WebElement menu = driver.findElement(By.id("react-burger-menu-btn"));
        menu.click();
        Thread.sleep(2000);
        boolean actualLogout = driver.findElement(By.id("logout_sidebar_link")).isDisplayed();
        assertEquals(expected, actualLogout, "Logout isn't displayed");

    }
