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

package c4DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
*
* @author jonesg1
*/

/*
 * This program performs the database connection
 * when called by the the Cluster Report, Frequency Analysis
 * Detail Report (Query) or any other requested database
 * connection.
 */
public class DBConnection
{
	/*
	 * Get the properties for the database connection
	 */
	public static void getProperties()
	{
		driver 		= c4Utilities.CIFORProperties.cifor_DB_driver;
		url			= c4Utilities.CIFORProperties.cifor_DB_url;
		userid		= c4Utilities.CIFORProperties.cifor_DB_userid;
		password	= c4Utilities.CIFORProperties.cifor_DB_password;   
	}
	
	/*
	 * Establish a connection to the database
	 */
	public static void DataBaseConnection()
	{
		getProperties();
		try 
			{
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
	
				connection = DriverManager.getConnection( url, userid, password );
			}
		catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(controllingFrame,
						"The CIFOR Database could not be opened.\n" +
						"Either the Database could not be found \n" +
						"or the User ID or Password was invalid.\n" +
						"(C4Oracle_DBConnection)\n" +
						" " +
						"Thank You");
				System.exit(0);
			}
    }
	
	/*
	 * Close the database connection
	 */
	public static void DatabaseClose()
	{
		try 
			{
				connection.close();
			} 
		catch (SQLException e) 
			{
				e.printStackTrace();
			}
    }

	/*
	 * Program Variables
	 * 
	 */
	public static Connection connection;
	public static String driver;
	public static String url;
	public static String userid; 
	public static String password;
	public static JFrame controllingFrame;
}