/*********************************************************************
This class has been automatically generated using Velocity!
Wed Dec 12 12:31:09 CET 2018
**********************************************************************/


package com.cnebrera.uc3.tech.lesson8.velocity ;


import java.util.List ;
import java.util.ArrayList ;

public class VelocityClassExample
{
	/** Attribute - myListStringValues */
	private List<String> myListStringValues ;
	
	/**
	 * Public Constructor
	 */
    public VelocityClassExample()
	{
		this.myListStringValues = new ArrayList<String>() ;
		
		// Set values from Velocity

		            throw new RuntimeException("No string values were injected by Velocity") ;
			}

	/**
	 * @return myListStringValues as string
	 */ 
	public String toString()
	{
		return myListStringValues.toString() ;
	}
	
	/**
	 * @param args with the input arguments
	 */ 
	public static void main(final String[] args)
	{
		final VelocityClassExample myExample = new VelocityClassExample() ;
		System.out.println(myExample.toString()) ;
	}
}
