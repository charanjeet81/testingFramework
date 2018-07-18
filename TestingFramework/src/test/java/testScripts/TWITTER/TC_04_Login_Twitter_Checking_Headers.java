package testScripts.MODULE_2;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import supportLiberaries.Reporting;
import supportLiberaries.TestCaseBase;

public class TC_04_Login extends TestCaseBase 
{
private SoftAssert softAssert = new SoftAssert();
	
	@Test
	public void runTC_TC_01_Login() 
	{
		//testParameters.setDescription("This TC is to validate login to application.");
		//testParameters.setIteration(TestIteration. <All running mode>);
		//testParameters.setBrowser(Browser.Chrome); <all modes>
		driverScript.startExecution(this.getClass().getName());
		if(Reporting.failTC)
		{
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}
}
