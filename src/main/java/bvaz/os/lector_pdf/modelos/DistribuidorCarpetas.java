package bvaz.os.lector_pdf.modelos;

import java.sql.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.Carpeta;
import bvaz.os.lector_pdf.modelos.entidades.Libro;

public class DistribuidorCarpetas extends DistribuidorEntidades<Carpeta>{

	public DistribuidorCarpetas() {
		super(Carpeta.class);
	}

	/**
	 * Busca una carpeta por su ID
	 * @param ID
	 * @return La carpeta o nulo si no hay un registro con ese ID.
	 */
	public Carpeta buscarPorID(int ID) {
		Object[] pk = {ID};
		
		return this.buscarPorID(pk);
	}

	/**
	 * Elimina una carpeta por su ID.
	 * @param ID
	 * @return true si la carpeta fue eliminada, false en caso contrario.
	 */
	public boolean eliminar(int ID) {
		Object[] pk = {ID};
		
		return this.eliminar(pk);
	}
	
	public List<Integer> contenido(Carpeta carpeta) {
		Connection conexion = ConectorBD.conectar();
		ResultSet resultado = null;
		String sql = "SELECT * FROM libros_carpetas WHERE carpeta = ?;";
		ArrayList<Integer> clavesLibros = new ArrayList<Integer>();
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, carpeta.id_carpeta);
			resultado = sentencia.executeQuery();
			
			if(resultado.next()) {
				clavesLibros.add(resultado.getInt("libro"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return clavesLibros;
	}
	
	public boolean registrarLibro(Carpeta pCarpeta, Libro pLibro) {
		Connection conexion = ConectorBD.conectar();
		String sql = "INSERT INTO libros_carpetas (libro, carpeta) VALUES (?, ?)";
		int nuevosRegistros = 0;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, pLibro.id_libro);
			sentencia.setInt(2, pCarpeta.id_carpeta);
			
			nuevosRegistros = sentencia.executeUpdate();
			
			if(nuevosRegistros == 1) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean quitarLibroDeCarpeta(Carpeta pCarpeta, Libro pLibro) {
		Connection conexion = ConectorBD.conectar();
		String sql = "DELETE FROM libros_carpetas WHERE libro = ? AND carpeta = ?;";
		int registrosEliminados = 0;
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, pLibro.id_libro);
			sentencia.setInt(2, pCarpeta.id_carpeta);
			
			registrosEliminados = sentencia.executeUpdate();
			
			if(registrosEliminados == 1) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
