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

import javax.swing.*;
import javax.swing.table.*;
//////////////////////////////////////
import c4Reports.LaunchExcel;
//////////////////////////////////////
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.sql.*;
import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 
 * @author jonesg1
 */

public class DynamicClusterMain extends JFrame
{
		private static final long serialVersionUID = 1L;

		/*
		 *  Create GUI Components
		 */
		public 		static 	JPanel 		topPanel;
		public 		static 	JPanel 		contentPanel;
		public 		static 	JLabel 		labelReportTitle;
		public 		static 	JLabel 		labelCurrentDate;
		public 		static 	JLabel 		labelBeginningDate;
	    public      static 	JButton 	printButton;
	    public		static	JButton		excelButton;
	    protected 	static 	JCheckBox 	headerCkBox;
	    protected 	static 	JCheckBox 	footerCkBox;
	    protected 	static 	JTextField 	headerField;
	    protected 	static 	JTextField 	footerField;
	    public 		static 	JTextField 	textCurrentDate;
	    public 		static 	JTextField 	textBeginningDate;
	    public 		static 	JTextField 	textReportSelection;
	    public 		static 	Date 		newCurrentDate;
	    public 		static 	Date 		newBeginDate;
	    public 		static	String		ckBoxStatus;
	    public 		static	String		regionStatus;
	    
	    static 		PreparedStatement 	psDates;
	    
	    public		static	String		fileName;
	    public		static	File		excelFile;
		public		static	String		excelFooter;
		public		static	String		firstRecord = "Y";
	    public		static	String		lastTestField = "";
	    public		static	String		inputTestField;
	    public		static	Date		inputDateReported;

	    public		static	String		reportedMo;
	    public		static	String		reportedDy;
	    public		static	String		reportedYr;
	    
	    public		static	int			newWidth;
	    public		static	int			newHeight;
	    public		static	int			insetX;
	    public		static	int			insetY;
	    public		static 	JPanel		jPanel;
	    public		static	JFrame		frame;
	    
	    //public		static	String		rcvDate;
	    public		static	String		rptDate;
 
		/*
		 *  "main" section of the program
		 */	  	
	    public static void main( String [] argv ) throws Exception
	    { 
	    	new DynamicClusterMain();

			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();
			
	        insetX 		= 0;
	        insetY 		= 0;
			newWidth 	= 0;
			newHeight 	= 0;
			
			if 		(d.width > 1900) {newWidth = d.width-300; newHeight = d.height-100; insetX = 10; insetY = 20;} //insetX = 100
			else if	(d.width < 1900) {newWidth = d.width-75; newHeight = d.height-50; insetX = 20; insetY = 20;}
			
			DynamicClusterMain frame = new DynamicClusterMain();
			

	        //System.out.println("DynamicClusterMain 129, regionSelected=" + DynamicClusterParameters.regionSelected);
			
			//frame.setSize(newWidth+85, newHeight); // was +10
			frame.setLocation(insetX, insetY);
	    	frame.setTitle(c4Utilities.CIFORProperties.crReportTitle);
	    	frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    	jPanel = new JPanel( new BorderLayout() );

	    	initValues();
	    	
			/*
			*  Get the parameter dates and set the MO, DY and YYYY for comparison to REPORTED_DATE
			*  Setup the date fields for the SQL Query
			*/
	    	c4RegionReport.DynamicDateCk.CkInputDates();
	    	c4RegionReport.DynamicDateCk.setParameterDates();
			//System.out.println("RegionMain 139,   newDateCurrentDate = " + DynamicDateCk.newDateCurrentDate 
			//		+ "  newDateBeginningDate = " + DynamicDateCk.newDateBeginningDate);
			

			/*
			 * Determine if the Region option is selected and then
			 * Read the database.  Put the selected results records 
			 * into the Vector for display, printing and Excel
			 */
//////////////////////////////////////////////////////
	    	//if(DynamicClusterParameters.ckboxRegion.isSelected())
	        //	{
	        //		//System.out.println("RegionMain 151, going to ReadDB_RegionSelected");
	        //		frame.setSize(newWidth+85, newHeight);
	        //		ReadDB_RegionSelected.main(null);
	        //		RegionSelected.main(null);
	        //	}
	        //else
		    //    {
	        		//System.out.println("RegionMain 158, going to ReadDB_RegionNotSelected");
		        	frame.setSize(newWidth+10, newHeight);
		        	//ReadDB_RegionNotSelected.main(null);
		        	//RegionNotSelected.main(null);
		        	DynamicReadDB.main(null);
		        	DynamicClusterReport.main(null);
		     //   }
//////////////////////////////////////////////////////////	    	
	    	/*
	    	 *   Setup a jPanel for printing
	    	 */	       
		     jPanel.add(contentPanel);
		     frame.add(jPanel, BorderLayout.CENTER);
		     frame.setVisible(true);
    	     
        }
	
	
    
	/*
	 *   Initialize values and get parameters passed from the parameter program
	 */		
	private static void initValues() 
	{	

		
		labelCurrentDate = new JLabel(c4Utilities.CIFORProperties.currentDate);
		textCurrentDate = new javax.swing.JTextField();
		textCurrentDate.setEditable(false);
		textCurrentDate.setText(DynamicClusterParameters.stringCurrentDate);
	    labelCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textCurrentDate.setBorder(null);
		
		labelBeginningDate = new JLabel(c4Utilities.CIFORProperties.beginningDate);
		textBeginningDate = new javax.swing.JTextField();
		textBeginningDate.setEditable(false);
		textBeginningDate.setText(DynamicClusterParameters.stringBeginningDate);
	    labelBeginningDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textBeginningDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textBeginningDate.setBorder(null);
		  
		textReportSelection = (DynamicClusterParameters.vReportSelection);
	    textReportSelection.setHorizontalAlignment(javax.swing.JTextField.CENTER);
	    textReportSelection.setEditable(false);
	    textReportSelection.setFont(new Font("Tahoma", Font.BOLD, 16));
	    textReportSelection.setBorder(null);
	    
	    String tooltipText;
	
	    tooltipText = c4Utilities.CIFORProperties.crReportIncludePageHeader;
	    headerCkBox = new JCheckBox(c4Utilities.CIFORProperties.crReportIncludeHeader, true);
	    headerCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
	    headerCkBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	            headerField.setEnabled(headerCkBox.isSelected());
	        }
	    });
	    headerCkBox.setToolTipText(tooltipText);
	    tooltipText = c4Utilities.CIFORProperties.crReportIncludePageNo;
	    
		if (DynamicClusterParameters.ckboxAllReports.isSelected()) 
			{ckBoxStatus = "Y";}
		else
			{ckBoxStatus = "N";}
		
		if (DynamicClusterParameters.ckboxRegion.isSelected()) 
			{regionStatus = "Y";}
		else
			{regionStatus = "N";}
		
        if (ckBoxStatus == "Y" && regionStatus == "Y") 				// Show Today's Reports and Show Regions
	        {
	        	headerField = new JTextField(c4Utilities.CIFORProperties.crReportHeading
	        			+ " By " + c4Utilities.UserProperties.RegionName
	        			+ c4Utilities.CIFORProperties.crReportTodaysReports);
	        }
        else
        	if (ckBoxStatus == "Y" && regionStatus == "N")			// Show Today's Reports and NO Regions
        		{
        			headerField = new JTextField(c4Utilities.CIFORProperties.crReportHeading
        					+ c4Utilities.CIFORProperties.crReportTodaysReports);
        		}
            else
            	if (ckBoxStatus == "N" && regionStatus == "Y")		// Show All Reports and Show Regions
            		{
            			headerField = new JTextField(c4Utilities.CIFORProperties.crReportHeading
        	        			+ " By " + c4Utilities.UserProperties.RegionName
        	        			+ c4Utilities.CIFORProperties.crReportAllReports);
            		}
                else
                	if (ckBoxStatus == "N" && regionStatus == "N")	// Show All Reports and NO Regions
                		{
                			headerField = new JTextField(c4Utilities.CIFORProperties.crReportHeading
                					+ c4Utilities.CIFORProperties.crReportAllReports);
                		}

	    headerField.setFont(new Font("Tahoma", Font.BOLD, 12));
	    headerField.setToolTipText(tooltipText);
	
	    tooltipText = c4Utilities.CIFORProperties.crReportIncludeFooter;
	    footerCkBox = new JCheckBox(c4Utilities.CIFORProperties.crReportFooter, true);
	    footerCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
	    footerCkBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	            footerField.setEnabled(footerCkBox.isSelected());
	        }
	    });

	    String currentDate = textCurrentDate.getText(); 	// get string date from text date
	    String beginningDate = textBeginningDate.getText(); // get string date from text date
	    footerField = new JTextField(c4Utilities.CIFORProperties.crReportDateRangeFrom + " "
	    		+ beginningDate + " " + c4Utilities.CIFORProperties.crReportDateRangeTo + " "  + currentDate
	    		+ "                  " + c4Utilities.CIFORProperties.crReportReportPage);
	    footerField.setFont(new Font("Tahoma", Font.BOLD, 12));
	    excelFooter = c4Utilities.CIFORProperties.crReportDateRangeFrom + beginningDate
	    		+ c4Utilities.CIFORProperties.crReportDateRangeTo + currentDate;
	
	    tooltipText = c4Utilities.CIFORProperties.crReportPrintTheTable;
	    printButton = new JButton(c4Utilities.CIFORProperties.printButton);
	    printButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	    printButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
	    printButton.setToolTipText(tooltipText);
	      
	    String tooltipExcelText;
	    tooltipExcelText = c4Utilities.CIFORProperties.crReportCreateSpreadsheet;
	    excelButton = new JButton(c4Utilities.CIFORProperties.excelButton);
	    excelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	    excelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
	    excelButton.setToolTipText(tooltipExcelText);
	    
	    topPanel = new JPanel();
	    contentPanel = new JPanel();
	}
}