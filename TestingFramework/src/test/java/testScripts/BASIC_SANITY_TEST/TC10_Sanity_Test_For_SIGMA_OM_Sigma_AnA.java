package testScripts.BASIC_SANITY_TEST;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC10_Sanity_Test_For_SIGMA_OM_Sigma_AnA extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC10_Sanity_Test_For_SIGMA_OM_Sigma_AnA() 
	{
		setDescription("To validate SIGMA_OM_Sigma_AnA up and running.");
		driverScript.startExecution(this.getClass().getName());
		if(reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
