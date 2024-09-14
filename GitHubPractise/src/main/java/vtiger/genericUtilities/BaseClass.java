package vtiger.genericUtilities;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import vtiger.ObjectRepository.HomePage;
import vtiger.ObjectRepository.LoginPage;

public class BaseClass {

	PropertyFileUtility putil = new PropertyFileUtility();
	ExcelFileUtility eutil = new ExcelFileUtility();
	WebDriverUtility wutil = new WebDriverUtility();
	public WebDriver driver = null;
	public static WebDriver sDriver;

	@BeforeSuite(groups = { "smoke", "Regression" })
	public void beforeSuiteConfig() {
		System.out.println("--DataBase connection established-----");
	}
	
	/*@Parameters("browserName")
	@BeforeTest*/
	
	
	@BeforeClass(groups = { "smoke", "Regression" })
	public void beforeClassConfig(/*String BROWSER*/) throws IOException {
		
		String BROWSER = putil.toReadDataFromPropertyFile("browser");
		String URL = putil.toReadDataFromPropertyFile("url");
		if (BROWSER.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (BROWSER.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		sDriver=driver;
		System.out.println("Bowser got launched");
		wutil.toMaximize(driver);
		wutil.toWaitForElements(driver);
		driver.get(URL);

	}

	@BeforeMethod(groups = { "smoke", "Regression" })
	public void beforeMethodConfig() throws IOException {
		String USERNAME = putil.toReadDataFromPropertyFile("username");
		String PASSWORD = putil.toReadDataFromPropertyFile("password");
		LoginPage lp = new LoginPage(driver);
		lp.getUsernameTextField().sendKeys(USERNAME);
		lp.getPasswordTextField().sendKeys(PASSWORD);
		lp.getLoginButton().click();

	}

	@AfterMethod(groups = { "smoke", "Regression" })
	public void afterMethodConfig() {
		HomePage hp = new HomePage(driver);
		wutil.toMouseHover(driver, hp.getLogoutLink());
		hp.getSignOutLink().click();
	}

	@AfterClass(groups = { "smoke", "Regression" })
	public void afterClassConfig() {
		System.out.println("Browser got closed");
		driver.quit();
	}

	@AfterSuite(groups = { "smoke", "Regression" })
	public void afterSuiteConfig() {
		System.out.println("Database got disconnected");
	}

	
}
