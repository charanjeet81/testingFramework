package testScripts.FREE_CRM;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.Browser;
import supportLiberaries.Reporting;
import supportLiberaries.TestCaseBase;
import supportLiberaries.TestIteration;

public class TC_01_Login_FreeCRM extends TestCaseBase 
{
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void runTC_01_Login_FreeCRM() 
	{
		//setIteration(TestIteration.RunSingleIteration);
		setDescription("This TC is to validate login to freeCRM application.");
		driverScript.startExecution(this.getClass().getName());
		if(Reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
