package bvaz.os.lector_pdf.modelos.entidades;

import java.util.Objects;

@Tabla("carpetas")
public final class Carpeta extends Entidad{
	@LlavePrimaria
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
	public int hashCode() {
		return Objects.hash(id_carpeta, nombre, raiz);
	}

	@Override
	public boolean equals(Object obj) {
		Carpeta otraCarpeta;
		boolean resultado;
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		otraCarpeta = (Carpeta) obj;
		resultado = id_carpeta == otraCarpeta.id_carpeta;
		resultado = resultado && Objects.equals(nombre, otraCarpeta.nombre);
		resultado = resultado && raiz == otraCarpeta.raiz;
		
		return resultado;   
	}
}
