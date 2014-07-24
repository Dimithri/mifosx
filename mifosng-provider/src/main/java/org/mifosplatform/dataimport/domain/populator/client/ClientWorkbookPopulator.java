package org.mifosplatform.dataimport.domain.populator.client;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.mifosplatform.dataimport.data.Office;
import org.mifosplatform.dataimport.domain.handler.Result;
import org.mifosplatform.dataimport.domain.populator.AbstractWorkbookPopulator;
import org.mifosplatform.dataimport.domain.populator.OfficeSheetPopulator;
import org.mifosplatform.dataimport.domain.populator.PersonnelSheetPopulator;

public class ClientWorkbookPopulator extends AbstractWorkbookPopulator {

    private static final int FIRST_NAME_COL = 0;
    private static final int LAST_NAME_COL = 1;
    private static final int MIDDLE_NAME_COL = 2;
    private static final int FULL_NAME_COL = 0;
    private static final int OFFICE_NAME_COL = 3;
    private static final int STAFF_NAME_COL = 4;
    private static final int EXTERNAL_ID_COL = 5;
    private static final int ACTIVATION_DATE_COL = 6;
    private static final int ACTIVE_COL = 7;
    private static final int WARNING_COL = 9;
    private static final int GENDER_COL = 10;
    private static final int DATE_OF_BIRTH_COL = 11;
    private static final int CLIENT_TYPE_COL = 12;
    private static final int CLIENT_CLASSIFICATION_COL = 13;
    private static final int RELATIONAL_OFFICE_NAME_COL = 14;
    private static final int RELATIONAL_OFFICE_OPENING_DATE_COL = 17;

    private final String clientType;

    private OfficeSheetPopulator officeSheetPopulator;

    private PersonnelSheetPopulator personnelSheetPopulator;

    public ClientWorkbookPopulator(String clientType, OfficeSheetPopulator officeSheetPopulator,
            PersonnelSheetPopulator personnelSheetPopulator) {
        this.clientType = clientType;
        this.officeSheetPopulator = officeSheetPopulator;
        this.personnelSheetPopulator = personnelSheetPopulator;
    }

    @Override
    public Result downloadAndParse() {
        Result result = officeSheetPopulator.downloadAndParse();
        if (result.isSuccess()) {
            result = personnelSheetPopulator.downloadAndParse();
        }
        return result;
    }

    @Override
    public Result populate(Workbook workbook) {
        Sheet clientSheet = workbook.createSheet("Clients");
        Result result = personnelSheetPopulator.populate(workbook);
        if (result.isSuccess()) result = officeSheetPopulator.populate(workbook);
        setLayout(clientSheet);
        setOfficeDateLookupTable(clientSheet, officeSheetPopulator.getOffices(), RELATIONAL_OFFICE_NAME_COL,
                RELATIONAL_OFFICE_OPENING_DATE_COL);
        if (result.isSuccess()) result = setRules(clientSheet);
        return result;
    }

    private void setLayout(Sheet worksheet) {
        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);
        if (clientType.equals("individual")) {
            worksheet.setColumnWidth(FIRST_NAME_COL, 6000);
            worksheet.setColumnWidth(LAST_NAME_COL, 6000);
            worksheet.setColumnWidth(MIDDLE_NAME_COL, 6000);
            writeString(FIRST_NAME_COL, rowHeader, "First Name*");
            writeString(LAST_NAME_COL, rowHeader, "Last Name*");
            writeString(MIDDLE_NAME_COL, rowHeader, "Middle Name");
        } else {
            worksheet.setColumnWidth(FULL_NAME_COL, 10000);
            worksheet.setColumnWidth(LAST_NAME_COL, 0);
            worksheet.setColumnWidth(MIDDLE_NAME_COL, 0);
            writeString(FULL_NAME_COL, rowHeader, "Full/Business Name*");
        }
        worksheet.setColumnWidth(OFFICE_NAME_COL, 5000);
        worksheet.setColumnWidth(STAFF_NAME_COL, 5000);
        worksheet.setColumnWidth(EXTERNAL_ID_COL, 3500);
        worksheet.setColumnWidth(ACTIVATION_DATE_COL, 4000);
        worksheet.setColumnWidth(ACTIVE_COL, 2000);
        worksheet.setColumnWidth(RELATIONAL_OFFICE_NAME_COL, 6000);
        worksheet.setColumnWidth(RELATIONAL_OFFICE_OPENING_DATE_COL, 4000);
        worksheet.setColumnWidth(GENDER_COL, 4000);
        worksheet.setColumnWidth(DATE_OF_BIRTH_COL, 6000);
        worksheet.setColumnWidth(CLIENT_TYPE_COL, 4000);
        worksheet.setColumnWidth(CLIENT_CLASSIFICATION_COL, 6000);
        writeString(OFFICE_NAME_COL, rowHeader, "Office Name*");
        writeString(STAFF_NAME_COL, rowHeader, "Staff Name*");
        writeString(EXTERNAL_ID_COL, rowHeader, "External ID");
        writeString(ACTIVATION_DATE_COL, rowHeader, "Activation Date*");
        writeString(ACTIVE_COL, rowHeader, "Active*");
        writeString(WARNING_COL, rowHeader, "All * marked fields are compulsory.");
        writeString(RELATIONAL_OFFICE_NAME_COL, rowHeader, "Office Name");
        writeString(RELATIONAL_OFFICE_OPENING_DATE_COL, rowHeader, "Opening Date");
        writeString(GENDER_COL, rowHeader, "Gender");
        writeString(DATE_OF_BIRTH_COL, rowHeader, "Date of Birth");
        writeString(CLIENT_TYPE_COL, rowHeader, "Client Type");
        writeString(CLIENT_CLASSIFICATION_COL, rowHeader, "Client Classification");

    }

    private Result setRules(Sheet worksheet) {
        Result result = new Result();
        try {
            CellRangeAddressList officeNameRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(),
                    OFFICE_NAME_COL, OFFICE_NAME_COL);
            CellRangeAddressList staffNameRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), STAFF_NAME_COL,
                    STAFF_NAME_COL);
            CellRangeAddressList dateRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), ACTIVATION_DATE_COL,
                    ACTIVATION_DATE_COL);
            CellRangeAddressList activeRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), ACTIVE_COL,
                    ACTIVE_COL);
            CellRangeAddressList genderRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), GENDER_COL,
            		GENDER_COL);
            CellRangeAddressList clientTypeRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), CLIENT_TYPE_COL,
            		CLIENT_TYPE_COL);
            CellRangeAddressList clientClassificationRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), CLIENT_CLASSIFICATION_COL,
            		CLIENT_CLASSIFICATION_COL);

            DataValidationHelper validationHelper = new HSSFDataValidationHelper((HSSFSheet) worksheet);

            List<Office> offices = officeSheetPopulator.getOffices();
            setNames(worksheet, offices);

            DataValidationConstraint officeNameConstraint = validationHelper.createFormulaListConstraint("Office");
            DataValidationConstraint staffNameConstraint = validationHelper
                    .createFormulaListConstraint("INDIRECT(CONCATENATE(\"Staff_\",$D1))");
            DataValidationConstraint activationDateConstraint = validationHelper.createDateConstraint(
                    DataValidationConstraint.OperatorType.BETWEEN, "=VLOOKUP($D1,$Q$2:$R" + (offices.size() + 1) + ",2,FALSE)", "=TODAY()",
                    "dd/mm/yy");
            DataValidationConstraint activeConstraint = validationHelper.createExplicitListConstraint(new String[] { "True", "False" });
            //new values
            DataValidationConstraint genderConstraint = validationHelper.createExplicitListConstraint(new String[] { "Male", "Female", "xxx" });
            DataValidationConstraint clientTypeConstraint = validationHelper.createExplicitListConstraint(new String[] { "Individual Person", "Very Small Enterprise", "Small/Medium Enterprise" });
            DataValidationConstraint clientClassificationConstraint = validationHelper.createExplicitListConstraint(new String[] { "Empty" });

            DataValidation officeValidation = validationHelper.createValidation(officeNameConstraint, officeNameRange);
            DataValidation staffValidation = validationHelper.createValidation(staffNameConstraint, staffNameRange);
            DataValidation activationDateValidation = validationHelper.createValidation(activationDateConstraint, dateRange);
            DataValidation activeValidation = validationHelper.createValidation(activeConstraint, activeRange);
            DataValidation genderValidation = validationHelper.createValidation(genderConstraint, genderRange);
            DataValidation clientTypeValidation = validationHelper.createValidation(clientTypeConstraint, clientTypeRange);
            DataValidation clientClassificationValidation = validationHelper.createValidation(clientClassificationConstraint, clientClassificationRange);

            worksheet.addValidationData(activeValidation);
            worksheet.addValidationData(genderValidation);
            worksheet.addValidationData(clientTypeValidation);
            worksheet.addValidationData(clientClassificationValidation);
            worksheet.addValidationData(officeValidation);
            worksheet.addValidationData(staffValidation);
            worksheet.addValidationData(activationDateValidation);
        } catch (RuntimeException re) {
            result.addError(re.getMessage());
        }
        return result;
    }

    private void setNames(Sheet worksheet, List<Office> offices) {
        Workbook clientWorkbook = worksheet.getWorkbook();
        Name officeGroup = clientWorkbook.createName();
        officeGroup.setNameName("Office");
        officeGroup.setRefersToFormula("Offices!$B$2:$B$" + (offices.size() + 1));

        for (Integer i = 0; i < offices.size(); i++) {
            Integer[] officeNameToBeginEndIndexesOfStaff = personnelSheetPopulator.getOfficeNameToBeginEndIndexesOfStaff().get(i);
            if (officeNameToBeginEndIndexesOfStaff != null) {
                Name name = clientWorkbook.createName();
                name.setNameName("Staff_" + offices.get(i).getName().trim().replaceAll("[ )(]", "_"));
                name.setRefersToFormula("Staff!$B$" + officeNameToBeginEndIndexesOfStaff[0] + ":$B$"
                        + officeNameToBeginEndIndexesOfStaff[1]);
            }
        }
    }

}
