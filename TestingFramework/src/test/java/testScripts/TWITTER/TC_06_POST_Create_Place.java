package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC_06_POST_Create_Place extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_06_POST_Create_Place() 
	{
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to Create_Place.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
