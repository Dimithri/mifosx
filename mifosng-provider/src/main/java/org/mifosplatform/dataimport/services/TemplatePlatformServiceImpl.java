package org.mifosplatform.dataimport.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.dataimport.domain.handler.DataImportHandler;
import org.mifosplatform.dataimport.domain.handler.ImportHandlerFactoryService;
import org.mifosplatform.dataimport.domain.handler.Result;
import org.mifosplatform.dataimport.domain.populator.WorkbookPopulator;
import org.mifosplatform.dataimport.domain.populator.WorkbookPopulatorFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplatePlatformServiceImpl implements TemplatePlatformService {

    private final WorkbookPopulatorFactoryService workbookPopulatorFactoryService;
    private final ImportHandlerFactoryService importHandlerFactoryService;
    private static final Logger logger = LoggerFactory.getLogger(TemplatePlatformServiceImpl.class);

    @Autowired
    public TemplatePlatformServiceImpl(final WorkbookPopulatorFactoryService workbookPopulatorFactoryService,
            ImportHandlerFactoryService importHandlerFactoryService) {
        this.workbookPopulatorFactoryService = workbookPopulatorFactoryService;
        this.importHandlerFactoryService = importHandlerFactoryService;
    }

    @Override
    public Response getClientImportTemplate(int clientTypeId) {
        Response response = null;
        String fileName = "client";
        String parameter = "";

        switch (clientTypeId) {

            case 0:
                // get the template for individual type
                parameter = "individual";
            break;

            case 1:
                // get the template for corporate type client
                parameter = "corporate";
            break;

            default:
                // default parameter
                parameter = "individual";
            break;
        }

        try {

            WorkbookPopulator populator = workbookPopulatorFactoryService.createWorkbookPopulator(parameter, fileName);
            Workbook workbook = new HSSFWorkbook();
            Result result = downloadAndPopulate(workbook, populator);

            if (result.isSuccess()) {
                fileName = fileName + ".xls";
                response = getOutput(workbook, fileName);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Response importClientsFromTemplate(InputStream content) {
        Response response = null;
        Workbook workbook;
        // Result result;
        try {
            workbook = new HSSFWorkbook(content);
            DataImportHandler handler = importHandlerFactoryService.createImportHandler(workbook);
            Result result = parseAndUpload(handler);
            if(result.isSuccess()){
                response = writeResult(workbook, result);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        // if(result.isSuccess()){
        // writeResult(workbook, result, response);
        // }

        //return "{clients are imported}";
        return response;
    }


    @Override
    public Response getGroupImportTemplate() {

        Response response = null;
        String fileName = "groups";

        try {

            WorkbookPopulator populator = workbookPopulatorFactoryService.createWorkbookPopulator(null, fileName);
            Workbook workbook = new HSSFWorkbook();
            Result result = downloadAndPopulate(workbook, populator);

            if (result.isSuccess()) {
                fileName = fileName + ".xls";
                response = getOutput(workbook, fileName);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Response importGroupsFromTemplate(InputStream content) {
        
        return importFromTemplate(content);
    }

    @Override
    public Response getLoanImportTemplate() {
        Response response = null;
        String fileName = "loan";

        try {

            WorkbookPopulator populator = workbookPopulatorFactoryService.createWorkbookPopulator(null, fileName);
            Workbook workbook = new HSSFWorkbook();
            Result result = downloadAndPopulate(workbook, populator);

            if (result.isSuccess()) {
                fileName = fileName + ".xls";
                response = getOutput(workbook, fileName);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Response importLoansFromTemplate(InputStream content) {
        
        return importFromTemplate(content);
    }
    
    @Override
    public Response getLoanRepaymentImportTemplate() {
        Response response = null;
        String fileName = "loanRepaymentHistory";

        try {

            WorkbookPopulator populator = workbookPopulatorFactoryService.createWorkbookPopulator(null, fileName);
            Workbook workbook = new HSSFWorkbook();
            Result result = downloadAndPopulate(workbook, populator);

            if (result.isSuccess()) {
                fileName = fileName + ".xls";
                response = getOutput(workbook, fileName);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Response importLoanRepaymentFromTemplate(InputStream content) {
        
        return importFromTemplate(content);
    }

    @Override
    public Response getSavingImportTemplate() {
        Response response = null;
        String fileName = "savings";

        try {

            WorkbookPopulator populator = workbookPopulatorFactoryService.createWorkbookPopulator(null, fileName);
            Workbook workbook = new HSSFWorkbook();
            Result result = downloadAndPopulate(workbook, populator);

            if (result.isSuccess()) {
                fileName = fileName + ".xls";
                response = getOutput(workbook, fileName);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Response importSavingsFromTemplate(InputStream content) {
        
        return importFromTemplate(content);
    }

    @Override
    public Response getSavingsTransactionImportTemplate() {
        Response response = null;
        String fileName = "savingsTransactionHistory";

        try {

            WorkbookPopulator populator = workbookPopulatorFactoryService.createWorkbookPopulator(null, fileName);
            Workbook workbook = new HSSFWorkbook();
            Result result = downloadAndPopulate(workbook, populator);

            if (result.isSuccess()) {
                fileName = fileName + ".xls";
                response = getOutput(workbook, fileName);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Response importSavingsTransactionFromTemplate(InputStream content) {
        
        return importFromTemplate(content);
    }


    private Result parseAndUpload(DataImportHandler handler) throws IOException {
        Result result = handler.parse();
        if (result.isSuccess()) {
            result = handler.upload();
        }
        return result;
    }

    private Response writeResult(Workbook workbook, Result result) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //OutputStreamWriter out = new OutputStreamWriter(stream, "UTF-8");
        ResponseBuilder response = null;

        if (result.isSuccess()) {
            //out.write("Import complete");
            //response = Response.ok(new ByteArrayInputStream(stream.toByteArray()));
            //response.header("Success", "true");
            
            String fileName = "Results.xls";

            workbook.write(stream);

            response = Response.ok(new ByteArrayInputStream(stream.toByteArray()));
            response.header("Success", "true");
            response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.header("Access-Control-Expose-Headers", "Success");
            
        } else {
            for (String e : result.getErrors())
                logger.debug("Failed: " + e);

            String fileName = "Re-Upload.xls";

            workbook.write(stream);

            response = Response.ok(new ByteArrayInputStream(stream.toByteArray()));
            response.header("Success", "false");
            response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.header("Access-Control-Expose-Headers", "Success");
        }
        
        response.header("Content-Type", "application/vnd.ms-excel");

        //out.flush();
        //out.close();
        return response.build();
    }
    
    private Result downloadAndPopulate(Workbook workbook, WorkbookPopulator populator) throws IOException {
        Result result = populator.downloadAndParse();
        if (result.isSuccess()) {
            result = populator.populate(workbook);
        }
        return result;
    }

    private Response getOutput(Workbook workbook, String fileName) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workbook.write(stream);
        ResponseBuilder response = Response.ok(new ByteArrayInputStream(stream.toByteArray()));

        response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.header("Content-Type", "application/vnd.ms-excel");

        // stream.close();
        return response.build();
    }

    
    private Response importFromTemplate(InputStream content){
        Response response = null;
        Workbook workbook;
        // Result result;
        try {
            workbook = new HSSFWorkbook(content);
            DataImportHandler handler = importHandlerFactoryService.createImportHandler(workbook);
            Result result = parseAndUpload(handler);
            if(result.isSuccess()){
                response = writeResult(workbook, result);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        // if(result.isSuccess()){
        // writeResult(workbook, result, response);
        // }

        //return "{clients are imported}";
        return response;
    }
    
}