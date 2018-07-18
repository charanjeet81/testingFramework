package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.Reporting;
import supportLiberaries.TestCaseBase;

public class TC_03_Login_Twitter extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_03_Login_Twitter() 
	{
		// setBrowser(Browser.Chrome);
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to validate login to Twitter.");
		driverScript.startExecution(this.getClass().getName());
		if (Reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
