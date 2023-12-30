package bvaz.os.lector_pdf.modelos;

import bvaz.os.lector_pdf.modelos.entidades.Editorial;

public class DistribuidorEditoriales extends DistribuidorEntidades<Editorial>{
	
	public DistribuidorEditoriales() {
		super(Editorial.class);
	}

	/**
	 * Busca una editorial por su ID.
	 * @param ID
	 * @return La editorial o nulo si no hay un registro con ese ID.
	 */
	public Editorial buscarPorID(int ID) {
		Object[] pk = {ID};
		
		return this.buscarPorID(pk);
	}

	/**
	 * Elimina una editorial por su ID.
	 * @param ID
	 * @return true si la editorial fue eliminada, false en caso contrario.
	 */
	public boolean eliminar(int ID) {
		Object[] pk = {ID};
		
		return this.eliminar(pk);
	}

}
