package supportLiberaries;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

public class ScriptHelper 
{
	protected WebDriver driver;
	protected String environment;
	protected Properties properties;
	protected DataTable dataTable;
	protected Reporting reporting;
	protected static String suiteName;
	protected static String projectName;
	protected static String env;
	
	public ScriptHelper(String environment, WebDriver driver, Properties properties, DataTable dataTable, Reporting reporting)
	{
		this.environment = environment;
		this.driver = driver;
		this.properties = properties;
		this.dataTable = dataTable;
		this.reporting = reporting;
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public String getEnvironment() {
		return environment;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public DataTable getDataTable() {
		return dataTable;
	}
	
	public Reporting getReporting() {
		return reporting;
	}
	
	public void settingStaticDetails(ITestContext ctx) 
	{
		  ScriptHelper.suiteName = ctx.getCurrentXmlTest().getSuite().getName();
		  ScriptHelper.env = environment;
		  ScriptHelper.projectName = properties.getProperty("ProjectName");
	}
}
