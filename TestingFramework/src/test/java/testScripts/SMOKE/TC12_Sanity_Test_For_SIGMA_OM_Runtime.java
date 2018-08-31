package testScripts.SMOKE;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC12_Sanity_Test_For_SIGMA_OM_Runtime extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC12_Sanity_Test_For_SIGMA_OM_Runtime()
	{
		setDescription("To validate SIGMA_OM_Runtime up and running.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
