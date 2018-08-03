package testScripts.BASIC_SANITY_TEST;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC16_Sanity_Test_For_NOKIA_OpenAM extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 2)
	public void Testing_TC16_Sanity_Test_For_NOKIA_OpenAM()
	{
		setDescription("To verify NOKIA OpenAM portal is up and running.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
