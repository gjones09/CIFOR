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

import javax.swing.*;
import javax.swing.table.TableColumn;
import c4Utilities.DateFormatUtility;
import java.awt.*;
import java.awt.Dimension;		// to get screen size
import java.awt.Font;
import java.awt.Toolkit;		// to get screen size

/**
*
* @author jonesg1
*/

public class View_DataFileTable extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private javax.swing.JButton 	closeButton;
	private javax.swing.JLabel 		currentDateLabel;
	private javax.swing.JTextField 	currentDateText;
	private javax.swing.JLabel 		headerLabel;
	private javax.swing.JPanel 		headerPanel;
	private javax.swing.JLabel 		selectedFileNameLabel;
	public  javax.swing.JTextField 	inputCurrentDate;
	public static String 			stringCurrentDate;

	public View_DataFileTable(String dataFilePath) {
		
		DateFormatUtility.CalculateDate();		// Run the date utility to get the current date 
		
		headerPanel 			= new javax.swing.JPanel();

		closeButton 			= new javax.swing.JButton();
		closeButton.setFont(new java.awt.Font("Tahoma", 0, 14));
		closeButton.setText("Close");
		FormListener formListener = new FormListener();
		closeButton.addActionListener(formListener);
		
		headerLabel 			= new javax.swing.JLabel();
		headerLabel.setFont(new java.awt.Font("Tahoma", Font.BOLD, 16));
		headerLabel.setText("View Data Files");
		
		selectedFileNameLabel 	= new javax.swing.JLabel();
		selectedFileNameLabel.setText("Selected File: ");
		selectedFileNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		selectedFileNameLabel.setBorder(null);
		
		FileImport_FileSelection.fileSelectionText.setEditable(false);
		FileImport_FileSelection.fileSelectionText.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
		FileImport_FileSelection.fileSelectionText.setBorder(null);

		currentDateLabel 		= new javax.swing.JLabel();
		currentDateLabel.setText("Current Date: ");
		currentDateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		currentDateLabel.setBorder(null);
		
		currentDateText 		= new javax.swing.JTextField();
		currentDateText.setText(DateFormatUtility.currDate);
		currentDateText.setEditable(false);
		currentDateText.setFont(new Font("Tahoma", Font.BOLD, 12));
		currentDateText.setBorder(null);

		JTable table;
		View_DataFileModel model;

		setLayout(new BorderLayout());
		model = new View_DataFileModel(dataFilePath);	// This is where data vector is created
		table = new JTable();
		table.setModel(model);
		table.createDefaultColumnsFromModel();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);		// was set to true
		table.setIntercellSpacing(new java.awt.Dimension(2, 2));
		table.setRowHeight(20);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	// was set to _ALL_COLUMNS

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		/*
		 *   Define the table's column widths
		 */
		TableColumn column = null;
		
		for (int i = 0; i < 32; i++) 
		{
			column = table.getColumnModel().getColumn(i);
			if 		(i == 0)  {column.setPreferredWidth(90);}		// Lab Accession No
			else if (i == 1)  {column.setPreferredWidth(90);}		// Submitter Specimen ID
			else if (i == 2)  {column.setPreferredWidth(30);}		// Specimen Type (S/I)
			else if (i == 3)  {column.setPreferredWidth(60);}		// Date Collected
			else if (i == 4)  {column.setPreferredWidth(60);}		// Date Received
			else if (i == 5)  {column.setPreferredWidth(60);}		// Date Reported
			else if (i == 6)  {column.setPreferredWidth(150);}		// Primary Test
			else if (i == 7)  {column.setPreferredWidth(220);}		// Organism
			else if (i == 8)  {column.setPreferredWidth(120);}		// CDC Primary Enzyme Pattern
			else if (i == 9)  {column.setPreferredWidth(120);}		// CDC Secondary Enzyme Pattern
			else if (i == 10) {column.setPreferredWidth(160);}		// Other Result
			else if (i == 11) {column.setPreferredWidth(60);}		// Submitter ID
			else if (i == 12) {column.setPreferredWidth(80);}		// Submitter Name
			else if (i == 13) {column.setPreferredWidth(60);}		// Submitter State
			else if (i == 14) {column.setPreferredWidth(60);}		// Patient ID
			else if (i == 15) {column.setPreferredWidth(70);}		// Patient First Name
			else if (i == 16) {column.setPreferredWidth(50);}		// Patient Middle Name
			else if (i == 17) {column.setPreferredWidth(70);}		// Patient Last Name
			else if (i == 18) {column.setPreferredWidth(50);}		// Multiple Unit
			else if (i == 19) {column.setPreferredWidth(50);}		// Street Address
			else if (i == 20) {column.setPreferredWidth(60);}		// City
			else if (i == 21) {column.setPreferredWidth(40);}		// State
			else if (i == 22) {column.setPreferredWidth(60);}		// County
			else if (i == 23) {column.setPreferredWidth(50);}		// Postal Code
			else if (i == 24) {column.setPreferredWidth(50);}		// Postal Code+4
			else if (i == 25) {column.setPreferredWidth(50);}		// Gender
			else if (i == 26) {column.setPreferredWidth(60);}		// Date of Birth
			else if (i == 27) {column.setPreferredWidth(60);}		// Patient Age
			else if (i == 28) {column.setPreferredWidth(60);}		// CDC Cluster Code
			else if (i == 29)  {column.setPreferredWidth(120);}		// Local Primary Enzyme Pattern
			else if (i == 30)  {column.setPreferredWidth(120);}		// Local Secondary Enzyme Pattern
			else if (i == 31) {column.setPreferredWidth(60);}		// Region
		} 

		/*
		/*  Add components to the GUI layout for display.  The GUI was created with
		/*  the Matisse GUI builder in NetBeans, so be careful when making changes!
		*/
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		scrollPane.setPreferredSize(new java.awt.Dimension(d.width, d.height));

		javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
		headerPanel.setLayout(headerPanelLayout);
		headerPanelLayout.setHorizontalGroup(
				headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(headerPanelLayout.createSequentialGroup()
						.addComponent(selectedFileNameLabel)
						.addGap(18, 18, 18)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(FileImport_FileSelection.fileSelectionText, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(390, 390, 390)
						.addComponent(headerLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
						.addComponent(closeButton))
						.addGroup(headerPanelLayout.createSequentialGroup()
								.addComponent(currentDateLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(currentDateText, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(d.width-400, d.width-400, d.width-400))  // width of table (1500)
		);
		headerPanelLayout.setVerticalGroup(
				headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(headerPanelLayout.createSequentialGroup()
						.addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(selectedFileNameLabel)
								.addComponent(FileImport_FileSelection.fileSelectionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(closeButton)
								.addComponent(headerLabel))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(currentDateLabel)
										.addComponent(currentDateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
		);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
								.addComponent(headerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, d.height-200, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(29, Short.MAX_VALUE))	
		);
		pack();
	}

	/*
	 *   Code for dispatching events from components to event handlers.
	 */
	private class FormListener implements java.awt.event.ActionListener {
		FormListener() {}
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			if (evt.getSource() == closeButton) {
				View_DataFileTable.this.closeButtonActionPerformed(evt);
			}
		}
	}

	private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		dispose();
	}

	/*
	 *  Main section
	 */
	public static void main(String s[]) {
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		
		View_DataFileTable frame = new View_DataFileTable(null);
		frame.setSize(d.width-50, d.height-70);
		frame.setTitle("CIFOR - View Data Files");
		frame.setLocation(20, 20);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}