package applicationPages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import supportLiberaries.SUPER_Page;
import supportLiberaries.ScriptHelper;
import supportLiberaries.Status;

public class Twitter_Page extends SUPER_Page
{
	public Twitter_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		PageFactory.initElements(driver, this);
	}
	
	//::::::::::::::::: O B J E C T S  ::::::::::::::::://
	
	@FindBy(xpath = "(//a[contains(text(),'Log in')])[2]")
	WebElement mainLogIn_BTN;
	@FindBy(xpath = "//input[@placeholder='Phone, email or username']")
	WebElement username;
	@FindBy(xpath = "(//input[@placeholder='Password'])[2]")
	WebElement password;
	@FindBy(xpath = "//button[contains(text(),'Log in')]")
	WebElement logIn_BTN;
	@FindBy(css = "input#search-query")
	WebElement searchField;
	
	
	
	//::::::::::::::::: M E T H O D S ::::::::::::::::://
	
	public Twitter_Page invokeApplication()
	{
		driver.get(properties.getProperty("Twitter_URL"));
		Reporting("Application invoked as: "+properties.getProperty("Twitter_URL"), Status.DONE);
		mainLogIn_BTN.click();
		Reporting("Clicked on 'Log In' button.", Status.DONE);
		return this;
	}
	
	public Twitter_Page login_To_Twitter()
	{
		String userName = dataTable.getData("UserName");
		username.sendKeys(userName);
		Reporting("Username set as: "+userName, Status.DONE);
		String strPassword = dataTable.getData("Password");
		password.sendKeys(strPassword);
		Reporting("Password set as: "+strPassword, Status.DONE);
		sync(2);
		logIn_BTN.click();
		Reporting("Clicked on 'Log In' button.", Status.PASS);
		return this;
	}
	
	public Twitter_Page searchPerson()
	{
		searchField.sendKeys("@charanjeet_92", Keys.ENTER);
		Reporting("Searching for user.", Status.SCREENSHOT);
		Reporting("Failing TC purposefully.", Status.FAIL);
		return this;
	}
	
	
	
	
						
	
	
	
}
