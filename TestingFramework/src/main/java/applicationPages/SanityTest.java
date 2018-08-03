package applicationPages;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import supportLiberaries.SUPER_Page;
import supportLiberaries.ScriptHelper;
import supportLiberaries.Status;

public class SanityTest extends SUPER_Page  
{
	public SanityTest(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		PageFactory.initElements(driver, this);
	}

	//::::::::::::::::: O B J E C T S  ::::::::::::::::://
	@FindBy(id = "sigma-login-username")
	WebElement sigmaUsername_TXT;
	@FindBy(id = "sigma-login-password")
	WebElement sigmaPassword_TXT;
	@FindBy(id = "sigma-login-button")
	WebElement sigmaLogin_BTN;
	@FindBy(id = "customer-search-id-input")
	WebElement customerSearch_TXT;
	@FindBy(id = "customer-search-button")
	WebElement customerSearchGo_BTN;
	@FindBy(id = "okta-signin-username")
	WebElement sfdcUsername_TXT;
	@FindBy(id = "okta-signin-password")
	WebElement sfdcPassword_TXT;
	@FindBy(id = "okta-signin-submit")
	WebElement sfdcSignIn_BTN;
	@FindBy(css = "div.infobox-error p")
	WebElement sfdcError_MSG;
	@FindBy(id = "inputEmail")
	WebElement sigmaCatalog_Username_TXT;
	@FindBy(id = "inputPassword")
	WebElement sigmaCatalog_Password_TXT;
	@FindBy(id = "signInButton")
	WebElement sigmaCatalog_SignIn_BTN;
	
		
	//::::::::::::::::: M E T H O D S ::::::::::::::::://
	
	public SanityTest invokeApplication()
	{
		String url = dataTable.getData("URL");
		driver.get(url);
		Reporting("Application invoked under: "+url, Status.DONE);
		return this;
	}
	
	public SanityTest loginToSIGMA()
	{
		String userName = dataTable.getData("UserName");
		String password = dataTable.getData("Password");
		sigmaUsername_TXT.sendKeys(userName);
		Reporting("SIGMA Username set as: "+userName, Status.DONE);
		sigmaPassword_TXT.sendKeys(password);
		Reporting("SIGMA Password set as: "+password, Status.DONE);
		sigmaLogin_BTN.click();
		Reporting("Clicked on 'Login' button.", Status.DONE);
		return this;
	}
	
	public SanityTest customerSearch()
	{
		int count = 0;
		String[] strLeftPan = {"Customer", "Portfolio", "Quotes", "Orders"};
		String customerID = dataTable.getData("CustomerID");
		customerSearch_TXT.sendKeys(customerID);
		Reporting("Customer ID entered as: "+customerID, Status.DONE);
		customerSearchGo_BTN.click();
		Reporting("Clicked on 'Go' button.", Status.DONE);
		List<WebElement> leftPanItems = driver.findElements(By.xpath("//span[@class='customer-text customer-nav-step-text step-title ng-binding']"));
		for (WebElement leftPanItem : leftPanItems) 
		{
			sync(1);
			if(leftPanItem.getText().equals(strLeftPan[count]))
				Reporting(strLeftPan[count]+" menu is coming as expected.", Status.PASS);
			else
				Reporting(strLeftPan[count]+" menu is not coming as expected.", Status.FAIL);
			count++;
		}
		return this;
	}
	
	public SanityTest validateBlankPage()
	{
		int bodyValue = StringUtils.substringBetween(driver.getPageSource(), "</head>", "</html>").length();
		if(bodyValue<250)
			Reporting("CPQ Salesforce plugin is not loading.", Status.FAIL);
		else
			Reporting("CPQ Salesforce plugin is loading properly.", Status.PASS);
		return this;
	}
	
	public SanityTest loginToSalesforce()
	{
		String userName = dataTable.getData("UserName");
		String password = dataTable.getData("Password");
		scrollToElement(sfdcUsername_TXT);
		sfdcUsername_TXT.sendKeys(userName);
		Reporting("SFDC Username set as: "+userName, Status.DONE);
		scrollToElement(sfdcPassword_TXT);
		sfdcPassword_TXT.sendKeys(password);
		Reporting("SFDC Password set as: "+password, Status.DONE);
		sfdcSignIn_BTN.click();
		Reporting("Clicked on 'SIGN IN' button.", Status.DONE);
		if(sfdcError_MSG.getText().contains("Sign in failed!"))
			Reporting("Error message is coming as expected: "+sfdcError_MSG.getText(), Status.PASS);
		else
			Reporting("Error message is not coming as expected: "+sfdcError_MSG.getText(), Status.FAIL);
		return this;
	}
	
	public SanityTest loginToSIGMACatalog()
	{
		String userName = dataTable.getData("UserName");
		String password = dataTable.getData("Password");
		sigmaCatalog_Username_TXT.sendKeys(userName);
		Reporting("SFDC Username set as: "+userName, Status.DONE);
		sigmaCatalog_Password_TXT.sendKeys(password);
		Reporting("SFDC Password set as: "+password, Status.DONE);
		sync(1);
		sigmaCatalog_SignIn_BTN.click();
		sync(2);
//		Reporting("Clicked on 'SIGN IN' button.", Status.DONE);
//		if(sfdcError_MSG.getText().contains("Sign in failed!"))
//			Reporting("Error message is coming as expected: "+sfdcError_MSG.getText(), Status.PASS);
//		else
//			Reporting("Error message is not coming as expected: "+sfdcError_MSG.getText(), Status.FAIL);
		return this;
	}
}
