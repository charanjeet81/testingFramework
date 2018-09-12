package supportLiberaries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SUPER_Page extends ReusableLiberaries
{
	public SUPER_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		PageFactory.initElements(driver, this);
	}
	
	public void Reporting(String description, Status status)
	{
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		String callerMethod = stack[2].getMethodName();
		if (callerMethod.length() > 35)
			callerMethod = callerMethod.substring(0, 35);
		callerMethod = callerMethod.substring(0, 1).toUpperCase() + callerMethod.substring(1);
		if (status == Status.FAIL) {
			reporting.updateReport(callerMethod + "@" + stack[2].getLineNumber(), description, status);
			System.out.println(callerMethod + "@" + stack[2].getLineNumber());
		} else
			reporting.updateReport(callerMethod, description, status);
	}
	
	public boolean deviceExecution() 
	{
		return properties.getProperty("MobileExecution").contains("Y");
	}
	
	/**
	 * This method is to open URL at any instance
	 * @author Charanjeet
	 * @param URL to open
	 **/
	public void open_URL(String url)
	{
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(21, TimeUnit.SECONDS);
		Reporting(url + " is opened.", Status.DONE);
	}

	/**
	 * This method is to validate URL part, passed as a parameter
	 * @author Charanjeet
	 * @param Text to validate
	 **/
	public void validate_URL(String textToValidate) 
	{
		waitForPageToBeReady();
		driver.manage().timeouts().pageLoadTimeout(-1, TimeUnit.SECONDS);
		if (driver.getCurrentUrl().contains(textToValidate.trim()))
			Reporting(textToValidate + ", is coming as a part of URL <br/> " + driver.getCurrentUrl(), Status.DONE);
		else
			Reporting(textToValidate + ", is not coming as a part of URL " + driver.getCurrentUrl(), Status.FAIL);
	}

	/**
	 * This method is to click on the WebElement if it is displayed.
	 * @author Charanjeet
	 * @param WebElement
	 *            to Click
	 **/
	public void clickElementIfDisplayed(WebElement element) 
	{
		//waitForElementisClickable(driver, element, 9);
		try {
			if (element.isDisplayed()) {
				Reporting("Element is displyed to click.", Status.DONE);
				element.click();
				Reporting("Element is clicked.", Status.DONE);
			}
		} catch (Exception e) {
			Reporting("Element is not displayed to click.", Status.DONE);
		}
	}

	/**
	 * This method is to check placeholder
	 * 
	 * @author Charanjeet
	 * @param Placeholder to validate
	 * @param Input field for which you want to check placeholder
	 **/
	public void validate_Placeholder(String placeHolder, WebElement inputField) {
		//WaitTool.waitForElementDisplayed(driver, inputField, 18);
		if (inputField.getAttribute("placeholder").contains(placeHolder))
			Reporting(placeHolder + ", is coming as placeholder as expected.", Status.PASS);
		else
			Reporting(placeHolder + ", is coming as placeholder as expected.", Status.FAIL);
	}

	/**
	 * This method is to check title of the page
	 * @author Charanjeet
	 * @param Title to validate
	 **/
	public void validate_PageTitle(String titleToValidate) 
	{
		driver.manage().timeouts().pageLoadTimeout(-1, TimeUnit.SECONDS);
		WaitTools.waitForLoad(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.titleContains(titleToValidate));
		if (driver.getTitle().contains(titleToValidate.trim()))
			Reporting(titleToValidate + ", title is coming as page title.", Status.DONE);
		else
			Reporting(titleToValidate + ", title is not coming as page title.", Status.FAIL);
	}

	/**
	 * This method is to check heading displayed.
	 * @author Charanjeet
	 * @param Heading to validate
	 * @param Underlying html tag
	 **/
	public void validate_HeadingDisplayed(String heading, String htmlTag) {
		try {
			WebElement headinghOBJ = driver.findElement(By.xpath("(//" + htmlTag + "[contains(text(),'" + heading.trim() + "')])[1]"));
			//WaitTool.waitForElementDisplayed(driver, headinghOBJ, 18);
			if (headinghOBJ.isDisplayed())
				Reporting(heading + ", heading is displayed as expected.", Status.PASS);
		} catch (Exception e) {
			Reporting(heading + ", heading is not displayed as expected.", Status.FAIL);
		}
	}

	/**
	 * This method is to check page content, basically text.
	 * 
	 * @author Charanjeet
	 * @param Text to validate
	 * @param Underlying html tag
	 **/
	public void validate_PageContent(String textToValidate, String htmlTag) {
		try {
			WebElement textOBJ = driver.findElement(By.xpath("(//" + htmlTag + "[contains(text(),'" + textToValidate.trim() + "')][1])"));
			//WaitTool.waitForElementDisplayed(driver, textOBJ, 18);
			scrollToElement(textOBJ);
			if (textOBJ.isDisplayed())
				Reporting(textToValidate + ", text is displayed as expected.", Status.PASS);
		} catch (Exception e) {
			Reporting(textToValidate + ", text is not displayed as expected.", Status.FAIL);
		}
	}

	/**
	 * This method is to check if any page element is displayed or not
	 * @author Charanjeet
	 * @param Heading to validate
	 * @param Description about element
	 **/
	public void validate_ElementDisplayed(WebElement webElement, String elementDescription)
	{
		WaitTools.waitForElementDisplayed(driver, webElement, 21);
		try {
			if (webElement.isDisplayed())
				Reporting(elementDescription + ", is displayed as expected.", Status.PASS);
		} catch (Exception e) {
			Reporting(elementDescription + ", is not displayed as expected.", Status.FAIL);
		}
	}

	/**
	 * This method is to check if any page element is displayed or not
	 * @author Charanjeet
	 * @param Value to check
	 * @param Underlying html tag
	 **/
	public void validate_ElementDisplayed(String value, String htmlTag) 
	{
		WebElement linkWE = null;
		try {
			linkWE = driver.findElement(By.xpath("(//" + htmlTag + "[contains(text(),'" + value + "')])[1]"));
			WaitTools.waitForElementDisplayed(driver, linkWE, 18);
			scrollToElement(linkWE);
			if (linkWE.isDisplayed()) {
				Reporting("Element '" + linkWE.getText() + "' is displayed as expected.", Status.PASS);
			}
		} catch (Exception e) {
			Reporting("Element '" + linkWE.getText() + "' is not displayed as expected.", Status.FAIL);
		}
	}

	/**
	 * This method is to check dropdown content/options
	 * @author Charanjeet
	 * @param Array of options
	 * @param Webelement for select dropdown/select
	 **/
	public void validate_DropdownContent(String[] contentList, WebElement selectElement) {
		int count = 0;
		WaitTools.waitForElementDisplayed(driver, selectElement, 21);
		Select dropdown = new Select(selectElement);
		for (WebElement option : dropdown.getOptions()) {
			if (option.getText().contains(contentList[count]))
				Reporting("<u>" + contentList[count] + "</u>, option is coming as a part of state dropdown list.",
						Status.PASS);
			else
				Reporting(contentList[count] + ", option is not coming as a part of state dropdown list.", Status.FAIL);
			count++;
		}
	}

	/**
	 * This method is to check if list items are displayed or not
	 * 
	 * @author Charanjeet
	 * @param List to check
	 * @param Underlying html tag
	 **/
	public void validate_List(String[] list, String htmlTag) {
		for (String listItem : list) {
			validate_ElementDisplayed(listItem, htmlTag);
		}
	}

	/**
	 * This method is navigate back.
	 * @author Charanjeet
	 **/
	public void navigate_Back() {
		driver.navigate().back();
		driver.manage().timeouts().pageLoadTimeout(-1, TimeUnit.SECONDS);
		Reporting("Application is navigated back.", Status.DONE);
	}

	public void elementShouldNotDisplayed(WebElement webElement, String descriptions) {
		//WaitTool.waitForElementDisplayed(driver, webElement, 18);
		try {
			if (webElement.isDisplayed())
				Reporting(descriptions + ", element displayed, should not be present.", Status.FAIL);
		} catch (Exception e) {
			Reporting(descriptions + ", element not displayed, should not be present also.", Status.PASS);
		}
	}

	public void sync() // implicit wait
	{
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public void sync(int sec) // hard wait
	{
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCookies()
	{
		driver.manage().deleteAllCookies();
		sync(1);
		Reporting("Deleted browser cookies.", Status.DONE);
	}

	public void validate_ElementDisabled(WebElement element, String description) {
		if (element.getAttribute("disabled").contains("true"))
			Reporting("<u>" + description + "</u> field is disabled before making any search.", Status.DONE);
		else
			Reporting("<u>" + description + "</u> field is enabled before making any search.", Status.FAIL);
	}

	public void validateAttribute(WebElement element, String attribute, String value, String msg) {
		if (element.getAttribute(attribute).contains(value))
			Reporting(msg, Status.DONE);
		else
			Reporting(msg, Status.FAIL);
	}

	public void clickElementUsing_JSE(WebElement webelement, String description)
	{
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", webelement);
		Reporting(description + ", element is clicked using JSE.", Status.DONE);
	}

	public void clickTo(String link, String linkTag) 
	{
		WebElement linkWE = null;
		try {
			linkWE = driver.findElement(By.xpath("//" + linkTag + "[contains(text(),'" + link + "')]"));
			WaitTools.waitForElementDisplayed(driver, linkWE, 9);
			if (linkWE.isDisplayed()) {
				Reporting("Link <u>" + link + "</u> is present on the page.", Status.DONE);
				scrollToElement(linkWE);
				linkWE.click();
				Reporting("Clicked to the link <u>" + link + "</u>.", Status.DONE);
			}
		} catch (Exception e) 
		{
			Reporting("Error wihile clicking on the Link.", Status.FAIL);
		}
	}

	public void clickTo(String link, String value, int index) 
	{
		WebElement linkWE = null;
		try
		{
			linkWE = driver.findElement(By.xpath("(//" + link + "[contains(text(),'" + value + "')])[" + index + "]"));
			sync(1);
			//WaitTool.waitForElementDisplayed(driver, linkWE, 9);
			if (linkWE.isDisplayed()) {
				Reporting("Link <u>" + link + "</u> is present on the page.", Status.DONE);
				scrollToElement(linkWE);
				linkWE.click();
				Reporting("Clicked to the link <u>" + link + "</u>.", Status.DONE);
			}
		} catch (Exception e) {
			Reporting(link + " link is not available on the application.", Status.FAIL);
		}
		sync(1);
	}

	public void clickTo(String tag, String parameter, String value) {

	}

	public void checkElementExistenance(WebElement element) 
	{
		try {
			//WaitTool.waitForElementDisplayed(driver, element, 13);
			if (element.isDisplayed()) {
				Reporting("Element '" + element.getText() + "' is displayed as expected.", Status.DONE);
			}
		} catch (Exception e) {
			Reporting("Element '" + element.getText() + "' is not displayed as expected.", Status.FAIL);
		}
		sync(1);
	}

	public void selectByText(WebElement select_WE, String text) {
		//WaitTool.waitForElementDisplayed(driver, select_WE, 18);
		scrollToElement(select_WE);
		Select select = new Select(select_WE);
		select.selectByVisibleText(text);
		sync(1);
		Reporting("<u>" + text + "</u> is selected from dropdown.", Status.DONE);
	}

	public void SCROLL_PAGE(String value)
	{
		// for Scroll down the current page
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0, " + value + ")", "");
		Reporting("Page is scrolled down by " + value + " units.", Status.DONE);
		sync(2);
	}
	
	public void scrollToElement(WebElement element) 
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		sync(2);
	}

	public void focusOnElement(WebElement element) 
	{
		driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (int second = 0;; second++) {
			if (second >= 60)
				break;
			jse.executeScript("window.scrollBy(0,50)", ""); // y value '800' can be altered
			try {
				Thread.sleep(1000);
				if (element.isDisplayed())
					break;
			} catch (Exception e) {
				continue;
			}
		}
	}

	public void SCROLL_DOWN_COMPLETE() 
	{
		
	}

	public void SCROLL_UP_COMPLETE() 
	{

	}

	public void HighlightElement(WebElement element) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int iCnt = 0; iCnt < 3; iCnt++) {
			js.executeScript("arguments[0].style.border='3px dotted black'", element);
			Thread.sleep(1000 / 7);
			js.executeScript("arguments[0].style.border='3px dotted white'", element);
			Thread.sleep(1000 / 7);
			js.executeScript("arguments[0].style.border='3px dotted black'", element);
			js.executeScript("arguments[0].style.border=''", element);
		}
	}

	public static String getData(String path, String sheetName, int rowNumber, int cellNumber) // Charanjeet
	{
		String data = null;
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			data = WorkbookFactory.create(fis).getSheet(sheetName).getRow(rowNumber).getCell(cellNumber)
					.getStringCellValue();

			if (data.isEmpty()) {
				data = "Data not Found okay.";
			}
		} catch (Exception e) {
			System.err.println("Error while getting Data.");
		}
		return data;
	}

	public void setData(String path, String sheetName, String dataToSet)
	{
		int rowNumber=0; //need to place it at proper place.
		int cellNumber = 0;
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			Workbook wb = WorkbookFactory.create(fis);

			Sheet sh = wb.getSheet(sheetName);
			Row r = sh.createRow(rowNumber);
			Cell c = r.createCell(cellNumber);
			if (StringUtils.isNotBlank(dataToSet)) {
				c.setCellValue(dataToSet);
			}

			FileOutputStream fos = new FileOutputStream(new File(path));
			wb.write(fos);
			fos.flush();
			fos.close();
			rowNumber++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private String pdfContent;
//	private String filePath;
//	private File file;
//	private PDFParser parser;
//	private PDFTextStripper pdfStripper;
//	private PDDocument pdDoc;
//	private COSDocument cosDoc;


//	public String getStringPDFContent(String filePath) {
//		driver.get(filePath);
//		sync(5);
//		try {
//			file = new File(filePath);
//			parser = new PDFParser(new RandomAccessFile(file, "r"));
//			parser.parse();
//			cosDoc = parser.getDocument();
//			pdfStripper = new PDFTextStripper();
//			pdDoc = PDDocument.load(new File(filePath));
//			pdfStripper = new PDFTextStripper();
//			pdfContent = pdfStripper.getText(pdDoc);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // update for PDFBox V 2.0
//		driver.navigate().back();
//		sync(2);
//		return pdfContent;
//	}



	public JsonObject getJsonFromString(String jsonstring) {
		try {
			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(jsonstring).getAsJsonObject();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void waitForPageToBeReady() 
	{
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// This loop will rotate for 100 times to check If page Is ready after
		// every 1 second.
		// You can replace your if you wants to Increase or decrease wait time.
		for (int i = 0; i < 400; i++) 
		{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) { }
			// To check page ready state.

			if (executor.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	public void verifyLinkActive(String linkUrl)
	{
		try 
		{
			URL url = new URL(linkUrl);
			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(5000);
			httpURLConnect.connect();
			if (httpURLConnect.getResponseCode() == 200) {
				Reporting(linkUrl + " - " + httpURLConnect.getResponseMessage(), Status.PASS);
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) 
			{
				Reporting(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
						+ HttpURLConnection.HTTP_NOT_FOUND, Status.FAIL);
			}
		} catch (Exception e) { }
	}

	public void verifyBrokenLinks(String pageName) 
	{
		// List<WebElement> allPageLNKS = driver.findElements(By.tagName("a"));
		List<WebElement> allPageLNKS = driver.findElements(By.tagName("img"));
		Reporting("Total Links in the" + pageName + "are >> " + allPageLNKS.size(), Status.DONE);
		for (WebElement link : allPageLNKS) {
			String url = link.getAttribute("href");
			verifyLinkActive(url);
		}
	}

	public boolean listIteration(List<WebElement> name){
		Iterator<WebElement> nameIt = name.iterator();
		while(nameIt.hasNext()){
			String userName = nameIt.next().getText();
			try{
			driver.findElement(By.xpath("(//table[@id='view-users-table-md']//span[text()='"+userName+"']//following::a[text()='Edit'])[1]"));
			}catch(Exception e){
				Reporting("Edit link is not present for" +userName,Status.FAIL);
				return false;
			}
		}
		return true;
	}
	
	
	
	/**
	 * This method is to validate URL 
	 * @author Charanjeet
	 * @param  String-url to compare with current url
	 **/
	
	public void compare_URL(String urlToCompare) 
	{
		String url = driver.getCurrentUrl();
		if (driver.getCurrentUrl().equals(urlToCompare.trim()))
			Reporting("URL is displaying as expected. ie <br/>"+url, Status.DONE);
		else
			Reporting("URL is not displaying as expected. ie <br/>"+url, Status.FAIL);
	}
	
	void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut)  
	{
        if (fileToZip.isHidden()) 
        {
            return;
        }
        if (fileToZip.isDirectory()) 
        {
            File[] children = fileToZip.listFiles();
            for (File childFile : children) 
            {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try 
        {
        	FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
		} catch (IOException e) 
        {
			Reporting("Report Compression got failed."+e.getMessage(), Status.FAIL);
		}
    }
	
	public void zipReport(String sourceDirPath, String zipFilePath) 
	{
		try 
		{
			Path p = Files.createFile(Paths.get(zipFilePath));
		    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
		        Path pp = Paths.get(sourceDirPath);
		        Files.walk(pp)
		          .filter(path -> !Files.isDirectory(path))
		          .forEach(path -> {
		              ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
		              try {
		                  zs.putNextEntry(zipEntry);
		                  Files.copy(path, zs);
		                  zs.closeEntry();
		            } catch (IOException e) {
		                System.err.println(e);
		            }
		          });
		    }
		} catch (Exception e2) 
		{
			//Reporting("Error occured while zipping the report.", Status.FAIL);
		}
	}
}
