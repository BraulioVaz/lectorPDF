package bvaz.os.lector_pdf.modelos.tda;

import java.util.*;

public class Arbol {
	private Object raiz;
	private List<Arbol> hijos;
	
	/**
	 * Crea un arbol.
	 * @param pRaiz Contenido del nodo
	 */
	public Arbol(Object pRaiz) {
		this.raiz = pRaiz;
		this.hijos = new ArrayList<Arbol>();
	}
	
	/**
	 * Agrega el subarbol.
	 * @param hijo
	 */
	public void agregarHijo(Arbol hijo) {
		this.hijos.add(hijo);
	}
	
	/**
	 * Recupera el contenido del nodo.
	 * @return Raiz del arbol
	 */
	public Object raiz() {
		return this.raiz;
	}
	
	/**
	 * Recupera los subarboles de este nodo.
	 * @return Lista de subarboles
	 */
	public List<Arbol> hijos(){
		return this.hijos;
	}
	
}
