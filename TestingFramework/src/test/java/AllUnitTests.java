import java.io.File;
import java.io.IOException;

import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

public class AllUnitTests 
{
	
	private SoftAssert softAssert = new SoftAssert();
	@Test
	public void teststackquestion1() 
	{
	    boolean actual = true;
	    boolean expected = true;
	    Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void teststackquestion2() 
	{
		softAssert.assertTrue(false);
	    boolean actual = true;
	    boolean expected = true;
	    Assert.assertEquals(actual, expected);
	    softAssert.assertAll();
	}
	
	@Test
	public void teststackquestion3() 
	{
	    boolean actual = true;
	    boolean expected = true;
	    Assert.assertEquals(actual, expected);
	}
		
	@BeforeMethod
	public void beforeMethod(ITestResult result)  throws ParserConfigurationException, SAXException, IOException
	{
		
	}

	@AfterMethod
	public void afterMethod(ITestResult result)  throws ParserConfigurationException, SAXException, IOException
	{
		System.out.println(result.getName());
		System.out.println(result.getStatus());
		System.out.println(result.getMethod().getMethodName());
		
		String path = System.getProperty("user.dir")+"/test-output/testng-results.xml";
	    File fXmlFile = new File(path);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
	    
	    System.out.println("Total : " + doc.getDocumentElement().getAttribute("total")) ;
	    System.out.println("Passed : " +doc.getDocumentElement().getAttribute("passed")) ;
	    System.out.println("Failed : " +doc.getDocumentElement().getAttribute("failed")) ;
	    System.out.println("Skipped : " +doc.getDocumentElement().getAttribute("skipped")) ;
	    
	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		NodeList nList = doc.getElementsByTagName("test-method");
				
		System.out.println("----------------------------");

		for (int temp = 0; temp < nList.getLength(); temp++) 
		{
			Node nNode = nList.item(temp);
//			System.out.println("\nCurrent Element :" + nNode.getNodeName());
//			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
//			{
				Element eElement = (Element) nNode;
				System.out.println("Staff id : " + eElement.getAttribute("name"));
				System.out.println("Staff id : " + eElement.getAttribute("status"));
//			}
		}
		
	    try
		 {
		    if(result.getStatus() == ITestResult.SUCCESS)
		    {
		        System.out.println("passed **********");
		    }
	
		    else if(result.getStatus() == ITestResult.FAILURE)
		    {
		        System.out.println("Failed ***********");
		    }
	
		     else if(result.getStatus() == ITestResult.SKIP )
		     {
		        System.out.println("Skiped***********");
		    }
		}
		   catch(Exception e)
		   {
		     e.printStackTrace();
		   }
	}
}
