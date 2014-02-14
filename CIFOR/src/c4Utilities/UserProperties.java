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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import c4FileImport.FileImport_FileSelection;

/**
 *
 *  @author jonesg1
 */
public class UserProperties 
{
	
	public static void main(String[] args)
	{
		
		//Get the properties file
		Properties defaultProps = new Properties();
		FileInputStream in;
		try
			{
			in = new FileInputStream("C:/CIFOR/Properties/CIFOR_UserProperties.properties");
			
			Properties prop = new Properties();  
			prop.load(in);  
			RegionName 				= prop.getProperty("RegionName");
			PrimaryPattern			= prop.getProperty("PrimaryPattern");
			ReportPrimaryEnzyme 	= prop.getProperty("ReportHeaderPrimary");
			ReportSecondaryEnzyme 	= prop.getProperty("ReportHeaderSecondary");
			ReportPrimaryEnzymeCDC 	= prop.getProperty("ReportHeaderPrimaryCDC");	//testing
			ReportSecondaryEnzymeCDC= prop.getProperty("ReportHeaderSecondaryCDC");	//testing
			ReportPrimaryEnzymeLocal= prop.getProperty("ReportHeaderPrimaryLocal");	//testing
			ReportSecondaryEnzymeLocal= prop.getProperty("ReportHeaderSecondaryLocal");	//testing
			OnlyShowPrimaryPattern	= prop.getProperty("OnlyShowPrimaryPattern");
			
			//System.out.println("UserProperties 53, Region=" + RegionName + "  PrimaryPattern=" + PrimaryPattern
			//		+ "  OnlyShowPrimaryPattern=" + OnlyShowPrimaryPattern
			//		+ "  PrimaryEnzyme=" + ReportPrimaryEnzyme + "  SecondaryEnzyme=" + ReportSecondaryEnzyme);
			
			in.close();
			}
		catch (IOException ioe) 
			{
				JOptionPane.showMessageDialog(controllingFrame,
						"The CIFOR_UserProperties file was not be found.  Please\n" +
						"put the file in the C:\\CIFOR\\Properties\\ directory\n" +
						"or refer to the User Guide for help.\n" +
						"\n" +
						"Thank You");
				System.exit(0);
			}
	}
  	/*
  	 * Common variables
  	 */
  	public static String RegionName;
  	public static String PrimaryPattern;
  	public static String ReportPrimaryEnzyme;
  	public static String ReportSecondaryEnzyme;
  	
  	public static String ReportPrimaryEnzymeCDC;
  	public static String ReportSecondaryEnzymeCDC;
  	public static String ReportPrimaryEnzymeLocal;
  	public static String ReportSecondaryEnzymeLocal;
  	
  	public static String OnlyShowPrimaryPattern;
	public static JFrame controllingFrame;
}