package testScripts.SANITY;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC02_Sanity_Test_For_SIGMA_CPQ_Salesforce_Plugin extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC02_Sanity_Test_For_SIGMA_CPQ_Salesforce_Plugin() 
	{
		runAllIteration(false);
		setDescription("To validate SIGMA CPQ Salesforce Plugin up and running.");
		driverScript.startExecution(this.getClass().getName());
		if(reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
