package org.mifosplatform.infrastructure.dataimport.services;

import javax.ws.rs.core.Response;

public interface TemplatePlatformService {

    public Response getClientImportTemplate(int clientTypeId);

    public Response getGroupImportTemplate();

    public Response getCenterImportTemplate();

    public Response getLoanImportTemplate();

    public Response getLoanRepaymentImportTemplate();

    public Response getSavingImportTemplate();

    public Response getSavingsTransactionImportTemplate();

    public Response getOfficeImportTemplate();

    public Response getCodeImportTemplate();

    public Response getCodeValueImportTemplate();

    public Response getStaffImportTemplate();

    public Response getUserImportTemplate();
}