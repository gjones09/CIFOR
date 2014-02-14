/*
 * The contents of this file are subject to the Mozilla Public License Version 1.1 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either
 * express or implied. See the License for the specific language governing rights and limitations under the License.
 * 
 * Redistributions of source code must retain the above License notice and all references to the OpenELIS Foundation.
 * 
 * Copyright (C) The OpenELIS Foundation. All Rights Reserved.
 * 
 */

package c4Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
*
* @author jonesg1
*/
	
/*
 * This is a utility to check input dates to see if they are valid
 */
public class DateValidator 
{

	public static String invalidDate = "";
	public static String ckDt = "";
	
	public static void validateInputDate() 
	{
		String dateformat = "MM/dd/yyyy";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
			sdf.setLenient(false);
			sdf.parse(ckDt);
			invalidDate = "";
		} catch (ParseException e) {
			invalidDate = "Y";
		} catch (IllegalArgumentException e) {
			invalidDate = "Y";
		}
	}
		
	/*
	 *  Check if this is a Leap year or Not
	 */	
	public static boolean isLeapYear(int year) {

		if ((year % 100 != 0) || (year % 400 == 0)) {
			return true;
		}
		return false;
	}
	
	/*
	 *  Check if this is a Leap year or Not
	 */
	public static void main(String args[]) {
		validateInputDate();
	}
}