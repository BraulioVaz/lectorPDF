package bvaz.os.lector_pdf.modelos.entidades;

public class Carpeta extends Entidad{
	public int id_carpeta;
	public String nombre;
	public int raiz;
	
	@Override
	public String toString() {
		return nombre;
	}
}
