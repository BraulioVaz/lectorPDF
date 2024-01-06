package bvaz.os.lector_pdf.modelos.vistas;

import java.util.*;
import bvaz.os.lector_pdf.modelos.entidades.*;

public class LibroCualificado {
	private Libro libro;
	private Editorial editorial;
	private ArrayList<Autor> autores;
	
	public LibroCualificado(Libro pLibro, Editorial pEditorial, List<Autor> pAutores) {
		autores = new ArrayList<Autor>();
		
		libro = pLibro;
		editorial = pEditorial;
		autores.addAll(pAutores);
	}
	
	public int getIDLibro() {
		return libro.id_libro;
	}
	
	public String getTitulo() {
		return libro.titulo;
	}
	
	public Editorial getEditorial() {
		return editorial;
	}
	
	public String getFechaDePublicacion() {
		return libro.fecha_publicacion;
	}
	
	public String getUbicacionDeArchivo() {
		return libro.archivo;
	}
	
	public List<Autor> getAutores(){
		return autores;
	}
	
	@Override
	public String toString() {
		return libro.toString();
	}
}
