package bvaz.os.lector_pdf.modelos;

import java.util.*;
import java.sql.*;
import bvaz.os.lector_pdf.modelos.entidades.Editorial;

public class DistribuidorEditoriales extends DistribuidorEntidades<Editorial>{
	
	protected Editorial instanciar(ResultSet rs) throws SQLException{
		Editorial entidad = new Editorial();
		
		entidad.id_editorial = rs.getInt("id_libro");
		entidad.nombre = rs.getString("nombre");
		
		return entidad;
	}
	
	@Override
	public List<Editorial> obtenerTodos() {
		Connection conexion = ConectorBD.conectar();
		ArrayList<Editorial> entidades = new ArrayList<Editorial>();
		Statement sentencia = null;
		ResultSet resultado = null;
		String sql = "SELECT * FROM editoriales";
		
		if(conexion != null) {
			try {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(sql);
				
				while(resultado.next()) {
					entidades.add(instanciar(resultado));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return entidades;
	}

	@Override
	public Editorial buscarPorID(Object[] pID) {
		Connection conexion = ConectorBD.conectar();
		Editorial entidad = null;
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		String sql = "SELECT * FROM editoriales WHERE id_editorial = ?;";
		Integer id = (Integer)pID[0];
		
		if(conexion != null) {
			try {
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, id);
				resultado = sentencia.executeQuery();
				
				while(resultado.next()) {
					entidad = instanciar(resultado);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return entidad;
	}

	@Override
	public boolean insertar(Editorial entidad) {
		Connection conexion = ConectorBD.conectar();
		PreparedStatement sentencia = null;
		boolean resultado = false;
		String sql = "INSERT INTO editoriales VALUES (null, ?);";
		
		if(conexion != null) {
			try {
				sentencia = conexion.prepareStatement(sql);
				sentencia.setString(1, entidad.nombre);
				resultado = sentencia.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}

	@Override
	public boolean actualizar(Editorial entidad) {
		Connection conexion = ConectorBD.conectar();
		PreparedStatement sentencia = null;
		boolean resultado = false;
		String sql = "UPDATE editoriales SET nombre = ? WHERE id_editorial = ?;";
		
		if(conexion != null) {
			try {
				sentencia = conexion.prepareStatement(sql);
				sentencia.setString(1, entidad.nombre);
				sentencia.setInt(2, entidad.id_editorial);
				resultado = sentencia.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}

	@Override
	public boolean eliminar(Object[] pID) {
		Connection conexion = ConectorBD.conectar();
		PreparedStatement sentencia = null;
		boolean resultado = false;
		String sql = "DELETE FROM editoriales WHERE id_editorial = ?;";
		Integer id = (Integer)pID[0];
		if(conexion != null) {
			try {
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, id);
				resultado = sentencia.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}

}
