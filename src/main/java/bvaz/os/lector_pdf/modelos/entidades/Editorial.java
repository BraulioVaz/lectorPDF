package bvaz.os.lector_pdf.modelos.entidades;

public class Editorial extends Entidad{
	public int id_editorial;
	public String nombre;
	
	@Override
	public String toString() {
		return nombre;
	}
}
