package businessComponents;

import applicationPages.Login_Page;
import supportLiberaries.ReusableLiberaries;
import supportLiberaries.ScriptHelper;

public class Login_Feature extends ReusableLiberaries
{
	public Login_Feature(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	public void invokeApplication()
	{
		// Will implement keyword driven later.
	}
	
	public void tC_01_Login()
	{
		Login_Page login = new Login_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Application();
	}
	
	public void tC_04_Login()
	{
		Login_Page login = new Login_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Application();
	}
}
