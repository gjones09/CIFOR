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
* Copyright (C) The OpenELIS Foundation.  All Rights Reserved.
*
*/

package c4FileImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;
/**
*
* @author jonesg1
*/

/*
 * Read the input file and parse into proper fields for the edit and update
 */
public class FileImport_ReadInputFile
{
	public static Vector<?> errorData;
	protected static Vector<?> updateData;
	
	public static String endOfFile = "";
	public static String[] fields = new String[33];	// was 29 with old file
	
	private static String open;
	private static FileInputStream in;
	private static BufferedReader br;
	static String strLine;
	private static int lineNumber;
	private static int tokenNumber;
	private static int fieldCounter;
	public static int recordCounter;
	public static int recordAddedCounter;
	public static int recordChangedCounter;
	protected static File inputDatafile;
	public static int nbrFields;
	
	public static int recordNumber;
	public static String field1;		// Lab Accession No		
	public static String field2;		// Submitter Specimen ID
	public static String field3;		// Specimen Type (S/I)
	public static String field4;		// Date Collected
	public static String field5;		// Date Received
	public static String field6;		// Date Reported
	public static String field7;		// Primary Test
	public static String field8;		// Organism
	public static String field9;		// CDC Primary Enzyme Pattern
	public static String field10;		// CDC Secondary Enzyme Pattern
	public static String field11;		// Other Result
	public static String field12;		// Submitter ID
	public static String field13;		// Submitter Name
	public static String field14;		// Submitter State
	public static String field15;		// Patient ID
	public static String field16;		// Patient First Name
	public static String field17;		// Patient Middle Name
	public static String field18;		// Patient Last Name
	public static String field19;		// Multiple Unit
	public static String field20;		// Street Address
	public static String field21;		// City
	public static String field22;		// State
	public static String field23;		// County
	public static String field24;		// Postal Code
	public static String field25;		// Postal Code+4
	public static String field26;		// Gender
	public static String field27;		// Patient DOB
	public static String field28;		// Patient Age
	public static String field29;		// CDC Cluster Code
	public static String field30;		// Local Primary Enzyme Pattern
	public static String field31;		// Local Secondary Enzyme Pattern
	public static String field32;		// Region
	
	/*
	 *  Open the input file
	 */
	public static void GetTheFile() 
	{	
		System.out.println("FileImport_ReadInputFIle");
		
		inputDatafile = FileImport_FileSelection.passedFile;
		
		try
			{
			in = new FileInputStream(inputDatafile);
			}
		catch (IOException ioe) {
	          System.out.println("IO Error:" + ioe.getMessage());
	      }
	}

	/*
	 *  Open the input file with the BufferedReader
	 */
	public static void OpenTheFile()
	{		
		try 
		{
			br = new BufferedReader(new InputStreamReader(in));		// buffered reader used to read the csv file

		    strLine = "";
		    StringTokenizer st = null;
		    recordCounter = 0; lineNumber = 0; tokenNumber = 0; fieldCounter = 0;
		    recordAddedCounter = 0;
		    recordChangedCounter = 0;
		}
		finally {}}
	
	/*
	 *  Read the input file, one record at a time, to the end-of-file
	 */
	public static void ReadTheNextRecord()
	{
	    strLine = "";
	    StringTokenizer st = null;
	    tokenNumber = 0; fieldCounter = 0;

		try 
		{
	    	strLine = br.readLine();
 
		} catch (IOException e) 
			{
			e.printStackTrace();
			}
	    
	    if (strLine != null)
	    {
			lineNumber ++;
			recordCounter ++;  
			
		    st = new StringTokenizer(strLine, "|");		//break comma separated line using "|"
 
		    tokenNumber++;
		    fieldCounter++;

		    fields[fieldCounter] = st.nextToken();
		    System.out.println("FileImport_ReadInputFile fieldCounter = " + fieldCounter);
		    recordNumber = lineNumber;
		    
			field1 = st.nextToken();  		// Lab Accession No
			field2 = st.nextToken();  		// Submitter Specimen ID
			field3 = st.nextToken();  		// Specimen Type (S/I)
			field4 = st.nextToken();  		// Date Collected
			field5 = st.nextToken();  		// Date Received
			field6 = st.nextToken();  		// Date Reported
			field7 = st.nextToken();  		// Primary Test
			field8 = st.nextToken();  		// Organism
			field9 = st.nextToken();  		// CDC Primary Enzyme Pattern
			field10 = st.nextToken(); 		// CDC Secondary Enzyme Pattern
			field11 = st.nextToken(); 		// Other Result
			field12 = st.nextToken(); 		// Submitter ID
			field13 = st.nextToken(); 		// Submitter Name
			field14 = st.nextToken(); 		// Submitter State
			field15 = st.nextToken(); 		// Patient ID
			field16 = st.nextToken(); 		// Patient First Name
			field17 = st.nextToken(); 		// Patient Middle Name
			field18 = st.nextToken(); 		// Patient Last Name
			field19 = st.nextToken(); 		// Multiple Unit
			field20 = st.nextToken(); 		// Street Address
			field21 = st.nextToken(); 		// City
			field22 = st.nextToken(); 		// State
			field23 = st.nextToken(); 		// County
			field24 = st.nextToken(); 		// Postal Code
			field25 = st.nextToken(); 		// Postal Code+4
			field26 = st.nextToken(); 		// Gender
			field27 = st.nextToken(); 		// Patient DOB
			field28 = st.nextToken(); 		// Patient Age
			field29 = st.nextToken();  		// CDC Cluster Code
			field30 = st.nextToken();  		// Local Primary Enzyme Pattern
			field31 = st.nextToken(); 		// Local Secondary Enzyme Pattern
			field32 = st.nextToken(); 		// Region	  
	    }
	    else if (strLine == null) {endOfFile = "eof";};
	}	
}