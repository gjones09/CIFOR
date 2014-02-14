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

import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author jonesg1
 */

    public class UserLogin extends javax.swing.JFrame {

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public UserLogin() {
            initComponents();
        }

//***************************************************************************************
//** Initialize all the components
//***************************************************************************************
        private void initComponents() {

            userIDLabel = new javax.swing.JLabel();
            passwordLabel = new javax.swing.JLabel();
            LoginTitle = new javax.swing.JLabel();
            userIDText = new javax.swing.JTextField();
            passwordText = new javax.swing.JPasswordField();
            okButton = new javax.swing.JButton();
            cancelButton = new javax.swing.JButton();
            helpButton = new javax.swing.JButton();

            FormListener formListener = new FormListener();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("CIFOR   User Login");
            setLocation(300,200);

            userIDLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            userIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            userIDLabel.setText("User ID: ");
            userIDLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

            passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            passwordLabel.setText("Password: ");
            passwordLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

            LoginTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            LoginTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            LoginTitle.setText("User Login");

            okButton.setText("OK");
            okButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
            okButton.addActionListener(formListener);

            cancelButton.setText("Cancel");
            cancelButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
            cancelButton.addActionListener(formListener);

            helpButton.setText("Help");
            helpButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
            helpButton.addActionListener(formListener);

//***************************************************************************************
//** Setup the OK button to respond to the ENTER key.
//***************************************************************************************
            
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
            
//***************************************************************************************
//** Setup the GUI section of the program (from NetBeans GUI builder
//***************************************************************************************
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(helpButton, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addComponent(userIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(passwordText, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                .addComponent(userIDText)
                                .addComponent(LoginTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(45, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(LoginTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(userIDText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(userIDLabel))
                    .addGap(8, 8, 8)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(24, Short.MAX_VALUE))
            );

            pack();
        }

//***************************************************************************************
//** Code for dispatching events from components to event handlers.
//***************************************************************************************
        private class FormListener implements java.awt.event.ActionListener {
            FormListener() {}
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (evt.getSource() == okButton) {
                    UserLogin.this.okButtonActionPerformed(evt);
                }
                else if (evt.getSource() == cancelButton) {
                    UserLogin.this.cancelButtonActionPerformed(evt);
                }
                else if (evt.getSource() == helpButton) {
                    UserLogin.this.helpButtonActionPerformed(evt);
                }
            }
        }

        private void okButtonActionPerformed(java.awt.event.ActionEvent evt) 
        	{
        		isUserIDCorrect();
        	  
        		if (userIDCorrect.equals("N"))
        			{
        				JOptionPane.showMessageDialog(controllingFrame,
        					"Invalid UserID....Try Again\n" +
        					" ");
		        		frame.dispose();
		                frame = new UserLogin();
		            	frame.setVisible(true);
						userIDText.requestFocusInWindow();
					}
        		else
        			{
        				char[] input = passwordText.getPassword();
        				if (isPasswordCorrect(input) && userIDCorrect.equals("Y"))
        					{
		            			frame.dispose();
		            			c4Menu.MainMenu.frame.setEnabled(true);
		            			c4Menu.MainMenu.frame.hasFocus();
		            			c4Menu.MainMenu.frame.toFront();
        					}
	            		else
	            		{
	            			JOptionPane.showMessageDialog(controllingFrame,
	            					"Invalid Password....Try Again\n" +
	            					" ");
	            			frame.dispose();
	                        frame = new UserLogin();
	                    	frame.setVisible(true);
	            		}
        			}
	        		
        		/*        		if (isPasswordCorrect(input) && userIDCorrect.equals("Y"))
        		{
        			frame.dispose();
        			c4Menu.MainMenu.frame.setEnabled(true);
        			c4Menu.MainMenu.frame.hasFocus();
        			c4Menu.MainMenu.frame.toFront();
        		}
       		else
        		{
        			JOptionPane.showMessageDialog(controllingFrame,
        					"Invalid Password....Try Again\n" +
        					" ");
        			frame.dispose();
                    frame = new UserLogin();
                	frame.setVisible(true);
        		}
        		Arrays.fill(input, '0');							// Zero out the password for security
        		passwordText.selectAll();
*/
        	}

        private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        	System.exit(0);
        }

        private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {
        	JOptionPane.showMessageDialog(controllingFrame,
    				"Enter your User Name and Password.  \n" +
    				"Password must be 5-10 characters \n" +
    				"with no spaces.");
        }
   
//*******************************************************************
//* Check the passed-in array to see if it's a valid password.
//*******************************************************************    
		
        private void isUserIDCorrect()
		{        
			String uiString = userIDText.getText();
    		if (uiString.equals("CIFOR"))
    			{
    				userIDCorrect = "Y";
    			}
    		else
    			{
					//JOptionPane.showMessageDialog(controllingFrame,
        			//		"Invalid UserID....Try Again\n" +
        			//		" ");
    				userIDCorrect = "N";
    				//frame.dispose();
                    //frame = new UserLogin();
                	//frame.setVisible(true);
    				//userIDText.requestFocusInWindow();
    			}
		}
//*******************************************************************
//* Check the passed-in array to see if it's a valid password.
//*******************************************************************    

		private static boolean isPasswordCorrect(char[] input)
		{
		  	boolean isCorrect = true;
		  	char[] correctPassword = { 'C', 'I', 'F', 'O', 'R' };
  	
		  	if (input.length != correctPassword.length)
		  	{
		  		isCorrect = false;
		  	}
		  	else
		  	{
		  		isCorrect = Arrays.equals(input, correctPassword);
		  	}
  	
	  	Arrays.fill(correctPassword,'0');							// Zero out the Password
	  	return isCorrect;
		}

		protected void resetFocus()
		{
			passwordText.requestFocusInWindow();
		}

//***************************************************************************************
//** Main section
//***************************************************************************************
      
        public static void main(String args[]) {
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    //new UserLogin().setVisible(true);
                    frame = new UserLogin();
                	frame.setVisible(true);
                    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            });
        }

//***************************************************************************************
//** Variables section
//***************************************************************************************
     
        private javax.swing.JLabel LoginTitle;
        private javax.swing.JButton cancelButton;
        private javax.swing.JButton helpButton;
        private javax.swing.JButton okButton;
        private javax.swing.JLabel passwordLabel;
        private javax.swing.JPasswordField passwordText;
        private javax.swing.JLabel userIDLabel;
        private javax.swing.JTextField userIDText;
        private String userIDCorrect;
        
        public static javax.swing.JFrame frame;
        
        private JFrame controllingFrame;								// Used for dialogs
    }
