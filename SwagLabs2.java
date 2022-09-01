package sampleapp;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This test checks if the site Swag Labs is working well
// Sara Virijevic

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SwagLabs2 {
    public WebDriver driver;
    String expectedUrl = "https://www.saucedemo.com/inventory.html";
    String actualUrl;

    @BeforeAll
    public static void setDriver() {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\vsara\\Desktop\\chromedriver.exe");

    }

    @BeforeEach
    public void setup() throws InterruptedException {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        this.verifyLogin();

    }

    @AfterEach
    public void quit() throws InterruptedException {

        this.verifyLogout();
        driver.quit();

    }

    public void verifyLogin() throws InterruptedException {

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
        actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl, "Login failed.");

    }

    @Test
    public void verifySauceLabsBackpackProductTest() throws InterruptedException {

        WebElement backpack = driver.findElement(By.xpath("//div[contains(text(), 'Sauce Labs Backpack')]"));
        backpack.click();
        actualUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.saucedemo.com/inventory-item.html?id=4";
        assertEquals(expectedUrl, actualUrl, "Sauce Lab Backpack page failed.");
        Thread.sleep(1000);

        WebElement title = driver.findElement(By.xpath("//*[@id=\"inventory_item_container\"]/div/div/div[2]/div[1]"));
        String expectedTitle = "Sauce Labs Backpack";
        String actualTitle = title.getText();
        assertEquals(expectedTitle, actualTitle, "The title isn't the same as expected.");

        WebElement description = driver.findElement(By.xpath("//div[contains(@class, 'inventory_details_desc large_size')]"));
        String expectedDescription = "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.";
        String actualDescription = description.getText();
        assertEquals(expectedDescription, actualDescription, "The description isn't the same as expected.");

        WebElement price = driver.findElement(By.xpath("//div[contains(@class, 'inventory_details_price')]"));
        String expectedPrice = "$29.99";
        String actualPrice = price.getText();
        assertEquals(expectedPrice, actualPrice, "The price is not the same as expected.");

        WebElement addToCart = driver.findElement(By.xpath("//div/button[contains(@id,'add-to-cart')]"));
        addToCart.click();
        boolean remove = driver.findElement(By.xpath("//*[@id=\"remove-sauce-labs-backpack\"]")).isDisplayed();
        boolean expected = true;
        assertEquals(expected, remove, "The product has not been added to the cart");

        driver.findElement(By.xpath("//div/button[contains(@id,'back-to-products')]")).click();
        actualUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.saucedemo.com/inventory.html";
        assertEquals(expectedUrl, actualUrl, "Back to home page failed.");
        Thread.sleep(1000);

    }

    @Test
    public void verifyItemAddedToShoppingCartAndRemoveFromItTest() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-fleece-jacket\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"remove-sauce-labs-fleece-jacket\"]"));

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        Thread.sleep(1000);
        String expectedCartUrl = "https://www.saucedemo.com/cart.html";
        String actualCartUrl = driver.getCurrentUrl();
        assertEquals(expectedCartUrl, actualCartUrl, "Open shopping cart failed.");

        boolean sauceLabsBackpack = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).isDisplayed();

        driver.findElement(By.xpath("//*[@id=\"item_5_title_link\"]/div")).isDisplayed();

        boolean expectedProducts1 = true;
        driver.findElement(By.xpath("//*[@id=\"remove-sauce-labs-backpack\"]")).click();
        assertEquals(expectedProducts1, sauceLabsBackpack, "The product Sauce Labs Backpack has not been removed from the shopping cart");

        driver.findElement(By.xpath("//*[@id=\"continue-shopping\"]")).click();
        actualUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.saucedemo.com/inventory.html";
        assertEquals(expectedUrl, actualUrl, "Back to home page failed.");
        Thread.sleep(1000);

    }

    @Test
    public void verifyOrderingFlowTest() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-fleece-jacket\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();

        WebElement checkoutButton = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        checkoutButton.click();
        String expectedCheckoutUrl = "https://www.saucedemo.com/checkout-step-one.html";
        String actualCheckoutUrl = driver.getCurrentUrl();
        assertEquals(expectedCheckoutUrl, actualCheckoutUrl, "Checkout button doesn't work.");

        driver.findElement(By.xpath("//*[@id=\"cancel\"]")).click();
        String expectedCancelUrl = "https://www.saucedemo.com/cart.html";
        String actualCancelUrl = driver.getCurrentUrl();
        assertEquals(expectedCancelUrl, actualCancelUrl, "The cancel button doesn't work.");

        checkoutButton = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        checkoutButton.click();

        WebElement firstName = driver.findElement(By.xpath("//*[@id=\"first-name\"]"));
        firstName.sendKeys("Sara");

        WebElement lastName = driver.findElement(By.xpath("//*[@id=\"last-name\"]"));
        lastName.sendKeys("Virijevic");

        WebElement zip = driver.findElement(By.xpath("//*[@id=\"postal-code\"]"));
        zip.sendKeys("34000");

        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"continue\"]"));
        continueButton.click();
        String expectedContinueOrder = "https://www.saucedemo.com/checkout-step-two.html";
        String actualContinueOrder = driver.getCurrentUrl();
        assertEquals(expectedContinueOrder, actualContinueOrder, "The continue button doesn't work. Checkout overview page is failed.");

        driver.findElement(By.xpath("//*[@id=\"finish\"]")).click();
        String expectedFinish = "https://www.saucedemo.com/checkout-complete.html";
        String actualFinish = driver.getCurrentUrl();
        assertEquals(expectedFinish, actualFinish, "The finsh button doesn't work. Checkout complete page is failed.");

        boolean expectedFinishText = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")).isDisplayed();
        boolean actualFinishText = true;
        assertEquals(expectedFinishText, actualFinishText, "The text - THANK YOU FOR YOUR ORDER - isn't displayed.");

        boolean expectedDispatchText = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/div")).isDisplayed();
        boolean actualDispatchText = true;
        assertEquals(expectedDispatchText, actualDispatchText, "The text - Your order has been dispatched, and will arrive just as fast as the pony can get there! - isn't displayed.");

        driver.findElement(By.xpath("//*[@id=\"back-to-products\"]")).click();
        actualUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.saucedemo.com/inventory.html";
        assertEquals(expectedUrl, actualUrl, "Back to home page failed.");
        Thread.sleep(1000);

    }

    public void verifyLogout() throws InterruptedException {

        WebElement menu = driver.findElement(By.id("react-burger-menu-btn"));
        menu.click();
        Thread.sleep(2000);
        WebElement actualLogout = driver.findElement(By.id("logout_sidebar_link"));
        actualLogout.click();
        String expectedLoginPage = "https://www.saucedemo.com/";
        String actualLoginPage = driver.getCurrentUrl();
        assertEquals(expectedLoginPage, actualLoginPage, "Logout failed.");

    }

}
