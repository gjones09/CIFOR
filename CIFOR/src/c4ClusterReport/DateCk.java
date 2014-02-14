/*
* The contents of this file are subject to the Mozilla Public License
* Version 1.1 (the "License"); you may not use this file except in
* compliance with the License. You may obtain a copy of the License at
* http://www.mozilla.org/MPL/ 
* 
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
* License for the specific language governing rights and limitations under
* the License.
* 
* - Redistributions of source code must retain the above License
* - notice and all references to the OpenELIS Foundation.
* 
* Copyright (C) The OpenELIS Foundation.  All Rights Reserved.
*
*/

package c4ClusterReport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import c4Reports.ClusterReportParameters;

/**
 * 
 * @author jonesg1
 */

/*
 *  Check input dates to ensure they are valid
 */

//public class RegionDateCk extends javax.swing.JFrame {
	public class DateCk
	{
	/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		
	    public		static	String			currentMo;
	    public		static	String			currentDy;
	    public		static	String			currentYr;
	    public		static	String			beginningMo;
	    public		static	String			beginningDy;
	    public		static	String			beginningYr;
	    
	    public		static	String			beginningDateString;
	    public		static	String			currentDateString;
	    public		static	String			currentString;

	    public		static	String			dateReportedString;
	    public		static	String			reportedString;
	    
	    public		static	String			goodDate;
	    public		static	String			renderColor;
	    public		static	String			excelDate;
	    
	    public		static	java.sql.Date 	newDateCurrentDate;
	    public		static	java.sql.Date 	newDateBeginningDate;

	public static void CkInputDates()
		{

		/*
		*  Get the parameter dates and set the MO, DY and YYYY for comparison to REPORTED_DATE
		*  Setup the date fields for the SQL Query
		*/
		// Set the current date and the current MO and YYYY values		  
		currentString = Parameters.stringCurrentDate;
		//System.out.println("RegionDateCk 72, stringCurrentDate = " + RegionClusterReportParameters.stringCurrentDate);
		SimpleDateFormat cdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateCurrentDate = null;
			try
				{
					dateCurrentDate = cdf.parse(currentString);
					cdf.applyPattern("yyyy-MM-dd");
					currentString = cdf.format(dateCurrentDate);
					//System.out.println("RegionDataCk 80  currentString = " + currentString);
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			newDateCurrentDate = java.sql.Date.valueOf(currentString);
			//System.out.println("RegionDateCk 119, newDateCurrentDate = " + newDateCurrentDate);
	
	        SimpleDateFormat cmo = new SimpleDateFormat("MM"); // two digit numerical representation
	        SimpleDateFormat cdy = new SimpleDateFormat("dd"); // two digit numerical representation
	        SimpleDateFormat cyr = new SimpleDateFormat("yyyy"); // four digit numerical representation  
	        
			try
			{
				currentMo = cmo.format(dateCurrentDate);
				currentDy = cdy.format(dateCurrentDate);
				currentYr = cyr.format(dateCurrentDate);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		// Set the beginning date and the beginning MO and YYYY values		
		String beginningString = Parameters.stringBeginningDate;
		//System.out.println("DynamicDateCk 109, stringBeginningDate = " + DynamicClusterParameters.stringBeginningDate);
		//System.out.println("DynamicDateCk 109, stringBeginningDate = " + beginningString);
		SimpleDateFormat bdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateBeginningDate = null;
			try
				{
				dateBeginningDate = bdf.parse(beginningString);
					bdf.applyPattern("yyyy-MM-dd");
					beginningString = bdf.format(dateBeginningDate);
					//System.out.println("RegionDataCk 113  beginningString = " + beginningString);
				}
			catch (Exception e){
				e.printStackTrace();
				}
			newDateBeginningDate = java.sql.Date.valueOf(beginningString);
			//System.out.println("RegionDateCk 119, newDateBeginningDate = " + newDateBeginningDate);
	
        SimpleDateFormat bmo = new SimpleDateFormat("MM"); 		// two digit numerical representation
        SimpleDateFormat byr = new SimpleDateFormat("yyyy"); 	// four digit numerical representation
		try
		{
			beginningMo = bmo.format(dateBeginningDate);
			beginningDy = bmo.format(dateBeginningDate);
			beginningYr = byr.format(dateBeginningDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
		
	/*
	 *   Get the parameter dates and set the MO, DY and YYYY for comparison to REPORTED_DATE
	 */      
    public static void setParameterDates()
    {
    	    {
    	    	/* Set the current date and the current MO and YYYY values */	  
    			currentDateString = Parameters.stringCurrentDate;
    			SimpleDateFormat cdf = new SimpleDateFormat("MM/dd/yyyy");
    			java.util.Date dateCurrentDate = null;
    				try
    					{
    						dateCurrentDate = cdf.parse(currentDateString);
    						cdf.applyPattern("yyyy-MM-dd");
    						currentDateString = cdf.format(dateCurrentDate);
    					}
    				catch (Exception e){
    					e.printStackTrace();
    					}
    		
    		        SimpleDateFormat cmo = new SimpleDateFormat("MM"); // two digit numerical representation
    		        SimpleDateFormat cdy = new SimpleDateFormat("dd"); // two digit numerical representation
    		        SimpleDateFormat cyr = new SimpleDateFormat("yyyy"); // four digit numerical representation  
    		        
    				try
    				{
    					currentMo = cmo.format(dateCurrentDate);
    					currentDy = cdy.format(dateCurrentDate);
    					currentYr = cyr.format(dateCurrentDate);
    				}
    				catch (Exception e)
    				{
    					e.printStackTrace();
    				}
    	    }
    }
	//////////////////////////////////////////////////////////////
    	/* Set the current date and the current MO and YYYY values */	  
    	    /*		currentDateString = RegionClusterReportParameters.stringCurrentDate;
		//System.out.println("RegionDataCk 137  currentDateString = " + currentDateString);
		
	//System.out.println("RegionDateCk 138,  currentDateString = " + currentDateString);
		
		SimpleDateFormat cdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateCurrentDate = null;
			try
				{
					dateCurrentDate = cdf.parse(currentDateString);
					cdf.applyPattern("yyyy-MM-dd");
					currentDateString = cdf.format(dateCurrentDate);
					//System.out.println("RegionDataCk 148  currentDateString = " + currentDateString);
				}
			catch (Exception e){
				e.printStackTrace();
				}
	
	        SimpleDateFormat cmo = new SimpleDateFormat("MM"); // two digit numerical representation
	        SimpleDateFormat cdy = new SimpleDateFormat("dd"); // two digit numerical representation
	        SimpleDateFormat cyr = new SimpleDateFormat("yyyy"); // four digit numerical representation  
	        
			try
			{
				currentMo = cmo.format(dateCurrentDate);
				currentDy = cdy.format(dateCurrentDate);
				currentYr = cyr.format(dateCurrentDate);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
	    }
*/		
	    
	/*
	 *  Get the date and time for the name of the Excel spreadsheet
	 */    
	public static String getDateTime() 
	{
	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
	    
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	    excelDate = formatter.format(date);
	
	    return dateFormat.format(date);
	}
	
	/*
	 *   Today's Reports Only - Check to see if the REPORTED_DATE is = to the Current Date
	 */          	    
	public static  void ckDateRange()
  	{
	  		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  	    Date convertedReportedDate = null;
	  		try 
	  		{
	  			convertedReportedDate = dateFormat.parse(dateReportedString);
	  		} 
	  			catch (ParseException e) 
	  			{
	  				e.printStackTrace();
	  			}
	  		//SimpleDateFormat rcvdDate = new SimpleDateFormat("yyyy-MM-dd"); // two digit numerical representation
	  		SimpleDateFormat rptdDate = new SimpleDateFormat("yyyy-MM-dd"); // two digit numerical representation
	  		try
	  		{
	  		reportedString = rptdDate.format(convertedReportedDate);
	  		}
	  			catch (Exception e)
		  		{
		  			e.printStackTrace();
		  		}
	  			
	  		int currentResult = reportedString.compareTo(currentDateString);

	  		if (currentResult == 0) 
	  			{
	  				goodDate = "Y";
	  				renderColor = "Y";
				}
	  		else 
	  			{
	  				goodDate = "N";
	  				renderColor = "N";
				}
	  		//System.out.println("DynamicDateCk 263, goodDate=" + goodDate + "  renderColor=" + renderColor);
	    }
	}