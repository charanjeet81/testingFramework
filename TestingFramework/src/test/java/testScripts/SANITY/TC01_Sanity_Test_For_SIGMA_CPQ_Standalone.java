package testScripts.SANITY;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC01_Sanity_Test_For_SIGMA_CPQ_Standalone extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC01_Sanity_Test_For_SIGMA_CPQ_Standalone() 
	{
		runAllIteration(false);
		setDescription("To validate SIGMA CPQ Standalone up and running.");
		driverScript.startExecution(this.getClass().getName());
		if(reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
