package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.Reporting;
import supportLiberaries.TestCaseBase;

public class TC_07_POST_Delete_Place extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_07_POST_Delete_Place() 
	{
		// setBrowser(Browser.Chrome);
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to Delete Places.");
		driverScript.startExecution(this.getClass().getName());
		if (Reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
