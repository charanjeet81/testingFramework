package bdd.StepDefinitions;

import org.openqa.selenium.support.PageFactory;

import cucumber.api.java.en.Then;
import supportLiberaries.SUPER_Page;
import supportLiberaries.ScriptHelper;

public class HomeStepDefinition  
{
	@Then("^user enters \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_enters_and(String arg1, String arg2) 
	{
		System.out.println(arg1 + arg2);
	}

	@Then("^Close the browser$")
	public void close_the_browser() 
	{
		System.out.println("close_the_browser");
	}

	@Then("^quit the browser$")
	public void quit_the_browser() 
	{
		System.out.println("quit_the_browser");
		
	}
		

	

}
