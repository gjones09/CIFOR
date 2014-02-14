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

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import c4FileImport.FileImport_FileModelUpdateRpt;
import c4FileImport.FileImport_ReadInputFile;
/**
*
* @author jonesg1
*/

/*
 *  This program is called by FileImport_FileModelUpdateRpt
 *  This program calls DBUpdate
 */

/*
 *  Update the CIFOR Database
 */

public class DataBase_Gateway
{
	
/* 
 * "main" section of the program
 */
	public static void main(String[] arguments) throws SQLException
	{
		LabSpecimenID 				= FileImport_FileModelUpdateRpt.field1;		// Lab Accession No
		SubmitterSpecimenID			= FileImport_FileModelUpdateRpt.field2;		// Submitter Specimen ID
		SpecimenSource				= FileImport_FileModelUpdateRpt.field3;		// Specimen Type (S/I)
		DateCollectedString			= FileImport_FileModelUpdateRpt.field4;		// Date Collected
		DateReceivedString			= FileImport_FileModelUpdateRpt.field5;		// Date Received
		DateReportedString			= FileImport_FileModelUpdateRpt.field6;		// Date Reported
		PrimaryTest					= FileImport_FileModelUpdateRpt.field7;		// Primary Test
		Organism					= FileImport_FileModelUpdateRpt.field8;		// Organism
		CDCPrimaryEnzymePattern		= FileImport_FileModelUpdateRpt.field9;		// CDC Primary Enzyme Pattern
		CDCSecondaryEnzymePattern	= FileImport_FileModelUpdateRpt.field10;	// CDC Secondary Enzyme Pattern
		OtherResult					= FileImport_FileModelUpdateRpt.field11;	// Other Result
//		DemographicsID				= FileImport_FileModelUpdateRpt.field12;	// Secondary Enzyme Pattern
//		EPICaseID					= FileImport_FileModelUpdateRpt.field11;	// Other Result
		SubmitterID					= FileImport_FileModelUpdateRpt.field12;	// Submitter ID
		SubmitterName				= FileImport_FileModelUpdateRpt.field13;	// Submitter Name
		SubmitterState				= FileImport_FileModelUpdateRpt.field14;	// Submitter State
		PatientID					= FileImport_FileModelUpdateRpt.field15;	// Patient ID
		FirstName					= FileImport_FileModelUpdateRpt.field16;	// Patient First Name
		MiddleName					= FileImport_FileModelUpdateRpt.field17;	// Patient Middle Name
		LastName					= FileImport_FileModelUpdateRpt.field18;	// Patient Last Name
		MultipleUnit				= FileImport_FileModelUpdateRpt.field19;	// Multiple Unit
		StreetAddress				= FileImport_FileModelUpdateRpt.field20;	// Street Address
		City						= FileImport_FileModelUpdateRpt.field21;	// City
		State						= FileImport_FileModelUpdateRpt.field22;	// State
		County						= FileImport_FileModelUpdateRpt.field23;	// County
		PostalCode					= FileImport_FileModelUpdateRpt.field24;	// Postal Code
		PostalCode4					= FileImport_FileModelUpdateRpt.field25;	// Postal Code+4
		Gender						= FileImport_FileModelUpdateRpt.field26;	// Gender
		BirthDateString				= FileImport_FileModelUpdateRpt.field27;	// Patient DOB
		AgeString					= FileImport_FileModelUpdateRpt.field28;	// Patient Age
		CDCClusterCode				= FileImport_FileModelUpdateRpt.field29;	// CDC Cluster Code
		LocalPrimaryEnzymePattern 	= FileImport_FileModelUpdateRpt.field30;	// Local Secondary Enzyme Pattern
		LocalSecondaryEnzymePattern	= FileImport_FileModelUpdateRpt.field31;	// Local Primary Enzyme Pattern
		Region						= FileImport_FileModelUpdateRpt.field32;	// Region

		if (AgeString.trim().length() == 0) 
			{AgeString = "0";}
		else
	    	{Age = Integer.parseInt( AgeString );};

		ConvertDates();
		GetAllData();
	}

/*
 * Add or Update the RESULT record in the database.
 * 
 * 		Use the ACCESSION # and ORGANISM field to search for the record in the database
 * 		If the record exists on the database, update it with the data on the input record.
 * 	ELSE
 * 		If the record DOES NOT EXIST in the database
 * 		Use the ACCESSION # field to search for the record in the database record
 * 			If the record EXISTS in the database, update it
 * 				with the data on the input record
 * 		ELSE
 * 			If the record DOES NOT EXIST in the database, Add it to the database
 * 
 * NOTE!
 * 		If the input record has a blank organism field, check to see if a record with
 * 		the same accession number exists in the database.  If it does, update it only if
 * 		record in the database also has a blank organism field.  Otherwise, if the database
 * 		record has a value other than blank in the organism field, do not add a new record
 * 		with a blank organism for that accession number.  A blank organism record can
 * 		exist in the database if the organism hasn't yet been identified.  If the organism 
 * 		is later identified, the database record for that accession number will be updated
 * 		with the organism information. 
 */		
	public static void GetAllData() throws SQLException
	{
		resultPK = 0;
		idOrgRecordExists = "N";
		idBlankRecordExists = "N";
		
		CheckForIdOrganismResultsRecord();
		CheckForIdResultsRecord();
		
		if (idOrgRecordExists == "N" && idBlankRecordExists == "N")
			{AddResultsRecord();}

		if (idOrgRecordExists == "Y" && idBlankRecordExists == "Y")
			{UpdateResultsRecord();}
		
		if (idOrgRecordExists == "N")
			if (idBlankRecordExists == "Y")
				if (dBOrganism.compareTo(" ") == 0)
					{UpdateBlankResultsRecord();}
		
		if (idOrgRecordExists == "N")
			if (idBlankRecordExists == "Y")
				if (dBOrganism.compareTo(" ") != 0)
					if (Organism.compareTo(" ") !=0)  //Do not add a record with a blank organism if non-blank record exists
						{AddResultsRecord();}
		
		AddOrUpdateDemographicsRecord();
		AddOrUpdateSubmitterRecord();

		DBUpdate.connection.commit();
	}

	public static void CheckForIdOrganismResultsRecord() throws SQLException
	{
		sql1 = "SELECT LAB_SPECIMEN_ID, ID, ORGANISM " +
				"from CIFOR_LAB_RESULT WHERE LAB_SPECIMEN_ID = ? and ORGANISM = ?" ;	// Check if the record exists
			PreparedStatement pStmt1 = DBUpdate.connection.prepareStatement(sql1);
			pStmt1.setString(1, LabSpecimenID);
			pStmt1.setString(2, Organism);
			ResultSet rset1 = pStmt1.executeQuery();
				if (rset1.next())	// Record Exists, so do an update to it
					{
						idOrgRecordExists = "Y";
						resultPK = rset1.getInt(2);
						dBOrganism = rset1.getString(3);
						DBUpdated = "Y";
					}
				else
					{
						idOrgRecordExists = "N";
						DBUpdated = "Y";
					}
				
				rset1.close();
				pStmt1.close();
	}
	
	public static void CheckForIdResultsRecord() throws SQLException
	{
		sql1 = "SELECT LAB_SPECIMEN_ID, ID, ORGANISM " +
				"from CIFOR_LAB_RESULT WHERE LAB_SPECIMEN_ID = ?" ;	// Check if the record exists
			PreparedStatement pStmt1 = DBUpdate.connection.prepareStatement(sql1);
			pStmt1.setString(1, LabSpecimenID);
			ResultSet rset1 = pStmt1.executeQuery();
				if (rset1.next())	// Record Exists, so do an update to it
					{
						idBlankRecordExists = "Y";
						resultPK = rset1.getInt(2);
						dBOrganism = rset1.getString(3);
					}
				else
					{
						idBlankRecordExists = "N";
					}
				
				rset1.close();
				pStmt1.close();
	}
	
	/*
	 * Update the existing results record
	 * 
	 */
	public static void UpdateResultsRecord() throws SQLException
	{
		FileImport_ReadInputFile.recordChangedCounter ++;
		
		sql2 = 	"UPDATE CIFOR_LAB_RESULT 	set " +				// Update the Results record
				"SUBMITTER_SPECIMEN_ID			= ?, " +
				"SPECIMEN_SOURCE				= ?, " +
				"DATE_COLLECTED					= ?, " +
				"DATE_RECEIVED					= ?, " +
				"DATE_REPORTED					= ?, " +
		   		"PRIMARY_TEST 					= ?, " +
		   		"ORGANISM 						= ?, " +
		   		"CDC_PRIMARY_ENZYME_PATTERN 	= ?, " +
		   		"CDC_SECONDARY_ENZYME_PATTERN 	= ?, " +
		   		"OTHER_RESULT 					= ?, " +
		   		"EPI_CASE_ID					= ?, " +
		   		"PATIENT_ID						= ?, " +
		   		"CDC_CLUSTER_CODE		 		= ?, " +
		   		"LOCAL_PRIMARY_ENZYME_PATTERN 	= ?, " +
		   		"LOCAL_SECONDARY_ENZYME_PATTERN	= ?, " +
		   		"REGION					 		= ? " +
		   		"WHERE LAB_SPECIMEN_ID = ? AND ORGANISM = ?";

			PreparedStatement 	pStmt2 = DBUpdate.connection.prepareStatement(sql2);
			pStmt2.setString 	(1, SubmitterSpecimenID);
			pStmt2.setString	(2, SpecimenSource);
			pStmt2.setDate		(3, DateCollected);
			pStmt2.setDate		(4, DateReceived);
			pStmt2.setDate		(5, DateReported);
			pStmt2.setString	(6, PrimaryTest);
			pStmt2.setString	(7, Organism);
			pStmt2.setString	(8, CDCPrimaryEnzymePattern);
			pStmt2.setString	(9, CDCSecondaryEnzymePattern);
			pStmt2.setString	(10, OtherResult);
			pStmt2.setString	(11, EPICaseID);
			pStmt2.setString	(12, PatientID);
			pStmt2.setString	(13, CDCClusterCode);
			pStmt2.setString	(14, LocalPrimaryEnzymePattern);
			pStmt2.setString	(15, LocalSecondaryEnzymePattern);
			pStmt2.setString	(16, Region);
			pStmt2.setString	(17, LabSpecimenID);
			pStmt2.setString	(18, Organism);
			
			pStmt2.executeUpdate();
			pStmt2.close();
	}	
	
	/*
	 * Update an existing results record that has a BLANK organism
	 * 
	 */
	public static void UpdateBlankResultsRecord() throws SQLException
	{
		FileImport_ReadInputFile.recordChangedCounter ++;
		
		sql2 = 	"UPDATE CIFOR_LAB_RESULT 	set " +				// Update the Results record
				"SUBMITTER_SPECIMEN_ID			= ?, " +
				"SPECIMEN_SOURCE				= ?, " +
				"DATE_COLLECTED					= ?, " +
				"DATE_RECEIVED					= ?, " +
				"DATE_REPORTED					= ?, " +
		   		"PRIMARY_TEST 					= ?, " +
		   		"ORGANISM 						= ?, " +
		   		"CDC_PRIMARY_ENZYME_PATTERN 	= ?, " +
		   		"CDC_SECONDARY_ENZYME_PATTERN 	= ?, " +
		   		"OTHER_RESULT 					= ?, " +
		   		"EPI_CASE_ID					= ?, " +
		   		"PATIENT_ID						= ?, " +
		   		"CDC_CLUSTER_CODE		 		= ?, " +
		   		"LOCAL_PRIMARY_ENZYME_PATTERN 	= ?, " +
		   		"LOCAL_SECONDARY_ENZYME_PATTERN	= ?, " +
		   		"REGION					 		= ? " +
		   		"WHERE LAB_SPECIMEN_ID = ?";

			PreparedStatement 	pStmt2 = DBUpdate.connection.prepareStatement(sql2);
				pStmt2.setString 	(1, SubmitterSpecimenID);
				pStmt2.setString	(2, SpecimenSource);
				pStmt2.setDate		(3, DateCollected);
				pStmt2.setDate		(4, DateReceived);
				pStmt2.setDate		(5, DateReported);
				pStmt2.setString	(6, PrimaryTest);
				pStmt2.setString	(7, Organism);
				pStmt2.setString	(8, CDCPrimaryEnzymePattern);
				pStmt2.setString	(9, CDCSecondaryEnzymePattern);
				pStmt2.setString	(10, OtherResult);
				pStmt2.setString	(11, EPICaseID);
				pStmt2.setString	(12, PatientID);
				pStmt2.setString	(13, LabSpecimenID);
				pStmt2.setString	(14, CDCClusterCode);
				pStmt2.setString	(15, LocalPrimaryEnzymePattern);
				pStmt2.setString	(16, LocalSecondaryEnzymePattern);
				pStmt2.setString	(17, Region);

			pStmt2.executeUpdate();
			pStmt2.close();
	}			

/*
 * Add a new results record
 * 
 */
	public static void AddResultsRecord() throws SQLException
	{
		FileImport_ReadInputFile.recordAddedCounter ++;
	
		String sql4 = "INSERT INTO CIFOR_LAB_RESULT " +
		   "(ID, " +
			"LAB_SPECIMEN_ID, " +
			"SUBMITTER_SPECIMEN_ID, " +
			"SPECIMEN_SOURCE, " +
			"DATE_COLLECTED, " +
			"DATE_RECEIVED, " +
			"DATE_REPORTED, " +
			"PRIMARY_TEST, " +
			"ORGANISM, " +
			"CDC_PRIMARY_ENZYME_PATTERN, " +
			"CDC_SECONDARY_ENZYME_PATTERN, " +
			"OTHER_RESULT, " +
			"DEMOGRAPHICS_ID, " +
			"EPI_CASE_ID, " +
			"PATIENT_ID, " +
			"SUBMITTER_ID, " +
			"CDC_CLUSTER_CODE, " +
			"LOCAL_PRIMARY_ENZYME_PATTERN, " +
			"LOCAL_SECONDARY_ENZYME_PATTERN, " +
			"REGION) " +
		"VALUES " +
			"(CIFOR_LAB_RESULT_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CIFOR_LAB_RESULT_SEQ.NEXTVAL, ?, ?, CIFOR_LAB_RESULT_SEQ.NEXTVAL, ?, ?, ? ,? )";
			
		PreparedStatement 	pStmt4 = DBUpdate.connection.prepareStatement(sql4);
			pStmt4.setString 	(1, LabSpecimenID);
			pStmt4.setString 	(2, SubmitterSpecimenID);
			pStmt4.setString	(3, SpecimenSource);
			pStmt4.setDate		(4, DateCollected);
			pStmt4.setDate		(5, DateReceived);
			pStmt4.setDate		(6, DateReported);
			pStmt4.setString	(7, PrimaryTest);
			pStmt4.setString	(8, Organism);
			pStmt4.setString	(9, CDCPrimaryEnzymePattern);
			pStmt4.setString	(10, CDCSecondaryEnzymePattern);
			pStmt4.setString	(11, OtherResult);
			//pStmt4.setString	(12, DemographicsID);
			pStmt4.setString	(12, EPICaseID);
			pStmt4.setString	(13, PatientID);
			//pStmt4.setString	(15, SubmitterID);
			pStmt4.setString	(14, CDCClusterCode);
			pStmt4.setString	(15, LocalPrimaryEnzymePattern);
			pStmt4.setString	(16, LocalSecondaryEnzymePattern);
			pStmt4.setString	(17, Region);
							
		pStmt4.executeUpdate();
		pStmt4.close();
	}

	/*
	 *  DEMOGRAPHICS Update.  Check to see if the record exists, then update if the record	
	 *                        exists, or insert a new record if not found.
	 * 	
	 */
		public static void AddOrUpdateDemographicsRecord() throws SQLException
		{
			String sql1a = "SELECT ID FROM CIFOR_DEMOGRAPHICS WHERE ID = ?";
			PreparedStatement pStmt1a = DBUpdate.connection.prepareStatement(sql1a);
			
			pStmt1a.setInt(1, resultPK);
			ResultSet rset2 = pStmt1a.executeQuery();
			
			if (rset2.next())		// Record Exists, so do an update to it
				{
					String sql3 = "UPDATE CIFOR_DEMOGRAPHICS 	set " +		// Update the Demograpics record
								   		"PATIENT_ID				= ?, " +
										"FIRST_NAME 			= ?, " +
										"MIDDLE_NAME			= ?, " +
										"LAST_NAME				= ?, " +
										"MULTIPLE_UNIT			= ?, " +
								   		"STREET_ADDRESS			= ?, " +
								   		"CITY			 		= ?, " +
								   		"COUNTY					= ?, " +
								   		"STATE					= ?, " +
								   		"POSTAL_CODE			= ?, " +
								   		"POSTAL_CODE_PLUS_4		= ?, " +
								   		"GENDER					= ?, " +
								   		"BIRTH_DATE 			= ?, " +
								   		"AGE					= ?, " +
								   		"AGE_MONTHS				= ? " +
								   	"WHERE ID = ?" ;	
					
					PreparedStatement 	pStmt3 = DBUpdate.connection.prepareStatement(sql3);
						pStmt3.setString	(1, PatientID);
						pStmt3.setString	(2, FirstName);
						pStmt3.setString	(3, MiddleName);
						pStmt3.setString	(4, LastName);
						pStmt3.setString	(5, MultipleUnit);
						pStmt3.setString	(6, StreetAddress);
						pStmt3.setString	(7, City);
						pStmt3.setString	(8, County);
						pStmt3.setString	(9, State);
						pStmt3.setString	(10, PostalCode);
						pStmt3.setString	(11, PostalCode4);
						pStmt3.setString	(12, Gender);
						pStmt3.setDate		(13, DateOfBirth);
						pStmt3.setInt		(14, Age);
						pStmt3.setInt		(15, AgeMonths);
						//pStmt3.setInt		(16, demographicsPK);
						pStmt3.setInt		(16, resultPK);
							
					pStmt3.executeUpdate();
					pStmt3.close();
					rset2.close();
					pStmt1a.close();
				}
			else
				{
					pStmt1a.close();
					String sql5 = "INSERT INTO CIFOR_DEMOGRAPHICS " +
											   "(ID, " +
												"PATIENT_ID, " +
												"FIRST_NAME, " +
												"MIDDLE_NAME, " +
												"LAST_NAME, " +
												"MULTIPLE_UNIT, " +
												"STREET_ADDRESS, " +
												"CITY, " +
												"COUNTY, " +
												"STATE, " +
												"POSTAL_CODE, " +
												"POSTAL_CODE_PLUS_4, " +
												"GENDER, " +
												"BIRTH_DATE, " +
												"AGE, " +
												"AGE_MONTHS) " +
											"VALUES " +
											    "(CIFOR_LAB_RESULT_SEQ.CURRVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
											
					PreparedStatement 	pStmt5 = DBUpdate.connection.prepareStatement(sql5);
										
										pStmt5.setString	(1, PatientID);
										pStmt5.setString	(2, FirstName);
										pStmt5.setString	(3, MiddleName);
										pStmt5.setString	(4, LastName);
										pStmt5.setString	(5, MultipleUnit);
										pStmt5.setString	(6, StreetAddress);
										pStmt5.setString	(7, City);
										pStmt5.setString	(8, County);
										pStmt5.setString	(9, State);
										pStmt5.setString	(10, PostalCode);
										pStmt5.setString	(11, PostalCode4);
										pStmt5.setString	(12, Gender);
										pStmt5.setDate		(13, DateOfBirth);
										pStmt5.setInt		(14, Age);
										pStmt5.setInt		(15, AgeMonths);
					pStmt5.executeUpdate();
					pStmt5.close();
					rset2.close();
					pStmt1a.close();
				}
		}
		
		/*
		 *  SUBMITTER Update.  Check to see if the record exists, then update if the record
		 *                     exists, or insert a new record if not found.
		 */
		public static void AddOrUpdateSubmitterRecord() throws SQLException
		{
			String submitterQuery = "SELECT ID FROM CIFOR_SUBMITTER WHERE ID = ?";
			PreparedStatement submitterQPS = DBUpdate.connection.prepareStatement(submitterQuery);
			
			submitterQPS.setInt(1, resultPK);
			ResultSet submitterRset = submitterQPS.executeQuery();
			
			if (submitterRset.next())	// Record Exists, so do an update to it
			{
				String submitterSQL = "UPDATE CIFOR_SUBMITTER 	set " +				// Update the Submitter record
											"SUBMITTER_ID_NBR		= ?, " +
									   		"SUBMITTER_NAME			= ?, " +
									   		"SUBMITTER_STATE		= ? " +
									   	"WHERE ID = ?" ;	
		
				PreparedStatement 	submitterPS = DBUpdate.connection.prepareStatement(submitterSQL);
				submitterPS.setString	(1, SubmitterID);
				submitterPS.setString	(2, SubmitterName);
				submitterPS.setString	(3, SubmitterState);
				//submitterPS.setInt		(4, submitterPK);
				submitterPS.setInt		(4, resultPK);
				
				submitterPS.executeUpdate();
				submitterPS.close();
				submitterRset.close();
				submitterQPS.close();
			}
			else
			{
				String subInsertSQL = "INSERT INTO CIFOR_SUBMITTER " +
										   "(ID, " +
											"SUBMITTER_ID_NBR, " +
											"SUBMITTER_NAME, " +
											"SUBMITTER_STATE) " +
										"VALUES " +
										    "(CIFOR_LAB_RESULT_SEQ.CURRVAL, ?, ?, ? ) ";

				PreparedStatement 	psSubInsert = DBUpdate.connection.prepareStatement(subInsertSQL);
									
				psSubInsert.setString	(1, SubmitterID);
				psSubInsert.setString	(2, SubmitterName);
				psSubInsert.setString	(3, SubmitterState);
		
				psSubInsert.executeUpdate();
				psSubInsert.close();
				submitterRset.close();
				submitterQPS.close();
			}
		}
	
	/*
	 * Convert string dates to java.sql.Date format
	 * 
	 */
	public static void ConvertDates()
	{
		if (FileImport_FileModelUpdateRpt.field4.trim().length() != 0)
			{
				String CollectedString = DateCollectedString;
				SimpleDateFormat cdf = new SimpleDateFormat("MMddyyyy");
				java.util.Date collectedDate;
					try
						{
						collectedDate = cdf.parse(CollectedString);
							cdf.applyPattern("yyyy-MM-dd");
							CollectedString = cdf.format(collectedDate);
						}
					catch (Exception e){
						e.printStackTrace();
						}
		
					Date DColl = java.sql.Date.valueOf(CollectedString);
					DateCollected = DColl;
			}
			
		String ReceivedString = DateReceivedString;
		SimpleDateFormat rdf = new SimpleDateFormat("MMddyyyy");
		java.util.Date receivedDate;
			try
				{
				receivedDate = rdf.parse(ReceivedString);
					rdf.applyPattern("yyyy-MM-dd");
					ReceivedString = rdf.format(receivedDate);
				}
			catch (Exception e){
				e.printStackTrace();
				}
			java.sql.Date DRcvd = java.sql.Date.valueOf(ReceivedString);
			DateReceived = DRcvd;

		String ReportedString = DateReportedString;
		SimpleDateFormat rptdf = new SimpleDateFormat("MMddyyyy");
		java.util.Date reportedDate;
			try
				{
				reportedDate = rptdf.parse(ReportedString);
					rptdf.applyPattern("yyyy-MM-dd");
					ReportedString = rptdf.format(reportedDate);
				}
			catch (Exception e){
				e.printStackTrace();
				}
			java.sql.Date DRptd = java.sql.Date.valueOf(ReportedString);
			DateReported = DRptd;

			if (FileImport_FileModelUpdateRpt.field27.trim().length() != 0)
				{
					String DOBString = BirthDateString;
					SimpleDateFormat dobf = new SimpleDateFormat("MMddyyyy");
					java.util.Date dobDate;
						try
							{
							dobDate = dobf.parse(DOBString);
								dobf.applyPattern("yyyy-MM-dd");
								DOBString = dobf.format(dobDate);
							}
						catch (Exception e){
							e.printStackTrace();
							}
						java.sql.Date DOBd = java.sql.Date.valueOf(DOBString);
						DateOfBirth = DOBd;
				}
	}	
	
	/*
	 * Program Variables
	 * 
	 */
	static String 			LabSpecimenID;			
	static String			SubmitterSpecimenID;	
	static String			SpecimenSource;
	static java.sql.Date	DateCollected;
	static java.sql.Date	DateReceived;
	static java.sql.Date	DateReported;
	static java.sql.Date	DateOfBirth;
	static String			PrimaryTest;
	static String			Organism;
	static String			CDCPrimaryEnzymePattern;
	static String			CDCSecondaryEnzymePattern;
	static String			OtherResult;
	static String			CDCClusterCode;
	static String			LocalPrimaryEnzymePattern;
	static String			LocalSecondaryEnzymePattern;
	static String			Region;
	
	static String			DemographicsID; 
	static String			EPICaseID;
	static String			PatientID;
	
	public static String	DateCollectedString;
	static String			DateReceivedString;
	static String			DateReportedString;
	static String			BirthDateString;
	
	static String			SubmitterID;
	static String			FirstName;
	static String			MiddleName;
	static String			LastName;
	static String			MultipleUnit;
	static String			StreetAddress;
	static String			City;
	static String			County;
	static String			State;
	static String			PostalCode;
	static String			PostalCode4;
	static String			Gender;
	static java.sql.Date	BirthDate;
	static String			AgeString;
	static int				Age; 
	static int				AgeMonths;
	
	static int				RecID;
	static int				resultPK;
	static int				demographicsPK;
	static int				submitterPK;
	
	static String			dbOrganism;
	//static String			dbPrimaryEnzyme;
	//static String			dbSecondaryEnzyme;
	//static String			dbOtherResult;
	static String			dbUpdate;
	
	static String			SubmitterName;
	static String			SubmitterState;
	
	static String			sql1;
	static String			sql2;
	static String			sql3;
	static String			sql4;

	static String			recordExists;
	static String			DBUpdated;
	
	static String			idOrgRecordExists;
	static String			idBlankRecordExists;
	
	static String			dBOrganism;
	static String			blankOrganism = "";
	
}
