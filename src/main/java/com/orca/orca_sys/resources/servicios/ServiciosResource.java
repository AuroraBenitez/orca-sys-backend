package com.orca.orca_sys.resources.servicios;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.orca.orca_sys.model.catalogo.Servicios;
import com.orca.orca_sys.model.personas.Clientes;
import com.orca.orca_sys.model.servicios.CircuitoServicio;
import com.orca.orca_sys.model.servicios.CircuitoServicioIngreso;
import com.orca.orca_sys.services.personas.ClientesService;
import com.orca.orca_sys.services.servicios.ServiciosService;

@Path("/servicios")
public class ServiciosResource {
	
	@GET
	@Path("/secuencia")
	public Response secuencia() throws SQLException{
		ServiciosService serviciosService = new ServiciosService();
		String secuencia = serviciosService.getSecuencia();
		return Response.ok(secuencia).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/ingresar-equipo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ingresarEquipo(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		ServiciosService serviciosService = new ServiciosService();
		CircuitoServicioIngreso entidad = new CircuitoServicioIngreso();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			entidad = mapper.readValue(paramJson, CircuitoServicioIngreso.class);
		}	
		serviciosService.ingresarEquipo(entidad);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/circuito")
	public Response listarCircuito(@QueryParam("paramJson") String paramJson) throws JsonParseException, JsonMappingException, IOException, SQLException{
		ServiciosService serviciosService = new ServiciosService();
		CircuitoServicioIngreso entidad = new CircuitoServicioIngreso();
		if(!paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			entidad = mapper.readValue(paramJson, CircuitoServicioIngreso.class);
		}	
		List<CircuitoServicio> lista = serviciosService.listarCircuito(entidad, null);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();		
	}
	
	@GET
	@Path("/listar-servicio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarServicio(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		ServiciosService serviciosService = new ServiciosService();
		Servicios servicios = new Servicios();
		List<Servicios> lista = new ArrayList<Servicios>();
		if(!paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			servicios = mapper.readValue(paramJson, Servicios.class);
		}	
		lista =serviciosService.listar(servicios);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/cotizacion")
	public Response listarCotizacion(@QueryParam("paramJson") String paramJson) throws JsonParseException, JsonMappingException, IOException, SQLException{
		ServiciosService serviciosService = new ServiciosService();
		CircuitoServicioIngreso entidad = new CircuitoServicioIngreso();
		String valor = " and estado in ('RECEPCIONADO', 'PENDIENTE_APROVACION') ";
		if(!paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			entidad = mapper.readValue(paramJson, CircuitoServicioIngreso.class);
		}	
		List<CircuitoServicio> lista = serviciosService.listarCircuito(entidad, valor);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();		
	}

}
