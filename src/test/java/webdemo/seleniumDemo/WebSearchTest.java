package webdemo.seleniumDemo;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebSearchTest {
	
	private static WebDriver driver;

	@BeforeAll
	public static void setUpDriver(){
		System.setProperty("webdriver.gecko.driver", "resources/geckodriver" + (System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : "" ));
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        options.addPreference("intl.accept_languages", "en");
		driver = new FirefoxDriver(options);
		// Implicity wait -> max czas na znalezienie elementu na stronie
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@BeforeEach
	public void setUp() throws Exception {
		driver.get("https://duckduckgo.com/");
	}

	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}

	
	@Test
	public void getResultsOfTestingAndAccessFirstResult(){
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("testing");
		driver.findElement(By.id("search_button_homepage")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector("#links a.result__a"));
		assertThat(elements.get(0).getAttribute("href").toLowerCase(), CoreMatchers.containsString("testing"));
	}

	@Test
	public void getResultsOfTestingAndAccessThirdResult(){
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("testing");
		driver.findElement(By.id("search_button_homepage")).click();
		List<WebElement> elements = driver.findElements(By.className("result__a"));
		assertThat(elements.get(3).getAttribute("href").toLowerCase(), CoreMatchers.containsString("testing"));
	}

	@Test
	public void clickSearchWithOtherWay(){
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("testing");
		WebElement searchButton = driver.findElement(By.id("search_button_homepage"));
		Dimension searchBtnSize = searchButton.getSize();
		Point searchButtonLocation = searchButton.getLocation();
		int posX = searchButtonLocation.x + searchBtnSize.width / 2;
		int posy = searchButtonLocation.y + searchBtnSize.height / 2;
		Actions act = new Actions(driver);
		act.moveByOffset(posX, posy).click().build().perform();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.titleIs("testing at DuckDuckGo"));
		assertEquals("testing at DuckDuckGo", driver.getTitle());
	}

	@Test
	public void lookForNonExistingElement(){
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			WebElement searchInput = driver.findElement(By.linkText("CANNOT_FIND_DUCKS"));
		});
	}

}
