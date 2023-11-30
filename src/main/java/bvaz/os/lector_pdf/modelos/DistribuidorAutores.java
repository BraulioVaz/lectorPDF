package bvaz.os.lector_pdf.modelos;

import java.util.*;
import java.sql.*;
import bvaz.os.lector_pdf.modelos.entidades.Autor;

public class DistribuidorAutores extends DistribuidorEntidades<Autor>{
	
	protected Autor instanciar(ResultSet rs) throws SQLException{
		Autor autor = new Autor();
		
		autor.id_autor = rs.getInt(1);
		autor.nombre = rs.getString(2);
		autor.apellidos = rs.getString(3);
		
		return autor;
	}
	
	@Override
	public List<Autor> obtenerTodos(){
		Connection conexion = ConectorBD.conectar();
		ArrayList<Autor> autores = new ArrayList<Autor>();
		ResultSet rs;
		
		try(Statement sentencia = conexion.createStatement()){
			rs = sentencia.executeQuery("SELECT * FROM autores;");
				
			while(rs.next()) {
				autores.add(instanciar(rs));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return autores;
	}

	@Override
	public Autor buscarPorID(Object[] pID) {
		Connection conexion = ConectorBD.conectar();
		ResultSet rs = null;
		String sql = "SELECT * FROM autores WHERE id_autor = ?;";
		Integer id = (Integer)pID[0];
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, id);
			rs = sentencia.executeQuery();
			
			if(rs.next()) {
				return instanciar(rs);
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
		String sql = "INSERT INTO autores (nombre, apellidos) VALUES (?,?);";
		int inserciones = 0;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, entidad.nombre);
			sentencia.setString(2, entidad.apellidos);
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
	public boolean actualizar(Autor entidad) {
		Connection conexion = ConectorBD.conectar();
		String sql = "UPDATE autores SET nombre = ?, apellidos = ? WHERE id_autor = ?;";
		int modificaciones = 0;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)){
			sentencia.setString(1, entidad.nombre);
			sentencia.setString(2, entidad.apellidos);
			sentencia.setInt(3, entidad.id_autor);
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
	public boolean eliminar(Object[] pID) {
		Connection conexion = ConectorBD.conectar();
		String sql = "DELETE FROM autores SET WHERE id_autor = ?;";
		int modificaciones = 0;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, (int)pID[0]);
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

}
