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

public class DataTable {
	String path;
	String sheetName;
	Sheet sheet;
	String packageName;
	String tcName;
	int iteration;

	public DataTable(String environment, String path, int iteration) 
	{
		this.iteration = iteration;
		this.sheetName = environment;
		packageName = path.replace(".", "#").split("#")[0];
		tcName = path.replace(".", "#").split("#")[1];
		this.path = System.getProperty("user.dir") + "\\Resources\\TestDataSheets\\" + packageName + ".xls";
		try {
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
				System.err.println("Data not Found in test data sheet. " + packageName + ".xls");
			}
		} catch (Exception e) {
			System.err.println("Error while getting Data from " + packageName + ".xls");
		}
		return data;
	}

	public int getIterationCount(String testCaseToExecute) 
	{
		int coloumnIndex = 0;
		try {
			for (int count = 0; count < sheet.getPhysicalNumberOfRows(); count++) {
				String fetchedValue = sheet.getRow(count).getCell(0).getStringCellValue().trim();
				if (testCaseToExecute.trim().contains(fetchedValue)) {
					coloumnIndex++;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return coloumnIndex;
	}
	
	public int getStartingCount(String testCaseToExecute) 
	{
		int startingCount = 0;
		try 
		{
			for (int count = 0; count < sheet.getPhysicalNumberOfRows(); count++)
			{
				String fetchedValue = sheet.getRow(count).getCell(0).getStringCellValue().trim();
				if (testCaseToExecute.trim().contains(fetchedValue)) 
				{
					startingCount = count;
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return startingCount;
	}

	public int getEquivalentIntValue(String strNumber)
	{
		int intNumber = 0;
		switch (strNumber.toUpperCase()) 
		{
			case "ONE": intNumber = 1; break;
			case "TWO": intNumber = 2; break;
			case "THREE": intNumber = 3; break;
			case "FOUR": intNumber = 4; break;
			case "FIVE": intNumber = 5; break;
			case "SIX": intNumber = 6; break;
			case "SEVEN": intNumber = 7; break;
			case "EIGHT": intNumber = 8; break;
			case "NINE": intNumber = 9; break;
			case "TEN": intNumber = 10; break;
		
			default: System.err.println("Iteration exceeding 10.");
			break;
		}
		return intNumber;
	}
	
	public String getData(String field) // Charanjeet
	{
		boolean flag = false;
		String data = null;
		try
		{
			int totalRows = sheet.getLastRowNum();
			for (int count = 0; count < totalRows + 1; count++) 
			{
				if(flag)
				{
					break;
				}
				else
				{
					String fetchedTCName =  sheet.getRow(count).getCell(0).getStringCellValue();
					if (fetchedTCName.equals(tcName)) 
					{
						int cellIndex = getCellIndex(field);
						int totalCount =  getIterationCount(tcName);
						int startCount = getStartingCount(tcName);
						for (int rowCount = startCount; rowCount < totalCount + startCount; rowCount++) 
						{
							String fetchedIteration = sheet.getRow(rowCount).getCell(1).getStringCellValue();
							if(fetchedIteration.equals(String.valueOf(iteration)))
							{
								if (cellIndex == 0) 
								{
									System.err.println(field + ", field is not present in the datasheet." + packageName + ".xls");
									break;
								} 
								else 
								{
									data = getData(rowCount, cellIndex);
									flag = true;
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.toString());
			System.err.println("Error while getting Data.");
		}
		if(data==null)
		{
			System.out.println("test");
			System.out.println("test");
			throw new NullPointerException(field + ", field is not present in the datasheet." + packageName + ".xls");
		}
		return data;
	}

	private int getCellIndex(String fieldName) // Charanjeet
	{
		int coloumnIndex = 0;
		try {
			for (int count = 0; count < 50; count++) {
				String fetchedValue = sheet.getRow(0).getCell(count).getStringCellValue().trim();
				if (fetchedValue.equals(fieldName)) {
					if (fetchedValue.isEmpty()) {
//throw new ColumnNotFoundException();
						System.err.println("Coloumn Not Found!!!");
					} else {
						coloumnIndex = count;
					}
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(fieldName + " field is not found in datasheet." + e);
		}
		return coloumnIndex;
	}

	public void setData(String columnName, String dataToSet)
	{
		boolean flag = false;
		try 
		{
			FileInputStream file = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			Cell cell = null;

			int totalRows = sheet.getLastRowNum();
			for (int count = 0; count < totalRows + 1; count++) 
			{
				if(flag)
				{
					break;
				}
				else
				{
					String fetchedTCName = sheet.getRow(count).getCell(0).getStringCellValue();
					if (fetchedTCName.equals(tcName)) 
					{
						int cellIndex = getCellIndex(columnName);
						int totalCount =  getIterationCount(tcName);
						int startCount = getStartingCount(tcName);
						for (int rowCount = startCount; rowCount < startCount+totalCount; rowCount++) 
						{
							String fetchedIteration = sheet.getRow(rowCount).getCell(1).getStringCellValue();
							if(fetchedIteration.equals(String.valueOf(iteration)))
							{
								if (cellIndex == 0) 
								{
									System.err.println(columnName + " field is not present in the datasheet " + packageName + ".xls");
								} 
								else 
								{
									Row row = sheet.getRow(rowCount);
									row.createCell(cellIndex).setCellValue(dataToSet);
									file.close();
									FileOutputStream outFile = new FileOutputStream(new File(path));
									workbook.write(outFile);
									outFile.close();
									flag = true;
								    break;
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Set to Sheet.");
	}
}
