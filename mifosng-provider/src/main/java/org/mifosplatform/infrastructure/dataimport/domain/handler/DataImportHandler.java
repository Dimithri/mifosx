package org.mifosplatform.infrastructure.dataimport.domain.handler;

public interface DataImportHandler {

    Result parse();

    Result upload();

}
