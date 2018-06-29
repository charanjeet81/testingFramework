package businessComponents;

import applicationPages.Login_Page;
import supportLiberaries.ReusableLiberaries;
import supportLiberaries.ScriptHelper;

public class HomeScreen_Feature extends ReusableLiberaries
{
	public HomeScreen_Feature(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	public void invokeApplication()
	{
		// Will implement keyword driven later.
	}
	
	public void tC_02_Login()
	{
		Login_Page login = new Login_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Application();
	}
	
	public void tC_03_Login()
	{
		Login_Page login = new Login_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Application();
	}
}
