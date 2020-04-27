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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class InfUgTest {
	
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
		driver.get("https://inf.ug.edu.pl/");
	}

	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void getAmountOfLinks(){
		List<WebElement> elements = driver.findElements(By.xpath("//a[@href and string-length(@href)!=0]"));
		System.out.println("Found "+elements.size()+" links on https://inf.ug.edu.pl/");
		assertFalse(elements.isEmpty());
	}

	@Test
	public void getAmountOfImages(){
		List<WebElement> elements = driver.findElements(By.xpath("//img[@src and string-length(@src)!=0]"));
		System.out.println("Found "+elements.size()+" images on https://inf.ug.edu.pl/");
		assertFalse(elements.isEmpty());
	}
	@Test
	public void getAllLinksEnterEachOfThemAndReturn(){
		String startTitle = driver.getTitle();
		Stream<String> hrefs =
				driver
				.findElements(By.xpath("//a[@href and string-length(@href)!=0 ]"))
				.stream()
				.map(x -> x.getAttribute("href"))
				.filter(x-> !x.endsWith("xml"))
				.filter(x -> !x.startsWith("mailto"));
		for (String href :
				hrefs.collect(Collectors.toList())) {
			driver.get(href);
			driver.navigate().back();
		}
		assertEquals(startTitle, driver.getTitle());
	}
	@Test
	public void getFormAllChilds(){
		driver.get("https://inf.ug.edu.pl/awaria");
		List<WebElement> formElements = driver.findElement(By.xpath("//form")).findElements(By.xpath("./*"));
		System.out.println("Found "+formElements.size()+" elements at form on https://inf.ug.edu.pl/awaria");
		assertTrue(formElements.size() >= 6);
	}
}
