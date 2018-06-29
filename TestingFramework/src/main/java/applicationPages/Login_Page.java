package applicationPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import supportLiberaries.SUPER_Page;
import supportLiberaries.ScriptHelper;
import supportLiberaries.Status;

public class Login_Page extends SUPER_Page
{
	public Login_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name = "username")
	WebElement username;
	@FindBy(name = "password")
	WebElement password;
	
	public Login_Page invokeApplication()
	{
		driver.get(properties.getProperty("Application_URL"));
		Reporting("Application invoked as: "+properties.getProperty("Application_URL"), Status.DONE);
		return this;
	}
	
	public Home_Page login_To_Application()
	{
		String userName = dataTable.getData("UserName");
		username.sendKeys(userName);
		Reporting("Username set as: "+userName, Status.SCREENSHOT);
		sync(3);
		String strPassword = dataTable.getData("Password");
		password.sendKeys(strPassword);
		Reporting("Password set as: "+strPassword, Status.FAIL);
		Reporting("Username set as: "+userName, Status.PASS);
		Reporting("Username set as: "+userName, Status.FAIL);
		//Reporting("Username set as: "+userName, Status.FAIL);

		return new Home_Page(scriptHelper);
	}
}
