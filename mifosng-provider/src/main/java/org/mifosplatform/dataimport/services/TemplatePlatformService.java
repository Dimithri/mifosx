package org.mifosplatform.dataimport.services;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;

public interface TemplatePlatformService {

    public Response getClientImportTemplate(int clientTypeId);

    public String importClientsFromTemplate(InputStream content);
}