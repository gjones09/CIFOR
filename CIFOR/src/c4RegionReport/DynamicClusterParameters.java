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

package c4RegionReport;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JCheckBox;

/**
*
* @author jonesg1
*/

public class DynamicClusterParameters extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;

		public		static	String		ckDateString;
	
	public DynamicClusterParameters() {
         
    	c4Utilities.DateFormatUtility.CalculateDate();		//* Run date utility 
        
    	processSection();							//* run the processSection
    }
    
	/*
	 *  Processing section of the program
	 */
    private void processSection() {

        labelCurrentDate 	= new javax.swing.JLabel();
        labelBeginningDate 	= new javax.swing.JLabel();
        ckboxAllReports 	= new javax.swing.JCheckBox();
        ckboxAllReports.setSelected(true);
        ckboxRegion		 	= new javax.swing.JCheckBox();
        ckboxRegion.setSelected(false);
        //if(ckboxRegion.isSelected()){regionSelected="Yes";} else {regionSelected="No";}
        //

//        if (regionSelected) 
//        	{
//        		System.out.println("Check box state is selected." + regionSelected);
//        	} 
//    	else 
//    		{
//    			System.out.println("Check box state is not selected." + regionSelected);
//    		}

        ckboxSortByRegion 		= new javax.swing.JCheckBox("Sort the Report by Region (Region Selected)");
        ckboxSortByRegion.setSelected(false);
        ckboxClusterCode	 	= new javax.swing.JCheckBox("Show CDC Cluster Code");
        ckboxClusterCode.setSelected(false);
        ckboxShowOnlyPrimaryPattern = new javax.swing.JCheckBox("Show only Primary Enzyme Pattern");
        ckboxShowOnlyPrimaryPattern.setSelected(true);
            
        //System.out.println("DynamicClusterParameters 65, regionSelected=" + regionSelected);
        //regionSelected = "Yes";
        //labelRegion			= new javax.swing.JLabel();
        okButton 			= new javax.swing.JButton();
        cancelButton 		= new javax.swing.JButton();
        helpButton 			= new javax.swing.JButton();
        labelHeader 		= new javax.swing.JLabel();
        inputCurrentDate 	= new javax.swing.JTextField();
        inputBeginningDate 	= new javax.swing.JTextField();
        inputRegion			= new javax.swing.JTextField();

        FormListener formListener = new FormListener();
        KeyListener keyListener = null;

        setTitle(c4Utilities.CIFORProperties.crParameterTitle);
        setLocation(300,200);
        
        labelCurrentDate.setText(c4Utilities.CIFORProperties.currentDate);
        labelBeginningDate.setText(c4Utilities.CIFORProperties.beginningDate);

        ckboxAllReports.setText(c4Utilities.CIFORProperties.crParameterSelect);
        ckboxRegion.setText(c4Utilities.CIFORProperties.crParameterRegion 
        		+ " " + c4Utilities.UserProperties.RegionName);
        
        //labelRegion.setText(c4Utilities.CIFORProperties.crParameterRegion);

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
        labelHeader.setText(c4Utilities.CIFORProperties.crParameterHeading);

        inputCurrentDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputCurrentDate.setText(c4Utilities.DateFormatUtility.currDate);

        inputBeginningDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputBeginningDate.setText(c4Utilities.DateFormatUtility.beginDate);
        
        inputRegion.setHorizontalAlignment(javax.swing.JTextField.LEFT);

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
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(110)
        					.addComponent(labelHeader))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(49)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(labelBeginningDate)
        						.addComponent(labelCurrentDate))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(inputCurrentDate)
        						.addComponent(inputBeginningDate, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
        					.addGap(168))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(45)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(ckboxAllReports, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
        						.addComponent(ckboxRegion, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
        						.addComponent(ckboxSortByRegion)
        						.addComponent(ckboxClusterCode)
        						.addComponent(ckboxShowOnlyPrimaryPattern)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        							.addGap(18)
        							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
        							.addGap(18)
        							.addComponent(helpButton, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)))))
        			.addGap(34))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(25)
        			.addComponent(labelHeader)
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(labelCurrentDate)
        				.addComponent(inputCurrentDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(labelBeginningDate)
        				.addComponent(inputBeginningDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addComponent(ckboxAllReports)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(ckboxRegion)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(ckboxSortByRegion)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(ckboxClusterCode)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(ckboxShowOnlyPrimaryPattern)
        			.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
        			.addGroup(layout.createParallelGroup(Alignment.CENTER)
        				.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        				.addComponent(helpButton, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
        				.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        layout.linkSize(SwingConstants.VERTICAL, new Component[] {inputCurrentDate, inputBeginningDate});
        layout.linkSize(SwingConstants.VERTICAL, new Component[] {okButton, cancelButton, helpButton});
        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {inputCurrentDate, inputBeginningDate});
        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {okButton, cancelButton, helpButton});
        getContentPane().setLayout(layout);

        pack();
    
}
    
	/*
	 *  Code for dispatching events from components to event handlers.
	 */
    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == okButton) {
                DynamicClusterParameters.this.okButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cancelButton) {
                DynamicClusterParameters.this.cancelButtonActionPerformed(evt);
            }
            else if (evt.getSource() == helpButton) {
                DynamicClusterParameters.this.helpButtonActionPerformed(evt);
            }
        }
    }                       
	
    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
                 JOptionPane.showMessageDialog(controllingFrame,
            		"\n" +
                	"Enter the dates in MM/DD/YYYY format.\n"+
	         		"\n" +
	        		"Use  *  as the Wildcard to select a Region,\n" +
	        		"eg;  Northwest or *west  or  *N*\n" +
	        		"Leave the field blank to get all results." +
	        		" \n");
    }                                          

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        dispose();
    }                                            

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) { 
        vAllReports = new JTextField(c4Utilities.CIFORProperties.crParameterAll);
        vTodaysReports = new JTextField(c4Utilities.CIFORProperties.crParameterToday);
        if (ckboxAllReports.isSelected()) 
            {
                vReportSelection = vTodaysReports;
            }
        else
            {
                vReportSelection = vAllReports;
            }
           	
        // Get checkbox selection state
        //
        showAllReports = ckboxAllReports.isSelected();
        regionSelected = ckboxRegion.isSelected();
        sortByRegionSelected = ckboxSortByRegion.isSelected();
        clusterCodeSelected = ckboxClusterCode.isSelected();
        showOnlyPrimaryPatternSelected = ckboxShowOnlyPrimaryPattern.isSelected();
        
		//stringRegion			= inputRegion.getText();					// get the organism (with wildcards)
		//stringRegion 			= stringRegion.replace('*', '%');			// substitute the * wildcard with % for SQL LIKE statement
				
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
	        	//RegionClusterReport.main(null);
/////////////////////////////////////////////////////////
	        	DynamicClusterMain.main(null);
	        	//DynamicClusterMainX.main(null);
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
            	DynamicClusterParameters frame = new DynamicClusterParameters();
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
	    private javax.swing.JLabel labelRegion;
	    
	    public javax.swing.JTextField inputCurrentDate;
	    public javax.swing.JTextField inputBeginningDate;
	    public javax.swing.JTextField inputRegion;
	    
	    public static String stringCurrentDate;
	    public static String stringBeginningDate;
	    public static JTextField vReportSelection;
	    public static JTextField vAllReports;
	    public static JTextField vTodaysReports;
	    //public static String stringRegion;
		                    
	    private javax.swing.JButton cancelButton;
	    private javax.swing.JButton helpButton;
	    private javax.swing.JButton okButton;
	    static javax.swing.JCheckBox ckboxAllReports;
	    public static boolean showAllReports;
	    static javax.swing.JCheckBox ckboxRegion;
	    public static boolean regionSelected;
	    static javax.swing.JCheckBox ckboxClusterCode;
	    public static boolean clusterCodeSelected;
	    static javax.swing.JCheckBox ckboxShowOnlyPrimaryPattern;
	    public static boolean showOnlyPrimaryPatternSelected;
	    private JCheckBox ckboxSortByRegion;
	    public static boolean sortByRegionSelected;
	    
}