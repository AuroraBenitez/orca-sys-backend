package com.orca.orca_sys.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	public java.sql.Connection con;

	public static Connection ObtenerConexion() throws SQLException {
		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost:5432/ORCADB";
		String user = "postgres";
		String password = "123456789";
		Connection con = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(connectString, user, password);
		} catch (Exception e) {
			System.out.print("Error al cargar la base de datos: " + e.getMessage() + "\n");
		}
		return con;
	}
}
