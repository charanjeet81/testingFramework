package testScripts.BASIC_SANITY_TEST;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC17_Sanity_Test_For_NOKIA_Service_Catalogue extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC17_Sanity_Test_For_NOKIA_Service_Catalogue()
	{
		setDescription("To validate NOKIA_Service_Catalogue up and running.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
