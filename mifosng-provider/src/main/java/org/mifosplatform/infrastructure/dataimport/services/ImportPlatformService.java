package org.mifosplatform.infrastructure.dataimport.services;

import java.io.InputStream;
import javax.ws.rs.core.Response;

public interface ImportPlatformService {

    public Response importClientsFromTemplate(InputStream content);

    public Response importGroupsFromTemplate(InputStream content);

    public Response importCentersFromTemplate(InputStream content);

    public Response importLoansFromTemplate(InputStream content);

    public Response importLoanRepaymentFromTemplate(InputStream content);

    public Response importSavingsFromTemplate(InputStream content);

    public Response importSavingsTransactionFromTemplate(InputStream content);

}
