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

package c4Menu;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.event.*;
import java.awt.*;
import c4FileImport.FileImport_FileSelection;

/**
*
* @author jonesg1
*/

/*
 * CIFOR Menu Menu
 * 
 * The Main Menu controls all activities for the CIFOR application
 */
public class MainMenu extends JFrame implements ActionListener
	{

		private static final long serialVersionUID = 1L;

		/*
		 * This is the processing section of the program. 
		 * Screen size is determined and set for the monitor size.
		 */

		public MainMenu()
			{
			getProperties();
	        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("MessagesBundle_en_US"); // NOI18N

			ciforHeading = "  " + bundle.getString("Title") + "  " + bundle.getString("Version");
			setTitle(ciforHeading);
			
			// Make the main menu be indented X and Y pixels from each edge of the screen.
			int insetX = 150;
			int insetY = 50;
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setBounds(insetX, insetY, screenSize.width - insetX * 5, screenSize.height - insetX * 3);

			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();

			int newWidth = d.width;
			int newHeight = d.height;

			if (d.width > 1900)
				{
					newWidth = d.width - 750;
					newHeight = d.height - 300;
					insetX = 100;
					insetY = 40;
				}
			else
				if (d.width < 1900)
					{
						newWidth = d.width - 100;
						newHeight = d.height - 100;
						insetX = 50;
						insetY = 20;
					}

			setBounds(insetX, insetY, newWidth, newHeight);

			iconLabel = new JLabel();
			//iconLabel.setIcon(new javax.swing.ImageIcon(ciforLogo));
			ImageIcon ciforImage = new ImageIcon( getClass().getResource("/images/CIFOR_Image.png") );
			iconLabel.setIcon(ciforImage);
			iconLabel.setVerticalAlignment(JLabel.CENTER);
			iconLabel.setHorizontalAlignment(JLabel.CENTER);

			setJMenuBar(createMenuBar());
			}

		/*
		 * Display the Main Menu and setup the references for the menu items as well as the shortcut keys
		 */
		protected JMenuBar createMenuBar()
			{

			JMenuBar menuBar = new JMenuBar();
			menuBar.setOpaque(true);

			JMenu menu = new JMenu(ciforFileMenu + "     ");
			menu.setMnemonic(KeyEvent.VK_F);
			menuBar.add(menu);

			JMenuItem menuItem = new JMenuItem(ciforFileMenu_UserSetup + "     ");
			menuItem.setMnemonic(KeyEvent.VK_U);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("userSetup");
			menuItem.addActionListener(this);
			// menuItem.setEnabled(MenuActive);
			menuItem.setEnabled(false);
			menu.add(menuItem);

			menuItem = new JMenuItem(ciforFileMenu_MetaData + "  ");
			menuItem.setMnemonic(KeyEvent.VK_B);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("database");
			menuItem.addActionListener(this);
			menuItem.setEnabled(true);
			menu.add(menuItem);

			menuItem = new JMenuItem(ciforFileMenu_Exit + "     ");
			menuItem.setMnemonic(KeyEvent.VK_E);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("exit");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			menu = new JMenu(ciforImportMenu + "     ");
			menu.setMnemonic(KeyEvent.VK_I);
			menuBar.add(menu);

			menuItem = new JMenuItem(ciforImportMenu_Import);
			menuItem.setMnemonic(KeyEvent.VK_M);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("import");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			menu = new JMenu(ciforQueryMenu + "     ");
			menu.setMnemonic(KeyEvent.VK_Q);
			menuBar.add(menu);

			menuItem = new JMenuItem(ciforQueryMenu_DetailQuery + "     ");
			menuItem.setMnemonic(KeyEvent.VK_D);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("query");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			menu = new JMenu(ciforMapResultsMenu + "     ");
			menu.setMnemonic(KeyEvent.VK_M);
			menuBar.add(menu);

			menuItem = new JMenuItem(ciforMapResultsMenu_Query);
			menuItem.setMnemonic(KeyEvent.VK_M);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("mapping");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			menu = new JMenu(ciforReportMenu + "     ");
			menu.setMnemonic(KeyEvent.VK_R);
			menuBar.add(menu);

			menuItem = new JMenuItem(ciforReportMenu_ClusterReport);
			menuItem.setMnemonic(KeyEvent.VK_C);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("cluster");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			menuItem = new JMenuItem(ciforReportMenu_FrequencyAnalysis);
			menuItem.setMnemonic(KeyEvent.VK_A);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("frequency");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			menu = new JMenu(ciforHelpMenu + "     ");
			menu.setMnemonic(KeyEvent.VK_H);
			menuBar.add(menu);

			menuItem = new JMenuItem(ciforHelpMenu_UserGuide);
			menuItem.setMnemonic(KeyEvent.VK_G);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("userguide");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			menuItem = new JMenuItem(ciforHelpMenu_Welcome);
			menuItem.setMnemonic(KeyEvent.VK_W);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("welcome");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			return menuBar;

			}

		/*
		 * React to the menu selection.
		 */

		public void actionPerformed(ActionEvent e)
			{
			if ("login".equals(e.getActionCommand()))
				{
					loginFrame();
				}
			else
				if ("userSetup".equals(e.getActionCommand()))
					{
						userSetupFrame();
					}
				else
					if ("database".equals(e.getActionCommand()))
						{
							databaseFrame();
						}
					else
						if ("cluster".equals(e.getActionCommand()))
							{
								clusterFrame();
							}
						else
							if ("frequency".equals(e.getActionCommand()))
								{
									frequencyFrame();
								}
							else
								if ("import".equals(e.getActionCommand()))
									{
										importFrame();
									}
								else
									if ("query".equals(e.getActionCommand()))
										{
											detailFrame();
										}
									else
										if ("mapping".equals(e.getActionCommand()))
											{
												mappingFrame();
											}
										else
											if ("userguide".equals(e.getActionCommand()))
												{
													helpFrame();
												}
											else
												if ("welcome".equals(e.getActionCommand()))
													{
														welcomeFrame();
													}
												else
													{
														quit();
													}
			}

		/*
		 * Create a new frame for the GUI to display
		 */

		protected void loginFrame()
			{
			c4Admin.UserLogin.main(null);
			createMenuBar();
			}

		protected void userSetupFrame()
			{
			c4Admin.UserSetup.main(null);
			}

		protected void databaseFrame()
			{
			c4Admin.DBMetaData.main(null);
			}

		protected void clusterFrame()
			{
			c4ClusterReport.Parameters.main(null);
			}

		protected void frequencyFrame()
			{
			c4Reports.FrequencyReportParameters.main(null);
			}

		protected void detailFrame()
			{
			c4Reports.DetailReportParameters.main(null);
			}

		protected void mappingFrame()
			{
			c4Reports.MappingParameters.main(null);
			}

		protected void importFrame()
			{
			FileImport_FileSelection.main(null);
			}

		protected void helpFrame()
			{
			DisplayUserDoc.main(null);
			}

		protected void welcomeFrame()
			{
			About.main(null);
			}

		/*
		 * Read the properties file to get text fields for the GUI
		 */
		public static void getProperties()
			{
				c4Utilities.UserProperties.main(null); // Get the User Properties (RegionName)
				
				c4Utilities.CIFORProperties.getProperties();
	
				ciforFileMenu = c4Utilities.CIFORProperties.ciforFileMenu;
				ciforFileMenu_UserSetup = c4Utilities.CIFORProperties.ciforFileMenu_UserSetup;
				ciforFileMenu_MetaData = c4Utilities.CIFORProperties.ciforFileMenu_MetaData;
				ciforFileMenu_Exit = c4Utilities.CIFORProperties.ciforFileMenu_Exit;
	
				ciforImportMenu = c4Utilities.CIFORProperties.ciforImportMenu;
				ciforImportMenu_Import = c4Utilities.CIFORProperties.ciforImportMenu_Import;
	
				ciforQueryMenu = c4Utilities.CIFORProperties.ciforQueryMenu;
				ciforQueryMenu_DetailQuery = c4Utilities.CIFORProperties.ciforQueryMenu_DetailQuery;
	
				ciforMapResultsMenu = c4Utilities.CIFORProperties.ciforMapResultsMenu;
				ciforMapResultsMenu_Query = c4Utilities.CIFORProperties.ciforMapResultsMenu_Query;
	
				ciforReportMenu = c4Utilities.CIFORProperties.ciforReportMenu;
				ciforReportMenu_ClusterReport = c4Utilities.CIFORProperties.ciforReportMenu_ClusterReport;
				ciforReportMenu_FrequencyAnalysis = c4Utilities.CIFORProperties.ciforReportMenu_FrequencyAnalysis;
	
				ciforHelpMenu = c4Utilities.CIFORProperties.ciforHelpMenu;
				ciforHelpMenu_UserGuide = c4Utilities.CIFORProperties.ciforHelpMenu_UserGuide;
				ciforHelpMenu_Welcome = c4Utilities.CIFORProperties.ciforHelpMenu_Welcome;
	
				ciforLogo = c4Utilities.CIFORProperties.ciforLogo;
				ciforTitle = c4Utilities.CIFORProperties.ciforTitle;
				ciforVersion = c4Utilities.CIFORProperties.ciforVersion;
	
				// ciforHeading = "  " + ciforTitle + "   " + ciforVersion;
			}

		/*
		 * Quit the application and close any open components
		 */

		protected void quit()
			{
			System.exit(0);
			}

		/*
		 * main section of the program
		 */

		public static void main(String[] args)
			{
			SwingUtilities.invokeLater(new Runnable()
				{ // Create and show the Menu GUI
						public void run()
							{
							UIManager.put("swing.Windows", Boolean.TRUE);
							try
								{
									UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								}
							catch (Exception e)
								{
								}

							frame = new MainMenu();
							frame.add(iconLabel, BorderLayout.CENTER);

							frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							frame.setVisible(true);
							frame.setEnabled(false);

							c4Admin.UserLogin.main(null);

							}
					});

			}

		/*
		 * Program Variables
		 */

		public static JFrame frame;
		private JFrame controllingFrame;
		private static JLabel iconLabel;
		private static String ciforLogo;
		private static String ciforTitle;
		private static String ciforVersion;
		private static String ciforHeading;

		private static String ciforFileMenu;
		private static String ciforFileMenu_UserSetup;
		private static String ciforFileMenu_MetaData;
		private static String ciforFileMenu_Exit;

		private static String ciforImportMenu;
		private static String ciforImportMenu_Import;

		private static String ciforQueryMenu;
		private static String ciforQueryMenu_DetailQuery;

		private static String ciforMapResultsMenu;
		private static String ciforMapResultsMenu_Query;

		private static String ciforReportMenu;
		private static String ciforReportMenu_ClusterReport;
		private static String ciforReportMenu_FrequencyAnalysis;

		private static String ciforHelpMenu;
		private static String ciforHelpMenu_UserGuide;
		private static String ciforHelpMenu_Welcome;

		// ciforHeading = "  " + ciforTitle + "   " + ciforVersion;
	}
