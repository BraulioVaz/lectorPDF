package bvaz.os.lector_pdf.modelos;

import java.util.*;
import java.sql.*;
import bvaz.os.lector_pdf.modelos.entidades.*;

public abstract class DistribuidorEntidades<T extends Entidad> {
	
	public DistribuidorEntidades() {
		
	}
	
	/**
	 * Crea una instancia basada en el resultado de una consulta.
	 * @param rs ResultSet de consulta SQL
	 * @return Instancia de la entidad con los datos del ResultSet
	 */
	protected abstract T instanciar(ResultSet rs) throws SQLException;
	
	/**
	 * Recupera una lista de todas las entidades almacenadas en la BD.
	 * @return Lista con las entidades, pudiendo estar vacia si no hay registros
	 * en la tabla.
	 */
	public abstract List<T> obtenerTodos();
	
	/**
	 * Recupera la entidad cuya clave primaria coincide con los parametros.
	 * @param id Arreglo con valores de la clave primaria, pudiendo ser de longitud 1 para
	 * claves simples y de longitud 'n' para claves compuestas
	 * @return La entidad o nulo si ningun registro coincide
	 */
	public abstract T buscarPorID(Object[] id);
	
	/**
	 * Inserta un nuevo registro.
	 * @param entidad La entidad que se va a agregar.
	 * @return El estado de exito de la operacion.
	 */
	public abstract boolean insertar(T entidad);
	
	/**
	 * Actualiza el registro cuyo ID coincide con la entidad cambiando todos los campos no primarios.
	 * @param entidad Entidad a actualizar.
	 * @return El estado de exito de la operacion.
	 */
	public abstract boolean actualizar(T entidad);
	
	/**
	 * Elimina el registro cuya clave primaria coincide con los parametros.
	 * @param id Arreglo con valores de la clave primaria, pudiendo ser de longitud 1 para
	 * claves simples y de longitud 'n' para claves compuestas
	 * @return El estado de exito de la operacion
	 */
	public abstract boolean eliminar(Object[] id);
}
