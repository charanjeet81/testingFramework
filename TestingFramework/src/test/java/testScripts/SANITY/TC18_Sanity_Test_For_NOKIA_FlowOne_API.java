package testScripts.SANITY;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC18_Sanity_Test_For_NOKIA_FlowOne_API extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC18_Sanity_Test_For_NOKIA_FlowOne_API()
	{
		setDescription("To verify NOKIA_FlowOne_API portal is up and running..");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
