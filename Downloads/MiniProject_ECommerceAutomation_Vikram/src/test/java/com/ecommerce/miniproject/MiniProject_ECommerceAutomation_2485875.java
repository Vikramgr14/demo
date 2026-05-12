package com.ecommerce.miniproject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class MiniProject_ECommerceAutomation_2485875 {

    WebDriver driver;
    WebDriverWait wait;
    SoftAssert softAssert;
    String productName;

    // ================== BROWSER SETUP ==================
    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
            System.out.println("Chrome browser launched");

        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
            System.out.println("Edge browser launched");

        } else {
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser +
                            ". Please use 'chrome' or 'edge'.");
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        softAssert = new SoftAssert();

        driver.get("https://www.demoblaze.com/");
        captureScreenshot("01_SiteOpened");
    }

    // ================== TEST 1: READ PRODUCT ==================2
    @Test(priority = 1)
    public void readProductFromExcel() {
        productName = readExcelData();
        softAssert.assertNotNull(productName, "Product name is null");
    }

    // ================== TEST 2: OPEN PRODUCT ==================
    @Test(priority = 2, dependsOnMethods = "readProductFromExcel")
    public void openProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(productName))).click();
        captureScreenshot("02_ProductOpened");
    }

    // ================== TEST 3: VALIDATE PRICE ==================
    @Test(priority = 3, dependsOnMethods = "openProduct")
    public void validatePrice() {
        WebElement price = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.price-container")));
        softAssert.assertFalse(price.getText().isEmpty(), "Price not displayed");
        captureScreenshot("03_PriceValidated");
    }

    // ================== TEST 4: ADD TO CART ==================
    @Test(priority = 4, dependsOnMethods = "validatePrice")
    public void addToCart() {
        driver.findElement(By.linkText("Add to cart")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        captureScreenshot("04_AddedToCart");
    }

    // ================== TEST 5: OPEN CART ==================
    @Test(priority = 5, dependsOnMethods = "addToCart")
    public void openCart() {
        driver.findElement(By.id("cartur")).click();
        wait.until(ExpectedConditions.urlContains("cart"));
        captureScreenshot("05_CartOpened");
    }

    // ================== TEST 6: PLACE ORDER ==================
    @Test(priority = 6, dependsOnMethods = "openCart")
    public void placeOrder() {
        driver.findElement(By.xpath("//button[text()='Place Order']")).click();//click place order button
        WebElement modal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));//waits for the order form popup
        softAssert.assertTrue(modal.isDisplayed(), "Order modal not displayed");
        captureScreenshot("06_OrderModalOpened");
    }

    // ================== TEST 7: TO FILL THE PAYMENT FORM ==================
    @Test(priority = 7, dependsOnMethods = "placeOrder")
    public void makePayment() {

        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("country")).sendKeys("India");
        driver.findElement(By.id("city")).sendKeys("Chennai");
        driver.findElement(By.id("card")).sendKeys("4111111111111111");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2026");

        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.sweet-alert h2")));

        softAssert.assertTrue(     //Validate Confirmation Message
                confirmation.getText().contains("Thank you"),
                "Order not confirmed");

        captureScreenshot("07_PaymentConfirmed");
        softAssert.assertAll();
    }

    // ================== EXCEL READER ==================
    public String readExcelData() {
        try {
            FileInputStream fis = new FileInputStream(  //open excel file
                    System.getProperty("user.dir") + "/src/test/resources/TestData.xlsx");
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            String value = sheet.getRow(0).getCell(0).toString();
            wb.close();
            return value;
        } catch (Exception e) {
            return "Samsung galaxy s6";
        }
    }

    // ================== SCREENSHOT ==================
    public void captureScreenshot(String stepName) {
        try {
            File dir = new File(System.getProperty("user.dir") + "/Screenshot");
            if (!dir.exists()) dir.mkdir();

            String date = LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("dd-yy-MM"));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(dir,
                    stepName + "_" + date + ".png");

           // src.renameTo(dest);
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ignored) {
        }
    }

    // ================== TEARDOWN ==================
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed");
        }
        else {
            System.out.println("Driver was not initialized");
        }

    }
}