/**
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

package c4ClusterReport;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import c4Reports.ClusterReport;

/**
 * @author gjones1
 */

public class ClusterTableCellRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Check the Received Date to see if it matches the Current Date parameter
	 * If it matches, render the Received Date cell in light gray for visibility
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean
			isSelected, boolean hasFocus, int row, int column)
	{
		super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		
		String rptDate = (String) value;

		int dateResult = rptDate.compareTo(c4ClusterReport.ClusterReport.currentString);
	
   		if( dateResult == 0 )
   		{
   			this.setBackground( Color.LIGHT_GRAY );
   		}
   		else
   		{	
			this.setBackground( Color.WHITE );
   		}
		
	return this;
	}
}