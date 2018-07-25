package supportLiberaries;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SoapBase extends SUPER_Page
{
	String env = properties.getProperty("Environment");
	private String fileName = System.getProperty("user.dir") + "\\Resources\\TestDataSheets\\SOAP_WSDL.xls";
	public SoapBase(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}

	public SOAPMessage soapCall(String requestFolder, String requestName) throws Exception
	{
		// Create SOAP Connection
		SOAPMessage soapResponse = null;
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Get API URL.
		HashMap<String, String> hmap = readExcel(requestName);
		String url = hmap.get("url").toString();

		// Get API request from the text file
		String request = getAPIRequest(requestFolder, requestName);

		// Create Soap Request and get the response
		SOAPMessage soapReq = createSOAPRequest(request, requestName);

		try 
		{
			soapResponse = soapConnection.call(soapReq, url);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		if (soapResponse.getSOAPBody().getFault() != null)
		{
			Reporting("Response: <font color = 'red'>" + getSOAPResponseString(soapResponse) + "</font>", Status.DONE);
		}

		// Printing the SOAP Response
		System.out.println("Response SOAP Message =========> ");
		soapResponse.writeTo(System.out);
		soapConnection.close();
		System.out.println(soapResponse.toString());
		return soapResponse;
	}

	// Create Soap Request
	private SOAPMessage createSOAPRequest(String request, String Api) throws Exception
	{
		// Get Api Mime Headers from the sheet
		HashMap<String, String> hmap = readExcel(Api);
		String MimeHeaders;
		try 
		{
			MimeHeaders = hmap.get("MimeHeaders").toString();
		} catch (Exception e) {
			MimeHeaders = "";
		}
		String HeaderUrl = MimeHeaders;

		// Create soapMessage
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		InputStream is = new ByteArrayInputStream(request.getBytes());
		soapMessage = MessageFactory.newInstance().createMessage(null, is);

		// Add MimeHeader to the Soap Action
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", HeaderUrl);

		// Print the request Soap Message
		System.out.println("Request SOAP Message ========> ");
		soapMessage.writeTo(System.out);
		return soapMessage;
	}

	public String getAPIRequest(String requestFolder, String requestAPI) throws FileNotFoundException
	{
		String soapXMLRequestPath = requestFolder + "\\" + requestAPI+".xml";
		String strrequest = null;
		StringBuffer request = new StringBuffer();
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(soapXMLRequestPath));
			// as long as there are lines in the file, print them
			while (true)
			{
				String line = reader.readLine();
				if (line != null)
				{
					request.append(line);
				}
				else
				{
					break;
				}
			}
			reader.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		switch (requestAPI.toUpperCase())
		{
			case "ADD":
				String valueA = dataTable.getData("ValueA");
				String valueB = dataTable.getData("ValueB");
				String addingTwoNumbers = StringUtils.replace(request.toString(), "$valueA$", valueA);
				strrequest = StringUtils.replace(addingTwoNumbers, "$valueB$", valueB);
				break;
	
			default:
				System.err.println("Unhandled API Name!");
		}
		return strrequest;
	}

	// Method to read the excel
	public HashMap<String, String> readExcel(String apiname) throws Exception
	{
		List<List<Cell>> sheetData = new ArrayList<List<Cell>>();
		FileInputStream fis = null;
		try 
		{
			fis = new FileInputStream(fileName);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheet("WSDL");
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext())
			{
				HSSFRow row = (HSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				List<Cell> data = new ArrayList<Cell>();
				while (cells.hasNext())
				{
					HSSFCell cell = (HSSFCell) cells.next();
					data.add(cell);
				}
				sheetData.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		HashMap<String, String> hMap = showExelData(sheetData, apiname);
		return hMap;
	}

	// function to return the Hashmap containing the URL/WSDL/APINAME
	private HashMap<String, String> showExelData(List<List<Cell>> sheetData, String apiname)
	{
		HashMap<String, String> hMap = new HashMap<String, String>();
		for (int i = 0; i < sheetData.size(); i++) 
		{
			List<Cell> list = (List<Cell>) sheetData.get(i);
			for (int j = 0; j < list.size(); j++)
			{
				HSSFCell cell = (HSSFCell) list.get(j);
				String apiname2 = cell.getRichStringCellValue().getString();
				if (apiname2.equals(apiname))
				{
					HSSFCell cell2 = (HSSFCell) list.get(j + 1);
					String url = cell2.getRichStringCellValue().getString();
					hMap.put("url", url);
					hMap.put("api", apiname);
					if(!(list.size()==2))
					{
						HSSFCell cell3 = (HSSFCell) list.get(j + 2);
						String MimeHeader = cell3.getRichStringCellValue().getString();
						hMap.put("MimeHeaders", MimeHeader);
					}
				}
			}
			System.out.println("");
		}
		return hMap;
	}

	public String getTagValueFromResponse(SOAPMessage response, String TagName) throws SOAPException
	{
		// Get the Tag Value Name
		SOAPBody soapBody = response.getSOAPBody();
		String TagValue = soapBody.getElementsByTagName(TagName).item(0).getTextContent();
		return TagValue;
	}

	public String getSOAPResponseString(SOAPMessage soapResponse) throws Exception
	{
		StringWriter sw = new StringWriter();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		StreamResult result = new StreamResult(sw);
		transformer.transform(sourceContent, result);
		return sw.toString();
	}

	public String getTagsValueFromResponse(SOAPMessage response, String TagName, int i) throws SOAPException
	{
		// Get the Tag Value Name
		SOAPBody soapBody = response.getSOAPBody();
		String TagValue = soapBody.getElementsByTagName(TagName).item(i - 1).getTextContent();
		return TagValue;
	}

	public void createNewAccount_CBMA(String accountNumber)
	{
		try {
			RestComponents RestComponents = new RestComponents(scriptHelper);
			String RestResponse = "";
			String cableServiceCode = dataTable.getData("ServiceCodes_Cable");
			String dataServiceCode = dataTable.getData("ServiceCodes_Data");
			String voiceServiceCode = dataTable.getData("ServiceCodes_Voice");

			Reporting("Data Service Codes: " + dataServiceCode, Status.DONE);
			Reporting("Cable Service Codes: " + cableServiceCode, Status.DONE);
			Reporting("Telephony Service Codes: " + voiceServiceCode, Status.DONE);

			// Create House Record
			SOAPMessage response = soapCall("requestFolder", "CreateHouseRecordInput");

			// Get the House Number from the response and put the same in excel
			String HouseNumber = getTagValueFromResponse(response, "HouseNumber");
			Reporting("House Number: " + HouseNumber, Status.DONE);
			dataTable.setData("HouseNumber", HouseNumber);

			// Running POST Call
				RestResponse = RestComponents.RestPost_json(
						"https://bis.qa.cox.com/ws/house/v1/networkelements/create?clientId=CBVMUSER", null,
						"{\"siteId\": \"" + dataTable.getData("SiteId") + "\",\"houseNumber\": \"" + HouseNumber
						+ "\",\"networkElement\": {\"centralOfficeFacilityId\": \"MACMTC\",\"networkElementName\": \"CIRCUIT\"}}");

			// Add Customer and create account
			SOAPMessage responseAddCustomer = soapCall("requestFolder", "AddCustomer");

			// Get the Account Number from the response and put the same in excel
			String strAccountNumber = getTagValueFromResponse(responseAddCustomer, "AccountNumber");
			Reporting("Account Number: " + strAccountNumber, Status.DONE);
			System.out.println("<<<<< Account Created >>>>>");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void addTwoNumbers()
	{
		
	}
}