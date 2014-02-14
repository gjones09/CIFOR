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
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
/**
*
* @author jonesg1
*/

public class DetailReportParameters extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public		static	String		ckDateString;
	
	public DetailReportParameters() {
         
    	c4Utilities.DateFormatUtility.CalculateDate();		//* Run date utility 
        
    	processSection();							//* run the processSection
    }
    
	/*
	 *  Processing section of the program
	 */
    private void processSection() {

        labelCurrentDate 	= new javax.swing.JLabel();
        labelBeginningDate 	= new javax.swing.JLabel();
        labelOrganism		= new javax.swing.JLabel();
        labelPrimaryEnzyme	= new javax.swing.JLabel();
        labelSecondaryEnzyme = new javax.swing.JLabel();
        labelOtherResult	= new javax.swing.JLabel();
        okButton 			= new javax.swing.JButton();
        cancelButton 		= new javax.swing.JButton();
        helpButton 			= new javax.swing.JButton();
        labelHeader 		= new javax.swing.JLabel();
        inputCurrentDate 	= new javax.swing.JTextField();
        inputBeginningDate 	= new javax.swing.JTextField();
        inputOrganism		= new javax.swing.JTextField();
        inputPrimaryEnzyme	= new javax.swing.JTextField();
        inputSecondaryEnzyme = new javax.swing.JTextField();
        inputOtherResult	= new javax.swing.JTextField();

        FormListener formListener = new FormListener();
        KeyListener keyListener = null;

        setTitle(c4Utilities.CIFORProperties.qParameterTitle);
        setLocation(300,200);

        labelCurrentDate.setText(c4Utilities.CIFORProperties.currentDate);
        labelBeginningDate.setText(c4Utilities.CIFORProperties.beginningDate);
        labelOrganism.setText(c4Utilities.CIFORProperties.qParameterOrganism);
        labelPrimaryEnzyme.setText(c4Utilities.CIFORProperties.qParameterPrimaryEnzyme);
        labelSecondaryEnzyme.setText(c4Utilities.CIFORProperties.qParameterSecondaryEnzyme);
        labelOtherResult.setText(c4Utilities.CIFORProperties.qParameterOtherResult);

        okButton.setText(c4Utilities.CIFORProperties.okButton);
        okButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        okButton.setSelected(true);
        okButton.requestFocus(true);
        okButton.addActionListener(formListener);
        okButton.addKeyListener(keyListener);

        cancelButton.setText(c4Utilities.CIFORProperties.cancelButton);
        cancelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cancelButton.addActionListener(formListener);

        helpButton.setText(c4Utilities.CIFORProperties.helpButton);
        helpButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        helpButton.addActionListener(formListener);

        labelHeader.setFont(new java.awt.Font("Tahoma", 1, 14));
        labelHeader.setText(c4Utilities.CIFORProperties.qParameterHeading);

        inputCurrentDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputCurrentDate.setText(c4Utilities.DateFormatUtility.currDate);

        inputBeginningDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputBeginningDate.setText(c4Utilities.DateFormatUtility.beginDate);
        
        inputOrganism.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        inputPrimaryEnzyme.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        inputSecondaryEnzyme.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        inputOtherResult.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        okButton.registerKeyboardAction(okButton.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                //JComponent.WHEN_FOCUSED);
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        okButton.registerKeyboardAction(okButton.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                //JComponent.WHEN_FOCUSED);
                JComponent.WHEN_IN_FOCUSED_WINDOW);

		/*
		 *  Setup the GUI section of the program (from NetBeans GUI builder)
		 */           
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(labelHeader))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelBeginningDate)
                            .addComponent(labelCurrentDate)
                            .addComponent(labelOrganism)
                            .addComponent(labelPrimaryEnzyme)
                            .addComponent(labelSecondaryEnzyme)
                            .addComponent(labelOtherResult))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputCurrentDate)
                            .addComponent(inputBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputOrganism, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(inputPrimaryEnzyme)
                            .addComponent(inputSecondaryEnzyme)
                            .addComponent(inputOtherResult)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {inputBeginningDate, inputCurrentDate});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, helpButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(labelHeader)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCurrentDate)
                    .addComponent(inputCurrentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBeginningDate)
                    .addComponent(inputBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrganism)
                    .addComponent(inputOrganism, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimaryEnzyme)
                    .addComponent(inputPrimaryEnzyme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSecondaryEnzyme)
                    .addComponent(inputSecondaryEnzyme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOtherResult)
                    .addComponent(inputOtherResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {inputBeginningDate, inputCurrentDate});

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
                DetailReportParameters.this.okButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cancelButton) {
                DetailReportParameters.this.cancelButtonActionPerformed(evt);
            }
            else if (evt.getSource() == helpButton) {
                DetailReportParameters.this.helpButtonActionPerformed(evt);
            }
        }
    }                       
	
    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
                 JOptionPane.showMessageDialog(controllingFrame,
            		"\n" +
                	"Enter the dates in MM/DD/YYYY format.\n"+
	         		"\n" +
	        		"Use  *  as the Wildcard to select an Organism,\n" +
	        		"Primary Enzyme, Secondary Enzyme, or Other Result.\n" +
	        		" \n" +
	        		"eg;  *O157*  or  Listeria*  or  *coli\n" +
	        		" \n" +
	        		"Leave the field blank to get all results." +
	        		" \n" + " \n");
    }                                          

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        dispose();
    }                                            

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
           	
		stringOrganism			= inputOrganism.getText();					// get the organism (with wildcards)
		stringOrganism 			= stringOrganism.replace('*', '%');			// substitute the * wildcard with % for SQL LIKE statement
		
		stringPrimaryEnzyme		= inputPrimaryEnzyme.getText();				// get the PrimaryEnzyme (with wildcards)
		stringPrimaryEnzyme 	= stringPrimaryEnzyme.replace('*', '%');	// substitute the * wildcard with % for SQL LIKE statement
		
		stringSecondaryEnzyme	= inputSecondaryEnzyme.getText();			// get the SecondaryEnzyme (with wildcards)
		stringSecondaryEnzyme 	= stringSecondaryEnzyme.replace('*', '%');	// substitute the * wildcard with % for SQL LIKE statement

		stringOtherResult		= inputOtherResult.getText();				// get the OtherResult (with wildcards)
		stringOtherResult 		= stringOtherResult.replace('*', '%');		// substitute the * wildcard with % for SQL LIKE statement
    	    	
		/*
		 *  Check the input string dates for valid date and format
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
	
		if (c4Utilities.DateValidator.invalidDate != "Y")
		{
	        try {
	        	DetailReport.main(null);
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
            	UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
            	DetailReportParameters frame = new DetailReportParameters();
                //new ClusterReportParameters().setVisible(true);
            	frame.setVisible(true);
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });
    }
    
		/*
		 *  Variables declaration
		 */
	    private JFrame controllingFrame;
	    
	    private javax.swing.JLabel labelHeader;
	    private javax.swing.JLabel labelCurrentDate;
	    private javax.swing.JLabel labelBeginningDate;
	    private javax.swing.JLabel labelOrganism;
	    private javax.swing.JLabel labelPrimaryEnzyme;
	    private javax.swing.JLabel labelSecondaryEnzyme;
	    private javax.swing.JLabel labelOtherResult;
	    
	    public javax.swing.JTextField inputCurrentDate;
	    public javax.swing.JTextField inputBeginningDate;
	    public javax.swing.JTextField inputOrganism;
	    public javax.swing.JTextField inputPrimaryEnzyme;
	    public javax.swing.JTextField inputSecondaryEnzyme;
	    public javax.swing.JTextField inputOtherResult;
	    
	    public static String stringCurrentDate;
	    public static String stringBeginningDate;
	    public static String stringOrganism;
	    public static String stringPrimaryEnzyme;
	    public static String stringSecondaryEnzyme;
	    public static String stringOtherResult;
		                    
	    private javax.swing.JButton cancelButton;
	    static javax.swing.JCheckBox ckboxAllReports;
	    private javax.swing.JButton helpButton;
	    private javax.swing.JButton okButton;
}