package applicationPages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import supportLiberaries.SUPER_Page;
import supportLiberaries.ScriptHelper;
import supportLiberaries.Status;
import supportLiberaries.WaitTools;

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
	
	@FindBy(partialLinkText = "log in")
	WebElement login_BTN;
	
	//::::::::::::::::: M E T H O D S ::::::::::::::::://
	
	public Login_Page invokeApplication() throws IOException
	{
		driver.get(properties.getProperty("Application_URL"));
//		Reporting("Application invoked as: "+properties.getProperty("Application_URL"), Status.DONE);
//		Reporter.addStepLog("Application invoked as: "+properties.getProperty("Application_URL"));
//		Reporter.addScreenCaptureFromPath(screenshot());
		Reporter.addScreenCaptureFromPath(screenshot());
		LogReport("Application opened DONE.", Status.DONE);
		LogReport("Application opened PASS.", Status.PASS);
		LogReport("Application opened SCREENSHOT.", Status.SCREENSHOT);
		
//		LogReport("Application opened FAIL.", Status.FAIL);
		return this;
	}
	
	public Home_Page login_To_Application()
	{
		TakeScreenshot();
		String userName = dataTable.getData("UserName");
		WaitTools.waitForElementDisplayed(driver, username, 21);
		username.sendKeys(userName);
		LogReport("Username set as: "+userName, Status.DONE);
		//Reporting("Username set as: "+userName, Status.DONE);
		
		String strPassword = dataTable.getData("Password");
		password.sendKeys(strPassword);
		LogReport("Password set as: "+strPassword, Status.DONE);
		//Reporting("Password set as: "+strPassword, Status.DONE);
		
		sync(1);
		clickElementUsing_JSE(logIn_BTN, "Log In button");
		TakeScreenshot();
		return new Home_Page(scriptHelper);
	}
	
	public Home_Page login_To_Application(String user, String pwd)
	{
		TakeScreenshot();
		String userName = user;
		WaitTools.waitForElementDisplayed(driver, username, 21);
		username.sendKeys(userName);
		LogReport("Username set as: "+userName, Status.DONE);
		//Reporting("Username set as: "+userName, Status.DONE);
		
		String strPassword = pwd;
		password.sendKeys(strPassword);
		LogReport("Password set as: "+strPassword, Status.DONE);
		//Reporting("Password set as: "+strPassword, Status.DONE);
		
		sync(1);
		clickElementUsing_JSE(logIn_BTN, "Log In button");
		TakeScreenshot();
		return new Home_Page(scriptHelper);
	}

}
