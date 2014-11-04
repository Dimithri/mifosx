package org.mifosplatform.infrastructure.dataimport.domain.handler;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

public interface ImportHandlerFactoryService {

    public DataImportHandler createImportHandler(Workbook workbook) throws IOException;

}
