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

import java.io.File;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
*
* @author jonesg1
*/

/*
 * This program is called by the MainMenu program to allow the user to
 * select a file to import into the CIFOR Database.
 */

public class FileImport_FileSelection extends javax.swing.JFrame
	{
		private static final long	serialVersionUID	= 1L;

		/*
		 * Select a Results .txt file to view, edit, and import
		 */
		public FileImport_FileSelection()
			{
				getProperties();
				initComponents();
				displayGUI();
			}

		/*
		 * Create the GUI Components and Variables
		 */
		public void initComponents()
			{
				fileSelectionLabel = new javax.swing.JLabel();
				fileSelectionString = new String();
				fileSelectionText = new javax.swing.JTextField();
				fileSelectionText1 = new javax.swing.JTextField();
				browseButton = new javax.swing.JButton();
				viewButton = new javax.swing.JButton();
				uploadButton = new javax.swing.JButton();
				cancelButton = new javax.swing.JButton();
				helpButton = new javax.swing.JButton();

				FormListener formListener = new FormListener();

				setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
				setTitle("CIFOR - File Import");
				setLocation(300, 200);

				fileSelectionLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
				fileSelectionLabel.setText("Select a File to Import");

				fileSelectionText.setFont(new java.awt.Font("Tahoma", 0, 12));

				browseButton.setFont(new java.awt.Font("Tahoma", 0, 14));
				browseButton.setText("Browse");
				browseButton.addActionListener(formListener);

				viewButton.setFont(new java.awt.Font("Tahoma", 0, 14));
				viewButton.setText("View");
				viewButton.addActionListener(formListener);

				uploadButton.setFont(new java.awt.Font("Tahoma", 0, 14));
				uploadButton.setText("Upload");
				uploadButton.addActionListener(formListener);

				cancelButton.setFont(new java.awt.Font("Tahoma", 0, 14));
				cancelButton.setText("Cancel");
				cancelButton.addActionListener(formListener);

				helpButton.setFont(new java.awt.Font("Tahoma", 0, 14));
				helpButton.setText("Help");
				helpButton.addActionListener(formListener);
			}

		/*
		 * Add components to the GUI layout for display. The GUI was created with
		 * the Matisse GUI builder in NetBeans, so be careful when making changes!
		 * 
		 * Note! The GUI has been imported to the Eclipse Window Builder for
		 * easier maintenance
		 */
		private void displayGUI()
			{

				javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
				getContentPane().setLayout(layout);
				layout.setHorizontalGroup(layout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(	layout.createSequentialGroup()
						.addGap(27, 27, 27)
						.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(	layout.createSequentialGroup()
							.addComponent(	fileSelectionLabel,javax.swing.GroupLayout.PREFERRED_SIZE,160,javax.swing.GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED,186,Short.MAX_VALUE))
							.addGroup(	layout.createSequentialGroup()
							.addComponent(fileSelectionText)
							.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(browseButton))
							.addGroup(	layout.createSequentialGroup()
							.addComponent(viewButton)
							.addGap(29, 29, 29)
							.addComponent(uploadButton)
							.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED,javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							.addComponent(cancelButton)
							.addGap(18, 18, 18)
							.addComponent(helpButton)))
							.addGap(27, 27, 27)));
				layout.setVerticalGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(	layout.createSequentialGroup()
						.addGap(27, 27, 27)
						.addComponent(fileSelectionLabel)
						.addGap(18, 18, 18)
						.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(browseButton)
						.addComponent(fileSelectionText,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(35, 35, 35)
						.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(cancelButton).addComponent(uploadButton)
						.addComponent(viewButton).addComponent(helpButton))
						.addContainerGap(33, Short.MAX_VALUE)));

				pack();
			}

		/*
		 * This code is used for dispatching events from components to event handlers
		 */
		private class FormListener implements java.awt.event.ActionListener
			{
				FormListener()
					{
					}

				public void actionPerformed(java.awt.event.ActionEvent evt)
					{
						if (evt.getSource() == browseButton)
							{
								FileImport_FileSelection.this.browseButtonActionPerformed(evt);
							}
						else
							if (evt.getSource() == viewButton)
								{
									FileImport_FileSelection.this.viewButtonActionPerformed(evt);
								}
							else
								if (evt.getSource() == uploadButton)
									{
										FileImport_FileSelection.this.uploadButtonActionPerformed(evt);
									}
								else
									if (evt.getSource() == cancelButton)
										{
											FileImport_FileSelection.this.cancelButtonActionPerformed(evt);
										}
									else
										if (evt.getSource() == helpButton)
											{
												FileImport_FileSelection.this.helpButtonActionPerformed(evt);
											}
					}
			}

		/*
		 * Perform the Browse Button actions - Set the initial directory location,
		 * Filter the File Extensions, Display a FileChooser and get the selected file name
		 */
		private void browseButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "xls"); // Set a file filter
				fc.setCurrentDirectory(new File(ciforDataFiles)); // Set the directory
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						File selectedFileName = fc.getSelectedFile(); // Get the selected file
						fileSelectionText.setText(fc.getSelectedFile().getAbsolutePath()); // Get the file path
						fileSelectionText1.setText(fc.getSelectedFile().getAbsolutePath()); // Temporary solution for
						passedFile = fc.getSelectedFile(); // Create File object for DataFileModel
						displayGUI(); // Update the GUI

					}
				else
					{
						JOptionPane.showMessageDialog(controllingFrame, "File Selection Cancelled");
					}
			}

		/*
		 * Perform the View Button actions - Run the DataFileTable/DataFileModel programs to
		 * display the selected file in a JTable for review
		 */
		private void viewButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
				if (passedFile == null)
					{
						JOptionPane.showMessageDialog(controllingFrame, "File Not Selected");

					}
				else
					{
						View_DataFileTable.main(null);
					}
			}

		/*
		 * Perform the Upload Button actions
		 */
		private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
				if (passedFile == null)
					{
						JOptionPane.showMessageDialog(controllingFrame, "File Not Selected");
					}
				else
					{
						try
							{
								FileImport_GetDateRange.main(null);
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
						dispose();

					}
			}

		/*
		 * Perform the Cancel Button actions - Close the Program
		 */
		private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
				dispose();
			}

		/*
		 * Perform the Help Button actions - Display a Help message dialog
		 */
		private void helpButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
				JOptionPane.showMessageDialog(controllingFrame, "Press the Browse button to Select a file to import.\n"
						+ "After selecting a file, press the Import button to\n"
						+ "import the file and update the database, or press\n"
						+ "the View button to view the contents of the file.\n" + "  \n"
						+ "Files must have the .txt extension" + " \n");
			}

		/*
		 * Get the properties for the database connection
		 */
		public static void getProperties()
			{
				ciforDataFiles = c4Utilities.CIFORProperties.cifor_DataFiles;
				ciforTitle = c4Utilities.CIFORProperties.ciforTitle;
				ciforVersion = c4Utilities.CIFORProperties.ciforVersion;
				ciforHeading = "  " + ciforTitle + "   " + ciforVersion;
			}

		/*
		 * This is the "main" section of the program
		 */
		public static void main(String args[])
			{
				java.awt.EventQueue.invokeLater(new Runnable()
					{

						public void run()
							{
								UIManager.put("swing.boldMetal", Boolean.FALSE);
								new FileImport_FileSelection().setVisible(true);
							}
					});
			}

		/*
		 * Set the Program Variables
		 */
		private static JFrame				controllingFrame;		// Used for dialogs
		private static javax.swing.JLabel	fileSelectionLabel;
		public static String				fileSelectionString;
		public static JTextField			fileSelectionText;
		public static JTextField			fileSelectionText1;
		public static InputStream			selectedFilePath;
		public static String				selectedFileName;
		public static File					passedFile;
		private static javax.swing.JButton	browseButton;
		private static javax.swing.JButton	cancelButton;
		private static javax.swing.JButton	helpButton;
		private static javax.swing.JButton	uploadButton;
		private static javax.swing.JButton	viewButton;

		private static String				ciforDataFiles;
		private static String				ciforTitle;
		private static String				ciforVersion;
		private static String				ciforHeading;

	}
