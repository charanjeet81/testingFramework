package supportLiberaries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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
				data = "";
				System.err.println("Data not Found in test data sheet. "+packageName+".xls");
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
		} catch (Exception e) 
		{
			System.err.println("Error while getting Data.");
		}
		return data;
	}
	
	private int getCellIndex(String fieldName) // Charanjeet
	{
		int coloumnIndex = 0;
		try {
			for (int count = 0; count<50; count++)
			{
				 String fetchedValue = sheet.getRow(0).getCell(count).getStringCellValue().trim();
				 if(fetchedValue.equals(fieldName))
				 {
					 if(fetchedValue.isEmpty())
					 {
						 //throw new ColumnNotFoundException();
						 System.err.println("Coloumn Not Found!!!");
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

	public void setData(String columnName, String dataToSet)
	{
		try 
		{
			FileInputStream file = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			Cell cell = null;

			int totalRows = sheet.getLastRowNum();
			for (int count = 0; count < totalRows + 1; count++) 
			{
				String fetchedValue = sheet.getRow(count).getCell(0).getStringCellValue();
				if (fetchedValue.equals(tcName)) 
				{
					int cellIndex = getCellIndex(columnName);
					if (cellIndex == 0)
					{
						System.err.println(columnName + " field is not present in the datasheet " + packageName + ".xls");
					} 
					else 
					{
						Row row = sheet.getRow(count);
						row.createCell(cellIndex).setCellValue(dataToSet);
						file.close();
						FileOutputStream outFile = new FileOutputStream(new File(path));
						workbook.write(outFile);
						outFile.close();
					}
					break;
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Set to Sheet.");
	}
}
