package bvaz.os.lector_pdf.modelos.entidades;

public class Autor extends Entidad{
	public int id_autor;
	public String nombre;
	public String apellidos;
	
	@Override
	public String toString() {
		return "Autor " + id_autor + " : " + nombre + " " + apellidos;
	}
}
