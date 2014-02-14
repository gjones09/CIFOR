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

package c4Utilities;

import java.util.Locale;
import java.util.ResourceBundle;
/**
*
* @author jonesg1
*/

/*
 * Read the Resource Bundle to get all of the application properties and 
 * the locale
 * 
 */

public class CIFORProperties_OLD
{
    /*
     * Read the properties file to get text fields for the GUI
     * as well as the database connection information.
     * 
     * Set the locale in the language and country variable below.
     */
  	public static void getProperties()
  	{
        String language;
        String country;

        language = new String("en");
        country = new String("US");

        Locale currentLocale;
        ResourceBundle messages;

        currentLocale = new Locale(language, country);

        messages =
          ResourceBundle.getBundle("MessagesBundle",currentLocale);
        
        /*
         * Get the database connection properties
         * 
         */
        cifor_DB_driver				= messages.getString("DB_driver");
        cifor_DB_url				= messages.getString("DB_url");
        cifor_DB_userid				= messages.getString("DB_userid");
        cifor_DB_password			= messages.getString("DB_password");
        
        /*
         * Locations for the directories on the C:/ drive
         */
    	cifor_DataFiles				= messages.getString("DataFiles");
    	cifor_ExcelFiles			= messages.getString("ExcelFiles");
    	cifor_ImageFile				= messages.getString("ExcelFiles");
    	cifor_UserDoc				= messages.getString("UserDoc");
    	cifor_TechDoc				= messages.getString("TechDoc");
        
        /*
         * Text for common Buttons, Titles, and Labels
         */
    	okButton					= messages.getString("okButton");
        closeButton					= messages.getString("closeButton");
        cancelButton				= messages.getString("cancelButton");
        helpButton					= messages.getString("helpButton");
        printButton					= messages.getString("printButton");
        excelButton					= messages.getString("excelButton");
        
        currentDate					= messages.getString("currentDate");
        beginningDate				= messages.getString("beginningDate");
        
        localeLanguage				= messages.getString("localeLanguage");
        localeCountry				= messages.getString("localeCountry");
        
        /*
         * Get the text for the MainMenu
         * 
         */
        ciforFileMenu 				= messages.getString("fileMenu");
		ciforFileMenu_UserSetup		= messages.getString("fileMenu_UserSetup");
		ciforFileMenu_MetaData		= messages.getString("fileMenu_MetaData");
		ciforFileMenu_Exit			= messages.getString("fileMenu_Exit");
			
		ciforImportMenu 			= messages.getString("importMenu");
		ciforImportMenu_Import 		= messages.getString("importMenu_Import");
		ciforImportMenu_DataMapper	= messages.getString("importMenu_DataMapper");
		
		ciforQueryMenu 				= messages.getString("queryMenu");
		ciforQueryMenu_DetailQuery 	= messages.getString("queryMenu_DetailQuery");

		ciforMapResultsMenu 		= messages.getString("mapResultsMenu");
		ciforMapResultsMenu_Query 	= messages.getString("mapResultsMenu_Query");
		
		ciforReportMenu 						= messages.getString("reportMenu");
		ciforReportMenu_ClusterReport 			= messages.getString("reportMenu_ClusterReport");
		ciforReportMenu_ClusterReportByRegion	= messages.getString("reportMenu_ClusterReportByRegion");
		ciforReportMenu_FrequencyAnalysis 		= messages.getString("reportMenu_FrequencyAnalysis");

		ciforHelpMenu				= messages.getString("helpMenu");
		ciforHelpMenu_UserGuide		= messages.getString("helpMenu_UserGuide");
		ciforHelpMenu_Welcome		= messages.getString("helpMenu_Welcome");
		
		ciforLogo 					= messages.getString("ImageFile");
		ciforTitle 					= messages.getString("Title");  
		ciforVersion 				= messages.getString("Version"); 
		
		ciforHeading	= "  " + ciforTitle + "   " + ciforVersion;

	  	/*
	  	 * Get the text for the About display
	  	 * 
	  	 */
		aboutTitleLabel 			= messages.getString("aboutTitleLabel");
		aboutVersionLabel 			= messages.getString("aboutVersionLabel");
		aboutVersion 				= messages.getString("aboutVersion");
		aboutVendorLabel			= messages.getString("aboutVendorLabel");
		aboutVendor 				= messages.getString("aboutVendor");
		aboutHomePageLabel 			= messages.getString("aboutHomePageLabel");
		aboutHomePage	 			= messages.getString("aboutHomePage");
	
		/*
		 * Get the text for the FileImport_EditErrorFields program
		 */
	    vAccessionError				= messages.getString("editAccessionError") + " ";
		vDateCollectedError			= messages.getString("editDateCollectedError");
		vDateReceivedError			= messages.getString("editDateReceivedError");
		vDateReportedError			= messages.getString("editDateReportedError");
		vPatientDOBError			= messages.getString("editPatientDOBError");
		vPatientAgeError			= messages.getString("editPatientAgeError");
		vPriTestErrorMsg			= messages.getString("editPriTestErrorMsg");
		vOrganismErrorMsg			= messages.getString("editOrganismErrorMsg");
		vPriEnzymeErrorMsg			= messages.getString("editPriEnzymeErrorMsg");
		vSecTestErrorMsg			= messages.getString("editSecTestErrorMsg");
		vOtherErrorMsg				= messages.getString("editOtherErrorMsg");
		vPIDErrorMsg				= messages.getString("editPIDErrorMsg");
		vFNameErrorMsg				= messages.getString("editFNameErrorMsg");
		vMNameErrorMsg				= messages.getString("editMNameErrorMsg");
		vLNameErrorMsg				= messages.getString("editLNameErrorMsg");
		vMUnitErrorMsg				= messages.getString("editMUnitErrorMsg");
		vAddrErrorMsg				= messages.getString("editAddrErrorMsg");
		vCityErrorMsg				= messages.getString("editCityErrorMsg");
		vStErrorMsg					= messages.getString("editStErrorMsg");
		vCountyErrorMsg				= messages.getString("editCountyErrorMsg");
		vZipErrorMsg				= messages.getString("editZipErrorMsg");
		vZip4ErrorMsg				= messages.getString("editZip4ErrorMsg");
		vSubIDErrorMsg				= messages.getString("editSubIDErrorMsg");
		vSubNameErrorMsg			= messages.getString("editSubNameErrorMsg");
		vSubStErrorMsg				= messages.getString("editSubStErrorMsg");
		vGenderErrorMsg				= messages.getString("editGenderErrorMsg");
		
		/*
		 * Get the text for the Cluster Parameters and Cluster Report
		 */
		cParameterTitle				= messages.getString("cParameterTitle");
		cParameterHeading			= messages.getString("cParameterHeading");
		cParameterSelect			= messages.getString("cParameterSelect");
		cParameterAll				= messages.getString("cParameterAll");
		cParameterToday				= messages.getString("cParameterToday");
		
		cReportTitle				= messages.getString("cReportTitle");
		cReportHeading				= messages.getString("cReportHeading");
		cReportPrintOptions			= messages.getString("cReportPrintOptions");
		cReportIncludePageHeader	= messages.getString("cReportIncludePageHeader");
		cReportIncludeHeader		= messages.getString("cReportIncludeHeader");
		cReportIncludePageNo		= messages.getString("cReportIncludePageNo");
		cReportTodaysReports		= messages.getString("cReportTodaysReports"); 
		cReportAllReports			= messages.getString("cReportAllReports");
		cReportIncludeFooter		= messages.getString("cReportIncludeFooter");
		cReportFooter				= messages.getString("cReportFooter");
		cReportDateRangeFrom		= messages.getString("cReportDateRangeFrom");
		cReportDateRangeTo			= messages.getString("cReportDateRangeTo");
		cReportReportPage			= messages.getString("cReportReportPage");
		cReportPrintTheTable		= messages.getString("cReportPrintTheTable");
		cReportCreateSpreadsheet 	= messages.getString("cReportCreateSpreadsheet");
		
		cReportAccessionNo			= messages.getString("cReportAccessionNo");
		cReportFirstName			= messages.getString("cReportFirstName");
		cReportLastName				= messages.getString("cReportLastName");
		cReportCity					= messages.getString("cReportCity");
		cReportState				= messages.getString("cReportState");
		cReportCounty				= messages.getString("cReportCounty");
		cReportAge					= messages.getString("cReportAge");
		cReportOrganism				= messages.getString("cReportOrganism");
		cReportPrimaryEnzyme		= messages.getString("cReportPrimaryEnzyme");
		cReportSecondaryEnzyme		= messages.getString("cReportSecondaryEnzyme");
		cReportOtherResult			= messages.getString("cReportOtherResult");
		cReportDateCollected		= messages.getString("cReportDateCollected");
		cReportDateReceived			= messages.getString("cReportDateReceived");
		cReportDateReported			= messages.getString("cReportDateReported");
		
		cReportExcelDatePrinted		= messages.getString("cReportExcelDatePrinted");
		cReportExcelPage			= messages.getString("cReportExcelPage");
		cReportExcelSheet1			= messages.getString("cReportExcelSheet1");
		cReportExcelSheet2			= messages.getString("cReportExcelSheet2");
		cReportExcelSheet3			= messages.getString("cReportExcelSheet3");
		cReportExcelDirectory		= messages.getString("cReportExcelDirectory");
		cReportExcelFileName		= messages.getString("cReportExcelFileName");
		
		/*
		 * Get the text for the Cluster Report By Region Parameters and Cluster Report By Region
		 */
		crParameterTitle				= messages.getString("crParameterTitle");
		crParameterHeading			= messages.getString("crParameterHeading");
		crParameterRegion			= messages.getString("crParameterRegion");
		crParameterSelect			= messages.getString("crParameterSelect");
		crParameterAll				= messages.getString("crParameterAll");
		crParameterToday				= messages.getString("crParameterToday");
		
		crReportTitle				= messages.getString("crReportTitle");
		crReportHeading				= messages.getString("crReportHeading");
		crReportPrintOptions			= messages.getString("crReportPrintOptions");
		crReportIncludePageHeader	= messages.getString("crReportIncludePageHeader");
		crReportIncludeHeader		= messages.getString("crReportIncludeHeader");
		crReportIncludePageNo		= messages.getString("crReportIncludePageNo");
		crReportTodaysReports		= messages.getString("crReportTodaysReports"); 
		crReportAllReports			= messages.getString("crReportAllReports");
		crReportIncludeFooter		= messages.getString("crReportIncludeFooter");
		crReportFooter				= messages.getString("crReportFooter");
		crReportDateRangeFrom		= messages.getString("crReportDateRangeFrom");
		crReportDateRangeTo			= messages.getString("crReportDateRangeTo");
		crReportReportPage			= messages.getString("crReportReportPage");
		crReportPrintTheTable		= messages.getString("crReportPrintTheTable");
		crReportCreateSpreadsheet 	= messages.getString("crReportCreateSpreadsheet");
		
		crReportAccessionNo			= messages.getString("crReportAccessionNo");
		crReportFirstName			= messages.getString("crReportFirstName");
		crReportLastName				= messages.getString("crReportLastName");
		crReportCity					= messages.getString("crReportCity");
		crReportState				= messages.getString("crReportState");
		crReportCounty				= messages.getString("crReportCounty");
		crReportAge					= messages.getString("crReportAge");
		crReportOrganism				= messages.getString("crReportOrganism");
		crReportPrimaryEnzyme		= messages.getString("crReportPrimaryEnzyme");
		crReportSecondaryEnzyme		= messages.getString("crReportSecondaryEnzyme");
		crReportOtherResult			= messages.getString("crReportOtherResult");
		crReportDateCollected		= messages.getString("crReportDateCollected");
		crReportDateReceived			= messages.getString("crReportDateReceived");
		crReportDateReported			= messages.getString("crReportDateReported");
		
		crReportExcelDatePrinted		= messages.getString("crReportExcelDatePrinted");
		crReportExcelPage			= messages.getString("crReportExcelPage");
		crReportExcelSheet1			= messages.getString("crReportExcelSheet1");
		crReportExcelSheet2			= messages.getString("crReportExcelSheet2");
		crReportExcelSheet3			= messages.getString("crReportExcelSheet3");
		crReportExcelDirectory		= messages.getString("crReportExcelDirectory");
		crReportExcelFileName		= messages.getString("crReportExcelFileName");
		
		/*
		 * Get the text for the Frequency Parameters and Frequency Analysis
		 */
		fParameterTitle				= messages.getString("fParameterTitle");
		fParameterHeading			= messages.getString("fParameterHeading");
		fParameterSelect			= messages.getString("fParameterSelect");
		fParameterAll				= messages.getString("fParameterAll");
		fParameterToday				= messages.getString("fParameterToday");
		
		fAnalysisTitle				= messages.getString("fAnalysisTitle");
		fAnalysisHeading			= messages.getString("fAnalysisHeading");
		fAnalysisPrintOptions		= messages.getString("fAnalysisPrintOptions");
		fAnalysisIncludePageHeader	= messages.getString("fAnalysisIncludePageHeader");
		fAnalysisIncludeHeader		= messages.getString("fAnalysisIncludeHeader");
		fAnalysisIncludePageNo		= messages.getString("fAnalysisIncludePageNo");
		fAnalysisTodaysReports		= messages.getString("fAnalysisTodaysReports"); 
		fAnalysisAllReports			= messages.getString("fAnalysisAllReports");
		fAnalysisIncludeFooter		= messages.getString("fAnalysisIncludeFooter");
		fAnalysisFooter				= messages.getString("fAnalysisFooter");
		fAnalysisDateRangeFrom		= messages.getString("fAnalysisDateRangeFrom");
		fAnalysisDateRangeTo		= messages.getString("fAnalysisDateRangeTo");
		fAnalysisReportPage			= messages.getString("fAnalysisReportPage");
		fAnalysisPrintTheTable		= messages.getString("fAnalysisPrintTheTable");
		fAnalysisShrinkToFit		= messages.getString("fAnalysisShrinkToFit");
		fAnalysisFitWidth			= messages.getString("fAnalysisFitWidth");
		fAnalysisCreateSpreadsheet 	= messages.getString("fAnalysisCreateSpreadsheet");
		
		fAnalysisOrganism			= messages.getString("fAnalysisOrganism");
		fAnalysisPrimaryEnzyme		= messages.getString("fAnalysisPrimaryEnzyme");
		fAnalysisSecondaryEnzyme	= messages.getString("fAnalysisSecondaryEnzyme");
		fAnalysisOtherResult		= messages.getString("fAnalysisOtherResult");
		fAnalysisMTD				= messages.getString("fAnalysisMTD");
		fAnalysisYTD				= messages.getString("fAnalysisYTD");
		fAnalysisJanuary			= messages.getString("fAnalysisJanuary");
		fAnalysisFebruary			= messages.getString("fAnalysisFebruary");
		fAnalysisMarch				= messages.getString("fAnalysisMarch");
		fAnalysisApril				= messages.getString("fAnalysisApril");
		fAnalysisMay				= messages.getString("fAnalysisMay");
		fAnalysisJune				= messages.getString("fAnalysisJune");
		fAnalysisJuly				= messages.getString("fAnalysisJuly");
		fAnalysisAugust				= messages.getString("fAnalysisAugust");
		fAnalysisSeptember			= messages.getString("fAnalysisSeptember");
		fAnalysisOctober			= messages.getString("fAnalysisOctober");
		fAnalysisNovember			= messages.getString("fAnalysisNovember");
		fAnalysisDecember			= messages.getString("fAnalysisDecember");
		
		fAnalysisExcelDatePrinted	= messages.getString("fAnalysisExcelDatePrinted");
		fAnalysisExcelPage			= messages.getString("fAnalysisExcelPage");
		fAnalysisExcelSheet1		= messages.getString("fAnalysisExcelSheet1");
		fAnalysisExcelSheet2		= messages.getString("fAnalysisExcelSheet2");
		fAnalysisExcelSheet3		= messages.getString("fAnalysisExcelSheet3");
		fAnalysisExcelDirectory		= messages.getString("fAnalysisExcelDirectory");
		fAnalysisExcelFileName		= messages.getString("fAnalysisExcelFileName");
		
		/*
		 * Get the text for the Detail Query Parameters, Detail Query Report and Excel Spreadsheet
		 */
		qParameterTitle				= messages.getString("qParameterTitle");
		qParameterHeading			= messages.getString("qParameterHeading");
		qParameterOrganism			= messages.getString("qParameterOrganism");
		qParameterPrimaryEnzyme		= messages.getString("qParameterPrimaryEnzyme");
		qParameterSecondaryEnzyme	= messages.getString("qParameterSecondaryEnzyme");
		qParameterOtherResult		= messages.getString("qParameterOtherResult");
		
		qReportTitle				= messages.getString("qReportTitle");
		qReportHeading				= messages.getString("qReportHeading");
		qReportAccessionNo			= messages.getString("cReportAccessionNo");
		qReportOrganism				= messages.getString("cReportOrganism");
		qReportPrimaryEnzyme		= messages.getString("cReportPrimaryEnzyme");
		qReportSecondaryEnzyme		= messages.getString("cReportSecondaryEnzyme");
		qReportOtherResult			= messages.getString("cReportOtherResult");
		qReportDateCollected		= messages.getString("cReportDateCollected");
		qReportDateReceived			= messages.getString("cReportDateReceived");
		qReportDateReported			= messages.getString("cReportDateReported");
		
		qReportPrintOptions			= messages.getString("qReportPrintOptions");
		qReportIncludePageHeader	= messages.getString("qReportIncludePageHeader");
		qReportIncludeHeader		= messages.getString("qReportIncludeHeader");
		qReportIncludePageNo		= messages.getString("qReportIncludePageNo");
		qReportIncludeFooter		= messages.getString("qReportIncludeFooter");
		qReportFooter				= messages.getString("qReportFooter");
		qReportDateRangeFrom		= messages.getString("qReportDateRangeFrom");
		qReportDateRangeTo			= messages.getString("qReportDateRangeTo");
		qReportReportPage			= messages.getString("qReportReportPage");
		qReportPrintTheTable		= messages.getString("qReportPrintTheTable");
		qReportCreateSpreadsheet	= messages.getString("qReportCreateSpreadsheet");
		
		qReportExcelDatePrinted		= messages.getString("qReportExcelDatePrinted");
		qReportExcelPage			= messages.getString("qReportExcelPage");
		qReportExcelSheet1			= messages.getString("qReportExcelSheet1");
		qReportExcelSheet2			= messages.getString("qReportExcelSheet2");
		qReportExcelSheet3			= messages.getString("qReportExcelSheet3");
		qReportExcelDirectory		= messages.getString("qReportExcelDirectory");
		qReportExcelFileName		= messages.getString("qReportExcelFileName");
		
		/*
		 * Get the text for the Mapping Parameters, Mapping Report and Excel Spreadsheet
		 */
		mParameterTitle				= messages.getString("mParameterTitle");
		mParameterHeading			= messages.getString("mParameterHeading");
		mParameterOrganism			= messages.getString("mParameterOrganism");
		mParameterPrimaryEnzyme		= messages.getString("mParameterPrimaryEnzyme");
		mParameterSecondaryEnzyme	= messages.getString("mParameterSecondaryEnzyme");
		mParameterOtherResult		= messages.getString("mParameterOtherResult");
		mParameterState				= messages.getString("mParameterState");
		mParameterCounty			= messages.getString("mParameterCounty");
		
		mReportTitle				= messages.getString("mReportTitle");
		mReportHeading				= messages.getString("mReportHeading");
		mReportAccessionNo			= messages.getString("mReportAccessionNo");
		mReportOrganism				= messages.getString("mReportOrganism");
		mReportPrimaryEnzyme		= messages.getString("mReportPrimaryEnzyme");
		mReportSecondaryEnzyme		= messages.getString("mReportSecondaryEnzyme");
		mReportOtherResult			= messages.getString("mReportOtherResult");
		mReportStreetAddress		= messages.getString("mReportStreetAddress");
		mReportCity					= messages.getString("mReportCity");
		mReportCounty				= messages.getString("mReportCounty");
		mReportState				= messages.getString("mReportState");
		mReportZip					= messages.getString("mReportZip");
		mReportZipPlus				= messages.getString("mReportZipPlus");
		mReportDateReported			= messages.getString("mReportDateReported");
		
		mReportPrintOptions			= messages.getString("mReportPrintOptions");
		mReportIncludePageHeader	= messages.getString("mReportIncludePageHeader");
		mReportIncludeHeader		= messages.getString("mReportIncludeHeader");
		mReportIncludePageNo		= messages.getString("mReportIncludePageNo");
		mReportIncludeFooter		= messages.getString("mReportIncludeFooter");
		mReportFooter				= messages.getString("mReportFooter");
		mReportDateRangeFrom		= messages.getString("mReportDateRangeFrom");
		mReportDateRangeTo			= messages.getString("mReportDateRangeTo");
		mReportReportPage			= messages.getString("mReportReportPage");
		mReportPrintTheTable		= messages.getString("mReportPrintTheTable");
		mReportCreateSpreadsheet	= messages.getString("mReportCreateSpreadsheet");
		
		mReportExcelDatePrinted		= messages.getString("mReportExcelDatePrinted");
		mReportExcelPage			= messages.getString("mReportExcelPage");
		mReportExcelSheet1			= messages.getString("mReportExcelSheet1");
		mReportExcelSheet2			= messages.getString("mReportExcelSheet2");
		mReportExcelSheet3			= messages.getString("mReportExcelSheet3");
		mReportExcelDirectory		= messages.getString("mReportExcelDirectory");
		mReportExcelFileName		= messages.getString("mReportExcelFileName");
		
		/*
		 * Get the text for the Mapping Parameters, Mapping Report and Excel Spreadsheet
		 */
		dMapperTitle				= messages.getString("dMapperTitle");
		dMapperHeader				= messages.getString("dMapperHeader");
		dMapperNextButton			= messages.getString("dMapperNextButton");
		dMapperPreviousButton		= messages.getString("dMapperPreviousButton");
		dMapperAddButton			= messages.getString("dMapperAddButton");
		dMapperRemoveButton			= messages.getString("dMapperRemoveButton");
		dMapperSaveButton			= messages.getString("dMapperSaveButton");
		dMapperExitButton			= messages.getString("dMapperExitButton");
		dMapperInputFile			= messages.getString("dMapperInputFile");
		dMapperMappedFileLabel		= messages.getString("dMapperMappedFileLabel");
		
  	}
     
  	/*
  	 * Common variables
  	 */
  	public static String okButton;
  	public static String closeButton;
  	public static String cancelButton;
  	public static String helpButton;
  	public static String printButton;
  	public static String excelButton;
  	
  	public static String currentDate;
  	public static String beginningDate;
  	
  	public static String localeLanguage;
  	public static String localeCountry;
  	
  	/*
  	 * Variables for the Database Connection
  	 */
  	public static String cifor_DB_driver;
	public static String cifor_DB_url;
	public static String cifor_DB_userid;
	public static String cifor_DB_password;
	
	/*
	 * Variables for the file directory locations on the C:/ drive
	 */
	public static String cifor_DataFiles;
	public static String cifor_ExcelFiles;
	public static String cifor_ImageFile;
	public static String cifor_UserDoc;
	public static String cifor_TechDoc;
  	/*
  	 * Variables for the MainMenu
  	 */
    public static String ciforLogo;
    public static String ciforTitle;
    public static String ciforVersion;
    public static String ciforHeading;
    
    public static String ciforFileMenu;
    public static String ciforFileMenu_UserSetup;
    public static String ciforFileMenu_MetaData;
    public static String ciforFileMenu_Exit;
    
    public static String ciforImportMenu;
    public static String ciforImportMenu_Import;
    public static String ciforImportMenu_DataMapper;
    
    public static String ciforQueryMenu;
    public static String ciforQueryMenu_DetailQuery;
    
    public static String ciforMapResultsMenu;
    public static String ciforMapResultsMenu_Query;
	
    public static String ciforReportMenu;
    public static String ciforReportMenu_ClusterReport;
    public static String ciforReportMenu_ClusterReportByRegion;
    public static String ciforReportMenu_FrequencyAnalysis;

    public static String ciforHelpMenu;
    public static String ciforHelpMenu_UserGuide;
    public static String ciforHelpMenu_Welcome;
    
    /*
     * Variables for "About"
     */
    public static String aboutTitleLabel;
    public static String aboutVersionLabel;
    public static String aboutVersion;
    public static String aboutVendorLabel;
    public static String aboutVendor ;
    public static String aboutHomePageLabel;
    public static String aboutHomePage;
    
    /*
     * Variables for FileImport_EditErrorFields.java
     */
    public static String vAccessionError;
	public static String vDateCollectedError;
	public static String vDateReceivedError;
	public static String vDateReportedError;
	public static String vPatientDOBError;
	public static String vPatientAgeError;
	public static String vPriTestErrorMsg;
	public static String vOrganismErrorMsg;
	public static String vPriEnzymeErrorMsg;
	public static String vSecTestErrorMsg;
	public static String vOtherErrorMsg;
	public static String vPIDErrorMsg;
	public static String vFNameErrorMsg;
	public static String vMNameErrorMsg;
	public static String vLNameErrorMsg;
	public static String vMUnitErrorMsg;
	public static String vAddrErrorMsg;
	public static String vCityErrorMsg;
	public static String vStErrorMsg;
	public static String vCountyErrorMsg;
	public static String vZipErrorMsg;
	public static String vZip4ErrorMsg;
	public static String vSubIDErrorMsg;
	public static String vSubNameErrorMsg;
	public static String vSubStErrorMsg;
	public static String vGenderErrorMsg;
	
	/*
	 * Get the text for the Cluster Report Parameters, Cluster Report and Excel Spreadsheet
	 */
	public static String cParameterTitle;
	public static String cParameterHeading;
	public static String cParameterSelect;
	public static String cParameterAll;
	public static String cParameterToday;	
	
	public static String cReportTitle;
	public static String cReportHeading;
	public static String cReportPrintOptions;
	public static String cReportIncludePageHeader;
	public static String cReportIncludeHeader;
	public static String cReportIncludePageNo;
	public static String cReportTodaysReports;
	public static String cReportAllReports;
	public static String cReportIncludeFooter;
	public static String cReportFooter;
	public static String cReportDateRangeFrom;
	public static String cReportDateRangeTo;
	public static String cReportReportPage;
	public static String cReportPrintTheTable;
	public static String cReportCreateSpreadsheet;
	
	public static String cReportAccessionNo;
	public static String cReportFirstName;
	public static String cReportLastName;
	public static String cReportCity;
	public static String cReportState;
	public static String cReportCounty;
	public static String cReportAge;
	public static String cReportOrganism;
	public static String cReportPrimaryEnzyme;
	public static String cReportSecondaryEnzyme;
	public static String cReportOtherResult;
	public static String cReportDateCollected;
	public static String cReportDateReceived;
	public static String cReportDateReported;
	
	public static String cReportExcelDatePrinted;
	public static String cReportExcelPage;
	public static String cReportExcelSheet1;
	public static String cReportExcelSheet2;
	public static String cReportExcelSheet3;
	public static String cReportExcelDirectory;
	public static String cReportExcelFileName;
	
	/*
	 * Get the text for the Cluster Report By Region Parameters, Cluster Report By Region and Excel Spreadsheet
	 */
	public static String crParameterTitle;
	public static String crParameterHeading;
	public static String crParameterRegion;
	public static String crParameterSelect;
	public static String crParameterAll;
	public static String crParameterToday;	
	
	public static String crReportTitle;
	public static String crReportHeading;
	public static String crReportPrintOptions;
	public static String crReportIncludePageHeader;
	public static String crReportIncludeHeader;
	public static String crReportIncludePageNo;
	public static String crReportTodaysReports;
	public static String crReportAllReports;
	public static String crReportIncludeFooter;
	public static String crReportFooter;
	public static String crReportDateRangeFrom;
	public static String crReportDateRangeTo;
	public static String crReportReportPage;
	public static String crReportPrintTheTable;
	public static String crReportCreateSpreadsheet;
	
	public static String crReportAccessionNo;
	public static String crReportFirstName;
	public static String crReportLastName;
	public static String crReportCity;
	public static String crReportState;
	public static String crReportCounty;
	public static String crReportAge;
	public static String crReportOrganism;
	public static String crReportPrimaryEnzyme;
	public static String crReportSecondaryEnzyme;
	public static String crReportOtherResult;
	public static String crReportDateCollected;
	public static String crReportDateReceived;
	public static String crReportDateReported;
	
	public static String crReportExcelDatePrinted;
	public static String crReportExcelPage;
	public static String crReportExcelSheet1;
	public static String crReportExcelSheet2;
	public static String crReportExcelSheet3;
	public static String crReportExcelDirectory;
	public static String crReportExcelFileName;
	
	/*
	 * Get the text for the Frequency Analysis Parameters, Frequency Analysis Report and Excel Spreadsheet
	 */
	public static String fParameterTitle;
	public static String fParameterHeading;
	public static String fParameterSelect;
	public static String fParameterAll;
	public static String fParameterToday;	
	
	public static String fAnalysisTitle;
	public static String fAnalysisHeading;
	public static String fAnalysisPrintOptions;
	public static String fAnalysisIncludePageHeader;
	public static String fAnalysisIncludeHeader;
	public static String fAnalysisIncludePageNo;
	public static String fAnalysisTodaysReports;
	public static String fAnalysisAllReports;
	public static String fAnalysisIncludeFooter;
	public static String fAnalysisFooter;
	public static String fAnalysisDateRangeFrom;
	public static String fAnalysisDateRangeTo;
	public static String fAnalysisReportPage;
	public static String fAnalysisPrintTheTable;
	public static String fAnalysisShrinkToFit;
	public static String fAnalysisFitWidth;
	public static String fAnalysisCreateSpreadsheet;
	
	public static String fAnalysisOrganism;
	public static String fAnalysisPrimaryEnzyme;
	public static String fAnalysisSecondaryEnzyme;
	public static String fAnalysisOtherResult;
	public static String fAnalysisMTD;
	public static String fAnalysisYTD;
	public static String fAnalysisJanuary;
	public static String fAnalysisFebruary;
	public static String fAnalysisMarch;
	public static String fAnalysisApril;
	public static String fAnalysisMay;
	public static String fAnalysisJune;
	public static String fAnalysisJuly;
	public static String fAnalysisAugust;
	public static String fAnalysisSeptember;
	public static String fAnalysisOctober;
	public static String fAnalysisNovember;
	public static String fAnalysisDecember;
	
	public static String fAnalysisExcelDatePrinted;
	public static String fAnalysisExcelPage;
	public static String fAnalysisExcelSheet1;
	public static String fAnalysisExcelSheet2;
	public static String fAnalysisExcelSheet3;
	public static String fAnalysisExcelDirectory;
	public static String fAnalysisExcelFileName;
	
	/*
	 * Get the text for the Detail Query Parameters, Detail Query Report and Excel Spreadsheet
	 */
	public static String qParameterTitle;
	public static String qParameterHeading;
	public static String qParameterOrganism;
	public static String qParameterPrimaryEnzyme;
	public static String qParameterSecondaryEnzyme;
	public static String qParameterOtherResult;
	
	public static String qReportTitle;
	public static String qReportHeading;
	public static String qReportAccessionNo;
	public static String qReportOrganism;
	public static String qReportPrimaryEnzyme;
	public static String qReportSecondaryEnzyme;
	public static String qReportOtherResult;
	public static String qReportDateCollected;
	public static String qReportDateReceived;
	public static String qReportDateReported;
	
	public static String qReportPrintOptions;
	public static String qReportIncludePageHeader;
	public static String qReportIncludeHeader;
	public static String qReportIncludePageNo;
	public static String qReportIncludeFooter;
	public static String qReportFooter;
	public static String qReportDateRangeFrom;
	public static String qReportDateRangeTo;
	public static String qReportReportPage;
	public static String qReportPrintTheTable;
	public static String qReportCreateSpreadsheet;
	
	public static String qReportExcelDatePrinted;
	public static String qReportExcelPage;
	public static String qReportExcelSheet1;
	public static String qReportExcelSheet2;
	public static String qReportExcelSheet3;
	public static String qReportExcelDirectory;
	public static String qReportExcelFileName;
	
	/*
	 * Get the text for the Mapping Parameters, Mapping Report and Excel Spreadsheet
	 */
	public static String mParameterTitle;
	public static String mParameterHeading;
	public static String mParameterOrganism;
	public static String mParameterPrimaryEnzyme;
	public static String mParameterSecondaryEnzyme;
	public static String mParameterOtherResult;
	public static String mParameterState;
	public static String mParameterCounty;
	
	public static String mReportTitle;
	public static String mReportHeading;
	public static String mReportAccessionNo;
	public static String mReportOrganism;
	public static String mReportPrimaryEnzyme;
	public static String mReportSecondaryEnzyme;
	public static String mReportOtherResult;
	public static String mReportStreetAddress;
	public static String mReportCity;
	public static String mReportCounty;
	public static String mReportState;
	public static String mReportZip;
	public static String mReportZipPlus;
	public static String mReportDateReported;
	
	public static String mReportPrintOptions;
	public static String mReportIncludePageHeader;
	public static String mReportIncludeHeader;
	public static String mReportIncludePageNo;
	public static String mReportIncludeFooter;
	public static String mReportFooter;
	public static String mReportDateRangeFrom;
	public static String mReportDateRangeTo;
	public static String mReportReportPage;
	public static String mReportPrintTheTable;
	public static String mReportCreateSpreadsheet;
	
	public static String mReportExcelDatePrinted;
	public static String mReportExcelPage;
	public static String mReportExcelSheet1;
	public static String mReportExcelSheet2;
	public static String mReportExcelSheet3;
	public static String mReportExcelDirectory;
	public static String mReportExcelFileName;
	
	/*
	 * Get the text for the DataMapper Parameters and DataMapper UI
	 */
	public static String dMapperTitle;
	public static String dMapperHeader;
	public static String dMapperNextButton;
	public static String dMapperPreviousButton;
	public static String dMapperAddButton;
	public static String dMapperRemoveButton;
	public static String dMapperSaveButton;
	public static String dMapperExitButton;
	public static String dMapperInputFile;
	public static String dMapperMappedFileLabel;
	
}