package com.thinksys.Reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class testresultXMLUtility 
{

	static Element TestStep;
	static Element rootElement;
	public String sNo;
	public String date;
	public String time;
	public String str;
	public String s;
	private static final String resultPlaceholder = "<!-- INSERT_RESULTS -->";
	private static final String templatePath = ".\\template\\reporter_template.html";
	
	public testresultXMLUtility(String sNo, String date, String time, String str)
	{
		this.sNo=sNo;
		this.date=date;
		this.time=time;
		this.str=str;
	}
	
	public void resultXMLUtility(ArrayList<testresultXMLUtility> al) throws IOException 
	{	 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder;
		
	  try {

		    //String reportIn = new String(Files.readAllBytes(Paths.get(templatePath)));
		  	docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElementNS("http://crunchify.com/CrunchifyCreateXMLDOM", "schema");
			doc.appendChild(rootElement);
		 	  
			
			Iterator<testresultXMLUtility> itr1=al.iterator(); 
			while(itr1.hasNext())
			{
				 testresultXMLUtility st=(testresultXMLUtility)itr1.next();
				 rootElement.appendChild(getScriptData(doc, st.sNo, st.date, st.time, st.str));
		//		 reportIn = reportIn.replaceFirst(resultPlaceholder,"<tr><td>" + st.sNo + "</td><td>" + st.date + "</td><td>" + st.time + "</td><td>" + st.str + "</td></tr>" + resultPlaceholder);
		         System.out.println(st.sNo +" testing... "+st.date); 
			 }
			
		//	 String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		//     String reportPath = "D:\\report_" + currentDate + ".html";
		 //    Files.write(Paths.get(reportPath),reportIn.getBytes(),StandardOpenOption.CREATE);
		        
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "Yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("D:\\execution.xml"));
			transformer.transform(source, result);
			System.out.println("File saved!");
			
			TransformerFactory tFactory = TransformerFactory.newInstance();

			Source xmlDoc = new StreamSource("D:\\execution.xml");
			 String outputFileName = ".\\template\\reporter_template.html";
	            OutputStream htmlFile = new FileOutputStream(outputFileName);
	            Transformer transformer1 = tFactory.newTransformer(xmlDoc);
	            transformer1.transform(xmlDoc, new StreamResult(htmlFile)); 
			 
			 
			 
			
	  }	  
	  catch (ParserConfigurationException pce) 
	  {
			pce.printStackTrace();
	  } 
	  catch (TransformerException tfe) 
	  {
			tfe.printStackTrace();
	  }
	/*  catch (IOException e ){
		  System.out.println("Error when writing report file:\n" + e.toString());
	  }*/
	
	}
	
	
	 private static Node getScriptData(Document doc, String sNo, String date, String time, String str)
	{
	        TestStep = doc.createElement("teststep");
	        TestStep.appendChild(getScriptElements(doc, TestStep, "StepNumber", sNo));
	        TestStep.appendChild(getScriptElements(doc, TestStep, "ExecutionDate", date));
	        TestStep.appendChild(getScriptElements(doc, TestStep, "ExecutionTime", time));
	        TestStep.appendChild(getScriptElements(doc, TestStep, "Status", str));
	        /*TestStep.appendChild(getScriptElements(doc, TestStep, "Remarks", remarks));
	        TestStep.appendChild(getScriptElements(doc, TestStep, "Attachments", attachment));
	        TestStep.appendChild(getScriptElements(doc, TestStep, "HtmlAttachments", htmlattachment));
	        TestStep.appendChild(getScriptElements(doc, TestStep, "ObjectHighlight", object));
	        TestStep.appendChild(getScriptElements(doc, TestStep, "StepComments", comments));
	        TestStep.appendChild(getScriptElements(doc, TestStep, "AttachmentUrl", url));*/	        
	        return TestStep;
	  }


	private static Node getScriptElements(Document doc, Element element, String name, String value) 
	{
		Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
	}
}
