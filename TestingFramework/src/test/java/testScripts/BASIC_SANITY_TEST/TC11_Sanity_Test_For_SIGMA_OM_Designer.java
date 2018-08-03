package testScripts.BASIC_SANITY_TEST;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC11_Sanity_Test_For_SIGMA_OM_Designer extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC11_Sanity_Test_For_SIGMA_OM_Designer() 
	{
		setDescription("To validate SIGMA_OM_Designer up and running.");
		driverScript.startExecution(this.getClass().getName());
		if(reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
