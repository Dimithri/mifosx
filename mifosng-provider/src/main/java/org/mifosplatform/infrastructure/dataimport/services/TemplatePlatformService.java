package org.mifosplatform.infrastructure.dataimport.services;

import javax.ws.rs.core.Response;

public interface TemplatePlatformService {

    public Response getClientImportTemplate(int clientTypeId);

    public Response getGroupImportTemplate();

    public Response getLoanImportTemplate();

    public Response getLoanRepaymentImportTemplate();

    public Response getSavingImportTemplate();

    public Response getSavingsTransactionImportTemplate();
}