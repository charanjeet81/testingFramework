package supportLiberaries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class Reporting
{
	public boolean failTC = false;
	int count = 1;
	String startTime;
	String testStep = "";
	String testSteps = "";
	public File currentTCReport;
	public static String currentTCReportPathForServices;
	protected ScriptHelper scriptHelper;
	protected Properties properties;
	protected WebDriver driver;
	protected String canonicalName;
	protected String startTimeCompleteFormat;
	protected String timeTaken;
	public String tcDescription;
	
	public Reporting(String startTimeCompleteFormat, String timeTaken)
	{
		this.startTimeCompleteFormat = startTimeCompleteFormat;
		this.timeTaken = timeTaken;
	}
	
	public Reporting(WebDriver driver, Properties properties, String canonicalName)
	{
		this.driver = driver;
		this.properties = properties;
		this.canonicalName = canonicalName;
		startTime =  DateFormat.getDateTimeInstance().format(new Date());
		String filePath = System.getProperty("user.dir")+"\\Reports";
		File reports = new File(filePath);
		if(!reports.exists())
		{
			reports.mkdir();
			File functionality = new File(filePath+"\\"+canonicalName.replace(".", "#").split("#")[0]+"."+canonicalName.replace(".", "#").split("#")[1]);
			if(!functionality.exists())
			{
				functionality.mkdir();
				File currentReport = new File(functionality.getPath()+"\\"+startTime.replace(",", "").replace(" ", "_").replace(":", "_"));
				if(!currentReport.exists())
				{
					currentReport.mkdir();
					//File currentTCReport = new File(currentReport.getPath()+"\\"+canonicalName.replace(".", "#").split("#")[1]);
					this.currentTCReport = currentReport;
					//if(!currentTCReport.exists())
					//{
						//currentTCReport.mkdir();
						try 
						{
							FileUtils.copyDirectory(new File(System.getProperty("user.dir")+"\\Resources\\ReportTemplate"), currentTCReport);
							System.setOut(new PrintStream(new FileOutputStream(currentTCReport.getPath()+"\\logs.txt")));
							System.setErr(new PrintStream(new FileOutputStream(currentTCReport.getPath()+"\\logs.txt")));
							this.currentTCReportPathForServices = currentReport.getPath();
						} catch (IOException e) {
							e.printStackTrace();
						}
					//}
				}
			}
		}
		else
		{
			File functionality = new File(filePath+"\\"+canonicalName.replace(".", "#").split("#")[0]+"."+canonicalName.replace(".", "#").split("#")[1]);
			if(!functionality.exists())
			{
				functionality.mkdir();
				File currentReport = new File(functionality.getPath()+"\\"+startTime.replace(",", "").replace(" ", "_").replace(":", "_"));
				if(!currentReport.exists())
				{
					currentReport.mkdir();
					//File currentTCReport = new File(currentReport.getPath()+"\\"+canonicalName.replace(".", "#").split("#")[1]);
					this.currentTCReport = currentReport;
					//if(!currentTCReport.exists())
					//{
						//currentTCReport.mkdir();
						try
						{
							FileUtils.copyDirectory(new File(System.getProperty("user.dir")+"\\Resources\\ReportTemplate"), currentTCReport);
							System.setOut(new PrintStream(new FileOutputStream(currentTCReport.getPath()+"\\logs.txt")));
							System.setErr(new PrintStream(new FileOutputStream(currentTCReport.getPath()+"\\logs.txt")));
							this.currentTCReportPathForServices = currentReport.getPath();
						} catch (IOException e) {
							e.printStackTrace();
						}
					//}
				}
			}
			else
			{
				File currentReport = new File(functionality.getPath()+"\\"+startTime.replace(",", "").replace(" ", "_").replace(":", "_"));
				//if(!currentReport.exists())
				//{
					currentReport.mkdir();
					//File currentTCReport = new File(currentReport.getPath()+"\\"+canonicalName.replace(".", "#").split("#")[1]);
					this.currentTCReport = currentReport;
					//if(!currentTCReport.exists())
					//{
						//currentTCReport.mkdir();
						try 
						{
							FileUtils.copyDirectory(new File(System.getProperty("user.dir")+"\\Resources\\ReportTemplate"), currentTCReport);
							System.setOut(new PrintStream(new FileOutputStream(currentTCReport.getPath()+"\\logs.txt")));
							System.setErr(new PrintStream(new FileOutputStream(currentTCReport.getPath()+"\\logs.txt")));
							this.currentTCReportPathForServices = currentReport.getPath();
						} catch (IOException e) {
							e.printStackTrace();
						}
					//}
				}
			}
		}
	//}

	public void updateReport(String method, String description, Status status)
	{
		switch (status.getValue().toUpperCase())
		{
			case "DONE":
				testStep = "<tr><td width='70'>Step "+count+".</td><td>"+method+"</td><td>"+description+"</td><td class=\"yellow\">DONE</td></tr>";
				break;
	
			case "PASS":
				Assert.assertTrue(true);
				if(Boolean.parseBoolean(properties.getProperty("ScreenShotOnPass").trim()))
				{
					String imgName = takeScreenShot(method+"_PASS_");
					testStep = "<tr><td width='70'>Step "+count+".</td><td>"+method+"</td><td>"+description+"</td><td class=\"green\"><a href='.\\Screenshots\\"+imgName+"'>PASS</a></td></tr>";
				}
				else
				{
					testStep = "<tr><td width='70'>Step "+count+".</td><td>"+method+"</td><td>"+description+"</td><td class=\"green\">PASS</td></tr>";
				}
				break;
				
			case "FAIL":
				if(Boolean.parseBoolean(properties.getProperty("ScreenShotOnFail").trim()))
				{
					String imgName = takeScreenShot(method+"_FAIL_");
					testStep = "<tr><td width='70'>Step "+count+".</td><td>"+method+"</td><td>"+description+"</td><td class=\"red\"><a href='.\\Screenshots\\"+imgName+"'>FAIL</a></td></tr>";
				}
				else
				{
					testStep = "<tr><td width='70'>Step "+count+".</td><td>"+method+"</td><td>"+description+"</td><td class=\"red\">FAIL</td></tr>";
				}
				failTC = true;
				break;
				
			case "SCREENSHOT":
				String imgName = takeScreenShot(method+"_SCREENSHOT_");
				testStep = "<tr><td width='70'>Step "+count+".</td><td>"+method+"</td><td>"+description+"</td><td class=\"yellow\" width='50'><a href='.\\Screenshots\\"+imgName+"'>SCREENSHOT</a></td></tr>";
				break;
	
			default:
				System.err.println("Wrong Status Selected.");
				break;
		}
		testSteps = testSteps + testStep;
		count++;
	}
	
	public String takeScreenShot(String method)
	{
		String imgName;
		int imgCount = 1;
		method = method.replace("<i>", "").replace(":</i>", "");
		if(!new File(currentTCReport+"\\Screenshots").exists())
		{
			new File(currentTCReport+"\\Screenshots").mkdir();
		}
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try 
		{
			do
			{
				if(!new File(currentTCReport+"\\Screenshots\\"+method+imgCount+".png").exists())
				{
					FileUtils.copyFile(scrFile, new File(currentTCReport+"\\Screenshots\\"+method+imgCount+".png"));
					break;
				}
				else
				{
					imgCount++;
					FileUtils.copyFile(scrFile, new File(currentTCReport+"\\Screenshots\\"+method+imgCount+".png"));
					break;
				}
			}while(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		imgName = method+imgCount+".png";
		return imgName;
	}
	
	public String completeHTMLReport(Reporting times, ITestResult result)
	{
		String tcStatus = "";
		StringBuffer completeHTML = new StringBuffer();
		String currentHTML = currentTCReport.toString()+"\\Report.html";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(currentHTML));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try 
		{
			if (result.getStatus() == ITestResult.SUCCESS) 
			{
				tcStatus = "<font color='green'><b>Test-Case Passed</b></font>";
			}
			else if (result.getStatus() == ITestResult.FAILURE) 
			{
				tcStatus = "<font color='red'><b>Test-Case Failed</b></font>";
			}
			else if (result.getStatus() == ITestResult.SKIP) 
			{
				tcStatus = "<font color='yello'><b>Test-Case Skipped</b></font>";
			}
			new PrintStream(new FileOutputStream(currentTCReport.getPath()+"\\status.txt")).print(StringUtils.substringBetween(tcStatus, "<b>", "</b>").replace("Test-Case", "").trim());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try
		{
			while (true) // as long as there are lines in the file, print them
			{
				String line = reader.readLine();
				if (line != null) 
				{
					if(line.contains("$applicationName$"))
					{
						line = StringUtils.replace(line, "$applicationName$", properties.getProperty("ProjectName"));
					}
					else if(line.contains("$suite$"))
					{
						line = StringUtils.replace(line, "$suite$", "");
					}
					else if(line.contains("$environment$"))
					{
						line = StringUtils.replace(line, "$environment$", properties.getProperty("Environment")); 
					}
					else if(line.contains("$browser$"))
					{
						line = StringUtils.replace(line, "$browser$", properties.getProperty("Browser"));
					}
					else if(line.contains("$date$"))
					{
						line = StringUtils.replace(line, "$date$", times.startTimeCompleteFormat);
					}
					else if(line.contains("$timeTaken$"))
					{
						line = StringUtils.replace(line, "$timeTaken$", times.timeTaken);
					}
					else if(line.contains("$description$"))
					{
						line = StringUtils.replace(line, "$description$", tcDescription);
					}
					else if(line.contains("$functionality$"))
					{
						line = StringUtils.replace(line, "$functionality$", canonicalName.replace(".", "#").split("#")[0]);
					}
					else if(line.contains("$tcName$"))
					{
						line = StringUtils.replace(line, "$tcName$", canonicalName.replace(".", "#").split("#")[1]);
					}
					else if(line.contains("$status$"))
					{
						line = StringUtils.replace(line, "$status$", tcStatus);
					}
					else if(line.contains("$testSteps$"))
					{
						line = StringUtils.replace(line, "$testSteps$", testSteps);
					}
					completeHTML.append(line);
				} 
				else 
				{
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return completeHTML.toString();
	}
}
