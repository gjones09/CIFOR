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

import c4DataBase.DBUpdate;
/**
*
* @author jonesg1
*/

public class FileImport_FileModelUpdateRpt extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static Vector<String> updateData;
	protected Vector<Object> columnNames ;
	protected String datafile;
	protected File inputDatafile;
	private static FileInputStream in;
	protected static BufferedReader br;

	public static int recordNumber;
	public static String field1;		// Lab Accession No		
	public static String field2;		// Submitter Specimen ID
	public static String field3;		// Specimen Source
	public static String field4;		// Date Collected
	public static String field5;		// Date Received
	public static String field6;		// Date Reported
	public static String field7;		// Primary Test
	public static String field8;		// Organism
	public static String field9;		// Primary Enzyme Pattern
	public static String field10;		// Secondary Enzyme Pattern
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
	static int recordCounter;
	public static int nbrFields;

	public static String endOfFile = "false";
	
	public static String errorDesc = "This record has some errors.";

	public FileImport_FileModelUpdateRpt(String f){
		initVectors();
	}

	public void initVectors() {

		String strLine;
		updateData = new Vector<String>();
		columnNames = new Vector<Object>();

		inputDatafile = FileImport_FileSelection.passedFile;

		try
		{
			in = new FileInputStream(inputDatafile);
		}
		catch (IOException ioe) {
			System.out.println("IO Error:" + ioe.getMessage());
		}
		
		/*
		 *  Open the database and read the input file
		 */
		DBUpdate.DataBaseConnection();				// Open the DB Connection
		
		try 
		{
			br = new BufferedReader(new InputStreamReader(in));		// buffered reader used to read the csv file

			strLine = "";
			StringTokenizer st = null;
			recordCounter = 0; lineNumber = 0; tokenNumber = 0; fieldCounter = 0;
		}
		finally {}

		/*
		 *  Read the input file, one record at a time, to the end-of-file
		 */
		strLine = "";
		tokenNumber = 0; fieldCounter = 0;
		updateData = new Vector();

		try {
			while ((FileImport_ReadInputFile.strLine = br.readLine()) != null) 
			{
				StringTokenizer st =
					new StringTokenizer(FileImport_ReadInputFile.strLine, "|");
				
			    //iterate through tokens
	    		nbrFields = st.countTokens();
	    		nbrFields--;
				
				fieldCounter = 0;
				tokenNumber = 0;
				
				recordCounter++;
			    String recordCounterString = Integer.toString(recordCounter);
				
				tokenNumber++;
				fieldCounter++;
				
				field1 = st.nextToken();  		// Lab Accession No
				field2 = st.nextToken();  		// Submitter Specimen ID
				field3 = st.nextToken();  		// Specimen Source
				field4 = st.nextToken();  		// Date Collected
				field5 = st.nextToken();  		// Date Received
				field6 = st.nextToken();  		// Date Reported
				field7 = st.nextToken();  		// Primary Test
				field8 = st.nextToken();  		// Organism
				field9 = st.nextToken();  		// Primary Enzyme Pattern
				field10 = st.nextToken(); 		// Secondary Enzyme Pattern
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
				if (nbrFields > 28)
					{	
						field29 = st.nextToken(); 		// CDC Cluster Code
						field30 = st.nextToken(); 		// Local Primary Enzyme Pattern
						field31 = st.nextToken(); 		// Local Secondary Enzyme Pattern
						field32 = st.nextToken(); 		// Region
					}
				else	
					{
						field29 = "";
						field30 = "";
						field31 = "";
						field32 = "";
					}
				recordNumber = lineNumber;
				
				FileImport_EditUpdateFields.EditInputFields();						// Edit the record
				if 	(FileImport_EditUpdateFields.errorFlag != "true")
		    		{
						updateData.addElement(recordCounterString);
						updateData.addElement(field1);		// Lab Accession No
						updateData.addElement(field2);		// Submitter Specimen ID
						updateData.addElement(field3);		// Specimen Type (S/I)			
						updateData.addElement(field4);		// Date Collected
						updateData.addElement(field5);		// Date Received
						updateData.addElement(field6);		// Date Reported
						updateData.addElement(field7);		// Primary Test
						updateData.addElement(field8);		// Organism
						updateData.addElement(field9);		// Primary Enzyme Pattern
						updateData.addElement(field10);		// Secondary Enzyme Pattern
						updateData.addElement(field11);		// Other Result
						updateData.addElement(field12);		// Submitter ID
						updateData.addElement(field13);		// Submitter Name
						updateData.addElement(field14);		// Submitter State
						updateData.addElement(field15);		// Patient ID
						updateData.addElement(field16);		// Patient First Name
						updateData.addElement(field17);		// Patient Middle Name
						updateData.addElement(field18);		// Patient Last Name
						updateData.addElement(field19);		// Multiple Unit
						updateData.addElement(field20);		// Street Address
						updateData.addElement(field21);		// City
						updateData.addElement(field22);		// State
						updateData.addElement(field23);		// County
						updateData.addElement(field24);		// Postal Code
						updateData.addElement(field25);		// Postal Code+4
						updateData.addElement(field26);		// Gender
						updateData.addElement(field27);		// Patient DOB
						updateData.addElement(field28);		// Patient Age
						updateData.addElement(field29);		// CDC Cluster Code
						updateData.addElement(field30);		// Local Primary Enzyme Pattern
						updateData.addElement(field31);		// Local Secondary Enzyme Pattern
						updateData.addElement(field32);		// Region
	
/*
 *  Here is where we Change or Insert the record into the database
 */
						c4DataBase.DataBase_Gateway.main(null);
		    		}
			}  
			endOfFile = "true";
			br.close();

/*
 *  After closing the filereader, close the database.... before catch exception
 */
			DBUpdate.DatabaseClose();					// Close the DB Connection
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount() {
		return updateData.size() / getColumnCount();
	}

	public int getColumnCount(){
		return 33;
	}

	public String getColumnName(int col) 
	{
		switch(col) 
		{
		case 0: return "Record #";
		case 1: return "Accession #";
        case 2: return "Subm Spec ID";
        case 3: return "Source";
        case 4: return "Date Coll";
        case 5: return "Date Rcvd";
        case 6: return "Date Rptd";
        case 7: return "Primary Test";
        case 8: return "Organism";
        case 9: return "CDC Prim Enzyme Pattern";
        case 10: return "CDC Sec Enzyme Pattern";
        case 11: return "Other Result";
        case 12: return "Subm ID";
        case 13: return "Subm Name";
        case 14: return "Sub St";
        case 15: return "Patient ID";
        case 16: return "P Fst Name";
        case 17: return "P Mid";
        case 18: return "P Lst Name";
        case 19: return "P Mult Unit";
        case 20: return "P St Addr";
        case 21: return "P City";
        case 22: return "P St";
        case 23: return "P Cnty";
        case 24: return "P Zip";
        case 25: return "P Zip4";
        case 26: return "Gender";
        case 27: return "P DOB";
        case 28: return "P Age";
        case 29: return "Cluster Cd";
        case 30: return "Lcl Prim Enz Ptn";
        case 31: return "Lcl Sec Enz Ptn";
        case 32: return "Region";
		}
		throw new AssertionError("invalid column");
	}

	public Class getColumnClass(int columnIndex){
		return String.class;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return (String)updateData.elementAt( (rowIndex * getColumnCount()) + columnIndex);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		return;
	}
}
