package com.orca.orca_sys.services.servicios;

import java.awt.datatransfer.StringSelection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orca.orca_sys.model.catalogo.Dominios;
import com.orca.orca_sys.model.catalogo.Servicios;
import com.orca.orca_sys.model.personas.Clientes;
import com.orca.orca_sys.model.servicios.CircuitoServicio;
import com.orca.orca_sys.model.servicios.CircuitoServicioIngreso;
import com.orca.orca_sys.util.Conexion;

public class ServiciosService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
    
    public String getSecuencia() throws SQLException{
    	String secuencia = null;
    	String sql = " select nextval('servicio_seq') as secuencia; ";
    	Statement statement = con.ObtenerConexion().createStatement();
    	rs = statement.executeQuery(sql);
		 while(rs.next()){
			 secuencia = rs.getString("secuencia");
		 }
    	return secuencia;
    }
    
    public List<Servicios> listar(Servicios servicios) throws SQLException{
		List<Servicios> lista = new ArrayList<Servicios>();
		String sql = "select * from servicios ";
		if(servicios.getCodigo()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " codigo like '%"+servicios.getCodigo()+"%' ";
		}
		if(servicios.getDescripcion()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " descripcion like '%"+servicios.getDescripcion()+"%' ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Servicios entidad = new Servicios();
        	entidad.setCodigo(rs.getString("codigo"));
        	entidad.setDescripcion(rs.getString("descripcion"));
        	entidad.setPrecioUnitario(rs.getString("precio_unitario"));
        	entidad.setPrecioUnitario(rs.getString("precio_unitario"));
        	lista.add(entidad);
        }

		return lista;
	}
    
    public boolean ingresarEquipo(CircuitoServicioIngreso entidad) throws SQLException{
    	Statement statement = con.ObtenerConexion().createStatement();
    	try { 
    		String sql0 = "select * from  circuito_servicio where ";
    		rs = statement.executeQuery(sql0);
            while(rs.next()){
            	CircuitoServicio entidad0 = new CircuitoServicio();
            	entidad0.setSecuencia(rs.getString("secuencia"));
            	entidad0.setEstado(rs.getString("estado"));
            	entidad0.setPaso(rs.getString("paso"));
            	entidad0.setLugar(rs.getString("lugar"));
            	entidad0.setResponsable(rs.getString("responsable"));
            	entidad0.setFecha(rs.getString("fecha"));    
            	entidad0.setObservacion(rs.getString("observacion"));
            }
            
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha,observacion) "
    				+ "values ( "
    				+ " "+entidad.getSecuencia()+" ,"
    				+ " '"+entidad.getEstado()+"',"
    				+ " '"+entidad.getPaso()+"',"
    				+ "'"+entidad.getLugar()+"',"
    				+ "'"+entidad.getResponsable()+"',"
    				+ "'"+new Date(System.currentTimeMillis())+"',"
    				+ "'"+entidad.getObservacion()+"'"
    				+ ");";
    		
    		statement.execute(sql);	
    		String sql2= "insert into circuito_servicio_ingreso (secuencia, paso, cliente, correo, encargado,telefono, detalle_equipo, detalle_trabajo) values ("
    				+ " "+entidad.getSecuencia()+" ,"
    				+ " "+entidad.getPaso()+" ,"
    				+ " '"+entidad.getCliente()+"' ,"
    				+ " '"+entidad.getCorreo()+"' ,"
    				+ " '"+entidad.getEncargado()+"' ,"
    				+ " '"+entidad.getTelefono()+"' ,"
    				+ " '"+entidad.getDetalleEquipo()+"' ,"
    				+ " '"+entidad.getDetalleTrabajo()+"' "
    				+ ");";
    		//Statement statement2 = con.ObtenerConexion().createStatement();
    		statement.execute(sql2);	
    		return true;
		} catch (Exception e) {
			return false;
		}
		
	}

    public List<CircuitoServicio> listarCircuito(CircuitoServicioIngreso circuitoServicioIngreso, String aux) throws SQLException{
		List<CircuitoServicio> lista = new ArrayList<CircuitoServicio>();
		String sql = "select cs.* from circuito_servicio cs, circuito_servicio_ingreso csi where cs.secuencia = csi.secuencia ";
		if(aux!= null && !aux.equals("")){
			sql = sql +aux;
		} 
		if(circuitoServicioIngreso.getEstado()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " cs.estado = '"+ circuitoServicioIngreso.getEstado()+"' ";
		}
		if(circuitoServicioIngreso.getCliente()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " upper(csi.cliente) like UPPER('%"+circuitoServicioIngreso.getCliente()+"%') ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	CircuitoServicio entidad = new CircuitoServicio();
        	entidad.setSecuencia(rs.getString("secuencia"));
        	entidad.setEstado(rs.getString("estado"));
        	entidad.setPaso(rs.getString("paso"));
        	entidad.setLugar(rs.getString("lugar"));
        	entidad.setResponsable(rs.getString("responsable"));
        	entidad.setFecha(rs.getString("fecha"));    
        	entidad.setObservacion(rs.getString("observacion"));
        	lista.add(entidad);
        }

		return lista;
	}
    
    public boolean cotizacion(CircuitoServicioIngreso entidad) throws SQLException{
    	Statement statement = con.ObtenerConexion().createStatement();
    	try {    	
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha,observacion) "
    				+ "values ( "
    				+ " "+entidad.getSecuencia()+" ,"
    				+ " 'PENDIENTE_APROVACION',"
    				+ " '"+entidad.getPaso()+"',"
    				+ "'"+entidad.getLugar()+"',"
    				+ "'"+entidad.getResponsable()+"',"
    				+ "'"+new Date(System.currentTimeMillis())+"',"
    				+ "'"+entidad.getObservacion()+"'"
    				+ ");";
    		
    		statement.execute(sql);	
	
    		return true;
		} catch (Exception e) {
			return false;
		}
		
	}


}
