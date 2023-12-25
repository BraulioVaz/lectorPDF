package bvaz.os.lector_pdf.modelos;

import java.sql.*;

public class ConectorBD {
	private static final String CADENA_CONEXION = "jdbc:sqlite::resource:base_datos.db";
	private static Connection conexion;
	
	private ConectorBD() {
		
	}
	
	private static void definirConfiguracion(Connection c) {
		Statement sentencia = null;
		
		try {
			sentencia = c.createStatement();
			sentencia.execute("PRAGMA foreign_keys = 1;");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Crea una conexion con la base de datos.
	 * @return Conexion a BD
	 */
	public static Connection conectar(){
		try {
			if(conexion == null || conexion.isClosed()) {
				conexion = DriverManager.getConnection(CADENA_CONEXION);
				definirConfiguracion(conexion);
			}	
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return conexion;
	}
}
