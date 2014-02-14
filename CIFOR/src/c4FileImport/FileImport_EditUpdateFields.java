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

package c4FileImport;

import c4Utilities.DateFormatUtility;
/**
*
* @author jonesg1
*/

/*
 * This program will edit the update fields as part of the file import
 */
public class FileImport_EditUpdateFields 
{
	public static int recordErrorCounter = 0;
	public static int recordSkippedCounter = 0;
	public static String errorFlag = null;
	public static String skipFlag = null;
	public static String errorDescription;
	public static int fieldLength = 0;
	
	/*
	 * Setup and edit the input fields
	 */
	public static void EditInputFields()
	{
		String accessionError 		= "";
		String dateCollectedError 	= "";
		String dateCollectedWarning	= "";
		String dateReceivedError 	= "";
		String dateReportedError 	= "";		
		String dateReportedWarning 	= "";
		String patientDOBWarning	= "";
		String patientDOBError		= "";
		String patientAgeWarning	= "";
		String patientAgeError		= "";
		String dateCollRangeError	= "";
		String dateRcvdRangeError	= "";
		String dateRptdRangeError	= "";
		String PriTestLengthError 	= "";
		String PriTestErrorMsg		= "";
		String OrganismLengthError	= "";
		String OrganismErrorMsg 		= "";
		String CDCClusterCodeLengthError= "";
		String CDCClusterCodeErrorMsg	= "";
		String CDCPriEnzymeLengthError 	= "";
		String CDCPriEnzymeErrorMsg		= "";
		String CDCSecEnzymeLengthError 	= "";
		String CDCSecEnzymeErrorMsg		= "";
		String LocalPriEnzymeLengthError= "";
		String LocalPriEnzymeErrorMsg	= "";
		String LocalSecEnzymeLengthError= "";
		String LocalSecEnzymeErrorMsg	= "";
		String OtherLengthError 		= "";
		String OtherErrorMsg			= "";
		String PIDLengthError			= "";
		String PIDErrorMsg				= "";
		String RegionLengthError		= "";
		String RegionErrorMsg			= "";
		String fNameLengthError 	= "";
		String fNameErrorMsg		= "";
		String mNameLengthError 	= "";
		String mNameErrorMsg		= ""; 
		String lNameLengthError 	= "";
		String lNameErrorMsg		= "";
		String mUnitLengthError 	= "";
		String mUnitErrorMsg		= "";
		String addrLengthError 		= "";
		String addrErrorMsg			= "";
		String cityLengthError 		= "";
		String cityErrorMsg			= "";
		String stLengthError 		= "";
		String stErrorMsg			= "";
		String countyLengthError 	= "";
		String countyErrorMsg		= "";
		String zipLengthError 		= "";
		String zipErrorMsg			= "";
		String zip4LengthError 		= "";
		String zip4ErrorMsg			= "";
		String subIDLengthError 	= "";
		String subIDErrorMsg		= "";
		String subNameLengthError 	= "";
		String subNameErrorMsg		= "";
		String subStLengthError 	= "";
		String subStErrorMsg		= "";
		String genderError			= "";
		String genderErrorMsg		= "";
		
		errorFlag = "false";
		errorDescription = " ";

		/*
		 * Validate the Accession number (not blank)
		 */
		if (FileImport_FileModelUpdateRpt.field1.trim().length() == 0) {accessionError = "Invalid Accession No. ";}

		/*
		 * Validate the Date Collected (valid date, blank OK-set warning, but ignore it for now)
		 */
		if (FileImport_FileModelUpdateRpt.field4.trim().length() == 0)
			{dateCollectedWarning = "true";}
		else if (dateCollectedWarning != "true")			// Errors exist, so set error flag
			{
				DateFormatUtility.inputDate = FileImport_FileModelUpdateRpt.field4;
				DateFormatUtility.editDate();
				
				DateFormatUtility.currentString = FileImport_GetDateRange.inputCurrentDate.getText();
				DateFormatUtility.beginningString = FileImport_GetDateRange.inputBeginningDate.getText();
				DateFormatUtility.dateCompare();
				
				java.sql.Date newInputDate = java.sql.Date.valueOf(DateFormatUtility.inputString);
				java.sql.Date newCurrentDate = java.sql.Date.valueOf(DateFormatUtility.currentString);
				java.sql.Date newBeginningDate = java.sql.Date.valueOf(DateFormatUtility.beginningString);
				dateCollRangeError = "false";
			}
			if (DateFormatUtility.inputDateError == "true")
				{
				dateCollectedError = " /Invalid Collection Date ";
				}
				
		/*
		 * Validate the Date Received (valid date, not blank)
		 */
		skipFlag = "false";
		
		DateFormatUtility.inputDate = FileImport_FileModelUpdateRpt.field5;
		DateFormatUtility.editDate();
		if (DateFormatUtility.inputDateError == "true")
			{
				dateReceivedError = "Invalid Date Received ";
			}
		else if (DateFormatUtility.inputDateError != "true")	// No errors, so compare Received Date to date range
		{	
			DateFormatUtility.inputString = FileImport_FileModelUpdateRpt.field5;
			DateFormatUtility.currentString = FileImport_GetDateRange.inputCurrentDate.getText();
			DateFormatUtility.beginningString = FileImport_GetDateRange.inputBeginningDate.getText();
			
			DateFormatUtility.dateCompare();
			
			java.sql.Date newInputDate = java.sql.Date.valueOf(DateFormatUtility.inputString);
			java.sql.Date newCurrentDate = java.sql.Date.valueOf(DateFormatUtility.currentString);
			java.sql.Date newBeginningDate = java.sql.Date.valueOf(DateFormatUtility.beginningString);
			dateRcvdRangeError = "false";
			
			if 		(newInputDate.before(newBeginningDate)) 	dateRcvdRangeError 	= "true";
			else if (newInputDate.after(newCurrentDate))		dateRcvdRangeError	= "true";
		}

		/*
		 * Validate the Date Reported (valid date. Blank Date Reported is not OK, but the logic
		 * for a blank date is in the edit for the Patient DOB if it's needed at a later time)
		 */
		DateFormatUtility.inputDate = FileImport_FileModelUpdateRpt.field6;
		DateFormatUtility.editDate();
		if (DateFormatUtility.inputDateError == "true")
			{
				dateReportedError = "Invalid Date Reported ";
			}
		else if (DateFormatUtility.inputDateError != "true")	// No errors, so compare Reported Date to date range
		{	
			DateFormatUtility.inputString = FileImport_FileModelUpdateRpt.field6;
			DateFormatUtility.currentString = FileImport_GetDateRange.inputCurrentDate.getText();
			DateFormatUtility.beginningString = FileImport_GetDateRange.inputBeginningDate.getText();
			
			DateFormatUtility.dateCompare();
			
			java.sql.Date newInputDate = java.sql.Date.valueOf(DateFormatUtility.inputString);
			java.sql.Date newCurrentDate = java.sql.Date.valueOf(DateFormatUtility.currentString);
			java.sql.Date newBeginningDate = java.sql.Date.valueOf(DateFormatUtility.beginningString);

			dateRptdRangeError = "false";
			if 		(newInputDate.before(newBeginningDate)) 	dateRptdRangeError 	= "true";
			else if (newInputDate.after(newCurrentDate))		dateRptdRangeError	= "true";
		}

		/*
		 * Validate the Patient DOB (valid date, blank OK-set warning, but ignore it for now)
		 */
		if (FileImport_FileModelUpdateRpt.field27.trim().length() == 0)
			{patientDOBWarning = "true";}
		else if (patientDOBWarning != "true") 
			{
				DateFormatUtility.inputDate = FileImport_FileModelUpdateRpt.field27; 
				DateFormatUtility.editDate();
			}
			if (DateFormatUtility.inputDateError == "true")
				{
					patientDOBError = "Invalid Patient DOB";
				}

		/*
		 * Validate the Patient Age (valid age, blank OK)
		 */
		if (FileImport_FileModelUpdateRpt.field28.trim().length() == 0)
			{
				patientAgeWarning = "false";		// Age = blank, no error
			}
		else if (patientAgeWarning != "false") 
			{
			try
				{
					Integer.parseInt(FileImport_FileModelUpdateRpt.field28);
				}
					catch (NumberFormatException exc)
					{
						patientAgeError = " /Invalid Patient Age";
					}
			}

		/*
		 * Validate the length of the test fields
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field7.length();
		if (fieldLength > 60)
		{
			PriTestLengthError = "true";
			PriTestErrorMsg = " /Primary Test Field is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field8.length();
		if (fieldLength > 80)
		{
			OrganismLengthError = "true";
			OrganismErrorMsg = " /Organism Field is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field9.length();
		if (fieldLength > 30)
		{
			CDCPriEnzymeLengthError = "true";
			CDCPriEnzymeErrorMsg = " /Primary Enzyme Pattern is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field10.length();
		if (fieldLength > 60)
		{
			CDCSecEnzymeLengthError = "true";
			CDCSecEnzymeErrorMsg = " /Secondary Enzyme Pattern is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field11.length();
		if (fieldLength > 80)
		{
			OtherLengthError = "true";
			OtherErrorMsg = " /Other Result is too long";
		}

		/*
		 * Validate the length of the Submitter fields
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field12.length();
		if (fieldLength > 20)
		{
			subIDLengthError = "true";
			subIDErrorMsg = " /Submitter ID is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field13.length();
		if (fieldLength > 50)
		{
			subNameLengthError = "true";
			subNameErrorMsg = " /Submitter Name is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field14.length();
		if (fieldLength > 2)
		{
			subStLengthError = "true";
			subStErrorMsg = " /Submitter State Abbrev is too long";
		}
		
		/*
		 * Validate the length of the Patient ID field
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field15.length();
		if (fieldLength > 20)
		{
			PIDLengthError = "true";
			PIDErrorMsg = " /Patient ID Field is too long";
			//System.out.println("Field length error on record # " + C4FileImport_FileModelErrorRpt.field1);
		}
					
		/*
		 * Validate the Patient Name fields to make sure they not greater than 20,20,30 characters 
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field16.length();
		if (fieldLength > 20)
		{
			fNameLengthError = "true";
			fNameErrorMsg = " /Patient First Name Field is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field17.length();
		if (fieldLength > 20)
		{
			mNameLengthError = "true";
			mNameErrorMsg = " /Patient Middle Name Field is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field18.length();
		if (fieldLength > 20)
		{
			lNameLengthError = "true";
			lNameErrorMsg = " /Patient Last Name Field is too long";
		}
		
		/*
		 * Validate the Patient Address fields to make sure they not greater than the DB size 
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field19.length();
		if (fieldLength > 30)
		{
			mUnitLengthError = "true";
			mUnitErrorMsg = " /Patient MultUnit Address is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field20.length();
		if (fieldLength > 30)
		{
			addrLengthError = "true";
			addrErrorMsg = " /Patient Address is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field21.length();
		if (fieldLength > 30)
		{
			cityLengthError = "true";
			cityErrorMsg = " /Patient City is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field22.length();
		if (fieldLength > 2)
		{
			stLengthError = "true";
			stErrorMsg = " /Patient State is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field23.length();
		if (fieldLength > 30)
		{
			countyLengthError = "true";
			countyErrorMsg = " /Patient County Field is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field24.length();
		if (fieldLength > 5)
		{
			zipLengthError = "true";
			zipErrorMsg = " /Patient Zip is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field25.length();
		if (fieldLength > 4)
		{
			zip4LengthError = "true";
			zip4ErrorMsg = " /Patient Zip4 is too long";
		}
		
		/*
		 * Validate the length of the Patient Gender field
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelUpdateRpt.field26.length();
		if (fieldLength > 1)
		{
			genderError = "true";
			genderErrorMsg = " /Patient Gender is too long";
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field29.length();
		if (fieldLength > 20)
		{
			CDCClusterCodeLengthError = "true";
			CDCClusterCodeErrorMsg = vCDCClusterCodeErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field30.length();
		if (fieldLength > 30)
		{
			LocalPriEnzymeLengthError = "true";
			LocalPriEnzymeErrorMsg = vLocalPriEnzymeErrorMsg;
		}

		fieldLength = FileImport_FileModelErrorRpt.field31.length();
		if (fieldLength > 60)
		{
			LocalSecEnzymeLengthError = "true";
			LocalSecEnzymeErrorMsg = vLocalSecEnzymeErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field32.length();
		if (fieldLength > 30)
		{
			RegionLengthError = "true";
			RegionErrorMsg = vRegionErrorMsg;
		}

		/*
		 * Check date errors and set errorDescription field
		 */
		if 		(dateCollectedError 		!= "") 		{errorFlag = "true";}
		else if (dateReceivedError  		!= "") 		{errorFlag = "true";}
		else if (dateReportedError  		!= "") 		{errorFlag = "true";}
		else if (accessionError     		!= "")		{errorFlag = "true";}
		else if (patientDOBError			!= "")		{errorFlag = "true";}
		else if (patientAgeError			!= "")		{errorFlag = "true";}
		else if (PriTestLengthError			!= "")		{errorFlag = "true";}
		else if (OrganismLengthError  		!= "")		{errorFlag = "true";}
		else if (CDCPriEnzymeLengthError 	!= "")		{errorFlag = "true";}
		else if (CDCSecEnzymeLengthError 	!= "")		{errorFlag = "true";}
		else if (LocalPriEnzymeLengthError 	!= "")		{errorFlag = "true";}
		else if (LocalSecEnzymeLengthError	!= "")		{errorFlag = "true";}
		else if (OtherLengthError			!= "")		{errorFlag = "true";}
		else if (PIDErrorMsg				!= "")		{errorFlag = "true";}
		else if (fNameLengthError			!= "")		{errorFlag = "true";}
		else if (mNameLengthError			!= "")		{errorFlag = "true";}
		else if (lNameLengthError			!= "")		{errorFlag = "true";}
		else if (mUnitLengthError			!= "")		{errorFlag = "true";}
		else if (addrLengthError			!= "")		{errorFlag = "true";}
		else if (cityLengthError			!= "")		{errorFlag = "true";}
		else if (stLengthError				!= "")		{errorFlag = "true";}
		else if (countyLengthError			!= "")		{errorFlag = "true";}
		else if (zipLengthError				!= "")		{errorFlag = "true";}
		else if (zip4LengthError			!= "")		{errorFlag = "true";}
		else if (subIDLengthError			!= "")		{errorFlag = "true";}
		else if (subNameLengthError			!= "")		{errorFlag = "true";}
		else if (subStLengthError			!= "")		{errorFlag = "true";}
		else if (genderError				!= "")		{errorFlag = "true";}
		else if (CDCClusterCodeLengthError	!= "")		{errorFlag = "true";}
		else if (CDCClusterCodeErrorMsg		!= "")		{errorFlag = "true";}
		else if (RegionLengthError			!= "")		{errorFlag = "true";}
		else if (RegionErrorMsg				!= "")		{errorFlag = "true";}
	
		if  (errorFlag == "true")	{recordErrorCounter ++;}

		if (errorFlag != "true" && dateRptdRangeError == "true")
			{
				errorFlag = "true";
				recordSkippedCounter ++;
				skipFlag = "true";
			}	
	}
	
	/*
	 * Program Variables
	 *
	 * Define the error variable names for the Edit Report and then
	 * get the error descriptions from the properties file.
	 */
	private static String vAccessionError		= c4Utilities.CIFORProperties.vAccessionError;
	private static String vDateCollectedError	= c4Utilities.CIFORProperties.vDateCollectedError;
	private static String vDateReceivedError	= c4Utilities.CIFORProperties.vDateReceivedError;
	private static String vDateReportedError	= c4Utilities.CIFORProperties.vDateReportedError;
	private static String vPatientDOBError		= c4Utilities.CIFORProperties.vPatientDOBError;
	private static String vPatientAgeError		= c4Utilities.CIFORProperties.vPatientAgeError;
	private static String vPriTestErrorMsg		= c4Utilities.CIFORProperties.vPriTestErrorMsg;
	private static String vOrganismErrorMsg		= c4Utilities.CIFORProperties.vOrganismErrorMsg;
	private static String vCDCPriEnzymeErrorMsg	= c4Utilities.CIFORProperties.vCDCPriEnzymeErrorMsg;
	private static String vCDCSecEnzymeErrorMsg	= c4Utilities.CIFORProperties.vCDCSecEnzymeErrorMsg;
	private static String vLocalPriEnzymeErrorMsg	= c4Utilities.CIFORProperties.vLocalPriEnzymeErrorMsg;
	private static String vLocalSecEnzymeErrorMsg	= c4Utilities.CIFORProperties.vLocalSecEnzymeErrorMsg;
	private static String vOtherErrorMsg		= c4Utilities.CIFORProperties.vOtherErrorMsg;
	private static String vPIDErrorMsg			= c4Utilities.CIFORProperties.vPIDErrorMsg;
	private static String vFNameErrorMsg		= c4Utilities.CIFORProperties.vFNameErrorMsg;
	private static String vMNameErrorMsg		= c4Utilities.CIFORProperties.vMNameErrorMsg;
	private static String vLNameErrorMsg		= c4Utilities.CIFORProperties.vLNameErrorMsg;
	private static String vMUnitErrorMsg		= c4Utilities.CIFORProperties.vMUnitErrorMsg;
	private static String vAddrErrorMsg			= c4Utilities.CIFORProperties.vAddrErrorMsg;
	private static String vCityErrorMsg			= c4Utilities.CIFORProperties.vCityErrorMsg;
	private static String vStErrorMsg			= c4Utilities.CIFORProperties.vStErrorMsg;
	private static String vCountyErrorMsg		= c4Utilities.CIFORProperties.vCountyErrorMsg;
	private static String vZipErrorMsg			= c4Utilities.CIFORProperties.vZipErrorMsg;
	private static String vZip4ErrorMsg			= c4Utilities.CIFORProperties.vZip4ErrorMsg;
	private static String vSubIDErrorMsg		= c4Utilities.CIFORProperties.vSubIDErrorMsg;
	private static String vSubNameErrorMsg		= c4Utilities.CIFORProperties.vSubNameErrorMsg;
	private static String vSubStErrorMsg		= c4Utilities.CIFORProperties.vSubStErrorMsg;
	private static String vGenderErrorMsg		= c4Utilities.CIFORProperties.vGenderErrorMsg;
	private static String vCDCClusterCodeErrorMsg = c4Utilities.CIFORProperties.vCDCClusterCodeErrorMsg;
	private static String vRegionErrorMsg		= c4Utilities.CIFORProperties.vRegionErrorMsg;
}
