package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.Reporting;
import supportLiberaries.TestCaseBase;

public class TC_10_GET_Getting_Response_With_Parameters_ReqRes extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_07_GET_Getting_Response_Without_Parameters_ReqRes() 
	{
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to Getting_Response_With_Parameters_ReqRes.");
		driverScript.startExecution(this.getClass().getName());
		if (Reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
