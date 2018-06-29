package supportLiberaries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverScript extends ReusableLiberaries
{
	public String browser;
	
	public DriverScript(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	public void startExecution(String testCaseToExecute)
	{
 		testCaseToExecute = testCaseToExecute.replace(".", "#").split("#")[2];
		File businessComponents = new File(System.getProperty("user.dir")+"\\target\\classes\\businessComponents");
		File[] allClasses = businessComponents.listFiles();
		for (File classToSearch : allClasses) 
		{
			if(classToSearch.getName().endsWith(".class"))
			{
				try 
				{
					Class<?> className = Class.forName("businessComponents."+classToSearch.getName().replace(".class", ""));
					Method[] testCaseMethods = className.getMethods();
					for (Method method : testCaseMethods) 
					{
						String tcToExecute = testCaseToExecute.replace(String.valueOf(testCaseToExecute.charAt(0)), String.valueOf(testCaseToExecute.charAt(0)).toLowerCase());
						if(method.getName().equals(tcToExecute))
						{
							try
							{
								Constructor<?> ctor = className.getDeclaredConstructors()[0];
								Object businessComponent = ctor.newInstance(scriptHelper);
								method.invoke(businessComponent, (Object[]) null);  
								break;
							} catch (InstantiationException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
						 	} catch (InvocationTargetException e) 
							{
								Assert.assertTrue("Exception other than UI validation.", false);
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							}
						}
					}
				} 
				catch (SecurityException e)
				{
					e.printStackTrace();
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
