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

public class View_DataFileModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	protected Vector data;
    protected Vector columnNames ;
    protected String datafile;
    protected File inputDatafile;
	private static FileInputStream in;
	protected static BufferedReader br;
	private int fieldCounter;
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
	
    
    public View_DataFileModel(String f){
        datafile = f;
        initVectors();
    }
    
    public void initVectors() 
	{
        String aLine;
        data = new Vector();
        columnNames = new Vector();
        
        inputDatafile = FileImport_FileSelection.passedFile;
        
//////////////////////////////////////////////////////////////
		try
		{
			in = new FileInputStream(inputDatafile);
		}
		catch (IOException ioe) {
			System.out.println("IO Error:" + ioe.getMessage());
		}
//////////////////////////////////////////////////////////////		
		try 
		{
			br = new BufferedReader(new InputStreamReader(in));		// buffered reader used to read the csv file

			FileImport_ReadInputFile.strLine = "";
			StringTokenizer st = null;
			//recordCounter = 0; lineNumber = 0; tokenNumber = 0; fieldCounter = 0;
		}
		finally {}
//////////////////////////////////////////////////////////////
		try {
			while ((FileImport_ReadInputFile.strLine = br.readLine()) != null) 
			{
				StringTokenizer st =
					new StringTokenizer(FileImport_ReadInputFile.strLine, "|");
				
		    		//System.out.println("Remaining Tokens : " + st.countTokens());
		    		nbrFields = st.countTokens();
		    		nbrFields--;
		    		//System.out.println("nbrFields = " + nbrFields);
		    		//System.out.println(st.nextToken());

				
			    fieldCounter = 0;
				
				fieldCounter++;
				
				field1 = st.nextToken();  data.addElement(field1);        //System.out.println("field1 = " + field1);		// Lab Accession No
				field2 = st.nextToken();  data.addElement(field2);        //System.out.println("field2 = " + field2);		// Submitter Specimen ID
				field3 = st.nextToken();  data.addElement(field3);        //System.out.println("field3 = " + field3);		// Specimen Type (S/I)
				field4 = st.nextToken();  data.addElement(field4);        //System.out.println("field4 = " + field4);		// Date Collected
				field5 = st.nextToken();  data.addElement(field5);        //System.out.println("field5 = " + field5);		// Date Received
				field6 = st.nextToken();  data.addElement(field6);        //System.out.println("field6 = " + field6);		// Date Reported
				field7 = st.nextToken();  data.addElement(field7);        //System.out.println("field7 = " + field7);		// Primary Test
				field8 = st.nextToken();  data.addElement(field8);        //System.out.println("field8 = " + field8);		// Organism
				field9 = st.nextToken();  data.addElement(field9);        //System.out.println("field9 = " + field9);		// CDC Primary Enzyme Pattern
				field10 = st.nextToken();  data.addElement(field10);      //System.out.println("field10 = " + field10);		// CDC Secondary Enzyme Pattern
				field11 = st.nextToken();  data.addElement(field11);      //System.out.println("field11 = " + field11);		// Other Result
				field12 = st.nextToken();  data.addElement(field12);      //System.out.println("field12 = " + field12);		// Submitter ID
				field13 = st.nextToken();  data.addElement(field13);      //System.out.println("field13 = " + field13);		// Submitter Name
				field14 = st.nextToken();  data.addElement(field14);      //System.out.println("field14 = " + field14);		// Submitter State
				field15 = st.nextToken();  data.addElement(field15);      //System.out.println("field15 = " + field15);		// Patient ID
				field16 = st.nextToken();  data.addElement(field16);      //System.out.println("field16 = " + field16);		// Patient First Name
				field17 = st.nextToken();  data.addElement(field17);      //System.out.println("field17 = " + field17);		// Patient Middle Name
				field18 = st.nextToken();  data.addElement(field18);      //System.out.println("field18 = " + field18);		// Patient Last Name
				field19 = st.nextToken();  data.addElement(field19);      //System.out.println("field19 = " + field19);		// Multiple Unit
				field20 = st.nextToken();  data.addElement(field20);      //System.out.println("field20 = " + field20);		// Street Address
				field21 = st.nextToken();  data.addElement(field21);      //System.out.println("field21 = " + field21);		// City
				field22 = st.nextToken();  data.addElement(field22);      //System.out.println("field22 = " + field22);		// State
				field23 = st.nextToken();  data.addElement(field23);      //System.out.println("field23 = " + field23);		// County
				field24 = st.nextToken();  data.addElement(field24);      //System.out.println("field24 = " + field24);		// Postal Code
				field25 = st.nextToken();  data.addElement(field25);      //System.out.println("field25 = " + field25);		// Postal Code+4
				field26 = st.nextToken();  data.addElement(field26);      //System.out.println("field26 = " + field26);		// Gender
				field27 = st.nextToken();  data.addElement(field27);      //System.out.println("field27 = " + field27);		// Patient DOB
				field28 = st.nextToken();  data.addElement(field28);      //System.out.println("field28 = " + field28);		// Patient Age	
				if (nbrFields > 28)
					{
						field29 = st.nextToken();  data.addElement(field29);      //System.out.println("field29 = " + field29);		// CDC Cluster Code
						field30 = st.nextToken();  data.addElement(field30);      //System.out.println("field30 = " + field30);		// Local Primary Enzyme Pattern
						field31 = st.nextToken();  data.addElement(field31);      //System.out.println("field31 = " + field31);	// Local Secondary Enzyme Pattern
						field32 = st.nextToken();  data.addElement(field32);      //System.out.println("field32 = " + field32);	// Region
					}
				else	
					{
						field29 = "";  data.addElement(field29);       //System.out.println("field29 = " + field29);		// CDC Cluster Code
						field30 = "";  data.addElement(field30);       //System.out.println("field30 = " + field30);		// Local Primary Enzyme Pattern
						field31 = "";  data.addElement(field31);       //System.out.println("field31 = " + field31);	// Local Secondary Enzyme Pattern
						field32 = "";  data.addElement(field32);       //System.out.println("field32 = " + field32);	// Region
					}

               // data.addElement(st.nextToken());

			}  
			
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//////////////////////////////////////////////////////////////
/*        try {
            FileInputStream fin =  new FileInputStream(inputDatafile);
        	BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            while ((aLine = br.readLine()) != null) {
                StringTokenizer st2 =
                        new StringTokenizer(aLine, "|");
                while(st2.hasMoreTokens())
                    data.addElement(st2.nextToken());
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
*/
    public int getRowCount() {
        return data.size() / getColumnCount();
    }
    
    public int getColumnCount(){
        return 32;
    }

    public String getColumnName(int col) 
    {
        switch(col) 
        {
            case 0: return "Accession #";
            case 1: return "Subm Spec ID";
            case 2: return "Type";
            case 3: return "Date Coll";
            case 4: return "Date Rcvd";
            case 5: return "Date Rptd";
            case 6: return "Primary Test";
            case 7: return "Organism";
            case 8: return "CDC Prim Enz Pattern";
            case 9: return "CDC Sec Enz Pattern";
            case 10: return "Other Result";
            case 11: return "Subm ID";
            case 12: return "Subm Name";
            case 13: return "Sub St";
            case 14: return "Patient ID";
            case 15: return "P Fst Name";
            case 16: return "P Mid";
            case 17: return "P Lst Name";
            case 18: return "P Mult Unit";
            case 19: return "P St Addr";
            case 20: return "P City";
            case 21: return "P St";
            case 22: return "P Cnty";
            case 23: return "P Zip";
            case 24: return "P Zip4";
            case 25: return "P Gen";
            case 26: return "P DOB";
            case 27: return "P Age";
            case 28: return "Clstr Cd";
            case 29: return "Lcl Prim Enz Pattern";
            case 30: return "Lcl Sec Enz Pattern";
            case 31: return "Region";
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
        return (String)data.elementAt( (rowIndex * getColumnCount()) + columnIndex);
    }
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        return;
    }

}