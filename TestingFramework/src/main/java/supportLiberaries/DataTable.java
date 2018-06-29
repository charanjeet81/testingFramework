package supportLiberaries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataTable
{
	String path ;
	String sheetName;
	Sheet sheet;
	String packageName;
	String tcName;
	
	DataTable(String environment, String path)
	{
		this.sheetName = environment;
		packageName = path.replace(".", "#").split("#")[0];
		tcName = path.replace(".", "#").split("#")[1];
		this.path = System.getProperty("user.dir")+"\\Resources\\TestDataSheets\\"+packageName+".xls";
		try
		{
			FileInputStream fis = new FileInputStream(new File(this.path));
			Workbook workbook = WorkbookFactory.create(fis);
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private String getData(int rowNumber, int cellNumber) // Charanjeet
	{
		String data = "";
		try {
			data = sheet.getRow(rowNumber).getCell(cellNumber).getStringCellValue();

			if (data.isEmpty()) {
				data = "Data not Found in test data sheet. "+packageName+".xls";
			}
		} catch (Exception e) {
			System.err.println("Error while getting Data from "+packageName+".xls");
		}
		return data;
	}
	
	public String getData(String field) // Charanjeet
	{
		String data = null;
		try {
			int totalRows = sheet.getLastRowNum();
			for (int count = 0; count<totalRows+1; count++)
			{
				 String fetchedValue = sheet.getRow(count).getCell(0).getStringCellValue();
				 if(fetchedValue.contains(tcName))
				 {
					 int cellIndex = getCellIndex(field);
					 if(cellIndex==0)
					 {
						 System.err.println(field+" field is not present in the datasheet "+packageName+".xls");
					 }
					 else
					 {
						 data = getData(count, cellIndex);
					 }
					 break;
				 }
			}

			if (data.isEmpty()) {
				data = "Data not Found okay.";
			}
		} catch (Exception e) {
			System.err.println("Error while getting Data.");
		}
		return data;
	}
	
	private int getCellIndex(String fieldName) // Charanjeet
	{
		int coloumnIndex = 0;
		try {
			for (int count = 0; count<20; count++)
			{
				 String fetchedValue = sheet.getRow(0).getCell(count).getStringCellValue();
				 if(fetchedValue.contains(fieldName))
				 {
					 if(fetchedValue.isEmpty())
					 {
						 System.out.println("Coloumn Not Found!!!");
					 }
					 else
					 {
						 coloumnIndex = count;
					 }
					 break;
				 }
			}
		} catch (Exception e) {
			System.err.println(fieldName+ " field is not found in datasheet."+e);
		}
		return coloumnIndex;
	}

	// Need to work later.
/*	public static void setData(String sheetName, String dataToSet)
	{
		String path = "C:\\Users\\465839\\Desktop\\Book2.xls";
		int rowNumber = 1;
		int cellNumber = 1;
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			Workbook wb = WorkbookFactory.create(fis);

			Sheet sh = wb.getSheet(sheetName);
			Row r = sh.createRow(rowNumber);
			Cell c = r.createCell(cellNumber);
			c.setCellValue(dataToSet);

			FileOutputStream fos = new FileOutputStream(path);
			fos.flush();
			fos.close();
			rowNumber++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
