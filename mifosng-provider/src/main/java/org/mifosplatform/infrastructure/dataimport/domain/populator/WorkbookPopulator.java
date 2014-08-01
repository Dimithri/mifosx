package org.mifosplatform.infrastructure.dataimport.domain.populator;

import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;

public interface WorkbookPopulator {

    Result downloadAndParse();
    
    Result populate(Workbook workbook);

}