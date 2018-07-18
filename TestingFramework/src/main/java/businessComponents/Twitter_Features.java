package businessComponents;

import applicationPages.Twitter_Page;
import supportLiberaries.ReusableLiberaries;
import supportLiberaries.ScriptHelper;

public class Twitter_Features extends ReusableLiberaries
{
	public Twitter_Features(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	public void tC_03_Login_Twitter()
	{
		Twitter_Page login = new Twitter_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Twitter();
	}
	
	public void tC_04_Login_Twitter_Checking_Headers()
	{
		Twitter_Page login = new Twitter_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Twitter()
			 .searchPerson();
	}
}
