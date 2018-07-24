package applicationPages;

import org.openqa.selenium.WebDriver;
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
	
	//::::::::::::::::: O B J E C T S  ::::::::::::::::://
	
	@FindBy(name = "username")
	WebElement username;
	@FindBy(name = "password")
	WebElement password;
	@FindBy(css = "input[value='Login']")
	WebElement logIn_BTN;
	
	//::::::::::::::::: M E T H O D S ::::::::::::::::://
	
	public Login_Page invokeApplication()
	{
		driver.get(properties.getProperty("Application_URL"));
		Reporting("Application invoked as: "+properties.getProperty("Application_URL"), Status.DONE);
		return this;
	}
	
	public Home_Page login_To_Application()
	{
		String userName = dataTable.getData("UserName");
		userName = dataTable.getData("User");
		dataTable.setData("Data", "Charanjeet Singh");
		/*username.sendKeys(userName);
		Reporting("Username set as: "+userName, Status.DONE);
		String strPassword = dataTable.getData("Password");
		password.sendKeys(strPassword);
		Reporting("Password set as: "+strPassword, Status.DONE);
		sync(1);
		logIn_BTN.click();
		Reporting("Clicked on 'LogIn' button.", Status.DONE);*/
		return new Home_Page(scriptHelper);
	}
}
