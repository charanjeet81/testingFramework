package bdd.TestRunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

	//@RunWith(Cucumber.class)
	@CucumberOptions(
			dryRun = false, 
			strict = true, 
			monochrome = true,
			features = "FeatureFiles\\SMOKE", 
			glue={"bdd.StepDefinitions"}, 
			format= {"pretty","html:test-outout", "json:json_output/cucumber.json"}//, "junit:junit_xml/cucumber.xml"}
			//tags = {"~@SmokeTest" , "~@RegressionTest", "~@End2End"}			
			)
	
	public class TestRunner_SMOKE extends AbstractTestNGCucumberTests
	{
		
		/*@BeforeClass
		public static void setup() 
		{
		
		}


		@AfterClass
		public static void teardown(){
			
		}*/
	}
	
	//ORed : tags = {"@SmokeTest , @RegressionTest"} -- execute all tests tagged as @SmokeTest OR @RegressionTest
	//ANDed : tags = tags = {"@SmokeTest" , "@RegressionTest"} -- execute all tests tagged as @SmokeTest AND @RegressionTest
	

