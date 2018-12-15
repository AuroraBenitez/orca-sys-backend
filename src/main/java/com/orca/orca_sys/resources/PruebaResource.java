package com.orca.orca_sys.resources;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("prueba")
public class PruebaResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getStringName(){
		return "Esto es un recurso de prueba";
	} 
}
