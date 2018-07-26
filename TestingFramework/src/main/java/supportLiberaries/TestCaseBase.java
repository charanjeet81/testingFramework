package supportLiberaries;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

public class TestCaseBase  
{
	protected String environment;
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
	
	@BeforeSuite
	public void reportInitializations() 
	{
//		extentReport = new ExtentReporterNG();
//		extentReport.getReporter();
	}
	
	@BeforeTest
	public void initializations(ITestContext ctx) 
	{
//		String module = ctx.getCurrentXmlTest().getParameter("classes");
//		extentReport.test = extentReport.startTest("FREE_CRM:TC_01_Login_FreeCRM");
//		extentReport.test = extentReport.startTest(module + " : " + ctx.getCurrentXmlTest().getParameter("testname"));
	}
	
	@BeforeMethod
	@Parameters("browser")
	public void setScriptHelper(@Optional String strBrowser, ITestContext ctx)
	{
		//Setting-up testcase name.
		canonicalName = this.getClass().getCanonicalName().split("testScripts.")[1];
		
		//Setting-up Properties file.
		properties = new Properties();
		try {
			properties.load(new FileInputStream("GlobalSettings.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Setting-up Environment.
		environment = properties.getProperty("Environment");
		
		//Setting-up DataTables.
		dataTable = new DataTable(environment, canonicalName);
		
		// For TestNG parallel execution.
		propBrowser = properties.getProperty("Browser");
		if(strBrowser==null)
		{
			browser = dataTable.getData("Browser");
			if(!browser.isEmpty())
			{
				properties.setProperty("Browser", browser);
			}
		}
		else
		{
			browser = strBrowser;
			properties.setProperty("Browser", browser);
		}
		
		//Setting-up Execution Mode.
		switch (properties.getProperty("ExecutionMode")) 
		{
			case "Local": 
				driver = getDriver(browser);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				
				
				break;
				
			case "Grid":
				
				break;
				
			case "Docker":
		
				break;
	
			default: System.err.println("Execution Mode is other than; Local, Grid and Docker.");
			reporting.updateReport("SetScriptHelper", "Execution Mode is other than; Local, Grid and Docker.", Status.FAIL);
				break;
		}
		// ====================
		startTimeTC =  DateFormat.getDateTimeInstance().format(new Date());
		strStartTime = startTimeTC.split(" ")[3];
		
		reporting = new Reporting(driver, properties, canonicalName);
		scriptHelper = new ScriptHelper(environment, driver, properties, dataTable, reporting);
		scriptHelper.settingStaticDetails(ctx);
		driverScript = new DriverScript(scriptHelper);
	}

	@SuppressWarnings("deprecation")
	@AfterMethod
	public void testMethodTearDown(ITestResult result)
	{
		endTimeTC =  DateFormat.getDateTimeInstance().format(new Date());
		strEndTime = endTimeTC.split(" ")[3];
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try 
		{
			timeTaken = String.valueOf(DurationFormatUtils.formatDuration(format.parse(strEndTime).getTime() - format.parse(strStartTime).getTime(), "HH:mm:ss"));
		} 
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		} 
		times = new Reporting(startTimeTC, timeTaken);
		String completeHTMLReport = reporting.completeHTMLReport(times, result);
		File finalReport = new File(reporting.currentTCReport.getPath()+"\\Report.html");
		try
		{
			FileUtils.writeStringToFile(finalReport, completeHTMLReport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		properties.setProperty("Browser", propBrowser);
		
		driver.quit();
	}
	
	@AfterTest
	public void wrapUp()
	{ 
//		extentReport.extent.endTest(extentReport.getTest());
	}
	
	
	@AfterSuite
	public void windUp()
	{
//		extentReport.getReporter().flush();
//		ITestResult  result = Reporter.getCurrentTestResult();
//		result.getStatus();
	}
	
	public void setDescription(String description)
	{
		reporting.tcDescription = description;
	}
	
	public void setIteration(TestIteration testIteration)
	{
		
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
//			String[] switches = { "--ignore-certificate-errors","disable-popup-blocking",};
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
}
