package bvaz.os.lector_pdf.modelos;

import java.util.*;
import java.sql.*;
import bvaz.os.lector_pdf.modelos.entidades.Autor;

public class DistribuidorAutores extends DistribuidorEntidades<Autor>{
	
	private Autor nuevaInstancia(ResultSet rs) throws SQLException{
		Autor autor = new Autor();
		
		autor.id_autor = rs.getInt(1);
		autor.nombre = rs.getString(2);
		autor.apellidos = rs.getString(3);
		
		return autor;
	}
	
	@Override
	public List<Autor> obtenerTodos(){
		Connection conexion = null;
		ArrayList<Autor> autores = new ArrayList<Autor>();
		Statement stmt;
		ResultSet rs;
		
		conexion = ConectorBD.conectar();
		
		try {
			stmt = conexion.createStatement();
			rs = stmt.executeQuery("SELECT * FROM autores;");
				
			while(rs.next()) {
				autores.add(nuevaInstancia(rs));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try{ conexion.close(); } catch(Exception e){ }
		}
		
		return autores;
	}

	@Override
	public Autor buscarPorID(Object[] pID) {
		Connection conexion = ConectorBD.conectar();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM autores WHERE id_autor = ?;";
		Integer id = (Integer)pID[0];
		
		try {
			stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return nuevaInstancia(rs);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean insertar(Autor entidad) {
		Connection conexion = ConectorBD.conectar();
		PreparedStatement stmt = null;
		String sql = "INSERT INTO autores (nombre, apellidos) VALUES (?,?);";
		int inserciones = 0;
		
		try {
			stmt = conexion.prepareStatement(sql);
			stmt.setString(1, entidad.nombre);
			stmt.setString(2, entidad.apellidos);
			inserciones = stmt.executeUpdate();
			
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
	public boolean actualizar(Autor entidad) {
		Connection conexion = ConectorBD.conectar();
		PreparedStatement stmt = null;
		String sql = "UPDATE autores SET nombre = ?, apellidos = ? WHERE id_autor = ?;";
		int modificaciones = 0;
		
		try {
			stmt = conexion.prepareStatement(sql);
			stmt.setString(1, entidad.nombre);
			stmt.setString(2, entidad.apellidos);
			stmt.setInt(3, entidad.id_autor);
			modificaciones = stmt.executeUpdate();
			
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
	public boolean eliminar(Object[] pID) {
		Connection conexion = ConectorBD.conectar();
		PreparedStatement stmt = null;
		String sql = "DELETE FROM autores SET WHERE id_autor = ?;";
		int modificaciones = 0;
		
		try {
			stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, (int)pID[0]);
			modificaciones = stmt.executeUpdate();
			
			if(modificaciones == 1) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
