package testScripts.BASIC_SANITY_TEST;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC03_Sanity_Test_For_SIGMA_CPQ_Salesforce_Website_Login extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC03_Sanity_Test_For_SIGMA_CPQ_Salesforce_Website_Login() 
	{
		runAllIteration(false);
		setDescription("To validate login to SIGMA CPQ Salesforce Website.");
		driverScript.startExecution(this.getClass().getName());
		if(reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
