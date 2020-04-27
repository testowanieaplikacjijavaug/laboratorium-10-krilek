package webdemo.seleniumDemo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StackOverflowTest {
	// Robots do brrrr
	private final String correctEmail = "k.gzik.293@studms.ug.edu.pl";
	private final String correctPassword = "ZAQ!2wsx";
	private static WebDriver driver;
	private WebElement inputEmail;
	private WebElement inputPassword;
	private WebElement submitButton;

	@BeforeEach
	public void setUpDriver(){
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver" + (System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : "" ));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en", "--no-sandbox", "--headless", "--disable-dev-shm-usage");
		options.setHeadless(true);
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://stackoverflow.com/users/login");
		this.inputEmail = driver.findElement(By.cssSelector("input#email"));
		this.inputPassword = driver.findElement(By.cssSelector("input#password"));
		this.submitButton = driver.findElement(By.cssSelector("button#submit-button"));
	}
	@AfterEach
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

	@Test
	public void tryToAccessWithoutEmail(){
		this.inputPassword.sendKeys(this.correctPassword);
		this.submitButton.click();
		WebElement messageElement = driver.findElement(By.cssSelector("div.has-error p.grid--cell.s-input-message.js-error-message"));
		assertEquals("Email cannot be empty.", messageElement.getText());
	}

	@Test
	public void tryToAccessWithoutPassword(){
		this.inputEmail.sendKeys(this.correctEmail);
		this.submitButton.click();
		WebElement messageElement = driver.findElement(By.cssSelector("div.has-error p.grid--cell.s-input-message.js-error-message"));
		assertEquals("Password cannot be empty.", messageElement.getText());
	}

	@Test
	public void tryToAccessWithWrongCredentials(){
		this.inputEmail.sendKeys("wrong@email.com");
		this.inputPassword.sendKeys("wrongpassword");
		this.submitButton.click();
		WebElement messageElement = driver.findElement(By.cssSelector("div.has-error p.grid--cell.s-input-message.js-error-message"));
		assertEquals("The email or password is incorrect.", messageElement.getText());
	}

	@Test
	public void accessSiteWithCorrectCredentials(){
		this.inputEmail.sendKeys(this.correctEmail);
		this.inputPassword.sendKeys(this.correctPassword);
		this.submitButton.click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.ws-nowrap.s-btn.s-btn__primary")));
		WebElement elementWithUserLink = driver.findElement(By.cssSelector("a[href*='testuserug']"));
		assertEquals("my-profile js-gps-track", elementWithUserLink.getAttribute("class"));
	}

}
