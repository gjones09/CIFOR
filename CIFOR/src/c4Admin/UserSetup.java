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
 * Copyright (C) The OpenELIS Foundation. All Rights Reserved.
 * 
 */

package c4Admin;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * 
 * @author jonesg1
 */
public class UserSetup extends javax.swing.JFrame
	{

		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;

		// private static String open;
		public static Connection connection;
		private static String driver;
		private static String url;
		private static String userid;
		private static String password;
		// private boolean ALLOW_COLUMN_SELECTION = false;
		private boolean ALLOW_ROW_SELECTION = true;

		/** Creates new form UserSetup */
		public UserSetup()
			{
			getProperties();
			databaseConnection();
			initComponents();
			}

		private void initComponents()
			{

			userTableScrollPane1 = new javax.swing.JScrollPane();
			// userMasterTable = new javax.swing.JTable();
			userIDLabel = new javax.swing.JLabel();
			userNameLabel = new javax.swing.JLabel();
			passwordLabel = new javax.swing.JLabel();
			userRoleLabel = new javax.swing.JLabel();
			accountActiveLabel = new javax.swing.JLabel();
			addButton = new javax.swing.JButton();
			updateButton = new javax.swing.JButton();
			deleteButton = new javax.swing.JButton();
			exitButton = new javax.swing.JButton();
			titleLabel = new javax.swing.JLabel();
			userIDText = new javax.swing.JTextField();
			userNameText = new javax.swing.JTextField();
			userRoleText = new javax.swing.JTextField();
			userActiveText = new javax.swing.JTextField();
			userPasswordText = new javax.swing.JPasswordField();

			FormListener formListener = new FormListener();

// ***************************************************************************************
// ** Setup Row and Column Vectors for the database
// ***************************************************************************************

			Vector<String> Columns = new Vector<String>();
			Vector<Vector<Object>> Rows = new Vector<Vector<Object>>();

// ***************************************************************************************
// ** Create the String component for the database Query
// ***************************************************************************************

			ResultSet rs = null;
			PreparedStatement pstmt = null;

			String userQuery = "SELECT  USER_ID, USER_NAME, USER_PASSWORD, USER_ROLE, USER_ACTIVE " +
			// "SELECT  USER_ID, USER_NAME, USER_ROLE, USER_ACTIVE " +
					"FROM CIFOR_USER " + "ORDER BY USER_ID";

			try
				{
					pstmt = connection.prepareStatement(userQuery);

					rs = pstmt.executeQuery(); // execute the query

					ResultSetMetaData md = rs.getMetaData();
					// int columns = md.getColumnCount();

// ***************************************************************************************
// ** Setup the columns for the GUI and the Report
// ***************************************************************************************

					int cols = md.getColumnCount();
					Columns.clear(); // clear unwanted value if exist any in Columns variable.

					String[] stringcolumnNames = // add the column names to the header
					{ "User ID", "User Name", "Password", "Role", "Active" };

					for (int i = 0; i < stringcolumnNames.length; i++)
						Columns.addElement((String) stringcolumnNames[i]);

// ***************************************************************************************
// ** Fill the vectors with data from the query
// ***************************************************************************************

					Rows.clear(); // clear unwanted value if exist any in Rows variable.

					while (rs.next())
						{
							Vector<Object> row = new Vector<Object>(cols);
							for (int i = 1; i <= cols; i++)
								{
									row.addElement(rs.getObject(i));
									// System.out.println("Row1 = " + row);
								}
							Rows.addElement(row);
							// System.out.println("Row2 = " + row);
						}

					rs.close(); // close the Resultset.
					pstmt.close(); // close the PreparedStatement.

// ***************************************************************************************
// ** catch block for the DB read
// ***************************************************************************************
				}
			catch (SQLException e)
				{
					System.out.println("SQL Error on the Date Fields");
					e.printStackTrace();
				}

			final TableModel userModel = new DefaultTableModel(Rows, Columns)
				{

					/**
			 * 
			 */
					private static final long serialVersionUID = 1L;
				};

// ***************************************************************************************
// ** Create and set attributes for the JTable and clusterTable
// ***************************************************************************************
			setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
			setTitle("CIFOR     User Setup");
			setBounds(new java.awt.Rectangle(200, 100, 0, 0));

			final JTable userMasterTable = new JTable(userModel);
			userMasterTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
			// clusterTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
			// userMasterTable.setColumnSelectionAllowed(true);
			userMasterTable.setRowSelectionAllowed(true);
			// userMasterTable.setAutoCreateRowSorter(true);
			userMasterTable.setFillsViewportHeight(true);
			// clusterTable.setPreferredSize(new java.awt.Dimension(1550, 850));
			userMasterTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
			userMasterTable.setRowHeight(24);
			// clusterTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			userMasterTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// ***************************************************************************************
// ** Define the table's column widths
// ***************************************************************************************

			TableColumn column = null;
			for (int i = 0; i < 5; i++)
				{
					column = userMasterTable.getColumnModel().getColumn(i);
					if (i == 0)
						{
							column.setPreferredWidth(120);
						} // User ID
					else
						if (i == 1)
							{
								column.setPreferredWidth(200);
							} // User Name
						else
							if (i == 2)
								{
									column.setPreferredWidth(200);
								} // Password
							else
								if (i == 3)
									{
										column.setPreferredWidth(55);
									} // Role
								else
									if (i == 4)
										{
											column.setPreferredWidth(45);
										} // Active
				}

// **********************************************************************************

// ***************************************************************************************

			userMasterTable.setRowSelectionAllowed(true);
			userTableScrollPane1.setViewportView(userMasterTable);
			// userMasterTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			// userMasterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			userMasterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			if (ALLOW_ROW_SELECTION)
				{ // true by default
					ListSelectionModel rowSM = userMasterTable.getSelectionModel();
					rowSM.addListSelectionListener(new ListSelectionListener()
						{
							public void valueChanged(ListSelectionEvent e)
								{
								// Ignore extra messages.
								if (e.getValueIsAdjusting()) return;

								ListSelectionModel lsm = (ListSelectionModel) e.getSource();
								if (lsm.isSelectionEmpty())
									{
										// System.out.println("No rows are selected.");
									}
								else
									{
										int selectedRow = lsm.getMinSelectionIndex();
										// System.out.println("Row " + selectedRow + " is now selected.");

										int numCols = userMasterTable.getColumnCount();
										javax.swing.table.TableModel model = userMasterTable.getModel();

										for (int j = 0; j < numCols; j++)
											{
												if (j == 0)
													{
														userIDString = (String) model.getValueAt(selectedRow, j);
														userIDText.setText(userIDString);
													}
												else
													if (j == 1)
														{
															userNameString = (String) model.getValueAt(selectedRow, j);
															userNameText.setText(userNameString);
														}
													else
														if (j == 2)
															{
																userPasswordString = (String) model
																		.getValueAt(selectedRow, j);
																userPasswordText.setText(userPasswordString);
															}
														else
															if (j == 3)
																{
																	userRoleString = (String) model
																			.getValueAt(selectedRow, j);
																	userRoleText.setText(userRoleString);
																}
															else
																if (j == 4)
																	{
																		userActiveString = (String) model
																				.getValueAt(selectedRow, j);
																		userActiveText.setText(userActiveString);
																	}

												// System.out.print("  " + model.getValueAt(selectedRow, j));
											}
									}
								}
						});
				}
			else
				{
					userMasterTable.setRowSelectionAllowed(false);
				}

			userTableScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			userTableScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

			userIDLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
			userIDLabel.setText("User ID:");

			userNameLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
			userNameLabel.setText("User Name:");

			passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
			passwordLabel.setText("Password:");

			userRoleLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
			userRoleLabel.setText("User Role:");

			accountActiveLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
			accountActiveLabel.setText("Account Active:");

			addButton.setText("Add");
			addButton.addActionListener(formListener);

			updateButton.setText("Update");
			updateButton.addActionListener(formListener);

			deleteButton.setText("Delete");
			deleteButton.addActionListener(formListener);

			exitButton.setText("Exit");
			exitButton.addActionListener(formListener);

			titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
			titleLabel.setText("User Setup");

			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(	layout.createSequentialGroup()
										.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
															.addGroup(	layout.createSequentialGroup()
																				.addContainerGap()
																				.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
																									.addGroup(	layout.createSequentialGroup()
																														.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
																																			.addGroup(	layout.createSequentialGroup()
																																								.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.TRAILING)
																																													.addComponent(	userIDLabel)
																																													.addComponent(	userNameLabel))
																																								.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																								.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
																																													.addComponent(	userIDText,
																																																	javax.swing.GroupLayout.PREFERRED_SIZE,
																																																	124,
																																																	javax.swing.GroupLayout.PREFERRED_SIZE)
																																													.addComponent(	userRoleText,
																																																	javax.swing.GroupLayout.PREFERRED_SIZE,
																																																	170,
																																																	javax.swing.GroupLayout.PREFERRED_SIZE)))
																																			.addGroup(	layout.createSequentialGroup()
																																								.addGap(8,
																																										8,
																																										8)
																																								.addComponent(	userRoleLabel)
																																								.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																								.addComponent(	userNameText,
																																												javax.swing.GroupLayout.PREFERRED_SIZE,
																																												77,
																																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																														.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
																																			.addGroup(	layout.createSequentialGroup()
																																								.addGap(63,
																																										63,
																																										63)
																																								.addComponent(	passwordLabel)
																																								.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																								.addComponent(	userPasswordText,
																																												javax.swing.GroupLayout.PREFERRED_SIZE,
																																												165,
																																												javax.swing.GroupLayout.PREFERRED_SIZE))
																																			.addGroup(	layout.createSequentialGroup()
																																								.addGap(28,
																																										28,
																																										28)
																																								.addComponent(	accountActiveLabel)
																																								.addPreferredGap(	javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																								.addComponent(	userActiveText,
																																												javax.swing.GroupLayout.PREFERRED_SIZE,
																																												72,
																																												javax.swing.GroupLayout.PREFERRED_SIZE))))
																									.addComponent(	userTableScrollPane1,
																													javax.swing.GroupLayout.DEFAULT_SIZE,
																													669,
																													Short.MAX_VALUE)))
															.addGroup(	layout.createSequentialGroup()
																				.addGap(266, 266, 266)
																				.addComponent(titleLabel))
															.addGroup(	layout.createSequentialGroup()
																				.addGap(130, 130, 130)
																				.addComponent(addButton)
																				.addGap(27, 27, 27)
																				.addComponent(updateButton)
																				.addGap(18, 18, 18)
																				.addComponent(deleteButton)
																				.addGap(26, 26, 26)
																				.addComponent(exitButton)))
										.addContainerGap()));

			layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { addButton, deleteButton,
					exitButton, updateButton });

			layout.setVerticalGroup(layout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(	layout.createSequentialGroup()
										.addContainerGap()
										.addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
														javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(userTableScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
														100, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(24, 24, 24)
										.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
															.addComponent(userIDLabel)
															.addComponent(userIDText,
																			javax.swing.GroupLayout.PREFERRED_SIZE,
																			javax.swing.GroupLayout.DEFAULT_SIZE,
																			javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
															.addComponent(userNameLabel)
															.addComponent(userRoleText,
																			javax.swing.GroupLayout.PREFERRED_SIZE,
																			javax.swing.GroupLayout.DEFAULT_SIZE,
																			javax.swing.GroupLayout.PREFERRED_SIZE)
															.addComponent(passwordLabel)
															.addComponent(userPasswordText,
																			javax.swing.GroupLayout.PREFERRED_SIZE,
																			javax.swing.GroupLayout.DEFAULT_SIZE,
																			javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING)
															.addComponent(userRoleLabel)
															.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(	userNameText,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(accountActiveLabel)
																				.addComponent(	userActiveText,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29,
															Short.MAX_VALUE)
										.addGroup(	layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.BASELINE)
															.addComponent(addButton).addComponent(updateButton)
															.addComponent(deleteButton).addComponent(exitButton))
										.addGap(23, 23, 23)));

			layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] { addButton, deleteButton,
					exitButton, updateButton });

			pack();

			}

		public void addRow()
			{
			// Append a row
			// //userModel.addRow(new Object[]{"v1", "v2"});
			// there are now 2 rows with 2 columns

			// userModel.addText(textField.getText());
			// ((DefaultTableModel) userModel).addRow(new Object[]{"v1", "v2"});
			}

		// Code for dispatching events from components to event handlers.

		private class FormListener implements java.awt.event.ActionListener
			{
				FormListener()
					{
					}

				public void actionPerformed(java.awt.event.ActionEvent evt)
					{
					if (evt.getSource() == exitButton)
						{
							UserSetup.this.exitButtonActionPerformed(evt);
						}
					else
						if (evt.getSource() == deleteButton)
							{
								UserSetup.this.deleteButtonActionPerformed(evt);
							}
						else
							if (evt.getSource() == updateButton)
								{
									UserSetup.this.updateButtonActionPerformed(evt);
								}
							else
								if (evt.getSource() == addButton)
									{
										UserSetup.this.addButtonActionPerformed(evt);
									}
					}
			}// </editor-fold>

		private void exitButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
			// System.out.println("Exit Button");
			dispose();
			}

		private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
			System.out.println("Delete Button");
			}

		private void updateButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
			System.out.println("Update Button");
			}

		private void addButtonActionPerformed(java.awt.event.ActionEvent evt)
			{
			System.out.println("Add Button");
			addRow();

			}

// *******************************************************************************
// Read Database.properties file to get properties info for the Database
// *******************************************************************************
		public static void getProperties()
			{
			String language;
			String country;

			language = new String("en");
			country = new String("US");

			Locale currentLocale;
			ResourceBundle messages;

			currentLocale = new Locale(language, country);

			messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

			driver = messages.getString("DB_driver");
			url = messages.getString("DB_url");
			userid = messages.getString("DB_userid");
			password = messages.getString("DB_password");

			}

			{

			}

// ***************************************************************************************
// ** Establish a connection to the database
// ***************************************************************************************
		public static void databaseConnection()
			{
			getProperties();
			try
				{
					DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

					connection = DriverManager.getConnection(url, userid, password);

					// System.out.println("C4Oracle_DBUpdate *** Database Connected ***");
				}
			catch (SQLException e)
				{
					// System.out.println("C4Oracle_DBUpdate *** Could not Open the Database ***");
					// e.printStackTrace();
				}
			}

// ***************************************************************************************
// ** Close the database connection
// ***************************************************************************************
		public static void databaseClose()
			{
			try
				{
					// connection.commit();
					connection.close();
					// System.out.println("C4Oracle_DBUpdate *** Database Closed ***");
				}
			catch (SQLException e)
				{
					// System.out.println("C4Oracle_DBUpdate *** Could not Close the Database ***");
					e.printStackTrace();
				}

			// System.out.println("*** Database Closed ***");
			}

// ***************************************************************************************
// ** main Section
// ***************************************************************************************

		public static void main(String args[])
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

							frame = new UserSetup();

							frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							frame.setVisible(true);
							databaseClose();
							}
					});
			}

		// ////////////////////////////////////
		/*
		 * java.awt.EventQueue.invokeLater(new Runnable() {
		 * 
		 * public void run() {
		 * new UserSetup().setVisible(true);
		 * databaseClose();
		 * }
		 * });
		 * 
		 * }
		 */
// ***************************************************************************************
// ** Variables declaration
// ***************************************************************************************

		public static JFrame frame;
		private javax.swing.JLabel accountActiveLabel;
		private javax.swing.JButton addButton;
		private javax.swing.JButton deleteButton;
		private javax.swing.JButton exitButton;
		private javax.swing.JPasswordField userPasswordText;
		private javax.swing.JTextField userIDText;
		private javax.swing.JTextField userNameText;
		private javax.swing.JTextField userRoleText;
		private javax.swing.JTextField userActiveText;
		private String userIDString;
		private String userNameString;
		private String userPasswordString;
		private String userRoleString;
		private String userActiveString;
		private javax.swing.JLabel passwordLabel;
		private javax.swing.JLabel titleLabel;
		private javax.swing.JButton updateButton;
		private javax.swing.JLabel userIDLabel;
		// private javax.swing.JTable userMasterTable;
		private javax.swing.JLabel userNameLabel;
		private javax.swing.JLabel userRoleLabel;
		private javax.swing.JScrollPane userTableScrollPane1;
		// End of variables declaration
	}
