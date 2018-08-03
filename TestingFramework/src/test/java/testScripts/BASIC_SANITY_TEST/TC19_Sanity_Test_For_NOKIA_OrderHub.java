package testScripts.BASIC_SANITY_TEST;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC19_Sanity_Test_For_NOKIA_OrderHub extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC19_Sanity_Test_For_NOKIA_OrderHub()
	{
		setDescription("To validate NOKIA_OrderHub up and running.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
