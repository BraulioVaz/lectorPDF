package bvaz.os.lector_pdf.modelos;

import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.*;
import bvaz.os.lector_pdf.modelos.tda.Arbol;

public class SistemaArchivos {
	private static SistemaArchivos instancia;
	private DistribuidorCarpetas manejadorCarpetas;
	private DistribuidorLibros manejadorLibros;
	
	private SistemaArchivos() {
		manejadorCarpetas = new DistribuidorCarpetas();
		manejadorLibros = new DistribuidorLibros();
	}
	
	public static SistemaArchivos obtenerInstancia() {
		if(instancia == null) {
			instancia = new SistemaArchivos();
		}
		
		return instancia;
	}
	
	/**
	 * Genera un arbol que representa la estructura de las carpetas almacenadas,
	 * a excepcion de la raiz cada nodo tiene por contenido un objeto {@link Carpeta}.
	 * @return Estructura de las carpetas.
	 */
	public Arbol estructurarCarpetas() {
		List<Carpeta> carpetas = manejadorCarpetas.obtenerTodos();
		Arbol raiz = new Arbol("RAIZ");
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
	
	/**
	 * Genera la estructura completa del sistema de archivos, es decir incluye 
	 * carpetas y archivos.
	 * @return Estructura del sistema de archivos
	 */
	public Arbol estructurarArchivos() {
		ArrayList<Libro> libros = (ArrayList<Libro>) manejadorLibros.obtenerTodos();
		ArrayList<Libro> librosAgregados = new ArrayList<Libro>();
		Arbol raiz = estructurarCarpetas();
		
		agregarHojas(raiz, librosAgregados);
		libros.removeAll(librosAgregados);
		
		for(Libro l : libros) {
			raiz.agregarHijo(new Arbol(l));
		}
		
		return raiz;
	}
	
	private void agregarHojas(Arbol raiz, ArrayList<Libro> librosAgregados) {
		Carpeta carpetaActual = null;
		Libro libroActual = null;
		List<Integer> idLibros = null;
		Object[] parametroBusqueda = new Object[1];
		int cantidadDeSubcarpetas = raiz.hijos().size();
		
		for(int i = 0; i < cantidadDeSubcarpetas; i++) {
			Arbol nodoActual = raiz.hijos().get(i);
			
			agregarHojas(nodoActual, librosAgregados);
			
			carpetaActual = (Carpeta) nodoActual.raiz();
			idLibros = manejadorCarpetas.contenido(carpetaActual);
			
			for(Integer id : idLibros) {
				parametroBusqueda[0] = id;
				libroActual = manejadorLibros.buscarPorID(parametroBusqueda);
				
				nodoActual.agregarHijo(new Arbol(libroActual));
				if(!librosAgregados.contains(libroActual)) {
					librosAgregados.add(libroActual);
				}
			}
		}
	}
}
