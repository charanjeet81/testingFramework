package supportLiberaries;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.testng.Assert;

public class DriverScript extends ReusableLiberaries
{
	public DriverScript(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	boolean flag;
	String tcToExecute;
	
	public void startExecution(String testCaseToExecute)
	{
		triggerExecution(testCaseToExecute);
	}
	
	public void triggerExecution(String testCaseToExecute)
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
					tcToExecute = Character.toLowerCase(testCaseToExecute.charAt(0))+testCaseToExecute.substring(1, testCaseToExecute.length());
					for (Method method : testCaseMethods) 
					{
						if(method.getName().equals(tcToExecute))
						{
							flag = true;
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
						 		Assert.assertTrue(false, "Exception other than UI validation.");
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
		if(!flag)
		{
			reporting.updateReport("DriverScript.triggerExecution()", tcToExecute+" does not exists in BusinessComponents.", Status.FAIL);
			System.err.println(tcToExecute+", does not exists in BusinessComponents.");
		}
	}
}
