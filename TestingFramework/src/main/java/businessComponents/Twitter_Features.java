package businessComponents;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import applicationPages.Twitter_Page;
import io.restassured.response.Response;
import supportLiberaries.Reporting;
import supportLiberaries.ReusableLiberaries;
import supportLiberaries.ScriptHelper;
import supportLiberaries.ServiceBase;
import supportLiberaries.SoapBase;

public class Twitter_Features extends ReusableLiberaries
{
	public Twitter_Features(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	String googleReqPath = "\\Requests\\Google";
	
	public void tC_03_Login_Twitter() throws Exception
	{
		SoapBase soapBase = new SoapBase(scriptHelper);
		
		SOAPMessage response = soapBase.soapCall(googleReqPath, "ADD");
		String addResult = soapBase.getTagValueFromResponse(response, "AddResult");

		/*	Twitter_Page login = new Twitter_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Twitter();*/
	}
	
	public void tC_04_Login_Twitter_Checking_Headers()
	{
		Twitter_Page login = new Twitter_Page(scriptHelper);
		login.invokeApplication()
			 .login_To_Twitter()
			 .searchPerson();
	}
	
	public void tC_05_GET_Google_Search_Place()
	{
		ServiceBase serviceBase = new ServiceBase(scriptHelper);
		
		String completeURI = serviceBase.getURI("PlaceSearch_URI");
		
		Map<String, String> queryParameter = serviceBase.getQueryParameters();
		Response response = serviceBase.executeAPI("GET", completeURI, queryParameter, "{}");
		serviceBase.HttpStatusCodeValidation(response);
		
		String respond = serviceBase.getValueFromResponse(response, "status");
		serviceBase.putRespond("Status", respond);
		
		respond = serviceBase.getValueFromResponse(response, "results[0].types");
		
		respond = serviceBase.getValueFromResponse(response, "results[0].vicinity");
		
		serviceBase.getValuesFromResponse(response, "vicinity");
		
		//Response File parse
        String files = serviceBase.getJsonFile(Reporting.currentTCReportPathForServices+"\\Response.json");
        DocumentContext documentContext = JsonPath.parse(files);

        //Basic Assertions for the API like,  Response Time, Status-Code and Content-Type.
        String responseTime = serviceBase.getURI("responseTime");
        serviceBase.basicAssertions(response, Integer.parseInt(responseTime));

        //Tester-Defined Assertions for the API response
        serviceBase.validateResponse(documentContext, "Header", response);  
        serviceBase.validateResponse(documentContext, "StatusCode", response);
        serviceBase.validateResponse(documentContext, "Contains_Value", response);  
        serviceBase.validateResponse(documentContext, "Element_Occurrence", response);  
	}
	
	public void tC_06_POST_Create_Place()
	{
		ServiceBase serviceBase = new ServiceBase(scriptHelper);
		
		String completeURI = serviceBase.getURI("CreatePlace_URI");
		Map<String, String> queryParameter = serviceBase.getQueryParameters();
		Map<String, String> bodyParameter = serviceBase.getBodyParameters();
		String requestString = serviceBase.generateRequestBody(googleReqPath+"\\POST_CreatePlace.json", bodyParameter);
		
		Response response = serviceBase.executeAPI("POST", completeURI, queryParameter, requestString);
		serviceBase.HttpStatusCodeValidation(response);
		
		//Response File parse
        String files = serviceBase.getJsonFile(Reporting.currentTCReportPathForServices+"\\Response.json");
        DocumentContext documentContext = JsonPath.parse(files);

        //Basic Assertions for the API like,  Response Time, Status-Code and Content-Type.
        String responseTime = serviceBase.getURI("responseTime");
        serviceBase.basicAssertions(response, Integer.parseInt(responseTime));

        //Tester-Defined Assertions for the API response
        serviceBase.validateResponse(documentContext, "Header", response);  
        serviceBase.validateResponse(documentContext, "StatusCode", response);
        serviceBase.validateResponse(documentContext, "Element_Value", response);  
        serviceBase.validateResponse(documentContext, "Contains_Value", response);  
        serviceBase.validateResponse(documentContext, "Content_Presence", response);  
        serviceBase.validateResponse(documentContext, "Element_Occurrence", response);  
	}
	
	public void tC_07_POST_Delete_Place()
	{
		ServiceBase serviceBase = new ServiceBase(scriptHelper);
		String completeURI = serviceBase.getURI("Google_BaseURI");
	}
	
	public void tC_08_POST_Create_Place_Delete_Place()
	{
		ServiceBase serviceBase = new ServiceBase(scriptHelper);
		
		String completeURI = serviceBase.getURI("CreatePlace_URI");
		Map<String, String> queryParameter = serviceBase.getQueryParameters();
		Map<String, String> bodyParameter = serviceBase.getBodyParameters();
		String requestString = serviceBase.generateRequestBody(googleReqPath+"\\POST_CreatePlace.json", bodyParameter);
		
		Response response = serviceBase.executeAPI("POST", completeURI, queryParameter, requestString);
		serviceBase.HttpStatusCodeValidation(response);
		
		//Response File parse
        String files = serviceBase.getJsonFile(Reporting.currentTCReportPathForServices+"\\Response.json");
        DocumentContext documentContext = JsonPath.parse(files);

        //Basic Assertions for the API like,  Response Time, Status-Code and Content-Type.
        String responseTime = serviceBase.getURI("responseTime");
        serviceBase.basicAssertions(response, Integer.parseInt(responseTime));

        //Tester-Defined Assertions for the API response
        serviceBase.validateResponse(documentContext, "", response);  
	}
	
	public void tC_09_GET_Getting_Response_Without_Parameters_ReqRes()
	{
		ServiceBase serviceBase = new ServiceBase(scriptHelper);
		
		String baseURI = serviceBase.getURI("ReqRes_BaseURI");
		String serviceURI = serviceBase.getURI("SingleUser_ServiceURI");

		String completeAPI = baseURI + serviceURI;
		
		Map<String, String> queryParameter = serviceBase.getQueryParameters();
		Response response = serviceBase.executeAPI("GET", completeAPI, queryParameter, "{}");
		serviceBase.HttpStatusCodeValidation(response);
	}
	
	public void tC_10_GET_Getting_Response_With_Parameters_ReqRes()
	{
		ServiceBase serviceBase = new ServiceBase(scriptHelper);
		
		String baseURI = serviceBase.getURI("ReqRes_BaseURI");
		String serviceURI = serviceBase.getURI("ListUser_ServiceURI");
				
		String completeAPI = baseURI + serviceURI;
		
		Map<String, String> queryParameter = serviceBase.getQueryParameters();
		Response response = serviceBase.executeAPI("GET", completeAPI, queryParameter, "{}");
		serviceBase.HttpStatusCodeValidation(response);
	}
	
	public void tC_11_GET_Twitter_oAuth_Latest_Tweet()
	{
		ServiceBase serviceBase = new ServiceBase(scriptHelper);
		
		String completeURI = serviceBase.getURI("LatestTweet_URI");
		Map<String, String> queryParameter = serviceBase.getQueryParameters();
		Response response = serviceBase.executeAPI("GET", completeURI, queryParameter, true, "{}");
		serviceBase.HttpStatusCodeValidation(response);
		
		
	}
	
}
