package org.mifosplatform.dataimport.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.dataimport.domain.handler.Result;
import org.mifosplatform.dataimport.domain.populator.WorkbookPopulator;
import org.mifosplatform.dataimport.domain.populator.WorkbookPopulatorFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplatePlatformServiceImpl implements TemplatePlatformService {

    private final WorkbookPopulatorFactoryService workbookPopulatorFactoryService;

    @Autowired
    public TemplatePlatformServiceImpl(final WorkbookPopulatorFactoryService workbookPopulatorFactoryService) {
        this.workbookPopulatorFactoryService = workbookPopulatorFactoryService;
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
                    e.printStackTrace();
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
}