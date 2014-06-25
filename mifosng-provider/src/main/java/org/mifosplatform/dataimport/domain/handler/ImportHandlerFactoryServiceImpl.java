package org.mifosplatform.dataimport.domain.handler;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.dataimport.domain.handler.client.ClientDataImportHandler;
import org.mifosplatform.dataimport.services.http.MifosRestClient;
import org.mifosplatform.portfolio.client.service.ClientWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportHandlerFactoryServiceImpl implements ImportHandlerFactoryService {

    private final ClientWritePlatformService clientWritePlatformService;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    @Autowired
    public ImportHandlerFactoryServiceImpl(final ClientWritePlatformService clientWritePlatformService,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.clientWritePlatformService = clientWritePlatformService;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }

    @Override
    public DataImportHandler createImportHandler(Workbook workbook) throws IOException {

        if (workbook.getSheetIndex("Clients") == 0) { return new ClientDataImportHandler(workbook, clientWritePlatformService,
                commandsSourceWritePlatformService); }

        throw new IllegalArgumentException("No work sheet found for processing : active sheet " + workbook.getSheetName(0));
    }

}
