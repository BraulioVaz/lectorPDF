package bvaz.os.lector_pdf.modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bvaz.os.lector_pdf.modelos.entidades.*;

public class DistribuidorLibros extends DistribuidorEntidades<Libro>{

	@Override
	protected Libro instanciar(ResultSet rs) throws SQLException {
		Libro libro = new Libro();
		
		libro.id_libro = rs.getInt(1);
		libro.titulo = rs.getString(2);
		libro.editorial = rs.getInt(3);
		libro.fecha_publicacion = rs.getString(4);
		libro.archivo = rs.getString(5);
		libro.marcador = rs.getString(6);
		
		return libro;
	}

	@Override
	public List<Libro> obtenerTodos() {
		Connection conexion = ConectorBD.conectar();
		ArrayList<Libro> libros = new ArrayList<Libro>();
		ResultSet rs;
		
		try(Statement sentencia = conexion.createStatement()) {
			rs = sentencia.executeQuery("SELECT * FROM libros;");
				
			while(rs.next()) {
				libros.add(instanciar(rs));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return libros;
	}

	@Override
	public Libro buscarPorID(Object[] pId) {
		Connection conexion = ConectorBD.conectar();
		Libro libros = new Libro();
		String sql = "SELECT * FROM libros WHERE id_libro = ?;";
		int id = (int) pId[0];
		ResultSet rs;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, id);
			rs = sentencia.executeQuery();
				
			while(rs.next()) {
				libros = instanciar(rs);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return libros;
	}

	@Override
	public boolean insertar(Libro entidad) {
		Connection conexion = ConectorBD.conectar();
		String sql = "INSERT INTO libros (titulo, "
				+ "editorial, "
				+ "fecha_publicacion, "
				+ "archivo, "
				+ "marcador) VALUES (?,?,?,?,?);";
		int inserciones = 0;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, entidad.titulo);
			sentencia.setInt(2, entidad.editorial);
			sentencia.setString(3, entidad.fecha_publicacion);
			sentencia.setString(4, entidad.archivo);
			sentencia.setString(5, entidad.marcador);
			
			inserciones = sentencia.executeUpdate();
			
			if(inserciones == 1) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean actualizar(Libro entidad) {
		Connection conexion = ConectorBD.conectar();
		String sql = "UPDATE libros SET titulo = ?,"
				+ "editorial = ?,"
				+ "fecha_publicacion = ?,"
				+ "archivo = ?,"
				+ "marcador = ? WHERE id_libro = ?;";
		int modificaciones = 0;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, entidad.titulo);
			sentencia.setInt(2, entidad.editorial);
			sentencia.setString(3, entidad.fecha_publicacion);
			sentencia.setString(4, entidad.archivo);
			sentencia.setString(5, entidad.marcador);
			sentencia.setInt(6, entidad.id_libro);
			
			modificaciones = sentencia.executeUpdate();
			
			if(modificaciones == 1) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean eliminar(Object[] pId) {
		Connection conexion = ConectorBD.conectar();
		String sql = "DELETE FROM libros WHERE id_libro = ?";
		int id = (int) pId[0];
		int eliminaciones = 0;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, id);
			
			eliminaciones = sentencia.executeUpdate();
			
			if(eliminaciones == 1) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
