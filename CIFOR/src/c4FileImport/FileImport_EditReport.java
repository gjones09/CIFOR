/*
 * The contents of this file are subject to the Mozilla Public License Version 1.1 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either
 * express or implied. See the License for the specific language governing rights and limitations under the License.
 * 
 * Redistributions of source code must retain the above License notice and all references to the OpenELIS Foundation.
 * 
 * Copyright (C) The OpenELIS Foundation. All Rights Reserved.
 * 
 */

package c4FileImport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import c4Utilities.PrintUtility;
/**
*
* @author jonesg1
*/

/*
 * Prepare the Edit Report for display or printing
 */
public class FileImport_EditReport extends javax.swing.JFrame
	{
	private static final long serialVersionUID = 1L;
		/*
		 * Creates new form ImportErrorReport
		 */
		public FileImport_EditReport()
			{
			initComponents();
			}

		private void initComponents()
			{
			inputFileLabel = new javax.swing.JLabel();
			fromDateLabel = new javax.swing.JLabel();
			reportTitleLabel = new javax.swing.JLabel();
			thruDateLabel = new javax.swing.JLabel();
			inputFileName = new javax.swing.JTextField();

			inputFileLabel1 = new javax.swing.JLabel();
			fromDateLabel1 = new javax.swing.JLabel();
			thruDateLabel1 = new javax.swing.JLabel();
			inputFileName1 = new javax.swing.JTextField();

			fromDate = new javax.swing.JTextField();
			thruDate = new javax.swing.JTextField();
			fromDate1 = new javax.swing.JTextField();
			thruDate1 = new javax.swing.JTextField();

			editReportTabbedPane = new javax.swing.JTabbedPane();

			errorPanel = new javax.swing.JPanel();
			errorTable = new javax.swing.JTable();

			updatedRecordsPanel = new javax.swing.JPanel();
			updateTable = new javax.swing.JTable();

			recapReportPanel = new javax.swing.JPanel();
			recapPanel = new javax.swing.JPanel();
			headerTitle = new javax.swing.JLabel();
			totalRecordsLabel = new javax.swing.JLabel();
			skippedRecordsLabel = new javax.swing.JLabel();
			rejectedRecordsLabel = new javax.swing.JLabel();
			addedRecordsLabel = new javax.swing.JLabel();
			changedRecordsLabel = new javax.swing.JLabel();
			changedRecordsField = new javax.swing.JTextField();
			totalRecordsField = new javax.swing.JTextField();
			skippedRecordsField = new javax.swing.JTextField();
			rejectedRecordsField = new javax.swing.JTextField();
			addedRecordsField = new javax.swing.JTextField();

			printButton = new java.awt.Button();

			runDateLabel = new javax.swing.JLabel();
			runDate = new javax.swing.JTextField();
			runDateLabel1 = new javax.swing.JLabel();
			runDate1 = new javax.swing.JTextField();

			fromDate.setText(FileImport_GetDateRange.stringBeginningDate);
			thruDate.setText(FileImport_GetDateRange.stringCurrentDate);
			fromDate1.setText(FileImport_GetDateRange.stringBeginningDate);
			thruDate1.setText(FileImport_GetDateRange.stringCurrentDate);
			runDate.setText(c4Utilities.DateFormatUtility.currDate);
			runDate1.setText(c4Utilities.DateFormatUtility.currDate);

			inputFileName = FileImport_FileSelection.fileSelectionText;
			inputFileName1 = FileImport_FileSelection.fileSelectionText1;

			FormListener formListener = new FormListener();

			setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
			setTitle("CIFOR - Edit Report");
			setName("editReportFrame");

			inputFileLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			inputFileLabel.setText("Input File:");
			inputFileName.setFont(new java.awt.Font("Tahoma", 0, 13));

			fromDateLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			fromDateLabel.setText("From Date:");

			reportTitleLabel.setFont(new java.awt.Font("Tahoma", 1, 16));
			reportTitleLabel.setText("CIFOR Edit Report");

			thruDateLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			thruDateLabel.setText("Thru");

			/*
			 * Error Records Tab of the GUI
			 */
			String inputDatafile = FileImport_FileSelection.fileSelectionText.getText();

			FileImport_FileModelErrorRpt errorModel;
			errorModel = new FileImport_FileModelErrorRpt(inputDatafile); // This is where data vector is created

			/*
			 * Count records for Total, Rejected, Skipped totals
			 */
			JFormattedTextField count = new JFormattedTextField(NumberFormat.getIntegerInstance());
			count.setValue(new Integer(FileImport_FileModelErrorRpt.totalRecordCounter));
			totalRecordsField = count;
			FileImport_FileModelErrorRpt.totalRecordCounter = 0;

			JFormattedTextField errorRecords = new JFormattedTextField(NumberFormat.getIntegerInstance());
			errorRecords.setValue(new Integer(FileImport_FileModelErrorRpt.errorRecordCounter));
			rejectedRecordsField = errorRecords;
			FileImport_FileModelErrorRpt.errorRecordCounter = 0;

			/*
			 * Create the Error Records Table from the model
			 */
			errorTable = new JTable();
			errorTable.setModel(errorModel);
			errorTable.createDefaultColumnsFromModel();
			errorTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
			errorTable.setAutoCreateRowSorter(true);
			errorTable.setFillsViewportHeight(true);
			errorTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
			errorTable.setRowHeight(20);
			errorTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			JScrollPane errorScrollPane = new JScrollPane(errorTable);
			errorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			errorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			errorTable.setColumnSelectionAllowed(false);
			errorTable.setFillsViewportHeight(true);
			errorScrollPane.setViewportView(errorTable);

			errorTable.getColumnModel().getSelectionModel()
					.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			errorTable.getColumnModel().getColumn(0).setPreferredWidth(120); // Record No
			errorTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Accession No
			errorTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Date Collected
			errorTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Date Received
			errorTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Date Reported
			errorTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Patient DOB
			errorTable.getColumnModel().getColumn(6).setPreferredWidth(1200); // Error Description

			/*
			 * Add components to the GUI layout for display. The GUI was created with
			 * the Matisse GUI builder in NetBeans, so be careful when making changes!
			 * 
			 * NOTE! GUI was imported into WindowsBuilder for easier maintenance
			 */
			Toolkit tk1 = Toolkit.getDefaultToolkit();
			Dimension d1 = tk1.getScreenSize();
			errorScrollPane.setPreferredSize(new java.awt.Dimension(d1.width, d1.height));

			javax.swing.GroupLayout errorPanelLayout = new javax.swing.GroupLayout(errorPanel);
			errorPanel.setLayout(errorPanelLayout);
			errorPanelLayout.setHorizontalGroup(errorPanelLayout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(errorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE));
			errorPanelLayout.setVerticalGroup(errorPanelLayout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(errorScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, d1.height - 400,
									Short.MAX_VALUE));

			editReportTabbedPane.addTab("Error Records", errorPanel);

			/*
			 * Updated Records Tab of the GUI - This is where the data vector is created
			 */
			FileImport_FileModelUpdateRpt updateModel;
			updateModel = new FileImport_FileModelUpdateRpt(inputDatafile);

			/*
			 * Count records for Skipped and Changed totals
			 */
			JFormattedTextField skipped = new JFormattedTextField(NumberFormat.getIntegerInstance());
			skipped.setValue(new Integer(FileImport_EditUpdateFields.recordSkippedCounter));
			skippedRecordsField = skipped;
			FileImport_EditUpdateFields.recordSkippedCounter = 0;

			JFormattedTextField changed = new JFormattedTextField(NumberFormat.getIntegerInstance());
			changed.setValue(new Integer(FileImport_ReadInputFile.recordChangedCounter));
			changedRecordsField = changed;
			FileImport_ReadInputFile.recordChangedCounter = 0;

			JFormattedTextField addedRecords = new JFormattedTextField(NumberFormat.getIntegerInstance());
			addedRecords.setValue(new Integer(FileImport_ReadInputFile.recordAddedCounter));
			addedRecordsField = addedRecords; // count the records added to the database
			FileImport_ReadInputFile.recordAddedCounter = 0;

			updateTable = new JTable();
			updateTable.setModel(updateModel);
			updateTable.createDefaultColumnsFromModel();
			updateTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
			updateTable.setAutoCreateRowSorter(true);
			updateTable.setFillsViewportHeight(true);
			updateTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
			updateTable.setRowHeight(20);
			updateTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			JScrollPane updateScrollPane = new JScrollPane(updateTable);
			updateScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			updateScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			updateTable.setColumnSelectionAllowed(false);
			updateTable.setFillsViewportHeight(true);
			updateScrollPane.setViewportView(updateTable);

			updateTable.getColumnModel().getSelectionModel()
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			updateTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // Record No.
			updateTable.getColumnModel().getColumn(1).setPreferredWidth(90);  // Accession No.
			updateTable.getColumnModel().getColumn(2).setPreferredWidth(90);  // Submitter Specimen ID
			updateTable.getColumnModel().getColumn(3).setPreferredWidth(50);  // Speciment Type (S/I)
			updateTable.getColumnModel().getColumn(4).setPreferredWidth(70);  // Date Collected
			updateTable.getColumnModel().getColumn(5).setPreferredWidth(70);  // Date Received
			updateTable.getColumnModel().getColumn(6).setPreferredWidth(70);  // Date Reported
			updateTable.getColumnModel().getColumn(7).setPreferredWidth(200); // Primary Test
			updateTable.getColumnModel().getColumn(8).setPreferredWidth(200); // Organism
			updateTable.getColumnModel().getColumn(9).setPreferredWidth(150); // CDC Primary Enzyme Pattern
			updateTable.getColumnModel().getColumn(10).setPreferredWidth(150);// CDC Secondary Enzyme Pattern
			updateTable.getColumnModel().getColumn(11).setPreferredWidth(150);// Local Primary Enzyme Pattern
			updateTable.getColumnModel().getColumn(12).setPreferredWidth(150);// LocalSecondary Enzyme Pattern
			updateTable.getColumnModel().getColumn(13).setPreferredWidth(80); // Other Result
			updateTable.getColumnModel().getColumn(14).setPreferredWidth(80); // Submitter ID
			updateTable.getColumnModel().getColumn(15).setPreferredWidth(100);// Submitter Name
			updateTable.getColumnModel().getColumn(16).setPreferredWidth(60); // Submitter State
			updateTable.getColumnModel().getColumn(17).setPreferredWidth(70); // Patient ID
			updateTable.getColumnModel().getColumn(18).setPreferredWidth(100);// Patient First Name
			updateTable.getColumnModel().getColumn(19).setPreferredWidth(60); // Patient Middle Name
			updateTable.getColumnModel().getColumn(20).setPreferredWidth(100);// Patient Last Name
			updateTable.getColumnModel().getColumn(21).setPreferredWidth(80); // Multiple Unit
			updateTable.getColumnModel().getColumn(22).setPreferredWidth(100);// Street Address
			updateTable.getColumnModel().getColumn(23).setPreferredWidth(100);// City
			updateTable.getColumnModel().getColumn(24).setPreferredWidth(40); // State
			updateTable.getColumnModel().getColumn(25).setPreferredWidth(100);// County
			updateTable.getColumnModel().getColumn(26).setPreferredWidth(50); // Postal Code
			updateTable.getColumnModel().getColumn(27).setPreferredWidth(50); // Postal Code+4
			updateTable.getColumnModel().getColumn(28).setPreferredWidth(50); // Gender
			updateTable.getColumnModel().getColumn(29).setPreferredWidth(70); // Patient DOB
			updateTable.getColumnModel().getColumn(30).setPreferredWidth(50); // Patient Age
			updateTable.getColumnModel().getColumn(31).setPreferredWidth(50);// CDC CLuster Code
			updateTable.getColumnModel().getColumn(32).setPreferredWidth(50);// Region

			/*
			 * Add components to the GUI layout for display. The GUI was created with
			 * the Matisse GUI builder in NetBeans, so be careful when making changes!
			 * 
			 * NOTE! GUI was imported into WindowsBuilder for easier maintenance
			 */
			Toolkit tk2 = Toolkit.getDefaultToolkit();
			Dimension d2 = tk2.getScreenSize();
			updateScrollPane.setPreferredSize(new java.awt.Dimension(d2.width, d2.height));

			javax.swing.GroupLayout updatedRecordsPanelLayout = new javax.swing.GroupLayout(updatedRecordsPanel);
			updatedRecordsPanel.setLayout(updatedRecordsPanelLayout);
			updatedRecordsPanelLayout.setHorizontalGroup(updatedRecordsPanelLayout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(updateScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE));
			updatedRecordsPanelLayout.setVerticalGroup(updatedRecordsPanelLayout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(updateScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, d2.height - 400,
									Short.MAX_VALUE));

			editReportTabbedPane.addTab("Uploaded Records", updatedRecordsPanel);

			/*
			 * Report Recap Section of the GUI
			 */
			recapPanel.setBackground(Color.white);
			recapPanel.setLayout(null);

			headerTitle.setFont(new java.awt.Font("Tahoma", 1, 13));
			headerTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			headerTitle.setText("Import Recap");
			recapPanel.add(headerTitle);
			headerTitle.setBounds(150, 33, 113, 16);

			fromDateLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
			fromDateLabel1.setText("From Date:");
			recapPanel.add(fromDateLabel1);
			fromDateLabel1.setBounds(34, 73, 71, 16);

			fromDate1.setBorder(null);
			fromDate1.setOpaque(false);
			fromDate1.setFocusable(false);
			recapPanel.add(fromDate1);
			fromDate1.setBounds(110, 70, 78, 22);

			thruDateLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
			thruDateLabel1.setText("Thru");
			recapPanel.add(thruDateLabel1);
			thruDateLabel1.setBounds(192, 73, 29, 16);

			thruDate1.setBorder(null);
			thruDate1.setOpaque(false);
			thruDate1.setFocusable(false);
			recapPanel.add(thruDate1);
			thruDate1.setBounds(230, 70, 78, 22);

			inputFileLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
			inputFileLabel1.setText("Input File:");
			recapPanel.add(inputFileLabel1);
			inputFileLabel1.setBounds(34, 108, 64, 16);

			inputFileName1.setEditable(false);
			inputFileName1.setBorder(null);
			inputFileName1.setOpaque(false);
			recapPanel.add(inputFileName1);
			inputFileName1.setBounds(110, 105, 420, 22);

			runDateLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
			runDateLabel1.setText("Run Date:");
			recapPanel.add(runDateLabel1);
			runDateLabel1.setBounds(34, 140, 65, 16);

			runDate1.setBorder(null);
			runDate1.setOpaque(false);
			runDate1.setFocusable(false);
			recapPanel.add(runDate1);
			runDate1.setBounds(112, 138, 96, 22);

			totalRecordsLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			totalRecordsLabel.setText("Total Records in the File:");
			recapPanel.add(totalRecordsLabel);
			totalRecordsLabel.setBounds(34, 201, 159, 16);

			totalRecordsField.setEditable(false);
			totalRecordsField.setFont(new java.awt.Font("Tahoma", 0, 13));
			totalRecordsField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			totalRecordsField.setBorder(null);
			totalRecordsField.setOpaque(false);
			recapPanel.add(totalRecordsField);
			totalRecordsField.setBounds(280, 201, 40, 16);

			rejectedRecordsLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			rejectedRecordsLabel.setText("Records Rejected with Errors:");
			recapPanel.add(rejectedRecordsLabel);
			rejectedRecordsLabel.setBounds(34, 230, 196, 16);

			rejectedRecordsField.setEditable(false);
			rejectedRecordsField.setFont(new java.awt.Font("Tahoma", 0, 13));
			rejectedRecordsField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			rejectedRecordsField.setBorder(null);
			rejectedRecordsField.setOpaque(false);
			recapPanel.add(rejectedRecordsField);
			rejectedRecordsField.setBounds(280, 230, 40, 16);

			skippedRecordsLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			skippedRecordsLabel.setText("Records Not Within The Date Range:");
			recapPanel.add(skippedRecordsLabel);
			skippedRecordsLabel.setBounds(34, 264, 254, 16);

			skippedRecordsField.setEditable(false);
			skippedRecordsField.setFont(new java.awt.Font("Tahoma", 0, 13));
			skippedRecordsField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			skippedRecordsField.setBorder(null);
			skippedRecordsField.setOpaque(false);
			recapPanel.add(skippedRecordsField);
			skippedRecordsField.setBounds(280, 264, 40, 16);

			addedRecordsLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			addedRecordsLabel.setText("Records Added to the Database:");
			recapPanel.add(addedRecordsLabel);
			addedRecordsLabel.setBounds(34, 294, 220, 16);

			addedRecordsField.setEditable(false);
			addedRecordsField.setFont(new java.awt.Font("Tahoma", 0, 13));
			addedRecordsField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			addedRecordsField.setBorder(null);
			addedRecordsField.setOpaque(false);
			recapPanel.add(addedRecordsField);
			addedRecordsField.setBounds(280, 294, 40, 16);

			changedRecordsLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			changedRecordsLabel.setText("Records Changed in the Database:");
			recapPanel.add(changedRecordsLabel);
			changedRecordsLabel.setBounds(32, 326, 240, 16);

			changedRecordsField.setEditable(false);
			changedRecordsField.setFont(new java.awt.Font("Tahoma", 0, 13));
			changedRecordsField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			changedRecordsField.setBorder(null);
			changedRecordsField.setOpaque(false);
			recapPanel.add(changedRecordsField);
			changedRecordsField.setBounds(280, 326, 40, 16);

			javax.swing.GroupLayout recapReportPanelLayout = new javax.swing.GroupLayout(recapReportPanel);
			recapReportPanel.setLayout(recapReportPanelLayout);
			recapReportPanel.setBackground(Color.white);
			recapReportPanelLayout.setHorizontalGroup(recapReportPanelLayout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(	recapReportPanelLayout
										.createSequentialGroup()
										.addComponent(recapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 455,
														javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 643, Short.MAX_VALUE)));
			recapReportPanelLayout.setVerticalGroup(recapReportPanelLayout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(recapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE));

			editReportTabbedPane.addTab("Recap Report", recapReportPanel);

			/*
			 * GUI Header Information
			 */
			printButton.setFont(new java.awt.Font("Tahoma", 1, 13));
			printButton.setLabel("Print");
			printButton.setMinimumSize(new java.awt.Dimension(60, 24));
			printButton.setPreferredSize(new java.awt.Dimension(60, 24));
			printButton.addActionListener(formListener);

			runDateLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
			runDateLabel.setText("Run Date:");
			runDate.setBorder(null);
			runDate.setOpaque(false);
			runDate.setFocusable(false);

			fromDate.setBorder(null);
			fromDate.setOpaque(false);
			fromDate.setFocusable(false);
			thruDate.setBorder(null);
			thruDate.setOpaque(false);
			thruDate.setFocusable(false);

			inputFileName.setBorder(null);
			inputFileName.setOpaque(false);
			inputFileName.setFocusable(false);

			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(	layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(editReportTabbedPane)
						.addGroup(	layout.createSequentialGroup()
						.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(inputFileLabel)
							.addComponent(runDateLabel))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(	layout.createSequentialGroup()
							.addComponent(runDate,javax.swing.GroupLayout.PREFERRED_SIZE,96,javax.swing.GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							.addComponent(	fromDateLabel)
							.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(	fromDate,javax.swing.GroupLayout.PREFERRED_SIZE,98,javax.swing.GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(	thruDateLabel)
							.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(	thruDate,javax.swing.GroupLayout.PREFERRED_SIZE,91,javax.swing.GroupLayout.PREFERRED_SIZE) // .addGap(294,// 294, 294))
							.addGap(750,750,750))
							.addGroup(	layout.createSequentialGroup()
							.addComponent(	inputFileName,javax.swing.GroupLayout.PREFERRED_SIZE,340,javax.swing.GroupLayout.PREFERRED_SIZE) // .addGap(253,// 253, 253)
							.addGap(510,510,510)
							.addComponent(	reportTitleLabel)
							.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED,javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							.addComponent(	printButton,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
							.addGap(20,20,20)))))
							.addContainerGap()));
			layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(	layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(inputFileLabel)
						.addComponent(	inputFileName,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE))
					.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(	printButton,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(reportTitleLabel)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(fromDateLabel)
						.addComponent(thruDateLabel)
						.addComponent(fromDate,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(thruDate,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(runDateLabel)
						.addComponent(runDate,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(editReportTabbedPane)));
			pack();
			}

		/*
		 * Code for dispatching events from components to event handlers.
		 */

		private class FormListener implements java.awt.event.ActionListener
			{
				FormListener()
					{
					}

				public void actionPerformed(java.awt.event.ActionEvent evt)
					{
					if (evt.getSource() == printButton)
						{
							FileImport_EditReport.this.printButtonActionPerformed(evt);
						}
					}
			}

		/*
		 * Print the selected report (based upon the selected Tab)
		 */
		private void printButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
			MessageFormat header = null;
			header = new MessageFormat(reportTitleLabel.getText());

			MessageFormat footer = null;
			inputFileName = FileImport_FileSelection.fileSelectionText;

			getSelectedIndex();

			try
				{
					if (editReportTabbedPane.getSelectedIndex() == 0)
						{
							errorTable.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, null, true, null);
						}
					else
						if (editReportTabbedPane.getSelectedIndex() == 1)
							{
								updateTable.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, null, true, null);
							}
						else
							if (editReportTabbedPane.getSelectedIndex() == 2)
								{
									PrintUtility.printComponent(recapReportPanel);
								}

				}
			catch (HeadlessException pe)
				{
					pe.printStackTrace();
				}
			catch (PrinterException pe)
				{
					pe.printStackTrace();
				}
			};

		/*
		 * Get the index of the selected Tab for printing (0=1st page, 1=2nd page, 2=3rd page)
		 */
		public int getSelectedIndex()
			{
			return tabIndex;
			}

		/*
		 * Program "main" section
		 */
		public static void main(String args[])
			{
			UIManager.put("swing.Windows", Boolean.TRUE);
			try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
			catch (Exception e)
				{
				}

			/*
			 * Create and display the form
			 */
			java.awt.EventQueue.invokeLater(new Runnable()
				{

					public void run()
						{
						Toolkit tk = Toolkit.getDefaultToolkit();
						Dimension d = tk.getScreenSize();

						FileImport_EditReport frame = new FileImport_EditReport();
						frame.setSize(d.width - 50, d.height - 120);
						frame.setTitle("CIFOR - View Data Files");
						frame.setLocation(20, 20);
						frame.setVisible(true);
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						}
				});
			}

		/*
		 * Program Variables
		 */
		protected String inputDatafile;

		public int tabIndex;

		private javax.swing.JTextField addedRecordsField;
		private javax.swing.JLabel addedRecordsLabel;
		private javax.swing.JTextField changedRecordsField;
		private javax.swing.JLabel changedRecordsLabel;
		private javax.swing.JTextField skippedRecordsField;
		private javax.swing.JLabel skippedRecordsLabel;
		private javax.swing.JTabbedPane editReportTabbedPane;
		private javax.swing.JPanel errorPanel;
		private javax.swing.JTable errorTable;
		private javax.swing.JTextField fromDate;
		private javax.swing.JLabel fromDateLabel;
		private javax.swing.JLabel inputFileLabel;
		private javax.swing.JTextField inputFileName;

		private javax.swing.JTextField fromDate1;
		private javax.swing.JLabel fromDateLabel1;
		private javax.swing.JLabel inputFileLabel1;
		private javax.swing.JTextField inputFileName1;
		private javax.swing.JTextField thruDate1;
		private javax.swing.JLabel thruDateLabel1;
		private javax.swing.JTextField runDate1;

		private java.awt.Button printButton;
		private javax.swing.JPanel recapReportPanel;
		private javax.swing.JPanel recapPanel;
		private javax.swing.JLabel headerTitle;
		private javax.swing.JTextField rejectedRecordsField;
		private javax.swing.JLabel rejectedRecordsLabel;
		private javax.swing.JLabel reportTitleLabel;
		private javax.swing.JTextField thruDate;
		private javax.swing.JLabel thruDateLabel;
		private javax.swing.JTextField totalRecordsField;
		private javax.swing.JLabel totalRecordsLabel;
		private javax.swing.JPanel updatedRecordsPanel;
		private javax.swing.JTable updateTable;
		private javax.swing.JLabel runDateLabel;
		private javax.swing.JLabel runDateLabel1;
		private javax.swing.JTextField runDate;
		public static String errorDescription;

	}
