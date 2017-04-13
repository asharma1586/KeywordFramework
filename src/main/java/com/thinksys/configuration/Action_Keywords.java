package com.thinksys.configuration;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.thinksys.Utilities.DatabaseUtility;
import com.thinksys.Utilities.KryptonException;


public class Action_Keywords {
	
	public String ActionKeyword;
	private String locatorType;
	private String locatorValue;
	private String data;
	private String sNo;
	public String stestcaseID;
	public Logger debugLog;
	public Logger resultLog;

	Map<String, Boolean> map=new HashMap<String, Boolean>();
	
	
	public Action_Keywords(String ActionKeyword, String locatorType, String locatorValue, String data, String sNo, String stestcaseID, Logger debugLog, Logger resultLog) {
		this.ActionKeyword=ActionKeyword;
		this.locatorType=locatorType;
		this.locatorValue=locatorValue;
		this.data=data;
		this.sNo=sNo;
		this.stestcaseID=stestcaseID;
		this.resultLog=resultLog;
		this.debugLog=debugLog;
	}
	
	
	
	public static WebDriver driver;
	boolean status=true;
	
	public boolean performAction() throws Exception
	{
		switch(ActionKeyword)
		{
		
		case "getPage":
		{
			status=getPage(data);
		}	
		break;
		
		case "enterText":
		{
			status=enterText(locatorType,locatorValue,data);
		}
		break;
		
		case "click":
		{
			status=click(locatorValue, locatorType);
		}
		break;
		
		case "launch": 
		{
			status=launch(data);
		}
		break;
		
		case "selectItemByValue":
		{
			status=selectItemByValue(locatorValue, locatorType, data);
		}
		break;
		
		case "selectItemByIndex":
		{
			
			status=selectItemByIndex(locatorValue, locatorType, data);
		}
		break;
		
		case "selectItemByVisibleText":
		{
			status=selectItemByVisibleText(locatorValue, locatorType, data);
		}
		break;
		
		case "closebrowser":
		{
			status=closebrowser();
		}
		break;
		
		case "closeallbrowsers":
		{
			status=closeallbrowsers();
		}
		break;
		
		case "clearBrowserCookies":
		{
			status=clearBrowserCookies();
		}
		break;
		
		case "refreshBrowser":
		{
			status=refreshbrowser();
		}
		break;
		
		case "goForward":
		{
			status=goforward();
		}
		break;
		
		case "goBack":
		{
			status=goback();
		}
		break;
		
		case "verifyPageProperty":
		{
			status=verifyPageproperty(data);
		}
		break;
		
		case "selectMultipleItems":
		{
			status=selectMultipleitems(locatorValue, locatorType, data);
		}
		break;
		
		case "verifyTextinpagesource":
		{
			status=verifyTextinpagesource(data);
		}
		break;
		
		case "verifyTextnotinpagesource":
		{
			status=verifyTextnotinpagesource(data);
		}
		break;
		
		case "verifytextcontained":
		{
			status=verifytextcontained(locatorValue, locatorType, data);
		}
		break;
		
		case "verifytextnotcontained":
		{
			status=verifytextnotcontained(locatorValue, locatorType, data);
		}
		break;
		
		case "verifytextonpage":
		{
			status=verifytextonpage(data);
		}
		break;
		
		case "verifytextnotonpage":
		{
			status=verifytextnotonpage(data);
		}
		break;
		
		case "verifylistitempresent":
		{
			status=verifylistitempresent(locatorValue, locatorType, data);
		}
		break;
		
		case "verifylistitemnotpresent":
		{
			status=verifylistitemnotpresent(locatorValue, locatorType, data);
		}
		break;
		
		case "verifyobjectdisplayed":
		{
			status=verifyobjectdisplayed(locatorValue, locatorType);
		}
		break;
		
		case "executedatabasequery":
		{
			status=executedatabasequery(data);
		}
		break;
		
		default:
		{
			try 
			{
				throw new KryptonException("Please enter correct Action keyword.");
			} 
			catch (NullPointerException ne)
			{
				status=false;
				debugLog.info("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+ne);
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+ne.getMessage());
			}
			catch (KryptonException ke) 
			{
				status=false;
				System.out.println(ke.getMessage());
				//ke.printStackTrace();
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
				debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			}
		}
		break;
		
		}
		return status;
	} 
	
	public boolean executedatabasequery(String data) 
	{
		try {
			
			if(data.isEmpty())
			{
				data="EMPTY";
				throw new KryptonException("Data column is empty. Please enter the query to execute.");
			}
			else
			{
				DatabaseUtility db=new DatabaseUtility();
				ResultSet resultset=db.queryExecution(data);
				ResultSetMetaData rsmd = resultset.getMetaData();
				int columnNum=rsmd.getColumnCount();
				
				while(resultset.next())
				{
					for(int i=1; i<=columnNum; i++)
					{
						if (i > 1) 
						System.out.print(",  ");
				        String columnValue = resultset.getString(i);
				        System.out.print(columnValue+" ");
					}
					System.out.println(" ");
				}
				resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			}
		} 
		
		catch(KryptonException ke)
		{
			status=false;
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			System.out.println("Error is: "+ke.getMessage());
		}
		catch(MySQLSyntaxErrorException se)
		{
			status=false;
			System.out.println("You have an error in your SQL syntax");
			debugLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+se.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"SQL syntax error.");
		}
		catch(MySQLTimeoutException te)
		{
			status=false;
			System.out.println("Timeoutexception in your SQL syntax");
			debugLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+te.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"SQL timeout error.");
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+e);
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Exception");
			debugLog.error(e);
		}
		return status;
	}
	
	
	public boolean verifyobjectdisplayed(String locatorValue, String locatorType) 
	{	
		try {
			By valueOfLocator=locator(locatorType, locatorValue);
			WebElement w= driver.findElement(valueOfLocator);
		
				if(w.isDisplayed())
				{
					System.out.println("Object is displayed.");
					resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+"Object is displayed.");
				}
			}
			catch(NoSuchElementException ne)
			{
				status=false;
				System.out.println("Object is not found.");
				debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+ne.getMessage());
				resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Object is not displayed.");
			}
			catch(NullPointerException np)
			{
				status=false;
				System.out.println("Error is: " +" "+np.getMessage());
				debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
				resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Object is not displayed.");
			}
			catch(Exception e)
			{
				status=false;
				debugLog.error(e);
				debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+e.getMessage());
				resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Object is not displayed.");
			}
			return status;
	}
	
	
	
	public boolean verifylistitempresent(String locatorValue, String locatorType, String data)
	{
		boolean a=false;
		try {
			if(data.isEmpty() ||data=="")
			{
				data="EMPTY";
				throw new KryptonException("Data column is empty. Please enter the list item needs to be check.");
			}
			By valueOfLocator=locator(locatorType, locatorValue);		
			List<WebElement> w= driver.findElements(valueOfLocator);
			
			for(int i=0; i<w.size(); i++)
			{
				String s= w.get(i).getAttribute("value");
				if(s.equalsIgnoreCase(data))
				{
					System.out.println("Specified list item is present in the list.");
					resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Specified list item is present in the list.");
					a=true;
				}
			}
			if(a==false)
			{
				status=false;
				System.out.println("Specified list item is not present in the list.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Specified list item is not present in the list.");
			}
		} 
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch(KryptonException ke)
		{
			status=false;
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			System.out.println("Error is: "+ke.getMessage());
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getStackTrace());
		}
		return status;
	}
	
	
	public boolean verifylistitemnotpresent(String locatorValue, String locatorType, String data)
	{
		boolean a=false;
		try {
			if(data.isEmpty()||data=="")
			{
				data="EMPTY";
				throw new KryptonException("Data column is empty. Please enter the list item needs to be check.");
			}
			By valueOfLocator=locator(locatorType, locatorValue);		
			List<WebElement> w= driver.findElements(valueOfLocator);
			for(int i=0; i<w.size(); i++)
			{
				String s= w.get(i).getAttribute("value");
				if(s.equalsIgnoreCase(data))
				{
					System.out.println("Specified list item is present in the list.");
					resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Specified list item is present in the list.");
					a=true;
				}
			}
			if(a==false)
			{
				System.out.println("Specified list item is not present in the list.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Specified list item is not present in the list.");
			}
		}
		catch(KryptonException ke)
		{
			status=false;
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			System.out.println("Error is: "+ke.getMessage());
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch(Exception e) 
		{
			status=false;
			debugLog.info("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getStackTrace());
		}
		return status;
	}
	
	
	public boolean verifytextnotonpage(String data)
	{
		try {
			if(data.isEmpty()||data=="")
			{
				data="EMPTY";
				throw new KryptonException("Data field is empty. Please enter text to verify on page.");
			}
			
			WebElement webelement = driver.findElement(By.tagName("body"));
			boolean e = webelement.getText().toLowerCase().contains(data.toLowerCase());
			if(e!=true)
			{
				System.out.println("Text is not present on the page.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Text is not present on the page.");
			}
			else
			{
				status=false;
				System.out.println("Text is present on the page.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Text is present on the page.");
			}
		} 
		catch(KryptonException ke)
		{
			status=false;
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			System.out.println("Error is: "+ke.getMessage());
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getStackTrace());
		}
		return status;
	}
	
	public boolean verifytextonpage(String data)
	{
		try {
			if(data.isEmpty()||data=="")
			{
				data="EMPTY";
				throw new KryptonException("Data field is empty. Please enter text to verify on page.");
			}
			WebElement webelement = driver.findElement(By.tagName("body"));
			if(webelement.getText().toLowerCase().contains(data.toLowerCase()))
			{
				System.out.println("Text is present on the page.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Text is present on the page.");
			}
			else
			{
				status=false;
				System.out.println("Text is not present on the page.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Text is not present on the page.");
			}
		}
		catch(KryptonException ke)
		{
			status=false;
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			System.out.println("Error is: "+ke.getMessage());
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getStackTrace());
		}
		return status;
	}
	
	public boolean verifytextcontained(String locatorValue, String locatorType, String data)
	{
		try {
			if(data.isEmpty())
			{
				data="EMPTY";
				throw new KryptonException("Data field is empty. Please enter text to verify.");
			}
			By valueOfLocator=locator(locatorType, locatorValue);		
			WebElement w= driver.findElement(valueOfLocator);	
			WebElement w1=driver.findElement(By.tagName("body"));

			if(w.getText().toLowerCase().contains(data.toLowerCase()))
			{
				System.out.println("Text is present in the specified test object.");
				resultLog.info("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			}
			else if(w1.getText().toLowerCase().contains(data.toLowerCase()))
			{
				System.out.println("Text is present in the page.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			}
			else 
			{
				System.out.println("Text is not present.");
			}
		} 
		catch(KryptonException ke)
		{
			status=false;
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			System.out.println("Error is: "+ke.getMessage());
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getStackTrace());
		}
		return status;
	}
	
	public boolean verifytextnotcontained(String locatorValue, String locatorType, String data)
	{
		try {
				if(data.isEmpty())
				{
					data="EMPTY";
					throw new KryptonException("Data field is empty. Please enter tet to verify.");
				}
			By valueOfLocator=locator(locatorType, locatorValue);		
			WebElement w= driver.findElement(valueOfLocator);	
			boolean e=w.getText().toLowerCase().contains(data.toLowerCase());
			if(e!=true)
			{
				System.out.println("Text is not present in the specified test object.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			}
			else
			{
				status=false;
				System.out.println("Text is present in the test object.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+"Text is present in the test object.");
			}
		} 
		catch(KryptonException ke)
		{
			status=false;
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			System.out.println("Error is: "+ke.getMessage());
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getStackTrace());
		}
		return status;
	}
	
	public boolean verifyTextinpagesource(String data)
	{
		try{
		     if(data.isEmpty())
		     {
		    	data="EMPTY";
				throw new KryptonException("Data column is blank. Please enter text to verify in page source.");
			 } 
		     if(driver.getPageSource().contains(data))
		     {
		    	 System.out.println("Text is present in page source.");
		    	 resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
		     }
		   }
			catch (KryptonException ke) 
			{
				status=false;
				System.out.println("Error is: " +ke.getMessage());
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
				debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		return status;
	}
	
	public boolean verifyTextnotinpagesource(String data)
	{
		try{
			boolean e=driver.getPageSource().contains(data);
		     if(data.isEmpty())
		     {
				throw new KryptonException("Data column is blank. Please enter text to verify in page source.");
			 } 
		     if(e!=true)
		     {
		    	 System.out.println("Specified text is not present in the page source.");
		    	 resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
		     }
		     else
		     {
		    	 System.out.println("Text is present in the page source.");
		     }
		}  
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		  catch (KryptonException ke) 
		  {
			  status=false;
			  System.out.println("Error is: " +ke.getMessage());
			  resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
				debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
		  }
		return status;
	}
	
	public boolean selectMultipleitems(String locatorValue, String locatorType, String data)
	{
		By valueOfLocator=locator(locatorType, locatorValue);		
		WebElement w= driver.findElement(valueOfLocator);	
		Select select=new Select(w);
		String data_array[]= data.split(Pattern.quote("|"));
		
		for (String temp: data_array)
		{   
	          select.selectByVisibleText(temp);
	          resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
	    }	
		return status;
	}	
	
	public boolean verifyPageproperty(String data)
	{
		try {
			if(data.isEmpty())
			{
				data="empty";
				throw new KryptonException("Data column is empty. Please enter data to verify page property.");
			}
			if(data.equals(driver.getTitle()) || data.equals(driver.getCurrentUrl()))
			{
				System.out.println("Specified page property is coming correctly as: " +data);
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			}
			/*else
			{
				System.out.println("Specified page property is incorrect.");
			}*/
		} 
		catch (KryptonException ke)
		{
			status=false;
			System.out.println(ke.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
		}
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
	}
	
	public boolean launch(String browserName) 
	{
		if(data.isEmpty())
		{
			data="empty";
			System.out.println("Data field is blank.");
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";");
		}
		
		try{
			if(!data.equalsIgnoreCase("Firefox") && !data.equalsIgnoreCase("Chrome") && !data.equalsIgnoreCase("IE"))
			{
				throw new KryptonException("Please enter correct data.");
			}
		}
		catch (KryptonException ke)
		{
			status=false;
			System.out.println(ke.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
		}
		
			 
		try {
			if(browserName.equalsIgnoreCase("Firefox"))
			{
				System.setProperty("webdriver.gecko.driver", ".\\Drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				System.out.println(browserName+" Launched Successfully");
				resultLog.info("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
				
			}
			else if(browserName.equalsIgnoreCase("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver",".\\Drivers\\chromedriver.exe");
				driver=new ChromeDriver();
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+";"+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
				System.out.println(browserName+"Launched Successfully");
			}
			else if(browserName.equalsIgnoreCase("IE"))
			{
				System.setProperty("webdriver.IE.driver",".\\Drivers\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
				System.out.println(browserName+" Launched Successfully");
			}
		} 
		catch (NoSuchElementException Ne) 
		{
			status=false;
			System.out.println(Ne.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
			
		catch (Exception e)
		{
			status=false;
			e.printStackTrace();
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
	}
	

	
	public boolean getPage(String url) 
	{
			map.put("getPage", status);
		try {
				if(url.isEmpty())
				{
					data="empty";
					throw new KryptonException("Url field is blank.");
				}
				else
				{
					driver.get(url);
					resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
					System.out.println("URL Launched Successfully");
				} 
		}
		catch (KryptonException ke) 
		{
			status=false;
			System.out.println("Error is: "+ke.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
	}
	

	public static By locator(String locatorTpye, String value) 
	{
		By by=null;
		try {
			switch (locatorTpye) {
			case "id":
				by = By.id(value);
				break;
			case "name":
				by = By.name(value);
				break;
			case "xpath":
				by = By.xpath(value);
				break;
			case "css":
				by = By.cssSelector(value);
				break;
			case "linkText":
				by = By.linkText(value);
				break;
			case "partialLinkText":
				by = By.partialLinkText(value);
				break;
			default:
				by = null;
				break;
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Element Not Found.");
		}
		return by;
	}
	
	public boolean enterText(String locatorType,String locatorValue,String data) 
	{
		boolean flagStatus=locatorValidation(locatorType, locatorValue);
		if(flagStatus==true)
		{
				By valueOfLocator=locator(locatorType, locatorValue);
				if(data.isEmpty())
				{
					data="empty";
					System.out.println("Data field is blank.");
				}
				try
				{		
					driver.findElement(valueOfLocator).sendKeys(data);
					System.out.println("Text entered successfully.");
					resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
				}	
			
			catch(NoSuchElementException Ne)
			{
				status=false;
				System.out.println("Error is: " +"No such element.");
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
				debugLog.error("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+Ne);
			}
				catch(NullPointerException np)
				{
					status=false;
					System.out.println("Error is: " +" "+np.getMessage());
					debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
					resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
				}
			catch(Exception e)
			{
				status=false;
				System.out.println(e.getMessage());
				debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
			}
		}
		return status;
	}
	
	
	public boolean locatorValidation(String locatortype,String locatorValue)
	{
		boolean flag=true;
		try
		{
			if((locatorValue.equals("")||locatorValue.equals(null))&&(locatortype.equals("")||locatortype.equals(null)))
			{
				throw new KryptonException("LocatorValue and Locatortype are empty."); 
			}
			
			if(locatorValue.equals("")||locatorValue.equals(null))
			{
				throw new KryptonException("Locator Value is empty.");
			}
			
			if(locatortype.equals("")||locatortype.equals(null))
			{
				throw new KryptonException("Locator type is empty.");
			}
		}
		catch(KryptonException ke)
		{
			flag=false;
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
		}
		return flag;
	}
	
	
	public boolean click(String locatorValue,String locatortype)
	{
			boolean flagStatus=locatorValidation(locatorType, locatorValue);
			if(flagStatus==true)
			{
				By valueOfLocator=locator(locatorType, locatorValue);		
				
				if (valueOfLocator.equals(null))
				{
					System.out.println("locatortype is wrong");
				}
				
				try {
					driver.findElement(valueOfLocator).click();
					resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
				}
				catch(NullPointerException np)
				{
					status=false;
					System.out.println("Error is: " +" "+np.getMessage());
					debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
					resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
				}
				catch(NoSuchElementException Ne)
				{
					status=false;
					System.out.println("Error is: " +" No such element.");
					resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
					debugLog.error("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+Ne);
				}
			
				catch (Exception e) 
				{
					status=false;
					e.printStackTrace();
					debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
				}
			}
			return status;
		}
		
		
	
	public boolean selectItemByValue(String locatorValue, String locatorType, String data)
	{
		boolean flagstatus=locatorValidation(locatorType, locatorValue);
		
		try {
			if(data.isEmpty())
			{
				data="empty";
				System.out.println("Data field is blank.");
				throw new KryptonException("Data field is blank.");
			}
			
			if(flagstatus==true)
			{
				By valueOfLocator=locator(locatorType, locatorValue);		
				WebElement webelement= driver.findElement(valueOfLocator);	
				Select select=new Select(webelement);
				select.selectByValue(data);
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			}
		} 
		catch(NoSuchElementException Ne)
		{
			status=false;
			debugLog.error("TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+Ne);
			System.out.println("Error is: " +"No such element.");
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (KryptonException ke) 
		{
			status=false;
			System.out.println("Error is: "+ke.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
		}
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
		
	}
	
	public boolean selectItemByIndex(String locatorValue, String locatorType, String data)
	{
        boolean flagstatus=locatorValidation(locatorType, locatorValue);
		
		try {
			if(data.isEmpty())
			{
				data="empty";
				System.out.println("Data field is blank.");
				throw new KryptonException("Data field is blank.");
			}
			
			if(flagstatus==true)
			{
			int i=Integer.parseInt(data);
			By valueOfLocator=locator(locatorType, locatorValue);
			WebElement w= driver.findElement(valueOfLocator);	
			Select select=new Select(w);
			select.selectByIndex(i);
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			}
		} 
		catch (KryptonException ke) 
		{
			status=false;
			System.out.println("Error is: "+ke.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
	}
	
	public boolean selectItemByVisibleText(String locatorValue, String locatorType, String data)
	{
		boolean flagstatus=locatorValidation(locatorType, locatorValue);
		
		try {
			if(data.isEmpty())
			{
				data="empty";
				System.out.println("Data field is blank.");
				throw new KryptonException("Data field is blank.");
			}
			
			if(flagstatus==true)
			{
				By valueOfLocator=locator(locatorType, locatorValue);
				WebElement webelement= driver.findElement(valueOfLocator);
				Select select=new Select(webelement);
				select.selectByVisibleText(data);
				resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			}
		} 
		catch (KryptonException ke) 
		{
			status=false;
			System.out.println("Error is: "+ke.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+ke.getMessage());
		}
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
	}
	

	
	public boolean closebrowser()
	{
		try 
		{
			driver.close();
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
			System.out.println("Browser Closed");
		} 
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
	}
	
	
	public boolean closeallbrowsers()
	{
		try {
			driver.quit();
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
		} 
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		System.out.println("Browsers Closed");
		return status;
	}
	
	public boolean clearBrowserCookies()
	{
		try 
		{
			driver.manage().deleteAllCookies();
			System.out.println("Browser cookies cleared.");
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
		} 
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer eception.");
		}
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
			resultLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";");
		}
		return status;
	}
	
	public boolean refreshbrowser()
	{
		try 
		{
			driver.navigate().refresh();
			System.out.println("Browser has been refreshed.");
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Browser has been refreshed.");
		} 
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer exception.");
		}
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage()); 
		}
		return status;
	}
	
	public boolean goforward()
	{
		try 
		{
			driver.navigate().forward();
			System.out.println("Navigated to the next page.");
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Navigated to the next page.");
		} 
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer exception.");
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
	}
	
	public boolean goback()
	{
		try 
		{
			driver.navigate().back();
			System.out.println("Navigated to the back page.");
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Navigated to the back page.");
		} 
		catch(NullPointerException np)
		{
			status=false;
			System.out.println("Error is: " +" "+np.getMessage());
			debugLog.error("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+np.getMessage());
			resultLog.info("TestCaseID: "+stestcaseID+" "+";"+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+" "+"Null Pointer exception.");
		}
		catch (Exception e) 
		{
			status=false;
			debugLog.info("TestCaseID: "+stestcaseID+" "+"TestStep#: "+sNo+";"+" "+"Action Keyword: "+ActionKeyword+";"+" "+"Status: "+status+";"+e.getMessage());
		}
		return status;
	}
}
