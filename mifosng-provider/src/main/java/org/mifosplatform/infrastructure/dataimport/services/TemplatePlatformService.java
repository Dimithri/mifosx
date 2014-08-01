package org.mifosplatform.infrastructure.dataimport.services;

import java.io.InputStream;

import javax.ws.rs.core.Response;

public interface TemplatePlatformService {

    public Response getClientImportTemplate(int clientTypeId);

    public Response importClientsFromTemplate(InputStream content);

    public Response getGroupImportTemplate();

    public Response importGroupsFromTemplate(InputStream content);

    public Response getLoanImportTemplate();

    public Response importLoansFromTemplate(InputStream content);

    public Response getLoanRepaymentImportTemplate();

    public Response importLoanRepaymentFromTemplate(InputStream content);

    public Response getSavingImportTemplate();

    public Response importSavingsFromTemplate(InputStream content);

    public Response getSavingsTransactionImportTemplate();

    public Response importSavingsTransactionFromTemplate(InputStream content);
}