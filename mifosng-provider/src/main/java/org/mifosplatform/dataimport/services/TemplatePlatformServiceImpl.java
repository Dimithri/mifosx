package org.mifosplatform.dataimport.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.dataimport.domain.handler.DataImportHandler;
import org.mifosplatform.dataimport.domain.handler.ImportHandlerFactory;
import org.mifosplatform.dataimport.domain.handler.ImportHandlerFactoryService;
import org.mifosplatform.dataimport.domain.handler.Result;
import org.mifosplatform.dataimport.domain.populator.WorkbookPopulator;
import org.mifosplatform.dataimport.domain.populator.WorkbookPopulatorFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jersey.core.header.FormDataContentDisposition;

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

                try {

                    WorkbookPopulator populator = workbookPopulatorFactoryService.createWorkbookPopulator(parameter, fileName);
                    Workbook workbook = new HSSFWorkbook();
                    Result result = downloadAndPopulate(workbook, populator);

                    if (result.isSuccess()) {
                        fileName = fileName + ".xls";
                        // writeToStream(workbook, result, response, fileName);
                        response = getOutput(workbook, fileName);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

            break;

            case 1:
                // get the template for corporate type client
                parameter = "corporate";

            break;

            default:
            // exception
            break;
        }

        return response;
    }

    Result downloadAndPopulate(Workbook workbook, WorkbookPopulator populator) throws IOException {
        Result result = populator.downloadAndParse();
        if (result.isSuccess()) {
            result = populator.populate(workbook);
        }
        return result;
    }

    Response getOutput(Workbook workbook, String fileName) throws IOException {

        // TODO
        // check the stream initializing
        OutputStream stream = null;
        workbook.write(stream);
        ResponseBuilder response = Response.ok(stream);

        response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.header("Content-Type", "text/xls");

        return response.build();
    }

    void writeToStream(Workbook workbook, Result result, HttpServletResponse response, String fileName) throws IOException {

        OutputStream stream = response.getOutputStream();
        if (result.isSuccess()) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            workbook.write(stream);
            stream.flush();
            stream.close();
        } else {
            OutputStreamWriter out = new OutputStreamWriter(stream, "UTF-8");
            for (String e : result.getErrors()) {
                out.write(e);
            }
            out.flush();
            out.close();
        }
    }

    @Override
    public String importClientsFromTemplate(@SuppressWarnings("unused") int clientTypeId, InputStream content, @SuppressWarnings("unused") FormDataContentDisposition fileDetail) {

        /*
         * switch (clientTypeId) {
         * 
         * case 0: // get the template for individual type break;
         * 
         * case 1: // get the template for corporate type client break;
         * 
         * default: // exception break; }
         */

        Workbook workbook;
        //Result result;
        try {
            workbook = new HSSFWorkbook(content);
            DataImportHandler handler = ImportHandlerFactory.createImportHandler(workbook);
            Result result = parseAndUpload(handler);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        
        //if(result.isSuccess()){
        // writeResult(workbook, result, response);
        //}
        
        return "{clients are imported}";
    }

    private Result parseAndUpload(DataImportHandler handler) throws IOException {
        Result result = handler.parse();
        if (result.isSuccess()) {
            result = handler.upload();
        }
        return result;
    }

    private Response getResultOutput(Workbook workbook, String fileName) throws IOException {

        return null;
    }

    private void writeResult(Workbook workbook, Result result, HttpServletResponse response) throws IOException {

        OutputStream stream = response.getOutputStream();
        OutputStreamWriter out = new OutputStreamWriter(stream, "UTF-8");
        if (result.isSuccess()) {
            out.write("Import complete");
        } else {
            for (String e : result.getErrors())
                logger.debug("Failed: " + e);
            String fileName = "Re-Upload.xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            workbook.write(stream);
        }
        out.flush();
        out.close();
    }
}