package webdemo.seleniumDemo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PracticeTestOpera {

	private static WebDriver driver;

	@BeforeAll
	public static void setUpDriver(){
		System.setProperty("webdriver.opera.driver", "resources/operadriver" + (System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : "" ));
        OperaOptions options = new OperaOptions();
		options.addArguments("--headless");
		driver = new OperaDriver(options);
		// Implicity wait -> max czas na znalezienie elementu na stronie
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}

    @BeforeEach
    public void setUp() {
        driver.get("http://automationpractice.com/index.php");
    }

    @Test
    public void clickMenuOptionWomen() {
        driver.findElement(By.cssSelector("a.sf-with-ul[title*='Women']"))
                .click();
        assertTrue(driver.getTitle()
                .contains("Women - My Store"));
    }

    @Test
    public void searchDress() {
        driver.findElement(By.cssSelector("input#search_query_top")).sendKeys("dress");
        driver.findElement(By.cssSelector("button.btn.btn-default.button-search")).click();
        assertEquals("\"DRESS\"",driver.findElement(By.cssSelector("h1.page-heading.product-listing span.lighter")).getText());
    }

    @Test
    public void gotoSeleniumWebsite() {
        WebElement seleniumBtn = driver.findElement(By.cssSelector("a.btn.btn-default[href='http://www.seleniumframework.com']"));
        seleniumBtn.click();
        assertEquals("Selenium Framework | Selenium, Cucumber, Ruby, Java et al.", driver.getTitle());
    }
}
