package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.Reporting;
import supportLiberaries.TestCaseBase;

public class TC_11_GET_Twitter_oAuth_Latest_Tweet extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_11_GET_Twitter_oAuth_Latest_Tweet() 
	{
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is for getting latest tweet.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
