package testScripts.FREE_CRM;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.TestCaseBase;

public class TC_02_Login_FreeCRM_HomePage extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_02_Login_FreeCRM_HomePage()
	{
		// setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to validate login to freeCRM application and Home Page.");
		driverScript.startExecution(this.getClass().getName());
		if (reporting.failTC) 
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
