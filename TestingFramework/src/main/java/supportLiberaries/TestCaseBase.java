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
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.openqa.selenium.Platform;
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
import org.openqa.selenium.remote.RemoteWebDriver;
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

import com.zapi.base.ZapiBase;

public class TestCaseBase  
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
	
	@BeforeSuite
	public void reportInitializations() 
	{
	}
	
	@BeforeTest
	public void initializations(ITestContext ctx) 
	{
		deleteFile("target\\surefire-reports\\CompleteExecution.html");
		deleteFile("target\\surefire-reports\\testng-results.xml");
	}
	
	@BeforeMethod
	@Parameters("browser")
	public void setScriptHelper(@Optional String strBrowser, ITestContext ctx)
	{
		if(allIterations)
		{ }
		else
		{
			invocationCount = 1;
		}
	
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
		env = System.getProperty("environment");

		if(env==null)
		{
			environment = properties.getProperty("Environment");
		}
		else
		{
			properties.setProperty("Environment", env);
			environment = properties.getProperty("Environment");
		}
		
		//Setting-up DataTables.
		dataTable = new DataTable(environment, canonicalName, invocationCount);
		invocationCount++;
		
		// For TestNG parallel execution.
		propBrowser = properties.getProperty("Browser");
		if(strBrowser==null) // strBrowser from testNG file
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
		switch (properties.getProperty("Execution_Mode")) 
		{
			case "Local": 
				driver = getDriver(browser);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				break;
				
			case "Grid":
				String hubURL = properties.getProperty("HUB_URL");
				driver = getDriver(browser, hubURL);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				break;
				
			case "Docker":
		
				break;
	
			default: System.err.println("Execution Mode is other than; Local, Grid and Docker.");
			reporting.updateReport("SetScriptHelper", "Execution Mode is other than; Local, Grid and Docker.", Status.FAIL);
				break;
		}

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
		
		// JIRA Update if true.
		if(JIRAUpdate())
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
		}
		
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
	
	@AfterTest
	public void wrapUp()
	{ 
		
	}
	
	@AfterSuite
	public void windUp()
	{
	}
	
	public void setDescription(String description)
	{
		reporting.tcDescription = description;
	}
	
	public void runAllIteration(boolean allIterations)
	{
		this.allIterations = allIterations;
	}
	
	// For Local Execution
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
	
	// For Grid Execution.
	public WebDriver getDriver(String browser, String hubURL)
	{
	   DesiredCapabilities desireCapabilities;
		
	   if(browser==null || browser.isEmpty())
		{
			this.browser = properties.getProperty("Browser");
		}
		
		if(this.browser.equalsIgnoreCase("chrome"))
		{
			String[] switches = { "--ignore-certificate-errors","disable-popup-blocking",};
			desireCapabilities = DesiredCapabilities.chrome();
			desireCapabilities.setBrowserName("chrome");
			desireCapabilities.setPlatform(Platform.WINDOWS);
			ChromeOptions options = new ChromeOptions(); 
			options.addArguments("--start-maximized"); 
			options.addArguments("no-sandbox");
			options.addArguments("test-type");
			options.addArguments("disable-popup-blocking");
			options.addArguments("chrome.switches","--disable-extensions");
			options.addArguments("disable-infobars");
			desireCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			desireCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			desireCapabilities.setCapability("chrome.switches", Arrays.asList(switches));
			desireCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			options.merge(desireCapabilities);
		}
		else if(this.browser.equalsIgnoreCase("InternetExplorer"))
		{
			desireCapabilities =  DesiredCapabilities.internetExplorer();
			desireCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); 
			desireCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			desireCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); 
			desireCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			desireCapabilities.setJavascriptEnabled(true); 
			desireCapabilities.setCapability("requireWindowFocus", true);
			desireCapabilities.setCapability("ignoreProtectedModeSettings", true);
			desireCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			desireCapabilities.setCapability("nativeEvents", false);
			desireCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
			desireCapabilities.setCapability("disable-popup-blocking", true);
			desireCapabilities.setCapability("enablePersistentHover", true);
		}
		else if(this.browser.equalsIgnoreCase("firefox"))
		{
			desireCapabilities =  DesiredCapabilities.firefox();
//			desireCapabilities.setCapability("marionette", false);
//			desireCapabilities.setBrowserName("firefox");
//			desireCapabilities.setPlatform(Platform.WINDOWS);
//			//desireCapabilities.setVersion("ANY");
//			//desireCapabilities.acceptInsecureCerts();
//			FirefoxOptions opt = new FirefoxOptions();
//			opt.merge(desireCapabilities);
		}
		else
		{
			desireCapabilities = DesiredCapabilities.firefox();
		}
		try {
			url = new URL(hubURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver = new RemoteWebDriver(url, desireCapabilities);
		return driver;
	}
	
	public void zapiUpdate(String logstatus) throws Exception
	{
		String status = null;
		String comment = null;
		String currentTest = canonicalName.replace(".", "#").split("#")[1]; 
		if (JIRAUpdate()) 
		{
			if (logstatus.contains("Passed")) 
			{
				status = JIRAStatus.PASS.getValue();
			} 
			else if (logstatus.contains("Failed"))
			{
				status = JIRAStatus.FAIL.getValue();
			}
			String issueKey = dataTable.getData("IssueKey");
			String versionName = dataTable.getData("VersionName");
			String cycleName = dataTable.getData("CycleName");
			
			ZapiBase.Cycle_Name = cycleName;
			ZapiBase.Issue_Key = issueKey;
			
			ZapiBase zapi = new ZapiBase(currentTest);
			//reporting.updateReport("ZapiUpdate", "Test Status update for, IssueKey: "+issueKey+", VersionName: "+versionName+" and CycleName: "+cycleName, Status.DONE);
			Map<String, Map<String, String>> cycleList = zapi.getListOfCycles(versionName);
			System.out.println("cycleList "+cycleList);
			Map<String, String> testList = zapi.getTestList(cycleList, cycleName);
			System.out.println("testList "+testList);

			if (status.equals("1")) 
			{
				comment = currentTest + ", is Passed and updated using Automation.";
			} 
			else if (status.equals("2"))
			{
				comment = currentTest + ", is Failed and updated using Automation.";
			}
			zapi.cloneExecution(versionName, issueKey, comment, testList, cycleList, status);
			zapi.addAttachment(testList, cycleList, reporting.currentTCReport.getPath()+".zip", issueKey);
		}
	}
	
	public enum JIRAStatus 
	{
		PASS("1"), FAIL("2"), WIP("3"), BLOCKED("4"), UNEXECUTED("5");
		private final String value;
		private JIRAStatus(final String value) {
			this.value = value;
		}
		public String getValue() 
		{
			return value;
		}
	}
	
	public boolean JIRAUpdate()
	{
		return Boolean.parseBoolean(properties.getProperty("JIRA_Update"));
	}
	
	public void deleteFile(String filePath)
	{
		File fileToDelete = new File(System.getProperty("user.dir")+"\\"+filePath);
		if(fileToDelete.exists())
		{
			fileToDelete.delete();
		}
		else
		{
			System.err.println("File does not exists to delete.");
		}
	}
}
