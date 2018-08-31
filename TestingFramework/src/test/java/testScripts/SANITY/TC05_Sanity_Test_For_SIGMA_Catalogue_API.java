package testScripts.SANITY;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC05_Sanity_Test_For_SIGMA_Catalogue_API extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	
	@Test(invocationCount = 1)
	public void Testing_TC05_Sanity_Test_For_SIGMA_Catalogue_API() 
	{
		setDescription("To validate SIGMA_Catalogue_API up and running.");
		driverScript.startExecution(this.getClass().getName());
		if(reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
