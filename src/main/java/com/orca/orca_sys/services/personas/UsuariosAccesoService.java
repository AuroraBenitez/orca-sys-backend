package com.orca.orca_sys.services.personas;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.orca.orca_sys.model.personas.UsuariosAcceso;
import com.orca.orca_sys.param.personas.UsuariosAccesoParam;
import com.orca.orca_sys.util.Conexion;

import java.sql.SQLException;
import java.sql.Statement;

public class UsuariosAccesoService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	/**
	 * Metodo que recibe como parametros dos argumentos, y retorna una lista de
	 * la tabla usuarios
	 * 
	 * @param usuario
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public UsuariosAcceso login(String usuario, String pass) throws SQLException {

		String sql = "select * from usuarios where usuario ='" + usuario + "' and contrasenha= '" + pass + "'";

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);

		UsuariosAcceso entidad = null;

		while (rs.next()) {
			entidad = new UsuariosAcceso();
			entidad.setUsuario(rs.getString("usuario"));
			entidad.setContrasenha(rs.getString("contrasenha"));
		}
		return entidad;
	}

	/**
	 * Metodo que inserta registros a la tabla usuarios
	 * 
	 * @param usuariosAcceso
	 * @return
	 * @throws SQLException
	 */
	public boolean insertar(UsuariosAcceso usuariosAcceso) throws SQLException {
		String sql = "insert into usuarios (usuario,contrasenha) " + "values ('" + usuariosAcceso.getUsuario() + "','"
				+ usuariosAcceso.getContrasenha() + "');";

		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);
		return true;
	}

	/**
	 * Metodo que actualiza las constrasenhas del usuario
	 * 
	 * @param usuariosAcceso
	 * @return
	 * @throws SQLException
	 */
	public boolean modificar(UsuariosAccesoParam usuariosAcceso) throws SQLException {

		String sql = "update usuarios set contrasenha=" + usuariosAcceso.getContrasenha() + " where usuario = "
				+ usuariosAcceso.getUsuario() + "'; ";

		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);

		return true;
	}

}
