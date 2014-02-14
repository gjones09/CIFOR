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

package c4ClusterReport;

import javax.swing.*;
import javax.swing.table.*;
import c4Utilities.UserProperties;
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

public class ClusterReport extends JFrame
{
		private static final long serialVersionUID = 1L;

		private 	static 	JPanel 		topPanel;
	    private 	static 	JPanel 		contentPanel;
	    private 	static 	JLabel 		labelReportTitle;
	    private 	static 	JLabel 		labelCurrentDate;
	    private 	static 	JLabel 		labelBeginningDate;
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
	    public 		static	String		ckBoxStatus;
	    
	    public		static	String		excelDate;
	    public		static	String		fileName;
	    public		static	File		excelFile;
		public		static	String		excelFooter;
	    public		static	String		lastTestField = "";
	    public		static	String		inputTestField;
	    public		static	Date		inputDateReported;
	    public		static	String		beginningDateString;
	    public		static	String		currentDateString;
	    public		static	String		dateReportedString;
	    public		static	String		reportedString;
	    public		static	String		currentString;
	    public		static	String		currentMo;
	    public		static	String		currentDy;
	    public		static	String		currentYr;
	    public		static	String		beginningString;
	    public		static	String		beginningMo;
	    public		static	String		beginningDy;
	    public		static	String		beginningYr;
	    public		static	String		reportedMo;
	    public		static	String		reportedDy;
	    public		static	String		reportedYr;
	    public		static	String		goodDate;
		public 		static 	String 		clusterQuery;
		public 		static 	String 		pEP1;
		public 		static 	String 		sEP1;
		public 		static 	String 		pEP2;
		public 		static 	String 		sEP2;
		public 		static 	String 		rptPEP1;
		public 		static 	String 		rptSEP1;
		public 		static 	String 		rptPEP2;
		public 		static 	String 		rptSEP2;
		public		static	int			fieldWidth;
		
		/**
		 *  "main" section of the program
		 */	  	
	    public static void main( String [] argv ) throws Exception
	    { 
	    	new ClusterReport();

	    	GUI_Layout.ckDimensions();
			ClusterReport frame = new ClusterReport();
			frame.setSize(GUI_Layout.newWidth, GUI_Layout.newHeight);
			frame.setLocation(GUI_Layout.insetX, GUI_Layout.insetY);
	    	frame.setTitle(c4Utilities.CIFORProperties.cReportTitle);
	    	frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    	JPanel jPanel = new JPanel( new BorderLayout() );
	    	
	    	initValues();
	    	setParameterDates();

		/**
		 *  Establish a connection to the database
		 */    	
    	c4DataBase.DBUpdate.DataBaseConnection();
		
		/**
		 *   Setup Row and Column Vectors for the database
		 */		
		Vector<String> Columns = new Vector<String>();
		Vector<Vector<Object>> Rows = new Vector<Vector<Object>>();

		/**
		*  Get the parameter dates and set the MO, DY and YYYY for comparison to REPORTED_DATE
		*  Setup the date fields for the SQL Query
		*/	  
		currentString = Parameters.stringCurrentDate;
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
			java.sql.Date newDateCurrentDate = java.sql.Date.valueOf(currentString);
	
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
		beginningString = Parameters.stringBeginningDate;
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
			java.sql.Date newDateBeginningDate = java.sql.Date.valueOf(beginningString);
	
        SimpleDateFormat bmo = new SimpleDateFormat("MM"); 		// two digit numerical representation
        SimpleDateFormat byr = new SimpleDateFormat("yyyy"); 	// four digit numerical representation
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
		
		/**
		 *   Create the String component for the database Query 
		 */	
	    ResultSet rs = null;
	    PreparedStatement pstmt = null;
		
		if(Parameters.sortByRegionSelected == false)
		    	{
				clusterQuery =	
					"SELECT  REGION, LAB_SPECIMEN_ID, FIRST_NAME, LAST_NAME, " +
						"CITY, STATE, COUNTY, AGE, " +
						"ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, " +
						"TO_CHAR(DATE_COLLECTED,'yyyy-MM-dd') DATE_COLLECTED, " +
						"TO_CHAR(DATE_RECEIVED,'yyyy-MM-dd') DATE_RECEIVED, " +
						"TO_CHAR(DATE_REPORTED,'yyyy-MM-dd') DATE_REPORTED, " +
						"CDC_CLUSTER_CODE, LOCAL_PRIMARY_ENZYME_PATTERN, LOCAL_SECONDARY_ENZYME_PATTERN " +
					"FROM CIFOR_LAB_RESULT " +
					"JOIN	CIFOR_DEMOGRAPHICS ON CIFOR_LAB_RESULT.DEMOGRAPHICS_ID = CIFOR_DEMOGRAPHICS.ID " +
					"WHERE	DATE_REPORTED <= ? " +
					"AND 	DATE_REPORTED >= ? " +
					"ORDER BY ORGANISM, ?, DATE_REPORTED DESC";					// order by organism, primary pattern, date rptd
		    	}
		    else
				{
				clusterQuery =
					"SELECT  REGION, LAB_SPECIMEN_ID, FIRST_NAME, LAST_NAME, " +
						"CITY, STATE, COUNTY, AGE, " +
						"ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, " +
						"TO_CHAR(DATE_COLLECTED,'yyyy-MM-dd') DATE_COLLECTED, " +
						"TO_CHAR(DATE_RECEIVED,'yyyy-MM-dd') DATE_RECEIVED, " +
						"TO_CHAR(DATE_REPORTED,'yyyy-MM-dd') DATE_REPORTED, " +
						"CDC_CLUSTER_CODE, LOCAL_PRIMARY_ENZYME_PATTERN, LOCAL_SECONDARY_ENZYME_PATTERN " +
					"FROM CIFOR_LAB_RESULT " +
					"JOIN	CIFOR_DEMOGRAPHICS ON CIFOR_LAB_RESULT.DEMOGRAPHICS_ID = CIFOR_DEMOGRAPHICS.ID " +
					"WHERE	DATE_REPORTED <= ? " +
					"AND 	DATE_REPORTED >= ? " +
					"ORDER BY REGION, ORGANISM, ?, DATE_REPORTED DESC";		// order by region, organism, primary pattern, date rptd
				}
			
		pstmt = c4DataBase.DBUpdate.connection.prepareStatement(clusterQuery);
		pstmt.setDate(1, newDateCurrentDate); 				// set input parameter
		pstmt.setDate(2, newDateBeginningDate); 			// set input parameter
		pstmt.setString(3, UserProperties.PrimaryPattern);	// set the Primary Pattern for the sort
		rs = pstmt.executeQuery();							// execute the query

		ResultSetMetaData metaDt = rs.getMetaData();
		
	/**
	 *   Get the proper heading for the CDC and Local pattern Choice
	 *   Setup the columns for the GUI and the Report
	 */			    
	if(c4Utilities.UserProperties.PrimaryPattern.equals("CDC")) 
		{
			rptPEP1=c4Utilities.UserProperties.ReportPrimaryEnzymeCDC;
			rptSEP1=c4Utilities.UserProperties.ReportSecondaryEnzymeCDC;
			rptPEP2=c4Utilities.UserProperties.ReportPrimaryEnzymeLocal;
			rptSEP2=c4Utilities.UserProperties.ReportSecondaryEnzymeLocal;
		}
	else
		if(c4Utilities.UserProperties.PrimaryPattern.equals("Local"))
			{
				rptPEP2=c4Utilities.UserProperties.ReportPrimaryEnzymeLocal;
				rptSEP2=c4Utilities.UserProperties.ReportSecondaryEnzymeLocal;
				rptPEP1=c4Utilities.UserProperties.ReportPrimaryEnzymeCDC;
				rptSEP1=c4Utilities.UserProperties.ReportSecondaryEnzymeCDC;
			}
	
	int cols = metaDt.getColumnCount();
	
		Columns.clear(); 					// clear unwanted value if exist any in Columns variable.
		
        String[] stringcolumnNames = 		// add the column names to the header
        { 
        	c4Utilities.CIFORProperties.crReportRegion,
        	c4Utilities.CIFORProperties.crReportAccessionNo,
        	c4Utilities.CIFORProperties.crReportFirstName,
        	c4Utilities.CIFORProperties.crReportLastName,
        	c4Utilities.CIFORProperties.crReportCity,
        	c4Utilities.CIFORProperties.crReportState,
        	c4Utilities.CIFORProperties.crReportCounty,
        	c4Utilities.CIFORProperties.crReportAge,
        	c4Utilities.CIFORProperties.crReportOrganism,
        	rptPEP1,
        	rptSEP1,
        	rptPEP2,
        	rptSEP2,
        	c4Utilities.CIFORProperties.crReportOtherResult,
        	c4Utilities.CIFORProperties.crReportClusterCode,
        	c4Utilities.CIFORProperties.crReportDateCollected,
        	c4Utilities.CIFORProperties.crReportDateReceived,
        	c4Utilities.CIFORProperties.crReportDateReported,
        };

        for(int i=0;i<stringcolumnNames.length;i++)
    		Columns.addElement((String) stringcolumnNames[i]);
		
	/**
	 *   Fill the vectors with data from the query 
	 */        
	Rows.clear(); 			// clear unwanted value if exist any in Rows variable.
	
	while( rs.next())
	{
		inputTestField = rs.getString(9) + rs.getString(10) + rs.getString(11) + rs.getString(12);
				
		dateReportedString = rs.getString(15);
	
		int testResult = lastTestField.compareTo(inputTestField);
		
		if (testResult != 0 && ckBoxStatus == "Y")
			{
				ckDateRange();					
				lastTestField = inputTestField;
			}
		else if (ckBoxStatus == "N")
			{
				goodDate = "Y";		//records are filtered in the sort for the date range, so if ckbox not cked set goodDate to "Y"
			}

		if(goodDate == "Y")
		{
			if(c4Utilities.UserProperties.PrimaryPattern.equals("CDC")) 
				{
					pEP1 = rs.getString(10);
					sEP1 = rs.getString(11);
					pEP2 = rs.getString(17);
					sEP2 = rs.getString(18);
				}
			else
				if(c4Utilities.UserProperties.PrimaryPattern.equals("Local"))
					{
						pEP2 = rs.getString(10);
						sEP2 = rs.getString(11);
						pEP1 = rs.getString(17);
						sEP1 = rs.getString(18);
					}
			
			Vector<Object> row = new Vector<Object>(cols);

			row.addElement(rs.getObject(1));	// region
			row.addElement(rs.getObject(2));	// lab specimen id
			row.addElement(rs.getObject(3));	// first name
			row.addElement(rs.getObject(4));	// last name
			row.addElement(rs.getObject(5));	// city
			row.addElement(rs.getObject(6));	// county	
			row.addElement(rs.getObject(7));	// state
			row.addElement(rs.getObject(8));	// age
			row.addElement(rs.getObject(9));	// organism
			row.addElement(pEP1);				// primary pattern 1
			row.addElement(sEP1);				// secondary pattern 1
			row.addElement(pEP2);				// primary pattern 2
			row.addElement(sEP2);				// secondary pattern 2
			row.addElement(rs.getObject(12));	// other result
			row.addElement(rs.getObject(16)); 	// cluster code
			row.addElement(rs.getObject(13));	// date collected
			row.addElement(rs.getObject(14));	// date received
			row.addElement(rs.getObject(15));	// date reported
			
			Rows.addElement(row);
		}
	}
	
	rs.close(); 			//close the Resultset.
	pstmt.close(); 			//close the PreparedStatement.
	
	/**
	 *   Close the database connection
	 */
    c4DataBase.DBUpdate.DatabaseClose();
	
	/**
	 *   Define the TableModel
	 */
	TableModel ClusterModel = new DefaultTableModel(Rows, Columns){
		
		private static final long serialVersionUID = 1L; };	
		
	TableColumn col;
	
	/**
	 *   Create and set attributes for the JTable and clusterTable
	 */	
	final JTable clusterTable = new JTable(ClusterModel);
	
	// Set the cell color if reported date = current date
	col = clusterTable.getColumnModel().getColumn(17);
	col.setCellRenderer(new ClusterTableCellRenderer());
		
	clusterTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
    clusterTable.setColumnSelectionAllowed(true);
	clusterTable.setAutoCreateRowSorter(true);
	clusterTable.setFillsViewportHeight(true);
	clusterTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
	clusterTable.setRowHeight(24);
    clusterTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
	JScrollPane scrollPane = new JScrollPane(clusterTable);
	clusterTable.setSize(GUI_Layout.newWidth, GUI_Layout.newHeight);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	/**
	 *   Center the State, Age, and Date columns
	 */    
	DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
	cellRenderer.setHorizontalAlignment( JLabel.CENTER );
	clusterTable.getColumnModel().getColumn(5).setCellRenderer( cellRenderer );	// Center the State column
	clusterTable.getColumnModel().getColumn(7).setCellRenderer( cellRenderer );	// Center the Age column
	clusterTable.getColumnModel().getColumn(15).setCellRenderer( cellRenderer );	// Center the Date Collected column
	clusterTable.getColumnModel().getColumn(16).setCellRenderer( cellRenderer );	// Center the Date Received column
	//clusterTable.getColumnModel().getColumn(17).setCellRenderer( cellRenderer );	// Center the Date Reported column
	// Can't have 2 cell renderer's for a single cell.  The second one overrides the first one.
	
    TableColumn column = null;
	for (int i = 0; i < 18; i++) 
		{
			column = clusterTable.getColumnModel().getColumn(i);
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
			else if (i == 10) {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern1
			else if (i == 11) {column.setPreferredWidth(150);}		// Primary Enzyme Pattern2
			else if (i == 12) {column.setPreferredWidth(170);}		// Secondary Enzyme Pattern2
			else if (i == 13) {column.setPreferredWidth(165);}		// Other Result
			else if (i == 14) {column.setPreferredWidth(100);}		// Cluster Code
			else if (i == 15) {column.setPreferredWidth(100);}		// Date Collected
			else if (i == 16) {column.setPreferredWidth(100);}		// Date Received
			else if (i == 17) {column.setPreferredWidth(100);}		// Date Reported
		}
	
	/**
	 * Hide any columns that were not selected in the Parameter input
	 * can be Region, Prim/Sec Enzyme Pattern, Cluster Code
	 */
	//deleteRowCtr=0;
	if(Parameters.regionSelected == false) 
		{
			int col_index = 0;
			TableColumn tcol = clusterTable.getColumnModel().getColumn(col_index);
			clusterTable.getColumnModel().getColumn(col_index).setMinWidth(0);
			clusterTable.getColumnModel().getColumn(col_index).setMaxWidth(0);
		}
	if(Parameters.showOnlyPrimaryPatternSelected == true)
		{
			int col_index = 11;
			TableColumn tcol1 = clusterTable.getColumnModel().getColumn(col_index);
			clusterTable.getColumnModel().getColumn(col_index).setMinWidth(0);
			clusterTable.getColumnModel().getColumn(col_index).setMaxWidth(0);
			
			col_index = 12;
			TableColumn tcol2 = clusterTable.getColumnModel().getColumn(col_index);
			clusterTable.getColumnModel().getColumn(col_index).setMinWidth(0);
			clusterTable.getColumnModel().getColumn(col_index).setMaxWidth(0);
		}
	if(Parameters.clusterCodeSelected == false) 
		{
			int col_index = 14;
			TableColumn tcol = clusterTable.getColumnModel().getColumn(col_index);
			clusterTable.getColumnModel().getColumn(col_index).setMinWidth(0);
			clusterTable.getColumnModel().getColumn(col_index).setMaxWidth(0);
		}
	
	/**
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
					clusterTable.print	(JTable.PrintMode.FIT_WIDTH, 
															header, footer, true, null, true, null);	
				} catch (HeadlessException pe) {
					pe.printStackTrace();
				} catch (PrinterException pe) {
					pe.printStackTrace();
				}
	        }
	    });

		/**
		 *   Write an Excel Spreadsheet file
		 */	
		excelButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
        	
            try
            {
            	getDateTime();
	           
            	fileName = c4Utilities.CIFORProperties.cReportExcelDirectory
        				+ c4Utilities.CIFORProperties.cReportExcelFileName
        				+ excelDate + ".xls";

	            WorkbookSettings ws = new WorkbookSettings();
	            ws.setLocale(new Locale(c4Utilities.CIFORProperties.localeLanguage, c4Utilities.CIFORProperties.localeCountry));
	            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName), ws);
	            WritableSheet s3 = workbook.createSheet(c4Utilities.CIFORProperties.cReportExcelSheet3, 0);
	            WritableSheet s2 = workbook.createSheet(c4Utilities.CIFORProperties.cReportExcelSheet2, 0);
	            WritableSheet s1 = workbook.createSheet(c4Utilities.CIFORProperties.cReportExcelSheet1, 0);
	            writeSheetHeaders(s1);
	            writeSheetFormat(s1);
	            
		    	int line = 0;
		    	Object[][] array = new Object [clusterTable.getRowCount()] [clusterTable.getColumnCount()];  
		    	String string = null;  
		    	Object object = new Object();
		   
		    	for ( int i = 0; i < clusterTable.getRowCount() ; ++i) 
		    	{   
		    		if(i>0){s1.setRowView(i, 300);}
		    		
		    		line++;
		    		for ( int j = 0; j < clusterTable.getColumnCount(); ++j) 
		    		{  
		    			array[i][j] = clusterTable.getValueAt(i, j);  
		    			object = clusterTable.getValueAt(i,j);  
		    			if (object == null)
	    					{string = null;}
	    				else
	    					{string = object.toString();}
		
		    			if (j < clusterTable.getColumnCount()-3)
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
		        	c4Reports.LaunchExcel.fileName = fileName;
		        	c4Reports.LaunchExcel.main(null);
				} catch (Exception e) {
					e.printStackTrace();
				}				
        }
    });

	/**
	 *   Setup panels for the GUI
	 */	
	topPanel = new JPanel();
    contentPanel = new JPanel();
    
	/**
	 *  Add components to the GUI layout for display.  The GUI was created with
	 *  the Matisse GUI builder in NetBeans, so be careful when making changes!
	 */
	scrollPane.setPreferredSize(new java.awt.Dimension(GUI_Layout.newWidth, GUI_Layout.newHeight));
    
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
	                      .addGap(GUI_Layout.newWidth-930, GUI_Layout.newWidth-930, GUI_Layout.newWidth-930)) 
	                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
	                      .addGap(GUI_Layout.newWidth-955, GUI_Layout.newWidth-955, GUI_Layout.newWidth-955))))
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
	      ));

	     topPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {textBeginningDate, textCurrentDate});
	  	
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
	                       .addComponent(headerCkBox)
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                       .addComponent(headerField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
	                   .addGroup(bottomPanelLayout.createSequentialGroup()
	                       .addComponent(footerCkBox)
	                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                       .addComponent(footerField, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
	                       .addGap(46, 46, 46)
	                       .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
	                       .addGap(46, 46, 46)
	                       .addComponent(excelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
	               .addContainerGap(GUI_Layout.newWidth-860, Short.MAX_VALUE))
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
	                       .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, GUI_Layout.newWidth, Short.MAX_VALUE)))
	                       .addContainerGap())
	       );
	     layout.setVerticalGroup(
	         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	         .addGroup(layout.createSequentialGroup()
	               .addContainerGap()
	               .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	               .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, GUI_Layout.newHeight-280, javax.swing.GroupLayout.PREFERRED_SIZE) // was 600
	               .addGap(18, 18, 18)
	               .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	               .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	       );

	/**
	 *   Setup a jPanel for printing
	 */	       
	jPanel.add(contentPanel);
	frame.add(jPanel, BorderLayout.CENTER);
	frame.setVisible(true);
	}
	    
	/**
	 *  Setup Excel Worksheet
	 */	    
    private static void writeSheetHeaders(WritableSheet s1) throws WriteException
    {
		/* Set the Page Format, Header and Margins for the worksheet */
    	s1.getSettings().setPaperSize(PaperSize.LETTER);
    	s1.getSettings().setOrientation(PageOrientation.LANDSCAPE);
    	s1.getSettings().setFitWidth(1);
	    s1.getSettings().setProtected(false);
	    s1.getSettings().setVerticalFreeze(1);
	    //s1.getSettings().setDefaultRowHeight(300);
	    s1.setRowView(0, 500);
	    s1.getSettings().setHeaderMargin(.25);
	    s1.getSettings().setFooterMargin(.25);
	    s1.getSettings().setTopMargin(.75);
	    s1.getSettings().setBottomMargin(.5);
	    s1.getSettings().setLeftMargin(.25);
	    s1.getSettings().setRightMargin(.25);
	    
	    /* Add a header and footer */
	    HeaderFooter header1 = new HeaderFooter();
	    header1.getLeft().append(excelFooter);
	    header1.getCentre().append(c4Utilities.CIFORProperties.cReportHeading);
	    s1.getSettings().setHeader(header1);
	        
	    HeaderFooter footer = new HeaderFooter();
	    s1.getSettings().setFooter(footer);
	    footer.getLeft().append(c4Utilities.CIFORProperties.cReportExcelDatePrinted);
	    footer.getLeft().appendDate();
	    footer.getRight().append(c4Utilities.CIFORProperties.cReportExcelPage);
	    footer.getRight().appendPageNumber();
	    
		/* Format the Font, set cell wrap "true", set background to color */
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);
		cf.setWrap(true);
		cf.setBackground(Colour.GRAY_25);

		/** 
		 * Creates Labels for each column
		 */
		WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat cf1 = new WritableCellFormat(wf1);
		cf1.setAlignment(Alignment.LEFT);
		cf1.setBackground(Colour.GRAY_25);
		cf1.setWrap(true);

		
		WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat cf2 = new WritableCellFormat(wf2);
		cf2.setAlignment(Alignment.CENTRE);
		cf2.setBackground(Colour.GRAY_25);
		cf2.setWrap(true);
		
		int columnCtr = 0;
	
		if(Parameters.regionSelected == true){fieldWidth = 10;} else {fieldWidth = 0;}
			Label regionCol = new Label(columnCtr,0,c4Utilities.UserProperties.RegionName,cf1);
			s1.addCell(regionCol);
			s1.setColumnView(columnCtr, fieldWidth);
			columnCtr ++;

		Label accessCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportAccessionNo,cf1);
		s1.addCell(accessCol);
		s1.setColumnView(columnCtr, 15);
		columnCtr ++;
		
		Label fNameCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportFirstName,cf1);
		s1.addCell(fNameCol);
		s1.setColumnView(columnCtr, 15);
		columnCtr ++;
		
		Label lNameCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportLastName,cf1);
		s1.addCell(lNameCol);
		s1.setColumnView(columnCtr, 15);
		columnCtr ++;
		
		Label cityCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportCity,cf1);
		s1.addCell(cityCol);
		s1.setColumnView(columnCtr, 20);
		columnCtr ++;
		
		Label stateCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportState,cf1);
		s1.addCell(stateCol);
		s1.setColumnView(columnCtr, 7);
		columnCtr ++;
		
		Label cntyCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportCounty,cf1);
		s1.addCell(cntyCol);
		s1.setColumnView(columnCtr, 15);
		columnCtr ++;
		
		Label ageCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportAge,cf1);
		s1.addCell(ageCol);
		s1.setColumnView(columnCtr, 5);
		columnCtr ++;
		
		Label orgCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportOrganism,cf1);
		s1.addCell(orgCol);
		s1.setColumnView(columnCtr, 40);
		columnCtr ++; 
		
		Label pep1Col = new Label(columnCtr,0,rptPEP1,cf1);
		s1.addCell(pep1Col);
		s1.setColumnView(columnCtr, 25);
		columnCtr ++;
		
		Label sep1Col = new Label(columnCtr,0,rptSEP1,cf1);
		s1.addCell(sep1Col);
		s1.setColumnView(columnCtr, 25);
		columnCtr ++;

		if(Parameters.showOnlyPrimaryPatternSelected == false){fieldWidth = 25;} else {fieldWidth = 0;}
			Label pep2Col = new Label(columnCtr,0,rptPEP2,cf1);
			s1.addCell(pep2Col);
			s1.setColumnView(columnCtr, fieldWidth);
			columnCtr ++;
			Label sep2Col = new Label(columnCtr,0,rptSEP2,cf1);
			s1.addCell(sep2Col);
			s1.setColumnView(columnCtr, fieldWidth);
			columnCtr ++;

		
		Label otherCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportOtherResult,cf1);
		s1.addCell(otherCol);
		s1.setColumnView(columnCtr, 15);
		columnCtr ++;
		
		if(Parameters.clusterCodeSelected == true){fieldWidth = 10;} else {fieldWidth = 0;}
			Label clusterCd = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportClusterCode,cf1);
			s1.addCell(clusterCd);
			s1.setColumnView(columnCtr, fieldWidth);
			columnCtr ++;
		
		Label collDateCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportDateCollected,cf2);
		s1.addCell(collDateCol);
		s1.setColumnView(columnCtr, 12);
		columnCtr ++;
		
		Label rcvdDateCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportDateReceived,cf2);
		s1.addCell(rcvdDateCol);
		s1.setColumnView(columnCtr, 12);
		columnCtr ++;
		
		Label rptdDateCol = new Label(columnCtr,0,c4Utilities.CIFORProperties.crReportDateReported,cf2);
		s1.addCell(rptdDateCol);
		s1.setColumnView(columnCtr, 12);
		columnCtr ++;
	
  	  }
  	    
	/**
	 *  Write the Excel sheet
	 */
    private static void writeSheetFormat(WritableSheet s1) throws WriteException
    {

		/* Format the Font */
	    WritableFont wf = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
	    WritableCellFormat cf = new WritableCellFormat(wf);
	    cf.setWrap(true);
    }
  	    
	/**
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

	/**
	 *   Today's Reports Only - Check to see if the REPORTED_DATE is = to the Current Date
	 */          	    
  	public static  void ckDateRange()
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

  	  		if (currentResult == 0) 
  	  			{
  	  				goodDate = "Y";
  				}
  	  		else 
  	  			{
  	  				goodDate = "N";
  				}
  	    }
      	
	/**
	 *   Get the parameter dates and set the MO, DY and YYYY for comparison to REPORTED_DATE
	 */      
    private static void setParameterDates()
    {
    	/* Set the current date and the current MO and YYYY values */	  
		currentDateString = Parameters.stringCurrentDate;
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
    }

	/**
	 *   Initialize values and get parameters passed from the parameter program
	 */		
	private static void initValues() {
	  			
		if (Parameters.ckboxAllReports.isSelected()) 
			{ckBoxStatus = "Y";}
		else
			{ckBoxStatus = "N";}
		
		labelCurrentDate = new JLabel(c4Utilities.CIFORProperties.currentDate);
		textCurrentDate = new javax.swing.JTextField();
		textCurrentDate.setEditable(false);
		textCurrentDate.setText(Parameters.stringCurrentDate);
		
		labelBeginningDate = new JLabel(c4Utilities.CIFORProperties.beginningDate);
		textBeginningDate = new javax.swing.JTextField();
		textBeginningDate.setEditable(false);
		textBeginningDate.setText(Parameters.stringBeginningDate);
		  
		textReportSelection = (Parameters.vReportSelection);
		
		labelReportTitle = new JLabel(c4Utilities.CIFORProperties.cReportHeading);
		labelReportTitle.setHorizontalAlignment(javax.swing.JTextField.CENTER);

	    textReportSelection.setHorizontalAlignment(javax.swing.JTextField.CENTER);
	    textReportSelection.setEditable(false);
	    
		labelReportTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
	    textReportSelection.setFont(new Font("Tahoma", Font.BOLD, 16));
	    textReportSelection.setBorder(null);
	    
	    labelBeginningDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textBeginningDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textBeginningDate.setBorder(null);
	    
	    labelCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textCurrentDate.setBorder(null);
	    
	    String tooltipText;
	
	    tooltipText = c4Utilities.CIFORProperties.cReportIncludePageHeader;
	    headerCkBox = new JCheckBox(c4Utilities.CIFORProperties.cReportIncludeHeader, true);
	    headerCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
	    headerCkBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	            headerField.setEnabled(headerCkBox.isSelected());
	        }
	    });
	    headerCkBox.setToolTipText(tooltipText);
	    tooltipText = c4Utilities.CIFORProperties.cReportIncludePageNo;
        if (Parameters.ckboxAllReports.isSelected()) 
	        {
	        	headerField = new JTextField(c4Utilities.CIFORProperties.cReportTodaysReports);
	        }
	    else
	        {
	    		headerField = new JTextField(c4Utilities.CIFORProperties.cReportAllReports);
	        }
	    headerField.setFont(new Font("Tahoma", Font.BOLD, 12));
	    headerField.setToolTipText(tooltipText);
	
	    tooltipText = c4Utilities.CIFORProperties.cReportIncludeFooter;
	    footerCkBox = new JCheckBox(c4Utilities.CIFORProperties.cReportFooter, true);
	    footerCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
	    footerCkBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	            footerField.setEnabled(footerCkBox.isSelected());
	        }
	    });

	    String currentDate = textCurrentDate.getText(); 	// get string date from text date
	    String beginningDate = textBeginningDate.getText(); // get string date from text date
	    footerField = new JTextField(c4Utilities.CIFORProperties.cReportDateRangeFrom + " "
	    		+ beginningDate + " " + c4Utilities.CIFORProperties.cReportDateRangeTo + " "  + currentDate
	    		+ "                  " + c4Utilities.CIFORProperties.cReportReportPage);
	    footerField.setFont(new Font("Tahoma", Font.BOLD, 12));
	    excelFooter = c4Utilities.CIFORProperties.cReportDateRangeFrom + beginningDate
	    		+ c4Utilities.CIFORProperties.cReportDateRangeTo + currentDate;
	
	    tooltipText = c4Utilities.CIFORProperties.cReportPrintTheTable;
	    printButton = new JButton(c4Utilities.CIFORProperties.printButton);
	    printButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	    printButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
	    printButton.setToolTipText(tooltipText);
	      
	    String tooltipExcelText;
	    tooltipExcelText = c4Utilities.CIFORProperties.cReportCreateSpreadsheet;
	    excelButton = new JButton(c4Utilities.CIFORProperties.excelButton);
	    excelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	    excelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
	    excelButton.setToolTipText(tooltipExcelText);
	    
	    topPanel = new JPanel();
	    contentPanel = new JPanel();
	}
}