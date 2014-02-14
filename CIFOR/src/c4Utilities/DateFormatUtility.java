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

package c4Utilities;

import java.awt.Component;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;
import java.text.ParseException;
/**
*
* @author jonesg1
*/

/*
 * This is a utility program that will calculate and format dates
 */
public class DateFormatUtility {

public static final Component lastDate = null;

	public static String ftfDate = "JFormattedTextField";
	
	public static String currentString 		= "20110101";	// Current date (Today) of a date range
	public static String beginningString 	= "20110101";	// Beginning date (30 days ago, etc) of a date range
	public static String inputString 		= "20110101";	// Input date from a CSV File, GUI, Database, etc
	
    /*
     * Fields for data entry
     */
    public static JFormattedTextField formattedDate;
    public static JFormattedTextField tomorrow;
    public static JFormattedTextField tomorrow2;
    public static SimpleDateFormat tomorrow3;
    public static JFormattedTextField sqlDate;
    
    public static String currDate="12-31-2010";
    public static String beginDate="11-30-2010";

    public static Date ckDate;

    public static String ftfString = "JFormattedTextField";
    
    /*
     * Values for the fields
     */
	static String datePattern="MM-dd-yyyy";
	static String value;
	static boolean strict;
	public static String inputDateError;
	public static String inputDate;

	/*
	 * Setup the date for display in MM-dd-yyyy format 
	 */
	public static void displayDate()
	{
	    Date today;
	    SimpleDateFormat formatter;
	
	    formatter = new SimpleDateFormat("MM-dd-yyyy");
	    today = new Date();
	    ftfString = formatter.format(today);
	    ftfDate = ftfString;
	}
	
	/*
	 * Edit the input date to make sure it's valid
	 */
	public static void editDate()
	{
		inputDateError = "false";
		
		String date = inputDate;
		String dateformat = "MMddyyyy";
		ckDate = null;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
			sdf.setLenient(false);
			ckDate = sdf.parse(date);
		} catch (ParseException e) {
			inputDateError = "true";
		} catch (IllegalArgumentException e) {
			inputDateError = "true";
		}
	}
	
	/*
	 *   Setup dates for a date range comparison
	 */
	  public static void dateCompare()
	  {
		SimpleDateFormat cdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateCurrentDate;
			try
				{
					dateCurrentDate = cdf.parse(currentString);
					cdf.applyPattern("yyyy-MM-dd");
					currentString = cdf.format(dateCurrentDate);
				}
			catch (Exception e){
				e.printStackTrace();
				}
		
		SimpleDateFormat bdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateBeginningDate;
			try
				{
				dateBeginningDate = bdf.parse(beginningString);
					bdf.applyPattern("yyyy-MM-dd");
					beginningString = bdf.format(dateBeginningDate);
				}
			catch (Exception e){
				e.printStackTrace();
				}
			
		SimpleDateFormat idf = new SimpleDateFormat("MMddyyyy");
		java.util.Date dateInputDate;
			try
				{
				dateInputDate = idf.parse(inputString);
					idf.applyPattern("yyyy-MM-dd");
					inputString = idf.format(dateInputDate);
				}
			catch (Exception e){
				//System.out.println("DFU catch exception - inputString = " + inputString);
				e.printStackTrace();
				}
	  }

	  /*
	   * Calculate the date for previous month based upon today
	   */
	  public static void CalculateDate() 
	  {
		
		Date now = new Date();
	      
        java.util.Calendar c1 = java.util.Calendar.getInstance();
       
        c1.setTime(now);
        
        String currentMonth = Integer.toString (c1.get(Calendar.MONTH) + 1);
        if (currentMonth.length() == 1)
           currentMonth = "0" + currentMonth;
        
        String currentDate = Integer.toString (c1.get(java.util.Calendar.DATE));
        
        if (currentDate.length()== 1)
        	currentDate = "0" + currentDate;
        
        String cDate = currentMonth + "/" + currentDate +"/" + c1.get(java.util.Calendar.YEAR);
        c1.add (java.util.Calendar.DATE, -30);
        
        String nextMonth = Integer.toString (c1.get(Calendar.MONTH) + 1);
        if (nextMonth.length() == 1)
           nextMonth = "0" + nextMonth;
        
        String nextDate = Integer.toString (c1.get(java.util.Calendar.DATE));
        
        if (nextDate.length()== 1)
        	nextDate = "0" + nextDate;
        
        String bDate = nextMonth + "/" + nextDate +"/" + c1.get(java.util.Calendar.YEAR);

        beginDate=bDate;
        currDate=cDate;
        
	  }
	        
	/*
	 * Main section of the program
	 */
	static public void main(String[] args) 
	{
	   CalculateDate();
	}

}