//package supportLiberaries;
//
//import java.io.BufferedReader;
//
//import java.io.IOException;
//
//import java.io.InputStreamReader;
//
//import java.util.ArrayList;
//
//import java.util.List;
//
//import java.io.FileInputStream;
//
//import java.util.Iterator;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//
//import org.apache.poi.hssf.usermodel.HSSFRow;
//
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//
//import org.apache.poi.ss.usermodel.Cell;
//
//import org.apache.poi.ss.usermodel.Row;
//
//import org.apache.commons.httpclient.HttpClient;
//
//import org.apache.commons.httpclient.HttpException;
//
//import org.apache.commons.httpclient.URI;
//
//import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
//
//import org.apache.commons.httpclient.methods.GetMethod;
//
//import org.apache.commons.httpclient.methods.PostMethod;
//
//import org.apache.commons.httpclient.methods.PutMethod;
//
//import org.apache.commons.lang.StringUtils;
//
//public class RestComponents extends SUPER_Page
//
//{
//
//	public RestComponents(ScriptHelper scriptHelper)
//
//	{
//
//		super(scriptHelper);
//
//	}
//
//	public String env = properties.getProperty("Environmetns");
//
//	public String RestGET(String URL, String Headers) throws HttpException, IOException
//
//	{
//
//		HttpClient client = new HttpClient();
//
//		// client.getParams().setAuthenticationPreemptive(true);
//
//		// Enter Rest Url
//
//		URI uri = new URI(URL, false);
//
//		GetMethod method = new GetMethod(uri.getEscapedURI());
//
//		String[] Header = Headers.split(",");
//
//		for (int i = 0; i < Header.length; i++) {
//
//			String[] HeaderDetails = Header[i].split(":");
//
//			method.addRequestHeader(HeaderDetails[0], HeaderDetails[1]);
//
//		}
//
//		// method.setDoAuthentication(true);
//
//		// Get the Status number
//
//		int status = client.executeMethod(method);
//
//		System.out.println("Status:" + status);
//
//		// Get the response and print it in console
//
//		BufferedReader rd = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
//
//		String response = "";
//
//		String response1 = "";
//
//		if ((response = rd.readLine()) != null) {
//
//			System.out.println(response);
//
//			response1 = response;
//
//		}
//
//		method.releaseConnection();
//
//		return response1;
//
//	}
//
//	public String RestPut(String URL, String Headers, String ApiRequest) throws HttpException, IOException
//
//	{
//
//		HttpClient client = new HttpClient();
//
//		// client.getParams().setAuthenticationPreemptive(true);
//
//		// String ApiRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\"
//		// standalone=\"yes\"?><addTicket><accountAddressLine1>APT
//		// 26</accountAddressLine1><accountCity>OKLAHOMA
//		// CITY</accountCity><accountAlias>217836701</accountAlias><accountName>test
//		// cbot
//		// agile</accountName><accountNumber12>131217836701</accountNumber12><accountState>OK</accountState><accountVipCode>K</accountVipCode><accountZip>73107</accountZip><allServicesImpacted>true</allServicesImpacted><alternateContactList><alternateContact><activeContact>true</activeContact><alternatePhoneNumber>1234567890</alternatePhoneNumber><alternatePhoneNumberExtension>1234</alternatePhoneNumberExtension><bestTimeToCallType>MORNING</bestTimeToCallType><displayName>Alternate
//		// Contact
//		// Name1</displayName><email>acontact1@mailinator.com</email><phoneNumber>1234567890</phoneNumber><phoneNumberExtension>1234</phoneNumberExtension><preferredContactMethodType>BOTH</preferredContactMethodType></alternateContact></alternateContactList><billingMonth>JANUARY</billingMonth><custRefNumber>987654321</custRefNumber><equipmentIds>1234,5678,6543</equipmentIds><mostRecentBillIssue>false</mostRecentBillIssue><preferredContact><activeContact>true</activeContact><alternatePhoneNumber>1234567890</alternatePhoneNumber><alternatePhoneNumberExtension>1234</alternatePhoneNumberExtension><bestTimeToCallType>MORNING</bestTimeToCallType><displayName>Preferred
//		// Contact
//		// Name</displayName><email>preferred@mailinator.com</email><phoneNumber>1234567890</phoneNumber><phoneNumberExtension>1234</phoneNumberExtension><preferredContactMethodType>BOTH</preferredContactMethodType></preferredContact><questionAndAnswerList><questionAndAnswer><answerList>answer1</answerList><answerList>answer2</answerList><answerList>answer3</answerList><question>testing
//		// question and
//		// answer?</question></questionAndAnswer></questionAndAnswerList><serviceCategoryList><serviceCategory><name>Phone</name><serviceIssueList><serviceIssue><name>Unable
//		// to Receive
//		// Calls</name></serviceIssue></serviceIssueList></serviceCategory></serviceCategoryList><ticketType>BILLING_AND_PAYMENT</ticketType></addTicket>";
//
//		// Enter Rest Url
//
//		URI uri = new URI(URL, false);
//
//		PutMethod method = new PutMethod(uri.getEscapedURI());
//
//		method.addRequestHeader("Accept", "application/xml");
//
//		method.addRequestHeader("Content-Type", "application/xml");
//
//		method.setRequestEntity(new ByteArrayRequestEntity(ApiRequest.getBytes()));
//
//		String[] Header = Headers.split(",");
//
//		for (int i = 0; i < Header.length; i++) {
//
//			String[] HeaderDetails = Header[i].split(":");
//
//			method.addRequestHeader(HeaderDetails[0], HeaderDetails[1]);
//
//		}
//
//		// method.setDoAuthentication(true);
//
//		// Get the Status number
//
//		int status = client.executeMethod(method);
//
//		System.out.println("Status:" + status);
//
//		// Get the response and print it in console
//
//		BufferedReader rd = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
//
//		String response = "";
//
//		String response1 = "";
//
//		if ((response = rd.readLine()) != null) {
//
//			System.out.println(response);
//
//			response1 = response;
//
//		}
//
//		method.releaseConnection();
//
//		return response1;
//
//	}
//
//	public String RestPost(String URL, String Headers, String strApirequest) throws HttpException, IOException {
//
//		HttpClient client = new HttpClient();
//
//		// client.getParams().setAuthenticationPreemptive(true);
//
//		String ApiRequest = strApirequest;
//
//		// Enter Rest Url
//
//		/*
//		 * URI uri = new URI(URL, false);
//		 * 
//		 * PostMethod method = new PostMethod(uri.getEscapedURI());
//		 * 
//		 */
//
//		PostMethod method = new PostMethod(URL);
//
//		method.addRequestHeader("Accept", "application/xml");
//
//		method.addRequestHeader("Content-Type", "application/xml");
//
//		method.setRequestEntity(new ByteArrayRequestEntity(ApiRequest.getBytes()));
//
//		String[] Header = Headers.split(",");
//
//		for (int i = 0; i < Header.length; i++) {
//
//			String[] HeaderDetails = Header[i].split(":");
//
//			method.addRequestHeader(HeaderDetails[0], HeaderDetails[1]);
//
//		}
//
//		// method.setDoAuthentication(true);
//
//		// Get the Status number
//
//		int status = client.executeMethod(method);
//
//		System.out.println("Status:" + status);
//
//		// Get the response and print it in console
//
//		BufferedReader rd = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
//
//		String response = "";
//
//		String response1 = "";
//
//		if ((response = rd.readLine()) != null) {
//
//			System.out.println(response);
//
//			response1 = response;
//
//		}
//
//		method.releaseConnection();
//
//		return response1;
//
//	}
//
//	public String RestPost_Url(String URL, String Headers) throws HttpException, IOException {
//
//		HttpClient client = new HttpClient();
//
//		// client.getParams().setAuthenticationPreemptive(true);
//
//		// String ApiRequest = strApirequest;
//
//		// Enter Rest Url
//
//		/*
//		 * URI uri = new URI(URL, false);
//		 * 
//		 * PostMethod method = new PostMethod(uri.getEscapedURI());
//		 * 
//		 */
//
//		PostMethod method = new PostMethod(URL);
//
//		method.addRequestHeader("Accept", "application/xml");
//
//		method.addRequestHeader("Content-Type", "application/xml");
//
////                            method.setRequestEntity(new ByteArrayRequestEntity(ApiRequest.getBytes()));
//
//		String[] Header = Headers.split(",");
//
//		for (int i = 0; i < Header.length; i++) {
//
//			String[] HeaderDetails = Header[i].split(":");
//
//			method.addRequestHeader(HeaderDetails[0], HeaderDetails[1]);
//
//		}
//
//		method.setDoAuthentication(true);
//
//		// Get the Status number
//
//		int status = client.executeMethod(method);
//
//		System.out.println("Status:" + status);
//
//		// Get the response and print it in console
//
//		BufferedReader rd = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
//
//		String response = "";
//
//		String response1 = "";
//
//		if ((response = rd.readLine()) != null) {
//
//			System.out.println(response);
//
//			response1 = response;
//
//		}
//
//		method.releaseConnection();
//
//		return response1;
//
//	}
//
//	public String RestPost_json(String URL, String Headers, String strApirequest) throws HttpException, IOException {
//
//		HttpClient client = new HttpClient();
//
//		// client.getParams().setAuthenticationPreemptive(true);
//
//		String ApiRequest = strApirequest;
//
//		// Enter Rest Url
//
//		/*
//		 * URI uri = new URI(URL, false);
//		 * 
//		 * PostMethod method = new PostMethod(uri.getEscapedURI());
//		 * 
//		 */
//
//		System.out.println("POST Request: " + ApiRequest);
//
//		PostMethod method = new PostMethod(URL);
//
//		method.addRequestHeader("Accept", "application/json");
//
//		method.addRequestHeader("Content-Type", "application/json");
//
//		method.setRequestEntity(new ByteArrayRequestEntity(ApiRequest.getBytes()));
//
//		try {
//
//			String[] Header = Headers.split(",");
//
//			for (int i = 0; i < Header.length; i++) {
//
//				String[] HeaderDetails = Header[i].split(":");
//
//				method.addRequestHeader(HeaderDetails[0], HeaderDetails[1]);
//
//			}
//
//		} catch (Exception e) {
//
//			// TODO: handle exception
//
//		}
//
//		// method.setDoAuthentication(true);
//
//		// Get the Status number
//
//		int status = client.executeMethod(method);
//
//		System.out.println("Status:" + status);
//
//		// Get the response and print it in console
//
//		BufferedReader rd = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
//
//		String response = "";
//
//		String response1 = "";
//
//		if ((response = rd.readLine()) != null) {
//
//			System.out.println(response);
//
//			response1 = response;
//
//		}
//
//		method.releaseConnection();
//
//		return response1;
//
//	}
//
//	public String getTagValueFromResponse(String response, String tagName) {
//
//		String tagValue = "";
//
//		tagValue = StringUtils.substringBetween(response, "<" + tagName + ">", "</" + tagName + ">");
//
//		return tagValue;
//
//	}
//
//	public static List<List<Cell>> readExcel_Prerequisite(String strFileNamePath, String strSheetName)
//
//			throws Exception {
//
//		List<List<Cell>> sheetData = new ArrayList<List<Cell>>();
//
//		FileInputStream fis = null;
//
//		try {
//
//			fis = new FileInputStream(strFileNamePath);
//
//			HSSFWorkbook workbook = new HSSFWorkbook(fis);
//
//			HSSFSheet sheet = workbook.getSheet(strSheetName);
//
//			Iterator<Row> rows = sheet.rowIterator();
//
//			while (rows.hasNext())
//
//			{
//
//				HSSFRow row = (HSSFRow) rows.next();
//
//				Iterator<Cell> cells = row.cellIterator();
//
//				List<Cell> data = new ArrayList<Cell>();
//
//				while (cells.hasNext()) {
//
//					HSSFCell cell = (HSSFCell) cells.next();
//
//					data.add(cell);
//
//				}
//
//				sheetData.add(data);
//
//			}
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		} finally {
//
//			if (fis != null) {
//
//				fis.close();
//
//			}
//
//		}
//
//		return sheetData;
//
//	}
//
//}