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

package c4RegionReport;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import c4Reports.LaunchExcel;


/**
 * 
 * @author jonesg1
 */

/*
 *  Setup the table model and GUI for display and reporting
 *  Setup the Excel format for creation and display
 */

	public class DynamicClusterReport
	{
	/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		


	    
	    public static void main(String[] args) throws SQLException
		{
			if(DynamicClusterParameters.ckboxRegion.isSelected())
				{
					DynamicClusterMain.labelReportTitle = new JLabel(c4Utilities.CIFORProperties.crReportHeading
		        			+ " By " + c4Utilities.UserProperties.RegionName);
				}
			else
				{
					DynamicClusterMain.labelReportTitle = new JLabel(c4Utilities.CIFORProperties.crReportHeading);
				}
			
			DynamicClusterMain.labelReportTitle.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			DynamicClusterMain.labelReportTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			//System.out.println("DynamicClusterReport, regionSelected="  + DynamicClusterParameters.regionSelected);
			//System.out.println("DynamicClusterReport, sortbyRegionSelected="  + DynamicClusterParameters.sortByRegionSelected);
			//System.out.println("DynamicClusterReport, clusterCodeSelected="  + DynamicClusterParameters.clusterCodeSelected);
			//System.out.println("DynamicClusterReport, showOnlyPrimaryPatternSelected="  + DynamicClusterParameters.showOnlyPrimaryPatternSelected);

/*
	 *   Define the TableModel
	 */
	TableModel regionSelectedModel = new DefaultTableModel(DynamicReadDB.Rows, DynamicReadDB.Columns)
		{
			private static final long serialVersionUID = 1L; 
		};
		
	TableColumn col = null;
	
	/*
	 *   Create and set attributes for the JTable and clusterTable
	 */	
	final JTable regionSelectedTable = new JTable(regionSelectedModel);
	/**
	 * Determine if the cell renderer should be called.
	 * and which cell should be greyed.
	 */
	if(//DynamicClusterParameters.showAllReports == true && 
			DynamicClusterParameters.regionSelected == true
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
			&& DynamicClusterParameters.clusterCodeSelected == false)
		{
			col = regionSelectedTable.getColumnModel().getColumn(14);
			col.setCellRenderer(new DynamicClusterTableCellRenderer());
		}
	else
		if(//DynamicClusterParameters.showAllReports == true && 
				DynamicClusterParameters.regionSelected == true
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
			&& DynamicClusterParameters.clusterCodeSelected == true)
		{
			col = regionSelectedTable.getColumnModel().getColumn(15);
			col.setCellRenderer(new DynamicClusterTableCellRenderer());
		}	
	else
		if(//DynamicClusterParameters.showAllReports == true && 
				DynamicClusterParameters.regionSelected == true
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
			&& DynamicClusterParameters.clusterCodeSelected == false)
		{
			col = regionSelectedTable.getColumnModel().getColumn(16);
			col.setCellRenderer(new DynamicClusterTableCellRenderer());
		}	
	else
		if(//DynamicClusterParameters.showAllReports == true && 
				DynamicClusterParameters.regionSelected == true
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
			&& DynamicClusterParameters.clusterCodeSelected == true)
		{
			col = regionSelectedTable.getColumnModel().getColumn(17);
			col.setCellRenderer(new DynamicClusterTableCellRenderer());
		}
	else	//Region NOT selected
		if(//DynamicClusterParameters.showAllReports == true && 
				DynamicClusterParameters.regionSelected == false
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
				&& DynamicClusterParameters.clusterCodeSelected == false)
			{
				col = regionSelectedTable.getColumnModel().getColumn(13);
				col.setCellRenderer(new DynamicClusterTableCellRenderer());
			}
		else
			if(//DynamicClusterParameters.showAllReports == true && 
					DynamicClusterParameters.regionSelected == false
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
				&& DynamicClusterParameters.clusterCodeSelected == true)
			{
				col = regionSelectedTable.getColumnModel().getColumn(14);
				col.setCellRenderer(new DynamicClusterTableCellRenderer());
			}	
		else
			if(//DynamicClusterParameters.showAllReports == true && 
					DynamicClusterParameters.regionSelected == false
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
				&& DynamicClusterParameters.clusterCodeSelected == false)
			{
				col = regionSelectedTable.getColumnModel().getColumn(15);
				col.setCellRenderer(new DynamicClusterTableCellRenderer());
			}	
		else
			if(//DynamicClusterParameters.showAllReports == true && 
					DynamicClusterParameters.regionSelected == false
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
				&& DynamicClusterParameters.clusterCodeSelected == true)
			{
				col = regionSelectedTable.getColumnModel().getColumn(16);
				col.setCellRenderer(new DynamicClusterTableCellRenderer());
			}
	//col = regionSelectedTable.getColumnModel().getColumn(13);					// columns
	//col.setCellRenderer(new DynamicClusterTableCellRenderer());
		
	regionSelectedTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
    regionSelectedTable.setColumnSelectionAllowed(true);
	regionSelectedTable.setAutoCreateRowSorter(true);
	regionSelectedTable.setFillsViewportHeight(true);
	regionSelectedTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
	regionSelectedTable.setRowHeight(24);
    regionSelectedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
	JScrollPane scrollPane = new JScrollPane(regionSelectedTable);
	regionSelectedTable.setSize(DynamicClusterMain.newWidth, DynamicClusterMain.newHeight);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	/*
	 *   Define the table's column widths
	 */    
    TableColumn column = null;	
    

///////////////////////////////////////////////////////
//Region IS selected
///////////////////////////////////////////////////////
	if(DynamicClusterParameters.regionSelected == true
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
			&& DynamicClusterParameters.clusterCodeSelected == false)
		{
			//System.out.println("DCR 184, Reg=y,  PP=Y,  cc=F");
			for (int i = 0; i < 15; i++) 
			{
			    column = regionSelectedTable.getColumnModel().getColumn(i);
			    if 		(i == 0)  {column.setPreferredWidth(75);}		// Region
			    else if (i == 1)  {column.setPreferredWidth(110);}		// Accession No
			    else if (i == 2)  {column.setPreferredWidth(75);}		// First Name
			    else if (i == 3)  {column.setPreferredWidth(75);}		// Last Name
			    else if (i == 4)  {column.setPreferredWidth(125);}		// City
			    else if (i == 5)  {column.setPreferredWidth(50);}		// State
			    else if (i == 6)  {column.setPreferredWidth(125);}		// County
			    else if (i == 7)  {column.setPreferredWidth(50);}		// Age
			    else if (i == 8)  {column.setPreferredWidth(170);}		// Organism
			    else if (i == 9)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern1
			    else if (i == 10)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
			    else if (i == 11) {column.setPreferredWidth(165);}		// Other Result
			    else if (i == 12) {column.setPreferredWidth(100);}		// Date Collected
			    else if (i == 13) {column.setPreferredWidth(100);}		// Date Received
			    else if (i == 14) {column.setPreferredWidth(100);}		// Date Reported
			}
		}	
	if(DynamicClusterParameters.regionSelected == true 
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
			&& DynamicClusterParameters.clusterCodeSelected == false)
		{
			//System.out.println("DCR 165, Reg=y,  PP=F,  cc=F");
			for (int i = 0; i < 17; i++) 
			{
			    column = regionSelectedTable.getColumnModel().getColumn(i);
			    if 		(i == 0)  {column.setPreferredWidth(75);}		// Region
			    else if (i == 1)  {column.setPreferredWidth(110);}		// Accession No
			    else if (i == 2)  {column.setPreferredWidth(75);}		// First Name
			    else if (i == 3)  {column.setPreferredWidth(75);}		// Last Name
			    else if (i == 4)  {column.setPreferredWidth(125);}		// City
			    else if (i == 5)  {column.setPreferredWidth(50);}		// State
			    else if (i == 6)  {column.setPreferredWidth(125);}		// County
			    else if (i == 7)  {column.setPreferredWidth(50);}		// Age
			    else if (i == 8)  {column.setPreferredWidth(170);}		// Organism
			    else if (i == 9)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern1
			    else if (i == 10)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
			    else if (i == 11)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern2
			    else if (i == 12)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern2
			    else if (i == 13) {column.setPreferredWidth(165);}		// Other Result
			    else if (i == 14) {column.setPreferredWidth(100);}		// Date Collected
			    else if (i == 15) {column.setPreferredWidth(100);}		// Date Received
			    else if (i == 16) {column.setPreferredWidth(100);}		// Date Reported
			}
		}
	
	if(DynamicClusterParameters.regionSelected == true
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
			&& DynamicClusterParameters.clusterCodeSelected == true)
		{
			//System.out.println("DCR 193, Reg=y,  PP=Y,  cc=Y");
			for (int i = 0; i < 16; i++) 
			{
			    column = regionSelectedTable.getColumnModel().getColumn(i);
			    if 		(i == 0)  {column.setPreferredWidth(75);}		// Region
			    else if (i == 1)  {column.setPreferredWidth(110);}		// Accession No
			    else if (i == 2)  {column.setPreferredWidth(75);}		// First Name
			    else if (i == 3)  {column.setPreferredWidth(75);}		// Last Name
			    else if (i == 4)  {column.setPreferredWidth(125);}		// City
			    else if (i == 5)  {column.setPreferredWidth(50);}		// State
			    else if (i == 6)  {column.setPreferredWidth(125);}		// County
			    else if (i == 7)  {column.setPreferredWidth(50);}		// Age
			    else if (i == 8)  {column.setPreferredWidth(170);}		// Organism
			    else if (i == 9)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern1
			    else if (i == 10)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
			    else if (i == 11) {column.setPreferredWidth(165);}		// Other Result
			    else if (i == 12) {column.setPreferredWidth(100);}		// Cluster Code
			    else if (i == 13) {column.setPreferredWidth(100);}		// Date Collected
			    else if (i == 14) {column.setPreferredWidth(100);}		// Date Received
			    else if (i == 15) {column.setPreferredWidth(100);}		// Date Reported
			}
		}	
	if(DynamicClusterParameters.regionSelected == true 
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
			&& DynamicClusterParameters.clusterCodeSelected == true)
		{
			//System.out.println("DCR 218, Reg=y,  PP=F,  cc=Y");
			for (int i = 0; i < 18; i++) 
			{
			    column = regionSelectedTable.getColumnModel().getColumn(i);
			    if 		(i == 0)  {column.setPreferredWidth(75);}		// Region
			    else if (i == 1)  {column.setPreferredWidth(110);}		// Accession No
			    else if (i == 2)  {column.setPreferredWidth(75);}		// First Name
			    else if (i == 3)  {column.setPreferredWidth(75);}		// Last Name
			    else if (i == 4)  {column.setPreferredWidth(125);}		// City
			    else if (i == 5)  {column.setPreferredWidth(50);}		// State
			    else if (i == 6)  {column.setPreferredWidth(125);}		// County
			    else if (i == 7)  {column.setPreferredWidth(50);}		// Age
			    else if (i == 8)  {column.setPreferredWidth(170);}		// Organism
			    else if (i == 9)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern1
			    else if (i == 10)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
			    else if (i == 11)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern2
			    else if (i == 12)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern2
			    else if (i == 13) {column.setPreferredWidth(165);}		// Other Result
			    else if (i == 14) {column.setPreferredWidth(100);}		// Cluster Code
			    else if (i == 15) {column.setPreferredWidth(100);}		// Date Collected
			    else if (i == 16) {column.setPreferredWidth(100);}		// Date Received
			    else if (i == 17) {column.setPreferredWidth(100);}		// Date Reported
			}
		}
///////////////////////////////////////////////////////
// Region NOT selected
///////////////////////////////////////////////////////
	//if(c4Utilities.UserProperties.OnlyShowPrimaryPattern.equals("No") && DynamicClusterParameters.regionSelected == true)
	if(DynamicClusterParameters.regionSelected == false 
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
			&& DynamicClusterParameters.clusterCodeSelected == false)
		{
			for (int i = 0; i < 14; i++) 
			{
			    column = regionSelectedTable.getColumnModel().getColumn(i);
			    if 		(i == 0)  {column.setPreferredWidth(110);}		// Accession No
			    else if (i == 1)  {column.setPreferredWidth(75);}		// First Name
			    else if (i == 2)  {column.setPreferredWidth(75);}		// Last Name
			    else if (i == 3)  {column.setPreferredWidth(125);}		// City
			    else if (i == 4)  {column.setPreferredWidth(50);}		// State
			    else if (i == 5)  {column.setPreferredWidth(125);}		// County
			    else if (i == 6)  {column.setPreferredWidth(50);}		// Age
			    else if (i == 7)  {column.setPreferredWidth(170);}		// Organism
			    else if (i == 8)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern1
			    else if (i == 9)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
			    //else if (i == 10)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern2
			    //else if (i == 11)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern2
			    else if (i == 10) {column.setPreferredWidth(165);}		// Other Result
			    else if (i == 11) {column.setPreferredWidth(100);}		// Date Collected
			    else if (i == 12) {column.setPreferredWidth(100);}		// Date Received
			    else if (i == 13) {column.setPreferredWidth(100);}		// Date Reported
			}
		}
	//if(c4Utilities.UserProperties.OnlyShowPrimaryPattern.equals("No") && DynamicClusterParameters.regionSelected == false)
	if(DynamicClusterParameters.regionSelected == false 
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
			&& DynamicClusterParameters.clusterCodeSelected == false)
		{
			for (int i = 0; i < 16; i++) 
				{
				    column = regionSelectedTable.getColumnModel().getColumn(i);
				    //if 		(i == 0)  {column.setPreferredWidth(75);}		// Region
				    if 		(i == 0)  {column.setPreferredWidth(110);}		// Accession No
				    else if (i == 1)  {column.setPreferredWidth(75);}		// First Name
				    else if (i == 2)  {column.setPreferredWidth(75);}		// Last Name
				    else if (i == 3)  {column.setPreferredWidth(125);}		// City
				    else if (i == 4)  {column.setPreferredWidth(50);}		// State
				    else if (i == 5)  {column.setPreferredWidth(125);}		// County
				    else if (i == 6)  {column.setPreferredWidth(50);}		// Age
				    else if (i == 7)  {column.setPreferredWidth(170);}		// Organism
				    else if (i == 8)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern1
				    else if (i == 9)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
				    else if (i == 10)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern2
				    else if (i == 11)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern2
				    else if (i == 12) {column.setPreferredWidth(165);}		// Other Result
				    else if (i == 13) {column.setPreferredWidth(100);}		// Date Collected
				    else if (i == 14) {column.setPreferredWidth(100);}		// Date Received
				    else if (i == 15) {column.setPreferredWidth(100);}		// Date Reported
				}
			}
	
	if(DynamicClusterParameters.regionSelected == false
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
			&& DynamicClusterParameters.clusterCodeSelected == true)
		{
			//System.out.println("DCR 193, Reg=y,  PP=Y,  cc=Y");
			for (int i = 0; i < 15; i++) 
			{
			    column = regionSelectedTable.getColumnModel().getColumn(i);
			    if 		(i == 0)  {column.setPreferredWidth(110);}		// Accession No
			    else if (i == 1)  {column.setPreferredWidth(75);}		// First Name
			    else if (i == 2)  {column.setPreferredWidth(75);}		// Last Name
			    else if (i == 3)  {column.setPreferredWidth(125);}		// City
			    else if (i == 4)  {column.setPreferredWidth(50);}		// State
			    else if (i == 5)  {column.setPreferredWidth(125);}		// County
			    else if (i == 6)  {column.setPreferredWidth(50);}		// Age
			    else if (i == 7)  {column.setPreferredWidth(170);}		// Organism
			    else if (i == 8)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern1
			    else if (i == 9)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
			    else if (i == 10) {column.setPreferredWidth(165);}		// Other Result
			    else if (i == 11) {column.setPreferredWidth(100);}		// Cluster Code
			    else if (i == 12) {column.setPreferredWidth(100);}		// Date Collected
			    else if (i == 13) {column.setPreferredWidth(100);}		// Date Received
			    else if (i == 14) {column.setPreferredWidth(100);}		// Date Reported
			}
		}	
	if(DynamicClusterParameters.regionSelected == false 
			&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
			&& DynamicClusterParameters.clusterCodeSelected == true)
		{
			//System.out.println("DCR 218, Reg=y,  PP=F,  cc=Y");
			for (int i = 0; i < 17; i++) 
			{
			    column = regionSelectedTable.getColumnModel().getColumn(i);
			    if 		(i == 0)  {column.setPreferredWidth(110);}		// Accession No
			    else if (i == 1)  {column.setPreferredWidth(75);}		// First Name
			    else if (i == 2)  {column.setPreferredWidth(75);}		// Last Name
			    else if (i == 3)  {column.setPreferredWidth(125);}		// City
			    else if (i == 4)  {column.setPreferredWidth(50);}		// State
			    else if (i == 5)  {column.setPreferredWidth(125);}		// County
			    else if (i == 6)  {column.setPreferredWidth(50);}		// Age
			    else if (i == 7)  {column.setPreferredWidth(170);}		// Organism
			    else if (i == 8)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern1
			    else if (i == 9)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
			    else if (i == 10)  {column.setPreferredWidth(150);}		// Primary Enzyme Pattern2
			    else if (i == 11)  {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern2
			    else if (i == 12) {column.setPreferredWidth(165);}		// Other Result
			    else if (i == 13) {column.setPreferredWidth(100);}		// Cluster Code
			    else if (i == 14) {column.setPreferredWidth(100);}		// Date Collected
			    else if (i == 15) {column.setPreferredWidth(100);}		// Date Received
			    else if (i == 16) {column.setPreferredWidth(100);}		// Date Reported
			}
		}
	
	/*
	 *   Print the clusterTable
	 */	
	DynamicClusterMain.printButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	        	
	        	MessageFormat header = null;
	            if (DynamicClusterMain.headerCkBox.isSelected()) {								// if we should print a header
	                header = new MessageFormat(DynamicClusterMain.headerField.getText());		// create a MessageFormat around the header text
	            }

	            MessageFormat footer = null;								// if we should print a footer
	            if (DynamicClusterMain.footerCkBox.isSelected()) {								// create a MessageFormat around the footer text
	                footer = new MessageFormat(DynamicClusterMain.footerField.getText());
	            }
	        	
            try {
					regionSelectedTable.print	(JTable.PrintMode.FIT_WIDTH, 
															header, footer, true, null, true, null);
					
				} catch (HeadlessException pe) {
					pe.printStackTrace();
				} catch (PrinterException pe) {
					pe.printStackTrace();
				}
	        }
	    });

		/*
		 *   Write an Excel Spreadsheet file
		 */	
	DynamicClusterMain.excelButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
        	
            try
            {
            	DynamicDateCk.getDateTime();
	           
            	DynamicClusterMain.fileName = c4Utilities.CIFORProperties.crReportExcelDirectory
        				+ c4Utilities.CIFORProperties.crReportExcelFileName
        				+ DynamicDateCk.excelDate + ".xls";

	            WorkbookSettings ws = new WorkbookSettings();
	            ws.setLocale(new Locale(c4Utilities.CIFORProperties.localeLanguage, c4Utilities.CIFORProperties.localeCountry));
	            WritableWorkbook workbook = Workbook.createWorkbook(new File(DynamicClusterMain.fileName), ws);
	            WritableSheet s3 = workbook.createSheet(c4Utilities.CIFORProperties.crReportExcelSheet3, 0);
	            WritableSheet s2 = workbook.createSheet(c4Utilities.CIFORProperties.crReportExcelSheet2, 0);
	            //WritableSheet s1 = workbook.createSheet(c4Utilities.CIFORProperties.crReportExcelSheet1Region, 0);
	            WritableSheet s1 = workbook.createSheet(c4Utilities.CIFORProperties.crReportHeading
        			+ " By " + c4Utilities.UserProperties.RegionName, 0);
	            writeSheetHeaders(s1);
	            writeSheetFormat(s1);
	            
		    	int line = 0;
		    	Object[][] array = new Object [regionSelectedTable.getRowCount()] [regionSelectedTable.getColumnCount()];  
		    	String string = null;  
		    	Object object = new Object();
		    	
		    	for ( int i = 0; i < regionSelectedTable.getRowCount() ; ++i) 
		    	{   
		    		if(i>0){s1.setRowView(i, 300);}
		    		
		    		
		    	int colCount = regionSelectedTable.getColumnCount();
		    	//System.out.println("DCR 423, columncount=" + colCount);
		    	
		    		line++;
		    		for ( int j = 0; j < regionSelectedTable.getColumnCount(); ++j) 
		    		{  
		    			array[i][j] = regionSelectedTable.getValueAt(i, j);  
		    			object = regionSelectedTable.getValueAt(i,j);  
		    			if (object == null)
	    					{string = null;}
	    				else
	    					{string = object.toString();}
		    			
		    			int leftJust = colCount-3;	//The left columns are left justified, last 3 columns are centered
		    			//System.out.println("leftjust=" + leftJust);
		    			if (j < leftJust)
		    			{
		    			    WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
		    				WritableCellFormat cf1 = new WritableCellFormat(wf1);
		    				cf1.setAlignment(Alignment.LEFT);
		    				cf1.setWrap(false);
		    				Label column1 = new Label(j,line,string,cf1);
		    				s1.addCell(column1);
		    			}
		    			else
		    			{
		    			    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
		    				WritableCellFormat cf2 = new WritableCellFormat(wf2);
		    				cf2.setAlignment(Alignment.CENTRE);
		    				cf2.setWrap(true);
		    				Label column1 = new Label(j,line,string,cf2);
		    				s1.addCell(column1);
		    			}
	    			
		    		}
		    	}
	            
	            workbook.write();
	            workbook.close();      
	            }
	            catch (IOException e)
	            {
	              e.printStackTrace();
	            }
	            catch (WriteException e)
	            {	
	              e.printStackTrace();
	            }
	            
				/*
				 *  Launch Excel with the file passed from the requesting program.
				 */	            
		        try {
		        	LaunchExcel.fileName = DynamicClusterMain.fileName;
		        	LaunchExcel.main(null);
				} catch (Exception e) {
					e.printStackTrace();
				}				
        }
    });

	/*
	 *   Setup panels for the GUI
	 */	
	DynamicClusterMain.topPanel = new JPanel();
	DynamicClusterMain.contentPanel = new JPanel();
    
	/*
	 *  Add components to the GUI layout for display.  The GUI was created with
	 *  the Matisse GUI builder in NetBeans, so be careful when making changes!
	 */
	scrollPane.setPreferredSize(new java.awt.Dimension(DynamicClusterMain.newWidth, DynamicClusterMain.newHeight));
    
	javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(DynamicClusterMain.topPanel);
	DynamicClusterMain.topPanel.setLayout(topPanelLayout);
	     topPanelLayout.setHorizontalGroup(
	          topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	          .addGroup(topPanelLayout.createSequentialGroup()
	              .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                  .addGroup(topPanelLayout.createSequentialGroup()
	                      .addComponent(DynamicClusterMain.labelBeginningDate)
	                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                      .addComponent(DynamicClusterMain.textBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                  .addGroup(topPanelLayout.createSequentialGroup()
	                      .addComponent(DynamicClusterMain.labelCurrentDate)
	                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                      .addComponent(DynamicClusterMain.textCurrentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
	              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 406, Short.MAX_VALUE)
	              .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
	                      .addComponent(DynamicClusterMain.labelReportTitle)
	                      .addGap(DynamicClusterMain.newWidth-900, DynamicClusterMain.newWidth-900, DynamicClusterMain.newWidth-900)) 		// was 930
	                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
	                      .addGap(DynamicClusterMain.newWidth-925, DynamicClusterMain.newWidth-925, DynamicClusterMain.newWidth-925))))  	// was 955
	      );
      
	     topPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {DynamicClusterMain.textBeginningDate, DynamicClusterMain.textCurrentDate});

	     topPanelLayout.setVerticalGroup(
	          topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	          .addGroup(topPanelLayout.createSequentialGroup()
	              .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                  .addComponent(DynamicClusterMain.labelCurrentDate)
	                  .addComponent(DynamicClusterMain.textCurrentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	              .addGap(15, 15, 15)
	              .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                  .addComponent(DynamicClusterMain.labelBeginningDate)
	                  .addComponent(DynamicClusterMain.textBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
	          .addGroup(topPanelLayout.createSequentialGroup()
	              .addComponent(DynamicClusterMain.labelReportTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
	              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	      ));

	     topPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {DynamicClusterMain.textBeginningDate, DynamicClusterMain.textCurrentDate});
	  	
	JPanel bottomPanel = new JPanel();										
	bottomPanel.setBorder(BorderFactory.createTitledBorder(c4Utilities.CIFORProperties.cReportPrintOptions));
	  	
	  	 javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
	       bottomPanel.setLayout(bottomPanelLayout);
	       bottomPanelLayout.setHorizontalGroup(
	           bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	           .addGroup(bottomPanelLayout.createSequentialGroup()
	               .addContainerGap()
	               .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                   .addGroup(bottomPanelLayout.createSequentialGroup()
	                       .addComponent(DynamicClusterMain.headerCkBox)
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                       .addComponent(DynamicClusterMain.headerField, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
	                   .addGroup(bottomPanelLayout.createSequentialGroup()
	                       .addComponent(DynamicClusterMain.footerCkBox)
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                       .addComponent(DynamicClusterMain.footerField, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
	                       .addGap(46, 46, 46)
	                       .addComponent(DynamicClusterMain.printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
	                       .addGap(46, 46, 46)
	                       .addComponent(DynamicClusterMain.excelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
	               .addContainerGap(DynamicClusterMain.newWidth-860, Short.MAX_VALUE))	// was 860
	       );
	     bottomPanelLayout.setVerticalGroup(
	         bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	         .addGroup(bottomPanelLayout.createSequentialGroup()
	               .addContainerGap()
	               .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                   .addComponent(DynamicClusterMain.headerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addComponent(DynamicClusterMain.headerCkBox))
	               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	               .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                   .addComponent(DynamicClusterMain.footerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addComponent(DynamicClusterMain.footerCkBox)
	                   .addComponent(DynamicClusterMain.printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addComponent(DynamicClusterMain.excelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
	               .addContainerGap(25, Short.MAX_VALUE))
	       );
	         
	     javax.swing.GroupLayout layout = new javax.swing.GroupLayout(DynamicClusterMain.contentPanel);
	     DynamicClusterMain.contentPanel.setLayout(layout);
	       layout.setHorizontalGroup(
	           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	           .addGroup(layout.createSequentialGroup()
	               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                   .addGroup(layout.createSequentialGroup()
	                       .addContainerGap()
	                       .addComponent(DynamicClusterMain.topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                       .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addGroup(layout.createSequentialGroup()
	                  	 .addContainerGap()
	                       .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, DynamicClusterMain.newWidth, Short.MAX_VALUE)))
	                       .addContainerGap())
	       );
	     layout.setVerticalGroup(
	         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	         .addGroup(layout.createSequentialGroup()
	               .addContainerGap()
	               .addComponent(DynamicClusterMain.topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	               .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, DynamicClusterMain.newHeight-280, javax.swing.GroupLayout.PREFERRED_SIZE) // was 600
	               .addGap(18, 18, 18)
	               .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	               .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	       );

	/*
	 *   Setup a jPanel for printing
	 */	       
	    // DynamicClusterMain.jPanel.add(DynamicClusterMain.contentPanel);
	    // DynamicClusterMain.frame.add(DynamicClusterMain.jPanel, BorderLayout.CENTER);
	    // DynamicClusterMain.frame.setVisible(true);
	}
	    
	/*
	 *  Setup Excel Worksheet
	 */	    
    private static void writeSheetHeaders(WritableSheet s1) throws WriteException
    {
		/* Set the Page Format, Header and Margins for the worksheet */
    	//s1.getSettings().getFitHeight();
	////////////////////////////
    	s1.getSettings().setPaperSize(PaperSize.LETTER);
    	s1.getSettings().setOrientation(PageOrientation.LANDSCAPE);
    	s1.getSettings().setFitWidth(1);
	    s1.getSettings().setProtected(false);
	    s1.getSettings().setVerticalFreeze(1);
    ////////////////////////////////
	    //s1.getSettings().setDefaultRowHeight(300);
	    s1.setRowView(0, 500);
	    //s1.getSettings().setVerticalCentre(true);
    /////////////////////////////
	    s1.getSettings().setHeaderMargin(.25);
	    s1.getSettings().setFooterMargin(.25);
	    s1.getSettings().setTopMargin(.75);
	    s1.getSettings().setBottomMargin(.5);
	    s1.getSettings().setLeftMargin(.25);
	    s1.getSettings().setRightMargin(.25);
	    
	    /* Add a header and footer */
	    HeaderFooter header1 = new HeaderFooter();
	    header1.getLeft().append(DynamicClusterMain.excelFooter);
	    //s1.getSettings().getFitHeight();
    ////////////////////////////
	    reportHeader = c4Utilities.CIFORProperties.crReportHeading 
	    		+ " By " + c4Utilities.UserProperties.RegionName;
	    header1.getCentre().append(reportHeader);
	    //header1.getCentre().append(c4Utilities.CIFORProperties.crReportHeadingRegion);
	    s1.getSettings().setHeader(header1);
	        
	    HeaderFooter footer = new HeaderFooter();
	    s1.getSettings().setFooter(footer);
	    footer.getLeft().append(c4Utilities.CIFORProperties.crReportExcelDatePrinted);
	    footer.getLeft().appendDate();
	    footer.getRight().append(c4Utilities.CIFORProperties.crReportExcelPage);
	    footer.getRight().appendPageNumber();
	    
		/* Format the Font, set cell wrap "true", set background to color */
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);
		cf.setWrap(true);
		cf.setBackground(Colour.GRAY_25);

		/* Creates Labels for each column*/
		//System.out.println("DCR 655, regionSelected=" + DynamicClusterParameters.regionSelected 
		//		+ "  OnlyPEPSelected=" + DynamicClusterParameters.showOnlyPrimaryPatternSelected
		//		+ "  CCCodeSelected=" + DynamicClusterParameters.clusterCodeSelected);
////////////////////////////////////////////////////////////
//Region NOT selected
////////////////////////////////////////////////////////////
		if(DynamicClusterParameters.regionSelected == true
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
				&& DynamicClusterParameters.clusterCodeSelected == true)
			{
				Label column0 = new Label(0,0,c4Utilities.UserProperties.RegionName,cf);
				//Label column0 = new Label(0,0,c4Utilities.CIFORProperties.crReportRegion,cf);
				s1.addCell(column0);
				s1.setColumnView(0, 15);
				
				Label column1 = new Label(1,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf);
				s1.addCell(column1);
				s1.setColumnView(1, 15);
				
				Label column2 = new Label(2,0,c4Utilities.CIFORProperties.crReportFirstName,cf);
				s1.addCell(column2);
				s1.setColumnView(2, 15);
				
				Label column3 = new Label(3,0,c4Utilities.CIFORProperties.crReportLastName,cf);
				s1.addCell(column3);
				s1.setColumnView(3, 15);
				
				Label column4 = new Label(4,0,c4Utilities.CIFORProperties.crReportCity,cf);
				s1.addCell(column4);
				s1.setColumnView(4, 20);
				
				Label column5 = new Label(5,0,c4Utilities.CIFORProperties.crReportState,cf);
				s1.addCell(column5);
				s1.setColumnView(5, 10);
						
				Label column6 = new Label(6,0,c4Utilities.CIFORProperties.crReportCounty,cf);
				s1.addCell(column6);
				s1.setColumnView(6, 15);
				
				Label column7 = new Label(7,0,c4Utilities.CIFORProperties.crReportAge,cf);
		  		s1.addCell(column7);
		  		s1.setColumnView(7, 5);
				
				Label column8 = new Label(8,0,c4Utilities.CIFORProperties.crReportOrganism,cf);
		  		s1.addCell(column8);
		  		s1.setColumnView(8, 60);  			
				
				Label column9 = new Label(9,0,c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,cf);
		  		s1.addCell(column9);
		  		s1.setColumnView(9, 25); 
				
				Label column10 = new Label(10,0,c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,cf);
				s1.addCell(column10);
				s1.setColumnView(10, 25);
				
				Label column11 = new Label(11,0,c4Utilities.CIFORProperties.crReportOtherResult,cf);
				s1.addCell(column11);
				s1.setColumnView(11, 30);
				
				Label column12 = new Label(12,0,c4Utilities.CIFORProperties.crReportClusterCode,cf);
				s1.addCell(column12);
				s1.setColumnView(12, 10);
				
				WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				WritableCellFormat cf1 = new WritableCellFormat(wf1);
				cf1.setWrap(true);
				cf1.setBackground(Colour.GRAY_25);
				cf1.setAlignment(Alignment.CENTRE);
		
				Label column13 = new Label(13,0,c4Utilities.CIFORProperties.crReportDateCollected,cf1);
				s1.addCell(column13);
				s1.setColumnView(13, 15);
		
				Label column14 = new Label(14,0,c4Utilities.CIFORProperties.crReportDateReceived,cf1);
				s1.addCell(column14);
				s1.setColumnView(14, 15);
				
				Label column15 = new Label(15,0,c4Utilities.CIFORProperties.crReportDateReported,cf1);
				s1.addCell(column15);
				s1.setColumnView(15, 15);
			}

		if(DynamicClusterParameters.regionSelected == true
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
				&& DynamicClusterParameters.clusterCodeSelected == false)
			{
				Label column0 = new Label(0,0,c4Utilities.UserProperties.RegionName,cf);
				//Label column0 = new Label(0,0,c4Utilities.CIFORProperties.crReportRegion,cf);
				s1.addCell(column0);
				s1.setColumnView(0, 15);
				
				Label column1 = new Label(1,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf);
				s1.addCell(column1);
				s1.setColumnView(1, 15);
				
				Label column2 = new Label(2,0,c4Utilities.CIFORProperties.crReportFirstName,cf);
				s1.addCell(column2);
				s1.setColumnView(2, 15);
				
				Label column3 = new Label(3,0,c4Utilities.CIFORProperties.crReportLastName,cf);
				s1.addCell(column3);
				s1.setColumnView(3, 15);
				
				Label column4 = new Label(4,0,c4Utilities.CIFORProperties.crReportCity,cf);
				s1.addCell(column4);
				s1.setColumnView(4, 20);
				
				Label column5 = new Label(5,0,c4Utilities.CIFORProperties.crReportState,cf);
				s1.addCell(column5);
				s1.setColumnView(5, 10);
						
				Label column6 = new Label(6,0,c4Utilities.CIFORProperties.crReportCounty,cf);
				s1.addCell(column6);
				s1.setColumnView(6, 15);
				
				Label column7 = new Label(7,0,c4Utilities.CIFORProperties.crReportAge,cf);
		  		s1.addCell(column7);
		  		s1.setColumnView(7, 5);
				
				Label column8 = new Label(8,0,c4Utilities.CIFORProperties.crReportOrganism,cf);
		  		s1.addCell(column8);
		  		s1.setColumnView(8, 60);  			
				
				Label column9 = new Label(9,0,c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,cf);
		  		s1.addCell(column9);
		  		s1.setColumnView(9, 25); 
				
				Label column10 = new Label(10,0,c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,cf);
				s1.addCell(column10);
				s1.setColumnView(10, 35);
				
				Label column11 = new Label(11,0,c4Utilities.CIFORProperties.crReportOtherResult,cf);
				s1.addCell(column11);
				s1.setColumnView(11, 30);
				
				WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				WritableCellFormat cf1 = new WritableCellFormat(wf1);
				cf1.setWrap(true);
				cf1.setBackground(Colour.GRAY_25);
				cf1.setAlignment(Alignment.CENTRE);
		
				Label column12 = new Label(12,0,c4Utilities.CIFORProperties.crReportDateCollected,cf1);
				s1.addCell(column12);
				s1.setColumnView(12, 15);
		
				Label column13 = new Label(13,0,c4Utilities.CIFORProperties.crReportDateReceived,cf1);
				s1.addCell(column13);
				s1.setColumnView(13, 15);
				
				Label column14 = new Label(14,0,c4Utilities.CIFORProperties.crReportDateReported,cf1);
				s1.addCell(column14);
				s1.setColumnView(14, 15);
			}

		if(DynamicClusterParameters.regionSelected == true
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
				&& DynamicClusterParameters.clusterCodeSelected == false)
			{				
				Label column0 = new Label(0,0,c4Utilities.UserProperties.RegionName,cf);
				//Label column0 = new Label(0,0,c4Utilities.CIFORProperties.crReportRegion,cf);
				s1.addCell(column0);
				s1.setColumnView(0, 15);
				
				Label column1 = new Label(1,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf);
				s1.addCell(column1);
				s1.setColumnView(1, 15);
				
				Label column2 = new Label(2,0,c4Utilities.CIFORProperties.crReportFirstName,cf);
				s1.addCell(column2);
				s1.setColumnView(2, 15);
				
				Label column3 = new Label(3,0,c4Utilities.CIFORProperties.crReportLastName,cf);
				s1.addCell(column3);
				s1.setColumnView(3, 15);
				
				Label column4 = new Label(4,0,c4Utilities.CIFORProperties.crReportCity,cf);
				s1.addCell(column4);
				s1.setColumnView(4, 20);
				
				Label column5 = new Label(5,0,c4Utilities.CIFORProperties.crReportState,cf);
				s1.addCell(column5);
				s1.setColumnView(5, 10);
						
				Label column6 = new Label(6,0,c4Utilities.CIFORProperties.crReportCounty,cf);
				s1.addCell(column6);
				s1.setColumnView(6, 15);
				
				Label column7 = new Label(7,0,c4Utilities.CIFORProperties.crReportAge,cf);
		  		s1.addCell(column7);
		  		s1.setColumnView(7, 5);
				
				Label column8 = new Label(8,0,c4Utilities.CIFORProperties.crReportOrganism,cf);
		  		s1.addCell(column8);
		  		s1.setColumnView(8, 60);  			
				
				Label column9 = new Label(9,0,c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,cf);
		  		s1.addCell(column9);
		  		s1.setColumnView(9, 25); 
				
				Label column10 = new Label(10,0,c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,cf);
				s1.addCell(column10);
				s1.setColumnView(10, 25); 			
				
				Label column11 = new Label(11,0,c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,cf);
		  		s1.addCell(column11);
		  		s1.setColumnView(11, 25); 
				
				Label column12 = new Label(12,0,c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,cf);
				s1.addCell(column12);
				s1.setColumnView(12, 35);
				
				Label column13 = new Label(13,0,c4Utilities.CIFORProperties.crReportOtherResult,cf);
				s1.addCell(column13);
				s1.setColumnView(13, 30);

			    WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				WritableCellFormat cf1 = new WritableCellFormat(wf1);
				cf1.setAlignment(Alignment.LEFT);
				cf1.setBackground(Colour.GRAY_25);
				cf1.setWrap(true);
		
				Label column14 = new Label(14,0,c4Utilities.CIFORProperties.crReportDateCollected,cf1);
				s1.addCell(column14);
				s1.setColumnView(14, 15);
		
				Label column15 = new Label(15,0,c4Utilities.CIFORProperties.crReportDateReceived,cf1);
				s1.addCell(column15);
				s1.setColumnView(15, 15);
				
				Label column16 = new Label(16,0,c4Utilities.CIFORProperties.crReportDateReported,cf1);
				s1.addCell(column16);
				s1.setColumnView(16, 15);
			}

		if(DynamicClusterParameters.regionSelected == true
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
				&& DynamicClusterParameters.clusterCodeSelected == true)
			{				
				Label column0 = new Label(0,0,c4Utilities.UserProperties.RegionName,cf);
				//Label column0 = new Label(0,0,c4Utilities.CIFORProperties.crReportRegion,cf);
				s1.addCell(column0);
				s1.setColumnView(0, 15);
				
				Label column1 = new Label(1,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf);
				s1.addCell(column1);
				s1.setColumnView(1, 15);
				
				Label column2 = new Label(2,0,c4Utilities.CIFORProperties.crReportFirstName,cf);
				s1.addCell(column2);
				s1.setColumnView(2, 15);
				
				Label column3 = new Label(3,0,c4Utilities.CIFORProperties.crReportLastName,cf);
				s1.addCell(column3);
				s1.setColumnView(3, 15);
				
				Label column4 = new Label(4,0,c4Utilities.CIFORProperties.crReportCity,cf);
				s1.addCell(column4);
				s1.setColumnView(4, 20);
				
				Label column5 = new Label(5,0,c4Utilities.CIFORProperties.crReportState,cf);
				s1.addCell(column5);
				s1.setColumnView(5, 10);
						
				Label column6 = new Label(6,0,c4Utilities.CIFORProperties.crReportCounty,cf);
				s1.addCell(column6);
				s1.setColumnView(6, 15);
				
				Label column7 = new Label(7,0,c4Utilities.CIFORProperties.crReportAge,cf);
		  		s1.addCell(column7);
		  		s1.setColumnView(7, 5);
				
				Label column8 = new Label(8,0,c4Utilities.CIFORProperties.crReportOrganism,cf);
		  		s1.addCell(column8);
		  		s1.setColumnView(8, 60);  			
				
				Label column9 = new Label(9,0,c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,cf);
		  		s1.addCell(column9);
		  		s1.setColumnView(9, 25); 
				
				Label column10 = new Label(10,0,c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,cf);
				s1.addCell(column10);
				s1.setColumnView(10, 25); 			
				
				Label column11 = new Label(11,0,c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,cf);
		  		s1.addCell(column11);
		  		s1.setColumnView(11, 25); 
				
				Label column12 = new Label(12,0,c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,cf);
				s1.addCell(column12);
				s1.setColumnView(12, 25);
				
				Label column13 = new Label(13,0,c4Utilities.CIFORProperties.crReportOtherResult,cf);
				s1.addCell(column13);
				s1.setColumnView(13, 30);
				
				Label column14 = new Label(14,0,c4Utilities.CIFORProperties.crReportClusterCode,cf);
				s1.addCell(column14);
				s1.setColumnView(14, 10);
				
			    WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				WritableCellFormat cf1 = new WritableCellFormat(wf1);
				cf1.setAlignment(Alignment.LEFT);
				cf1.setBackground(Colour.GRAY_25);
				cf1.setWrap(true);
		
				Label column15 = new Label(15,0,c4Utilities.CIFORProperties.crReportDateCollected,cf1);
				s1.addCell(column15);
				s1.setColumnView(15, 15);
		
				Label column16 = new Label(16,0,c4Utilities.CIFORProperties.crReportDateReceived,cf1);
				s1.addCell(column16);
				s1.setColumnView(16, 15);
				
				Label column17 = new Label(17,0,c4Utilities.CIFORProperties.crReportDateReported,cf1);
				s1.addCell(column17);
				s1.setColumnView(17, 15);
			}
////////////////////////////////////////////////////////////
// Region NOT selected
////////////////////////////////////////////////////////////		
		if(DynamicClusterParameters.regionSelected == false
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
				&& DynamicClusterParameters.clusterCodeSelected == true)
			{
				Label column0 = new Label(0,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf);
				s1.addCell(column0);
				s1.setColumnView(0, 15);
				
				Label column1 = new Label(1,0,c4Utilities.CIFORProperties.crReportFirstName,cf);
				s1.addCell(column1);
				s1.setColumnView(1, 15);
				
				Label column2 = new Label(2,0,c4Utilities.CIFORProperties.crReportLastName,cf);
				s1.addCell(column2);
				s1.setColumnView(2, 15);
				
				Label column3 = new Label(3,0,c4Utilities.CIFORProperties.crReportCity,cf);
				s1.addCell(column3);
				s1.setColumnView(3, 20);
				
				Label column4 = new Label(4,0,c4Utilities.CIFORProperties.crReportState,cf);
				s1.addCell(column4);
				s1.setColumnView(4, 10);
						
				Label column5 = new Label(5,0,c4Utilities.CIFORProperties.crReportCounty,cf);
				s1.addCell(column5);
				s1.setColumnView(5, 15);
				
				Label column6 = new Label(6,0,c4Utilities.CIFORProperties.crReportAge,cf);
		  		s1.addCell(column6);
		  		s1.setColumnView(6, 5);
				
				Label column7 = new Label(7,0,c4Utilities.CIFORProperties.crReportOrganism,cf);
		  		s1.addCell(column7);
		  		s1.setColumnView(7, 60);  			
				
				Label column8 = new Label(8,0,c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,cf);
		  		s1.addCell(column8);
		  		s1.setColumnView(8, 25); 
				
				Label column9 = new Label(9,0,c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,cf);
				s1.addCell(column9);
				s1.setColumnView(9, 25);
				
				Label column10 = new Label(10,0,c4Utilities.CIFORProperties.crReportOtherResult,cf);
				s1.addCell(column10);
				s1.setColumnView(10, 30);
				
				Label column11 = new Label(11,0,c4Utilities.CIFORProperties.crReportClusterCode,cf);
				s1.addCell(column11);
				s1.setColumnView(11, 10);
				
				WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				WritableCellFormat cf1 = new WritableCellFormat(wf1);
				cf1.setWrap(true);
				cf1.setBackground(Colour.GRAY_25);
				cf1.setAlignment(Alignment.CENTRE);
		
				Label column12 = new Label(12,0,c4Utilities.CIFORProperties.crReportDateCollected,cf1);
				s1.addCell(column12);
				s1.setColumnView(12, 15);
		
				Label column13 = new Label(13,0,c4Utilities.CIFORProperties.crReportDateReceived,cf1);
				s1.addCell(column13);
				s1.setColumnView(13, 15);
				
				Label column14 = new Label(14,0,c4Utilities.CIFORProperties.crReportDateReported,cf1);
				s1.addCell(column14);
				s1.setColumnView(14, 15);
			}

		if(DynamicClusterParameters.regionSelected == false
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
				&& DynamicClusterParameters.clusterCodeSelected == false)
			{
				Label column0 = new Label(0,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf);
				s1.addCell(column0);
				s1.setColumnView(0, 15);
				
				Label column1 = new Label(1,0,c4Utilities.CIFORProperties.crReportFirstName,cf);
				s1.addCell(column1);
				s1.setColumnView(1, 15);
				
				Label column2 = new Label(2,0,c4Utilities.CIFORProperties.crReportLastName,cf);
				s1.addCell(column2);
				s1.setColumnView(2, 15);
				
				Label column3 = new Label(3,0,c4Utilities.CIFORProperties.crReportCity,cf);
				s1.addCell(column3);
				s1.setColumnView(3, 20);
				
				Label column4 = new Label(4,0,c4Utilities.CIFORProperties.crReportState,cf);
				s1.addCell(column4);
				s1.setColumnView(4, 10);
						
				Label column5 = new Label(5,0,c4Utilities.CIFORProperties.crReportCounty,cf);
				s1.addCell(column5);
				s1.setColumnView(5, 15);
				
				Label column6 = new Label(6,0,c4Utilities.CIFORProperties.crReportAge,cf);
		  		s1.addCell(column6);
		  		s1.setColumnView(6, 5);
				
				Label column7 = new Label(7,0,c4Utilities.CIFORProperties.crReportOrganism,cf);
		  		s1.addCell(column7);
		  		s1.setColumnView(7, 60);  			
				
				Label column8 = new Label(8,0,c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,cf);
		  		s1.addCell(column8);
		  		s1.setColumnView(8, 25); 
				
				Label column9 = new Label(9,0,c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,cf);
				s1.addCell(column9);
				s1.setColumnView(9, 25);
				
				Label column10 = new Label(10,0,c4Utilities.CIFORProperties.crReportOtherResult,cf);
				s1.addCell(column10);
				s1.setColumnView(10, 30);
				
				WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				WritableCellFormat cf1 = new WritableCellFormat(wf1);
				cf1.setWrap(true);
				cf1.setBackground(Colour.GRAY_25);
				cf1.setAlignment(Alignment.CENTRE);
		
				Label column11 = new Label(11,0,c4Utilities.CIFORProperties.crReportDateCollected,cf1);
				s1.addCell(column11);
				s1.setColumnView(11, 15);
		
				Label column12 = new Label(12,0,c4Utilities.CIFORProperties.crReportDateReceived,cf1);
				s1.addCell(column12);
				s1.setColumnView(12, 15);
				
				Label column13 = new Label(13,0,c4Utilities.CIFORProperties.crReportDateReported,cf1);
				s1.addCell(column13);
				s1.setColumnView(13, 15);
			}

		if(DynamicClusterParameters.regionSelected == false
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
				&& DynamicClusterParameters.clusterCodeSelected == false)
			{				
				Label column0 = new Label(0,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf);
				s1.addCell(column0);
				s1.setColumnView(0, 15);
				
				Label column1 = new Label(1,0,c4Utilities.CIFORProperties.crReportFirstName,cf);
				s1.addCell(column1);
				s1.setColumnView(1, 15);
				
				Label column2 = new Label(2,0,c4Utilities.CIFORProperties.crReportLastName,cf);
				s1.addCell(column2);
				s1.setColumnView(2, 15);
				
				Label column3 = new Label(3,0,c4Utilities.CIFORProperties.crReportCity,cf);
				s1.addCell(column3);
				s1.setColumnView(3, 20);
				
				Label column4 = new Label(4,0,c4Utilities.CIFORProperties.crReportState,cf);
				s1.addCell(column4);
				s1.setColumnView(4, 10);
						
				Label column5 = new Label(5,0,c4Utilities.CIFORProperties.crReportCounty,cf);
				s1.addCell(column5);
				s1.setColumnView(5, 15);
				
				Label column6 = new Label(6,0,c4Utilities.CIFORProperties.crReportAge,cf);
		  		s1.addCell(column6);
		  		s1.setColumnView(6, 5);
				
				Label column7 = new Label(7,0,c4Utilities.CIFORProperties.crReportOrganism,cf);
		  		s1.addCell(column7);
		  		s1.setColumnView(7, 60);  			
				
				Label column8 = new Label(8,0,c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,cf);
		  		s1.addCell(column8);
		  		s1.setColumnView(8, 25); 
				
				Label column9 = new Label(9,0,c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,cf);
				s1.addCell(column9);
				s1.setColumnView(9, 25);			
				
				Label column10 = new Label(10,0,c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,cf);
		  		s1.addCell(column10);
		  		s1.setColumnView(10, 25); 
				
				Label column11 = new Label(11,0,c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,cf);
				s1.addCell(column11);
				s1.setColumnView(11, 35);
				
				Label column12 = new Label(12,0,c4Utilities.CIFORProperties.crReportOtherResult,cf);
				s1.addCell(column12);
				s1.setColumnView(12, 30);

			    WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				WritableCellFormat cf1 = new WritableCellFormat(wf1);
				cf1.setAlignment(Alignment.LEFT);
				cf1.setBackground(Colour.GRAY_25);
				cf1.setWrap(true);
		
				Label column13 = new Label(13,0,c4Utilities.CIFORProperties.crReportDateCollected,cf1);
				s1.addCell(column13);
				s1.setColumnView(13, 15);
		
				Label column14 = new Label(14,0,c4Utilities.CIFORProperties.crReportDateReceived,cf1);
				s1.addCell(column14);
				s1.setColumnView(14, 15);
				
				Label column15 = new Label(15,0,c4Utilities.CIFORProperties.crReportDateReported,cf1);
				s1.addCell(column15);
				s1.setColumnView(15, 15);
			}

		if(DynamicClusterParameters.regionSelected == false
				&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
				&& DynamicClusterParameters.clusterCodeSelected == true)
			{				
				Label column0 = new Label(0,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf);
				s1.addCell(column0);
				s1.setColumnView(0, 15);
				
				Label column1 = new Label(1,0,c4Utilities.CIFORProperties.crReportFirstName,cf);
				s1.addCell(column1);
				s1.setColumnView(1, 15);
				
				Label column2 = new Label(2,0,c4Utilities.CIFORProperties.crReportLastName,cf);
				s1.addCell(column2);
				s1.setColumnView(2, 15);
				
				Label column3 = new Label(3,0,c4Utilities.CIFORProperties.crReportCity,cf);
				s1.addCell(column3);
				s1.setColumnView(3, 20);
				
				Label column4 = new Label(4,0,c4Utilities.CIFORProperties.crReportState,cf);
				s1.addCell(column4);
				s1.setColumnView(4, 10);
						
				Label column5 = new Label(5,0,c4Utilities.CIFORProperties.crReportCounty,cf);
				s1.addCell(column5);
				s1.setColumnView(5, 15);
				
				Label column6 = new Label(6,0,c4Utilities.CIFORProperties.crReportAge,cf);
		  		s1.addCell(column6);
		  		s1.setColumnView(6, 5);
				
				Label column7 = new Label(7,0,c4Utilities.CIFORProperties.crReportOrganism,cf);
		  		s1.addCell(column7);
		  		s1.setColumnView(7, 60);  			
				
				Label column8 = new Label(8,0,c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,cf);
		  		s1.addCell(column8);
		  		s1.setColumnView(8, 25); 
				
				Label column9 = new Label(9,0,c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,cf);
				s1.addCell(column9);
				s1.setColumnView(9, 25);			
				
				Label column10 = new Label(10,0,c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,cf);
		  		s1.addCell(column10);
		  		s1.setColumnView(10, 25); 
				
				Label column11 = new Label(11,0,c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,cf);
				s1.addCell(column11);
				s1.setColumnView(11, 35);
				
				Label column12 = new Label(12,0,c4Utilities.CIFORProperties.crReportOtherResult,cf);
				s1.addCell(column12);
				s1.setColumnView(12, 30);
				
				Label column13 = new Label(13,0,c4Utilities.CIFORProperties.crReportClusterCode,cf);
				s1.addCell(column13);
				s1.setColumnView(13, 10);
				
			    WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				WritableCellFormat cf1 = new WritableCellFormat(wf1);
				cf1.setAlignment(Alignment.LEFT);
				cf1.setBackground(Colour.GRAY_25);
				cf1.setWrap(true);
		
				Label column14 = new Label(14,0,c4Utilities.CIFORProperties.crReportDateCollected,cf1);
				s1.addCell(column14);
				s1.setColumnView(14, 15);
		
				Label column15 = new Label(15,0,c4Utilities.CIFORProperties.crReportDateReceived,cf1);
				s1.addCell(column15);
				s1.setColumnView(15, 15);
				
				Label column16 = new Label(16,0,c4Utilities.CIFORProperties.crReportDateReported,cf1);
				s1.addCell(column16);
				s1.setColumnView(16, 15);
			}
  	  }
  	    
	/*
	 *  Write the Excel sheet
	 */
    private static void writeSheetFormat(WritableSheet s1) throws WriteException
    {
		/* Format the Font */
	    WritableFont wf = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
	    WritableCellFormat cf = new WritableCellFormat(wf);
	    cf.setWrap(true);
    	//s1.getSettings().setDefaultRowHeight(300);
    }
    
	/*
	 * Program Variables
	 * 
	 */
    public		static	String			currentMo;
    public		static	String			currentDy;
    public		static	String			currentYr;
    public		static	String			beginningMo;
    public		static	String			beginningDy;
    public		static	String			beginningYr;
    
    public		static	String 			reportHeader; // for appending RegionName property
    
    public		static	String			beginningDateString;
    public		static	String			currentDateString;

    public		static	String			dateReportedString;
    public		static	String			reportedString;
    
    public		static	String			goodDate;
    public		static	String			renderColor;
    public		static	String			excelDate;
    
    public		static	java.sql.Date 	newDateCurrentDate;
    public		static	java.sql.Date 	newDateBeginningDate;
//////////////////////////////

}