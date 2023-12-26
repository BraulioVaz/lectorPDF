package bvaz.os.lector_pdf.modelos.entidades;

import java.util.Objects;

public final class Editorial extends Entidad{
	@LlavePrimaria
	public int id_editorial;
	public String nombre;
	
	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_editorial, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Editorial other = (Editorial) obj;
		return id_editorial == other.id_editorial && Objects.equals(nombre, other.nombre);
	}
}
