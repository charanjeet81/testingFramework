package supportLiberaries;

import java.util.Properties;
import org.openqa.selenium.WebDriver;


public class ReusableLiberaries 
{
	protected Properties properties;
	protected WebDriver driver;
	protected String environment;
	protected DataTable dataTable;
	protected Reporting reporting;
	public ScriptHelper scriptHelper;
	
	public ReusableLiberaries(ScriptHelper scriptHelper)
	{
		this.scriptHelper = scriptHelper;
		driver = scriptHelper.getDriver();
		environment = scriptHelper.getEnvironment();
		properties = scriptHelper.getProperties();
		dataTable = scriptHelper.getDataTable();
		reporting = scriptHelper.getReporting();
	}
}
