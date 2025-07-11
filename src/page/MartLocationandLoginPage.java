package page;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseTest;

public class MartLocationandLoginPage extends BaseTest {
	public String mobileNo = "9951994998";
	public static String otp;
	public static WebElement verifyOTP;
	public static WebElement verifyOTPBtn;
	public static WebElement loginLink;
	public static String userName = null;
	public static String productName = null;
	public static String quantityDropdown1 = null;
	public static String quantityDropdown = null;
	public WebElement locationInput;
	WebElement locateMe = null;
	WebDriverWait wait = null;

	public void locatoin() throws InterruptedException {
		System.out.println("Location Method");
		// Implementation for selecting location
		driver.findElement(By.id("locationTooltip")).click();
		Thread.sleep(3000);
		System.out.println("Location Tooltip Clicked");
		locationInput = driver.findElement(By.cssSelector(".rbt-input-main"));
//		Assert.assertTrue(locationInput.isDisplayed(), "Location Input Field is Available");
		locationInput.click();
		System.out.println("Location Input Field Clicked");
		Thread.sleep(2000);
		locationInput.sendKeys("madhapur");
		Thread.sleep(2000);
		System.out.println("Enter madhapur in Location Input Field");
		WebElement locationOption = driver.findElement(By.cssSelector(".active p > .d-block"));
		System.out.println(
				"Location Option Found: " + locationOption.isDisplayed() + " and " + locationOption.isEnabled());
		if (locationOption.isDisplayed() || locationOption.isEnabled()) {
			locationOption.click();
			System.out.println("Location selected successfully");
		} else {
			System.out.println("Location option not available");
		}

	}

	public void martLogin(String mobileNo) throws InterruptedException {
		// Implementation for login using mobile number
		System.out.println("Login Page Start");
		driver.navigate().refresh();
		Thread.sleep(5000);
		System.out.println("Login Page Refreshed");
		loginLink = driver.findElement(By.xpath("//span[@class='user-name']"));
		System.out.println("Login Link Found: " + loginLink.isDisplayed() + " and " + loginLink.isEnabled());
		if (loginLink.isDisplayed() || loginLink.isEnabled()) {
			System.out.println("Login Link is Displayed and Enabled");
			loginLink.click();
			System.out.println("Clicked on Login Link");
			Thread.sleep(3000);
			driver.findElement(By.id("mobileNumber")).sendKeys(mobileNo);
			Thread.sleep(2000);
			verifyOTP = driver.findElement(By.cssSelector(".btn-brand-gradient:nth-child(2)"));
			verifyOTP.click();
			System.out.println("Clicked on OTP");
			Thread.sleep(5000);
		} else {
			System.out.println("Login Link is not available");
			Assert.fail("Login Link is not available on the page");
		}
		/*
		 * loginLink.click(); Thread.sleep(5000);
		 * driver.findElement(By.id("mobileNumber")).sendKeys("mobileNo");
		 * driver.findElement(By.
		 * xpath("//button[@class='btn btn-brand-gradient rounded-pill btn-block custom-btn-lg']"
		 * )).click(); Thread.sleep(4000);
		 */
	}

	// JDBC method to fetch OTP from MySQL
	public String fetchOTPFromDB() {
		String url = "jdbc:mysql://192.168.1.11"; // replace with your DB URL
		String user = "varma"; // replace with your DB user
		String password = "varma321"; // replace with your DB password
		System.out.println("Url,USer,Paswrd" + url + "," + user + "," + password);

		try {
			System.out.println("DB connection Started");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = conn.createStatement();
			String query = "select * from customers.tbl_customer_otp where MobileNo=9951994998 and Vertical='W' order by 1 desc limit 1";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				otp = rs.getString("otp");
				System.out.println("OTP fetched from DB: " + otp);

				try {
					wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increased wait time
					WebElement otpInput = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='OTP']")));
					otpInput.sendKeys(otp);
					Thread.sleep(2000);
					System.out.println("Clicked on Vrify OTP ");
					// Thread.sleep(5000);

					String userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
							"//span[@class='username mr-2 text-white d-inline-flex align-items-center justify-content-center font-16']")))
							.getText();
					try {
						Assert.assertEquals(userName, "karna", "User name does not match");
						System.out.println("loginLink Pass " + userName);
					} catch (AssertionError e) {
						System.out.println("loginLink Fail " + e.getMessage());
					}
				} catch (org.openqa.selenium.TimeoutException e) {
					System.out.println("OTP input field not found: " + e.getMessage());
					Assert.fail("OTP input field not found");
				}
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DB Connection Closed");
		return otp;
	}

	public static void searchAndAddProduct() throws InterruptedException {
		System.out.println("search And Add Product");
		String filePath = "/home/karunakar/Documents/Selenium/TestData.xls";

		try {
			FileInputStream file = new FileInputStream(new String(filePath));
			Workbook workbook = new HSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0); // or workbook.getSheet("Sheet1");
			System.out.println("Sheet selected and Opedned");

			for (Row row : sheet) {
				// Process each row
				for (int i = 0; i < row.getLastCellNum(); i++) {
					Cell cell = row.getCell(i);
					System.out.println("\n  Data From Excel Product Name is " + cell);
					if (cell != null) {
						productName = cell.toString();
						System.out.println("Product Name is : " + cell.toString() + "\t");
					} else {
						System.out.print("[BLANK]\t");
					}

					WebElement passDataInSearchTextBox = driver
							.findElement(By.xpath("//div[@class='homepage-input w-100']"));
					passDataInSearchTextBox.click();
					Thread.sleep(2000);

					WebElement passDataInSearchTextBox1 = driver.findElement(
							By.xpath("//input[@class='font-16 form-control form-control-lg text-secondary ']"));
					passDataInSearchTextBox1.sendKeys(productName);
					Thread.sleep(3000);

					try {
						WebElement quantityOption = driver
								.findElement(By.xpath("//a[@class='btn btn-sm px-0 text-brand ']"));
						System.out.println(" Quantity Option Available " + quantityOption.isDisplayed() + "OR"
								+ quantityOption.isEnabled());
						Assert.assertTrue(quantityOption.isDisplayed(), "Quantity Option is not Available");
						quantityOption.click();
						Thread.sleep(2000);
						System.out.println("clicked on Add to cart button");

					} catch (NoSuchElementException e) {
						System.out.println(e.getMessage());
						System.out.println("Add to cart button not available for this product");

					}
					driver.navigate().refresh();
					Thread.sleep(3000);
				}

			}

			workbook.close();
			file.close();
			System.out.println("closing the excel file");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void miniCart() throws InterruptedException {
		System.out.println("Mini Cart Method");
		try {
			WebElement miniCart = driver
					.findElement(By.xpath("//div[@class=\"d-inline-block icon-hover p-0 dropdown\"]"));
			System.out.println("Mini Cart Clicked");
			Thread.sleep(3000);
			System.out.println("Item in cart: " + miniCart.getText() + " is displayed " + miniCart.isDisplayed()
					+ " and enabled " + miniCart.isEnabled());

			String cartText = miniCart.getText().trim();
			System.out.println("Number of items in the cart: " + cartText);

			int miniCartCount = 0;
			try {
				miniCartCount = Integer.parseInt(cartText.replaceAll("[^0-9]", ""));
			} catch (NumberFormatException nfe) {
				System.out.println("Cart text is not a valid number: " + cartText);
			}

			Assert.assertTrue(miniCartCount > 0 || miniCart.isDisplayed(), "Mini Cart is not displayed");
			System.out.println("Mini Cart is displayed and enabled");
			miniCart.click();
			System.out.println("Mini Cart Clicked");
		} catch (NoSuchElementException e) {
			System.out.println("Mini Cart is not displayed or not enabled :" + e.getMessage());
		}
	}

	public void proceedToCheckout() throws InterruptedException {
		System.out.println("Proceed to Checkout Method");
		try {
			WebElement proceedToCheckout = driver.findElement(By.id("pharmaCheckOutBtn"));
			System.out.println("Proceed to Checkout Button Found: " + proceedToCheckout.isDisplayed() + " and "
					+ proceedToCheckout.isEnabled());
			if (proceedToCheckout.isDisplayed() || proceedToCheckout.isEnabled()) {
				proceedToCheckout.click();
				//Thread.sleep(5000);
				System.out.println("Clicked on Proceed to Checkout Button");
			} else {
				System.out.println("Proceed to Checkout Button is not available");
				Assert.fail("Proceed to Checkout Button is not available on the page");
			}
		} catch (NoSuchElementException e) {
			System.out.println("Proceed to Checkout Button not found: " + e.getMessage());
			Assert.fail("Proceed to Checkout Button not found");
		}
	}

	public void selectPatient() throws InterruptedException {
		System.out.println("Verify selectPatient ");
		try {
			// Wait for the page to load and the radio button to be present
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='custom-control-input']")));

			WebElement selectPatientRadioBtn = driver.findElement(By.xpath("//input[@class='custom-control-input']"));
			System.out.println("Cart Found: " + selectPatientRadioBtn.isSelected());
			if (selectPatientRadioBtn.isSelected()) {
				// selectPatientRadioBtn.click();
				Thread.sleep(2000);
				System.out.println("Clicked on Cart");
				WebElement continueBtn = driver.findElement(
						By.xpath("//button[@class='btn btn-brand-gradient rounded-pill px-5 ml-3 custom-btn-lg']"));
				System.out.println(
						"Continue Button Found: " + continueBtn.isDisplayed() + " and " + continueBtn.isEnabled());
				if (continueBtn.isDisplayed() || continueBtn.isEnabled()) {
					continueBtn.click();
					Thread.sleep(3000);
					System.out.println("Clicked on Continue Button");
				} else {
					System.out.println("Continue Button is not available");
					Assert.fail("Continue Button is not available on the page");
				}

			} else {
				System.out.println("Cart is not available");
				Assert.fail("Cart is not available on the page");
			}

		} catch (NoSuchElementException e) {
			System.out.println("Cart not found: " + e.getMessage());
			Assert.fail("Cart not found");
		}
	}

	public void proceedButton() throws InterruptedException {
		try {
			WebElement proceedBtn = driver.findElement(By.xpath("//button[@class='btn  btn-brand-gradient ml-3 px-5 rounded-pill custom-btn-lg']"));
			System.out.println("Proceed Button Found");
			if (proceedBtn.isDisplayed() && proceedBtn.isEnabled()) {
				proceedBtn.click();
				Thread.sleep(3000);
				System.out.println("Clicked on Proceed Button");
			} else {
				System.out.println("Proceed Button is not available");
				Assert.fail("Proceed Button is not available on the page");
			}
			
		} catch (NoSuchElementException e) {
			System.out.println("No alert found: " + e.getMessage());
		}
	}
	
	
	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
			System.out.println("Browser closed successfully");
		} else {
			System.out.println("Driver is null, cannot close browser");
		}
	}

}