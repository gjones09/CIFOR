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

package c4FileImport;

import javax.swing.table.*;
import java.io.*;
import java.util.*;
/**
*
* @author jonesg1
*/

public class FileImport_FileModelErrorRpt extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	protected static Vector<String> errorData;
	protected Vector<Object> columnNames ;
	protected String datafile;
	protected File inputDatafile;
	private static FileInputStream in;
	protected static BufferedReader br;

	public static int recordNumber;
	public static int errorRecordCounter;
	public static int totalRecordCounter;
	public static int recordSkippedCounter;
	public static int nbrFields;
	
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

	public static StringTokenizer st = null;

	private static int lineNumber;
	private static int tokenNumber;
	private static int fieldCounter;
	public static String endOfFile = "false";
	public static String errorDesc;
	
	public FileImport_FileModelErrorRpt(String f){
		initVectors();
	}

	public void initVectors() {

		String strLine;
		errorData = new Vector<String>();
		columnNames = new Vector<Object>();

		inputDatafile = FileImport_FileSelection.passedFile;

		try
		{
			in = new FileInputStream(inputDatafile);
		}
		catch (IOException ioe) {
			System.out.println("IO Error:" + ioe.getMessage());
		}

		try 
		{
			br = new BufferedReader(new InputStreamReader(in));		// buffered reader used to read the import file

			strLine = "";
			StringTokenizer st = null;
			totalRecordCounter = 0; lineNumber = 0; tokenNumber = 0; fieldCounter = 0;
		}
		finally {}

		/*
		 * Read the input file, one record at a time, to the end-of-file
		 */
		strLine = "";
		tokenNumber = 0; fieldCounter = 0;
		errorData = new Vector();

		try {
			while ((FileImport_ReadInputFile.strLine = br.readLine()) != null) 
			{
				StringTokenizer st =
					new StringTokenizer(FileImport_ReadInputFile.strLine, "|");
				
				///////////////////////////////////
			    //iterate through tokens
			    //while(st.hasMoreTokens())
			    //	{
			    		//System.out.println("Remaining Tokens : " + st.countTokens());
			    		nbrFields = st.countTokens();
			    		nbrFields--;
			    		//System.out.println("nbrFields = " + nbrFields);
			    //		System.out.println(st.nextToken());
			    //	}
			    /////////////////////////////////
				
				totalRecordCounter++;		// get count of total records in the file.
			    fieldCounter = 0;
				tokenNumber = 0;
				
			    String recordCounterString = Integer.toString(totalRecordCounter);		// Convert int recordCounter to a String
				
				tokenNumber++;
				fieldCounter++;
				
				field1 = st.nextToken();  //System.out.println("field1 = " + field1);		// Lab Accession No
				field2 = st.nextToken();  //System.out.println("field2 = " + field2);		// Submitter Specimen ID
				field3 = st.nextToken();  //System.out.println("field3 = " + field3);		// Specimen Type (S/I)
				field4 = st.nextToken();  //System.out.println("field4 = " + field4);		// Date Collected
				field5 = st.nextToken();  //System.out.println("field5 = " + field5);		// Date Received
				field6 = st.nextToken();  //System.out.println("field6 = " + field6);		// Date Reported
				field7 = st.nextToken();  //System.out.println("field7 = " + field7);		// Primary Test
				field8 = st.nextToken();  //System.out.println("field8 = " + field8);		// Organism
				field9 = st.nextToken();  //System.out.println("field9 = " + field9);		// CDC Primary Enzyme Pattern
				field10 = st.nextToken(); //System.out.println("field10 = " + field10);		// CDC Secondary Enzyme Pattern
				field11 = st.nextToken(); //System.out.println("field11 = " + field11);		// Other Result
				field12 = st.nextToken(); //System.out.println("field12 = " + field12);		// Submitter ID
				field13 = st.nextToken(); //System.out.println("field13 = " + field13);		// Submitter Name
				field14 = st.nextToken(); //System.out.println("field14 = " + field14);		// Submitter State
				field15 = st.nextToken(); //System.out.println("field15 = " + field15);		// Patient ID
				field16 = st.nextToken(); //System.out.println("field16 = " + field16);		// Patient First Name
				field17 = st.nextToken(); //System.out.println("field17 = " + field17);		// Patient Middle Name
				field18 = st.nextToken(); //System.out.println("field18 = " + field18);		// Patient Last Name
				field19 = st.nextToken(); //System.out.println("field19 = " + field19);		// Multiple Unit
				field20 = st.nextToken(); //System.out.println("field20 = " + field20);		// Street Address
				field21 = st.nextToken(); //System.out.println("field21 = " + field21);		// City
				field22 = st.nextToken(); //System.out.println("field22 = " + field22);		// State
				field23 = st.nextToken(); //System.out.println("field23 = " + field23);		// County
				field24 = st.nextToken(); //System.out.println("field24 = " + field24);		// Postal Code
				field25 = st.nextToken(); //System.out.println("field25 = " + field25);		// Postal Code+4
				field26 = st.nextToken(); //System.out.println("field26 = " + field26);		// Gender
				field27 = st.nextToken(); //System.out.println("field27 = " + field27);		// Patient DOB
				field28 = st.nextToken(); //System.out.println("field28 = " + field28);		// Patient Age	
				if (nbrFields > 28)
					{
						field29 = st.nextToken(); //System.out.println("field8 = " + field8);		// CDC Cluster Code
						field30 = st.nextToken(); //System.out.println("field9 = " + field9);		// Local Primary Enzyme Pattern
						field31 = st.nextToken(); //System.out.println("field10 = " + field10);		// Local Secondary Enzyme Pattern
						field32 = st.nextToken(); //System.out.println("field11 = " + field11);		// Region
					}
				else	
					{
						field29 = "";
						field30 = "";
						field31 = "";
						field32 = "";
					}
				recordNumber = lineNumber;
			 
				FileImport_EditErrorFields.EditInputFields();						// Edit the record
		    	if 	(FileImport_EditErrorFields.errorFlag == "true")
		    		{
						errorData.addElement(recordCounterString);
						errorData.addElement(field1);
						errorData.addElement(field4);
						errorData.addElement(field5);
						errorData.addElement(field6);
						errorData.addElement(field27);
						errorData.addElement(FileImport_EditErrorFields.errorDescription);
						errorRecordCounter++;
		    		}
			}  
			endOfFile = "true";
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount() 
		{
			return errorData.size() / getColumnCount();
		}

	public int getColumnCount()
		{
			return 7;
		}

	public String getColumnName(int col) 
	{
		switch(col) 
		{
		case 0: return "Record No.";
		case 1: return "Accession No.";
		case 2: return "Date Collected";
		case 3: return "Date Received";
		case 4: return "Date Reported";
		case 5: return "Patient DOB";
		case 6: return "Error Description";
		}
		throw new AssertionError("invalid column");
	}

	public Class getColumnClass(int columnIndex)
		{
			return String.class;
		}

	public boolean isCellEditable(int rowIndex, int columnIndex) 
		{
			return false;
		}

	public Object getValueAt(int rowIndex, int columnIndex) 
		{
			return (String)errorData.elementAt( (rowIndex * getColumnCount()) + columnIndex);
		}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
		{
			return;
		}
}