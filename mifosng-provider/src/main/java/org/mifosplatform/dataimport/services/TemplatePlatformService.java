package org.mifosplatform.dataimport.services;

import java.io.InputStream;

import javax.ws.rs.core.Response;

public interface TemplatePlatformService {

    public Response getClientImportTemplate(int clientTypeId);

    public Response importClientsFromTemplate(InputStream content);
}