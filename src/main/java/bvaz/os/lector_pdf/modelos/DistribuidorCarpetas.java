package bvaz.os.lector_pdf.modelos;

import java.sql.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.Carpeta;
import bvaz.os.lector_pdf.modelos.tda.Arbol;

public class DistribuidorCarpetas extends DistribuidorEntidades<Carpeta>{

	@Override
	protected Carpeta instanciar(ResultSet rs) throws SQLException {
		Carpeta carpeta = new Carpeta();
		
		carpeta.id_carpeta = rs.getInt(1);
		carpeta.nombre = rs.getString(2);
		carpeta.raiz = rs.getInt(3);
		
		if(rs.wasNull()) {
			carpeta.raiz = -1;
		}
		
		return carpeta;
	}

	@Override
	public List<Carpeta> obtenerTodos() {
		Connection conexion = null;
		ArrayList<Carpeta> carpetas = new ArrayList<Carpeta>();
		Statement stmt;
		ResultSet rs;
		
		conexion = ConectorBD.conectar();
		
		try {
			stmt = conexion.createStatement();
			rs = stmt.executeQuery("SELECT * FROM carpetas;");
				
			while(rs.next()) {
				carpetas.add(instanciar(rs));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try{ conexion.close(); } catch(Exception e){ }
		}
		
		return carpetas;
	}

	@Override
	public Carpeta buscarPorID(Object[] pID) {
		Carpeta carpetaBuscada = null;
		Connection conexion = null;
		PreparedStatement stmt = null;
		ResultSet resultado = null;
		int id = (int) pID[0];
		
		conexion = ConectorBD.conectar();
		
		try {
			stmt = conexion.prepareStatement("SELECT * FROM carpetas WHERE id_carpeta = ?;");
			stmt.setInt(1, id);
			resultado = stmt.executeQuery();
			
			if(resultado.next()) {
				carpetaBuscada = instanciar(resultado);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {conexion.close(); } catch(Exception e) {}
		}
		
		return carpetaBuscada;
	}

	@Override
	public boolean insertar(Carpeta entidad) {
		boolean operacionExitosa = false;
		Connection conexion = null;
		PreparedStatement stmt = null;
		
		conexion = ConectorBD.conectar();
		
		try {
			stmt = conexion.prepareStatement("INSERT INTO carpetas (nombre, raiz) VALUES (?,?);");
			stmt.setString(1, entidad.nombre);
			
			if(entidad.raiz == -1) {
				stmt.setNull(2, Types.INTEGER);
			}
			else {
				stmt.setInt(2, entidad.raiz);
			}
			
			operacionExitosa = stmt.executeUpdate() == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {conexion.close(); } catch(Exception e) {}
		}
		
		return operacionExitosa;
	}

	@Override
	public boolean actualizar(Carpeta entidad) {
		boolean operacionExitosa = false;
		Connection conexion = null;
		PreparedStatement stmt = null;
		
		conexion = ConectorBD.conectar();
		
		try {
			stmt = conexion.prepareStatement("UPDATE carpetas SET nombre = ?, raiz = ? WHERE id_carpeta = ?;");
			stmt.setString(1, entidad.nombre);
			
			if(entidad.raiz == -1) {
				stmt.setNull(2, Types.INTEGER);
			}
			else {
				stmt.setInt(2, entidad.raiz);
			}
			
			stmt.setInt(3, entidad.id_carpeta);
			operacionExitosa = stmt.executeUpdate() == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {conexion.close(); } catch(Exception e) {}
		}
		
		return operacionExitosa;
	}

	@Override
	public boolean eliminar(Object[] pID) {
		boolean operacionExitosa = false;
		Connection conexion = null;
		PreparedStatement stmt = null;
		int id = (int) pID[0];
		conexion = ConectorBD.conectar();
		
		try {
			stmt = conexion.prepareStatement("DELETE FROM carpetas WHERE id_carpeta = ?;");
			stmt.setInt(1, id);
			
			operacionExitosa = stmt.executeUpdate() == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {conexion.close(); } catch(Exception e) {}
		}
		
		return operacionExitosa;
	}
	
	/**
	 * Genera un arbol que representa la estructura de las carpetas almacenadas,
	 * a excepcion de la raiz cada nodo tiene por contenido un objeto {@link Carpeta}.
	 * @return Estructura de las carpetas.
	 */
	public Arbol estructurarCarpetas() {
		List<Carpeta> carpetas = obtenerTodos();
		Arbol raiz = new Arbol("Raiz");
		ArrayList<Arbol> nodosSinRevisar = new ArrayList<Arbol>();
		Arbol nodoActual, subarbol;
		Carpeta contenidoNodo;
		
		for(Carpeta c : carpetas) {
			if(c.raiz == -1) {
				nodoActual = new Arbol(c);
				raiz.agregarHijo(nodoActual);
				nodosSinRevisar.add(nodoActual);
			}
		}
		
		while(!nodosSinRevisar.isEmpty()) {
			nodoActual = nodosSinRevisar.get(0);
			contenidoNodo = (Carpeta) nodoActual.raiz();
			
			for(Carpeta c : carpetas) {
				if(c.raiz == contenidoNodo.id_carpeta) {
					subarbol = new Arbol(c);
					nodoActual.agregarHijo(subarbol);
					nodosSinRevisar.add(subarbol);
				}
			}
			
			nodosSinRevisar.remove(0);
		}
		
		return raiz;
	}

}
