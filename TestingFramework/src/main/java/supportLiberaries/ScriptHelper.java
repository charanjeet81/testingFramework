package supportLiberaries;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class ScriptHelper 
{
	protected WebDriver driver;
	protected String environment;
	protected Properties properties;
	protected DataTable dataTable;
	protected Reporting reporting;
	
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
}
