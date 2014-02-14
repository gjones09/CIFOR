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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import c4Utilities.UserProperties;
/**
*
* @author jonesg1
*/

/*
 * This program performs database updates when
 * called by the DataBase_Gateway program.
 */
public class DynamicReadDB
{
	
	public static void main(String[] args) throws SQLException
	{
		DataBaseConnection();
		SetupResultSet();
		ReportHeaders();
		DataBaseClose();
	}

	
	/*
	 * Establish a database connection
	 */
	public static void DataBaseConnection()
	{
		c4Utilities.CIFORProperties.getProperties();
		driver 		= c4Utilities.CIFORProperties.cifor_DB_driver;
		url			= c4Utilities.CIFORProperties.cifor_DB_url;
		userid		= c4Utilities.CIFORProperties.cifor_DB_userid;
		password	= c4Utilities.CIFORProperties.cifor_DB_password;
		
		try {
			DriverManager.registerDriver(
			        new oracle.jdbc.OracleDriver());
		connection = DriverManager.getConnection( url, userid, password );
		}
		catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(controllingFrame,
						"The CIFOR Database could not be opened.\n" +
						"Either the Database could not be found \n" +
						"or the User ID or Password was invalid.\n" +
						"(C4Oracle_DBUpdate)\n" +
						" " +
						"Thank You");
				System.exit(0);
			}
    }
	
	public static void SetupResultSet() throws SQLException
		{	
			//System.out.println("DynamicReadDB 85, - SetupResultSet");
			//if(DynamicClusterParameters.regionSelected == true
			//		&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
			//		&& DynamicClusterParameters.clusterCodeSelected == false)
				
		    ResultSet rs = null;
		    PreparedStatement pstmt = null;
		    if(UserProperties.PrimaryPattern.equals ("CDC") && DynamicClusterParameters.sortByRegionSelected == true)
		    	{
				regionQuery =	// use the CDC primary enzyme pattern for sorting
					"SELECT  REGION, LAB_SPECIMEN_ID, FIRST_NAME, LAST_NAME, " +
						"CITY, STATE, COUNTY, AGE, " +
						"ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, " +
						"TO_CHAR(DATE_COLLECTED,'yyyy-MM-dd') DATE_COLLECTED, " +
						"TO_CHAR(DATE_RECEIVED,'yyyy-MM-dd') DATE_RECEIVED, " +
						"TO_CHAR(DATE_REPORTED,'yyyy-MM-dd') DATE_REPORTED, " +
						"CDC_CLUSTER_CODE, LOCAL_PRIMARY_ENZYME_PATTERN, LOCAL_SECONDARY_ENZYME_PATTERN " +
					"FROM CIFOR_LAB_RESULT " +
					"JOIN	CIFOR_DEMOGRAPHICS ON CIFOR_LAB_RESULT.DEMOGRAPHICS_ID = CIFOR_DEMOGRAPHICS.ID " +
					"WHERE	DATE_REPORTED <= ? " +
					"AND 	DATE_REPORTED >= ? " +
					"ORDER BY REGION, ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, DATE_REPORTED DESC";
		    	}
		    else 
		    	if(UserProperties.PrimaryPattern.equals ("Local") && DynamicClusterParameters.sortByRegionSelected == true)
			    	{
					regionQuery =	//use the Local Primary Enzyme Pattern for sorting
							"SELECT  REGION, LAB_SPECIMEN_ID, FIRST_NAME, LAST_NAME, " +
								"CITY, STATE, COUNTY, AGE, " +
								"ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, " +
								"TO_CHAR(DATE_COLLECTED,'yyyy-MM-dd') DATE_COLLECTED, " +
								"TO_CHAR(DATE_RECEIVED,'yyyy-MM-dd') DATE_RECEIVED, " +
								"TO_CHAR(DATE_REPORTED,'yyyy-MM-dd') DATE_REPORTED, " +
								"CDC_CLUSTER_CODE, LOCAL_PRIMARY_ENZYME_PATTERN, LOCAL_SECONDARY_ENZYME_PATTERN " +
							"FROM CIFOR_LAB_RESULT " +
							"JOIN	CIFOR_DEMOGRAPHICS ON CIFOR_LAB_RESULT.DEMOGRAPHICS_ID = CIFOR_DEMOGRAPHICS.ID " +
							"WHERE	DATE_REPORTED <= ? " +
							"AND 	DATE_REPORTED >= ? " +
							"ORDER BY REGION, ORGANISM, LOCAL_PRIMARY_ENZYME_PATTERN, DATE_REPORTED DESC";
			    	}
	    	else
			    if(UserProperties.PrimaryPattern.equals ("CDC") && DynamicClusterParameters.sortByRegionSelected == false)
			    	{
					regionQuery =	// use the CDC primary enzyme pattern for sorting
						"SELECT  REGION, LAB_SPECIMEN_ID, FIRST_NAME, LAST_NAME, " +
							"CITY, STATE, COUNTY, AGE, " +
							"ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, " +
							"TO_CHAR(DATE_COLLECTED,'yyyy-MM-dd') DATE_COLLECTED, " +
							"TO_CHAR(DATE_RECEIVED,'yyyy-MM-dd') DATE_RECEIVED, " +
							"TO_CHAR(DATE_REPORTED,'yyyy-MM-dd') DATE_REPORTED, " +
							"CDC_CLUSTER_CODE, LOCAL_PRIMARY_ENZYME_PATTERN, LOCAL_SECONDARY_ENZYME_PATTERN " +
						"FROM CIFOR_LAB_RESULT " +
						"JOIN	CIFOR_DEMOGRAPHICS ON CIFOR_LAB_RESULT.DEMOGRAPHICS_ID = CIFOR_DEMOGRAPHICS.ID " +
						"WHERE	DATE_REPORTED <= ? " +
						"AND 	DATE_REPORTED >= ? " +
						"ORDER BY ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, DATE_REPORTED DESC";
			    	}
		    else 
		    	if(UserProperties.PrimaryPattern.equals ("Local") && DynamicClusterParameters.sortByRegionSelected == false)
			    	{
					regionQuery =	//use the Local Primary Enzyme Pattern for sorting
							"SELECT  REGION, LAB_SPECIMEN_ID, FIRST_NAME, LAST_NAME, " +
								"CITY, STATE, COUNTY, AGE, " +
								"ORGANISM, CDC_PRIMARY_ENZYME_PATTERN, CDC_SECONDARY_ENZYME_PATTERN, OTHER_RESULT, " +
								"TO_CHAR(DATE_COLLECTED,'yyyy-MM-dd') DATE_COLLECTED, " +
								"TO_CHAR(DATE_RECEIVED,'yyyy-MM-dd') DATE_RECEIVED, " +
								"TO_CHAR(DATE_REPORTED,'yyyy-MM-dd') DATE_REPORTED, " +
								"CDC_CLUSTER_CODE, LOCAL_PRIMARY_ENZYME_PATTERN, LOCAL_SECONDARY_ENZYME_PATTERN " +
							"FROM CIFOR_LAB_RESULT " +
							"JOIN	CIFOR_DEMOGRAPHICS ON CIFOR_LAB_RESULT.DEMOGRAPHICS_ID = CIFOR_DEMOGRAPHICS.ID " +
							"WHERE	DATE_REPORTED <= ? " +
							"AND 	DATE_REPORTED >= ? " +
							"ORDER BY ORGANISM, LOCAL_PRIMARY_ENZYME_PATTERN, DATE_REPORTED DESC";
			    	}
		    	else
		    		if(UserProperties.PrimaryPattern != "CDC" && UserProperties.PrimaryPattern != "Local")
		    		{
		    	        JOptionPane.showMessageDialog(controllingFrame,
		    	           		"\n" +
		    	               	"The User Properties file contains no\n"+
	    	            		"value for the choice of the Primary\n" +
		    	           		"Enzyme Pattern (CDC or Local)\n" +
	    	            		"\n" +
		    	           		"Please exit the CIFOR program and fix\n" +
		    	           		"the CIFOR_UserProperties file in the " +
		    	           		"C:/CIFOR/Properties/ directory." +
		    	           		" \n");
		    	        System.exit(0);
		    		}
		    
			pstmt = connection.prepareStatement(regionQuery);
			pstmt.setDate(1, DynamicDateCk.newDateCurrentDate); 		// set input parameter
			pstmt.setDate(2, DynamicDateCk.newDateBeginningDate); 	// set input parameter
			rs = pstmt.executeQuery();								// execute the query
					
			ResultSetMetaData metaDt = rs.getMetaData();
			int cols = metaDt.getColumnCount();
			
			/*
			 *   Fill the vectors with data from the query 
			 */        
			Rows.clear(); 			// clear unwanted value if exist any in Rows variable.
			
			while( rs.next())
			{				
				DynamicClusterMain.inputTestField = rs.getString(9) + rs.getString(10) + rs.getString(11) + rs.getString(12);
				DynamicDateCk.dateReportedString = rs.getString(15);		// Get the data from the row using the column index
				//System.out.println("DynamicReadDB 154, dateReportedString = " + DynamicDateCk.dateReportedString);
				int testResult = DynamicClusterMain.lastTestField.compareTo(DynamicClusterMain.inputTestField);
				
				if (testResult != 0 && DynamicClusterMain.ckBoxStatus == "Y")
					{
					DynamicDateCk.ckDateRange();
					DynamicClusterMain.lastTestField = DynamicClusterMain.inputTestField;
					}
				else if (DynamicClusterMain.ckBoxStatus == "N")
					{
					DynamicDateCk.goodDate = "Y";
					}
		
				if(DynamicDateCk.goodDate == "Y")
				{
					if(c4Utilities.UserProperties.PrimaryPattern.equals("CDC")) //Check which pattern is 1st row
						{
							//System.out.println("DynamicReadDB 171, PrimaryPattern = " + c4Utilities.UserProperties.PrimaryPattern);
							pEP1 = rs.getString(10);
							sEP1 = rs.getString(11);
							pEP2 = rs.getString(17);
							sEP2 = rs.getString(18);	
							//System.out.println("ReadDB 142, pEP1="+pEP1+"  sEP1="+sEP1+"  pEP2="+pEP2+"  sEP2="+sEP2);
						}
					else
						if(c4Utilities.UserProperties.PrimaryPattern.equals("Local")) //Check which pattern is 2nd row
							{
								//System.out.println("DynamicReadDB 181, PrimaryPattern = " + c4Utilities.UserProperties.PrimaryPattern);
								pEP2 = rs.getString(10);
								sEP2 = rs.getString(11);
								pEP1 = rs.getString(17);
								sEP1 = rs.getString(18);
								//System.out.println("ReadDB 151, pEP1="+pEP1+"  sEP1="+sEP1+"  pEP2="+pEP2+"  sEP2="+sEP2);
							}
					
					//System.out.println("DynamicReadDB 190, starting vector");
					Vector<Object> row = new Vector<Object>(cols);
					//System.out.println("DynamicReadDB 192, out of vector");
					//for (int i = 1; i <= cols; i++)
					//{
					//	row.addElement(rs.getObject(i));
					//}
//////////////////////////////////////////////////////
					//if(DynamicClusterParameters.regionSelected == true
					//		&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
					//		&& DynamicClusterParameters.clusterCodeSelected == false)
					
					//if(DynamicClusterParameters.ckboxRegion.isSelected())
					if(DynamicClusterParameters.regionSelected == true)
					{
						//System.out.println("DynamicReadDB 205, 1 Region Selected=" + DynamicClusterParameters.regionSelected +
						//"  showOnlyPrimaryPattern=" + DynamicClusterParameters.showOnlyPrimaryPatternSelected);
						row.addElement(rs.getObject(1));
						row.addElement(rs.getObject(2));
						row.addElement(rs.getObject(3));
						row.addElement(rs.getObject(4));
						row.addElement(rs.getObject(5));
						row.addElement(rs.getObject(6));
						row.addElement(rs.getObject(7));
						row.addElement(rs.getObject(8));
						row.addElement(rs.getObject(9));
						row.addElement(pEP1);
						row.addElement(sEP1);
						//////////////////
						//if(c4Utilities.UserProperties.OnlyShowPrimaryPattern.equals("No"))
						if(DynamicClusterParameters.showOnlyPrimaryPatternSelected == true)
							{
								row.addElement(rs.getObject(12));	//other result
								if(DynamicClusterParameters.clusterCodeSelected == true) row.addElement(rs.getObject(16)); //cluster code
								row.addElement(rs.getObject(13));
								row.addElement(rs.getObject(14));
								row.addElement(rs.getObject(15));
							}
						else
							{
						//////////////////
								row.addElement(pEP2);
								row.addElement(sEP2);
								row.addElement(rs.getObject(12));	//other result
								if(DynamicClusterParameters.clusterCodeSelected == true) row.addElement(rs.getObject(16)); //cluster code
								row.addElement(rs.getObject(13));
								row.addElement(rs.getObject(14));
								row.addElement(rs.getObject(15));
							} //only with above change!!

						Rows.addElement(row);
						//DynamicClusterMain.rcvDate = rs.getString(14); 
						DynamicClusterMain.rptDate = rs.getString(15);									
					}

					if(DynamicClusterParameters.regionSelected == false)
					{
						//System.out.println("DynamicReadDB 244, 1 Region Selected=" + DynamicClusterParameters.regionSelected +
						//"  showOnlyPrimaryPattern=" + DynamicClusterParameters.showOnlyPrimaryPatternSelected);
						row.addElement(rs.getObject(2));
						row.addElement(rs.getObject(3));
						row.addElement(rs.getObject(4));
						row.addElement(rs.getObject(5));
						row.addElement(rs.getObject(6));
						row.addElement(rs.getObject(7));
						row.addElement(rs.getObject(8));
						row.addElement(rs.getObject(9));
						row.addElement(pEP1);
						row.addElement(sEP1);
						//////////////////
						if(DynamicClusterParameters.showOnlyPrimaryPatternSelected == true)
							{
								row.addElement(rs.getObject(12));
								if(DynamicClusterParameters.clusterCodeSelected == true) row.addElement(rs.getObject(16)); //cluster code
								row.addElement(rs.getObject(13));
								row.addElement(rs.getObject(14));
								row.addElement(rs.getObject(15));
							}
						else
							{
								row.addElement(pEP2);
								row.addElement(sEP2);
								row.addElement(rs.getObject(12));
								if(DynamicClusterParameters.clusterCodeSelected == true) row.addElement(rs.getObject(16)); //cluster code
								row.addElement(rs.getObject(13));
								row.addElement(rs.getObject(14));
								row.addElement(rs.getObject(15));
							}
						//////////////////

						Rows.addElement(row);
						//DynamicClusterMain.rcvDate = rs.getString(14); 
						DynamicClusterMain.rptDate = rs.getString(15);	
					}
				}
			}			
		rs.close(); 			//close the Resultset.
		pstmt.close(); 			//close the PreparedStatement.

	}
			
	/*
	 * Setup the columns for the GUI and the Report
	 */
	public static void ReportHeaders()
		{
			Columns.clear(); 					// clear unwanted value if exist any in Columns variable.
			
	        DynamicClusterParameters.regionSelected = DynamicClusterParameters.ckboxRegion.isSelected();
	        //if (DynamicClusterParameters.regionSelected) 
	        //	{
	        //		System.out.println("Check box state is selected." + DynamicClusterParameters.regionSelected);
	        //	} 
	    	//else 
	    	//	{
	    	//		System.out.println("Check box state is not selected." + DynamicClusterParameters.regionSelected);
	    	//	}
	        
			//if(DynamicClusterParameters.regionSelected == true
			//		&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
			//		&& DynamicClusterParameters.clusterCodeSelected == false)
			
			if(DynamicClusterParameters.regionSelected == true 
					&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
					&& DynamicClusterParameters.clusterCodeSelected == false)
				{
					//System.out.println("DynamicReadDB 308, OnlyShowPrimaryPattern=No and regionSelected=" + DynamicClusterParameters.regionSelected);
				    String[] stringColumnNames = 		// add the column names to the header
				    { 
				    	c4Utilities.UserProperties.RegionName,
				    	c4Utilities.CIFORProperties.crReportAccessionNo,
				    	c4Utilities.CIFORProperties.crReportFirstName,
				    	c4Utilities.CIFORProperties.crReportLastName,
				    	c4Utilities.CIFORProperties.crReportCity,
				    	c4Utilities.CIFORProperties.crReportState,
				    	c4Utilities.CIFORProperties.crReportCounty,
				    	c4Utilities.CIFORProperties.crReportAge,
				    	c4Utilities.CIFORProperties.crReportOrganism,
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,	//Changeable by the user
				    	c4Utilities.CIFORProperties.crReportOtherResult,
				    	c4Utilities.CIFORProperties.crReportDateCollected,
				    	c4Utilities.CIFORProperties.crReportDateReceived,
				    	c4Utilities.CIFORProperties.crReportDateReported,
				    };
						{
						    for(int i=0;i<stringColumnNames.length;i++)
						    	//System.out.println("TestDBRead, ln 171  column name = " + stringColumnNames[i]);
								Columns.addElement((String) stringColumnNames[i]);
						}
				}

			if(DynamicClusterParameters.regionSelected == true 
					&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
					&& DynamicClusterParameters.clusterCodeSelected == false)
			//if(c4Utilities.UserProperties.OnlyShowPrimaryPattern.equals("No") && DynamicClusterParameters.regionSelected == false)
				{
					//System.out.println("DynamicReadDB 338, OnlyShowPrimaryPattern=No and regionSelected=" + DynamicClusterParameters.regionSelected);
				    String[] stringColumnNames = 		// add the column names to the header
				    { 
				    	c4Utilities.UserProperties.RegionName,
				    	c4Utilities.CIFORProperties.crReportAccessionNo,
				    	c4Utilities.CIFORProperties.crReportFirstName,
				    	c4Utilities.CIFORProperties.crReportLastName,
				    	c4Utilities.CIFORProperties.crReportCity,
				    	c4Utilities.CIFORProperties.crReportState,
				    	c4Utilities.CIFORProperties.crReportCounty,
				    	c4Utilities.CIFORProperties.crReportAge,
				    	c4Utilities.CIFORProperties.crReportOrganism,
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,	//Changeable by the user
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,	//Changeable by the user
				    	c4Utilities.CIFORProperties.crReportOtherResult,
				    	c4Utilities.CIFORProperties.crReportDateCollected,
				    	c4Utilities.CIFORProperties.crReportDateReceived,
				    	c4Utilities.CIFORProperties.crReportDateReported,
				    };
						{
						    for(int i=0;i<stringColumnNames.length;i++)
						    	//System.out.println("TestDBRead, ln 171  column name = " + stringColumnNames[i]);
								Columns.addElement((String) stringColumnNames[i]);
						}
				}
			
			if(DynamicClusterParameters.regionSelected == true 
					&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
					&& DynamicClusterParameters.clusterCodeSelected == true)
				{
					//System.out.println("DynamicReadDB 308, OnlyShowPrimaryPattern=No and regionSelected=" + DynamicClusterParameters.regionSelected);
				    String[] stringColumnNames = 		// add the column names to the header
				    { 
				    	c4Utilities.UserProperties.RegionName,
				    	c4Utilities.CIFORProperties.crReportAccessionNo,
				    	c4Utilities.CIFORProperties.crReportFirstName,
				    	c4Utilities.CIFORProperties.crReportLastName,
				    	c4Utilities.CIFORProperties.crReportCity,
				    	c4Utilities.CIFORProperties.crReportState,
				    	c4Utilities.CIFORProperties.crReportCounty,
				    	c4Utilities.CIFORProperties.crReportAge,
				    	c4Utilities.CIFORProperties.crReportOrganism,
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,	//Changeable by the user
				    	c4Utilities.CIFORProperties.crReportOtherResult,
				    	c4Utilities.CIFORProperties.crReportClusterCode,
				    	c4Utilities.CIFORProperties.crReportDateCollected,
				    	c4Utilities.CIFORProperties.crReportDateReceived,
				    	c4Utilities.CIFORProperties.crReportDateReported,
				    };
						{
						    for(int i=0;i<stringColumnNames.length;i++)
						    	//System.out.println("TestDBRead, ln 171  column name = " + stringColumnNames[i]);
								Columns.addElement((String) stringColumnNames[i]);
						}
				}

			if(DynamicClusterParameters.regionSelected == true 
					&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
					&& DynamicClusterParameters.clusterCodeSelected == true)
			//if(c4Utilities.UserProperties.OnlyShowPrimaryPattern.equals("No") && DynamicClusterParameters.regionSelected == false)
				{
					//System.out.println("DynamicReadDB 338, OnlyShowPrimaryPattern=No and regionSelected=" + DynamicClusterParameters.regionSelected);
				    String[] stringColumnNames = 		// add the column names to the header
				    { 
				    	c4Utilities.UserProperties.RegionName,
				    	c4Utilities.CIFORProperties.crReportAccessionNo,
				    	c4Utilities.CIFORProperties.crReportFirstName,
				    	c4Utilities.CIFORProperties.crReportLastName,
				    	c4Utilities.CIFORProperties.crReportCity,
				    	c4Utilities.CIFORProperties.crReportState,
				    	c4Utilities.CIFORProperties.crReportCounty,
				    	c4Utilities.CIFORProperties.crReportAge,
				    	c4Utilities.CIFORProperties.crReportOrganism,
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,	//Changeable by the user
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,	//Changeable by the user
				    	c4Utilities.CIFORProperties.crReportOtherResult,
				    	c4Utilities.CIFORProperties.crReportClusterCode,
				    	c4Utilities.CIFORProperties.crReportDateCollected,
				    	c4Utilities.CIFORProperties.crReportDateReceived,
				    	c4Utilities.CIFORProperties.crReportDateReported,
				    };
						{
						    for(int i=0;i<stringColumnNames.length;i++)
						    	//System.out.println("TestDBRead, ln 171  column name = " + stringColumnNames[i]);
								Columns.addElement((String) stringColumnNames[i]);
						}
				}
			
///////////////////////////////////////////////			
			if(DynamicClusterParameters.regionSelected == false
					&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
					&& DynamicClusterParameters.clusterCodeSelected == false)
			//if(c4Utilities.UserProperties.OnlyShowPrimaryPattern.equals("Yes") && DynamicClusterParameters.regionSelected == true)
				{
					//System.out.println("DynamicReadDB 374, Show Region=" + DynamicClusterParameters.regionSelected +
					//		"  OnlyShowPrimaryPattern=" + DynamicClusterParameters.showOnlyPrimaryPatternSelected);
				    String[] stringColumnNames = 		// add the column names to the header
				    { 
				    	//c4Utilities.UserProperties.RegionName,
				    	c4Utilities.CIFORProperties.crReportAccessionNo,
				    	c4Utilities.CIFORProperties.crReportFirstName,
				    	c4Utilities.CIFORProperties.crReportLastName,
				    	c4Utilities.CIFORProperties.crReportCity,
				    	c4Utilities.CIFORProperties.crReportState,
				    	c4Utilities.CIFORProperties.crReportCounty,
				    	c4Utilities.CIFORProperties.crReportAge,
				    	c4Utilities.CIFORProperties.crReportOrganism,
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,	//Changeable by the user
				    	//c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,		//Changeable by the user
				    	//c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,	//Changeable by the user
				    	c4Utilities.CIFORProperties.crReportOtherResult,
				    	c4Utilities.CIFORProperties.crReportDateCollected,
				    	c4Utilities.CIFORProperties.crReportDateReceived,
				    	c4Utilities.CIFORProperties.crReportDateReported,
				    };
						{
						    for(int i=0;i<stringColumnNames.length;i++)
						    	//System.out.println("TestDBRead, ln 171  column name = " + stringColumnNames[i]);
								Columns.addElement((String) stringColumnNames[i]);
						}
				}

			if(DynamicClusterParameters.regionSelected == false
					&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
					&& DynamicClusterParameters.clusterCodeSelected == false)
			//if(c4Utilities.UserProperties.OnlyShowPrimaryPattern.equals("Yes") && DynamicClusterParameters.regionSelected == false)
				{
					//System.out.println("DynamicReadDB 408, Show Region=" + DynamicClusterParameters.regionSelected +
					//		"  OnlyShowPrimaryPattern=" + DynamicClusterParameters.showOnlyPrimaryPatternSelected);
				    String[] stringColumnNames = 		// add the column names to the header
				    { 
				    	//c4Utilities.UserProperties.RegionName,
				    	c4Utilities.CIFORProperties.crReportAccessionNo,
				    	c4Utilities.CIFORProperties.crReportFirstName,
				    	c4Utilities.CIFORProperties.crReportLastName,
				    	c4Utilities.CIFORProperties.crReportCity,
				    	c4Utilities.CIFORProperties.crReportState,
				    	c4Utilities.CIFORProperties.crReportCounty,
				    	c4Utilities.CIFORProperties.crReportAge,
				    	c4Utilities.CIFORProperties.crReportOrganism,
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,	//Changeable by the user
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,	//Changeable by the user
				    	c4Utilities.CIFORProperties.crReportOtherResult,
				    	c4Utilities.CIFORProperties.crReportDateCollected,
				    	c4Utilities.CIFORProperties.crReportDateReceived,
				    	c4Utilities.CIFORProperties.crReportDateReported,
				    };
						{
						    for(int i=0;i<stringColumnNames.length;i++)
						    	//System.out.println("TestDBRead, ln 171  column name = " + stringColumnNames[i]);
								Columns.addElement((String) stringColumnNames[i]);
						}
				}
			
			
			if(DynamicClusterParameters.regionSelected == false 
					&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == true
					&& DynamicClusterParameters.clusterCodeSelected == true)
				{
					//System.out.println("DynamicReadDB 308, OnlyShowPrimaryPattern=No and regionSelected=" + DynamicClusterParameters.regionSelected);
				    String[] stringColumnNames = 		// add the column names to the header
				    { 
				    	//c4Utilities.UserProperties.RegionName,
				    	c4Utilities.CIFORProperties.crReportAccessionNo,
				    	c4Utilities.CIFORProperties.crReportFirstName,
				    	c4Utilities.CIFORProperties.crReportLastName,
				    	c4Utilities.CIFORProperties.crReportCity,
				    	c4Utilities.CIFORProperties.crReportState,
				    	c4Utilities.CIFORProperties.crReportCounty,
				    	c4Utilities.CIFORProperties.crReportAge,
				    	c4Utilities.CIFORProperties.crReportOrganism,
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,	//Changeable by the user
				    	c4Utilities.CIFORProperties.crReportOtherResult,
				    	c4Utilities.CIFORProperties.crReportClusterCode,
				    	c4Utilities.CIFORProperties.crReportDateCollected,
				    	c4Utilities.CIFORProperties.crReportDateReceived,
				    	c4Utilities.CIFORProperties.crReportDateReported,
				    };
						{
						    for(int i=0;i<stringColumnNames.length;i++)
						    	//System.out.println("TestDBRead, ln 171  column name = " + stringColumnNames[i]);
								Columns.addElement((String) stringColumnNames[i]);
						}
				}

			if(DynamicClusterParameters.regionSelected == false 
					&& DynamicClusterParameters.showOnlyPrimaryPatternSelected == false
					&& DynamicClusterParameters.clusterCodeSelected == true)
			//if(c4Utilities.UserProperties.OnlyShowPrimaryPattern.equals("No") && DynamicClusterParameters.regionSelected == false)
				{
					//System.out.println("DynamicReadDB 338, OnlyShowPrimaryPattern=No and regionSelected=" + DynamicClusterParameters.regionSelected);
				    String[] stringColumnNames = 		// add the column names to the header
				    { 
				    	//c4Utilities.UserProperties.RegionName,
				    	c4Utilities.CIFORProperties.crReportAccessionNo,
				    	c4Utilities.CIFORProperties.crReportFirstName,
				    	c4Utilities.CIFORProperties.crReportLastName,
				    	c4Utilities.CIFORProperties.crReportCity,
				    	c4Utilities.CIFORProperties.crReportState,
				    	c4Utilities.CIFORProperties.crReportCounty,
				    	c4Utilities.CIFORProperties.crReportAge,
				    	c4Utilities.CIFORProperties.crReportOrganism,
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeCDC,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeCDC,	//Changeable by the user
				    	c4Utilities.UserProperties.ReportPrimaryEnzymeLocal,		//Changeable by the user
				    	c4Utilities.UserProperties.ReportSecondaryEnzymeLocal,	//Changeable by the user
				    	c4Utilities.CIFORProperties.crReportOtherResult,
				    	c4Utilities.CIFORProperties.crReportClusterCode,
				    	c4Utilities.CIFORProperties.crReportDateCollected,
				    	c4Utilities.CIFORProperties.crReportDateReceived,
				    	c4Utilities.CIFORProperties.crReportDateReported,
				    };
						{
						    for(int i=0;i<stringColumnNames.length;i++)
						    	//System.out.println("TestDBRead, ln 171  column name = " + stringColumnNames[i]);
								Columns.addElement((String) stringColumnNames[i]);
						}
				}
			
		}

	/*
	 * Close the database connection
	 */
	public static void DataBaseClose()
	{
		try 
			{
				connection.close();
			} 
			catch (SQLException e) 
				{
					System.out.println("C4Oracle_DBUpdate *** Could not Close the Database ***");
					e.printStackTrace();
				}

    }
	
    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        JOptionPane.showMessageDialog(controllingFrame,
   		"\n" +
       	"Enter the dates in MM/DD/YYYY format.\n"+
    		"\n" +
   		"Use  *  as the Wildcard to select a Region,\n" +
   		"eg;  Northwest or *west  or  *N*\n" +
   		"Leave the field blank to get all results." +
   		" \n");
}

	/*
	 *   Setup Row and Column Vectors for the database
	 */		
	static Vector<String> Columns = new Vector<String>();
	static Vector<Vector<Object>> Rows = new Vector<Vector<Object>>();
	//public static String[] stringColumnNames;
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
	public static String regionQuery;
	public static String pEP1;
	public static String sEP1;
	public static String pEP2;
	public static String sEP2;
}