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

import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * 
 * @author jonesg1
 */

public class ClusterReportParameters extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    	public		static	String		ckDateString;
    
	/*
	 *  Initiate Processing
	 */	
	public ClusterReportParameters() {
         
    	c4Utilities.DateFormatUtility.CalculateDate();		//* Run date utility 
        
    	processSection();									//* run the processSection
    }

	/*
	 *  Processing section of the program
	 */
    private void processSection() {

        labelCurrentDate 	= new javax.swing.JLabel();
        labelBeginningDate 	= new javax.swing.JLabel();
        ckboxAllReports 	= new javax.swing.JCheckBox();
        ckboxAllReports.setSelected(true);
        okButton 			= new javax.swing.JButton();
        cancelButton 		= new javax.swing.JButton();
        helpButton 			= new javax.swing.JButton();
        headerLabel 		= new javax.swing.JLabel();
        inputCurrentDate 	= new javax.swing.JTextField();
        inputBeginningDate 	= new javax.swing.JTextField();

        FormListener formListener = new FormListener();
        
        setTitle(c4Utilities.CIFORProperties.cParameterTitle);
        setLocation(300,200);

        labelCurrentDate.setText(c4Utilities.CIFORProperties.currentDate);

        labelBeginningDate.setText(c4Utilities.CIFORProperties.beginningDate);

        ckboxAllReports.setText(c4Utilities.CIFORProperties.cParameterSelect);

        okButton.setText(c4Utilities.CIFORProperties.okButton);
        okButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        okButton.addActionListener(formListener);

        cancelButton.setText(c4Utilities.CIFORProperties.cancelButton);
        cancelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cancelButton.addActionListener(formListener);

        helpButton.setText(c4Utilities.CIFORProperties.helpButton);
        helpButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        helpButton.addActionListener(formListener);

        headerLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        headerLabel.setText(c4Utilities.CIFORProperties.cParameterHeading);

        inputCurrentDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputCurrentDate.setText(c4Utilities.DateFormatUtility.currDate);

        inputBeginningDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputBeginningDate.setText(c4Utilities.DateFormatUtility.beginDate);
        
		okButton.registerKeyboardAction(okButton.getActionForKeyStroke(
			KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
			KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
			JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		okButton.registerKeyboardAction(okButton.getActionForKeyStroke(
			KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
			KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
			JComponent.WHEN_IN_FOCUSED_WINDOW);

		/*
		 *  Setup the GUI section of the program (from NetBeans GUI builder
		 */        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(headerLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelBeginningDate)
                                    .addComponent(labelCurrentDate))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(inputCurrentDate)
                                    .addComponent(inputBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(ckboxAllReports)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {inputCurrentDate, inputBeginningDate});
        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, helpButton, okButton});
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(headerLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCurrentDate)
                    .addComponent(inputCurrentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBeginningDate)
                    .addComponent(inputBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ckboxAllReports)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {inputCurrentDate, inputBeginningDate});
        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelButton, helpButton, okButton});
        pack();
    }

	/*
	 *  Code for dispatching events from components to event handlers.
	 */
    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == okButton) {
                ClusterReportParameters.this.okButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cancelButton) {
                ClusterReportParameters.this.cancelButtonActionPerformed(evt);
            }
            else if (evt.getSource() == helpButton) {
                ClusterReportParameters.this.helpButtonActionPerformed(evt);
            }
        }
    }                       

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
                 JOptionPane.showMessageDialog(controllingFrame,
    		"Enter the dates in MM/DD/YYYY format.\n"+
    		"Select the checkbox to show only items\n" +
    		"with reports on the Beginning Date.");
    }                                          

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        dispose();
    }                                            

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        vAllReports = new JTextField(c4Utilities.CIFORProperties.cParameterAll);
        vTodaysReports = new JTextField(c4Utilities.CIFORProperties.cParameterToday);
        if (ckboxAllReports.isSelected()) 
            {
                vReportSelection = vTodaysReports;
            }
        else
            {
                vReportSelection = vAllReports;
            }
    	
		/*
		 *  Convert input string date to a Date format for the Cluster Report sql statement
		 */
		stringCurrentDate 	= inputCurrentDate.getText();
		c4Utilities.DateValidator.ckDt	= stringCurrentDate;
		c4Utilities.DateValidator.validateInputDate();
		if (c4Utilities.DateValidator.invalidDate == "Y")
		{
			JOptionPane.showMessageDialog(controllingFrame,
					"Invalid Current Date.\n" +
					"Please enter a valid date.\n" +
					"MM/DD/YYYY \n" +
					" " +
					"Thank You");
		}
		

		if (c4Utilities.DateValidator.invalidDate != "Y")
		{
			stringBeginningDate	= inputBeginningDate.getText();
			c4Utilities.DateValidator.ckDt	= stringBeginningDate;
			c4Utilities.DateValidator.validateInputDate();
			
			if (c4Utilities.DateValidator.invalidDate == "Y")
			{
				JOptionPane.showMessageDialog(controllingFrame,
						"Invalid Beginning Date.\n" +
						"Please enter a valid date.\n" +
						"MM/DD/YYYY \n" +
						" " +
						"Thank You");
			}
		}	

		/*
		 *  Call the Cluster Report program
		 */
		if (c4Utilities.DateValidator.invalidDate != "Y")
		{
	        try {
	        	ClusterReport.main(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        dispose();
	    }
    }

	/*
	 *  Main section
	 */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
            	ClusterReportParameters frame = new ClusterReportParameters();
            	frame.setVisible(true);
            	frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });
    }
    
		/*
		 *  Variables declaration
		 */
	    public JFrame controllingFrame;
	    
	    private javax.swing.JLabel headerLabel;
	    private javax.swing.JLabel labelCurrentDate;
	    private javax.swing.JLabel labelBeginningDate;
	    
	    public javax.swing.JTextField inputCurrentDate;
	    public javax.swing.JTextField inputBeginningDate;
	    
	    public static String stringCurrentDate;
	    public static String stringBeginningDate;
		
	    public static JTextField vReportSelection;
	    public static JTextField vAllReports;
	    public static JTextField vTodaysReports;
                    
	    private javax.swing.JButton cancelButton;
	    static javax.swing.JCheckBox ckboxAllReports;
	    private javax.swing.JButton helpButton;
	    private javax.swing.JButton okButton;           
}