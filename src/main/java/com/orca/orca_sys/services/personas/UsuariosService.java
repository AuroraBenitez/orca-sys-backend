package com.orca.orca_sys.services.personas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orca.orca_sys.model.personas.Clientes;
import com.orca.orca_sys.model.personas.Personas;
import com.orca.orca_sys.model.personas.UsuarioSucursal;
import com.orca.orca_sys.model.personas.Usuarios;
import com.orca.orca_sys.util.Conexion;

public class UsuariosService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
    
	public boolean insertar(Usuarios usuarios) throws SQLException{
		String sql = "insert into personas (cedula,nombre,apellido,fecha_nacimiento,nacionalidad,pais,ciudad,barrio,direccion, "
				+ " correo_electronico,ruc,sexo,telefono,celular_principal,estado) "
				+ "values ( "
				+ " "+usuarios.getCedula()+" ,"
				+ "UPPER ('"+usuarios.getNombre()+"'),"
				+ "upper ('"+usuarios.getApellido()+"'),"
				+ "'"+usuarios.getFechaNacimiento()+"',"
				+ "'"+usuarios.getNacionalidad()+"',"
				+ "'"+usuarios.getPais()+"',"
				+ "'"+usuarios.getCiudad()+"',"
				+ "'"+usuarios.getBarrio()+"',"
				+ "'"+usuarios.getDireccion()+"',"
				+ "'"+usuarios.getCorreoElectronico()+"',"
				+ "'"+usuarios.getRuc()+"',"
				+ "'"+usuarios.getSexo()+"',"
				+ "'"+usuarios.getTelefono()+"',"
				+ "'"+usuarios.getCelular1()+"',"
				+ "'ACTIVO');";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);	
		
		String sql2 = "select * from personas where cedula = "+usuarios.getCedula()+""
				+ " and nombre = UPPER('"+usuarios.getNombre()+"') "
				+ " and apellido = UPPER('"+usuarios.getApellido()+"')";
		Statement stmt2= con.ObtenerConexion().createStatement();
		rs = stmt2.executeQuery(sql2);
		Integer codigoPersona = null;
		while(rs.next()){
			codigoPersona = rs.getInt("codigo");
		}
		
		String sql3 = " insert into usuarios (codigo, usuario) values ("+codigoPersona+", '"+usuarios.getUsuario()+"')";
		Statement stmt3 =con.ObtenerConexion().createStatement();
		stmt3.execute(sql3);
		
		String sql4 = " insert into accesos (usuario, contrasenha, rol) values('"+usuarios.getUsuario()+"', '"+usuarios.getUsuario()+"', '"+usuarios.getRol()+"')";
		Statement stmt4 = con.ObtenerConexion().createStatement();
		stmt4.execute(sql4);
		return true;		
	}
	
	public List<Usuarios> listar(Usuarios personas, boolean complexQuery) throws SQLException{
		List<Usuarios> lista = new ArrayList<Usuarios>();
		String sql = " select * from personas p, usuarios u, accesos a "
				+ "  where p.codigo = u.codigo and a.usuario = u.usuario ";
		if(complexQuery){
			if(personas.getNombre()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql+conector + " (p.nombre like '%"+personas.getNombre()+"%'  or apellido like '%"+personas.getNombre()+"%' )";
			}
			if(personas.getRuc()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql+conector + " (p.ruc = '"+personas.getRuc()+"' or cedula = '"+personas.getRuc()+"') ";
			}
			
		}else{
			if(personas.getNombre()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql+conector + " p.nombre like '%"+personas.getNombre()+"%'  ";
			}
			
		}
		
		if(personas.getCodigo()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " p.codigo = '"+personas.getCodigo()+"' ";
		}
		
		if(personas.getApellido()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " p.apellido like '%"+personas.getApellido()+"%' ";
		}
		
		if(personas.getRuc()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " p.ruc like '%"+personas.getRuc()+"%' ";
		}
		
		
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Usuarios entidad = new Usuarios();
        	entidad.setCodigo(rs.getString("codigo"));
        	entidad.setCedula(rs.getString("cedula"));
        	entidad.setNombre(rs.getString("nombre"));
        	entidad.setApellido(rs.getString("apellido"));
        	entidad.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        	entidad.setNacionalidad(rs.getString("nacionalidad"));
        	entidad.setPais(rs.getString("pais"));
        	entidad.setCiudad(rs.getString("ciudad"));
        	entidad.setBarrio(rs.getString("barrio"));
        	entidad.setDireccion(rs.getString("direccion"));
        	entidad.setCorreoElectronico(rs.getString("correo_electronico"));        	
        	entidad.setRuc(rs.getString("ruc"));
        	entidad.setSexo(rs.getString("sexo").trim());
        	entidad.setTelefono(rs.getString("telefono"));
        	entidad.setCelular1(rs.getString("celular_principal"));
        	entidad.setCelular2(rs.getString("celular_secundario"));
        	entidad.setEstado(rs.getString("estado"));
        	entidad.setUsuario(rs.getString("usuario"));
        	entidad.setRol(rs.getString("rol"));
        	lista.add(entidad);
        }

		return lista;
	}
	
	public List<UsuarioSucursal> listarUsuarioSucursal(UsuarioSucursal usuarioSucursal) throws SQLException{
		List<UsuarioSucursal> lista = new ArrayList<UsuarioSucursal>();
		String sql = " select * from usuario_sucursal ";		
		if(usuarioSucursal.getUsuario()!= null){
			sql = sql+ " where  usuario = '"+usuarioSucursal.getUsuario()+"'  ";
		}		
		if(usuarioSucursal.getSucursal()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " sucursal = '"+usuarioSucursal.getSucursal()+"' ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	UsuarioSucursal entidad = new UsuarioSucursal();
        	entidad.setUsuario(rs.getString("usuario"));
        	entidad.setSucursal(rs.getString("sucursal"));
        	lista.add(entidad);
        }
		return lista;
	}

}
