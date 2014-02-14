package c4Reports;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FrequencyTableCellRenderer extends DefaultTableCellRenderer 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean
					isSelected, boolean hasFocus, int row, int column)
			{
				super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
				this.setHorizontalAlignment(RIGHT);
					
			return this;
			}
}