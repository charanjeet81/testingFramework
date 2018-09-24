package bdd.TestRunner;

import org.junit.runner.RunWith;
import org.testng.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.AfterSuite;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;

	//@RunWith(Cucumber.class)
	@CucumberOptions(
			dryRun = true, 
			strict = true, 
			monochrome = true,
			features = "FeatureFiles\\SANITY", 
			glue = {"bdd.StepDefinitions"}, 
			plugin = { "com.cucumber.listener.ExtentCucumberFormatter:", "usage" }
			//format = {"pretty","html:test-outout", "json:json_output/cucumber.json"}//, "junit:junit_xml/cucumber.xml"}
			//tags = {"~@SmokeTest" , "~@RegressionTest", "~@End2End"}			
			)
	
	public class TestRunner_SANITY extends AbstractTestNGCucumberTests
	{
		public static String resultFolder;
		public static String reportPath;
		public static String timeStamp = "";
		public String status = "2";
		public String comment = "";
		
		@BeforeSuite
		public static void setup() {
			ExtentProperties extentProperties = ExtentProperties.INSTANCE;
			reportPath = resultFolder + "/TestReport.html";
			extentProperties.setReportPath(reportPath);
			System.out.println("@BeforeSuite");
		}
		
		@AfterSuite
		public static void teardown() throws IOException {
			Reporter.loadXMLConfig(new File("extent-config.xml"));
			File file = new File("Resources/cognizant_logo_banner.png");
			File file1 = new File(resultFolder);
			Path src = FileSystems.getDefault().getPath(file.getPath(), new String[0]);
			Path dest = FileSystems.getDefault().getPath((new File(file1, file.getName())).getPath(), new String[0]);
			Files.copy(src, dest, new CopyOption[0]);
			Reporter.setSystemInfo("user", System.getProperty("user.name"));
			Reporter.setSystemInfo("os", "Windows");
			System.out.println("@AfterSuite");
		}

		public static String[] featureList;
		static {

			try {
				final CucumberOptions old = (CucumberOptions) TestRunner_SANITY.class.getAnnotation(CucumberOptions.class);

				Object handler = Proxy.getInvocationHandler(old);

				Field field = null;
				try {
					field = handler.getClass().getDeclaredField("memberValues");

				} catch (Exception e) {
					e.printStackTrace();
				}
				field.setAccessible(true);
				Map<String, String[]> memberValues;
				try {
					memberValues = (Map<String, String[]>) field.get(handler);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}

				SimpleDateFormat sdfDateReport = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

				Date now = new Date();
				timeStamp = sdfDateReport.format(now);
				resultFolder = "ReportGenerator/" + "BDD_HtmlReport_" + timeStamp;
				File file = new File(resultFolder);
				file.mkdirs();

				Map systemInfo = new HashMap();
				systemInfo.put("Cucumber version", "v1.2.3");

				// String[] format = { "pretty", "html:" + resultFolder +
				// "/cucumber-html-reports",
				// "json:target/cucumber.json", "junit:" + resultFolder +
				// "/firefox/cucumber.xml" };

				List<String> featureLists = new ArrayList<String>();
				String tagsLists = "";
//				try {
//					featureLists = TestRunner.featureList();
//					tagsLists = TestRunner.tagsList();
//
//				} catch (Exception e1) {
//					// TODO Auto-generate catch block
//					e1.printStackTrace();
//				}
//
//				int x = featureLists.size();
//				featureList = new String[x];
//				for (int i = 0; i < featureLists.size(); i++) {
//					featureList[i] = featureLists.get(i);
//				}
//
//				String[] tags = { tagsLists };
//
//				memberValues.put("features", featureList);
//				// memberValues.put("format", format);
//				if (!tagsLists.isEmpty()) {
//					memberValues.put("tags", tags);
//				}
//
//				System.out.println("featureLists--" + featureLists);
//				System.out.println("tagsLists--" + tagsLists);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

