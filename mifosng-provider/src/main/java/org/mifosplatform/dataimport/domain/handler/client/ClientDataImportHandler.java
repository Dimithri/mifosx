package org.mifosplatform.dataimport.domain.handler.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.dataimport.data.client.Client;
import org.mifosplatform.dataimport.data.client.CorporateClient;
import org.mifosplatform.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.Result;
import org.mifosplatform.dataimport.services.http.RestClient;
import org.mifosplatform.dataimport.services.utils.StringUtils;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.client.service.ClientWritePlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class ClientDataImportHandler extends AbstractDataImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientDataImportHandler.class);

    private static final int FIRST_NAME_COL = 0;
    private static final int FULL_NAME_COL = 0;
    private static final int LAST_NAME_COL = 1;
    private static final int MIDDLE_NAME_COL = 2;
    private static final int OFFICE_NAME_COL = 3;
    private static final int STAFF_NAME_COL = 4;
    private static final int EXTERNAL_ID_COL = 5;
    private static final int ACTIVATION_DATE_COL = 6;
    private static final int ACTIVE_COL = 7;
    private static final int STATUS_COL = 8;
    private static final int GENDER_COL = 10;
    private static final int DATE_OF_BIRTH_COL = 11;
    private static final int CLIENT_TYPE_COL = 12;
    private static final int CLIENT_CLASSIFICATION_COL = 13;

    private List<Client> clients;
    private String clientType;

    // private final RestClient restClient;

    private final Workbook workbook;
    @SuppressWarnings("unused")
    private final ClientWritePlatformService clientWritePlatformService;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    public ClientDataImportHandler(Workbook workbook, final ClientWritePlatformService clientWritePlatformService,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.workbook = workbook;
        // this.restClient = client;
        this.clientWritePlatformService = clientWritePlatformService;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        clients = new ArrayList<Client>();
    }

    @Override
    public Result parse() {
        Result result = new Result();
        Sheet clientSheet = workbook.getSheet("Clients");
        Integer noOfEntries = getNumberOfRows(clientSheet, 0);
        clientType = getClientType(clientSheet);
        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = clientSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) {
                    clients.add(parseAsClient(row));
                }
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }
        return result;
    }

    private String getClientType(Sheet clientSheet) {
        if (readAsString(FIRST_NAME_COL, clientSheet.getRow(0)).equals("First Name*"))
            return "Individual";
        else
            return "Corporate";
    }

    private Client parseAsClient(Row row) {

        Client client = null;

        String officeName = readAsString(OFFICE_NAME_COL, row);
        String officeId = getIdByName(workbook.getSheet("Offices"), officeName).toString();
        String staffName = readAsString(STAFF_NAME_COL, row);
        String staffId = getIdByName(workbook.getSheet("Staff"), staffName).toString();
        String externalId = readAsString(EXTERNAL_ID_COL, row);
        String activationDate = readAsDate(ACTIVATION_DATE_COL, row);
        String active = readAsBoolean(ACTIVE_COL, row).toString();
        String gender = getGenderId(readAsString(GENDER_COL, row));
        
        String clientTypeName = readAsString(CLIENT_TYPE_COL, row);
        Integer clientTypeIdInt = getIdByName(workbook.getSheet("ClientType"), clientTypeName);
        String clientTypeId = clientTypeIdInt !=0 ? clientTypeIdInt.toString() : "";
        
        String clientClassificationName = readAsString(CLIENT_CLASSIFICATION_COL, row);
        Integer clientClassificationIdInt = getIdByName(workbook.getSheet("ClientClassification"), clientClassificationName);
        String clientClassificationId = clientClassificationIdInt !=0 ? clientClassificationIdInt.toString() : "";

        if (clientType.equals("Individual")) {

            String firstName = readAsString(FIRST_NAME_COL, row);
            String lastName = readAsString(LAST_NAME_COL, row);
            String middleName = readAsString(MIDDLE_NAME_COL, row);
            if (StringUtils.isBlank(firstName)) { throw new IllegalArgumentException("Name is blank"); }
            client = new Client(firstName, lastName, middleName, gender, clientTypeId, clientClassificationId, activationDate, active,
                    externalId, officeId, staffId, row.getRowNum());

        } else {

            String fullName = readAsString(FULL_NAME_COL, row);
            if (StringUtils.isBlank(fullName)) { throw new IllegalArgumentException("Name is blank"); }
            client = new CorporateClient(fullName, activationDate, active, externalId, officeId, staffId, row.getRowNum());
        }
        return client;
    }

    @Override
    public Result upload() {
        Result result = new Result();
        Sheet clientSheet = workbook.getSheet("Clients");
        // restClient.createAuthToken();
        for (Client client : clients) {
            try {
                Gson gson = new Gson();
                String payload = gson.toJson(client);
                logger.info(payload);
                // restClient.post("clients", payload);

                // create client
                final CommandWrapper commandRequest = new CommandWrapperBuilder().createClient().withJson(payload).build();

                @SuppressWarnings("unused")
                final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService
                        .logCommandSource(commandRequest);

                // Log the results
                Cell statusCell = clientSheet.getRow(client.getRowIndex()).createCell(STATUS_COL);
                statusCell.setCellValue("Imported");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                String message = parseStatus(e.getMessage());
                Cell statusCell = clientSheet.getRow(client.getRowIndex()).createCell(STATUS_COL);
                statusCell.setCellValue(message);
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));
                result.addError("Row = " + client.getRowIndex() + " ," + message);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        clientSheet.setColumnWidth(STATUS_COL, 15000);
        writeString(STATUS_COL, clientSheet.getRow(0), "Status");
        return result;
    }

    public List<Client> getClients() {
        return clients;
    }

    private String getGenderId(String type) {
        String id = "null";
        type = type.trim();
        switch (type) {
            case "Male":
                id = "22";
            break;
            case "Female":
                id = "24";
            break;

        }
        return id;
    }
}
