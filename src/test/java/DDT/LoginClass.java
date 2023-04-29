package DDT;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.interactions.Actions;
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
    Random random;
    Actions actions;
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
        actions = new Actions(driver);
        random = new Random();
        driver.get("https://jf-frontshop-service-platform-sit2.loblaw.digital/ca");
        WebElement footer = driver.findElement(By.xpath("//button[@class='Modal_closeGrey__ApHlq Modal_bottom__kndkE Button_primary__PgyFQ Button_icon__GDOIB']"));
        footer.click();
        Thread.sleep(3000);
        WebElement footerCloseButton = driver.findElement(By.xpath("//button[text()='Close']"));
        footerCloseButton.click();
        driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//body/div[@id='__next']/div[@class='layout-container']/div[@role='main']/div[@class='layout__secondary-cta']/a[@class='link link--theme-base link--inline']/span[1]")).click();
        Random rand = new Random();
        int i = rand.nextInt(1000000);
        Thread.sleep(5000);
        String randomEmailID = "TestUserForPerf" + i + "@yopmail.com";
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

        WebElement myAccount = driver.findElement(By.xpath("//button[@class='MyAccountDesktop_button__dRh5V']"));
        myAccount.click();
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
        WebElement paymentMethods = driver.findElement(By.xpath("//a[text()='Payment Methods']"));
        paymentMethods.click();
        WebElement card = driver.findElement(By.xpath("//button[@class='AccountPaymentInfo_button__wNevv Button_primary__PgyFQ']"));
        card.click();
        Thread.sleep(2000);
        //address Line1
        WebElement address = driver.findElement(By.xpath("//input[@id='addressLine1']"));
        address.sendKeys("2729 MacLaren Street");
        // add rest of the details of the address
        By dropDown = By.xpath("//div[@class='pca pcalist']/div[@class='pcaitem']");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropDown));
        int dropdownOption = random.nextInt(driver.findElements(dropDown).size());
        actions.moveToElement(driver.findElements(dropDown).get(dropdownOption)).click().build().perform();
        Thread.sleep(2000);
        WebElement continueToCardDetails = driver.findElement(By.xpath("//button[text()='Continue to card details']"));
        continueToCardDetails.click();
        // fill credit card
        Thread.sleep(8000);
        driver.switchTo().frame("globalPaymentFrame");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("pas_ccnum"))));
        WebElement creditCardNumberInput = driver.findElement(By.id("pas_ccnum"));
        creditCardNumberInput.sendKeys("4242424242424242");
        WebElement expiryDate = driver.findElement(By.id("pas_expiry"));
        expiryDate.sendKeys("12");
        expiryDate.sendKeys("2");
        expiryDate.sendKeys("9");
        WebElement cvccode = driver.findElement(By.id("pas_cccvc"));
        cvccode.sendKeys("400");
        WebElement cardHolderName = driver.findElement(By.id("pas_ccname"));
        cardHolderName.sendKeys("Test Account");
        WebElement saveCardButton = driver.findElement(By.cssSelector("button[value='Pay Now']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveCardButton);
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Finished Test On Chrome Browser");
    }
}




