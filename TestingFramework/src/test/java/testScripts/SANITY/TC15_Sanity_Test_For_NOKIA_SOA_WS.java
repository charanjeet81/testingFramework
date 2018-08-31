package testScripts.SANITY;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC15_Sanity_Test_For_NOKIA_SOA_WS extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC15_Sanity_Test_For_NOKIA_SOA_WS()
	{
		setDescription("To validate NOKIA_SOA_WS up and running.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
