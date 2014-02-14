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

import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * 
 * @author gjones1
 */

public class DynamicClusterTableCellRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 1L;
	
	/*
	 * This Renderer is used in the DynamicClusterReport
	 * 
	 * Check the Received Date to see if it matches the Current Date parameter
	 * If it matches, render the Received Date cell in light gray for visibility
	 */

		public Component getTableCellRendererComponent(JTable table, Object value, boolean
					isSelected, boolean hasFocus, int row, int column)
			{
				super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
				
				//System.out.println("RC TableCellRenderer1  currentdate = " + DynamicClusterReport.currentDateString + "  rptDate=" + DynamicClusterMain.rptDate);
				
				//String rcvDate = (String) value;

///////////////////////////////////
				int col = (int) column;
				
				//System.out.println("RC Col = " + col);
				//System.out.println("RC TableCellRender2  specID = " +   "rcvDate = " + rcvDate);
				//System.out.println("RC TableCellRenderer2  currentdate = " + RegionClusterReportParameters.stringCurrentDate);
				//if(rcvDate != null)
				//if(DynamicClusterMain.rcvDate != null)
				if(DynamicClusterMain.rptDate != null)
					{
						//System.out.println("TCR 59, rptDate = " + DynamicClusterMain.rptDate + "  currentString = " + DynamicDateCk.currentString);
						int dateResult = DynamicClusterMain.rptDate.compareTo(DynamicDateCk.currentString);
						
				   		if( dateResult == 0 )
					   		{
					   			//System.out.println("TableCellRender2 currentDate = " + RegionDateCk.currentDateString + "  rcvDate = "+ rcvDate);
					   			this.setBackground( Color.LIGHT_GRAY );
					   			//System.out.println("TCR 66, color=gray");
					   		}
				   		else
					   		{	
					   			//System.out.println("TableCellRender3 currentDate = " + RegionDateCk.currentDateString + "  rcvDate = "+ rcvDate);
								this.setBackground( Color.WHITE );
					   			//System.out.println("TCR 72, color=white");
					   		}
					}
			return this;
			
			}
}