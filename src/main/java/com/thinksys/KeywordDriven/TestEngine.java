package com.thinksys.KeywordDriven;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.thinksys.Reporting.testresultXMLUtility;
import com.thinksys.Utilities.Constants;
import com.thinksys.Utilities.ExcelUtilities;
import com.thinksys.Utilities.KryptonException;
import com.thinksys.Utilities.ReadReusable;
import com.thinksys.Utilities.Validations;
import com.thinksys.configuration.Action_Keywords;

public class TestEngine {	
	
	/**
	 * @param args
	 * @throws Exception
	 */
	
	
	static String date = "";
	static String time = "";
	static String str;
	
	
	static
	{	
		SimpleDateFormat dateformat=new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-ms");
		SimpleDateFormat datexml = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timexml = new SimpleDateFormat("HH-mm-ss");
		System.setProperty("current.date.time", dateformat.format(new Date()));
		date = datexml.format(new Date());
		time = timexml.format(new Date());
	}
	
	public static void main(String[] args) throws Exception {
		
		
		System.out.println("Welcome to Krypton 2.0");
		System.out.println("Let's Start with the validations");
		Validations valid=new Validations();
		//Validations of all the Required Folders
		boolean validFolders=valid.listFolders(".\\ExcelTestFiles");
		
		//validation of Test Script and OR
		boolean validFilePresent=valid.validFiles();
		
		//boolean validFolders=valid.checkFOlderStructure();
		if(validFolders==true && validFilePresent==true)
		{
			System.out.println("Structure is as per Requirement, Start with the execution Now");
		}
		else
		{
			System.out.println("Problem with the structure part, Stoping the Execution");
			System.exit(0);
		}
		
		//TO get the count of rows from test script and OR we have defined below variables
		
		int countofTestScriptRows=0;
		
		//Below variables are defined to fetch the values from Script,OR and GD.
		/*int countofORRows=0;
		boolean status=true;*/
		
		/*String scriptPath = ".\\ExcelTestFiles\\TestScript.xlsx";
		String repositoryPath= ".\\ExcelTestFiles\\Object Repository.xlsx";
		*/
		String sNo="";
		String stestcaseID="";
		String scriptPageName = null;
		String scriptLocatorName = null;
		String scriptActionKeyword = null;
		String scriptData= null;
		String script_reusable_Iteration=null;
		String orlocatorType="";
		String orlocatorValue="";
		
		Constants cons=new Constants();

		//condition to check weather args of Main class has data in it or not
		
				String testScriptPath=null;
				if(args.length==0)
				{
					File directory = new File(cons.scriptfolder);
					
					File[] fList = directory.listFiles();
					
					System.out.println(directory.getAbsolutePath());
					System.out.println(fList[0].toString());
					
					testScriptPath=fList[0].toString();
				}
				else
				{
					testScriptPath=args[0];
				}
		
				
				
		String valueOfTestID="";
		
		int countofTestDataRows;
		int countofTestDataColumns;
		
		ArrayList<testresultXMLUtility> al = null;
		al=new ArrayList<testresultXMLUtility>();
		
				
		//Here we are passing the Excel path and SheetName to connect with the Excel file
		countofTestScriptRows=ExcelUtilities.setExcelFilePath(testScriptPath, "TestSteps");
		System.out.println("Count of rows in TestScript Excel: " +countofTestScriptRows);
		
		int globalIterationCount=ExcelUtilities.getNumericCellData(0, 1);
		System.out.println("Global Iteration Count : " +globalIterationCount);
		
		
		  for(int itr=1;itr<=globalIterationCount;itr++){
		  //It means this loop will execute all the steps mentioned for the test case in Test Steps sheet
		    for(int sRow=2; sRow<countofTestScriptRows; sRow++){
		    	
			    countofTestScriptRows=ExcelUtilities.setExcelFilePath(testScriptPath, "TestSteps");
			    sNo=ExcelUtilities.getCellData(sRow, 0);
			    stestcaseID=ExcelUtilities.getCellData(sRow, 1);
			    scriptPageName = ExcelUtilities.getCellData(sRow, 2);
			    scriptLocatorName = ExcelUtilities.getCellData(sRow, 3);
			    scriptActionKeyword = ExcelUtilities.getCellData(sRow, 4);
			    scriptData= ExcelUtilities.getCellData(sRow, 5);
			    script_reusable_Iteration=ExcelUtilities.getCellData(sRow,6);
			    System.out.println(script_reusable_Iteration);
			
			    if(stestcaseID==null||stestcaseID.equalsIgnoreCase(""))
			    {
			    	stestcaseID=valueOfTestID;
			    }
			    valueOfTestID=stestcaseID;
			    
			    //System.out.println(stestcaseID);
			 
			    
			    System.setProperty("LogFile", stestcaseID);
			    Logger debugLog = Logger.getLogger("debugLogger");
				Logger resultLog = Logger.getLogger("reportsLogger");
				
				PropertyConfigurator.configure("C:\\Users\\Anamika\\git\\KeywordDriven\\KeywordDriven_Framework\\log4j.properties");
				System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
			    
			   
			    try {
					if(scriptActionKeyword.isEmpty()||scriptActionKeyword=="")
					{
						scriptActionKeyword="EMPTY";
						throw new KryptonException("Action Keyword is missing. Please enter Action Keyword to perform an action.");
					}
				} 
			    catch (KryptonException ke) 
			    {
					System.out.println("Action Keyword is missing. Please enter Action Keyword to perform an action.");
					resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+scriptActionKeyword+";"+" "+ke.getMessage());
					debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+scriptActionKeyword+";"+" "+ke.getMessage());
			    }
			    catch (Exception e)
			    {
			    	resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+scriptActionKeyword+";"+" "+e.getStackTrace());
			    	debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+scriptActionKeyword+";"+e.getStackTrace());
			    }
			    
			  //Reusable Execution
					if(scriptActionKeyword.equalsIgnoreCase("Reusable")){
						System.out.println("In TestEngine, Reusable Action is: "+ scriptData+" and Reuable Iteration value is:"+ script_reusable_Iteration );
						new ReadReusable(scriptData,script_reusable_Iteration);
						continue;
					}
			
					
					
					//getting scriptData from the getTestData method
					
					//if(scriptData.startsWith("DP_")){
					scriptData=ExcelUtilities.getTestData(testScriptPath,scriptData,itr);
					//}

					//getORData
					System.out.println(scriptLocatorName);
					if(! scriptLocatorName.equalsIgnoreCase("")){
						
					String orData[]= ExcelUtilities.getORData(cons.repositoryPath,scriptPageName,scriptLocatorName);
					
						orlocatorType=orData[0];
						orlocatorValue=orData[1];
					
				}
			  
			
			 			
			 //A method of class Action_Keywords
			boolean a=execute_Actions(scriptActionKeyword,orlocatorType,orlocatorValue,scriptData,sNo, stestcaseID, debugLog, resultLog);
			
			str = String.valueOf(a);
			if(str == "true"){
				str = "Pass";
			}else{
				str = "Fail";
			}
			 testresultXMLUtility val = new testresultXMLUtility(sNo, date, time, str);
			 al.add(val);
			
			int length = al.size();
		
		    if(length == countofTestScriptRows - 2)
		    {
		    	testresultXMLUtility xml=new testresultXMLUtility(sNo, date, time, str);	
				 xml.resultXMLUtility(al);
		    }
		    
		    
		
		    }
	 }		
}
		
	
	private static boolean execute_Actions(String sActionKeyword,String orlocatorType,String orlocatorValue, String data,String sNo, String stestcaseID, Logger debugLog, Logger resultLog) throws Exception 
	{
		Action_Keywords keywords=new Action_Keywords(sActionKeyword,orlocatorType,orlocatorValue,data,sNo,stestcaseID, debugLog, resultLog);
		boolean value=keywords.performAction();
		return value;
	}
}

