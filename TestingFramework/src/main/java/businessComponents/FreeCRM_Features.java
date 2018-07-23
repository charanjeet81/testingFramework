package businessComponents;

import applicationPages.Login_Page;
import supportLiberaries.ReusableLiberaries;
import supportLiberaries.ScriptHelper;

public class FreeCRM_Features extends ReusableLiberaries
{
	public FreeCRM_Features(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	public void tC_01_Login_FreeCRM()
	{
		Login_Page login = new Login_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Application();
	}
	
	public void tC_02_Login_FreeCRM_HomePage()
	{
		Login_Page login = new Login_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Application();
			// .validateUser();
	}
}
