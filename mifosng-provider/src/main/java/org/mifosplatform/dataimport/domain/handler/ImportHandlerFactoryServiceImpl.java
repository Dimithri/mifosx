package org.mifosplatform.dataimport.domain.handler;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.dataimport.domain.handler.client.ClientDataImportHandler;
import org.mifosplatform.dataimport.services.http.MifosRestClient;
import org.mifosplatform.portfolio.client.service.ClientWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportHandlerFactoryServiceImpl implements ImportHandlerFactoryService {

    private final ClientWritePlatformService clientWritePlatformService;

    @Autowired
    public ImportHandlerFactoryServiceImpl(final ClientWritePlatformService clientWritePlatformService) {
        this.clientWritePlatformService = clientWritePlatformService;
    }

    @Override
    public DataImportHandler createImportHandler(Workbook workbook) throws IOException {

        if (workbook.getSheetIndex("Clients") == 0) {
            return new ClientDataImportHandler(workbook, new MifosRestClient());
        }
        
        throw new IllegalArgumentException("No work sheet found for processing : active sheet " + workbook.getSheetName(0));
    }

}
