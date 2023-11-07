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
	
	@Override
	public boolean equals(Object objComparado) {
		Carpeta otraCarpeta;
		
		if(!(objComparado instanceof Carpeta)) {
			return false;
		}
		
		otraCarpeta = (Carpeta) objComparado;
		if(this.id_carpeta == otraCarpeta.id_carpeta) {
			return true;
		}
		
		return false;
	}
}
