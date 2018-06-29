package applicationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import supportLiberaries.SUPER_Page;
import supportLiberaries.ScriptHelper;

public class Home_Page extends SUPER_Page  
{
	WebDriver driver;
	ScriptHelper scriptHelper;
	
	public Home_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		PageFactory.initElements(driver, this);
	}
	//Webelements
}
