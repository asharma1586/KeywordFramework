package com.thinksys.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtility {
	
	Connection con;
	public DatabaseUtility() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver"); //it will load the driver of db.
		System.out.println("Driver loaded.");
		
		//"jdbc:mysql://hostname:port/database name","user name","password"
		//if database is located in another machine then host name should be ip address of that machine.
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/selenium","root","anamika@12");
		System.out.println("Connected to MySQL DB.");
		
	}
	
	public ResultSet queryExecution(String data) throws Exception
	{
		Statement smt=con.createStatement();
		ResultSet rs=smt.executeQuery(data);
		//System.out.println(rs.toString());
		return rs;
	}
}
