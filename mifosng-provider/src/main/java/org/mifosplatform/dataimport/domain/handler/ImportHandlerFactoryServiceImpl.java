package org.mifosplatform.dataimport.domain.handler;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.dataimport.domain.handler.client.ClientDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.client.GroupDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.loan.LoanDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.loan.LoanRepaymentDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.savings.SavingsDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.savings.SavingsTransactionDataImportHandler;
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

        if (workbook.getSheetIndex("Clients") == 0) {
            return new ClientDataImportHandler(workbook, clientWritePlatformService, commandsSourceWritePlatformService);
        } else if (workbook.getSheetIndex("Groups") == 0) {
            return new GroupDataImportHandler(workbook, commandsSourceWritePlatformService);
        } else if (workbook.getSheetIndex("Loans") == 0) {
            return new LoanDataImportHandler(workbook, commandsSourceWritePlatformService);
        } else if (workbook.getSheetIndex("LoanRepayment") == 0) {
            return new LoanRepaymentDataImportHandler(workbook, commandsSourceWritePlatformService);
        } else if (workbook.getSheetIndex("Savings") == 0) {
            return new SavingsDataImportHandler(workbook, commandsSourceWritePlatformService);
        } else if (workbook.getSheetIndex("SavingsTransaction") == 0) { return new SavingsTransactionDataImportHandler(workbook,
                commandsSourceWritePlatformService); }

        throw new IllegalArgumentException("No work sheet found for processing : active sheet " + workbook.getSheetName(0));
    }

}
