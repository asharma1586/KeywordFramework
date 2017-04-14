package com.thinksys.Utilities;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Cell;
import com.thinksys.Utilities.Constants;
import com.thinksys.Utilities.ExcelUtilities;
import com.thinksys.configuration.Action_Keywords;

public class ReadReusable 
{
	String scriptData;
	String rIteration_range;
	String sActionKeyword;
	String data = null;
	String sNo = null;
	String stestcaseID = null;
	
	public ReadReusable(String scriptData,String rIteration_range)
	{
		this.scriptData=scriptData;
		this.rIteration_range=rIteration_range;
		try 
		{
			readReusable(scriptData,rIteration_range);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
		
	public void readReusable(String scriptData, String rIteration_range) throws Exception
	{
		System.setProperty("LogFile", "TestReusable");
	    Logger debugLog = Logger.getLogger("debugLogger");
		Logger resultLog = Logger.getLogger("reportsLogger");
		
		PropertyConfigurator.configure("C:\\Users\\Anamika\\git\\KeywordDriven\\KeywordDriven_Framework\\log4j.properties");
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		
		String orlocatorType="";
		String orlocatorValue="";
		
		
		//Read Reusable Iteration
		int dashIndex=rIteration_range.indexOf("-");
		int range_value1=Integer.parseInt(rIteration_range.trim().substring(0, dashIndex));
		int range_value2=Integer.parseInt(rIteration_range.trim().substring(dashIndex+1));
		System.out.println("range_value1: "+ range_value1+" "+"range_value2: "+range_value2);
		
		//This Piece of code will Identify reusable name to be executed.
		int dotIndex=scriptData.indexOf(".");
		String reusableFilename=scriptData.substring(0,dotIndex);
		String reusableName=scriptData.substring(dotIndex+1);
		String reusablefilepath=".\\ExcelTestFiles\\Reusable\\".concat(reusableFilename+".xlsx");
		int countofReusableTestStepsrows=ExcelUtilities.setExcelFilePath(reusablefilepath, "TestSteps");
	
		
		//Find out the reusable name in reusable sheet and which rows to execute. 
		//Start and stop are row numbers to execute based on the reusable name.
		int start=0;
		int stop=0;
		
		for(int i=1;i<countofReusableTestStepsrows;i++)
		{
			if(reusableName.equals(ExcelUtilities.getCellData(i, 1)))
			{
				start=i;
			}
			else
			{
				//System.out.println("Reusable Name Not found");
			}
		}
		for(int j=start+1;j<countofReusableTestStepsrows;j++)
		{		
			if(ExcelUtilities.getCellData(j, 1).equals("")||ExcelUtilities.getCellData(j, 1).equals(null)||ExcelUtilities.getCellData(j, 1).equals(Cell.CELL_TYPE_BLANK))
			{
				if(j==countofReusableTestStepsrows-1)
				{
					stop=j;
				}
			}
			else
			{
				 stop=j-1;
				 break;
			}		
		}
		
		//Read the reusable rows from start to stop. 
		//start and stop values are captured using above code.
				
				
		for(int i=range_value1;i<=range_value2;i++)
		{
			for(int row=start;row<=stop; row++)
			{
				List<String> valuesFromSheet=ExcelUtilities.getValuesFromScript(reusablefilepath,"TestSteps",row);
				String rPageName = valuesFromSheet.get(1);
				String rLocatorName = valuesFromSheet.get(2);
				String rActionKeyword = valuesFromSheet.get(3); 
				String rData= valuesFromSheet.get(4);
				String rIteration= valuesFromSheet.get(5);
		
				System.out.println("rActionKeyword: "+rActionKeyword);
		
				if(rActionKeyword.equalsIgnoreCase("Reusable"))
				{
					System.out.println("In Reusable sheet "+scriptData+" another reusable found: "+rData+" with iteration "+rIteration);
					new ReadReusable(rData,rIteration);
					continue;
				}
		
				//Reading Reusable Test Data
				if(rData.startsWith("DP_"))
				{
					//System.out.println("Test Data Starts with DP_");
					int countofTestDataRows;
					int countofTestDataColumns;
					countofTestDataRows=ExcelUtilities.setExcelFilePath(reusablefilepath, "TestData");
					//System.out.println("countofReusableTestDataRows :"+ countofTestDataRows);
					countofTestDataColumns=ExcelUtilities.setTestDataFilePath(reusablefilepath, "TestData");
					//System.out.println("countofReusableTestDataColumns :"+ countofTestDataColumns);
			
					for(int y=0;y<countofTestDataColumns;y++)
					{
						String DataColmn = ExcelUtilities.getCellData(0, y);
						//System.out.println(rData+"--------------------"+DataColmn);
						if(rData.equals(DataColmn))
						{
							//System.out.println("Row: "+1+"Column: "+i);
							rData=ExcelUtilities.getCellData(i,y);
						}	
					}
				}
		
				//getORData
				System.out.println(rLocatorName);
				if(! rLocatorName.equalsIgnoreCase(""))
				{
					String orData[]= ExcelUtilities.getORData(Constants.repositoryPath,rPageName,rLocatorName);
					orlocatorType=orData[0];
					orlocatorValue=orData[1];
				}
					
				//Performing action
				Action_Keywords keywords=new Action_Keywords(sActionKeyword,orlocatorType,orlocatorValue,data,sNo,stestcaseID, debugLog, resultLog);
				keywords.performAction();
				System.out.println("----------------------------");
			}
		}
		
	}
}