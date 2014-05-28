package org.mifosplatform.dataimport.api;

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
			
			
	}
	
	@POST
    @Path("import")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
	public String importTemplate(@Context final UriInfo uriInfo, @QueryParam("file") final String fileName){
			
			
	}
	
	@GET
	@Path("test")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
	public String importTemplate(@Context final UriInfo uriInfo){
			
			
	}
}