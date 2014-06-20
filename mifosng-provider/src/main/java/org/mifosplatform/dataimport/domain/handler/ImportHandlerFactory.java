package org.mifosplatform.dataimport.domain.handler;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.dataimport.domain.handler.client.ClientDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.client.GroupDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.loan.LoanDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.loan.LoanRepaymentDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.savings.SavingsDataImportHandler;
import org.mifosplatform.dataimport.domain.handler.savings.SavingsTransactionDataImportHandler;
import org.mifosplatform.dataimport.services.http.MifosRestClient;


public class ImportHandlerFactory {
    
    public static final DataImportHandler createImportHandler(Workbook workbook) throws IOException {
        
        if(workbook.getSheetIndex("Clients") == 0) {
            	return new ClientDataImportHandler(workbook, new MifosRestClient());
        } else if(workbook.getSheetIndex("Groups") == 0) {
    	    return new GroupDataImportHandler(workbook, new MifosRestClient());
        }else if(workbook.getSheetIndex("Loans") == 0) {
        	    return new LoanDataImportHandler(workbook, new MifosRestClient());
        } else if(workbook.getSheetIndex("LoanRepayment") == 0) {
        	    return new LoanRepaymentDataImportHandler(workbook, new MifosRestClient());
        } else if(workbook.getSheetIndex("Savings") == 0) {
    	    return new SavingsDataImportHandler(workbook, new MifosRestClient());
        } else if(workbook.getSheetIndex("SavingsTransaction") == 0) {
    	    return new SavingsTransactionDataImportHandler(workbook, new MifosRestClient());
        }
        throw new IllegalArgumentException("No work sheet found for processing : active sheet " + workbook.getSheetName(0));
    }

}
