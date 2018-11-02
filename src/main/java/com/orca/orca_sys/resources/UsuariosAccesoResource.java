package com.orca.orca_sys.resources;

import java.io.IOException;
import java.sql.SQLException;

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
import com.orca.orca_sys.model.personas.UsuariosAcceso;
import com.orca.orca_sys.param.personas.UsuariosAccesoParam;
import com.orca.orca_sys.services.personas.UsuariosAccesoService;

@Path("/personas/usuarios-acceso")
public class UsuariosAccesoResource {
 	
	@GET
	@Path("/{usuario}/{pass}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceder(@PathParam("usuario") String usuario,
			@PathParam("pass") String pass) throws SQLException {
		UsuariosAcceso accesos = new UsuariosAcceso();
		UsuariosAccesoService accesosService = new UsuariosAccesoService();
		accesos= accesosService.login(usuario, pass);
		Gson gson = new Gson();
		String json = gson.toJson(accesos);	
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) 
			throws SQLException, JsonParseException, IOException {
		
		UsuariosAccesoService usuariosAccesoService = new UsuariosAccesoService();
		UsuariosAcceso usuariosAcceso = new UsuariosAcceso();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			usuariosAcceso = mapper.readValue(paramJson, UsuariosAcceso.class);
		}	
		usuariosAccesoService.insertar(usuariosAcceso);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/modificar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificar(@QueryParam("paramJson") String paramJson) 
			throws SQLException, JsonParseException, IOException {
		UsuariosAccesoService usuariosAccesoService = new UsuariosAccesoService();
		UsuariosAccesoParam param = new UsuariosAccesoParam();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(paramJson, UsuariosAccesoParam.class);
		}	
		usuariosAccesoService.modificar(param);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

}
