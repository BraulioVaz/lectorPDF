package bvaz.os.lector_pdf.modelos;

import java.util.*;
import java.sql.*;
import bvaz.os.lector_pdf.modelos.entidades.*;

public class DistribuidorLibros extends DistribuidorEntidades<Libro>{

	public DistribuidorLibros() {
		super(Libro.class);
	}

	/**
	 * Busca un libro por su ID.
	 * @param ID
	 * @return El libro o nulo si no hay un registro con ese ID
	 */
	public Libro buscarPorID(int ID) {
		Object[] pk = {ID};
		
		return this.buscarPorID(pk);
	}

	/**
	 * Elimina un libro por su ID.
	 * @param ID
	 * @return true si el libro fue eliminado, false en caso contrario.
	 */
	public boolean eliminar(int ID) {
		Object[] pk = {ID};
		
		return this.eliminar(pk);
	}
	
	/**
	 * Guarda la relacion libro - autores
	 * @param libro
	 * @param autores Listado de autores a quienes se atribuye el libro
	 */
	public void registrarAutores(Libro libro, List<Autor> autores) {
		Connection conexion = ConectorBD.conectar();
		String sql = "INSERT INTO libros_autores (libro, autor) VALUES (?, ?);";
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)){
			for(Autor autor : autores) {
				sentencia.setInt(1, libro.id_libro);
				sentencia.setInt(2, autor.id_autor);
				
				sentencia.executeUpdate();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recupera los autores de un libro.
	 * @param libro
	 * @return Listado con los ID de los autores, si no estan registrados o si el libro no existe la lista 
	 * esta vacia.
	 */
	public List<Integer> autores(Libro libro){
		Connection conexion = ConectorBD.conectar();
		ResultSet resultado = null;
		String sql = "SELECT autor FROM libros_autores WHERE libro = ?;";
		ArrayList<Integer> autores = new ArrayList<Integer>();
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)){
			sentencia.setInt(1, libro.id_libro);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				autores.add(resultado.getInt(1));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return autores;
	}
}
