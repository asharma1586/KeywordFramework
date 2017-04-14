package com.thinksys.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities extends Constants 
{
	
	private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    static String valOfORPageName=null;

//This method is to set the File path and to open the Excel file
//Pass Excel Path and SheetName as Arguments to this method. It returns the number of rows in the sheet.
    
    public static int setExcelFilePath(String Path,String SheetName)    
    {
    	FileInputStream ExcelFile = null;
		try 
		{
			ExcelFile = new FileInputStream(Path);
		} 
		catch (FileNotFoundException e2) 
		{	
			e2.printStackTrace();
			System.out.println("Issues with Excel File: "+ Path+". Please check if this file is readable.");
		}
        int iRowNum=0;
        //System.out.println("Able to access Excel file");
        try 
        {
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} 
        catch (IOException e1) 
        {
			e1.printStackTrace();
			System.out.println("Issues with Excel file: "+Path+". Not able to read it as excel file.");
		}
        ExcelWSheet = ExcelWBook.getSheet(SheetName);
       iRowNum=ExcelWSheet.getLastRowNum();
        return iRowNum;
}

public static int setTestDataFilePath(String PathofTS,String SheetNameofTestData) throws Exception 
{
    FileInputStream ExcelFile = new FileInputStream(PathofTS);
    int colCount=0;
    ExcelWBook = new XSSFWorkbook(ExcelFile);
    ExcelWSheet = ExcelWBook.getSheet(SheetNameofTestData);
    Iterator<Row> rowIterator = ExcelWSheet.rowIterator();
    if (rowIterator.hasNext())
    {
    	Row headerRow = (Row) rowIterator.next();
        //get the number of cells in the header row
        colCount = headerRow.getPhysicalNumberOfCells();
    }   
    return colCount;
}

public static String getCellData(int RowNum, int ColNum)
{	
	String CellData="";
	Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
	try
	{
		if(Cell==null)
		{
    	  CellData="";
    	}  
    	else
    	{
    	  Cell.setCellType(Cell.CELL_TYPE_STRING);
    	  CellData = Cell.getStringCellValue();
    	}  
     }
     catch(Exception e)
     {
    	 CellData="";
    	 e.printStackTrace();
     }
     return CellData;
}

public static int getNumericCellData(int RowNum, int ColNum)
{	
	int CellData=1;
	Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);	
	if(Cell==null)
	{
		CellData=1;
	}
	else
	{
      try
      {
    	  Cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    	  CellData = (int) Cell.getNumericCellValue();
      }
      catch(Exception e)
      {
    	  CellData=1;
    	  e.printStackTrace();
      }
      
	}
	return CellData;
}

public static int getColCount(String PathofTS,String sheetName,int rowNum) throws Exception 
{
    FileInputStream ExcelFile = new FileInputStream(PathofTS);
    int colCount=0;
    ExcelWBook = new XSSFWorkbook(ExcelFile);
    ExcelWSheet = ExcelWBook.getSheet(sheetName);
    Iterator<Row> rowIterator = ExcelWSheet.rowIterator();
    colCount=ExcelWSheet.getRow(rowNum-1).getLastCellNum();  
    return colCount;
}


public static List<String> getValuesFromScript(String scriptPath,String sheetName,int scriptRow) throws Exception
{
	ArrayList<String> scriptVals=new ArrayList<String> ();
	int rowCount=ExcelUtilities.setExcelFilePath(scriptPath, sheetName);
	int colCount=getColCount(scriptPath, sheetName,scriptRow);
	for(int cols=1;cols<colCount;cols++)
	{
		scriptVals.add(ExcelUtilities.getCellData(scriptRow, cols));
	}
	System.out.println(scriptVals.toString());
	return scriptVals;
}

//to get the Test Data

public static String getTestData(String scriptPath,String scriptData,int iterationValue) throws Exception
{
	int countofTestDataRows;
	int countofTestDataColumns;
	//Reading TestData
	if(scriptData.startsWith("DP_"))
	{
		countofTestDataRows=ExcelUtilities.setExcelFilePath(scriptPath, "TestData");
		countofTestDataColumns=ExcelUtilities.getColCount(scriptPath, "TestData",countofTestDataRows);
		for(int i=0;i<countofTestDataColumns;i++)
		{
			String DataColmn = ExcelUtilities.getCellData(0, i);
			if(scriptData.equals(DataColmn))
			{
				scriptData=ExcelUtilities.getCellData(iterationValue,i);
			}	
		}
	}
	if(scriptData.startsWith("GD_"))
	{
		int countofGDRows=ExcelUtilities.setExcelFilePath(GDPath, "Global_Data");
		for(int gdRow=1; gdRow<=countofGDRows; gdRow++)
		{
			String gdData= ExcelUtilities.getCellData(gdRow,0);
			if(scriptData.equalsIgnoreCase(gdData))
			{
				scriptData=ExcelUtilities.getCellData(gdRow, 1);	
				break;
			}
		}
	}
	else
	{
		System.out.println("Value of SCriptData:"+scriptData);
	}
	return scriptData;
}

public static String[] getORData(String repoPath,String orPageName,String scriptLocatorName) throws Exception
{
	String[] orVals=new String[2];
	System.out.println(valOfORPageName);
	if(orPageName==null||orPageName.equalsIgnoreCase(""))
	{
		orPageName=valOfORPageName;
	}
	
	int countofORRows=ExcelUtilities.setExcelFilePath(repoPath, orPageName);
	for(int orRow=1; orRow<=countofORRows; orRow++)
	{
		String orLocatorName= ExcelUtilities.getCellData(orRow,1);
		if(scriptLocatorName.equalsIgnoreCase(orLocatorName))
		{
			orVals[0]=ExcelUtilities.getCellData(orRow, 2);
			orVals[1]=ExcelUtilities.getCellData(orRow, 3);
			break;
		}
	}
	valOfORPageName=orPageName; 
	return orVals;
}

}
