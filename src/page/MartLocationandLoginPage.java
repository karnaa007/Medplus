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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseTest;
import io.netty.util.internal.chmv8.ConcurrentHashMapV8.Action;

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
				Thread.sleep(2000);
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
						Assert.assertEquals(userName, "KB", "User name does not match");
						System.out.println("loginLink Pass " + userName);
					} catch (AssertionError e) {
						System.out.println("login Link Fail " + e.getMessage());
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
			// Wait for the mini cart to be present
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[@class='d-inline-block icon-hover p-0 dropdown']")));
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
				Thread.sleep(5000);
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

			// Wait for the select patient radio button to be present
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label/div/h6/p")));
			WebElement selectPatientRadioBtn = driver.findElement(By.xpath("//label/div/h6/p"));
			System.out.println("selectPatientRadioBtn Found: " + selectPatientRadioBtn.isSelected() + " and "
					+ selectPatientRadioBtn.isEnabled());
			if (selectPatientRadioBtn.isSelected() || selectPatientRadioBtn.isEnabled()) {

				Actions action = new Actions(driver);
				action.moveToElement(driver.findElement(By.xpath("//label/div/h6/p"))).click().perform();
				System.out.println("select PatientRadioBtn is already selected");
				Thread.sleep(2000);
				WebElement saveAndContinueBtn = driver.findElement(
						By.xpath("//button[@class='btn btn-brand-gradient rounded-pill px-5 ml-3 custom-btn-lg']"));
				if (saveAndContinueBtn.isDisplayed() && saveAndContinueBtn.isEnabled()) {
					saveAndContinueBtn.click();
					System.out.println("Clicked on continue Button");
					// wait for the proceed button to be present
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"//button[@class=\"btn  btn-brand-gradient ml-3 px-5 rounded-pill custom-btn-lg\"]")));
					WebElement proceedBtn = driver.findElement(By.xpath(
							"//button[@class=\"btn  btn-brand-gradient ml-3 px-5 rounded-pill custom-btn-lg\"]"));
					System.out.println(
							"Proceed Button Found: " + proceedBtn.isDisplayed() + " and " + proceedBtn.isEnabled());
					if (proceedBtn.isDisplayed() || proceedBtn.isEnabled()) {
						proceedBtn.click();
						Thread.sleep(3000);
						System.out.println("Clicked on Proceed Button");
					} else {
						System.out.println("Proceed Button is not available");
						Assert.fail("Proceed Button is not available on the page");
					}
				} else {
					System.out.println("Proceed Button is not available");
					Assert.fail("Proceed Button is not available on the page");
				}

			} else {
				System.out.println("selectPatientRadioBtn is not available");
				Assert.fail("selectPatientRadioBtn is not available on the page");
			}

		} catch (NoSuchElementException e) {
			System.out.println("Cart not found: " + e.getMessage());
			Assert.fail("selectPatientRadioBtn not found");
		}
	}

	public void prescriptionDetails() throws InterruptedException {
		try {
			System.out.println("Prescription Details Method");
			// Wait for the prescription details to be present
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'store-pickup')]")));
			WebElement prescriptionDetails = driver.findElement(By.xpath("//div[contains(@class, 'store-pickup')]"));
			System.out.println("Prescription Details Found: " + prescriptionDetails.isDisplayed() + " and "
					+ prescriptionDetails.getText());
			if (prescriptionDetails.isDisplayed()
					|| prescriptionDetails.getText().contains("I'll show\n" + "Prescription at store")) {
				System.out.println("Prescription Details is available");
				// Click on the prescription details
				// Assuming the prescription details are clickable
				// Wait for the select button to be present
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@id='app']/div/main/div/div/section/div[2]/div[2]/div/button")));
				WebElement selectBtn = driver
						.findElement(By.xpath("//div[@id='app']/div/main/div/div/section/div[2]/div[2]/div/button"));
				System.out.println("Select Button Found: " + selectBtn.isDisplayed() + " and " + selectBtn.isEnabled());
				if (selectBtn.isDisplayed() || selectBtn.isEnabled()) {
					selectBtn.click();
					Thread.sleep(3000);
					System.out.println("Clicked on Select Button");
					// wait for the prescription details proceed button to be present
					wait.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath("//button[@class='btn btn-brand-gradient px-5 ml-3 rounded-pill custom-btn-lg']")));
					WebElement prescriptionDetailsProceedBtn = driver.findElement(By
							.xpath("//button[@class='btn btn-brand-gradient px-5 ml-3 rounded-pill custom-btn-lg'] "));
					System.out.println(
							"Prescription Details Proceed Button Found: " + prescriptionDetailsProceedBtn.isDisplayed()
									+ " and " + prescriptionDetailsProceedBtn.isEnabled());
					if (prescriptionDetailsProceedBtn.isDisplayed() || prescriptionDetailsProceedBtn.isEnabled()) {
						prescriptionDetailsProceedBtn.click();
						Thread.sleep(3000);
						System.out.println("Clicked on Prescription Details Proceed Button");
					} else {
						System.out.println("Prescription Details Proceed Button is not available");
						Assert.fail("Prescription Details Proceed Button is not available on the page");
					}

				} else {
					System.out.println("Select Button is not available");
					Assert.fail("Select Button is not available on the page");
				}

			} else {
				System.out.println("Prescription Details is not available");
				Assert.fail("Prescription Details is not available on the page");
			}

		} catch (NoSuchElementException e) {
			System.out.println("prescriptionDetails option not found: " + e.getMessage());
		}
	}

	public void deliveryDetails() throws InterruptedException {
		try {
			System.out.println("Delivery Details Method");
			// Wait for the delivery details to be present
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".address-outline:nth-child(1)")));
			WebElement deliveryDetails = driver.findElement(By.cssSelector(".address-outline:nth-child(1)"));
			System.out.println(
					"Delivery Details Found: " + deliveryDetails.isDisplayed() + " and " + deliveryDetails.getText());
			if (deliveryDetails.isDisplayed() || deliveryDetails.getText().contains("Home Delivery")) {
				System.out.println("Delivery Details is available");
				// Click on the delivery details
				deliveryDetails.click();
				System.out.println("Clicked on Delivery Details");
				// Wait for the delivery details proceed button to be present
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"//button[contains(@class, 'btn-brand-gradient') and contains(@class, 'custom-btn-lg')]")));
				WebElement deliveryDetailsProceedBtn = driver.findElement(By.xpath(
						"//button[contains(@class, 'btn-brand-gradient') and contains(@class, 'custom-btn-lg')]"));
				System.out.println("Delivery Details Proceed Button Found: " + deliveryDetailsProceedBtn.isDisplayed()
						+ " and " + deliveryDetailsProceedBtn.isEnabled());
				if (deliveryDetailsProceedBtn.isDisplayed() || deliveryDetailsProceedBtn.isEnabled()) {
					deliveryDetailsProceedBtn.click();
					Thread.sleep(3000);
					System.out.println("Clicked on Delivery Details Proceed Button");
				} else {
					System.out.println("Delivery Details Proceed Button is not available");
					Assert.fail("Delivery Details Proceed Button is not available on the page");
				}
			} else {
				System.out.println("Delivery Details is not available");
				Assert.fail("Delivery Details is not available on the page");
			}
		} catch (NoSuchElementException e) {
			System.out.println("No alert found: " + e.getMessage());
		}
	}

	public void promotionsAndReview() throws InterruptedException {
		try {
			System.out.println("Promotions and Review Method");
			// Wait for the promotions and review to be present
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//button[contains(@class, 'btn-brand-gradient') and contains(@class, 'custom-btn-lg')]")));
			// wait for the promotions and review button to be present
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//button[contains(@class, 'btn-brand-gradient') and contains(@class, 'custom-btn-lg')]")));
			WebElement promotionsAndReviewProceedBtn = driver.findElement(
					By.xpath("//button[contains(@class, 'btn-brand-gradient') and contains(@class, 'custom-btn-lg')]"));
			System.out.println("Promotions and Review Found: " + promotionsAndReviewProceedBtn.isDisplayed() + " and "
					+ promotionsAndReviewProceedBtn.getText());
			if (promotionsAndReviewProceedBtn.isDisplayed()
					|| promotionsAndReviewProceedBtn.getText().contains("Apply Promo Code")) {
				System.out.println("Promotions and Review is available");
				// Click on the promotions and review
				promotionsAndReviewProceedBtn.click();
				Thread.sleep(3000);
				System.out.println("Clicked on Promotions and Review");
			} else {
				System.out.println("Promotions and Review is not available");
				Assert.fail("Promotions and Review is not available on the page");
			}
		} catch (NoSuchElementException e) {
			System.out.println("No alert found: " + e.getMessage());
		}
	}

	public void paymentDetails() throws InterruptedException {
		System.out.println("Payment Details Method");
		try {
			// Wait for the payment details to be present
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"//div[contains(@class, 'select-payment-container') and contains(@class, 'cash-on-delivery-select')]")));
			WebElement paymentDetails = driver.findElement(By.xpath(
					"//div[contains(@class, 'select-payment-container') and contains(@class, 'cash-on-delivery-select')]"));
			System.out.println(
					"Payment Details Found: " + paymentDetails.isDisplayed() + " and " + paymentDetails.getText());
			if (paymentDetails.isDisplayed() || paymentDetails.getText().contains("Cash on Delivery")) {
				paymentDetails.click();
				System.out.println("Clicked on Payment Details");
			} else {
				System.out.println("Payment Details is not available");
				Assert.fail("Payment Details is not available on the page");
			}
			// Wait for the payment details proceed button to be present
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//button[contains(@class, 'btn-brand-gradient') and contains(@class, 'btn-block')]")));
			WebElement placeTheOrderBtn = driver.findElement(
					By.xpath("//button[contains(@class, 'btn-brand-gradient') and contains(@class, 'btn-block')]"));
			System.out.println("Payment Details Proceed Button Found: " + placeTheOrderBtn.isDisplayed() + " and "
					+ placeTheOrderBtn.isEnabled());
			if (placeTheOrderBtn.isDisplayed() || placeTheOrderBtn.isEnabled()) {
				// placeTheOrderBtn.click();
				Thread.sleep(3000);
				System.out.println("Clicked on Payment Details Proceed Button");
			} else {
				System.out.println("Payment Details Proceed Button is not available");
				Assert.fail("Payment Details Proceed Button is not available on the page");
			}
		} catch (NoSuchElementException e) {
			System.out.println("No alert found: " + e.getMessage());
		}
	}
	
	public static void netPayment() throws InterruptedException {
		try {
			System.out.println("Net Payment Method" + driver.getTitle());
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='app']/div/main/div/div/section/div[2]/ul/li[3]/div/div/button")));
			WebElement netPaymentBtn = driver.findElement(By.xpath("//div[@id='app']/div/main/div/div/section/div[2]/ul/li[3]/div/div/button"));
			Actions actions = new Actions(driver);
			actions.moveToElement(netPaymentBtn).perform();
			System.out.println("Net Payment Button Found: " + netPaymentBtn.isDisplayed() + " and " + netPaymentBtn.isEnabled());
			if (netPaymentBtn.isDisplayed() || netPaymentBtn.isEnabled()) {
				netPaymentBtn.click();
				Thread.sleep(3000);
				System.out.println("Clicked on Net Payment Button");
			} else {
				System.out.println("Net Payment Button is not available");
				Assert.fail("Net Payment Button is not available on the page");
			}
			// Wait for the SBI Bank Payment option to be present
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("SBIB")));
			WebElement sbiBankPayment = driver.findElement(By.id("SBIB"));
			System.out.println("SBI Bank Payment Option Found 1: " + sbiBankPayment.isDisplayed() + " and " + sbiBankPayment.getText());
			Thread.sleep(2000);
			Actions action = new Actions(driver);
			action.moveToElement(sbiBankPayment).click().perform();
			System.out.println("SBI Bank Payment Option Found 2: " + sbiBankPayment.isDisplayed() + " and " + sbiBankPayment.getText());
		if (sbiBankPayment.isDisplayed() || sbiBankPayment.getText().contains("SBI Bank")) {
			sbiBankPayment.click();
			Thread.sleep(3000);
			System.out.println("Clicked on SBI Bank Payment Option");
		} else {
			System.out.println("SBI Bank Payment Option is not available");
			Assert.fail("SBI Bank Payment Option is not available on the page");
			
		}
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='app']/div/main/div/div/section/div/div[4]/button[2]")));
			WebElement paymentBtn = driver.findElement(By.xpath("//div[@id='app']/div/main/div/div/section/div/div[4]/button[2]"));
			System.out.println("Place Order Button Found: " + paymentBtn.isDisplayed() + " and " + paymentBtn.isEnabled());
			if (paymentBtn.isDisplayed() || paymentBtn.isEnabled()) {
				paymentBtn.click();
				Thread.sleep(3000);
				System.out.println("Clicked on Place Order Button");
			} else {
				System.out.println("Place Order Button is not available");
				Assert.fail("Place Order Button is not available on the page");
			}
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='btn btnd']")));
			WebElement successBtn = driver.findElement(By.xpath("//button[@class='btn btnd']"));
			System.out.println("Order Placed Success Message Found: " + successBtn.isDisplayed() + " and " + successBtn.getText());
			Assert.assertTrue(successBtn.isDisplayed(), "Order placed Success message is not displayed");
			System.out.println("Order placed successfully");
			successBtn.click();
			Thread.sleep(3000);
			System.out.println("Clicked on Success Button");
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='toggle-arrow ']")));
			WebElement toggleArrow = driver.findElement(By.xpath("//a[@class='toggle-arrow ']"));
			System.out.println("OrderId Found: " + toggleArrow.isDisplayed() + " and " + toggleArrow.getText());
			Assert.assertTrue(toggleArrow.isDisplayed(), "OrderId is not displayed");
			System.out.println("OrderId is displayed: " + toggleArrow.getText());
			
		} catch (NoSuchElementException e) {
			System.out.println("SBI Bank Payment Option not found: " + e.getMessage());
			Assert.fail("Order placed message not found");
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