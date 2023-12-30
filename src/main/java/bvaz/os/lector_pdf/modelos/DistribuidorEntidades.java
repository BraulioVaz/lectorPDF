package bvaz.os.lector_pdf.modelos;

import java.lang.reflect.*;
import java.util.*;
import java.sql.*;
import bvaz.os.lector_pdf.modelos.entidades.*;

public abstract class DistribuidorEntidades<T extends Entidad> {
	private static final int INSERT = 1;
	private static final int UPDATE = 2;
	private Class<T> claseDeEntidad;
	private String nombreDeTabla;
	private Field[] campos;
	
	public DistribuidorEntidades(Class<T> pClaseDeEntidad) {
		claseDeEntidad = pClaseDeEntidad;
		campos = claseDeEntidad.getFields();
		
		Tabla anotacion = claseDeEntidad.getAnnotation(Tabla.class);
		
		if(anotacion == null) {
			throw new IllegalArgumentException("La entidad no implementa la anotacion 'Tabla'");
		}
		
		nombreDeTabla = anotacion.value();
	}
	
	/**
	 * Recupera los campos que conforman la llave primaria de la entidad.
	 * @return Lista de campos.
	 */
	private List<Field> llavePrimaria() {
		ArrayList<Field> llavePrimaria = new ArrayList<Field>();
		
		for(Field c : campos) {
			if(c.getAnnotation(LlavePrimaria.class) != null) {
				llavePrimaria.add(c);
			}
		}
		
		llavePrimaria.sort(new Comparator<Field>() {
			@Override
			public int compare(Field f1, Field f2) {
				LlavePrimaria pk1 = f1.getAnnotation(LlavePrimaria.class);
				LlavePrimaria pk2 = f2.getAnnotation(LlavePrimaria.class);
				
				return pk1.orden() - pk2.orden();
			}
		});
		
		return llavePrimaria;
	}
	
	/**
	 * Genera la sentencia SQL SELECT simple o para busqueda por ID.
	 * @param porLlavePrimaria true si la busqueda utiliza la llave primaria.
	 * @return Sentencia SQL
	 */
	private String sentenciaSelect(boolean porLlavePrimaria) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(nombreDeTabla);
		
		if(porLlavePrimaria) {
			List<Field> llavePrimaria = llavePrimaria();
			Iterator<Field> iterador = llavePrimaria.iterator();
			Field campoActual = null;
			
			sql.append(" WHERE ");
			
			while(iterador.hasNext()) {
				campoActual = iterador.next();
				sql.append(campoActual.getName());
				sql.append(" = ?");
				
				if(iterador.hasNext()) {
					sql.append(" AND ");
				}
			}
		}
		
		sql.append(";");
		
		return sql.toString();
	}
	
	/**
	 * Crea una instancia vacia de la entidad
	 * @return Nueva instancia
	 */
	private T instanciaVacia() {
		Constructor<T> constructor = null;
		T nuevaInstancia = null;
		
		try {
			constructor = claseDeEntidad.getConstructor();
			nuevaInstancia = constructor.newInstance();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return nuevaInstancia;
	}
	
	/**
	 * Crea una instancia basada en el resultado de una consulta.
	 * @param rs ResultSet de consulta SQL
	 * @return Instancia de la entidad con los datos del ResultSet
	 */
	protected T instanciar(ResultSet rs) throws SQLException{
		T nuevaInstancia = instanciaVacia();
		Object valor = null;
		
		for(Field c : campos) {
			valor = rs.getObject(c.getName());
			
			try {
				c.set(nuevaInstancia, valor);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return nuevaInstancia;
	}
	
	/**
	 * Recupera una lista de todas las entidades almacenadas en la BD.
	 * @return Lista con las entidades, pudiendo estar vacia si no hay registros
	 * en la tabla.
	 */
	public List<T> obtenerTodos(){
		Connection conexion = ConectorBD.conectar();
		String sql = sentenciaSelect(false); 
		ResultSet rs = null;
		ArrayList<T> entidadesRecuperadas = new ArrayList<T>();
		
		try(Statement sentencia = conexion.createStatement()) {
			rs = sentencia.executeQuery(sql);
			
			while(rs.next()) {
				entidadesRecuperadas.add(instanciar(rs));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return entidadesRecuperadas;
	}
	
	/**
	 * Recupera la entidad cuya clave primaria coincide con los parametros.
	 * 
	 * Como los campos de la llave primaria usan el orden definido en la anotacion
	 * {@link LlavePrimaria}, se debe encapsular el metodo en las implementaciones especificas
	 * de cada entidad para garantizar el orden de los parametros.
	 * 
	 * @param id Arreglo con valores de la clave primaria, pudiendo ser de longitud 1 para
	 * claves simples y de longitud 'n' para claves compuestas
	 * @return La entidad o nulo si ningun registro coincide
	 */
	protected T buscarPorID(Object[] id) {
		Connection conexion = ConectorBD.conectar();
		ResultSet rs = null;
		T instancia = null;
		String sql = sentenciaSelect(true);
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			for(int i = 0; i < llavePrimaria().size(); i++) {
				sentencia.setObject(i + 1, id[i]);
			}
			
			rs = sentencia.executeQuery();
			instancia = instanciar(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return instancia;
	}
	
	/**
	 * Regresa los campos requeridos en las sentencias INSERT (clausula VALUES) y 
	 * UPDATE (clausula SET).
	 * Para las sentencias INSERT omite las llaves primarias con autoincremento y
	 * para las sentencias UPDATE omite todas las llaves primarias.
	 * @param sentencia El tipo de sentencia
	 * @return Un arreglo con los campos requeridos.
	 */
	private Field[] filtrarCampos(int sentencia) {
		List<Field> camposRequeridos = new ArrayList<Field>();
		
		for(Field c : campos) {
			LlavePrimaria pk = c.getAnnotation(LlavePrimaria.class);
			
			switch(sentencia) {
			case INSERT:
				if(pk != null && pk.autoincremento()) {
					continue;
				}
				break;
			
			case UPDATE:
				if(pk != null) {
					continue;
				}
				break;
			}
			
			camposRequeridos.add(c);
		}
		
		return camposRequeridos.toArray(new Field[0]);
	}
	
	/**
	 * Crea la sentencia SQL para la instruccion Insert.
	 * @param camposRequeridos
	 * @return Cadena con la sentencia SQL.
	 */
	private String sentenciaInsert(Field[] camposRequeridos) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO ");
		sql.append(nombreDeTabla);
		sql.append("(");
		
		for(int i = 0; i < camposRequeridos.length; i++) {
			sql.append(camposRequeridos[i].getName());
			
			if(i < camposRequeridos.length - 1) {
				sql.append(", ");
			}
		}
		
		sql.append(") VALUES (");
		
		for(int i = 0; i < camposRequeridos.length; i++) {
			sql.append("?");
			
			if(i < camposRequeridos.length - 1) {
				sql.append(", ");
			}
		}
		
		sql.append(");");
		
		return sql.toString();
	}
	
	/**
	 * Inserta un nuevo registro.
	 * @param entidad La entidad que se va a agregar.
	 * @return El estado de exito de la operacion.
	 */
	public boolean insertar(T entidad) {
		Connection conexion = ConectorBD.conectar();
		Field[] camposRequeridos = filtrarCampos(INSERT);
		String sql = sentenciaInsert(camposRequeridos);
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			for(int i = 0; i < camposRequeridos.length; i++) {
				Object valor = camposRequeridos[i].get(entidad);
				sentencia.setObject(i + 1, valor);
			}
			
			if(sentencia.executeUpdate() == 1) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String sentenciaUpdate(Field[] camposEnSET, List<Field> llavePrimaria) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE ");
		sql.append(nombreDeTabla);
		sql.append(" SET ");
		
		for(int i = 0; i < camposEnSET.length; i++) {
			sql.append(camposEnSET[i].getName());
			sql.append(" = ?");
			
			if(i < camposEnSET.length - 1) {
				sql.append(", ");
			}
		}
		
		sql.append(" WHERE ");
		
		Iterator<Field> iterador = llavePrimaria.iterator();
		
		while(iterador.hasNext()) {
			Field campoActual = iterador.next();
			
			sql.append(campoActual.getName());
			sql.append(" = ?");
			
			if(iterador.hasNext()) {
				sql.append(" AND ");
			}
		}
		
		sql.append(";");
		
		return sql.toString();
	}
	
	/**
	 * Actualiza el registro cuyo ID coincide con la entidad cambiando todos los campos no primarios.
	 * @param entidad Entidad a actualizar.
	 * @return El estado de exito de la operacion.
	 */
	public boolean actualizar(T entidad) {
		Connection conexion = ConectorBD.conectar();
		Field[] camposEnSET = filtrarCampos(UPDATE);
		List<Field> llavePrimaria = llavePrimaria();
		String sql = sentenciaUpdate(camposEnSET, llavePrimaria);
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			int i;
			
			for(i = 0; i < camposEnSET.length; i++) {
				Object valor = camposEnSET[i].get(entidad);
				sentencia.setObject(i + 1, valor);
			}
			
			for(int j = 0; j < llavePrimaria.size(); j++) {
				Object valor = llavePrimaria.get(j).get(entidad);
				sentencia.setObject(i + 1, valor);
			}
			
			if(sentencia.executeUpdate() == 1) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String sentenciaDelete() {
		List<Field> llavePrimaria = llavePrimaria();
		Iterator<Field> iterador = llavePrimaria.iterator();
		StringBuilder sql = new StringBuilder();
		
		sql.append("DELETE FROM ");
		sql.append(nombreDeTabla);
		sql.append(" WHERE ");
		
		while(iterador.hasNext()) {
			Field campoActual = iterador.next();
			
			sql.append(campoActual.getName());
			sql.append(" = ?");
			
			if(iterador.hasNext()) {
				sql.append(" AND ");
			}
		}
		
		sql.append(";");
		
		return sql.toString();
	}
	
	/**
	 * Elimina el registro cuya clave primaria coincide con los parametros.
	 * Al igual que {@link #buscarPorID(Object[])} se recomienda su ecapsulacion 
	 * para garantizar el orden de los campos de la llave primaria.
	 * @param id Arreglo con valores de la clave primaria, pudiendo ser de longitud 1 para
	 * claves simples y de longitud 'n' para claves compuestas
	 * @return El estado de exito de la operacion
	 */
	protected boolean eliminar(Object[] id) {
		Connection conexion = ConectorBD.conectar();
		String sql = sentenciaDelete();
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			for(int i = 0; i < llavePrimaria().size(); i++) {
				sentencia.setObject(i + 1, id[i]);
			}
			
			if(sentencia.executeUpdate() == 1) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
