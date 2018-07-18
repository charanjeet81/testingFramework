package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.Reporting;
import supportLiberaries.TestCaseBase;

public class TC_04_Login_Twitter_Checking_Headers extends TestCaseBase
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_04_Login_Twitter_Checking_Headers() 
	{
		// setBrowser(Browser.Chrome);
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to validate headers of the Twitter application..");
		driverScript.startExecution(this.getClass().getName());
		if (Reporting.failTC) {
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
