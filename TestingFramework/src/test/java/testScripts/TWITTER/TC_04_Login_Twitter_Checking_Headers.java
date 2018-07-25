package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC_04_Login_Twitter_Checking_Headers extends TestCaseBase
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_04_Login_Twitter_Checking_Headers() 
	{
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to validate headers of the Twitter application..");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
