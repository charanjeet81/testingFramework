package bdd.StepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import applicationPages.Home_Page;
import applicationPages.Login_Page;
import bdd.TestRunner.TestRunner_SMOKE;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import supportLiberaries.DataTable;
import supportLiberaries.DriverScript;
import supportLiberaries.ExtentReporterNG;
import supportLiberaries.Reporting;
import supportLiberaries.SUPER_Page;
import supportLiberaries.ScriptHelper;
import supportLiberaries.Status;

public class LoginStepDefinition
{

	protected String environment;
	protected String env;
	protected WebDriver driver;
	protected DriverScript driverScript;
	protected ScriptHelper scriptHelper;
	public String startTimeTC, endTimeTC;
	protected String strStartTime, strEndTime;
	protected Properties properties; 
	protected DataTable dataTable;
	protected Reporting reporting;
	protected String canonicalName;
	public String timeTaken; 
	public Reporting times;
	public String browser;
	public String propBrowser;
	ExtentReporterNG extentReport;
	static int invocationCount = 1;
	protected boolean allIterations = false;
	URL url;
	
	Login_Page lp;
	Home_Page hp;
	
	@Before
	public void init(Scenario scn)
	{
		invocationCount = 1;
		canonicalName = scn.getId().split(";")[0].replace("-", "_").toUpperCase();
		canonicalName = canonicalName+"."+scn.getName();
		
		properties = new Properties();
		try {
			properties.load(new FileInputStream("GlobalSettings.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Setting-up Environment.
		environment = properties.getProperty("Environment");
		dataTable = new DataTable(environment, canonicalName, invocationCount);
		
		// For TestNG parallel execution.
		propBrowser = properties.getProperty("Browser");
		browser = dataTable.getData("Browser");
		
		//Setting-up Execution Mode.
		switch (properties.getProperty("Execution_Mode")) 
		{
			case "Local": 
				driver = getDriver(browser);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				break;
	
			default: System.err.println("Execution Mode is other than; Local, Grid and Docker.");
			reporting.updateReport("SetScriptHelper", "Execution Mode is other than; Local, Grid and Docker.", Status.FAIL);
				break;
		}

		startTimeTC =  DateFormat.getDateTimeInstance().format(new Date());
		strStartTime = startTimeTC.split(" ")[3];
		
		reporting = new Reporting(driver, properties, canonicalName);
		scriptHelper = new ScriptHelper(environment, driver, properties, dataTable, reporting);
		//scriptHelper.settingStaticDetails(ctx);
		driverScript = new DriverScript(scriptHelper);
	}
	
	@After
	public void testMethodTearDown() 
	{
//		ITestResult result = ITestResult.class.newInstance();
//		endTimeTC =  DateFormat.getDateTimeInstance().format(new Date());
//		strEndTime = endTimeTC.split(" ")[3];
//		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//		try 
//		{
//			timeTaken = String.valueOf(DurationFormatUtils.formatDuration(format.parse(strEndTime).getTime() - format.parse(strStartTime).getTime(), "HH:mm:ss"));
//		} 
//		catch (ParseException e1) 
//		{
//			e1.printStackTrace();
//		} 
//		times = new Reporting(startTimeTC, timeTaken);
//		String completeHTMLReport = reporting.completeHTMLReport(times, result);
//		
//		File finalReport = new File(reporting.currentTCReport.getPath()+"\\Report.html");
//		try
//		{
//			FileUtils.writeStringToFile(finalReport, completeHTMLReport);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		properties.setProperty("Browser", propBrowser);
		
		// JIRA Update if true.
		/*if(JIRAUpdate())
		{
			SUPER_Page superPage = new SUPER_Page(scriptHelper);
			superPage.zipReport(reporting.currentTCReport.getPath(), reporting.currentTCReport.getPath()+".zip");
			try 
			{
				zapiUpdate(reporting.tcStatus);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}*/
		
		// Quitting Browser.
		if (browser.contains("InternetExplorer")) 
		{
			try {
				Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
	}

	@Given("^user is already on Login Page$")
	public void user_already_on_login_page() throws IOException 
	{
		lp = new Login_Page(scriptHelper);
		lp.invokeApplication();
//		driver.get("https://www.freecrm.com/index.html");
//		driver.manage().window().maximize();
	}

	@When("^title of login page is Free CRM$")
	public void title_of_login_page_is_free_CRM()
	{
		//hp = lp.login_To_Application();
//		String title = driver.getTitle();
//		System.out.println(title);
//		Assert.assertEquals("#1 Free CRM for Any Business: Online Customer Relationship Software", title);
	}

	// Reg Exp:
	// 1. \"([^\"]*)\"
	// 2. \"(.*)\"

	@Then("^user enters \"(.*)\" and \"(.*)\"$")
	public void user_enters_username_and_password(String username, String password) 
	{
		System.out.println(username+" - "+password);
		hp = lp.login_To_Application(username, password);
		//driver.findElement(By.name("username")).sendKeys(username);
		//driver.findElement(By.name("password")).sendKeys(password);
	}

	@Then("^user clicks on login button$")
	public void user_clicks_on_login_button() {
		WebElement loginBtn = driver.findElement(By.xpath("//input[@type='submit']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", loginBtn);
	}

	@Then("^user is on home page$")
	public void user_is_on_hopme_page() {
		String title = driver.getTitle();
		System.out.println("Home Page title ::" + title);
		// Assert.assertEquals("CRMPRO", title);
	}

	@Then("^user moves to new contact page$")
	public void user_moves_to_new_contact_page() {
		driver.switchTo().frame("mainpanel");
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"))).build().perform();
		driver.findElement(By.xpath("//a[contains(text(),'New Contact')]")).click();
	}

	@Then("^user enters contact details \"(.*)\" and \"(.*)\" and \"(.*)\"$")
	public void user_enters_contacts_details(String firstname, String lastname, String position) {
		driver.findElement(By.id("first_name")).sendKeys(firstname);
		driver.findElement(By.id("surname")).sendKeys(lastname);
		driver.findElement(By.id("company_position")).sendKeys(position);
		driver.findElement(By.xpath("//input[@type='submit' and @value='Save']")).click();
	}

	@Then("^Close the browser$")
	public void close_the_browser() {
		driver.quit();
	}
	
	public WebDriver getDriver(String browser)
	{
		if(browser==null || browser.isEmpty())
		{
			this.browser = properties.getProperty("Browser");
		}
		if(this.browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Resources\\Browsers\\chromedriver.exe");
			String[] switches = { "--ignore-certificate-errors","disable-popup-blocking",};
			ChromeOptions options = new ChromeOptions();
			options.addArguments("no-sandbox");
			options.addArguments("test-type");
			options.addArguments("disable-popup-blocking");
			options.addArguments("chrome.switches","--disable-extensions");
			options.addArguments("disable-infobars");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(options);
		}
		else if(this.browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\Resources\\Browsers\\geckodriver.exe");
//			FirefoxOptions options = new FirefoxOptions();
//			options.setCapability("marionette", false);
			driver = new FirefoxDriver();
		}
		else if(this.browser.equalsIgnoreCase("InternetExplorer"))
		{
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\Resources\\Browsers\\IEDriverServer.exe");
			InternetExplorerOptions ieOptions = new InternetExplorerOptions();
			ieOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		    ieOptions.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
		    ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		    ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		    ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		    ieOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		    ieOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		    ieOptions.setCapability("nativeEvents", false);
			ieOptions.setCapability("ignoreProtectedModeSettings", true);
			ieOptions.setCapability("disable-popup-blocking", true);
			ieOptions.setCapability("enablePersistentHover", true);
			ieOptions.setCapability("RequireWindowFocus", true);
			driver = new InternetExplorerDriver(ieOptions);
		}
		else if(this.browser.equalsIgnoreCase("HTMLUnitDriver"))
		{
			driver = new HtmlUnitDriver();
		}
		else if(this.browser.equalsIgnoreCase("android"))
		{
			//System.setProperty("webdriver.chrome.driver", "D:\\workD\\TestingFramework\\Resources\\Browsers\\chromedriver.exe");
			//driver = new InternetExplorerDriver();
		}
		else if(this.browser.equalsIgnoreCase("iphone"))
		{
			//System.setProperty("webdriver.chrome.driver", "D:\\workD\\TestingFramework\\Resources\\Browsers\\chromedriver.exe");
			//driver = new InternetExplorerDriver();
		}
		return driver;
	}
	
	@Then("^quit the browser$")
	public void quit_the_browser() {
		driver.quit();
	    
	}

}
