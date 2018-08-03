package testScripts.BASIC_SANITY_TEST;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC20_Sanity_Test_For_Stub_Server extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC20_Sanity_Test_For_Stub_Server()
	{
		setDescription("To validate Stub_Server up and running..");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
