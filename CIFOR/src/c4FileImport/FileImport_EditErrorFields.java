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
 * This program performs the edit for error fields and the
 * eventual error tab on the import report
 */
public class FileImport_EditErrorFields 
{
	
/*
 * Setup the error fields and warning fields
 */

	public static void EditInputFields()
	{
		String accessionError 			= "";
		String dateCollectedWarning		= "";
		String dateCollectedError 		= "";
		String dateReceivedError 		= "";
		String dateReportedError 		= "";
		String patientDOBWarning		= "";
		String patientDOBError			= "";
		String patientAgeWarning		= "";
		String patientAgeError			= "";
		String PriTestLengthError 		= "";
		String PriTestErrorMsg			= "";
		String OrganismLengthError		= "";
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
		String fNameLengthError 		= "";
		String fNameErrorMsg			= "";
		String mNameLengthError 		= "";
		String mNameErrorMsg			= ""; 
		String lNameLengthError 		= "";
		String lNameErrorMsg			= "";
		String mUnitLengthError 		= "";
		String mUnitErrorMsg			= "";
		String addrLengthError 			= "";
		String addrErrorMsg				= "";
		String cityLengthError 			= "";
		String cityErrorMsg				= "";
		String stLengthError 			= "";
		String stErrorMsg				= "";
		String countyLengthError 		= "";
		String countyErrorMsg			= "";
		String zipLengthError 			= "";
		String zipErrorMsg				= "";
		String zip4LengthError 			= "";
		String zip4ErrorMsg				= "";
		String subIDLengthError 		= "";
		String subIDErrorMsg			= "";
		String subNameLengthError 		= "";
		String subNameErrorMsg			= "";
		String subStLengthError 		= "";
		String subStErrorMsg			= "";
		String genderError				= "";
		String genderErrorMsg			= "";
		
		errorFlag = "false";
		errorDescription = " ";
		
		/*
		 * Validate the Accesson Number (make sure it's not a blank field)
		 */
		if (FileImport_FileModelErrorRpt.field1.trim().length() == 0) {accessionError = vAccessionError	;}

		/*
		 * Validate the Date Collected field (must be a valid date, a blank Date Collected is
		 * OK, but set the warning flag and just ignore it for now)
		 */
		if (FileImport_FileModelErrorRpt.field4.trim().length() == 0)
			{dateCollectedWarning = "true";}
		else if (dateCollectedWarning != "true")
			{
				DateFormatUtility.inputDate = FileImport_FileModelErrorRpt.field4;
				DateFormatUtility.editDate();
			}
			if (DateFormatUtility.inputDateError == "true")
				{
				dateCollectedError = vDateCollectedError;
				}

		/*
		 * Validate the Date Received (Must be a valid date and not blank)
		 */		
		DateFormatUtility.inputDate = FileImport_FileModelErrorRpt.field5;
		DateFormatUtility.editDate();
		if (DateFormatUtility.inputDateError == "true")
			{
				dateReceivedError = vDateReceivedError;
			}
		
		/*
		 * Validate the Date Reported (Must be a valid date and not blank)
		 * 
		 * (If needed in the future, the logic for a blank date is in the
		 * Patient Date of Birth edit later in this program)
		 */
		DateFormatUtility.inputDate = FileImport_FileModelErrorRpt.field6; 
		DateFormatUtility.editDate();
		if (DateFormatUtility.inputDateError == "true")
			{
				dateReportedError = vDateReportedError;
			}
		
		/*
		 * Validate the Patient Date of Birth (Must be a valid date, a blank DOB is OK,
		 * but set a warning and ignore it for now)
		 */
		if (FileImport_FileModelErrorRpt.field27.trim().length() == 0)
			{patientDOBWarning = "false";		// DOB = blank, no error
			DateFormatUtility.inputDateError = "false";} //Set to false, true was carried over from elsewhere
		else if (patientDOBWarning != "false") 
			{
				DateFormatUtility.inputDate = FileImport_FileModelErrorRpt.field27; 
				DateFormatUtility.editDate();
			}
			if (DateFormatUtility.inputDateError == "true")
				{
					patientDOBError = vPatientDOBError;
				}
			
		/*
		 * Validate teh Patient Age (Valid numeric age, blank is OK)
		 */
		if (FileImport_FileModelErrorRpt.field28.trim().length() == 0)
			{
				patientAgeWarning = "false";		// Age = blank, no error
			}
		else if (patientAgeWarning != "false") 
			{
			try
				{
					Integer.parseInt(FileImport_FileModelErrorRpt.field28);
				}
					catch (NumberFormatException exc)
					{
						patientAgeError = vPatientAgeError;
					}
			}
		
		/*
		 * Validate the length of the Test Fields
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field7.length();
		if (fieldLength > 60)
		{
			PriTestLengthError = "true";
			PriTestErrorMsg = vPriTestErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field8.length();
		if (fieldLength > 80)
		{
			OrganismLengthError = "true";
			OrganismErrorMsg = vOrganismErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field9.length();
		if (fieldLength > 30)
		{
			CDCPriEnzymeLengthError = "true";
			CDCPriEnzymeErrorMsg = vCDCPriEnzymeErrorMsg;
		}

		fieldLength = FileImport_FileModelErrorRpt.field10.length();
		if (fieldLength > 60)
		{
			CDCSecEnzymeLengthError = "true";
			CDCSecEnzymeErrorMsg = vCDCSecEnzymeErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field11.length();
		if (fieldLength > 80)
		{
			OtherLengthError = "true";
			OtherErrorMsg = vOtherErrorMsg;
		}

		/*
		 * Validate the length of the Submitter Fields
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field12.length();
		if (fieldLength > 20)
		{
			subIDLengthError = "true";
			subIDErrorMsg = vSubIDErrorMsg	;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field13.length();
		if (fieldLength > 50)
		{
			subNameLengthError = "true";
			subNameErrorMsg = vSubNameErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field14.length();
		if (fieldLength > 2)
		{
			subStLengthError = "true";
			subStErrorMsg = vSubStErrorMsg;
		}

		/*
		 * Validate the length of the Patient ID Field
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field15.length();
		if (fieldLength > 20)
		{
			PIDLengthError = "true";
			PIDErrorMsg = vPIDErrorMsg;
		}
		
		/*
		 * Validate the length of the Patient Name Fields
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field16.length();
		if (fieldLength > 20)
		{
			fNameLengthError = "true";
			fNameErrorMsg = vFNameErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field17.length();
		if (fieldLength > 20)
		{
			mNameLengthError = "true";
			mNameErrorMsg = vMNameErrorMsg;
		}
	
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field18.length();
		if (fieldLength > 20)
		{
			lNameLengthError = "true";
			lNameErrorMsg = vLNameErrorMsg;
		}
		
		/*
		 * Validate the length of the Patient Address Fields 
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field19.length();
		if (fieldLength > 30)
		{
			mUnitLengthError = "true";
			mUnitErrorMsg = vMUnitErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field20.length();
		if (fieldLength > 30)
		{
			addrLengthError = "true";
			addrErrorMsg = vAddrErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field21.length();
		if (fieldLength > 30)
		{
			cityLengthError = "true";
			cityErrorMsg = vCityErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field22.length();
		if (fieldLength > 2)
		{
			stLengthError = "true";
			stErrorMsg = vStErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field23.length();
		if (fieldLength > 30)
		{
			countyLengthError = "true";
			countyErrorMsg = vCountyErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field24.length();
		if (fieldLength > 5)
		{
			zipLengthError = "true";
			zipErrorMsg = vZipErrorMsg;
		}
		
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field25.length();
		if (fieldLength > 4)
		{
			zip4LengthError = "true";
			zip4ErrorMsg = vZip4ErrorMsg;
		}
		
		/*
		 * Validate the length of the Patient Gender Field
		 */
		fieldLength = 0;
		fieldLength = FileImport_FileModelErrorRpt.field26.length();
		if (fieldLength > 1)
		{
			genderError = "true";
			genderErrorMsg = vGenderErrorMsg;
		}
		
		if (FileImport_FileModelErrorRpt.nbrFields > 28)
			{
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
			}
		
		/*
		 * Check the Error Flags and set the errorDescription Field for the report
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
		else if (PIDLengthError				!= "")		{errorFlag = "true";}
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
		
		errorDescription = (accessionError + dateCollectedError + dateReceivedError + 
							dateReportedError + patientDOBError + patientAgeError + PriTestErrorMsg +
							OrganismErrorMsg + CDCPriEnzymeErrorMsg + CDCSecEnzymeErrorMsg + OtherErrorMsg +
							PIDErrorMsg + fNameErrorMsg + mNameErrorMsg + lNameErrorMsg +
							mUnitErrorMsg + addrErrorMsg + cityErrorMsg + stErrorMsg +
							countyErrorMsg + zipErrorMsg + zip4ErrorMsg +
							subIDErrorMsg + subNameErrorMsg + subStErrorMsg + genderErrorMsg +
							CDCClusterCodeErrorMsg + LocalPriEnzymeErrorMsg + LocalSecEnzymeErrorMsg + RegionErrorMsg);
	}
	
	/*
	 * Program Variables
	 */

	public static int recordErrorCounter = 0;
	public static int recordSkippedCounter = 0;
	public static int fieldLength = 0;
	public static String errorFlag = null;
	public static String errorDescription;
	
	/*
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
