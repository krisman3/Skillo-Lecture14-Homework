package resources;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class IlievTests {
    String URL = "http://training.skillo-bg.com:4200";
    String HOME_URL = URL + "/posts/all";
    String LOGIN_URL = URL + "/users/login";
    String REG_URL = URL + "/users/register";
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }
//Creating a Data Provider for invalid emails:

    @DataProvider(name = "invalidEmails")
    public Object[][] invalidEmails() {
        return new Object[][]{
                {"abv@gmail."},
                {"abv@"},
                {"abv.com.com"},
                {"@gmail.com"},
                {"abv@gmail,com"},
                {"ab,v@gmail.com"},
                {"abv@@gmail.com"},
                {"abv@gmail..com"},
                {"a,bv@gmail.com"},
                {"abv@gmail.c/om"}
        };
    }

    //Testing an invalid email scenario.
    @Test(dataProvider = "invalidEmails")
    public void invalidEmail(String email) {
        //Navigating to the Registration page.
        navigateToReg();
        //Clicking on the email field
        WebElement emlBtn = driver.findElement(By.cssSelector("input[formcontrolname='email']"));
        clickElement(emlBtn);
        //Populating the email field with the content of the Data Provider.
        emlBtn.sendKeys(email);
        //Finding the error text.
        WebElement invalidMsg = driver.findElement(By.xpath("//span[contains(text(), 'Email invalid')]"));
        wait.until(ExpectedConditions.visibilityOf(invalidMsg));
        //Capturing the text from the message and verifying it with the expected behavior.
        String errorMsg = invalidMsg.getText();
        Assert.assertEquals(errorMsg, "Email invalid!");

    }

    //Testing passwords mismatch
    @Test
    public void passMismatch(){
        //Navigating to the Registration page.
        navigateToReg();
        //Finding and populating first pass field.
        WebElement firstPass = driver.findElement(By.id("defaultRegisterFormPassword"));
        clickElement(firstPass);
        //Adding correct pass
        firstPass.sendKeys("passw0rd");
        //Finding and populating second pass field
        WebElement secondPass = driver.findElement(By.cssSelector("input[formcontrolname='confirmPassword']"));
        clickElement(secondPass);
        //Adding incorrect pass
        secondPass.sendKeys("passw0r");

        //Finding the error message
        WebElement errorMsg = driver.findElement(By.xpath("//span[contains(text(), 'Passwords do not match!')]"));
        wait.until(ExpectedConditions.visibilityOf(errorMsg));
        //Grabbing the text from the error message
        String errMsg = errorMsg.getText();
        Assert.assertEquals(errMsg, "Passwords do not match!");



    }

    @AfterTest
    public void cleanUp(){
        driver.close();
    }

    //Creating a method to move to the Registration page
    private void navigateToReg() {
        //Homepage navigation.
        driver.get(HOME_URL);
        waitForURL(HOME_URL);
        //Click Login
        WebElement loginBtn = driver.findElement(By.id("nav-link-login"));
        clickElement(loginBtn);
        waitForURL(LOGIN_URL);
        //Click Register button
        WebElement regBtn = driver.findElement(By.cssSelector("p>a[href]"));
        clickElement(regBtn);
        waitForURL(REG_URL);
    }

    private void clickElement(WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement));

        webElement.click();
    }

    private void waitForURL(String URL) {
        wait.until(ExpectedConditions.urlToBe(URL));
    }


}

