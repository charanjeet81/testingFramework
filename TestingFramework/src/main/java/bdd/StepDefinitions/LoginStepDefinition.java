package bdd.StepDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
	public static WebDriver driver;
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
		//driverScript = new DriverScript(scriptHelper);
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

	@Given("^user is already on Login Page$")
	public void user_is_already_on_Login_Page() 
	{
		System.out.println("user_is_already_on_Login_Page");
	}

	@When("^title of login page is Free CRM$")
	public void title_of_login_page_is_Free_CRM() 
	{
		System.out.println("title_of_login_page_is_Free_CRM");
	}
}
