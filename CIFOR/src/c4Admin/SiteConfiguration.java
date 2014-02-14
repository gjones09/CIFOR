package c4Admin;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import java.awt.event.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Set;

public class SiteConfiguration extends JFrame 
									implements ActionListener
{

	JDesktopPane desktop;
	
	GridBagLayout gridbag = new GridBagLayout();
	
    private static String OK = "ok";
    private static String CANCEL = "cancel";
    private static String HELP = "help";
    
    private JFrame controllingFrame;								// Used for dialogs
	
	private JTextField firstName;
    private JTextField lastName;
    private JTextField userID;
    private JPasswordField passWord;
    private JTextField permissions;
    
    JPanel pane = new JPanel();
    private JLabel lblNewLabel;



    

    public SiteConfiguration() 
    {
        super("Site Configuration1");

        //Make the big window be indented XXX pixels from each edge
        //of the screen.
        int insetX = 150;
        int insetY = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(insetX, insetY,
                  screenSize.width  - insetX*8,
                  screenSize.height - insetY*12);					//iconifiable
        
        lblNewLabel = new JLabel("New label");
        getContentPane().add(lblNewLabel, BorderLayout.NORTH);
    }
    
    private void InitializeGUI ()
    {

//        setBounds(20, 20, 200, 150);	//Set up the GUI.

        pane.setOpaque(true);
//        pane.setBackground(new Color(248, 213, 131));
        
        GridBagConstraints constraints;
        getContentPane().setLayout(gridbag);
        
//        JLabel firstLabel = new JLabel("User First Name: ");
//        JLabel lastLabel = new JLabel("User Last Name: ");
//        JLabel userIDLabel = new JLabel("User ID: ");
//        JLabel passWordLabel = new JLabel("Password: ");
//        JLabel permissionsLabel = new JLabel("Menu Permissions: ");
        
        firstName = new JTextField(15);							// Create the First Name Field
        firstName.setActionCommand(OK);							// Set the Action Command value
        firstName.addActionListener(this);						// Set the Listener
        JLabel firstLabel = new JLabel("User First Name: ");	// Create the Label
        
        lastName = new JTextField(15);							// Create the Last Name Field
        lastName.setActionCommand(OK);							// Set the Action Command value
        lastName.addActionListener(this);						// Set the Listener
        JLabel lastLabel = new JLabel("User Last Name: ");		// Create the Label
        
        userID = new JTextField(10);							// Create the User ID Field
        userID.setActionCommand(OK);							// Set the Action Command value
        userID.addActionListener(this);							// Set the Listener
        JLabel userIDLabel = new JLabel("User ID: ");			// Create the Label
        
        passWord = new JPasswordField(10);						// Create the User ID Field
        passWord.setActionCommand(OK);							// Set the Action Command value
        passWord.addActionListener(this);						// Set the Listener
        JLabel passWordLabel = new JLabel("Password: ");		// Create the Label
        
        permissions = new JTextField(1);						// Create the User ID Field
        permissions.setActionCommand(OK);							// Set the Action Command value
        permissions.addActionListener(this);						// Set the Listener
        JLabel permissionsLabel = new JLabel("Menu Permissions: ");	// Create the Label
        
        pane.add(firstLabel);
        pane.add(firstName);
        pane.add(lastLabel);
        pane.add(lastName);
        pane.add(userIDLabel);
        pane.add(userID);
        pane.add(passWordLabel);
        pane.add(passWord);
        pane.add(permissionsLabel);
        pane.add(permissions);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand(OK);
        okButton.addActionListener(this);
        
//        JButton cancelButton = new JButton("Cancel");
//        cancelButton.setActionCommand(CANCEL);
//        cancelButton.addActionListener(this);
        
        JButton helpButton = new JButton("Help");
        helpButton.setActionCommand(HELP);
        helpButton.addActionListener(this);
        //getContentPane().add(pane);

        addComponent(firstLabel, 		0, 0, 1, 1, 10, 100,
        		GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(firstName, 		1, 0, 9, 1, 90, 100,
        		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
        addComponent(lastLabel, 		0, 1, 1, 1, 10, 100,
        		GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(lastName, 			1, 1, 9, 1, 90, 100,
        		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
       
        addComponent(userIDLabel, 		0, 2, 1, 1, 10, 100,
        		GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(userID, 			1, 2, 9, 1, 90, 100,
        		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
        addComponent(passWordLabel, 	0, 3, 1, 1, 10, 100,
        		GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(passWord, 			1, 3, 9, 1, 90, 100,
        		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
        addComponent(permissionsLabel, 	0, 4, 1, 1, 10, 100,
        		GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(permissions, 		1, 4, 1, 1, 10, 100,
        		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
        addComponent(okButton, 			0, 5, 1, 1, 10, 30,
        		GridBagConstraints.NONE, GridBagConstraints.EAST);
//      addComponent(cancelButton, 		1, 5, 1, 1, 10, 30,
//      		GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(helpButton, 		1, 5, 1, 1, 10, 30,
        		GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        getContentPane().add(pane);
        
        try
        {
        	UIManager.setLookAndFeel(
        			UIManager.getSystemLookAndFeelClassName());
        	SwingUtilities.updateComponentTreeUI(this);
            //pane.setOpaque(true);
//            pane.setBackground(new Color(248, 213, 131));
        }
        	catch (Exception e)
        	{
        		System.out.println("Can't set look and feel: "
        				+ e.getMessage());
				e.printStackTrace();
        	}
        
        //pane.setOpaque(true);
//        pane.setBackground(new Color(248, 213, 131));
        setVisible(true);
 
    }

        
    public  void actionPerformed(ActionEvent e)
    {
    	String cmd = e.getActionCommand();
    	
    	if (OK.equals(cmd))
    		{
    			JOptionPane.showMessageDialog(controllingFrame,
					"Update User Setup");
    		}
    	else
		if (CANCEL.equals(cmd))
    		{
    			JOptionPane.showMessageDialog(controllingFrame,
    				"Cancelling User Setup!");
    		}
    	else
		if (HELP.equals(cmd))
    		{
    			JOptionPane.showMessageDialog(controllingFrame,
    				"Enter User Information");
    		}
    }
    


//*******************************************************************
//* Build the grid bag constraints method
//*******************************************************************
  
  private void addComponent(Component component, int gridx, int gridy,
  		int gridwidth, int gridheight, int weightx, int weighty, int fill,
  		int anchor)
  {
  	GridBagConstraints constraints = new GridBagConstraints();
  	constraints.gridx = gridx;							// Upper left corner vertically
  	constraints.gridy = gridy;							// Upper left corner horizontally
  	constraints.gridwidth = gridwidth;					// Number of cells horizontally
  	constraints.gridheight = gridheight;				// Number of cells vertically
  	constraints.weightx = weightx;						// Size relative to components on the same row
  	constraints.weighty = weighty;						// Size relative to components on the same column
  	constraints.fill = fill;							// Value that determines if a component expands
  	constraints.anchor = anchor;						// Where component is displayed within it's cell
  	gridbag.setConstraints(component, constraints);		// Set component constraints
  	getContentPane().add(component);										// Add the component
  }
    
//*******************************************************************    

  private static void createAndShowGUI() {
      // Make sure we have nice window decorations.
      JFrame.setDefaultLookAndFeelDecorated(true);

      // Create and set up the window.
      SiteConfiguration frame = new SiteConfiguration();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
      // Display the window.
      frame.setVisible(true);
  }
  
  public static void main(String[] args) 
  {
		/*
		 *  Establish a connection to the database
		 */    	
  	c4DataBase.DBUpdate.DataBaseConnection();
  	
      //Schedule a job for the event-dispatching thread:
      //creating and showing this application's GUI.
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
          public void run() {
//          	UIManager.put("swing.boldMetal", Boolean.FALSE);
          	UIManager.put("swing.Windows", Boolean.TRUE);
					createAndShowGUI();
				}
      });
              	
  }
  
	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}
}