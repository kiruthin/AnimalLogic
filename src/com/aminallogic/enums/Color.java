package com.aminallogic.enums;

public enum Color 
{	
	BLUE("Blue"), 
	GREEN("Green"), 
	YELLOW("Yellow"), 
	RED("Red");
	public int BlueCount   =0;
	public int GreenCount  =0;
	public int YellowCount =0;
	public int RedCount    =0;
	
	private String color;
	
	Color(String color) 
	{
		this.color = color;
		switch (color.toUpperCase()) 
		{
	    	case "BLUE":
	     		BlueCount++;
	     		break;
	    	case "GREEN":
	     		GreenCount++;
	     		break;
	    	case "YELLOW":
	     		YellowCount++;
	     		break;	     		
	    	case "RED":
	     		RedCount++;
	     		break;	 
	    	default:
	     		throw new AssertionError("Unknown operations " + this);
		}
	}
	public String color() {
	        return color;
	}
}
