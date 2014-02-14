package c4ClusterReport;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Button;
import java.util.Locale;
import java.util.Properties;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

import c4Calendar.Calendar;

public class Parameters_Calendar extends JFrame
	{
		/**
		 * Launch the application.
		 */
		public static void main(String[] args)
			{
				EventQueue.invokeLater(new Runnable()
					{
						public void run()
							{
								try
									{
										frame = new Parameters_Calendar();
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
		public Parameters_Calendar()
		{
	    	c4Utilities.DateFormatUtility.CalculateDate();		//* Run date utility		
	    	currentDate = new javax.swing.JTextField();
	        currentDate.setText(c4Utilities.DateFormatUtility.currDate);		
	        beginningDate = new javax.swing.JTextField();
	        beginningDate.setText(c4Utilities.DateFormatUtility.beginDate);
	    	
			UserProperties();	// Get the user properties
			
	        FormListener formListener = new FormListener();
	        KeyListener keyListener = null;

	        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("MessagesBundle_en_US"); // NOI18N
	        
			setTitle(bundle.getString("crParameterTitle")); // NOI18N
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(400, 100, 366, 444);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel labelPaneHeading = new JLabel(bundle.getString("crParameterHeading"));
			labelPaneHeading.setHorizontalAlignment(SwingConstants.CENTER);
			labelPaneHeading.setFont(new Font("Tahoma", Font.BOLD, 16));
			labelPaneHeading.setBounds(76, 23, 225, 22);
			contentPane.add(labelPaneHeading);
			
			JLabel labelCurrentDate = new JLabel(bundle.getString("currentDate"));
			labelCurrentDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
			labelCurrentDate.setBounds(24, 70, 103, 19);
			contentPane.add(labelCurrentDate);
			
			inputCurrentDate = new javax.swing.JTextField();
			inputCurrentDate = currentDate;
			inputCurrentDate.setToolTipText(bundle.getString("cTipEnterCurrentDate"));
			inputCurrentDate.setHorizontalAlignment(SwingConstants.CENTER);
			inputCurrentDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
			inputCurrentDate.setBounds(130, 67, 116, 22);
			contentPane.add(inputCurrentDate);
			inputCurrentDate.setColumns(12);
			
			btnCDCalendar = new JButton("");
			btnCDCalendar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnCDCalendar.setToolTipText(bundle.getString("cTipShowCalendar"));
			//ImageIcon calendarIcon = new ImageIcon( getClass().getResource("/Calendar_Image.PNG") );
			ImageIcon calendarIcon = new ImageIcon( getClass().getResource("/images/Calendar_Image.png") );
			btnCDCalendar.setIcon(calendarIcon);
			btnCDCalendar.setBounds(279, 67, 22, 25);
			btnCDCalendar.addActionListener(formListener);
			btnCDCalendar.addKeyListener(keyListener);
			contentPane.add(btnCDCalendar);
			
			JLabel labelBeginningDate = new JLabel(bundle.getString("beginningDate"));
			labelBeginningDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
			labelBeginningDate.setBounds(24, 105, 103, 19);
			contentPane.add(labelBeginningDate);
			
			inputBeginningDate = new javax.swing.JTextField();
	        inputBeginningDate = beginningDate;
			inputBeginningDate.setToolTipText(bundle.getString("cTipEnterBeginningDate"));
			inputBeginningDate.setHorizontalAlignment(SwingConstants.CENTER);
			inputBeginningDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
			inputBeginningDate.setColumns(12);
			inputBeginningDate.setBounds(130, 102, 116, 22);
			contentPane.add(inputBeginningDate);
			
			btnBDCalendar = new JButton("");
			btnBDCalendar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnBDCalendar.setToolTipText(bundle.getString("cTipShowCalendar"));
			btnBDCalendar.setIcon(calendarIcon);
			btnBDCalendar.setBounds(279, 102, 22, 25);
			btnBDCalendar.addActionListener(formListener);
			btnBDCalendar.addKeyListener(keyListener);
			contentPane.add(btnBDCalendar);
			
			ckboxShowAll = new JCheckBox(bundle.getString("cShowAllReports"));
			ckboxShowAll.setSelected(true);
			//showAllReports = ckboxShowAll.isSelected();
			ckboxShowAll.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
				}
			});
			ckboxShowAll.setFont(new Font("Tahoma", Font.PLAIN, 14));
			ckboxShowAll.setBounds(24, 155, 273, 25);
			contentPane.add(ckboxShowAll);
			
			ckboxShowRegions = new JCheckBox(bundle.getString("cShowRegions"));
			ckboxShowRegions.addItemListener(itemListener);
			ckboxShowRegions.setFont(new Font("Tahoma", Font.PLAIN, 14));
			ckboxShowRegions.setBounds(24, 185, 111, 25);
			contentPane.add(ckboxShowRegions);
			
			ckboxSortByRegion = new JCheckBox(bundle.getString("cSortByRegion"));
			ckboxSortByRegion.setFont(new Font("Tahoma", Font.PLAIN, 14));
			ckboxSortByRegion.setToolTipText(bundle.getString("cTipSortByRegion"));
			ckboxSortByRegion.setBounds(143, 185, 130, 25);
			ckboxSortByRegion.setEnabled(false);
			//sortByRegionSelected = ckboxSortByRegion.isSelected();
			contentPane.add(ckboxSortByRegion);
			
			ckboxShowClusterCode = new JCheckBox("Show the CDC Cluster Code");
			ckboxShowClusterCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
			ckboxShowClusterCode.setBounds(24, 239, 320, 25);
			contentPane.add(ckboxShowClusterCode);
			
			ckboxShowOnlyPrimaryPattern = new JCheckBox(bundle.getString("cShowOnlyPrimary"));
			if(upropOnlyShowPrimaryPattern.equals("No")) { 
				ckboxShowOnlyPrimaryPattern.setSelected(true);}
			else
				{ckboxShowOnlyPrimaryPattern.setSelected(false);}
			ckboxShowOnlyPrimaryPattern.setFont(new Font("Tahoma", Font.PLAIN, 14));
			ckboxShowOnlyPrimaryPattern.setBounds(24, 269, 320, 25);
			contentPane.add(ckboxShowOnlyPrimaryPattern);
			
			radiobtnCDC = new JRadioButton(bundle.getString("cCDC")); // NOI18N
			radiobtnCDC.setLocale(Locale.US);
			radiobtnCDC.setBounds(154, 302, 58, 25);
			if(upropPrimaryPattern.equals("CDC")) { 
				radiobtnCDC.setSelected(true); }
			radiobtnCDC.addActionListener(formListener);
			contentPane.add(radiobtnCDC, null);
			
			radiobtnLocal = new JRadioButton(bundle.getString("cLocal")); // NOI18N
			radiobtnLocal.setLocale(Locale.US);
			radiobtnLocal.setBounds(216, 302, 64, 25);			
			if(upropPrimaryPattern.equals("Local")) {
				radiobtnLocal.setSelected(true); }
			radiobtnLocal.addActionListener(formListener);
			contentPane.add(radiobtnLocal, null);
			
			buttons = new ButtonGroup();
			buttons.add(radiobtnCDC);
			buttons.add(radiobtnLocal);
			
			buttonOK = new JButton(bundle.getString("okButton")); //$NON-NLS-1$
			buttonOK.setLocale(Locale.US);
			buttonOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			buttonOK.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			buttonOK.setBounds(12, 348, 97, 25);
			buttonOK.requestFocus(true);
			buttonOK.setSelected(true);
			buttonOK.addActionListener(formListener);
			buttonOK.addKeyListener(keyListener);
			contentPane.add(buttonOK);
							
			buttonCancel = new JButton(bundle.getString("cancelButton")); //$NON-NLS-1$
			buttonCancel.setLocale(Locale.US);
			buttonCancel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
				}
			});
			buttonCancel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			buttonCancel.setBounds(118, 348, 97, 25);
			buttonCancel.addActionListener(formListener);
			contentPane.add(buttonCancel);

			buttonHelp = new JButton(bundle.getString("helpButton")); //$NON-NLS-1$
			buttonHelp.setLocale(Locale.US);
			buttonHelp.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
				}
			});
			buttonHelp.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			buttonHelp.setBounds(227, 348, 97, 25);
			buttonHelp.addActionListener(formListener);
			contentPane.add(buttonHelp);
			
			JTextField txtPrimaryPatternIs = new JTextField();
			txtPrimaryPatternIs.setBorder(null);
			txtPrimaryPatternIs.setEditable(false);
			txtPrimaryPatternIs.setRequestFocusEnabled(false);
			txtPrimaryPatternIs.setVerifyInputWhenFocusTarget(false);
			txtPrimaryPatternIs.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtPrimaryPatternIs.setText("Primary Pattern is:");
			txtPrimaryPatternIs.setBounds(27, 303, 134, 22);
			contentPane.add(txtPrimaryPatternIs);
			txtPrimaryPatternIs.setColumns(10);
		
		}
				
		/**
		 *  Check if Regions are shown, then enable the
		 *  checkbox to allow Sort by Region
		 */
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
			    
				if (state == ItemEvent.SELECTED) 
					{
						ckboxSortByRegion.setEnabled(true);
						frame.validate();
					}
				else 
					{
						ckboxSortByRegion.setSelected(false);
						ckboxSortByRegion.setEnabled(false);
						frame.validate();
					}
			}
		};
		
		/**
		 *  Code for dispatching events from components to event handlers.
		 */
	    private class FormListener implements java.awt.event.ActionListener {
	        FormListener() {}
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            if (evt.getSource() == btnCDCalendar) {
	                Parameters_Calendar.this.btnCDCalendarActionPerformed(evt);
	            }
	            if (evt.getSource() == btnBDCalendar) {
	                Parameters_Calendar.this.btnBDCalendarActionPerformed(evt);
	            }
	            if (evt.getSource() == buttonOK) {
	                Parameters_Calendar.this.buttonOKActionPerformed(evt);
	            }
	            else if (evt.getSource() == buttonCancel) {
	                Parameters_Calendar.this.cancelButtonActionPerformed(evt);
	            }
	            else if (evt.getSource() == buttonHelp) {
	                Parameters_Calendar.this.helpButtonActionPerformed(evt);
	            }
	            else if (evt.getSource() == radiobtnCDC) {
	                Parameters_Calendar.this.radiobtnCDCActionPerformed(evt);
	            }
	            else if (evt.getSource() == radiobtnLocal) {
	                Parameters_Calendar.this.radiobtnLocalActionPerformed(evt);
	            }
	        }
	    }                       
		
		private void btnCDCalendarActionPerformed(java.awt.event.ActionEvent evt) { 
			whichDate = "Current";
			c4Calendar.Calendar.main(null);	    
		}

		private void btnBDCalendarActionPerformed(java.awt.event.ActionEvent evt) { 
			whichDate = "Begin";
			c4Calendar.Calendar.main(null);
		}

		private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
	                 JOptionPane.showMessageDialog(controllingFrame,
	            		"\n" +
	                	"Click the Calendar Icon to select the Current\n"+
		         		"Or Beginning Dates for the Date Range.\n" +
	                	"\n" +
		        		"Check the Show Current Reports CheckBox for\n" +
	                	"Today's Reports.  Leave it unchecked for all reports.\n" +
		        		"\n" +
	                	"Check Show Regions to display the Region column\n" +
		        		"and check Sort by Region to sort the report by Region.\n" +
	                	"\n" +
		        		"Check Show the CDC Cluster Code to show the CDC\n" + 
	                	"Cluster Code.  Leave it unchecked and the CDC\n" +
		        		"Cluster Code column will not be displayed.\n" +
		        		"\n" +
	                	"Check Show only the Primary Pattern to show only\n" +
		        		"the selected Primary Pattern.  Leave it unchecked\n" +
	                	"to show both Primary and Local Patterns.\n" +
		        		"\n" +
	                	"Check the CDC or Local button to identify which\n" +
		        		"is the Primary Pattern.\n" +
		        		" \n");
	    }                                          

	    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
	        dispose();
	    }
	    
	    private void radiobtnCDCActionPerformed(java.awt.event.ActionEvent evt) {                                             
	    	radiobtnCDC.setSelected(true);
	    }
	    
	    private void radiobtnLocalActionPerformed(java.awt.event.ActionEvent evt) {                                             
	    	radiobtnLocal.setSelected(true);
	    }
	    
	    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) { 	    	
	        vAllReports = new JTextField(c4Utilities.CIFORProperties.crParameterAll);
	        vTodaysReports = new JTextField(c4Utilities.CIFORProperties.crParameterToday);
	        if (ckboxShowAll.isSelected()) 
	            {
	                vReportSelection = vTodaysReports;
	            }
	        else
	            {
	                vReportSelection = vAllReports;
	            }

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
		        	ClusterReport.main(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				dispose();
		    }
	    }                                          
	
		/**
		 * Get User Properties
		 */
		private void UserProperties() {
			FileInputStream in;
			try
				{
				in = new FileInputStream("C:/CIFOR/Properties/CIFOR_UserProperties.properties");
				
				Properties prop = new Properties();  
				prop.load(in);			
				
				upropPrimaryPattern = prop.getProperty("PrimaryPattern");
				upropOnlyShowPrimaryPattern = prop.getProperty("OnlyShowPrimaryPattern");
			
				in.close();
				}
			catch (IOException ioe) 
				{
					JOptionPane.showMessageDialog(controllingFrame,
							"The CIFOR_UserProperties file was not be found.  Please\n" +
							"put the file in the C:\\CIFOR\\Properties\\ directory\n" +
							"or refer to the User Guide for help.\n" +
							"\n" +
							"Thank You");
					System.exit(0);
				}
		}
		
	    /**
	     * Local Variables
	     */
		public static 	JFrame 		frame;
	    private 		JFrame 		controllingFrame;
		private 		JPanel		contentPane;
		public static	JTextField	currentDate;
		public static	JTextField	beginningDate;
		public static 	JTextField 	inputCurrentDate;
		public static 	JTextField 	inputBeginningDate;
		public static	String		stringCurrentDate;
		public static	String 		stringBeginningDate;
		public static 	String		whichDate;
		public static	JCheckBox 	ckboxShowAll;
		public static	JCheckBox	ckboxShowRegions;
		public static	JCheckBox	ckboxSortByRegion;
		public static	JCheckBox 	ckboxShowOnlyPrimaryPattern;
		public static	JCheckBox	ckboxShowClusterCode;

	    public static JTextField vReportSelection;
	    public static JTextField vAllReports;
	    public static JTextField vTodaysReports;
	    
		public	static	JRadioButton radiobtnCDC;
		public	static	JRadioButton radiobtnLocal;
		private 		ButtonGroup	buttons;
		
		public	static	Integer		currentMonth;
		public	static	Integer		currentDay;
		public	static	Integer		currentYear;
		public	static	JCheckBox	ckboxNewMonth;
	    public  static 	JButton 	btnCDCalendar;
	    public  static 	JButton 	btnBDCalendar;
	    public  static 	JButton 	buttonOK;
	    public  static 	JButton 	buttonCancel;
	    public  static 	JButton 	buttonHelp;
	    
	    /**
	     * Variables from the User Properties file
	     */
	  	public static String upropPrimaryPattern;
	  	public static String upropOnlyShowPrimaryPattern;
	  	
	}