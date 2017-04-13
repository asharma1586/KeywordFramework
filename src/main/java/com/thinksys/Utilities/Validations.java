package com.thinksys.Utilities;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.io.Files;

public class Validations 
{
	
	
	public boolean listFolders(String directoryName){
		boolean validationStatus=false;
		 ArrayList<String> ExpectedFolders = new ArrayList<String>();
		 ExpectedFolders.add("Global Data");
		 ExpectedFolders.add("Object Repository");
		// ExpectedFolders.add("Reports");
		 ExpectedFolders.add("Reusable");
		 ExpectedFolders.add("Test Scripts");
		 ExpectedFolders.add("Batch Execution");
		 
		ArrayList<String> availableFolds=new ArrayList<String> ();
		String availableFolders = null;
		File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File files : fList){
            System.out.println(files.getName());
            availableFolders=files.getName().toString();
            availableFolds.add(availableFolders);
        }
        
        List<Integer> comparingList = new ArrayList<Integer>();
        for (int a = 0; a < ExpectedFolders.size(); a++) {
            comparingList.add(0);

        }
        for (int counter = 0; counter < ExpectedFolders.size(); counter++) {
            if (availableFolds.contains(ExpectedFolders.get(counter))) {
                comparingList.set(counter, 1);
            }
            else
            {
            	System.out.println(ExpectedFolders.get(counter)+" is missing from the folder list..Please add the same");
            }
        }

        System.out.println(comparingList);

        if(comparingList.contains(0))
        {
        	System.out.println("Folder Structure is not correct");
        	validationStatus=false;
        }
        else
        {
        	System.out.println("All Required folders are present..Proceed with further validations");
        	validationStatus=true;
        }
        return validationStatus;
        
    }

	
	
	public boolean validFiles()
	{
		boolean validFileStatus=false;
		
		 ArrayList<String> directoriesToBeChecked = new ArrayList<String>();
		 
		 directoriesToBeChecked.add(".\\ExcelTestFiles\\Test Scripts");
		 directoriesToBeChecked.add(".\\ExcelTestFiles\\Object Repository");
			
		 List<Integer> comparingList = new ArrayList<Integer>();
		 
		 for(int a=0;a<directoriesToBeChecked.size();a++)
		 {
			 File directory = new File(directoriesToBeChecked.get(a));
		        //get all the files from a directory
			 if(directory.exists()!=false)
			 {
		        File[] fList = directory.listFiles();
		        //for (int b = 0; b < fList.length; b++) {
		           // comparingList.add(0);
		       // }
		        if(fList.length==0)
		        {
		        	//comparingList.set(0, 0);
		        	validFileStatus=false;
		        	comparingList.add(0);
		        }
		        else
		        {
		        	
		        for (File files : fList){
		            System.out.println(files.getName());
		            
		            System.out.println(fList.length);
		            for (int counter = 0; counter < fList.length; counter++) {
		                if (files.isFile()) {
		                	
		                    comparingList.add(1);
		                }
		                else
		                {
		                	comparingList.add(0);
		                }
		            }

		            System.out.println(comparingList);

		                }
		            }
		 }
			 else
			 {
				 System.out.println(directory+" Directory is missing");
				    validFileStatus=false;
		        	comparingList.add(0);
				 
			 }
		} 
		 if(comparingList.contains(0))
         {
         	System.out.println("Files Structure is not correct");
         	validFileStatus=false;
         }
         else
         {
         	System.out.println("All Required files are present..Proceed with further validations");
         	validFileStatus=true;
         }
		return validFileStatus;
		 
	}
}

