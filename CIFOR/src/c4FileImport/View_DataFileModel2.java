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

public class View_DataFileModel2 extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	protected Vector data;
    protected Vector columnNames ;
    protected String datafile;
    protected File inputDatafile;
	private int fieldCounter;
    
    public View_DataFileModel2(String f){
        datafile = f;
        initVectors();
    }
    
    public void initVectors() {
        
        String aLine;
        data = new Vector();
        columnNames = new Vector();
        
        inputDatafile = FileImport_FileSelection.passedFile;

        try {
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

    public int getRowCount() {
        return data.size() / getColumnCount();
    }
    
    public int getColumnCount(){
        return 28;
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
            case 8: return "Prim Enzyme Pattern";
            case 9: return "Sec Enzyme Pattern";
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