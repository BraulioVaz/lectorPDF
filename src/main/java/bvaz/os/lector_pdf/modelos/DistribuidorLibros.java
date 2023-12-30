package bvaz.os.lector_pdf.modelos;

import bvaz.os.lector_pdf.modelos.entidades.*;

public class DistribuidorLibros extends DistribuidorEntidades<Libro>{

	public DistribuidorLibros() {
		super(Libro.class);
	}

	/**
	 * Busca un libro por su ID.
	 * @param ID
	 * @return El libro o nulo si no hay un registro con ese ID
	 */
	public Libro buscarPorID(int ID) {
		Object[] pk = {ID};
		
		return this.buscarPorID(pk);
	}

	/**
	 * Elimina un libro por su ID.
	 * @param ID
	 * @return true si el libro fue eliminado, false en caso contrario.
	 */
	public boolean eliminar(int ID) {
		Object[] pk = {ID};
		
		return this.eliminar(pk);
	}
}
