package bvaz.os.lector_pdf.modelos.entidades;

import java.util.*;

public class Libro extends Entidad{
	public int id_libro;
	public String titulo;
	public int autor;
	public int editorial;
	public Date fecha_publicacion;
	public String archivo;
	public String marcador;
	
	@Override
	public String toString() {
		return titulo + " ( " + fecha_publicacion + " )";
	}
}
