package DDT;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    //        @Test(invocationCount = 3)
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
        int i = rand.nextInt(10000);
        Thread.sleep(5000);
        String randomEmailID = "TestUserForPerf" + i + "@yopmail.com";
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
            if ((response.getResponse().getUrl()).contains("getMemberInfo")) {
                System.out.println(response.getResponse().getUrl());
                String pcIdURL = response.getResponse().getUrl();
                Pattern pattern = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
                Matcher matcher = pattern.matcher(pcIdURL);
                if (matcher.find()) {
                    String pcId = matcher.group(0);
                    System.out.println(pcId);
                    try {
                        writeExcel.writeExcelFile("Sheet1", pcId, 0, 2);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        WebElement myAccount = driver.findElement(By.xpath("//button[@class='MyAccountDesktop_button__dRh5V']"));
        myAccount.click();
        WebElement paymentMethods = driver.findElement(By.xpath("//a[text()='Payment Methods']"));
        paymentMethods.click();
        WebElement card = driver.findElement(By.xpath("//button[@class='AccountPaymentInfo_button__wNevv Button_primary__PgyFQ']"));
        card.click();
        WebElement address = driver.findElement(By.xpath("//input[@id='addressLine1']"));
        address.sendKeys("2729 MacLaren Street");
        address.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        WebElement submit = driver.findElement(By.xpath("//button[@class='AccountPaymentInfo_button__wNevv Button_primary__PgyFQ']"));
        submit.click();
        WebElement cardNumber = driver.findElement(By.xpath("//input[@placeholder= \"Card Number\"]"));
        cardNumber.sendKeys("4242424242424242");
        WebElement expiry = driver.findElement(By.xpath("//input[@placeholder= \"MM/YY\"]"));
        expiry.sendKeys("1223");
        WebElement security = driver.findElement(By.xpath("//input[@placeholder= \"Security Code\"]"));
        security.sendKeys("123");
        WebElement cardholder = driver.findElement(By.xpath("//input[@placeholder= \"Cardholder Name\"]"));
        cardholder.sendKeys("Test User");
        WebElement save = driver.findElement(By.xpath("//button[@value= \"Pay Now\"]"));
        save.click();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Finished Test On Chrome Browser");
    }

}




