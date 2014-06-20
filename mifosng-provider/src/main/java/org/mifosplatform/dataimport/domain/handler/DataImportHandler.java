package org.mifosplatform.dataimport.domain.handler;


public interface DataImportHandler {

    Result parse();
    
    Result upload();

}
