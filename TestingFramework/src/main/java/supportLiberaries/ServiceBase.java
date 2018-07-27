package supportLiberaries;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.openqa.selenium.support.PageFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ServiceBase extends SUPER_Page
{
	public ServiceBase(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		PageFactory.initElements(driver, this);
	}
//	public String currentTest = "";
//	public static String templateFolder = "Template/";
//	public static String requestFolder = "Request/";
//	public static String responseFolder = "Response/";
//	public String requestTemplatePath = templateFolder;
//	public String APIURL = getURI("API");
	public static String totalTimeTaken;
	public String accessToken = null;
	public String instanceURL = null;
	public String salesforceUser = null;
	public String requestFilePath = ""; 
	public String responseFilePath = ""; 
	public String accountID = null;
	public String accountName = null;
	public Map<String, String> queryParams = new HashMap<>();
	public Map<String, String> bodyParams = new HashMap<>();

	public String getURI(String key)
	{
		String value = "";
		try 
		{
			FileInputStream fileInputStream = new FileInputStream("./Requests/RequestURIs.properties");
			Properties property = new Properties();
			property.load(fileInputStream);
			value = property.getProperty(key);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Reporting(key+" is called as: <b>"+value+"</b>", Status.DONE);
		return value;
	}

	/*public void GetInstanceURLAndToken() throws Exception
	{
		String baseURL = getURI("baseURL");
		String accessTokenURL = getURI("accessTokenURL");
		String sUserName = getURI("sUserName");
		String sPassword = getURI("sPassword");
		String clientID = getURI("clientID");
		String clientSecret = getURI("clientSecret");
		String grant_type = getURI("grant_type");
		String serviceUrl = baseURL + accessTokenURL + "?username=" + sUserName + "&password=" + sPassword
				+ "&client_id=" + clientID + "&client_secret=" + clientSecret + "&grant_type=" + grant_type;
		HttpResponse tokenResponse = null;
		HttpPost tokenTestRequest = new HttpPost(serviceUrl);
		HttpClient tokenrestClient = new DefaultHttpClient();
		tokenResponse = tokenrestClient.execute(tokenTestRequest);
		System.out.println("response for access token ---" + tokenResponse.getStatusLine().getStatusCode());
		HttpEntity tokenEntity = tokenResponse.getEntity();
		String tokenEntityString = null;
		tokenEntityString = EntityUtils.toString(tokenEntity);
		JSONObject tokenResp = new JSONObject(tokenEntityString);
		accessToken = tokenResp.getString("access_token");
		instanceURL = tokenResp.getString("instance_url");
		salesforceUser = sUserName;
	}*/

	public Response executeAPI(String requestType, String completeURI, Map<String, String> queryParams, boolean oAuthParams, String requestString)
	{
		Response response = null;
		
		String consumerKey = getURI("ConsumerKey");
		String consumerSecret = getURI("ConsumerSecret");
		String accessToken = getURI("AccessToken");
		String secretToken = getURI("SecretToken");
		try 
		{
			RequestSpecBuilder builder = new RequestSpecBuilder();
			RequestSpecification specification = builder.build();
			RequestSpecification specWithBody = given().auth().oauth(consumerKey, consumerSecret, accessToken, secretToken).accept(ContentType.JSON).queryParams(queryParams).body(requestString).log().all();
			RequestSpecification specWithoutBody = given().auth().oauth(consumerKey, consumerSecret, accessToken, secretToken).accept(ContentType.JSON).queryParams(queryParams).log().all();
			Reporting("Request Type is: <b>"+requestType+"</b>", Status.DONE);
			Reporting("Complete URI is: <b>"+completeURI+"</b>", Status.DONE);
			Reporting("Request Body is: <b>"+requestString+"</b>", Status.DONE);
			
			if (requestString.equals("{}")) 
			{
				specification = specWithoutBody;
			} 
			else 
			{
				specification = specWithBody;
			}
			switch (requestType.toUpperCase()) 
			{
			case "GET":
				response = specification.when().get(completeURI);
				break;

			case "POST":
				response = specification.when().post(completeURI);
				break;

			case "PUT":
				response = specification.when().put(completeURI);
				break;

			case "DELETE":
				response = specification.when().delete(completeURI);
				break;

			case "PATCH":
				response = specification.when().patch(completeURI);
				break;
			}

			Reporting("Response recieved successfully.", Status.DONE);
			if (requestType.toUpperCase() != "PUT" || requestType.toUpperCase() != "PATCH" || requestType.toUpperCase() != "DELETE")
			{
				responseFilePath = Reporting.currentTCReportPathForServices+"\\Response.json";
				storeResponseInFile(responseFilePath, response);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}

	public Response executeAPI(String requestType, String completeURI, Map<String, String> queryParams, String requestString)
	{
		Response response = null;
		try 
		{
			RequestSpecBuilder builder = new RequestSpecBuilder();
			RequestSpecification specification = builder.build();
			RequestSpecification specWithBody = given().contentType(ContentType.JSON).accept(ContentType.JSON).queryParams(queryParams).body(requestString).log().all();
			RequestSpecification specWithoutBody = given().contentType(ContentType.JSON).accept(ContentType.JSON).queryParams(queryParams).log().all();
			Reporting("Request Type is: <b>"+requestType+"</b>", Status.DONE);
			Reporting("Complete URI is: <b>"+completeURI+"</b>", Status.DONE);
			Reporting("Request Body is: <b>"+requestString+"</b>", Status.DONE);
			
			if (requestString.equals("{}")) 
			{
				specification = specWithoutBody;
			} 
			else 
			{
				specification = specWithBody;
			}
			switch (requestType.toUpperCase()) 
			{
			case "GET":
				response = specification.when().get(completeURI);
				break;

			case "POST":
				response = specification.when().post(completeURI);
				break;

			case "PUT":
				response = specification.when().put(completeURI);
				break;

			case "DELETE":
				response = specification.when().delete(completeURI);
				break;

			case "PATCH":
				response = specification.when().patch(completeURI);
				break;
			}

			Reporting("Response recieved successfully.", Status.DONE);
			if (requestType.toUpperCase() != "PUT" || requestType.toUpperCase() != "PATCH" || requestType.toUpperCase() != "DELETE")
			{
				responseFilePath = Reporting.currentTCReportPathForServices+"\\Response1.json";
				storeResponseInFile(responseFilePath, response);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}

	public void oAuth2(String url, String userName, String accessToken)
	{
		baseURI = url;
		authentication = oauth2(accessToken);
		if (!(baseURI.length() > 0))
		{
			Reporting("Invalid API call. Failed to Authenticate " + userName + baseURI, Status.FAIL);
		}
		else
		{
			Reporting("Authentication successful for " + userName + baseURI, Status.PASS);
		}
		System.out.println(authentication + " " + userName);
	}

	public String generateRequestBody(String requestTemplatePath, Map<String, String> bodyParams)
	{
		String requestString = null;
		try
		{
			requestString = getTemplate(requestTemplatePath);
			requestString = setJSONValue(requestString, bodyParams);
			System.out.println(requestString);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
		return requestString;
	}

	public void HttpStatusCodeValidation(Response response) 
	{
		int statusCode = response.getStatusCode();
		String codes = String.valueOf(statusCode);
		if (codes != null && !codes.isEmpty())
		{
			if ((statusCode >= 100) && (statusCode < 200)) 
			{
				Reporting("<b>"+statusCode+"</b> Recieving 'Informational Responses', 100-Continue / 101-Switching Protocols / 102-Processing.", Status.FAIL);
			} 
			else if ((statusCode >= 200) && (statusCode < 300))
			{
				Reporting("Recieving 'Success Responses'.", Status.PASS);
				switch (statusCode) 
				{
					case 200:
						Reporting("<b>"+statusCode+"</b> Status Code Validated Successfully - OK", Status.PASS);
						break;
	
					case 201:
						Reporting("<b>"+statusCode+"</b> Status Code Validated Successfully - Created", Status.PASS);
						break;
	
					case 202:
						Reporting("<b>"+statusCode+"</b> Status Code Validated Successfully - Accepted", Status.PASS);
						break;
				}
			} 
			else if ((statusCode >= 300) && (statusCode < 400)) 
			{
				Reporting("<b>"+statusCode+"</b> Recieving 'Redirection Responses' of Status Code 3XX range.", Status.DONE);
			}
			else if ((statusCode >= 400) && (statusCode < 500)) 
			{
				Reporting("Recieving 'Client Error Responses'.", Status.FAIL);
				switch (statusCode) 
				{
				case 400:
					Reporting("<b>"+statusCode+"</b> Status Code Validated as - Bad", Status.FAIL);
					break;

				case 401:
					Reporting("<b>"+statusCode+"</b> Status Code Validated as - Unauthorized", Status.FAIL);
					break;

				case 403:
					Reporting("<b>"+statusCode+"</b> Status Code Validated as - Forbidden", Status.FAIL);
					break;

				case 404:
					Reporting("<b>"+statusCode+"</b> Status Code Validated as - Not Found", Status.FAIL);
					break;

				case 407:
					Reporting("<b>"+statusCode+"</b> Status Code Validated as - Authentication Required", Status.FAIL);
					break;
				}
			} 
			else if ((statusCode >= 500) && (statusCode < 600)) 
			{
				Reporting("<b>"+statusCode+"</b> Recieving 'Server Error Response'.", Status.FAIL);
			}
		} else {
			// ExtentUtility.getTest().log(LogStatus.FAIL, "Status Code not reveived",
			// String.valueOf(statusCode));
		}
	}

	public void HttpStatusCodeValidation(Response response, String expectedStatusCode) 
	{
		int statusCode = response.getStatusCode();
		int intExpectedStatusCode = Integer.parseInt(expectedStatusCode);
		String codes = String.valueOf(statusCode);
		if (codes != null && !codes.isEmpty())
		{
			if (intExpectedStatusCode == statusCode) 
			{
				Reporting("Expected and Actual Status Code validated successfully i.e. "+statusCode, Status.PASS);
			} 
			else
			{
				Reporting("Expected: "+expectedStatusCode+" and Actual "+statusCode+" Status Code are not matching. ", Status.FAIL);
			}
		} else {
			Reporting("Status Code not reveived from generated response.", Status.FAIL);
		}
	}

	public void validateHeader(Response response, String key, String value) 
	{
		Headers allHeaders = response.headers();
		String head = allHeaders.get(key).getName();
		String valuefield = allHeaders.get(key).getValue();
		if (key.equalsIgnoreCase(head))
		{
			Reporting("Header Key is coming as expected i.e. "+head, Status.PASS);
		}
		else
		{
			Reporting("Header Key is not coming as expected i.e. "+head, Status.FAIL);
		}
		if (valuefield.equalsIgnoreCase(value))
		{
			Reporting("Header Value is coming as expected as <b>"+valuefield+"</b>  for Key: <b>"+key+"</b>.", Status.PASS);
		}
		else
		{
			Reporting("Header Value is not coming as expected as <b>"+valuefield+"</b>  for Key: <b>"+key+"</b>.", Status.FAIL);
		}
	}

	// For Single Key. 
	public String getValueFromResponse(String responseFile, Response response, String jsonpath)
	{
		String files = getJsonFile(Reporting.currentTCReportPathForServices+"\\"+responseFile);
        DocumentContext documentContext = JsonPath.parse(files);
        String value = String.valueOf(getJSONValue(documentContext, jsonpath)); 
        Reporting("Value for JSON path: "+jsonpath+" is: "+value, Status.DONE);
		return value;
	}
	
	public void putRespond(String field, String value)
	{
		dataTable.setData(field, value);
		Reporting(value+" value is set to sheet for column:"+field, Status.DONE);
	}
	
	public String fetchValue(String field)
	{
		String value = dataTable.getData(field); 
		Reporting(value+" value is fetched from sheet for column:"+field, Status.DONE);
		return value;
	}
	
	// For Multiplse Same Keys. 
	public void getValuesFromResponse(Response response, String key)
	{
		String[] allValues = StringUtils.substringsBetween(response.asString(), "\""+key+"\"", ",");
	}

	public void validateResponse(String responseFilePath, Response response) 
	{
		String value = "";
		String jsonPath = "";
		String files = getJsonFile(Reporting.currentTCReportPathForServices+"\\"+responseFilePath+".json");
        DocumentContext documentContext = JsonPath.parse(files);
		
        String[] validationTypes = {"HEADERS", "STATUS_CODE", "KEY_OCC", "VALUE_OCC", "CONTAINS_VALUE"};
		for (String validationType : validationTypes)
		{
			try
			{
				switch (validationType) 
				{
				case "HEADERS":
					String data = dataTable.getData("HEADERS");
					if(data.isEmpty())
					{ }
					else
					{
						jsonPath = data.split(":")[0];
						value = data.split(":")[1];
						validateHeader(response, jsonPath, value);
					}
					break;

				case "STATUS_CODE":
					data = dataTable.getData("STATUS_CODE");
					if(data.isEmpty())
					{ }
					else
					{
						value = data;
						HttpStatusCodeValidation(response, value);
					}
					break;
					
				case "KEY_OCC":
					data = dataTable.getData("KEY_OCC");
					if(data.isEmpty())
					{ }
					else
					{
						jsonPath = data;
						occurenceKeyCount(documentContext, jsonPath, Reporting.currentTCReportPathForServices+"\\"+responseFilePath+".json");
					}
					break;
					
				case "VALUE_OCC":
					data = dataTable.getData("VALUE_OCC");
					if(data.isEmpty())
					{ }
					else
					{
						jsonPath = data.split(":")[0];
						value = data.split(":")[1];
						occurenceValueCount(documentContext, value);
					}
					break;

				case "CONTAINS_VALUE":
					data = dataTable.getData("CONTAINS_VALUE");
					if(data.isEmpty())
					{ }
					else
					{
						jsonPath = data.split(":")[0];
						value = data.split(":")[1];
						containsString(documentContext, jsonPath, value);
					}
					break;

				default:
					System.out.println("Invalid validation.");
				}
			}
			catch (Exception e) 
			{
				Reporting("Exception occured because of incorrect inputs for: <b>"+validationType+"</b>", Status.FAIL);
			}
		}
	}

	public void containsString(DocumentContext documentContext, String jsonPath, String value) 
	{
		Object responseObj = getJSONValue(documentContext, jsonPath);
		String responseVal = String.valueOf(responseObj);
		if (responseVal.contains(value)) 
		{
			Reporting("Response contains the String in the path, 'Expected Value' :" + value+ " 'Actual Value': " + responseVal, Status.PASS);
		} 
		else 
		{
			Reporting("Response does not contains the String in the path, 'Expected Value' :" + value+ " 'Actual Value': " + responseVal, Status.FAIL);
		}
	}

	public void validateContent(DocumentContext documentContext, String jsonPath, String valueType, String value) 
	{
		Object res;
		String rs;
		int val;
		long lval;
		int values;
		long lvalues;
		float mat, matfloat;
		switch (valueType.toLowerCase()) 
		{
		case "string":
			res = getJSONValue(documentContext, jsonPath);
			rs = String.valueOf(res);
			if (value.equalsIgnoreCase(rs)) 
			{
				Reporting("String value verified <br/>'Expected Value': " + value+ " Actual Value: " + rs, Status.PASS); 
			} 
			else 
			{
				Reporting("String value verified failed <br/>'Expected Value': " + value+ " Actual Value: " + rs, Status.FAIL);
			}
			break;

		case "integer":

		case "int":
			res = getJSONValue(documentContext, jsonPath);
			rs = String.valueOf(res);
			val = Integer.valueOf(rs);
			if (isInteger(value)) 
			{
				values = Integer.valueOf(value);
				if (val == values) 
				{
					Reporting("Integer value verified, <br/>'Expected Value': "+value+" 'Actual Value': " + val, Status.PASS);
				} 
				else 
				{
					Reporting("Integer value verification failed <br/>'Expected Value': "+value+" 'Actual Value': " + val, Status.FAIL);				}
			} 
			else 
			{
				Reporting("Integer value verification failed <br/>'Expected Value': " + value+ " 'Actual Value' is not Integer.", Status.FAIL);
			}
			break;

		case "long":
			res = getJSONValue(documentContext, jsonPath);
			rs = String.valueOf(res);
			lval = Long.valueOf(rs);
			if (isLong(value))
			{
				lvalues = Long.parseLong(value);
				if (lval == lvalues) 
				{
					Reporting("Long value verified <br />'Expected Value': " + value+" 'Actual Value': " + lval, Status.PASS);
				} else {
					Reporting("Long value verified <br />'Expected Value': " + value+" 'Actual Value': " + lval, Status.FAIL);
				}
			} else {
				Reporting("Long value verification failed <br /> 'Expected Value': " + value + "'Actual Value' is not Long.", Status.FAIL);
			}
			break;

		case "float":
			res = getJSONValue(documentContext, jsonPath);
			rs = String.valueOf(res);
			matfloat = Float.parseFloat(rs);
			if (isFloat(value)) {
				mat = Float.parseFloat(value);
				if (mat == matfloat) 
				{
					Reporting("Float value verified <br />'Expected Value': " + value+" 'Actual Value': " + mat, Status.PASS);
				}
				else 
				{
					Reporting("Float value verification failed <br />'Expected Value': " + value+" 'Actual Value': " + mat, Status.FAIL);	// <br />" + "Expected value : " + value, "Actual value : " + mat);
				}
			} else {
				Reporting("Float value verification failed <br />'Expected Value': " + value + "'Actual Value' is not Float.", Status.FAIL);
			}
			break;
		}
	}

	public boolean isInteger(String s) 
	{
		boolean isValidInteger = false;
		try {
			Integer.parseInt(s);
			isValidInteger = true;
		} catch (NumberFormatException ex) {
		}
		return isValidInteger;
	}

	public boolean isLong(String s) 
	{
		boolean isValidLong = false;
		try {
			Long.parseLong(s);
			isValidLong = true;
		} catch (NumberFormatException ex) {
		}
		return isValidLong;
	}

	public boolean isFloat(String s)
	{
		boolean isValidFloat = false;
		try {
			Float.parseFloat(s);
			isValidFloat = true;
		} catch (NumberFormatException ex) {
		}
		return isValidFloat;
	}

	public Object getJSONValue(DocumentContext doc, String jsonpath) 
	{
		return doc.read(jsonpath);
	}
	
	public String setJSONValue(String jsonString, Map<String, String> bodyParams) 
	{
		String json = jsonString;
		for (Map.Entry<String, String> m : bodyParams.entrySet()) 
		{
			String jsonPath = m.getKey();
			String newValue = m.getValue();
			System.out.println("before doc context");
			DocumentContext initialDoc = null;
			try 
			{
				initialDoc = JsonPath.parse(json);
			} 
			catch (Exception e) 
			{
				System.out.println(e);
			}
			String currentValue = initialDoc.read(jsonPath);
			System.out.println("Currenttt------" + currentValue);
			DocumentContext doc = JsonPath.parse(json).set(jsonPath, newValue);
			json = doc.jsonString();
			System.out.println("jsonPath-" + jsonPath + " newValue-" + newValue);
			if (!jsonString.equals(json)) 
			{
				// ExtentUtility.getTest().log(LogStatus.PASS, "Value modified in JSON Object
				// <br /> Existing Value : " + currentValue, "New Value : " + newValue);
			} 
			else if (jsonString.contains(newValue)) 
			{
				// ExtentUtility.getTest().log(LogStatus.INFO, "Value exists, Unable to modify
				// value in JSON Object", "");
			}
			else
			{
				// ExtentUtility.getTest().log(LogStatus.FAIL, "Unable to modify value in JSON
				// Object", "");
			}
		}
		return json;
	}

	public String getTemplate(String requestTemplatePath) throws IOException
	{
		File file = new File(requestTemplatePath);
		if (!(file.exists())) {
			// ExtentUtility.getTest().log(LogStatus.FAIL, "Template File not found in
			// path", file.getAbsolutePath());
			return null;
		} else if (requestTemplatePath.isEmpty() || requestTemplatePath.equals("")) {
			// ExtentUtility.getTest().log(LogStatus.INFO, "Template File is Empty.
			// Assigning default body for request.", "{}");
			return "{}";
		}
		String fileContent = new String(Files.readAllBytes(Paths.get(requestTemplatePath)));
		// System.out.println("fileContent- " + fileContent + " requestTemplatePath--" +
		// requestTemplatePath);
		if (fileContent.contains("{}") || fileContent.length() > 2) {
			// ExtentUtility.getTest().log(LogStatus.PASS, "Fetching Template file
			// successful", "<a href=\"" + file.getAbsolutePath() + "\">" +
			// requestTemplatePath.split("/")[1] + "</a>");
		} else {
			// ExtentUtility.getTest().log(LogStatus.FAIL, "Fetching Template from file
			// unsuccessful", "");
		}
		return fileContent;
	}

	public String getJsonFile(String requestFilePath) 
	{
		File file = new File(requestFilePath);
		if (!(file.exists()))
		{
			Reporting("File not found in path " + file.getAbsolutePath(), Status.FAIL);
			return null;
		} 
		else if (requestFilePath.isEmpty() || requestFilePath.equals("")) 
		{
			Reporting("No request Body for the request ", Status.FAIL);
			return "{}";
		}

		String fileContent = null;
		try
		{
			fileContent = new String(Files.readAllBytes(Paths.get(requestFilePath)));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return fileContent;
	}

	public boolean storeJsonInFile(String jsonFilePath, String jsonString) 
	{
		File file = new File(jsonFilePath);
		try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
			fileWriter.write(jsonString);
			fileWriter.flush();
			// System.out.println("File Write Complete--" + jsonFilePath);
			// ExtentUtility.getTest().log(LogStatus.PASS, "JSON stored into file <br />" +
			// jsonFilePath.split("/")[1] + " file", "<a href=\"" + file.getAbsolutePath() +
			// "\">" + jsonFilePath.split("/")[1] + "</a>");
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("File Write Failed");
			// ExtentUtility.getTest().log(LogStatus.FAIL, "Failed to capture JSON in a
			// file");
		}
		return false;
	}
	
	public boolean storeResponseInFile(String responseFilePath, Response response)
	{
	    File file1 = new File(responseFilePath);
	    if(file1.exists())
	    {
	    	responseFilePath = responseFilePath.replace("Response1.json", "Response2.json");
    	    File file2 = new File(responseFilePath);
	 	    if(file2.exists())
	 	    {
	 	    	responseFilePath = responseFilePath.replace("Response2.json", "Response3.json");
	 	    }
	    }
		String responseString = formatResponse(response);
		try (FileWriter fileWriter = new FileWriter(responseFilePath)) 
		{
			fileWriter.write(responseString);
			fileWriter.flush();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	public String formatResponse(Response response)
	{
		String format = response.body().prettyPrint();
		return format;
	}

	public void assertResponseTime(Response response, int intervals) 
	{
		long time = response.getTimeIn(TimeUnit.SECONDS);
		if (time <= intervals) 
		{
			Reporting("Response time is successfully validated under expected time: " + intervals, Status.PASS);
		} 
		else 
		{
			Reporting("Response time is not successfully validated under expected time: " + intervals, Status.FAIL);
		}
	}

	public int getJSONCount(DocumentContext doc, String jsonpath) 
	{
		return doc.read(jsonpath);
	}

	// Assertion for specific value presence in Response Received
	public void occurenceValueCount(DocumentContext documentContext, String value) 
	{
		String ash = ".*";
		int count = 0;
		ArrayList<String> list = new ArrayList<String>();
		String rs = String.valueOf(getJSONValue(documentContext, "$.*"));
		rs = rs.replace("[", "");
		rs = rs.replace("]", "");

		String[] r;
		String val = "";
		boolean flag = false;
		while (rs != null) 
		{
			flag = false;
			r = rs.split(",");
			for (int j = 0; j < r.length; j++)
			{
				list.add(r[j]);
				val = r[j];
				val = val.replace("\"", "");
				if (value.equalsIgnoreCase(val)) 
				{
					count++;
				}
				if (val.contains("{")) 
				{
					rs = String.valueOf(getJSONValue(documentContext, "$.*" + ash));
					rs = rs.replace("[", "");
					rs = rs.replace("]", "");

					flag = true;
					ash = ash + ".*";
					break;
				}
			}
			if (flag == false) 
			{
				break;
			}
		}
		Reporting("Number of occurences of value: " + value+ " is " + count, Status.DONE);
	}

	// Assertion for specific key presence in Response Received
	public void occurenceKeyCount(DocumentContext documentContext, String key, String filePath) 
	{
		try
		{
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int count1 = 0;
			while ((line = bufferedReader.readLine()) != null)
			{
				line = line.replace("{", "");
				line = line.replace("}", "");
				line = line.replace("[", "");
				line = line.replace("]", "");
				line = line.replace("\"", "");
				line = line.replace(",", "");
				line = line.replace(" ", "");
				if (!line.equalsIgnoreCase("")) 
				{
					String lin = line.split(":")[0];
					if (lin.equalsIgnoreCase(key)) 
					{
						count1++;
					}
				}
			}
			fileReader.close();
			Reporting("<b>"+key+"</b> is repeated: "+count1+" times.", Status.DONE);
		} 
		catch (IOException e) 
		{
			System.err.println(e.getMessage());
		}
	}

	public void contentPresence(DocumentContext documentContext, String jsonpath, String containsString) 
	{
		String content = String.valueOf(getJSONValue(documentContext, jsonpath));
		if (content.isEmpty() || content == null) 
		{
			Reporting("No Content Available in the path: " + jsonpath + " for Value : " + content, Status.FAIL);
		} 
		else if (content.contains(containsString)) 
		{
			Reporting(containsString + " is available in the path: " + jsonpath + " and Value is: " + content, Status.PASS);
		}
		else 
		{
			Reporting(containsString + " is not available in the path: " + jsonpath + " and Value is: " + content, Status.FAIL);
		}
	}

	public Map<String, String> getQueryParameters(String queryParameters)
	{
		Map<String, String> map = new HashMap<>();
		String strQueryParameters = dataTable.getData(queryParameters);
		String[] strQueryParameter = strQueryParameters.split(";");
		for (String singleQP : strQueryParameter) 
		{
			try
			{
				map.put(singleQP.split(":")[0].trim(), singleQP.split(":")[1].trim());
			} 
			catch (Exception e) 
			{
				System.err.println("No QueryParameters are present.");
			}
		}
		return map;
	}
	
	public Map<String, String> getoAuthParameters()
	{
		Map<String, String> map = new HashMap<>();
		String oAuthParameters = dataTable.getData("oAuthParameters");
		String[] oAuthParameter = oAuthParameters.split(";");
		for (String singleAP : oAuthParameter) 
		{
			try
			{
				map.put(singleAP.split(":")[0].trim(), singleAP.split(":")[1].trim());
			} 
			catch (Exception e) 
			{
				System.err.println("No QueryParameters are present.");
			}
		}
		return map;
	}

	public String generateQueryParameters(Map<String, String> queryParams) {
		String query = "?";
		for (Map.Entry<String, String> param : queryParams.entrySet()) {
			query = query + param.getKey() + "=" + param.getValue() + "&";
		}
		return query;
	}

	public Map<String, String> getBodyParameters(String bodyParameters) 
	{
		Map<String, String> map = new HashMap<>();
		String queryParameters = dataTable.getData(bodyParameters);
		String[] queryParameter = queryParameters.split(";");
		for (String singleQP : queryParameter) 
		{
			try
			{
				map.put(singleQP.split(":")[0].trim(), singleQP.split(":")[1].trim());
			} 
			catch (Exception e) 
			{
				System.err.println("No BodyParameters are present.");
			}
		}
		return map;
	}
	
	public Map<String, String> getBodyParameters(String bodyParameters, boolean fromDataTable) 
	{
		Map<String, String> map = new HashMap<>();
		String queryParameters = "";
		if(fromDataTable)
		{
			queryParameters = dataTable.getData(bodyParameters);
		}
		else 
		{
			queryParameters = properties.getProperty(bodyParameters);
		}
		String[] queryParameter = queryParameters.split(";");
		for (String singleQP : queryParameter) 
		{
			try
			{
				map.put(singleQP.split(":")[0].trim(), singleQP.split(":")[1].trim());
			} 
			catch (Exception e) 
			{
				System.err.println("No BodyParameters are present.");
			}
		}
		return map;
	}

	public String getAppProperties(String key) throws IOException {
		String value = "";
		try {
			FileInputStream fileInputStream = new FileInputStream("data.properties");
			Properties property = new Properties();
			property.load(fileInputStream);
			value = property.getProperty(key);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public boolean basicAssertions(Response response, int responseTime) 
	{
		if (isJSONValid(response.asString()))
		{
			if (responseTime == 0)
			{
				responseTime = Integer.parseInt(getURI("responseTime"));
			}
			assertResponseTime(response, responseTime);
			contentType(response);
			HttpStatusCodeValidation(response);
			return true;
		} 
		else
		{
			return false;
		}
	}

	public void contentType(Response response)
	{
		if (response.header("Content-Type").contains("json") || response.header("Content-Type").equalsIgnoreCase("application/json"))
		{
			Reporting("Content-Type validated successfully: " + response.header("Content-Type"), Status.PASS);
		} 
		else if (response.header("Content-Type").contains("xml") || response.header("Content-Type").equalsIgnoreCase("application/xml")) 
		{
			Reporting("Content-Type validated successfully: " + response.header("Content-Type"), Status.PASS);
		}
		else 
		{
			Reporting("Failed to validate Content-Type neither JSON nor XML. " + response.header("Content-Type"), Status.FAIL);
		}
	}

	public boolean isJSONValid(String test) {
		try {
			new org.json.JSONObject(test);
		} catch (JSONException ex) {
			try {
				new org.json.JSONArray(test);
			} catch (JSONException ex1) {
				// ExtentUtility.getTest().log(LogStatus.FAIL, "Invalid JSON response");
				return false;
			}
		}
		// ExtentUtility.getTest().log(LogStatus.PASS, "JSON response validated
		// successfully");
		return true;
	}

	// *******************Service Methods**************************//

	/*
	public Response createSalesforceObject(String objName, Map<String, String> bodyParameters, String requestFilePath,
			String responseFilePath) throws Exception {
		Response response = null;
		try {
			String API = APIURL + objName + "/";
			requestTemplatePath = templateFolder + objName + ".json";
			String requestString = generateRequestBody(requestTemplatePath, bodyParameters);
			storeJsonInFile(requestFilePath, requestString);
			if (accessToken == null)
				GetInstanceURLAndToken();
			oAuth2(instanceURL, salesforceUser, accessToken);
			response = executeAPI("POST", API, queryParams, requestFilePath, responseFilePath);
			JSONObject createTestResp = new JSONObject(response.body().print());
			String objID = createTestResp.getString("id");
			// ExtentUtility.getTest().log(LogStatus.PASS, objName + " " + objID +" Created
			// Successfully");
		}
		catch (Exception e)
		{
			System.out.println(objName + " Creation Failed");
			// ExtentUtility.getTest().log(LogStatus.FAIL, objName + " Creation Failed");
		}
		return response;
	}

	public Response getSalesforceObject(String objName, String objID, String requestFilePath, String responseFilePath)
			throws Exception
	{
		Response response = null;
		try {
			String API = APIURL + objName + "/" + objID;
			requestTemplatePath = templateFolder + objName + ".json";
			String requestString = generateRequestBody(requestTemplatePath, bodyParams);
			storeJsonInFile(requestFilePath, requestString);
			if (accessToken == null)
				GetInstanceURLAndToken();
			oAuth2(instanceURL, salesforceUser, accessToken);
			response = executeAPI("GET", API, queryParams, requestFilePath, responseFilePath);
			JSONObject createTestResp = new JSONObject(response.body().print());
			accountName = createTestResp.getString("Name");
			// ExtentUtility.getTest().log(LogStatus.PASS, objName + " " + objID +" details
			// retrived Successfully");
		}

		catch (Exception e)
		{
			System.out.println("Unable to get " + objName + " details");
			// ExtentUtility.getTest().log(LogStatus.FAIL, "Unable to get " + objName + "
			// details");
		}
		return response;
	}

	public Response updateSalesforceObjectPATCH(String objName, String objID, Map<String, String> bodyParameters,
			String requestFilePath, String responseFilePath) throws Exception {
		Response response = null;
		try {
			String API = APIURL + objName + "/" + objID;
			requestTemplatePath = templateFolder + objName + ".json";
			String requestString = generateRequestBody(requestTemplatePath, bodyParameters);
			storeJsonInFile(requestFilePath, requestString);
			if (accessToken == null)
				GetInstanceURLAndToken();
			oAuth2(instanceURL, salesforceUser, accessToken);
			response = executeAPI("PATCH", API, queryParams, requestFilePath, responseFilePath);
			// ExtentUtility.getTest().log(LogStatus.PASS, objName + " " + objID +" is
			// updated Successfully");
		}
		catch (Exception e)
		{
			System.out.println("Unable to update " + objName + " " + objID + " details");
			// ExtentUtility.getTest().log(LogStatus.FAIL, objName + " " + objID +" updation
			// Failed");
		}
		return response;
	}

	public Response updateSalesforceObjectPUT(String objName, String objID, Map<String, String> bodyParameters,
			String requestFilePath, String responseFilePath) throws Exception {
		Response response = null;
		try {
			String API = APIURL + objName + "/" + objID;
			requestTemplatePath = templateFolder + objName + ".json";
			String requestString = generateRequestBody(requestTemplatePath, bodyParameters);
			storeJsonInFile(requestFilePath, requestString);
			if (accessToken == null)
				GetInstanceURLAndToken();
			oAuth2(instanceURL, salesforceUser, accessToken);
			response = executeAPI("PUT", API, queryParams, requestFilePath, responseFilePath);
			// ExtentUtility.getTest().log(LogStatus.PASS, objName + " " + objID +" is
			// updated Successfully");
		}
		catch (Exception e)
		{
			System.out.println("Unable to update " + objName + " " + objID + " details");
			// ExtentUtility.getTest().log(LogStatus.FAIL, objName + " " + objID +" updation
			// Failed");
		}
		return response;
	}

	public Response deleteSalesforceObject(String objName, String objID, String requestFilePath,
			String responseFilePath) throws Exception {
		Response response = null;
		try {
			String API = APIURL + objName + "/" + objID;
			requestTemplatePath = templateFolder + objName + ".json";
			String requestString = generateRequestBody(requestTemplatePath, bodyParams);
			storeJsonInFile(requestFilePath, requestString);
			if (accessToken == null)
				GetInstanceURLAndToken();
			oAuth2(instanceURL, salesforceUser, accessToken);
			response = executeAPI("DELETE", API, queryParams, requestFilePath, responseFilePath);
			// ExtentUtility.getTest().log(LogStatus.PASS, objName + " " + objID + " deleted
			// Successfully");
		}
		catch (Exception e)
		{
			System.out.println("Unable to delete Account details");
			// ExtentUtility.getTest().log(LogStatus.FAIL, "Unable to delete " + objName + "
			// " + objID);
		}
		return response;
	}

	public Boolean getDeletedSalesforceObjectDetails(String objName, String objID, String requestFilePath,
			String responseFilePath) throws Exception {
		Response response = null;
		try {
			String API = APIURL + objName + "/" + objID;
			requestTemplatePath = templateFolder + objName + ".json";
			String requestString = getTemplate(requestTemplatePath);
			if (accessToken == null)
				GetInstanceURLAndToken();
			oAuth2(instanceURL, salesforceUser, accessToken);
			response = executeAPI("GET", API, requestString);
			// ExtentUtility.getTest().log(LogStatus.PASS, objName + " " + objID +" details
			// not retrived Successfully");
		}
		catch (Exception e)
		{
			
			System.out.println("Unable to get " + objName + " details");
			// ExtentUtility.getTest().log(LogStatus.PASS, "Unable to get deleted " +
			// objName + " details");
		}
		return true;
	}*/
}