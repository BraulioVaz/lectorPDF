package bvaz.os.lector_pdf.modelos.entidades;

import java.util.Objects;

@Tabla("autores")
public final class Autor extends Entidad{
	@LlavePrimaria
	public int id_autor;
	public String nombre;
	public String apellidos;
	
	@Override
	public String toString() {
		return nombre + " " + apellidos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, id_autor, nombre);
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
		
		Autor other = (Autor) obj;
		return Objects.equals(apellidos, other.apellidos) && id_autor == other.id_autor
				&& Objects.equals(nombre, other.nombre);
	}
}
