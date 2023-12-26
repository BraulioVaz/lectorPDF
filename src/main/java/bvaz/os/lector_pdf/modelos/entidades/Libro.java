package bvaz.os.lector_pdf.modelos.entidades;

import java.util.Objects;

public final class Libro extends Entidad{
	@LlavePrimaria
	public int id_libro;
	public String titulo;
	public int editorial;
	public String fecha_publicacion;
	public String archivo;
	public String marcador;
	
	@Override
	public String toString() {
		return titulo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(archivo, editorial, fecha_publicacion, id_libro, marcador, titulo);
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
		
		Libro other = (Libro) obj;
		return Objects.equals(archivo, other.archivo) && editorial == other.editorial
				&& Objects.equals(fecha_publicacion, other.fecha_publicacion) && id_libro == other.id_libro
				&& Objects.equals(marcador, other.marcador) && Objects.equals(titulo, other.titulo);
	}
}
