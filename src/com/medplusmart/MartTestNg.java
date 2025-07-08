package com.medplusmart;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MartTestNg {
	WebDriver driver;

	@Test(priority = 1)
	public void assetLogin() throws InterruptedException {
		try {
			System.out.println("assetLogin Started");
			driver.findElement(By.id("name")).sendKeys("Abhichecker");
			driver.findElement(By.id("password-input")).sendKeys("Abhi@123");
			WebElement dropDown1 = driver.findElement(By.id("company"));
			Select dropDown = new Select(dropDown1);
			dropDown.selectByIndex(1);
			Thread.sleep(2000);
			driver.findElement(By.id("loginSubmit")).click();
			System.out.println("Login completed!");
			Thread.sleep(3000);
		} catch (Exception e) {
			System.out.println("Failed to load the page: " + e.getMessage());
		}
	}

	@Test(priority = 2)
	void assetCreater() throws InterruptedException {
		try {
			System.out.println("assetCreater Started");
			driver.findElement(By.xpath("//span[@class='ps-menu-label css-12w9als']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(
					"//div[@class='ps-submenu-content ps-open css-18unl23']//span[@class='ps-menu-label css-12w9als'][1]"))
					.click();
			Thread.sleep(2000);

			// Step 1: Type in location input
			Actions actions = new Actions(driver);
			WebElement locationInput = driver.findElement(By.id("locationSearch-input"));
			actions.moveToElement(locationInput).click().sendKeys("INAPHYD00384").pause(Duration.ofSeconds(1))
					.perform();
			WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));

			List<WebElement> suggestions = wait1.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'INAPHYD')]") 
					));

			for (WebElement suggestion : suggestions) {
				if (suggestion.getText().contains("INAPHYD00384")) {
					suggestion.click();
					break;
				}
			}
			WebElement empInput = driver.findElement(By.id("employeeSearch-input"));
			actions.moveToElement(empInput).click().sendKeys("MED0111165").pause(Duration.ofSeconds(1)).perform();
			List<WebElement> suggestions1 = wait1.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'MED011')]")));

			for (WebElement suggestion2 : suggestions1) {
				if (suggestion2.getText().contains("MED0111165")) {
					suggestion2.click();
					break;
				}
			}

			WebElement productInput = driver.findElement(By.id("input-productSelection"));
			actions.moveToElement(productInput).click().sendKeys("PIAY0001").pause(Duration.ofSeconds(1)).perform();

			List<WebElement> suggestions3 = wait1.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'PIAY0001')]")));
			for (WebElement suggestion4 : suggestions3) {
				if (suggestion4.getText().contains("PIAY0001")) {
					suggestion4.click();
					break;
				}
			}

			WebElement barcodeInput = driver.findElement(By.id("input-barcodeSelection"));
			actions.moveToElement(barcodeInput).click().sendKeys("260").pause(Duration.ofSeconds(1)).perform();
			List<WebElement> suggestions5 = wait1.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'260')]")));
			for (WebElement suggestion6 : suggestions5) {
				if (suggestion6.getText().contains("260")) {
					suggestion6.click();
					break;
				}
			}

			driver.findElement(By.xpath("//button[@class='w-50 btn-success btn btn-primary']")).click();
			driver.findElement(By.id("assert_checkbox")).click();
			driver.findElement(By.xpath("//div[@class='d-flex flex-row-reverse gap-3']")).click();
			System.out.println("Step 1 completed");
			Thread.sleep(2000);
			actions.sendKeys("09").sendKeys("06").sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys("09").perform();

			WebElement segmentInput = driver.findElement(By.id("segment"));
			actions.moveToElement(segmentInput).click().sendKeys("Retail").pause(Duration.ofSeconds(1)).perform();

			List<WebElement> suggestions6 = wait1.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'Retail')]")));
			for (WebElement suggestion7 : suggestions6) {
				if (suggestion7.getText().contains("Retail")) {
					suggestion7.click();
					break;
				}
			}
			driver.findElement(By.id("formSubmitButton")).click();
			System.out.println("Step 2 Completed and Step 3 Initiated ...");
			// Step 3

			driver.findElement(By.id("formSubmitButton")).click();

			System.out.println("Step 4 Completed and Step 4 Initiated ...");

			// Step 4 Initiated
			Thread.sleep(2000);
			WebElement serviceInput = driver.findElement(By.id("input-serviceSelection"));
			actions.moveToElement(serviceInput).click().sendKeys("ACIN002S").pause(Duration.ofSeconds(1)).perform();

			List<WebElement> suggestions7 = wait1.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'ACIN002S')]")));
			for (WebElement suggestion8 : suggestions7) {
				if (suggestion8.getText().contains("ACIN002S")) {
					suggestion8.click();
					break;
				}
			}

			WebElement batchInput = driver.findElement(By.id("input-batchSelection"));
			actions.moveToElement(batchInput).click().sendKeys("OTGCSOC25").pause(Duration.ofSeconds(1)).perform();

			List<WebElement> suggestions8 = wait1.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'OTGCSOC25')]")));
			for (WebElement suggestion9 : suggestions8) {
				if (suggestion9.getText().contains("OTGCSOC25")) {
					suggestion9.click();
					break;
				}
			}

			driver.findElement(By.id("consumeQuantity")).sendKeys("1");
			driver.findElement(By.xpath("//button[@class='w-50 btn-success btn btn-primary']")).click();
			driver.findElement(By.id("formSubmitButton")).click();
			System.out.println("Step 4 Completed and Movoing to Step 5 ...");

			// Step 5
			Thread.sleep(2000);
			driver.findElement(By.id("formSubmitButton")).click();
			driver.findElement(By.xpath("(//button[@class='px-4 btn btn-brand btn-sm'] )[2]")).click();

		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	@BeforeClass
	public void jnlp() {
		try {
			// Step 1: Launch the JNLP file
			String jnlpFilePath = "/home/karunakar/Downloads/medplusLoginJnlp(47).jnlp"; // <-- Change to your path
			File jnlpFile = new File(jnlpFilePath);

			if (!jnlpFile.exists()) {
				System.err.println("JNLP file not found at: " + jnlpFilePath);
				return;
			}

			System.out.println("Launching JNLP application...");
			ProcessBuilder pb = new ProcessBuilder("javaws", jnlpFilePath);
			pb.start();

			// Step 2: Wait for application to open
			System.out.println("Waiting for GUI to load...");
			Thread.sleep(3000);

			// Step 3: Automate GUI using SikuliX/home/karunakar/JNLP.sikuli
			Screen s = new Screen();
			Pattern laterButton = new Pattern("/home/karunakar/Documents/Selenium/sikuli screens.sikuli/Later.png")
					.similar(0.8);
			Pattern checkboxButton = new Pattern(
					"/home/karunakar/Documents/Selenium/sikuli screens.sikuli/Checkbox.png").similar(0.8);
			Pattern runButton = new Pattern("/home/karunakar/Documents/Selenium/sikuli screens.sikuli/Run.png")
					.similar(0.8);

			System.out.println("Attempting to interact with GUI...");

			s.wait(laterButton, 2); // Optional splash check
			s.click(laterButton);

			// Checkbox Button
			s.wait(checkboxButton, 10); // Optional splash check
			s.click(checkboxButton);

			// Run
			s.wait(runButton, 2); // Optional splash check
			s.click(runButton);

			// STEP 4: Wait for the app to open the browser
			System.out.println("Waiting for app to open browser");
			Thread.sleep(1000); // Adjust as needed

			// STEP 5: Try to confirm browser is open (optional visual check)

			try {
				Pattern browserTop = new Pattern(
						"/home/karunakar/Documents/Selenium/sikuli screens.sikuli/BrowserTop.png").similar(0.8);
				s.wait(browserTop, 10);
				s.click(browserTop);
				System.out.println("Web Browser Opened in New Tab");
			} catch (FindFailed e) {
				System.out.println(" BrowserTop.png not found");
			}

			// STEP 6: Use keyboard to copy the browser URL
			System.out.println("Wail Copying URL from address bar");
			s.keyDown(Key.CTRL);
			s.type("l");
			s.keyUp(Key.CTRL);
			Thread.sleep(500);

			s.keyDown(Key.CTRL);
			s.type("c");
			s.keyUp(Key.CTRL);
			Thread.sleep(1000);

			// STEP 7: Read and print URL from clipboard
			String copiedURL = getClipboardContents();
			System.out.println("🔗 Copied URL: " + copiedURL);

			if (copiedURL == null || !copiedURL.startsWith("http")) {
				System.out.println(" Faild to detect the URL.");
				System.exit(1);
			}

			System.setProperty("webdriver.chromedriver",
					"/home/karunakar/Documents/Selenium/chromedriver_linux64/chromedriver");
			/*
			 * ChromeOptions options = new ChromeOptions(); // Initialize the chrome driver
			 * WebDriver driver = new ChromeDriver(options);
			 */
			driver = new ChromeDriver();
			System.out.println("Opening detected URL in Selenium");
			driver.get(copiedURL);
			Thread.sleep(2000);
			driver.manage().window().maximize();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
			// assetLogin(driver);

		} catch (Exception e) {
			System.out.println("Failed to load the page: " + e.getMessage());
		}
	}

	// Helper method to read from clipboard
	private static String getClipboardContents() {
		System.out.println("📋 Attempting to read clipboard contents...");
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			Transferable contents = clipboard.getContents(null);
			if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return (String) contents.getTransferData(DataFlavor.stringFlavor);
			}
		} catch (Exception e) {
			System.out.println("📋 Clipboard read error: " + e.getMessage());
		}
		return null;
	}

	@AfterClass
	public void afterClass(WebDriver driver) {
		System.out.println("Test completed successfully!");
		// driver.quit();
	}
}
