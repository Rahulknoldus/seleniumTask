package DDT;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LoginClass {
    public WebDriverWait wait;

    public WebDriver driver;

    WriteExcelFile writeExcel = new WriteExcelFile();

    By email = By.id("email");
    By newPassword = By.id("newPassword");

    By password = By.id("password");


    @BeforeMethod
    public void beforeMethod() {

        System.out.println(" Test start on Chrome Browser");

    }


    @Test
    public void Chrome_browser_Test() throws InterruptedException, IOException {


        System.setProperty("webdriver.chrome.driver", "src/test/chromedriver_linux64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));


        driver.get("https://jf-frontshop-service-platform-sit2.loblaw.digital/ca");

//        Random rand = new Random();
//        int i = rand.nextInt(3000);
//        String randomEmailID = "User0" + i + "@yopmail.com";
//        writeExcel.writeExcelFile("Sheet1", randomEmailID, 0, 1);
//        System.out.println(randomEmailID);
        WebElement footer = driver.findElement(By.xpath("//button[@class='Modal_closeGrey__ApHlq Modal_bottom__kndkE Button_primary__PgyFQ Button_icon__GDOIB']"));
        footer.click();
        Thread.sleep(3000);
        WebElement footerCloseButton = driver.findElement(By.xpath("//button[text()='Close']"));
        footerCloseButton.click();
        driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//body/div[@id='__next']/div[@class='layout-container']/div[@role='main']/div[@class='layout__secondary-cta']/a[@class='link link--theme-base link--inline']/span[1]")).click();
        Random rand = new Random();
        int i = rand.nextInt(3000);
        Thread.sleep(5000);
        String randomEmailID = "User0" + i + "@yopmail.com";
//        String randomEmailID = "User01761@yopmail.com";

        writeExcel.writeExcelFile("Sheet1", randomEmailID, 0, 1);
        System.out.println(randomEmailID);
        driver.findElement(email).sendKeys(randomEmailID);
        driver.findElement(newPassword).sendKeys(randomEmailID);
        Thread.sleep(5000);
        driver.findElement(By.id("confirmPassword")).sendKeys(randomEmailID);
        Thread.sleep(300);
        driver.findElement(By.cssSelector(".checkbox-group__text")).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(5000);
        WebElement footer02 = driver.findElement(By.xpath("//button[@class='Modal_closeGrey__ApHlq Modal_bottom__kndkE Button_primary__PgyFQ Button_icon__GDOIB']"));
        footer02.click();
        Thread.sleep(3000);
        WebElement footerCloseButton02 = driver.findElement(By.xpath("//button[text()='Close']"));
        footerCloseButton02.click();
        driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(randomEmailID);
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(randomEmailID);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(5000);
        DevTools devTools = driver.getDevTools();
        devTools.createSession();


        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.responseReceived(), response -> {


            System.out.println("Response URL is : " + response.getResponse().getUrl() + "  status code is : " + response.getResponse().getStatus());


        });
        devTools.addListener(Network.requestWillBeSent(), request -> {
            System.out.println("Request body: " + request.getRequest().getPostData().toString() + "url: " + request.getRequest().getUrl());
        });
//        DevTools devTools = driver.getDevTools();
//        devTools.createSession();
//        devTools.send(Network.enable(Optional.of(1000000), Optional.empty(), Optional.empty()));
//        devTools.addListener(Network.requestWillBeSent(), request -> {
//            System.out.println("Request Method : " + request.getRequest().getMethod());
//            System.out.println("Request URL : " + request.getRequest().getUrl());
//            System.out.println("Request headers: " + request.getRequest().getHeaders().toString());
//            System.out.println("Request body: " + request.getRequest().getPostData().toString());
//        });

    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Finished Test On Chrome Browser");
    }

}




