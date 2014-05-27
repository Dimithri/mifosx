package org.mifosplatform.dataimport.domain.populator;

import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.dataimport.domain.handler.Result;

public interface WorkbookPopulator {

    Result downloadAndParse();
    
    Result populate(Workbook workbook);

}