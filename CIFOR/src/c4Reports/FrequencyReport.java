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

package c4Reports;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.io.File;
import java.io.IOException;
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

public class FrequencyReport extends JFrame
{
	public FrequencyReport() {
	}

	private static final long serialVersionUID = 1L;

/*
 *  Create GUI Components
 */
	
		private 	static 	JPanel 		topPanel;
	    private 	static 	JPanel 		contentPanel;
	    private 	static 	JLabel 		labelReportTitle;
	    private 	static 	JLabel 		labelCurrentDate;
	    private 	static 	JLabel 		labelBeginningDate;
	    private 	static 	JCheckBox 	fitWidthCkBox;
	    private 	static 	JButton 	printButton;
	    private		static	JButton		excelButton;
	    protected 	static 	JCheckBox 	headerCkBox;
	    protected 	static 	JCheckBox 	footerCkBox;
	    protected 	static 	JTextField 	headerField;
	    protected 	static 	JTextField 	footerField;
	    public 		static 	JTextField 	textCurrentDate;
	    public 		static 	JTextField 	textBeginningDate;
	    public 		static 	JTextField 	textReportSelection;
	    public 		static 	Date 		newCurrentDate;
	    public 		static 	Date 		newBeginDate;
	    
	    public		static	String		addRecord;
	    public		static	Date		inputDateReported;
	    public		static	String		beginningDateString;
	    public		static	String		currentDateString;
	    public		static	String		dateReportedString;
	    public		static	String		reportedString;
	    public		static	String		currentMo;
	    public		static	String		currentDy;
	    public		static	String		currentYr;
	    public		static	String		beginningMo;
	    public		static	String		beginningDy;
	    public		static	String		beginningYr;
	    public		static	String		reportedMo;
	    public		static	String		reportedDy;
	    public		static	String		reportedYr;
	    public		static	String		goodDate;
	    
	    public		static	int			currentMonthCounter;
	    public		static	int			previousMonthCounter;
	    public		static	int			priorMonthCounter;
	    public		static	int			currentYTDCounter;
	    public		static	int			oneYearOldCounter;
	    public		static	int			twoYearOldCounter;
	    public		static	int			threeYearOldCounter;
	    public		static	int			fourYearOldCounter;
	    
	    public		static	String		inputTestField;
	    public		static	String		lastTestField = "";
	    
	    public		static	String		strOrganism;
	    public		static	String		strPrimaryEnzyme;
	    public		static	String		strSecondaryEnzyme;
	    public		static	String		strOther;
	    
	    public		static	String		firstRecord;
	    public		static	String		printThisLine;
	    public		static JTextField	blank;
	    
	    public		static	String		cMoHeading;
	    public		static	String		p1MoHeading;
	    public		static	String		p2MoHeading;
	    public		static	String		cYrHeading;
	    public		static	String		p1YrHeading;
	    public		static	String		p2YrHeading;
	    public		static	String		p3YrHeading;
	    public		static	String		p4YrHeading;
	    
	    static 		PreparedStatement 	psDates;

	    public		static	String		excelDate;
	    public		static	String		fileName;
	    public		static	File		excelFile;
		public		static	String		excelFooter;
 
		/*
		 *  "main" section of the program
		 */	  	
	    public static void main( String [] argv ) throws Exception
	    {

			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();
			
			int newWidth = d.width;
			int location = 0;
			
			if 		(d.width > 1900) {newWidth = d.width-185; location = 50;}  //205
			else if	(d.width < 1900) {newWidth = d.width-60; location = 20;}	//30
			
			FrequencyReport frame = new FrequencyReport();
			frame.setSize(newWidth+10, d.height-100); 
			frame.setLocation(location, 20);
	    	frame.setTitle(c4Utilities.CIFORProperties.fAnalysisTitle);
	    	frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    	JPanel jPanel = new JPanel( new BorderLayout() );

	    	initValues();
	    	setParameterDates();
	    	 	
		/*
		 *  Establish a connection to the database
		 */    	
    	c4DataBase.DBUpdate.DataBaseConnection();
		
		/*
		 *   Setup Row and Column Vectors for the database
		 */		
		Vector<String> Columns = new Vector<String>();
		Vector<Vector<Object>> Rows = new Vector<Vector<Object>>();

		/*
		 *   Get the parameter dates and set the MO, DY and YYYY for comparison to REPORTED_DATE
		 */
		// Set the current date and the current MO and YYYY values	  
		String currentString = FrequencyReportParameters.stringCurrentDate;
		SimpleDateFormat cdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateCurrentDate = null;
			try
				{
					dateCurrentDate = cdf.parse(currentString);
					cdf.applyPattern("yyyy-MM-dd");
					currentString = cdf.format(dateCurrentDate);
				}
			catch (Exception e){
				e.printStackTrace();
				}
	
	        SimpleDateFormat cmo = new SimpleDateFormat("MM"); // two digit numerical representation
	        SimpleDateFormat cdy = new SimpleDateFormat("dd"); // two digit numerical representation
	        SimpleDateFormat cyr = new SimpleDateFormat("yyyy"); // four digit numerical representation  
			try
			{
				currentMo = cmo.format(dateCurrentDate);
				currentDy = cdy.format(dateCurrentDate);
				currentYr = cyr.format(dateCurrentDate);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		// Set the beginning date and the beginning MO and YYYY values	
		String beginningString = FrequencyReportParameters.stringBeginningDate;
		SimpleDateFormat bdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateBeginningDate = null;
			try
				{
				dateBeginningDate = bdf.parse(beginningString);
					bdf.applyPattern("yyyy-MM-dd");
					beginningString = bdf.format(dateBeginningDate);
				}
			catch (Exception e){
				e.printStackTrace();
				}
	
        SimpleDateFormat bmo = new SimpleDateFormat("MM"); // two digit numerical representation
        SimpleDateFormat byr = new SimpleDateFormat("yyyy"); // four digit numerical representation  
		try
		{
			beginningMo = bmo.format(dateBeginningDate);
			beginningDy = bmo.format(dateBeginningDate);
			beginningYr = byr.format(dateBeginningDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		/*
		 *   Get the Month and Year for the report column headings
		 */
		String[] monthName = {c4Utilities.CIFORProperties.fAnalysisJanuary, c4Utilities.CIFORProperties.fAnalysisFebruary, 
								c4Utilities.CIFORProperties.fAnalysisMarch, c4Utilities.CIFORProperties.fAnalysisApril, 
								c4Utilities.CIFORProperties.fAnalysisMay, c4Utilities.CIFORProperties.fAnalysisJune, 
								c4Utilities.CIFORProperties.fAnalysisJuly, c4Utilities.CIFORProperties.fAnalysisAugust, 
								c4Utilities.CIFORProperties.fAnalysisSeptember, c4Utilities.CIFORProperties.fAnalysisOctober,
								c4Utilities.CIFORProperties.fAnalysisNovember, c4Utilities.CIFORProperties.fAnalysisDecember,};
		
		int mon1 = Integer.parseInt(currentMo) -1;
			cMoHeading = monthName[mon1] + " MTD";

		if (mon1 > 1) 
		{
			int mon2 = Integer.parseInt(currentMo) -2; 
			p1MoHeading = monthName[mon2];
			int mon3 = Integer.parseInt(currentMo) -3; 
			p2MoHeading = monthName[mon3];
		}
		else if (mon1 == 1)
		{
			int mon2 = Integer.parseInt(currentMo) -2; 
			p1MoHeading = monthName[mon2];
			int mon3 = Integer.parseInt(currentMo) +9;
			p2MoHeading = monthName[mon3];
		}
		else if (mon1 == 0)
		{
			int mon2 = Integer.parseInt(currentMo) +10; 
			p1MoHeading = monthName[mon2];
			int mon3 = Integer.parseInt(currentMo) +9; 
			p2MoHeading = monthName[mon3];
		}
				
		cYrHeading = currentYr + " " + c4Utilities.CIFORProperties.fAnalysisYTD;
		int p1yr = Integer.parseInt(currentYr) -1;
	    p1YrHeading = Integer.toString(p1yr);
		int p2yr  = Integer.parseInt(currentYr) -2;
	    p2YrHeading = Integer.toString(p2yr);
		int p3yr  = Integer.parseInt(currentYr) -3;
	    p3YrHeading = Integer.toString(p3yr);
		int p4yr  = Integer.parseInt(currentYr) -4;
	    p4YrHeading = Integer.toString(p4yr);


		/*
		 *   Create the String component for the database Query 
		 */		
	    ResultSet rs = null;
	    PreparedStatement pstmt = null;
		    
		String frequencyQuery = 	
			"SELECT  ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, DATE_REPORTED " +
			"FROM CIFOR_LAB_RESULT " +
			"ORDER BY ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, DATE_REPORTED DESC";
			
		pstmt = c4DataBase.DBUpdate.connection.prepareStatement(frequencyQuery);
		rs = pstmt.executeQuery();								// execute the query

		ResultSetMetaData metaDt = rs.getMetaData();
		
	/*
	 *   Setup the columns for the GUI and the Report
	 */		
	int cols = metaDt.getColumnCount();
		Columns.clear(); 					// clear unwanted value if exist any in Columns variable.
		
        String[] stringcolumnNames = 		// add the column names to the header
        { 
        	c4Utilities.CIFORProperties.fAnalysisOrganism,
        	c4Utilities.CIFORProperties.fAnalysisPrimaryEnzyme,
        	c4Utilities.CIFORProperties.fAnalysisSecondaryEnzyme,
        	c4Utilities.CIFORProperties.fAnalysisOtherResult,
        	cMoHeading,
        	p1MoHeading,
        	p2MoHeading,
        	cYrHeading,
        	p1YrHeading,
        	p2YrHeading,
        	p3YrHeading,
        	p4YrHeading
        };

        for(int i=0;i<stringcolumnNames.length;i++)
			Columns.addElement((String) stringcolumnNames[i]);


	/*
	 *   Fill the vectors with data from the query 
	 */        
	Rows.clear(); 			// clear unwanted values from the Rows Vector.
	
	int recordCounter = 0;
	
	while( rs.next())
	{
		Vector<Object> row = new Vector<Object>(cols);
		
		recordCounter ++;
		
		inputTestField = rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4);

		if (lastTestField.equals(inputTestField))
		{
			dateReportedString = rs.getString(5);		// Get the data from the row using the column index 
	  		if (FrequencyReportParameters.ckboxAllReports.isSelected()) {ckTodaysDate();} // Today's Reports Option
	  		else {ckDateRange();}
			ckDateAndAddToCounters();
		}
		else
		{
			if (firstRecord == "N")
				{
					row.addElement(strOrganism);
					row.addElement(strPrimaryEnzyme);
					row.addElement(strSecondaryEnzyme);
					row.addElement(strOther);
					printThisLine = "N";
					if (currentMonthCounter != 0) 	{row.addElement(currentMonthCounter); printThisLine="Y";} 	else {row.addElement(blank);}
					if (previousMonthCounter != 0) 	{row.addElement(previousMonthCounter);} else {row.addElement(blank);}
					if (priorMonthCounter != 0) 	{row.addElement(priorMonthCounter);} 	else {row.addElement(blank);}
					if (currentYTDCounter != 0) 	{row.addElement(currentYTDCounter);} 	else {row.addElement(blank);}
					if (oneYearOldCounter != 0) 	{row.addElement(oneYearOldCounter);} 	else {row.addElement(blank);}
					if (twoYearOldCounter != 0) 	{row.addElement(twoYearOldCounter);} 	else {row.addElement(blank);}
					if (threeYearOldCounter != 0) 	{row.addElement(threeYearOldCounter);} 	else {row.addElement(blank);}
					if (fourYearOldCounter != 0) 	{row.addElement(fourYearOldCounter);} 	else {row.addElement(blank);}
					
					currentMonthCounter = 0; previousMonthCounter = 0; priorMonthCounter = 0;
					currentYTDCounter = 0; oneYearOldCounter = 0; twoYearOldCounter = 0; threeYearOldCounter = 0; fourYearOldCounter = 0;
					
					if (goodDate == "Y") {Rows.addElement(row);}					
					
					lastTestField = inputTestField;
					strOrganism = rs.getString(1);
					strPrimaryEnzyme = rs.getString(2);
					strSecondaryEnzyme = rs.getString(3);
					strOther = rs.getString(4);
					dateReportedString = rs.getString(5);		// Get the data from the row using the column index 
					goodDate = "N";
			  		if (FrequencyReportParameters.ckboxAllReports.isSelected()) {ckTodaysDate();} // Today's Reports Option
		  			else {ckDateRange();}
					ckDateAndAddToCounters();
				}
			else if (firstRecord == "Y")
				{
					firstRecord = "N";
					goodDate = "N";
					
					strOrganism = rs.getString(1);
					strPrimaryEnzyme = rs.getString(2);
					strSecondaryEnzyme = rs.getString(3);
					strOther = rs.getString(4);
					
					dateReportedString = rs.getString(5);		// Get the data from the row using the column index 
			  		if (FrequencyReportParameters.ckboxAllReports.isSelected()) {ckTodaysDate();} // Today's Reports Option
			  		else {ckDateRange();}
					ckDateAndAddToCounters();
					lastTestField = inputTestField;
				}
		}
	}

	rs.close(); 			//close the Resultset.
	pstmt.close(); 			//close the PreparedStatement.

	/*
	 *   Close the database connection
	 */
    c4DataBase.DBUpdate.DatabaseClose();
	
	/*
	 *   Define the TableModel
	 */
	TableModel frequencyModel = new DefaultTableModel(Rows, Columns){

		private static final long serialVersionUID = 1L; };
		
		TableColumn col;
	
	/*
	 *   Create and set attributes for the JTable and clusterTable
	 */	
	final JTable frequencyTable = new JTable(frequencyModel);
	frequencyTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
    frequencyTable.setColumnSelectionAllowed(true);
	frequencyTable.setAutoCreateRowSorter(true);
	frequencyTable.setFillsViewportHeight(true);
	frequencyTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
	frequencyTable.setRowHeight(24);
    frequencyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
	JScrollPane scrollPane = new JScrollPane(frequencyTable);
	frequencyTable.setSize(newWidth, d.height-100);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
	/*
	 *   Define the table's column widths
	 */    
    TableColumn column = null;		
    
	for (int i = 0; i < 12; i++) 
	{
	    column = frequencyTable.getColumnModel().getColumn(i);
	    if 		(i == 0)  {column.setPreferredWidth(200);}		// Organism
	    else if (i == 1)  {column.setPreferredWidth(200);}		// Primary Enzyme Pattern
	    else if (i == 2)  {column.setPreferredWidth(200);}		// Secondary Enzyme Pattern
	    else if (i == 3)  {column.setPreferredWidth(200);}		// Other Result
	    else if (i == 4)  {column.setPreferredWidth(110);}		// Current MTD
	    else if (i == 5)  {column.setPreferredWidth(110);}		// Previous Month
	    else if (i == 6)  {column.setPreferredWidth(110);}		// Prior Month
	    else if (i == 7)  {column.setPreferredWidth(110);}		// Current YTD
	    else if (i == 8)  {column.setPreferredWidth(110);}		// One Year Old
	    else if (i == 9)  {column.setPreferredWidth(110);}		// Two Years Old
	    else if (i == 10)  {column.setPreferredWidth(110);}		// Three Years Old
	    else if (i == 11)  {column.setPreferredWidth(110);}		// Four Years Old
	}
    
	 // Set the cell justification
	 	col = frequencyTable.getColumnModel().getColumn(4);
	 	col.setCellRenderer(new FrequencyTableCellRenderer());
	 	col = frequencyTable.getColumnModel().getColumn(5);
	 	col.setCellRenderer(new FrequencyTableCellRenderer());
	 	col = frequencyTable.getColumnModel().getColumn(6);
	 	col.setCellRenderer(new FrequencyTableCellRenderer());
	 	col = frequencyTable.getColumnModel().getColumn(7);
	 	col.setCellRenderer(new FrequencyTableCellRenderer());
	 	col = frequencyTable.getColumnModel().getColumn(8);
	 	col.setCellRenderer(new FrequencyTableCellRenderer());
	 	col = frequencyTable.getColumnModel().getColumn(9);
	 	col.setCellRenderer(new FrequencyTableCellRenderer());
	 	col = frequencyTable.getColumnModel().getColumn(10);
	 	col.setCellRenderer(new FrequencyTableCellRenderer());
	 	col = frequencyTable.getColumnModel().getColumn(11);
	 	col.setCellRenderer(new FrequencyTableCellRenderer());
 	
	/*
	 *   Print the clusterTable
	 */	
	printButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	        	
	        	MessageFormat header = null;
	            if (headerCkBox.isSelected()) {								// if we should print a header
	                header = new MessageFormat(headerField.getText());		// create a MessageFormat around the header text
	            }

	            MessageFormat footer = null;								// if we should print a footer
	            if (footerCkBox.isSelected()) {								// create a MessageFormat around the footer text
	                footer = new MessageFormat(footerField.getText());
	            }
	        	
            try {
					frequencyTable.print	(JTable.PrintMode.FIT_WIDTH, 
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
		excelButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
        	
            try
            {
            	getDateTime();
	           
            	fileName = "C:/CIFOR/ExcelFiles/" + "FrequencyRpt-" + excelDate + ".xls";
            	fileName = c4Utilities.CIFORProperties.fAnalysisExcelDirectory
        				+ c4Utilities.CIFORProperties.fAnalysisExcelFileName
        				+ excelDate + ".xls";

	            WorkbookSettings ws = new WorkbookSettings();
	            ws.setLocale(new Locale(c4Utilities.CIFORProperties.localeLanguage, c4Utilities.CIFORProperties.localeCountry));
	            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName), ws);
	            WritableSheet s3 = workbook.createSheet(c4Utilities.CIFORProperties.fAnalysisExcelSheet3, 0);
	            WritableSheet s2 = workbook.createSheet(c4Utilities.CIFORProperties.fAnalysisExcelSheet2, 0);
	            WritableSheet s1 = workbook.createSheet(c4Utilities.CIFORProperties.fAnalysisExcelSheet1, 0);
	            writeSheetHeaders(s1);
	            writeSheetDetail(s1);
	            
		    	int line = 0;
		    	Object[][] array = new Object [frequencyTable.getRowCount()] [frequencyTable.getColumnCount()];  
		    	String string = null ;  
		    	Object object = new Object();
		   
		    	for ( int i = 0; i < frequencyTable.getRowCount() ; ++i) 
		    	{   
		    		line++;
		    		for ( int j = 0; j < frequencyTable.getColumnCount(); ++j) 
		    		{  
		    			array[i][j] = frequencyTable.getValueAt(i, j);  
		    			object = frequencyTable.getValueAt(i, j);
	    				if (object == null)
	    					{string = null;}
	    				else
	    					{string = object.toString();}

		    			if (j < 4)
		    			{
		    			    WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
		    				WritableCellFormat cf1 = new WritableCellFormat(wf1);
		    				cf1.setAlignment(Alignment.LEFT);
		    				cf1.setWrap(true);
		    				Label column1 = new Label(j,line,string,cf1);
		    				s1.addCell(column1);
		    			}
		    			else
		    			{
		    			    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
		    				WritableCellFormat cf2 = new WritableCellFormat(wf2);
		    				cf2.setAlignment(Alignment.RIGHT);
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
		        	LaunchExcel.fileName = fileName;
		        	LaunchExcel.main(null);
				} catch (Exception e) {
					e.printStackTrace();
				}				
        }
    });

	/*
	 *   Setup panels for the GUI
	 */	
	topPanel = new JPanel();
    contentPanel = new JPanel();
    
	/*
	 * Add components to the GUI layout for display.  The GUI was created with
	 * the Matisse GUI builder in NetBeans, so be careful when making changes!
	 */
	scrollPane.setPreferredSize(new java.awt.Dimension(newWidth, d.height));

	javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
    topPanel.setLayout(topPanelLayout);
    topPanelLayout.setHorizontalGroup(
         topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(topPanelLayout.createSequentialGroup()
             .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                 .addGroup(topPanelLayout.createSequentialGroup()
                     .addComponent(labelBeginningDate)
                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                     .addComponent(textBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                 .addGroup(topPanelLayout.createSequentialGroup()
                     .addComponent(labelCurrentDate)
                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                     .addComponent(textCurrentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 406, Short.MAX_VALUE)
             .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                 .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                     .addComponent(labelReportTitle)
                     .addGap(newWidth-855, newWidth-855, newWidth-855))  //820 //920
                 .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                     .addComponent(textReportSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addGap(newWidth-855, newWidth-855, newWidth-855))))  //835 //945
     );

    topPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textBeginningDate, textCurrentDate});

    topPanelLayout.setVerticalGroup(
         topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(topPanelLayout.createSequentialGroup()
             .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                 .addComponent(labelCurrentDate)
                 .addComponent(textCurrentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
             .addGap(15, 15, 15)
             .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                 .addComponent(labelBeginningDate)
                 .addComponent(textBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
         .addGroup(topPanelLayout.createSequentialGroup()
             .addComponent(labelReportTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
             .addComponent(textReportSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
     );

    topPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {textBeginningDate, textCurrentDate});
 	
JPanel bottomPanel = new JPanel();										
bottomPanel.setBorder(BorderFactory.createTitledBorder("  Print Options"));
 	
 	 javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
      bottomPanel.setLayout(bottomPanelLayout);
      bottomPanelLayout.setHorizontalGroup(
          bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(bottomPanelLayout.createSequentialGroup()
              .addContainerGap()
              .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(bottomPanelLayout.createSequentialGroup()
                      .addComponent(headerCkBox)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(headerField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGroup(bottomPanelLayout.createSequentialGroup()
                      .addComponent(footerCkBox)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                      .addComponent(footerField, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                      .addGap(18, 18, 18)
                      .addComponent(fitWidthCkBox)
                      .addGap(46, 46, 46)
                      .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                      .addGap(46, 46, 46)
                      .addComponent(excelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addContainerGap(newWidth-1150, Short.MAX_VALUE))  //505 //860 //800
      );
    bottomPanelLayout.setVerticalGroup(
        bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(bottomPanelLayout.createSequentialGroup()
              .addContainerGap()
              .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(headerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(headerCkBox))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(footerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(footerCkBox)
                  .addComponent(fitWidthCkBox)
                  .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                  
	                   .addComponent(excelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addContainerGap(25, Short.MAX_VALUE))
      );
        
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(contentPanel);
    contentPanel.setLayout(layout);
      layout.setHorizontalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                      .addContainerGap()
                      .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                      .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGroup(layout.createSequentialGroup()
                 	 .addContainerGap()
                      .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, newWidth-100, Short.MAX_VALUE))) // was 1392 - d.height-200
                      .addContainerGap())
      );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
              .addContainerGap()
              .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
              .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, d.height-370, javax.swing.GroupLayout.PREFERRED_SIZE) // was 600 //370
              .addGap(18, 18, 18)
              .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

	/*
	 *   Setup a jPanel for printing
	 */	       
	jPanel.add(contentPanel);
	frame.getContentPane().add(jPanel, BorderLayout.CENTER);
	frame.setVisible(true);
		
	}

	/*
	 *  Setup Excel Worksheet
	 */	    
    private static void writeSheetHeaders(WritableSheet s1) throws WriteException
      {
  		/* Set the Page Format, Header and Margins for the worksheet */
      	s1.getSettings().setPaperSize(PaperSize.LETTER);
      	s1.getSettings().setOrientation(PageOrientation.LANDSCAPE);
      	s1.getSettings().setFitWidth(1);
      	s1.getSettings().setFitToPages(true);
  	    s1.getSettings().setProtected(false);
  	    s1.getSettings().setVerticalFreeze(1);
  	    s1.getSettings().setDefaultRowHeight(300);
  	    s1.getSettings().setHeaderMargin(.25);
  	    s1.getSettings().setFooterMargin(.25);
  	       
  	    s1.getSettings().setTopMargin(.75);
  	    s1.getSettings().setBottomMargin(.5);
  	    s1.getSettings().setLeftMargin(.25);
  	    s1.getSettings().setRightMargin(.25);
  	    
  	    /* Add a header and footer */
  	    HeaderFooter header1 = new HeaderFooter();
  	    header1.getLeft().append(excelFooter);
  	    header1.getCentre().append(c4Utilities.CIFORProperties.fAnalysisHeading);
  	    s1.getSettings().setHeader(header1);
  	        
  	    HeaderFooter footer = new HeaderFooter();
  	    s1.getSettings().setFooter(footer);
  	    footer.getLeft().append(c4Utilities.CIFORProperties.fAnalysisExcelDatePrinted);
  	    footer.getLeft().appendDate();
  	    footer.getRight().append(c4Utilities.CIFORProperties.fAnalysisExcelPage);
  	    footer.getRight().appendPageNumber();
  	    
  		/* Format the Font, set cell wrap "true", set background to color */
  		WritableFont wf = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
  		WritableCellFormat cf = new WritableCellFormat(wf);
  		cf.setWrap(true);
  		cf.setBackground(Colour.GRAY_25);
  		
  		WritableCellFormat right = new WritableCellFormat(wf);
		right.setAlignment(Alignment.RIGHT);
  		right.setWrap(true);
  		right.setBackground(Colour.GRAY_25);

  		/* Creates Labels for each column*/
  		Label column0 = new Label(0,0,c4Utilities.CIFORProperties.fAnalysisOrganism,cf);
  		s1.addCell(column0);
  		s1.setColumnView(0, 70);
  		
  		Label column1 = new Label(1,0,c4Utilities.CIFORProperties.fAnalysisPrimaryEnzyme,cf);
  		s1.addCell(column1);
  		s1.setColumnView(1, 25);
  		
  		Label column2 = new Label(2,0,c4Utilities.CIFORProperties.fAnalysisSecondaryEnzyme,cf);
  		s1.addCell(column2);
  		s1.setColumnView(2, 25);
  		
  		Label column3 = new Label(3,0,c4Utilities.CIFORProperties.fAnalysisOtherResult,cf);
  		s1.addCell(column3);
  		s1.setColumnView(3, 30);
  		
  		Label column4 = new Label(4,0,cMoHeading,right);
  		s1.addCell(column4);
  		s1.setColumnView(4, 15);
  				
  		Label column5 = new Label(5,0,p1MoHeading,right);
  		s1.addCell(column5);
  		s1.setColumnView(5, 12);
  		
  		Label column6 = new Label(6,0,p2MoHeading,right);
		s1.addCell(column6);
		s1.setColumnView(6, 12);
  		
  		Label column7 = new Label(7,0,cYrHeading,right);
		s1.addCell(column7);
		s1.setColumnView(7, 12);  			
  		
  		Label column8 = new Label(8,0,p1YrHeading,right);
		s1.addCell(column8);
		s1.setColumnView(8, 8); 
  		
  		Label column9 = new Label(9,0,p2YrHeading ,right);
  		s1.addCell(column9);
  		s1.setColumnView(9, 8);
  	
  		Label column10 = new Label(10,0,p3YrHeading ,right);
  		s1.addCell(column10);
  		s1.setColumnView(10, 8);
  		
  		Label column11 = new Label(11,0,p4YrHeading ,right);
  		s1.addCell(column11);
  		s1.setColumnView(11, 8);
	  }
    	    
	/*
	 *  Write the Excel sheet
	 */
    private static void writeSheetDetail(WritableSheet s1) throws WriteException
      {

  		/* Format the Font */
  	    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
  	    WritableCellFormat cf2 = new WritableCellFormat(wf2);
  	    cf2.setWrap(true);
      }
    	    
	/*
	 *  Get the date and time for the name of the Excel spreadsheet
	 */      
    private static String getDateTime() 
      {
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          Date date = new Date();
          
          DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
          excelDate = formatter.format(date);

          return dateFormat.format(date);
      }

	/*
	 *   Check to see if the REPORTED_DATE is within the parameter date range
	 */  	    
  	private static void ckDateAndAddToCounters()
  	{
	// set the REPORTED_DATE and get the MM and YYYY values
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedReportedDate = null;
		try {
				convertedReportedDate = dateFormat.parse(dateReportedString);
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}

        SimpleDateFormat rptdmo = new SimpleDateFormat("MM"); // two digit numerical representation
        SimpleDateFormat rptddy = new SimpleDateFormat("dd"); // two digit numerical representation
        SimpleDateFormat rptdyr = new SimpleDateFormat("yyyy"); // four digit numerical representation  
		try
		{
			reportedMo = rptdmo.format(convertedReportedDate);
			reportedDy = rptddy.format(convertedReportedDate);
			reportedYr = rptdyr.format(convertedReportedDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	    // compare the ReportedMO & YR to the parameter date range
		// Note! && = "and", || = "or", ^ = "either or"	
	    int cmo = Integer.parseInt(currentMo);
	    int rmo = Integer.parseInt(reportedMo);
	    int moDiff = cmo - rmo;
	    int cyr = Integer.parseInt(currentYr);
	    int ryr = Integer.parseInt(reportedYr);
	    int yrDiff = cyr - ryr;
	    		
		if (moDiff == 0 && yrDiff == 0 && goodDate == "Y") 		// current month and year = reported month and year
		{ 
			currentMonthCounter++; 
			currentYTDCounter++;
		}
		
		else if (moDiff == 1 && yrDiff == 0)	// reported month is 1 month older than current month										 
		{										// and current year = reported year, unless we are		
			previousMonthCounter++; 			// crossing a year ie; Jan & Dec and 2012 & 2011
			currentYTDCounter++;
		}
		
		else if (moDiff == 2 && yrDiff == 0)	// reported month is 2 months older than current month										 
		{										// and current year = reported year, unless we are					
			priorMonthCounter++; 				// crossing a year ie; Jan & Dec and 2012 & 2011
			currentYTDCounter++;
		}
		
		else if (moDiff == -11 && yrDiff == 1)	// reported month is 1 month older than current month										 
		{										//  and current year is 1 greater than reported year
			previousMonthCounter++; 
			oneYearOldCounter++;
		}
		
		else if (moDiff == -10 && yrDiff == 1)	// reported month is 1 month older than current month										 
		{										//  and current year is 2 greater than reported year
			priorMonthCounter++; 
			oneYearOldCounter++;
		}

		else if (yrDiff == 0 && moDiff >= 0 && goodDate == "Y")	// not last 3 months, but reported year is = current Year										 
		{										// and the reported month is not greater than the current month 				
			currentYTDCounter++;				// ie; current date = 11/20/2011 and reported date = 12/15/2011
		}
		
		else if (yrDiff == 1)					// reported year is 1 older than current Year										 
		{	
			oneYearOldCounter++;
		}
		
		else if (yrDiff == 2)					// reported year is 2 older than current Year										 
		{	 
			twoYearOldCounter++;
		}
		
		else if (yrDiff == 3)					// reported year is 2 older than current Year										 
		{	 
			threeYearOldCounter++;
		}
		
		else if (yrDiff == 4)					// reported year is 2 older than current Year										 
		{	 
			fourYearOldCounter++;
		}		
    }
    
	/*
	 *   Today's Reports Only - Check to see if the REPORTED_DATE is = to the Current Date
	 */      	    
  	private static void ckTodaysDate()
      	{
  			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  	    Date convertedReportedDate = null;
	  		try 
	  		{
	  			convertedReportedDate = dateFormat.parse(dateReportedString);
	  		} 
	  			catch (ParseException e) 
	  			{
	  				e.printStackTrace();
	  			}
	  		SimpleDateFormat rptdDate = new SimpleDateFormat("yyyy-MM-dd"); // two digit numerical representation
	  		try
	  		{
	  			reportedString = rptdDate.format(convertedReportedDate);
	  		}
	  			catch (Exception e)
		  		{
		  			e.printStackTrace();
		  		}
	  		
	    	if (reportedString.equals(currentDateString)) {goodDate = "Y";}
      	}
    
	/*
	 *   Today's Reports Only - Check to see if the REPORTED_DATE is = to the Current Date
	 */        	    
	private static void ckDateRange()
    	{

	  		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  	    Date convertedReportedDate = null;
	  		try 
	  		{
	  			convertedReportedDate = dateFormat.parse(dateReportedString);
	  		} 
	  			catch (ParseException e) 
	  			{
	  				e.printStackTrace();
	  			}
	  		SimpleDateFormat rptdDate = new SimpleDateFormat("yyyy-MM-dd"); // two digit numerical representation
	  		try
	  		{
	  			reportedString = rptdDate.format(convertedReportedDate);
	  		}
	  			catch (Exception e)
		  		{
		  			e.printStackTrace();
		  		}
	  			
	  		int currentResult = reportedString.compareTo(currentDateString);
	  		int beginningResult = reportedString.compareTo(beginningDateString);
	  		
	  		if (currentResult < 0 && beginningResult > 0) {goodDate = "Y";}
	  		else if (currentResult == 0) {goodDate = "Y";}
	  		else if (beginningResult == 0) {goodDate = "Y";}
	    	
    	}
    	
		/*
		 *   Get the parameter dates and set the MO, DY and YYYY for comparison to REPORTED_DATE
		 */        
		private static void setParameterDates()
			{
		    	// Set the current date and the current MO and YYYY values	  
		  		currentDateString = FrequencyReportParameters.stringCurrentDate;
		  		SimpleDateFormat cdf = new SimpleDateFormat("MM/dd/yyyy");
		  		java.util.Date dateCurrentDate = null;
		  			try
		  				{
		  					dateCurrentDate = cdf.parse(currentDateString);
		  					cdf.applyPattern("yyyy-MM-dd");
		  					currentDateString = cdf.format(dateCurrentDate);
		  				}
		  			catch (Exception e){
		  				e.printStackTrace();
		  				}
		  	
		  	        SimpleDateFormat cmo = new SimpleDateFormat("MM"); // two digit numerical representation
		  	        SimpleDateFormat cdy = new SimpleDateFormat("dd"); // two digit numerical representation
		  	        SimpleDateFormat cyr = new SimpleDateFormat("yyyy"); // four digit numerical representation
		  			try
		  			{
		  				currentMo = cmo.format(dateCurrentDate);
		  				currentDy = cdy.format(dateCurrentDate);
		  				currentYr = cyr.format(dateCurrentDate);
		  			}
		  			catch (Exception e)
		  			{
		  				e.printStackTrace();
		  			}
		
		  		// Set the beginning date and the beginning MO and YYYY values	
		  		beginningDateString = FrequencyReportParameters.stringBeginningDate;
		  		SimpleDateFormat bdf = new SimpleDateFormat("MM/dd/yyyy");
		  		java.util.Date dateBeginningDate = null;
		  			try
		  				{
		  				dateBeginningDate = bdf.parse(beginningDateString);
		  					bdf.applyPattern("yyyy-MM-dd");
		  					beginningDateString = bdf.format(dateBeginningDate);
		  				}
		  			catch (Exception e){
		  				e.printStackTrace();
		  				}
		  	
		          SimpleDateFormat bmo = new SimpleDateFormat("MM"); // two digit numerical representation
		          //SimpleDateFormat bdy = new SimpleDateFormat("dd"); // two digit numerical representation
		          SimpleDateFormat byr = new SimpleDateFormat("yyyy"); // four digit numerical representation  
		  		try
		  		{
		  			beginningMo = bmo.format(dateBeginningDate);
		  			beginningDy = bmo.format(dateBeginningDate);
		  			beginningYr = byr.format(dateBeginningDate);
		  		}
		  		catch (Exception e)
		  		{
		  			e.printStackTrace();
		  		}
		    }

	/*
	 *   Initialize values and get parameters passed from the parameter program
	 */		
	private static void initValues() 
		{
			firstRecord = "Y";
			
			labelCurrentDate = new JLabel(c4Utilities.CIFORProperties.currentDate);
			textCurrentDate = new javax.swing.JTextField();
			textCurrentDate.setEditable(false);
			textCurrentDate.setText(FrequencyReportParameters.stringCurrentDate);
			
			labelBeginningDate = new JLabel(c4Utilities.CIFORProperties.beginningDate);
			textBeginningDate = new javax.swing.JTextField();
			textBeginningDate.setEditable(false);
			textBeginningDate.setText(FrequencyReportParameters.stringBeginningDate);
			  
			textReportSelection = (FrequencyReportParameters.vReportSelection);
			
			labelReportTitle = new JLabel(c4Utilities.CIFORProperties.fAnalysisHeading);
			labelReportTitle.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			labelReportTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
			
		    textReportSelection.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		    textReportSelection.setEditable(false);
		    
		    textReportSelection.setFont(new Font("Tahoma", Font.BOLD, 16));
		    textReportSelection.setBorder(null);
		    
		    labelBeginningDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		    textBeginningDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		    textBeginningDate.setBorder(null);
		    
		    labelCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		    textCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		    textCurrentDate.setBorder(null);
		    
		    String tooltipText;
		
		    tooltipText = c4Utilities.CIFORProperties.fAnalysisIncludePageHeader;
		    headerCkBox = new JCheckBox(c4Utilities.CIFORProperties.fAnalysisIncludeHeader, true);
		    headerCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		    headerCkBox.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent ae) {
		            headerField.setEnabled(headerCkBox.isSelected());
		        }
		    });
		    headerCkBox.setToolTipText(tooltipText);
		    tooltipText = c4Utilities.CIFORProperties.fAnalysisIncludePageNo;
		    
	        if (FrequencyReportParameters.ckboxAllReports.isSelected()) 
	        {
	        	headerField = new JTextField(c4Utilities.CIFORProperties.fAnalysisTodaysReports);
	        }
	    else
	        {
	    		headerField = new JTextField(c4Utilities.CIFORProperties.fAnalysisAllReports);
	        }
	
		    headerField.setFont(new Font("Tahoma", Font.BOLD, 12));
		    headerField.setToolTipText(tooltipText);
		
		    tooltipText = c4Utilities.CIFORProperties.fAnalysisIncludeFooter;
		    footerCkBox = new JCheckBox(c4Utilities.CIFORProperties.fAnalysisFooter, true);
		    footerCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		    footerCkBox.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent ae) {
		            footerField.setEnabled(footerCkBox.isSelected());
		        }
		    });
		    footerCkBox.setToolTipText(tooltipText);
		    tooltipText = "Page Footer (Use {0} to Include Page Number)";
		    String currentDate = textCurrentDate.getText(); 	// get string date from text date
		    String beginningDate = textBeginningDate.getText(); // get string date from text date
		    footerField = new JTextField(c4Utilities.CIFORProperties.fAnalysisDateRangeFrom + " "
		    		+ beginningDate + " " + c4Utilities.CIFORProperties.fAnalysisDateRangeTo + " "  + currentDate
		    		+ "                  " + c4Utilities.CIFORProperties.fAnalysisReportPage);
		    footerField.setFont(new Font("Tahoma", Font.BOLD, 12));
		    footerField.setToolTipText(tooltipText);
		    excelFooter = c4Utilities.CIFORProperties.fAnalysisDateRangeFrom + beginningDate
		    		+ c4Utilities.CIFORProperties.fAnalysisDateRangeTo + currentDate;
		
		    tooltipText = c4Utilities.CIFORProperties.fAnalysisShrinkToFit;
		    fitWidthCkBox = new JCheckBox(c4Utilities.CIFORProperties.fAnalysisFitWidth, true);
		    fitWidthCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		    fitWidthCkBox.setToolTipText(tooltipText);
		
		    tooltipText = c4Utilities.CIFORProperties.fAnalysisPrintTheTable;
		    printButton = new JButton(c4Utilities.CIFORProperties.printButton);
		    printButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		    printButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		    printButton.setToolTipText(tooltipText);
		      
		    String tooltipExcelText;
		    tooltipExcelText = c4Utilities.CIFORProperties.fAnalysisCreateSpreadsheet;
		    excelButton = new JButton(c4Utilities.CIFORProperties.excelButton);
		    excelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		    excelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		    excelButton.setToolTipText(tooltipExcelText);
		    
		    topPanel = new JPanel();
		    contentPanel = new JPanel();
		}
}