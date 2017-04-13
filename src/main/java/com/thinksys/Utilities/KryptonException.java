package com.thinksys.Utilities;

public class KryptonException extends Exception{
	
	public String message;
	
	public KryptonException() {
	
	}
	
	public KryptonException(String message)
	{
		this.message=message;
		
	}
	
	@Override
	public String getMessage()
	{
		return message;
		
	}
	
	
}

