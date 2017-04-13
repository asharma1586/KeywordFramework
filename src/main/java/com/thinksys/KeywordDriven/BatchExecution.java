package com.thinksys.KeywordDriven;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thinksys.Utilities.Constants;
import com.thinksys.Utilities.ExcelUtilities;
import com.thinksys.Utilities.Validations;

public class BatchExecution {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		Constants cons=new Constants();
		
		//boolean validFolders=valid.listFolders(".\\ExcelTestFiles");
		//boolean validFilePresent=valid.validFiles();
		
		//ExcelUtilities.setExcelFilePath(cons.batchFilePath, "TestSteps");
		
		//TO get the count of rows from test script and OR we have defined below variables
		
		
				int countofBatchScriptRows=0;
				String scriptPath="";
				String testCaseID="";
				String flagStatus="";
				
				countofBatchScriptRows=ExcelUtilities.setExcelFilePath(cons.batchFilePath, "BatchFile");
				System.out.println("Count of rows in TestScript Excel: " +countofBatchScriptRows);
				
				for(int scriptRow=1; scriptRow<=countofBatchScriptRows; scriptRow++)
				{
					List<String> valuesFromBatchSheet=ExcelUtilities.getValuesFromScript(cons.batchFilePath,"BatchFile",scriptRow);
					System.out.println("value from the method is:- "+valuesFromBatchSheet.toString());
					
					scriptPath=valuesFromBatchSheet.get(0);
					testCaseID=valuesFromBatchSheet.get(1);
					flagStatus=valuesFromBatchSheet.get(2);
					
					System.out.println("Values are:- "+ scriptPath +" "+ testCaseID +" "+ flagStatus);
					
					if(flagStatus.equalsIgnoreCase("Y"))
					{
						String[] args1 = {cons.testSuiteFolder+scriptPath};
						System.out.println(args1);
						TestEngine.main(args1);
					}
					
					else
					{
						System.out.println("Skip the execution for the given Script:-"+ scriptPath );
					}				
				}
	}
}
