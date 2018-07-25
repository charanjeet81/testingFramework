package testScripts.TWITTER;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC_05_GET_Google_Search_Place extends TestCaseBase
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_05_GET_Google_Search_Place() 
	{
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to Search_Place");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) {
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
