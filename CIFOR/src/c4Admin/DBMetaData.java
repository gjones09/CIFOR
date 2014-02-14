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

package c4Admin;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author jonesg1
 */
public class DBMetaData extends javax.swing.JFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
		private JFrame controllingFrame;

    /**
     * Creates new form CiforDBMetaData
     */
    
    public DBMetaData() {
        initComponents();
    }

    private void initComponents() {

        headerLabel 	= new javax.swing.JLabel();
        dbVendorLabel 	= new javax.swing.JLabel();
        dbVersionLabel 	= new javax.swing.JLabel();
        dbDriverLabel 	= new javax.swing.JLabel();
        dbUsernameLabel = new javax.swing.JLabel();
        dbVendorField 	= new javax.swing.JTextField();
        dbVersionField 	= new javax.swing.JTextField();
        dbDriverField 	= new javax.swing.JTextField();
        dbUsernameField = new javax.swing.JTextField();
        dbSchemaLabel 	= new javax.swing.JLabel();
        totalRecordsField = new javax.swing.JTextField();
        totalRecordsLabel = new javax.swing.JLabel();
        closeButton 	= new javax.swing.JButton();
        dbURLLabel 		= new javax.swing.JLabel();
        dbURLField 		= new javax.swing.JTextField();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CIFOR - Database Details");
        setBounds(new java.awt.Rectangle(200, 100, 0, 0));

        headerLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        headerLabel.setText("CIFOR2 Database Details");

        dbVendorLabel.setText("Database Vendor: ");

        dbVersionLabel.setText("Database Version: ");

        dbDriverLabel.setText("Driver Name: ");

        dbUsernameLabel.setText("Username: ");

        dbVendorField.setText(" ");

        dbSchemaLabel.setText("Schema Name: ");
        
        totalRecordsLabel.setText("Total Result Records: ");
        
//***************************************************************************************
//** Establish a connection to the database
//***************************************************************************************
        	
      	c4DataBase.DBConnection.DataBaseConnection();

//***************************************************************************************
//** Get DB Metadata
//***************************************************************************************
  		
        DatabaseMetaData md = null;

        try 
        {
            md = c4DataBase.DBConnection.connection.getMetaData();					// Obtain a MetaData object
  		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(controllingFrame,
					"There is an error with the\n" +
					"Database Connection.  Please\n" +
					"Contact your database administrator." +
					"Thank You");
			e.printStackTrace();
		}
		
//***************************************************************************************
//** Get Metadata details
//***************************************************************************************
	
        try {					
            dbVendorField.setText(md.getDatabaseProductName());		// Get DB Product Name
            dbVersionField.setText(md.getDatabaseProductVersion());	// Get DB Version Number
            dbDriverField.setText(md.getDriverName());				// Get DB Driver Name
            dbUsernameField.setText(md.getUserName());				// Get DB Username
            dbURLField.setText(md.getURL());						// Get DB URL
            
        } catch (SQLException e) {
        	//System.out.println("java.sql.SQLException 1: Unsupported feature");
        }
        
//***************************************************************************************
//** Read the Results table and get the row count       
//***************************************************************************************
        
        try {
			readDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
//***************************************************************************************
//**  Screen layout (from Matisse)
//***************************************************************************************

        closeButton.setText("Close");
        closeButton.addActionListener(formListener);

        dbURLLabel.setText("Database URL: ");
   
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dbVendorLabel)
                            .addComponent(dbVersionLabel)
                            .addComponent(dbURLLabel)
                            .addComponent(dbDriverLabel)
                            .addComponent(dbUsernameLabel)
                            .addComponent(totalRecordsLabel))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dbVendorField, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbURLField, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                            .addComponent(dbUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalRecordsField, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbDriverField, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbVersionField)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(539, 539, 539)
                        .addComponent(closeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addComponent(headerLabel)))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbVendorLabel)
                    .addComponent(dbVendorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbVersionLabel)
                    .addComponent(dbVersionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbURLLabel)
                    .addComponent(dbURLField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbDriverLabel)
                    .addComponent(dbDriverField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbUsernameLabel)
                    .addComponent(dbUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalRecordsLabel)
                    .addComponent(totalRecordsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(closeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }        

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == closeButton) {
                DBMetaData.this.closeButtonActionPerformed(evt);
            }
        }
    }

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

//***************************************************************************************
//** Read Tables and get record counts
//***************************************************************************************
    private static void readDB() throws SQLException
    {
    	// Query for the RESULT Table
	    ResultSet resultRS = null;
	    PreparedStatement resultPstmt = null;    
		String resultQuery = 	
			"SELECT  ID FROM CIFOR_LAB_RESULT ";		
		resultPstmt = c4DataBase.DBConnection.connection.prepareStatement(resultQuery); 	// create a statement
		resultRS = resultPstmt.executeQuery();												// execute the query
        while( resultRS.next())
        {
        	resultCounter ++;																// count the rows
        }
        String results = Integer.toString(resultCounter);
        totalRecordsField.setText(results);
        resultRS.close(); 																	// close the Resultset.
        resultPstmt.close(); 																// close the PreparedStatement.
    }
//***************************************************************************************    
//** main section
//***************************************************************************************
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	UIManager.put("swing.Windows", Boolean.TRUE);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }
                new DBMetaData().setVisible(true);
            }
        });
    }

//***************************************************************************************    
//** Variables section
//***************************************************************************************
    private javax.swing.JButton 	closeButton;
    private javax.swing.JTextField 	dbDriverField;
    private javax.swing.JLabel 		dbDriverLabel;
    private javax.swing.JLabel 		dbSchemaLabel;
    private javax.swing.JTextField 	dbURLField;
    private javax.swing.JLabel 		dbURLLabel;
    private javax.swing.JTextField 	dbUsernameField;
    private javax.swing.JLabel 		dbUsernameLabel;
    private javax.swing.JTextField 	dbVendorField;
    private javax.swing.JLabel 		dbVendorLabel;
    private javax.swing.JTextField 	dbVersionField;
    private javax.swing.JLabel 		dbVersionLabel;
    private static javax.swing.JTextField 	totalRecordsField;
    private javax.swing.JLabel 		totalRecordsLabel;
    private javax.swing.JLabel 		headerLabel;
    
    private static int resultCounter;
    
}
