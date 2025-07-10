package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.MartLocationandLoginPage;

public class loacationAndLoginTest extends MartLocationandLoginPage {

	WebDriver driver;

	@BeforeClass
	public void launchBrowser() {
		System.out.println("\n launchBrowser Test");
		setup();
	}

	@Test
	public void locationTest() throws InterruptedException {
		System.out.println("\n locatoin Test");
		locatoin();
		System.out.println("Location Test Completed");
	}

	@Test
	public void loginTest() throws InterruptedException {
		System.out.println("\n login Test");
		login();
		System.out.println("Login Test Completed");
	}

	@Test
	void fetchOTPFromDBTest() {
		System.out.println("\n fetchOTPFromDB Test");
		fetchOTPFromDB();
		otp = fetchOTPFromDB();
		System.out.println("OTP fetched: " + otp);
		// Assert.assertNotNull(otp, "OTP should not be null");
	}

	@Test
	void searchAndAddProductTest() throws InterruptedException {
		System.out.println("\n searchAndAddProductTest Test");
		searchAndAddProduct();
		System.out.println("searchAndAddProductTest Test Completed");
	}
	
	@AfterClass
	public void closeBrowserTest() {
		System.out.println("\n closeBrowser Test");
		closeBrowser();
		System.out.println("Browser closed successfully");
	}
}
