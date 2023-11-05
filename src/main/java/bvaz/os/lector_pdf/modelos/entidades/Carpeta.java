package bvaz.os.lector_pdf.modelos.entidades;

public class Carpeta extends Entidad{
	public int id_carpeta;
	public String nombre;
	public int raiz;
	
	public Carpeta() {
		raiz = -1;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
