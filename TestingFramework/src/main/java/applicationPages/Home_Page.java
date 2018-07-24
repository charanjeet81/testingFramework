package applicationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import supportLiberaries.SUPER_Page;
import supportLiberaries.ScriptHelper;
import supportLiberaries.Status;

public class Home_Page extends SUPER_Page  
{
	public Home_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		PageFactory.initElements(driver, this);
	}

	//::::::::::::::::: O B J E C T S  ::::::::::::::::://
	
	@FindBy(xpath = "//td[contains(text(),'CRMPRO')]")
	WebElement user;
	
	//::::::::::::::::: M E T H O D S ::::::::::::::::://
	
	public Home_Page validateUser()
	{
		sync(3);
		if(driver.getTitle().contains("CRMPRO"))
			Reporting("Title is coming as expected.", Status.PASS);
		else
			Reporting("Title is not coming as expected.", Status.FAIL);
		return this;
	}
	
	
}
