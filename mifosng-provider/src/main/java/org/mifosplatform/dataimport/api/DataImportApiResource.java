package org.mifosplatform.dataimport.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.mifosplatform.accounting.journalentry.api.DateParam;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiParameterHelper;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.api.JsonQuery;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.PaginationParameters;
import org.mifosplatform.infrastructure.core.exception.UnrecognizedQueryParamException;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.core.serialization.ToApiJsonSerializer;
import org.mifosplatform.infrastructure.core.service.Page;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;

@Path("/dataimport")
@Component
@Scope("singleton")
public class DataImportApiResource {

    @Autowired
    public DataImportApiResource() {
        
		
    }
	
	@GET
    @Path("template")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
	public String retrieveTemplate(@Context final UriInfo uriInfo, @QueryParam("template") final String fileName,
            @QueryParam("clientType") final String clientType){
			
		//Handle the template downloading..	
		
		//Authenticate the user
		//this.context.authenticatedUser().validateHasReadPermission();
		
		//
		//return <JSON response> 
	}
	
	@POST
    @Path("import")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
	public String importTemplate(@Context final UriInfo uriInfo, @QueryParam("file") final String fileName){
		
		//Handle the data importing from a template..
		
		//Authenticate the user
		//this.context.authenticatedUser().validateHasReadPermission();
		
		//return <JSON response> 
	}
	
	@GET
	@Path("test")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
	public String importTemplate(@Context final UriInfo uriInfo){
			
		// Testing code 
		//Handle the data importing from a template..
	}
}