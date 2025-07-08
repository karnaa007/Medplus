package page;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class MartLocationandLoginPage extends base.BaseTest {
	WebDriver driver;
	public String mobileNo = "9951994998";
	public static String otp;
	public static WebElement verifyOTP;
	public static WebElement verifyOTPBtn;
	public static WebElement loginLink;
	public static String userName = null;
	public static String productName = null;
	public static String quantityDropdown1 = null;
	public static String quantityDropdown = null;
	WebElement location = null;
	WebElement locateMe = null;
	
	    protected void LocationPage() throws InterruptedException {
		System.out.println("Location Method");
		// Implementation for selecting location
		driver.findElement(By.id("locationTooltip")).click();
		wait(3000);
		locateMe = driver.findElement(By.xpath("//button[@class='btn btn-outline-light btn-sm ml-1 text-brand text-capitalize text-decoration-none ']"));
		locateMe.click();
		Thread.sleep(5000);
		WebElement locationInput = driver.findElement(By.xpath("//input[@class='rbt-input-main form-control rbt-input']"));
		locationInput.sendKeys("madhapur");
		Thread.sleep(2000);
		WebElement locationOption = driver.findElement(By.xpath("//li[@class='no-gutters active']']"));
		if (locationOption.isDisplayed()) {
			locationOption.click();
			System.out.println("Location selected successfully");
		} else {
			System.out.println("Location option not available");
		}
		
	}
	
	void login(String mobileNo) throws InterruptedException {
		System.out.println("Login Method");
		// Implementation for login using mobile number
		System.out.println("Ln Paogige Start");
		loginLink = driver.findElement(By.xpath("//span[@class='user-name']"));
		Assert.assertTrue(loginLink.isDisplayed(), "Ligin link Available");
		loginLink.click();
		Thread.sleep(3000);
		driver.findElement(By.id("mobileNumber")).sendKeys(mobileNo);
		verifyOTP = driver.findElement(By.cssSelector(".btn-brand-gradient:nth-child(2)"));
		verifyOTP.click();
		System.out.println("Clicked on OTP");
		Thread.sleep(5000);

	}
	
	// JDBC method to fetch OTP from MySQL
    static String fetchOTPFromDB(WebDriver driver) {
	String url = "jdbc:mysql://192.168.1.11"; // replace with your DB URL
	String user = "varma"; // replace with your DB user
	String password = "varma321"; // replace with your DB password
	System.out.println("Url,USer,Paswrd" + url + "," + user + "," + password);

	try {
		System.out.println("DB connection Started");
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		String query = "select * from customers.tbl_customer_otp where MobileNo=9951994998 and Vertical='W' order by 1 desc limit 1";
		System.out.println(stmt);
		ResultSet rs = stmt.executeQuery(query);
		System.out.println("Query Executed" + rs);

		if (rs.next()) {
			otp = rs.getString("otp");
			System.out.println(otp);

			WebElement otpInput = driver.findElement(By.xpath("//input[@name='OTP']"));
			Thread.sleep(2000);
			otpInput.sendKeys(otp);
			System.out.println("Clicked on Vrify OTP ");
			Thread.sleep(8000);
			userName = driver.findElement(By.xpath(
					"//span[@class='username mr-2 text-white d-inline-flex align-items-center justify-content-center font-16']"))
					.getText();
			try {
				Assert.assertEquals(userName, "karna");
				System.out.println("loginLink Pass " + userName);
			} catch (AssertionError e) {
				System.out.println("loginLink Fail " + e.getMessage());

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

    static void searchAndAddProduct(WebDriver driver) throws InterruptedException {
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
					System.out.println( " Data From Excel " + cell);
					if (cell != null) {
						productName = cell.toString();
						System.out.println("Product Name is : " + cell.toString() + "\t");
					} else {
						System.out.print("[BLANK]\t");
					}

					
					WebElement passDataInSearchTextBox = driver.findElement(
							By.xpath("//div[@class='homepage-input w-100']"));
					passDataInSearchTextBox.click();
					Thread.sleep(2000);
					
					WebElement passDataInSearchTextBox1 = driver.findElement(
							By.xpath("//input[@class='font-16 form-control form-control-lg text-secondary ']"));
					passDataInSearchTextBox1.sendKeys(productName);
					Thread.sleep(3000);
					
					try {
					WebElement quantityOption = driver
							.findElement(By.xpath("//a[@class='btn btn-sm px-0 text-brand ']"));
				//	System.out.println(" Quantity Option Available  " + quantityOption.isDisplayed()+ "OR" + quantityOption.isEnabled());
				
//					Assert.assertTrue(quantityOption.getSize()> 0), "Quantity Option is not Available");
				    quantityOption.click();
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
    

}
