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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author jonesg1
 */

/*
 *  Launch Excel with the file name passed from the requesting program.
 */

public class LaunchExcel extends javax.swing.JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LaunchExcel()
	{
		
        if (Desktop.isDesktopSupported()) 
        {
            desktop = Desktop.getDesktop();
	        
			//File excelFile = new File(DetailReport.fileName);
			File excelFile = new File(fileName);
			
			try {
				desktop.open(excelFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

	/*
	 *  Main Section
	 */	
	public static void main(String args[]) {
	    java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	        	new LaunchExcel();
	        }
	    });
}

	/*
	 *  Variables
	 */
	private Desktop desktop;
	public static String fileName;
}