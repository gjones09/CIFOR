package c4Calendar;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;

import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;

import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Button;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.SoftBevelBorder;

import c4ClusterReport.Parameters;

public class Calendar extends JFrame
	{
		/**
		 * Program Variables
		 */
			private 		JPanel		panelCalendar;
			public static 	JButton 	btnNextMonth;
			public static 	JButton 	btnPreviousMonth;
			public static 	JComboBox 	comboYear;
			public static	JButton		btnOK;
			public static 	JLabel 		lblMonth;
			public static 	JScrollPane scrollpaneCalendar;
			private static 	JTable 		tblCalendar;
			public static 	DefaultTableModel tblCalendarModel;
			public static 	Integer 	calendarDay = 0;
		    public static 	int 		gregorianYear, gregorianMonth, gregorianDay, currentYear, currentMonth;
		    public static	int 		rowClicked, colClicked;
		    public static	Integer		monthClicked;
		    public static	Integer		yearClicked;
		    public static	Integer		paramMonth;
		    public static	JTextField	newMonth;
		    private 		JFrame 		controllingFrame;

		/**
		 * Launch the application.
		 */
		public static  void main(String[] args)
			{
				EventQueue.invokeLater(new Runnable()
					{
						public void run()
							{
								try
									{
										Calendar frame = new Calendar();
										frame.setVisible(true);
									} catch (Exception e)
									{
										e.printStackTrace();
									}
							}
					});
			}

		/**
		 * Create the frame.
		 */
		public Calendar()
			{
				
		        FormListener formListener = new FormListener();
		        KeyListener keyListener = null;
		        
				/*
				 *   Setup Row and Column Vectors for the database
				 */		
				final Vector<String> Columns = new Vector<String>();		
				final Vector<String> Rows = new Vector<String>();
				
				setTitle("CIFOR - Calendar");
				setResizable(false);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setBounds(800, 180, 280, 286);		// x-Coordinate, y-Coordinate, width, height
				panelCalendar = new JPanel();
				panelCalendar.setBounds(new Rectangle(5, 5, 270, 290));
				panelCalendar.setLocale(Locale.US);
				panelCalendar.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
				setContentPane(panelCalendar);
				panelCalendar.setLayout(null);
				
				btnPreviousMonth = new JButton("<<");
				btnPreviousMonth.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btnPreviousMonth.addActionListener(formListener);
				btnPreviousMonth.addKeyListener(keyListener);
				btnPreviousMonth.setLocale(Locale.US);
				btnPreviousMonth.setBorder(new EmptyBorder(0, 0, 0, 0));
				btnPreviousMonth.setBounds(12, 13, 50, 25);
				panelCalendar.add(btnPreviousMonth);
				
				lblMonth = new JLabel("November");
				lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
				lblMonth.setLabelFor(panelCalendar);
				lblMonth.setLocale(Locale.US);
				lblMonth.setBounds(60, 13, 150, 25);
				panelCalendar.add(lblMonth);	
				
				btnNextMonth = new JButton(">>");
				btnNextMonth.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btnNextMonth.addActionListener(formListener);
				btnNextMonth.addKeyListener(keyListener);
				btnNextMonth.setBorder(new EmptyBorder(0, 0, 0, 0));
				btnNextMonth.setBounds(208, 13, 50, 25);
				panelCalendar.add(btnNextMonth);
				
				/*
				 * Create the calendar based upon today's date
				 */
		        GregorianCalendar cal = new GregorianCalendar(); 		//Create calendar
		        gregorianDay 	= cal.get(GregorianCalendar.DAY_OF_MONTH); 	//Get today's day
		        gregorianMonth 	= cal.get(GregorianCalendar.MONTH); 		//Get today's month
		        gregorianYear 	= cal.get(GregorianCalendar.YEAR); 			//Get today's year
		        currentMonth = gregorianMonth; 								//Set the current month and year
		        currentYear = gregorianYear;
				
				/*
				 *   Setup the columns for the GUI and the Report
				 */		
		        tblCalendarModel = new DefaultTableModel(){
		        	/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					
				public boolean isCellEditable(int rowIndex, int colIndex){return false;}};

		        tblCalendar = new JTable(tblCalendarModel);
		        tblCalendar.addMouseListener(new java.awt.event.MouseAdapter() {
		        	public void mouseClicked(java.awt.event.MouseEvent evt) {
		        		tblCalendarMouseClicked(evt);
		        	}
		        });	
		        scrollpaneCalendar = new JScrollPane(tblCalendar);
		        
		        //Add day headers
		        DateFormatSymbols gregorianDays;
		        String[] defaultDays;
		        gregorianDays = new DateFormatSymbols(new Locale("en", "US"));	// US=en,US  French=fr,FR  Japanese=ja,JP  German=de,DE
		        defaultDays = gregorianDays.getShortWeekdays();
		        
		        for (int i=1; i<defaultDays.length; i++)
		        	{
		        		tblCalendarModel.addColumn(defaultDays[i]);
		        	}
		        
		        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
		        
				/**
				 *   Create and set attributes for the Calendar Table
				 */
		        tblCalendar.getTableHeader().setResizingAllowed(false);		//No resize
		        tblCalendar.getTableHeader().setReorderingAllowed(false);	//No reorder
				tblCalendar.setFont(new Font("Tahoma", Font.PLAIN, 12));
				tblCalendar.setColumnSelectionAllowed(true);
		        tblCalendar.setRowSelectionAllowed(true);
		        tblCalendar.setCellSelectionEnabled(true);
				tblCalendar.setAutoCreateRowSorter(false);
				tblCalendar.setFillsViewportHeight(true);
				tblCalendar.setIntercellSpacing(new java.awt.Dimension(2, 2));
				tblCalendar.setRowHeight(24);
				tblCalendar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tblCalendar.setBounds(295, 5, -293, 170);  // x-coordinate, y-coordinate, width, height 307,50,-293,170
		        
				scrollpaneCalendar.setWheelScrollingEnabled(false);
				scrollpaneCalendar.setBounds(12, 45, 247, 162);
				scrollpaneCalendar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				scrollpaneCalendar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		    
				/*
				 *   Define the table's column widths
				 */    
		        tblCalendarModel.setColumnCount(7);
		        tblCalendarModel.setRowCount(6);
		        
			    TableColumn column = null;									
				for (int i = 0; i < 7; i++) 
				{
				    column = tblCalendar.getColumnModel().getColumn(i);
				    if 		(i == 0)	{column.setPreferredWidth(35); column.setResizable(false);}		// Sun
				    else if (i == 1)	{column.setPreferredWidth(35); column.setResizable(false);}		// Mon
				    else if (i == 2)	{column.setPreferredWidth(35); column.setResizable(false);}		// Tue
				    else if (i == 3)	{column.setPreferredWidth(35); column.setResizable(false);}		// Wed
				    else if (i == 4)	{column.setPreferredWidth(35); column.setResizable(false);}		// Thu
				    else if (i == 5)	{column.setPreferredWidth(35); column.setResizable(false);}		// Fri
				    else if (i == 6)	{column.setPreferredWidth(35); column.setResizable(false);}		// Sat
				}
	            
				panelCalendar.add(scrollpaneCalendar);
				
				JLabel lblNewYear = new JLabel("Change Year:");
				lblNewYear.setBounds(100, 219, 87, 16);
				panelCalendar.add(lblNewYear);
				
				comboYear = new JComboBox();
				comboYear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
						{
							Object selected = comboYear.getSelectedItem();							
						}
					});
				comboYear.addActionListener(formListener);
				comboYear.addKeyListener(keyListener);
				comboYear.setBounds(192, 216, 66, 22);
							
		        //Populate combo box with -10 years & +10 years from the current year
		        for (int i=gregorianYear-10; i<=gregorianYear+10; i++)
		        	{
		        	    String year = Integer.toString(i);
		        		comboYear.addItem(year);
		        	}
		        comboYear.setSelectedItem(String.valueOf(gregorianYear)); //Select the correct year in the combo box
		        
				panelCalendar.add(comboYear);  
				
				btnOK = new JButton("OK");				
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btnOK.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				btnOK.setFont(new Font("Tahoma", Font.PLAIN, 14));
				btnOK.setBounds(12, 215, 66, 25);
				btnOK.addActionListener(formListener);
				btnOK.addKeyListener(keyListener);
				panelCalendar.add(btnOK);
				
		        //Populate table
		        for (int i=gregorianYear-100; i<=gregorianYear+100; i++){
		            comboYear.addItem(String.valueOf(i));
		        }
		         
		        //Refresh calendar
		        refreshCalendar (gregorianMonth, gregorianYear); //Refresh calendar
		        }
		
		/**
		 * Get the day of the month for a mouse click
		 * refresh the calendar to highlight day selected
		 */
	    protected void tblCalendarMouseClicked(MouseEvent evt)
			{
				rowClicked = tblCalendar.getSelectedRow();
				colClicked = tblCalendar.getSelectedColumn();
				calendarDay = (Integer) tblCalendar.getValueAt(rowClicked, colClicked);

				//System.out.println("271 day clicked row=" + rowClicked + "  colClicked=" + colClicked + "  calendarDay=" + calendarDay);
				//System.out.println("272 thisMonth=" + thisMonth + "  thisYear=" + thisYear);
				//System.out.println("273 currentMonth=" + currentMonth + "  currentYear=" + currentYear);
				
				if(calendarDay != null) 
					{
						gregorianDay = calendarDay;
						//refreshCalendar(currentMonth, currentYear);
						refreshCalendar(monthClicked, yearClicked);
					}			
			}

		/**
	     * Refresh the Calendar after events for Next Month, Previous Month, Different Year 
	     * @param month
	     * @param year
	     */
	    public static void refreshCalendar(int month, int year){
	        //Variables
	        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	        int nod, som; //nod=Number Of Days, dom=Start Of Month
	             
	        btnPreviousMonth.setEnabled(true);
	        btnNextMonth.setEnabled(true);
	        if (month == 0 && year <= gregorianYear-10){btnPreviousMonth.setEnabled(false);} //Too early
	        if (month == 11 && year >= gregorianYear+100){btnNextMonth.setEnabled(false);} //Too late
	        lblMonth.setText(months[month]); //Refresh the month label at the top of the Panel
	        
	        comboYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
	        
	        monthClicked = month;
	        calendarDay = gregorianDay;
	        yearClicked = year;
	        //System.out.println("305 monthClicked=" + monthClicked + " yearClicked=" + yearClicked + "  calendarDay=" + calendarDay);

       //     paramMonth 	= 	currentMonth;	paramMonth++;
       //     paramDay	=	calendarDay;
       //     paramYear	=	currentYear;
            //System.out.println("361, paramMonth=" + paramMonth + "  paramDay=" + paramDay + "  paramYear=" + paramYear);
            
	        //Clear table
	        for (int i=0; i<6; i++){
	           for (int j=0; j<7; j++){
	        	   //System.out.println("Clear Table, i=" + i + "  j=" + j);
	               tblCalendarModel.setValueAt(null, i, j);
	            }
	        }
	         
	        //Get first day of month and number of days
	        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
	        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
	         
	        //Draw calendar
	        for (int i=1; i<=nod; i++){
	            int row = new Integer((i+som-2)/7);
	            int column  =  (i+som-2)%7;
	            tblCalendarModel.setValueAt(i, row, column);
	        }
	 
	        //Apply renderers
	        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	        
///////////////////////////////////
	        String moString = Integer.toString(gregorianMonth);
	        newMonth = new JTextField(moString,2);
	        //System.out.println("Cal 357, gregorianMonth=" + moString);
	    }
	 
	    static class tblCalendarRenderer extends DefaultTableCellRenderer{
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
	            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
	            
	            if (column == 0 || column == 6){ //Week-end
	                setBackground(new Color(250, 235, 215));
	            }
	            else{ //Week day
	                setBackground(new Color(255, 255, 255));
	            }
	            
	            if (value != null){
	                //if (Integer.parseInt(value.toString()) == thisDay && currentMonth == thisMonth && currentYear == thisYear){ //Today
	                if (Integer.parseInt(value.toString()) == calendarDay){ //Day Clicked
	            	//if (Integer.parseInt(value.toString()) == gregorianDay && currentMonth == gregorianMonth && currentYear == gregorianYear){
	                    setBackground(new Color(220, 220, 255));
                //System.out.println("351, gregorianDay=" + gregorianDay + "  gregorianMonth=" + gregorianMonth + "  gregorianYear=" + gregorianYear);
	                }
	            }
	            setBorder(null);
	            setForeground(Color.black);
	            return this; 
	        }
	    }
	    
/**
 *  Code for dispatching events from components to event handlers.
 */
final class FormListener implements java.awt.event.ActionListener {
    FormListener() {}
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == btnPreviousMonth) {						//System.out.println("actionPerformed previous btn");
            Calendar.this.btnPreviousMonthActionPerformed(evt);
        }
        else if (evt.getSource() == btnNextMonth) {						//System.out.println("actionPerformed next btn");
        	Calendar.this.btnNextMonthActionPerformed(evt);
        }
        else if (evt.getSource() == comboYear) {
        	Calendar.this.comboYearActionPerformed(evt);
        }
        else if (evt.getSource() == btnOK) {
            Calendar.this.btnOKActionPerformed(evt);
        }
    }
}

/**
 * Code to display the next month or previous month
 */
public void btnPreviousMonthActionPerformed(ActionEvent evt)
	{            
        if (currentMonth == 0){ //Back one year
            currentMonth = 11;
            currentYear -= 1;
        }
        else{ //Back one month
            currentMonth -= 1;
        }
        gregorianDay = 0;
        refreshCalendar(currentMonth, currentYear);
		
	}

public void btnNextMonthActionPerformed(ActionEvent evt)
	{
        if (currentMonth == 11){ //Foward one year
            currentMonth = 0;
            currentYear += 1;
        }
        else{ //Foward one month
            currentMonth += 1;
        }
        gregorianDay = 0;
        refreshCalendar(currentMonth, currentYear);
	}

/**
 * Code to display a new year and month
 */
public void comboYearActionPerformed(ActionEvent evt)
	{
        if (comboYear.getSelectedItem() != null)
        	{
            String b = comboYear.getSelectedItem().toString();
            currentYear = Integer.parseInt(b);
            refreshCalendar(currentMonth, currentYear);
        	}
	}

public void btnOKActionPerformed(ActionEvent evt)
	{
		if(c4ClusterReport.Parameters_Calendar.whichDate == "Current")
			{
				currentMonth++;
				String newDate = currentMonth + "/" + calendarDay +"/" + currentYear;
				c4ClusterReport.Parameters_Calendar.currentDate.setText(newDate);
			}
		else if(c4ClusterReport.Parameters_Calendar.whichDate == "Begin")
			{
				currentMonth++;
				String newDate = currentMonth + "/" + calendarDay +"/" + currentYear;
				c4ClusterReport.Parameters_Calendar.beginningDate.setText(newDate);
			}
		
		c4ClusterReport.Parameters_Calendar.frame.validate();
        
        if(calendarDay > 0) {
        }
        else {
	        JOptionPane.showMessageDialog(controllingFrame,
		   		"\n" +
		       	"Please select a Day of the month,\n"+
		   		"then press the OK key.\n" +
		   		" \n");
        }
        //System.out.println("Calendar 456, paramMonth=" + c4ClusterReport.ParametersCalendar.currentMonth + 
        //		"  paramDay=" + c4ClusterReport.ParametersCalendar.currentDay + 
        //		"  paramYear=" + c4ClusterReport.ParametersCalendar.currentYear);
        
        dispose();
	}

}
