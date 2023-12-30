package bvaz.os.lector_pdf.modelos;

import bvaz.os.lector_pdf.modelos.entidades.Autor;

public class DistribuidorAutores extends DistribuidorEntidades<Autor>{
	
	public DistribuidorAutores() {
		super(Autor.class);
	}
	
	/**
	 * Busca un autor por su ID.
	 * @param ID
	 * @return El autor o nulo si no hay un registro con la ID indicada
	 */
	public Autor buscarPorID(int ID) {
		Object[] pk = {ID};
		
		return this.buscarPorID(pk);
	}
	
	/**
	 * Elimina a un autor por su ID
	 * @param ID
	 * @return true si la eliminacion fue exitosa, false en caso contrario
	 */
	public boolean eliminar(int ID) {
		Object[] pk = {ID};
		
		return this.eliminar(pk);
	}
}
