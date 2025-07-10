package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {
	public static WebDriver driver;

	public void setup() {
		// Setup code for initializing WebDriver, etc.
		System.out.println("Setting up the test environment");
		System.setProperty("webdriver.chromedriver",
				"/home/karunakar/Documents/Selenium/chromedriver_linux64/chromedriver");
		
		ChromeOptions options = new ChromeOptions();
		// Disable notifications
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		this.driver = driver;
		driver.manage().window().maximize();
	//	driver.get("https://wiki.medplusindia.com/");
		driver.get("https://www.medplusmart.com/");

		try {
			Thread.sleep(5000); // Wait for the page to load
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
