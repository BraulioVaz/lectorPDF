package bvaz.os.lector_pdf.modelos;

import java.sql.*;

public class ConectorBD {
	
	private ConectorBD() {
		
	}
	
	/**
	 * Crea una conexion con la base de datos SQLite
	 * @return Conexion a BD
	 */
	public static Connection conectar(){
		Connection conexion = null;
		String url = "jdbc:sqlite::resource:base_datos.db";
		
		try {
			conexion = DriverManager.getConnection(url);
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return conexion;
	}
}
