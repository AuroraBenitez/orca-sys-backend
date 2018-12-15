package com.orca.orca_sys.resources.personas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.orca.orca_sys.model.catalogo.Dominios;
import com.orca.orca_sys.model.personas.Accesos;
import com.orca.orca_sys.model.personas.Clientes;
import com.orca.orca_sys.services.catalogo.DominiosService;
import com.orca.orca_sys.services.personas.AccesosService;
import com.orca.orca_sys.services.personas.ClientesService;

@Path("/personas/accesos")
public class AccesosResource {
	
	@GET
	@Path("/{usuario}/{pass}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceder(@PathParam("usuario") String usuario,
			@PathParam("pass") String pass) throws SQLException {
		Accesos accesos = new Accesos();
		AccesosService accesosService = new AccesosService();
		accesos= accesosService.login(usuario, pass);
		Gson gson = new Gson();
		String json = gson.toJson(accesos);	
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		ClientesService clientesService = new ClientesService();
		Clientes cliente = new Clientes();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			cliente = mapper.readValue(paramJson, Clientes.class);
		}	
		clientesService.insertar(cliente);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	

}
