package testScripts.SANITY;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC14_Sanity_Test_For_NOKIA_PnA_Instant_Link extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC14_Sanity_Test_For_NOKIA_PnA_Instant_Link()
	{
		setDescription("To validate NOKIA_PnA_Instant_Link up and running.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
