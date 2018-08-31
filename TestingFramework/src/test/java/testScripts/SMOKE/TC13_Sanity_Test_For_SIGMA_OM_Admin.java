package testScripts.SMOKE;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC13_Sanity_Test_For_SIGMA_OM_Admin extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC13_Sanity_Test_For_SIGMA_OM_Admin()
	{
		setDescription("To validate SIGMA_OM_Admin up and running.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
