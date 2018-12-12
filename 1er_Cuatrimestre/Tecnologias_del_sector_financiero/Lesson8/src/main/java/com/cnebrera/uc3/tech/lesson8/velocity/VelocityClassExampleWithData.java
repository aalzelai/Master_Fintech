/*********************************************************************
This class has been automatically generated using Velocity!
Wed Dec 12 12:31:32 CET 2018
**********************************************************************/


package com.cnebrera.uc3.tech.lesson8.velocity ;


import java.util.List ;
import java.util.ArrayList ;

public class VelocityClassExampleWithData
{
	/** Attribute - myListStringValues */
	private List<String> myListStringValues ;
	
	/**
	 * Public Constructor
	 */
    public VelocityClassExampleWithData()
	{
		this.myListStringValues = new ArrayList<String>() ;
		
		// Set values from Velocity

					                this.myListStringValues.add("Hello") ;
			                this.myListStringValues.add("World!") ;
			            System.out.println("Velocity injected values!") ;
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
		final VelocityClassExampleWithData myExample = new VelocityClassExampleWithData() ;
		System.out.println(myExample.toString()) ;
	}
}
