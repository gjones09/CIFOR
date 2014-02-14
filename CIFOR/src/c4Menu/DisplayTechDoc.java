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

package c4Menu;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
*
* @author jonesg1
*/

/*
 * This program will display a PDF of the CIFOR Technical Guide
 */
public class DisplayTechDoc extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JFrame controllingFrame;
	
	/*
	 * Open the PDF of the Technical Guide
	 */
	public static void main(String args[])       //main function
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
    	{
            public void run() 
        	{
		        try                                      //try statement
		        {
		            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + documentation);
		            
		        } catch (Exception e)                    //catch any exceptions here
		          {
					  JOptionPane.showMessageDialog(controllingFrame,
							"There is an error locating the CIFOR2 User Guide.\n" +
							"Please check the directory C:\\CIFOR2\\Documentation\n" +
							"to make sure the file CIFOR2_Data_Elements.pdf is present.\n" +
							" " +
							"Thank You");
		          }
		    }
    	});
	}
      
	/*
	 * Program Variables
	 */
      private static String documentation	=	c4Utilities.CIFORProperties.cifor_TechDoc;
     
}