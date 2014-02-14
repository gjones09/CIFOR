/*
 * The contents of this file are subject to the Mozilla Public License Version 1.1 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either
 * express or implied. See the License for the specific language governing rights and limitations under the License.
 * 
 * - Redistributions of source code must retain the above License - notice and all references to the OpenELIS
 * Foundation.
 * 
 * Copyright (C) The OpenELIS Foundation. All Rights Reserved.
 * 
 */

package c4Menu;

import javax.swing.JLabel;
import javax.swing.JTextArea;
/**
*
* @author jonesg1
*/

/*
 * The About program displays information about the CIFOR Application and it's development
 */
public class About extends javax.swing.JDialog
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		JLabel image;

		public About()
			{
				processSection();
			}

		/*
		 * Processing section of the program
		 */
		private void processSection()
			{
				closeButton = new javax.swing.JButton();
				JLabel titleLabel = new JLabel();
				JLabel versionLabel = new JLabel();
				JLabel version = new JLabel();
				JLabel vendorLabel = new JLabel();
				JLabel vendor = new JLabel();
				JLabel homepageLabel = new JLabel();
				JLabel homepage = new JLabel();
				JLabel imageLabel = new JLabel(); // Logo
				JTextArea descLabel = new JTextArea(
						"CIFOR has been developed thru collaboration of the APHL, CSTE, and CDC,"
								+ " along with the Council to Improve Foodborne Outbreak Response (CIFOR).");

				FormListener formListener = new FormListener();

				setLocation(500, 200);

				setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
				setModal(true);
				setName("aboutBox");
				setResizable(false);

				titleLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
				titleLabel.setText(c4Utilities.CIFORProperties.aboutTitleLabel);

				versionLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
				versionLabel.setText(c4Utilities.CIFORProperties.aboutVersionLabel);

				version.setFont(new java.awt.Font("Tahoma", 0, 14));
				version.setText(c4Utilities.CIFORProperties.ciforVersion);

				vendorLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
				vendorLabel.setText(c4Utilities.CIFORProperties.aboutVendorLabel);

				vendor.setFont(new java.awt.Font("Tahoma", 0, 14));
				vendor.setText(c4Utilities.CIFORProperties.aboutVendor);

				homepageLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
				homepageLabel.setText(c4Utilities.CIFORProperties.aboutHomePageLabel);

				homepage.setFont(new java.awt.Font("Tahoma", 0, 14));
				homepage.setText(c4Utilities.CIFORProperties.aboutHomePage);

				descLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
				descLabel.setLineWrap(true);
				descLabel.setWrapStyleWord(true);
				descLabel.setAutoscrolls(false);
				descLabel.setBorder(null);
				descLabel.setDisabledTextColor(new java.awt.Color(0, 0, 0));
				descLabel.setEnabled(false);
				descLabel.setOpaque(false);
				descLabel.setName("appDescLabel");
				descLabel.setLineWrap(true);

				closeButton.setText(c4Utilities.CIFORProperties.closeButton);
				closeButton.setFont(new java.awt.Font("Tahoma", 0, 14));
				closeButton.setBorder(javax.swing.BorderFactory
						.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
				closeButton.addActionListener(formListener);

				/*
				 * Setup the GUI section of the program This GUI was initially developed with the NetBeans GUI builder
				 * and then converted to the Eclipse plugin - WindowsBuilder Editor
				 */
				javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
				getContentPane().setLayout(layout);
				layout.setHorizontalGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(	layout.createSequentialGroup()
											.addComponent(imageLabel)
											.addGap(25, 25, 25)
											.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.TRAILING)
																.addGroup(	javax.swing.GroupLayout.Alignment.LEADING,
																			layout.createSequentialGroup()
																					.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
																										.addComponent(	versionLabel)
																										.addComponent(	vendorLabel)
																										.addComponent(	homepageLabel))
																					.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																					.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
																										.addComponent(	version)
																										.addComponent(	vendor)
																										.addComponent(	homepage)))
																.addComponent(	titleLabel,
																				javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(	descLabel,
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				310, Short.MAX_VALUE)
																.addComponent(closeButton,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				75,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
											.addContainerGap()));
				layout.setVerticalGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 228, Short.MAX_VALUE)
						.addGroup(	layout.createSequentialGroup()
											.addContainerGap()
											.addComponent(titleLabel)
											.addGap(15, 15, 15)
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(descLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
															javax.swing.GroupLayout.DEFAULT_SIZE,
															javax.swing.GroupLayout.PREFERRED_SIZE)
											.addGap(15, 15, 15)
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
											.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(versionLabel).addComponent(version))
											.addGap(15, 15, 15)
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
											.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(vendorLabel).addComponent(vendor))
											.addGap(15, 15, 15)
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
											.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(homepageLabel).addComponent(homepage))
											.addGap(15, 15, 15)
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32,
																Short.MAX_VALUE)
											.addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
															javax.swing.GroupLayout.PREFERRED_SIZE)
											.addContainerGap(25, 25)));
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

						if (evt.getSource() == closeButton)
							{
								About.this.closeButtonActionPerformed(evt);
							}
						else if (evt.getSource() != closeButton)
							{
								System.out.println("Close doesn't work");
							}
					}
			}

		private void closeButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
				dispose();
			}

		/*
		 * Main section
		 */
		public static void main(String args[])
			{
				java.awt.EventQueue.invokeLater(new Runnable()
					{

						public void run()
							{
								new About().setVisible(true);
							}
					});
			}

		/*
		 * Variables declaration
		 */
		private static javax.swing.JButton closeButton;

	}