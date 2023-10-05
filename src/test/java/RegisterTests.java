import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

/*
4 negative tests on iSkillo Register Page, designed using Pairwise, EP and BVA testing techniques
 */

public class RegisterTests {
    private final String URL = "http://training.skillo-bg.com:4200";
    private final String HOME_URL = URL + "/posts/all";
    private final String LOGIN_URL = URL + "/users/login";
    private final String REGISTER_URL = URL + "/users/register";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // Registration Test with VALID Username and INVALID Email, Password, Confirm Password
    @Parameters({"username", "email", "password", "confirmPassword"})
    @Test
    public void negativeRegistrationTest_1(String username, String email, String password, String confirmPassword) {
        navigateToRegisterPage();

        System.out.println("8. Populate Username field with valid username and verify a valid sign is displayed");
        WebElement usernameField = findElement(By.cssSelector("input[name='username']"));
        typeInField(usernameField, username);
        Assert.assertTrue(getElementClassAttribute(usernameField).contains("is-valid"));

        System.out.println("9. Populate Email field with invalid email and verify that the invalid sign and the correct feedback message are displayed");
        WebElement emailField = findElement(By.cssSelector("input[type='email']"));
        typeInField(emailField, email);
        Assert.assertTrue(getElementClassAttribute(emailField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(emailField), "Email invalid!");

        System.out.println("10. Populate Password field with invalid password and verify that the invalid sign and the correct feedback message are displayed");
        WebElement passwordField = findElement(By.id("defaultRegisterFormPassword"));
        typeInField(passwordField, password);
        Assert.assertTrue(getElementClassAttribute(passwordField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(passwordField), "Minimum 6 characters !");

        System.out.println("11. Populate Confirm Password field with invalid confirm password and verify that the invalid sign and the correct feedback message are displayed");
        WebElement confirmPasswordField = findElement(By.id("defaultRegisterPhonePassword"));
        typeInField(confirmPasswordField, confirmPassword);
        Assert.assertTrue(getElementClassAttribute(confirmPasswordField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(confirmPasswordField), "Passwords do not match!");

        System.out.println("12. Click Sign In button");
        WebElement signInButton = findElement(By.id("sign-in-button"));
        clickOnElement(signInButton);

        System.out.println("13. Verify that the toast message is as expected");
        WebElement toastMessage = findElement(By.cssSelector(".toast-message"));
        waitForElementToBeVisible(toastMessage);
        Assert.assertEquals(toastMessage.getText(), "Registration failed!");

        System.out.println("14. Verify that the URL hasn't changed");
        Assert.assertEquals(driver.getCurrentUrl(), REGISTER_URL);
    }

    //Registration Test when all the fields are empty
    @Test
    public void negativeRegistrationTest_2() {
        navigateToRegisterPage();

        System.out.println("8. Leave Username, Email, Password and Confirm Password fields blank");
        WebElement usernameField = findElement(By.cssSelector("input[name='username']"));
        clickOnElement(usernameField);

        WebElement emailField = findElement(By.cssSelector("input[type='email']"));
        clickOnElement(emailField);

        WebElement passwordField = findElement(By.id("defaultRegisterFormPassword"));
        clickOnElement(passwordField);

        WebElement confirmPasswordField = findElement(By.id("defaultRegisterPhonePassword"));
        clickOnElement(confirmPasswordField);

        clickOnElement(passwordField);

        System.out.println("9. Verify that the invalid signs and the correct feedback messages are displayed");
        Assert.assertTrue(getElementClassAttribute(usernameField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(usernameField), "This field is required!");
        Assert.assertTrue(getElementClassAttribute(emailField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(emailField), "This field is required!");
        Assert.assertTrue(getElementClassAttribute(passwordField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(passwordField), "This field is required!");
        Assert.assertTrue(getElementClassAttribute(confirmPasswordField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(confirmPasswordField), "This field is required!"); // The test fails on this line because the feedback message is not displayed

        System.out.println("10. Click Sign In button");
        WebElement signInButton = findElement(By.id("sign-in-button"));
        clickOnElement(signInButton);

        System.out.println("11. Verify that the toast message is as expected");
        WebElement toastMessage = findElement(By.cssSelector(".toast-message"));
        waitForElementToBeVisible(toastMessage);
        Assert.assertEquals(toastMessage.getText(), "Registration failed!");

        System.out.println("12. Verify that the URL hasn't changed");
        Assert.assertEquals(driver.getCurrentUrl(), REGISTER_URL);
    }

    // Registration Test with INVALID Username, Email and VALID Password, Confirm Password
    @Parameters({"username", "email", "password", "confirmPassword"})
    @Test
    public void negativeRegistrationTest_3(String username, String email, String password, String confirmPassword) {
        navigateToRegisterPage();

        System.out.println("8. Populate Username field with invalid username and verify that the invalid sign and the correct feedback message are displayed");
        WebElement usernameField = findElement(By.cssSelector("input[name='username']"));
        typeInField(usernameField, username);
        Assert.assertTrue(getElementClassAttribute(usernameField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(usernameField), "Minimum 2 characters !");

        System.out.println("9. Populate Email field with invalid email and verify that the invalid sign and the correct feedback message are displayed");
        WebElement emailField = findElement(By.cssSelector("input[type='email']"));
        typeInField(emailField, email);
        Assert.assertTrue(getElementClassAttribute(emailField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(emailField), "Email invalid!");

        System.out.println("10. Populate Password field with valid password and verify a valid sign is displayed");
        WebElement passwordField = findElement(By.id("defaultRegisterFormPassword"));
        typeInField(passwordField, password);
        Assert.assertTrue(getElementClassAttribute(passwordField).contains("is-valid"));

        System.out.println("11. Populate Confirm Password field with valid confirm password and verify a valid sign is displayed");
        WebElement confirmPasswordField = findElement(By.id("defaultRegisterPhonePassword"));
        typeInField(confirmPasswordField, confirmPassword);
        Assert.assertTrue(getElementClassAttribute(confirmPasswordField).contains("is-valid"));

        System.out.println("12. Click Sign In button");
        WebElement signInButton = findElement(By.id("sign-in-button"));
        clickOnElement(signInButton);

        System.out.println("13. Verify that the toast message is as expected");
        WebElement toastMessage = findElement(By.cssSelector(".toast-message"));
        waitForElementToBeVisible(toastMessage);
        Assert.assertEquals(toastMessage.getText(), "Registration failed!");

        System.out.println("14. Verify that the URL hasn't changed");
        Assert.assertEquals(driver.getCurrentUrl(), REGISTER_URL);
    }

    // Registration Test with VALID Username, Email, Confirm Password and INVALID Password
    @Parameters({"username", "email", "password", "confirmPassword"})
    @Test
    public void negativeRegistrationTest_4(String username, String email, String password, String confirmPassword) {
        navigateToRegisterPage();

        System.out.println("8. Populate Username field with valid username and verify a valid sign is displayed");
        WebElement usernameField = findElement(By.cssSelector("input[name='username']"));
        typeInField(usernameField, username);
        Assert.assertTrue(getElementClassAttribute(usernameField).contains("is-valid"));

        System.out.println("9. Populate Email field with valid email and verify a valid sign is displayed");
        WebElement emailField = findElement(By.cssSelector("input[type='email']"));
        typeInField(emailField, email);
        Assert.assertTrue(getElementClassAttribute(emailField).contains("is-valid"));

        System.out.println("10. Populate Password field with invalid password and verify that the invalid sign and the correct feedback message are displayed");
        WebElement passwordField = findElement(By.id("defaultRegisterFormPassword"));
        typeInField(passwordField, password);
        Assert.assertTrue(getElementClassAttribute(passwordField).contains("is-invalid"));
        Assert.assertEquals(getInvalidFeedbackMessage(passwordField), "Maximum 20 characters!");

        System.out.println("11. Populate Confirm Password field with valid confirm password and verify a valid sign is displayed");
        WebElement confirmPasswordField = findElement(By.id("defaultRegisterPhonePassword"));
        typeInField(confirmPasswordField, confirmPassword);
        Assert.assertTrue(getElementClassAttribute(confirmPasswordField).contains("is-valid"));

        System.out.println("12. Click Sign In button");
        WebElement signInButton = findElement(By.id("sign-in-button"));
        clickOnElement(signInButton);

        System.out.println("13. Verify that the toast message is as expected");
        WebElement toastMessage = findElement(By.cssSelector(".toast-message"));
        waitForElementToBeVisible(toastMessage);
        Assert.assertEquals(toastMessage.getText(), "Registration failed!");

        System.out.println("14. Verify that the URL hasn't changed");
        Assert.assertEquals(driver.getCurrentUrl(), REGISTER_URL);
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    private void navigateToRegisterPage() {
        System.out.println("1. Go to homepage");
        driver.get(HOME_URL);

        System.out.println("2. Click Login link");
        clickOnElement(findElement(By.id("nav-link-login")));

        System.out.println("3. Verify that the URL has changed to /login");
        waitUrlToBe(LOGIN_URL);

        System.out.println("4. Verify that the Login Form is visible");
        waitForElementToBeVisible(findElement(By.cssSelector(".login-container form")));

        System.out.println("5. Click Register link");
        clickOnElement(findElement(By.linkText("Register")));

        System.out.println("6. Verify that the URL has changed to /register");
        waitUrlToBe(REGISTER_URL);

        System.out.println("7. Verify that the Register Form is visible");
        waitForElementToBeVisible(findElement(By.cssSelector(".login-container form")));
    }

    private void typeInField(WebElement webElement, String input) {
        waitForElementToBeVisible(webElement);
        webElement.sendKeys(input);
    }

    private void clickOnElement(WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    private void waitForElementToBeVisible(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    private void waitUrlToBe(String URL) {
        wait.until(ExpectedConditions.urlToBe(URL));
    }

    private WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    private String getElementClassAttribute(WebElement webElement) {
        waitForElementToBeVisible(webElement);
        return webElement.getAttribute("class");
    }

    private String getInvalidFeedbackMessage(WebElement webElement) {
        waitForElementToBeVisible(webElement);
        String invalidMessage = "";

        if (webElement.getAttribute("name").equals("username")) {
            invalidMessage = findElement(By.cssSelector(".input-filed:nth-child(2) > .invalid-feedback")).getText();
        } else if (webElement.getAttribute("type").equals("email")) {
            invalidMessage = findElement(By.cssSelector(".input-filed:nth-child(3) > .invalid-feedback")).getText();
        } else if (webElement.getAttribute("id").equals("defaultRegisterFormPassword")) {
            invalidMessage = findElement(By.cssSelector(".input-filed:nth-child(4) > .invalid-feedback")).getText();
        } else if (webElement.getAttribute("id").equals("defaultRegisterPhonePassword")) {
            invalidMessage = findElement(By.cssSelector(".input-filed:nth-child(5) > .invalid-feedback")).getText();
        }

        return invalidMessage;
    }
}