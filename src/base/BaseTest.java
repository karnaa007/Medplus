package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
	public WebDriver driver;
	
void setup() {
		// Setup code for initializing WebDriver, etc.
		System.out.println("Setting up the test environment");
		System.setProperty("webdriver.chrome.driver", "/home/karunakar/Documents/Selenium/chromedriver_linux64/chromedriver"); // Update with your path
		driver = new ChromeDriver();
		
	}

	void teardown() {
		// Cleanup code after tests
		System.out.println("Tearing down the test environment");
	}

	void wait(int milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}
}
