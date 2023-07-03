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
	
	/**
	 * Recupera todos los autores almacenados.
	 * @return Lista de autores
	 */
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
	public Autor buscarPorID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertar(Autor entidad) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean actualizar(Autor entidad) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
