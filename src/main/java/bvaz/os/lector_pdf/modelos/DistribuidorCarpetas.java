package bvaz.os.lector_pdf.modelos;

import java.sql.*;
import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.Carpeta;
import bvaz.os.lector_pdf.modelos.entidades.Libro;

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
		Connection conexion = ConectorBD.conectar();
		ArrayList<Carpeta> carpetas = new ArrayList<Carpeta>();
		ResultSet rs;
		
		try(Statement sentencia = conexion.createStatement()) {
			rs = sentencia.executeQuery("SELECT * FROM carpetas;");
				
			while(rs.next()) {
				carpetas.add(instanciar(rs));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return carpetas;
	}

	@Override
	public Carpeta buscarPorID(Object[] pID) {
		Carpeta carpetaBuscada = null;
		Connection conexion = ConectorBD.conectar();
		ResultSet resultado = null;
		String sql = "SELECT * FROM carpetas WHERE id_carpeta = ?;";
		int id = (int) pID[0];
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, id);
			resultado = sentencia.executeQuery();
			
			if(resultado.next()) {
				carpetaBuscada = instanciar(resultado);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return carpetaBuscada;
	}

	@Override
	public boolean insertar(Carpeta entidad) {
		boolean operacionExitosa = false;
		Connection conexion = ConectorBD.conectar();
		String sql = "INSERT INTO carpetas (nombre, raiz) VALUES (?,?);";
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, entidad.nombre);
			
			if(entidad.raiz == -1) {
				sentencia.setNull(2, Types.INTEGER);
			}
			else {
				sentencia.setInt(2, entidad.raiz);
			}
			
			operacionExitosa = sentencia.executeUpdate() == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return operacionExitosa;
	}

	@Override
	public boolean actualizar(Carpeta entidad) {
		boolean operacionExitosa = false;
		Connection conexion = ConectorBD.conectar();
		String sql = "UPDATE carpetas SET nombre = ?, raiz = ? WHERE id_carpeta = ?;";
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, entidad.nombre);
			
			if(entidad.raiz == -1) {
				sentencia.setNull(2, Types.INTEGER);
			}
			else {
				sentencia.setInt(2, entidad.raiz);
			}
			
			sentencia.setInt(3, entidad.id_carpeta);
			operacionExitosa = sentencia.executeUpdate() == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return operacionExitosa;
	}

	@Override
	public boolean eliminar(Object[] pID) {
		boolean operacionExitosa = false;
		Connection conexion = ConectorBD.conectar();
		String sql = "DELETE FROM carpetas WHERE id_carpeta = ?;";
		int id = (int) pID[0];
		
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setInt(1, id);
			
			operacionExitosa = sentencia.executeUpdate() == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return operacionExitosa;
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
