package testScripts.BASIC_SANITY_TEST;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC21_Sanity_Test_For_SIS_Network_Platform extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC21_Sanity_Test_For_SIS_Network_Platform()
	{
		setDescription("To validate SIS_Network_Platform up and running.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
