package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC_10_GET_Getting_Response_With_Parameters_ReqRes extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void runTC_10_GET_Getting_Response_With_Parameters_ReqRes() 
	{
		setDescription("This TC is to Getting_Response_With_Parameters_ReqRes.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
