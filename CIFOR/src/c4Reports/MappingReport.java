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
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.sql.*;
import jxl.*;
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

public class MappingReport extends JFrame
{
	private static final long serialVersionUID = 1L;

/*
 *  Create GUI Components
 */	
		private 	static 	JPanel 		topPanel;
	    private 	static 	JPanel 		contentPanel;
	    private 	static 	JPanel 		bottomPanel;
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
	    public		static	String		excelDate;
	    public		static	String		fileName;
	    public		static	File		excelFile;
	    public		static	String		stringOrg;
	    public		static	String		stringPrim;
	    public		static	String		stringSec;
	    public		static	String		stringOth;
	    public		static	String		stringSt;
	    public		static	String		stringCnty;
	    	    
	    static 		PreparedStatement 	psDates;
	    
		public		static	String		excelFooter;
 
		/*
		 *  "main" section of the program
		 */	  	
	    public static void main( String [] argv ) throws Exception
	    {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();
			
	        int insetX = 0;
	        int insetY = 0;
			int newWidth = 0;
			int newHeight = 0;

			if 		(d.width > 1900) {newWidth = d.width-260; newHeight = d.height-150; insetX = 100; insetY = 20;}
			else if	(d.width < 1900) {newWidth = d.width-75; newHeight = d.height-50; insetX = 20; insetY = 20;}
						
			MappingReport frame = new MappingReport();
			frame.setSize(newWidth+10, newHeight);
			frame.setLocation(insetX, insetY);
	    	frame.setTitle(c4Utilities.CIFORProperties.qReportTitle);
	    	frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    	JPanel jPanel = new JPanel( new BorderLayout() );

	    	initValues();
	    	
		/*
		 *  Establish a connection to the database
		 */    	
    	c4DataBase.DBUpdate.DataBaseConnection();


		/*
		 *   Setup Row and Column Vectors for the database
		 */		
		final Vector<String> Columns = new Vector<String>();
		Vector<Vector<Object>> Rows = new Vector<Vector<Object>>();

		/*
		 *   Setup the date fields for the SQL Query
		 */	  
		String currentString = MappingParameters.stringCurrentDate;
		SimpleDateFormat cdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateCurrentDate;
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
	    	excelDate = currentString;	// used for file name with Excel Spreadsheet
			
		String beginningString = MappingParameters.stringBeginningDate;
		SimpleDateFormat bdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dateBeginningDate;
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
			
		/*
		 *   Create the String component for the database Query 
		 */		
	    ResultSet rs = null;
	    PreparedStatement pstmt = null;    
	    
		if((null != MappingParameters.stringOrganism) && (MappingParameters.stringOrganism.length() == 0))
	    		{stringOrg = "%";}
		    else
		    	{stringOrg = MappingParameters.stringOrganism;}
		
		if((null != MappingParameters.stringPrimaryEnzyme) && (MappingParameters.stringPrimaryEnzyme.length() == 0))
    		{stringPrim = "%";}
	    else
	    	{stringPrim = MappingParameters.stringPrimaryEnzyme;}
		
		if((null != MappingParameters.stringSecondaryEnzyme) && (MappingParameters.stringSecondaryEnzyme.length() == 0))
    		{stringSec = "%";}
	    else
	    	{stringSec = MappingParameters.stringSecondaryEnzyme;}
		
		if((null != MappingParameters.stringOtherResult) && (MappingParameters.stringOtherResult.length() == 0))
    		{stringOth = "%";}
	    else
	    	{stringOth = MappingParameters.stringOtherResult;}
		
		if((null != MappingParameters.stringState) && (MappingParameters.stringState.length() == 0))
    		{stringSt = "%";}
	    else
	    	{stringSt = MappingParameters.stringState;}
		
		if((null != MappingParameters.stringCounty) && (MappingParameters.stringCounty.length() == 0))
    		{stringCnty = "%";}
	    else
	    	{stringCnty = MappingParameters.stringCounty;}
	    		
		String MappingQuery = 	
			"SELECT  LAB_SPECIMEN_ID, ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, " +
					"STREET_ADDRESS, CITY, COUNTY, STATE, POSTAL_CODE, POSTAL_CODE_PLUS_4, " +
					"TO_CHAR(DATE_REPORTED,'yyyy-MM-dd') DATE_REPORTED " +
			"FROM CIFOR_LAB_RESULT " +
			"JOIN	CIFOR_DEMOGRAPHICS ON CIFOR_LAB_RESULT.DEMOGRAPHICS_ID = CIFOR_DEMOGRAPHICS.ID " +
					"WHERE	DATE_REPORTED <= ? " +
					"AND 	DATE_REPORTED >= ? " +
					"AND	ORGANISM LIKE ? " +
					"AND	CDC_PRIMARY_ENZYME_PATTERN LIKE ? " +
					"AND	CDC_SECONDARY_ENZYME_PATTERN LIKE ? " +
					"AND    OTHER_RESULT LIKE ? " +
					"AND	STATE LIKE ? " +
					"AND    COUNTY LIKE ? " +
 			"ORDER BY ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, DATE_REPORTED DESC";
		
		pstmt = c4DataBase.DBUpdate.connection.prepareStatement(MappingQuery);
		pstmt.setDate(1, newDateCurrentDate); 		// set input parameter
		pstmt.setDate(2, newDateBeginningDate); 	// set input parameter
		pstmt.setString(3, stringOrg);				// set the organism
		pstmt.setString(4, stringPrim);				// set the primary enzyme
		pstmt.setString(5, stringSec);				// set the secondary enzyme
		pstmt.setString(6, stringOth);				// set the other result
		pstmt.setString(7, stringSt);				// set the State
		pstmt.setString(8, stringCnty);				// set the County
		rs = pstmt.executeQuery();					// execute the query

		ResultSetMetaData metaDt = rs.getMetaData();

		/*
		 *   Setup the columns for the GUI and the Report
		 */		
		int cols = metaDt.getColumnCount();
			Columns.clear(); 					// clear unwanted value if exist any in Columns variable.
		
        final String[] stringcolumnNames = 		// add the column names to the header
        { 
        	c4Utilities.CIFORProperties.mReportAccessionNo,
        	c4Utilities.CIFORProperties.mReportOrganism,
        	c4Utilities.CIFORProperties.mReportPrimaryEnzyme,
        	c4Utilities.CIFORProperties.mReportSecondaryEnzyme,
        	c4Utilities.CIFORProperties.mReportOtherResult,
        	c4Utilities.CIFORProperties.mReportStreetAddress,
        	c4Utilities.CIFORProperties.mReportCity,
        	c4Utilities.CIFORProperties.mReportCounty,
        	c4Utilities.CIFORProperties.mReportState,
        	c4Utilities.CIFORProperties.mReportZip,
        	c4Utilities.CIFORProperties.mReportZipPlus,
        	c4Utilities.CIFORProperties.mReportDateReported
        };

        for(int i=0;i<stringcolumnNames.length;i++)
    		Columns.addElement((String) stringcolumnNames[i]);
		
		/*
		 *   Fill the vectors with data from the query 
		 */        
        Rows.clear(); 			// clear unwanted value if exist any in Rows variable.
	
        while( rs.next())
        {
        	Vector<Object> row = new Vector<Object>(cols);
        	for (int i = 1; i <= cols; i++)
        	{
        		row.addElement(rs.getObject(i));
        	}
        	Rows.addElement(row);
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
        final TableModel MappingModel = new DefaultTableModel(Rows, Columns){ };
	
		/*
		 *   Create and set attributes for the JTable and MappingTable
		 */	
		final JTable MappingTable = new JTable(MappingModel);
		MappingTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		MappingTable.setColumnSelectionAllowed(true);
		MappingTable.setAutoCreateRowSorter(true);
		MappingTable.setFillsViewportHeight(true);
		MappingTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
		MappingTable.setRowHeight(24);
		MappingTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    
		JScrollPane scrollPane = new JScrollPane(MappingTable);
		MappingTable.setSize(newWidth, newHeight);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
		/*
		 *   Define the table's column widths
		 */    
	    TableColumn column = null;									
		for (int i = 0; i < 12; i++) 
		{
		    column = MappingTable.getColumnModel().getColumn(i);
		    if 		(i == 0)	{column.setPreferredWidth(100);}		// Accession No
		    else if (i == 1)	{column.setPreferredWidth(220);}		// Organism
		    else if (i == 2)	{column.setPreferredWidth(170);}		// Primary Enzyme Pattern
		    else if (i == 3)	{column.setPreferredWidth(170);}		// Secondary Enzyme Pattern
		    else if (i == 4)	{column.setPreferredWidth(150);}		// Other Result
		    else if (i == 5)	{column.setPreferredWidth(195);}		// Street Address
		    else if (i == 6)	{column.setPreferredWidth(160);}		// City
		    else if (i == 7)	{column.setPreferredWidth(150);}		// County
		    else if (i == 8)	{column.setPreferredWidth(50);}			// State
		    else if (i == 9)	{column.setPreferredWidth(75);}			// Zip
		    else if (i == 10)	{column.setPreferredWidth(55);}			// Zip+4
		    else if (i == 11)	{column.setPreferredWidth(110);}		// Date Reported
		}

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
            	MappingTable.print	(JTable.PrintMode.FIT_WIDTH, 
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
	           
            	fileName = c4Utilities.CIFORProperties.mReportExcelDirectory
        				+ c4Utilities.CIFORProperties.mReportExcelFileName
        				+ excelDate + ".xls";

	            WorkbookSettings ws = new WorkbookSettings();
	            ws.setLocale(new Locale(c4Utilities.CIFORProperties.localeLanguage, c4Utilities.CIFORProperties.localeCountry));
	            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName), ws);
	            WritableSheet s3 = workbook.createSheet(c4Utilities.CIFORProperties.qReportExcelSheet3, 0);
	            WritableSheet s2 = workbook.createSheet(c4Utilities.CIFORProperties.qReportExcelSheet2, 0);
	            WritableSheet s1 = workbook.createSheet(c4Utilities.CIFORProperties.qReportExcelSheet1, 0);
	            writeSheetHeaders(s1);
	            writeSheetFormat(s1);

		    	int line = 0;
		    	Object[][] array = new Object [MappingTable.getRowCount()] [MappingTable.getColumnCount()];  
		    	String string = null;
		    	Object object = new Object();
		   
		    	for ( int i = 0; i < MappingTable.getRowCount() ; ++i) 
		    	{   
		    		line++;
		    		for ( int j = 0; j < MappingTable.getColumnCount(); ++j) 
		    		{  
		    			array[i][j] = MappingTable.getValueAt(i, j);  
		    			object = MappingTable.getValueAt(i,j);  
		    			if (object == null)
	    					{string = null;}
	    				else
	    					{string = object.toString();}
		    			
		    			if (j < 9)
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
		bottomPanel = new JPanel();
	    contentPanel = new JPanel();
    
	/*
	 *   Add components to the GUI layout for display.  The GUI was created with
	 *   the Matisse GUI builder in NetBeans, so be careful when making changes!
	 */
	scrollPane.setPreferredSize(new java.awt.Dimension(newWidth, newHeight));
    
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
	                      .addGap(newWidth-940, newWidth-940, newWidth-940)) 
	                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
	                            .addGap(newWidth-965, newWidth-965, newWidth-965))))
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
	bottomPanel.setBorder(BorderFactory.createTitledBorder(c4Utilities.CIFORProperties.mReportPrintOptions));
	  	
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
	               .addContainerGap(newWidth-869, Short.MAX_VALUE))
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
	                       .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, newWidth-20, Short.MAX_VALUE))) // was -20 - d.height-200
	                       .addContainerGap())
	       );
	     layout.setVerticalGroup(
	         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	         .addGroup(layout.createSequentialGroup()
	               .addContainerGap()
	               .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	               .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, newHeight-300, javax.swing.GroupLayout.PREFERRED_SIZE) // was 600
	               .addGap(18, 18, 18)
	               .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	               .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	       );

		/*
		 *   Setup a jPanel for printing
		 */	       
		jPanel.add(contentPanel);
		frame.add(jPanel, BorderLayout.CENTER);
		frame.setVisible(true);
		
    }
	    
		/*
		 * Setup the Spreadsheet
		 */	    
	    private static void writeSheetHeaders(WritableSheet s1) throws WriteException
	    {
			/* Set the Page Format, Header and Margins for the worksheet */
	    	s1.getSettings().setPaperSize(PaperSize.LETTER);
	    	s1.getSettings().setOrientation(PageOrientation.LANDSCAPE);
	    	s1.getSettings().setFitWidth(1);
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
		    header1.getCentre().append(c4Utilities.CIFORProperties.mReportHeading);
		    s1.getSettings().setHeader(header1);
		        
		    HeaderFooter footer = new HeaderFooter();
		    s1.getSettings().setFooter(footer);
		    footer.getLeft().append(c4Utilities.CIFORProperties.mReportExcelDatePrinted);
		    footer.getLeft().appendDate();
		    footer.getRight().append(c4Utilities.CIFORProperties.mReportExcelPage);
		    footer.getRight().appendPageNumber();
		    //s1.getSettings().setFooter(footer);
		    
			/* Format the Font, set cell wrap "true", set background to color */
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
			WritableCellFormat cf = new WritableCellFormat(wf);
			cf.setWrap(true);
			cf.setBackground(Colour.GRAY_25);

			/* Creates Labels for each column*/
			Label column0 = new Label(0,0,c4Utilities.CIFORProperties.mReportAccessionNo,cf);
			s1.addCell(column0);
			s1.setColumnView(0, 15);
			
			Label column1 = new Label(1,0,c4Utilities.CIFORProperties.mReportOrganism,cf);
			s1.addCell(column1);
			s1.setColumnView(1, 30);
			
			Label column2 = new Label(2,0,c4Utilities.CIFORProperties.mReportPrimaryEnzyme,cf);
			s1.addCell(column2);
			s1.setColumnView(2, 25);
			
			Label column3 = new Label(3,0,c4Utilities.CIFORProperties.mReportSecondaryEnzyme,cf);
			s1.addCell(column3);
			s1.setColumnView(3, 25);
			
			Label column4 = new Label(4,0,c4Utilities.CIFORProperties.mReportOtherResult,cf);
			s1.addCell(column4);
			s1.setColumnView(4, 30);
			
			Label column5 = new Label(5,0,c4Utilities.CIFORProperties.mReportStreetAddress,cf);
			s1.addCell(column5);
			s1.setColumnView(5, 30);
			
			Label column6 = new Label(6,0,c4Utilities.CIFORProperties.mReportCity,cf);
			s1.addCell(column6);
			s1.setColumnView(6, 15);
			
			Label column7 = new Label(7,0,c4Utilities.CIFORProperties.mReportCounty,cf);
			s1.addCell(column7);
			s1.setColumnView(7, 15);
			
			Label column8 = new Label(8,0,c4Utilities.CIFORProperties.mReportState,cf);
			s1.addCell(column8);
			s1.setColumnView(8, 10);
			
			Label column9 = new Label(9,0,c4Utilities.CIFORProperties.mReportZip,cf);
			s1.addCell(column9);
			s1.setColumnView(9, 10);
			
			Label column10 = new Label(10,0,c4Utilities.CIFORProperties.mReportZipPlus,cf);
			s1.addCell(column10);
			s1.setColumnView(10, 10);
			
			WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
			WritableCellFormat cf1 = new WritableCellFormat(wf1);
			cf1.setWrap(true);
			cf1.setBackground(Colour.GRAY_25);
			cf1.setAlignment(Alignment.CENTRE);
			
			Label column11 = new Label(11,0,c4Utilities.CIFORProperties.mReportDateReported,cf1);
			s1.addCell(column11);
			s1.setColumnView(11, 15);
	  }
	    
		/*
		 *  Write the Excel worksheet
		 */
  	    private static void writeSheetFormat(WritableSheet s1) throws WriteException
  	    {

  			/* Format the Font */
  		    WritableFont wf = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
  		    WritableCellFormat cf = new WritableCellFormat(wf);
  		    cf.setWrap(true);
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
	 *   Initialize values and get parameters passed from the parameter program
	 */		
	private static void initValues() {
	  	
		labelCurrentDate = new JLabel(c4Utilities.CIFORProperties.currentDate);
		textCurrentDate = new javax.swing.JTextField();
		textCurrentDate.setEditable(false);
		textCurrentDate.setText(MappingParameters.stringCurrentDate);
		
		labelBeginningDate = new JLabel(c4Utilities.CIFORProperties.beginningDate);
		textBeginningDate = new javax.swing.JTextField();
		textBeginningDate.setEditable(false);
		textBeginningDate.setText(MappingParameters.stringBeginningDate);
		
		labelReportTitle = new JLabel(c4Utilities.CIFORProperties.mReportHeading);
		labelReportTitle.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		labelReportTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
	    
	    labelBeginningDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textBeginningDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textBeginningDate.setBorder(null);
	    
	    labelCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
	    textCurrentDate.setBorder(null);
	    
	    String tooltipHeaderText;
	    tooltipHeaderText = c4Utilities.CIFORProperties.mReportIncludePageHeader;
	    headerCkBox = new JCheckBox(c4Utilities.CIFORProperties.mReportIncludeHeader, true);
	    headerCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
	    headerCkBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	            headerField.setEnabled(headerCkBox.isSelected());
	        }
	    });
	    headerCkBox.setToolTipText(tooltipHeaderText);

        headerField = new JTextField(c4Utilities.CIFORProperties.mReportHeading);
	    headerField.setFont(new Font("Tahoma", Font.BOLD, 12));
	
	    String tooltipFooterText;
	    tooltipFooterText = c4Utilities.CIFORProperties.mReportIncludeFooter;
	    footerCkBox = new JCheckBox(c4Utilities.CIFORProperties.mReportFooter, true);
	    footerCkBox.setFont(new Font("Tahoma", Font.BOLD, 12));
	    footerCkBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	            footerField.setEnabled(footerCkBox.isSelected());
	        }
	    });
	    footerCkBox.setToolTipText(tooltipFooterText);
	    
	    String currentDate = textCurrentDate.getText(); 	// get string date from text date
	    String beginningDate = textBeginningDate.getText(); // get string date from text date
	    footerField = new JTextField(c4Utilities.CIFORProperties.mReportDateRangeFrom + " "
	    		+ beginningDate + " " + c4Utilities.CIFORProperties.mReportDateRangeTo + " "  + currentDate
	    		+ "                  " + c4Utilities.CIFORProperties.mReportReportPage);
	    footerField.setFont(new Font("Tahoma", Font.BOLD, 12));
	    excelFooter = c4Utilities.CIFORProperties.mReportDateRangeFrom + beginningDate
	    		+ c4Utilities.CIFORProperties.mReportDateRangeTo + currentDate;
	    
	    String tooltipPrintText;
	    tooltipPrintText = c4Utilities.CIFORProperties.qReportPrintTheTable;
	    printButton = new JButton(c4Utilities.CIFORProperties.printButton);
	    printButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	    printButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
	    printButton.setToolTipText(tooltipPrintText);
	      
	    String tooltipExcelText;
	    tooltipExcelText = c4Utilities.CIFORProperties.qReportCreateSpreadsheet;
	    excelButton = new JButton(c4Utilities.CIFORProperties.excelButton);
	    excelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	    excelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
	    excelButton.setToolTipText(tooltipExcelText);
	    
	    topPanel = new JPanel();
	    bottomPanel = new JPanel();
	    contentPanel = new JPanel();
	}
}