package testScripts.FREE_CRM;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC_01_Login_FreeCRM extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 3)
	public void tC_01_Login_FreeCRM() 
	{
		runAllIteration(true);
		setDescription("This TC is to validate login to freeCRM application.");
		driverScript.startExecution(this.getClass().getName());
		if(reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
