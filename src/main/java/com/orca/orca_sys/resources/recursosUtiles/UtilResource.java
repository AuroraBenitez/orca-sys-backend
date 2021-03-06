package com.orca.orca_sys.resources.recursosUtiles;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

//import com.orca.orca_sys.util.Secuencia;

@Path("/util")
public class UtilResource {
	
	@GET
	@Path("/secuencia/{seq}")
	public Response secuencia(@PathParam("seq") String seq) throws SQLException{
		//Secuencia secuenciaService = new Secuencia();
		String secuencia = "null";//secuenciaService.getSecuencia(seq);
		return Response.ok(secuencia).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
}
